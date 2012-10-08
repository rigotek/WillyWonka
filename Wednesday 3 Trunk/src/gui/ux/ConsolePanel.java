package gui.ux;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Displays any System.out messages sent by any of the GUI agents or engine agents in a JmasterTextArea.
 * Allows the user to clear the textbox by clicking the "clear" button.
 * 
 * @author David Tan
 */
public class ConsolePanel extends JPanel implements GuiInterface, Runnable {
	
	/** Refers to the last update of the ConsolePanel class. */
	private static final long serialVersionUID = 20111127L;
	/** Refers to the internal ID of the ConsolePanel class. */
	private static final String CONSOLEPANELID = "WQOHKOWBYWCW";
	
	// --------------- DATA ---------------
	
	/** Represents the parent JFrame in which ControlPanel resides. */
	MainInterface parentFrame;
	
	/** Represents the scroll pane that contains a text area. */
	private JScrollPane scrollPane;
	/** Represents a blank text area if the user does not wish to see any console text. */
	private JTextArea blankTextArea;
	/** Represents the current text area in view. */
	private JTextArea currentTextArea;
	/** Represents the master text area in which all messages will be written. */
	private JTextArea masterTextArea;
	/** Represents the subsidiary text areas that write from the master text area. */
	private JTextArea subTextArea[];
	/** Represents the thread that will redirect System.out. */
	private Thread reader;
	
	/** Represents strings of all available filters for the console. */
	private String[] filterOptions;
	/** Represents all available filters for the console. */
	private JComboBox jcmbFilter;
	/** Represents whether scrolling is enabled. */
	private boolean scrolling;
	/** Represents the button that will enable or disable scrolling. */
	private JButton jbtnScroll;
	/** Represents the button that can clear the text area. */
	private JButton jbtnClear;
	
	/** Holds all possible filters for the console. */
	private enum Filter {
		/** Signifies that the console currently will not print text. */
		None,
		/** Signifies that the console currently has no filter. */
		All,
		/** Signifies that the console is only displaying messages from the GantryRobot. */
		GantryRobot,
		/** Signifies that the console is only displaying messages from the FeederAgents. */
		FeederAgent,
		/** Signifies that the console is only displaying messages from the LaneAgents. */
		LaneAgent,
		/** Signifies that the console is only displaying messages from the NestAgents. */
		Nest,
		/** Signifies that the console is only displaying messages from the Cameras. */
		Camera,
		/** Signifies that the console is only displaying messages from the PartsRobot. */
		PartsRobot,
		/** Signifies that the console is only displaying messages from the KitRobot. */
		KitRobot,
		/** Signifies that the console is only displaying messages from the KitStand. */
		KitStand,
		/** Signifies that the console is only displaying messages from the KitCamera. */
		KitCamera
	}
	/** Enum that represents the current filter. */
	private Filter currentFilter;
	
	/** Represents the piped input stream that receives all incoming data. */
	private final PipedInputStream pin;

	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * Public constructor for ConsolePanel. Creates the textbox, clear button, and prepares
	 * the console to receive all System.out data.
	 * 
	 * @param mainInterface Provides reference to parent JFrame
	 */
	public ConsolePanel (MainInterface mainInterface) {
		parentFrame = mainInterface;
		setPreferredSize(new Dimension(200, 226));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Console"));
		
		// Initialisation of the master text area, the "clear" button, and the scroll button.
		masterTextArea = new JTextArea();
		masterTextArea.setEditable(false);
		masterTextArea.setFont(new Font("Dialog", Font.PLAIN, 10));
		blankTextArea = new JTextArea();
		blankTextArea.setEditable(false);
		jbtnClear = new JButton("Clear text");
		jbtnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterTextArea.setText("");
			}
		});
		scrolling = true;
		jbtnScroll = new JButton("Disable scrolling");
		jbtnScroll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scrolling) {
					scrolling = false;
					jbtnScroll.setText("Enable scrolling");
				} else {
					scrolling = true;
					jbtnScroll.setText("Disable scrolling");
					masterTextArea.setCaretPosition(masterTextArea.getText().length());
				}
			}
		});
		
		// Initialisation of the subsidiary text areas.
		subTextArea = new JTextArea[10];
		for (int i = 0; i < 9; i++) {
			subTextArea[i] = new JTextArea();
			subTextArea[i].setEditable(false);
			subTextArea[i].setFont(new Font("Dialog", Font.PLAIN, 10));
		}
		
		// Initialisation of the filter selection.
		filterOptions = new String[11];
		filterOptions[0] = "None"; filterOptions[1] = "All";
		filterOptions[2] = "GantryRobot"; filterOptions[3] = "FeederAgent";
		filterOptions[4] = "LaneAgent"; filterOptions[5] = "Nest";
		filterOptions[6] = "Camera"; filterOptions[7] = "PartsRobot";
		filterOptions[8] = "KitStand"; filterOptions[9] = "KitRobot";
		filterOptions[10] = "KitCamera";
		jcmbFilter = new JComboBox(filterOptions);
		jcmbFilter.setSelectedIndex(1);
		jcmbFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox) e.getSource();
		        currentFilter = Filter.valueOf(((String) cb.getSelectedItem()));
		        switch ( currentFilter ) {
		        case None:			currentTextArea = blankTextArea; break;
		        case All:			currentTextArea = masterTextArea; break;
		        case GantryRobot:	currentTextArea = subTextArea[0]; break;
		        case FeederAgent:	currentTextArea = subTextArea[1]; break;
		        case LaneAgent:		currentTextArea = subTextArea[2]; break;
		        case Nest:			currentTextArea = subTextArea[3]; break;
		        case Camera:		currentTextArea = subTextArea[4]; break;
		        case PartsRobot:	currentTextArea = subTextArea[5]; break;
		        case KitStand:		currentTextArea = subTextArea[6]; break;
		        case KitRobot:		currentTextArea = subTextArea[7]; break;
		        case KitCamera:		currentTextArea = subTextArea[8]; break;
		        }
		        scrollPane.setViewportView(currentTextArea);
				if (scrolling) {
					currentTextArea.setCaretPosition(currentTextArea.getText().length());
				}
			}
		});
		currentFilter = Filter.All;
		
		// Initialisation of the piped input stream. There is no piped output stream that will stay
		// in focus as all read data will be redirected to the text area.
		pin = new PipedInputStream();
		
		// Initialisation of a panel to contain the two buttons.
		JPanel buttonRow = new JPanel();
		buttonRow.setLayout(new FlowLayout());
		buttonRow.add(jbtnScroll);
		buttonRow.add(jbtnClear);
		
		// Adding the new text area and the button to the panel. The text area is above the button.
		currentTextArea = masterTextArea;
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(currentTextArea);
		add(jcmbFilter, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonRow, BorderLayout.SOUTH);
		
		// Attempt to redirect all System.out to this console.
		try {
			PipedOutputStream pout = new PipedOutputStream(pin);
			System.setOut(new PrintStream(pout, true));
		}
		catch (IOException e) {
			masterTextArea.append("Unable to redirect STDOUT to this console:\n" + e.getMessage() + "\n");
		}
		catch (SecurityException e) {
			masterTextArea.append("Unable to redirect STDOUT to this console:\n" + e.getMessage() + "\n");
		}
		
		// Start the reader, which will intercept all System.out.
		reader = new Thread(this);
		reader.setDaemon(true);
		reader.start();
	}

	// --------------- ACTIONS ---------------
	
	@Override
	public synchronized void run() {
		try {
			while (Thread.currentThread() == reader) {
				try { this.wait(100); } catch (InterruptedException e) { }
				if (pin.available() != 0) {
					String input = this.readLine(pin);
					StringTokenizer consoleSplitter = new StringTokenizer(input, "\n");
					masterTextArea.append(input);
					while (consoleSplitter.hasMoreTokens()) {
						String nextToken = consoleSplitter.nextToken();
						nextToken += "\n";
						if (nextToken.startsWith("GantryRobot")) { subTextArea[0].append(nextToken); }
						if (nextToken.startsWith("FeederAgent")) { subTextArea[1].append(nextToken); }
						if (nextToken.startsWith("LaneAgent")) { subTextArea[2].append(nextToken); }
						if (nextToken.startsWith("Nest")) { subTextArea[3].append(nextToken); }
						if (nextToken.startsWith("Camera")) { subTextArea[4].append(nextToken); }
						if (nextToken.startsWith("PartsRobot")) { subTextArea[5].append(nextToken); }
						if (nextToken.startsWith("KitStand")) { subTextArea[6].append(nextToken); }
						if (nextToken.startsWith("KitRobot")) { subTextArea[7].append(nextToken); }
						if (nextToken.startsWith("KitCamera")) { subTextArea[8].append(nextToken); }
					}
					if (scrolling) {
						currentTextArea.setCaretPosition(currentTextArea.getText().length());
					}
				}
			}
		}
		
		catch (Exception e) {
			masterTextArea.append("Console reports an internal error:\n");
			masterTextArea.append(e + "\n");
		}
	}
	
	/**
	 * Reads a line of text from a PipedInputStream and returns said line to the method
	 * caller.
	 * 
	 * @param pinput PipedInputStream from which a line of text is to be read
	 * @return Returns a line of text as was read from the PipedInputStream
	 * @throws IOException Thrown by PipedInputStream.available() in the event of an error
	 */
	private synchronized String readLine(PipedInputStream pinput) throws IOException {
		String lineFeed = "";
		do {
			int available = pinput.available();
			if (available == 0) {
				break;
			}
			byte b[] = new byte[available];
			pinput.read(b);
			lineFeed += new String(b, 0, b.length);
		} while (!lineFeed.endsWith("\n") && !lineFeed.endsWith("\r\n"));
		return lineFeed;
	}
	
	@Override
	public String returnPanelID() {
		return CONSOLEPANELID;
	}
}
