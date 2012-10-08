LaneCameraAgent Unit Tests
Tyler Baetz

1. Tests that the lane camera receives both messages (checks log to verify)
2. Tests that the lane camera receives the message from timer when it fires (checks log to verify)
3. Tests that the lane camera receives the callback from GUI(checks log to verify) and then checks that the camera correctly messages both nests
4. Tests that the lane camera receives the parts lists from both nests (checks log to verify as well as compares list) and then checks that the parts robot receives two messages containing the list
5. Tests that resetting the timer works -- initially null and then exists after calling the scheduler.  Sending another message and comparing the timers confirm that they are not equal.  Therefore the timer has been reset.