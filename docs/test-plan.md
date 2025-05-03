# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Testing Key Functionality

This test checks whether the key system works as intended.
The player should not be able to access certain rooms
without the key, can only collect the key from the
correct room, and should be able to access restricted
rooms ("Factory Settings", "Exit") and pathways
("Travel Destination" from "Travel Point") after collecting the key.

### Test Data To Use

Navigate from "Personal Quarters" → "Hallway 2-AF" → "Overview Team Storage Room".
Reason: This is the expected route to collect the key,
and verifies that entering the key room correctly sets hasKey = true.

After collecting the key, move to rooms that require it:
"Travel Destination" (via "Travel Point")
"Factory Settings" (via "Production Line")
"Exit" (via "Central Atrium")
Reason: Tests that access is correctly granted once the key is obtained.

Try accessing "Travel Destination" from "Travel Point" without the key.
Reason: Access should be denied. Confirms that restricted movement is correctly blocked.
Attempt to access any other restricted room without obtaining the key.
Reason: Confirms that all rooms in the needsKeyRoom list are properly guarded.

### Expected Test Result

Before entering the "Overview Team Storage Room", the player’s key status
should read: “You do not have the key”. Upon entering the room,
the hasKey flag becomes true, and the label updates to:
“You have the key” after leaving the room. Attempts to enter
restricted rooms/pathways (e.g., “Travel Destination” from “Travel Point”)
should fail if the key is not held, and succeed once the key is collected.

---

## Testing Win Conditions and Win Screen

This test verifies that when the player reaches a winning room
(such as "Exit" or "HUD"), the game correctly triggers the win condition
and shows the "You win!" screen after a 3-second delay. It also
checks that the game exits after the win message is shown.

### Test Data To Use

Starting Room: "Central Atrium"
Reason: "Central Atrium" is one of the rooms that leads directly to a win condition (i.e., "Exit").
This allows testing of the win screen from a common room.

Valid Movement: Moving from "Central Atrium" to "Exit"
Reason: "Exit" is one of the rooms that should trigger the win condition,
allowing us to test if the win message is shown correctly and whether the game closes after the message.

Valid Movement to another win room (e.g., moving to "HUD" or "Factory Settings")
Reason: To ensure that all final rooms, not just "Exit", trigger the win condition correctly.
This data checks the broader win condition mechanism for other rooms.

Invalid Movement: Moving to "Shipping Area" (Non-final room)
Reason: To verify that moving to a room that is not a win condition does not trigger any
win-related behavior (i.e., the game should not display the win message prematurely).

Test delay of "You Win!" modal window pop-up
Reason: Ensure that the 3-second delay works as expected before the win message is shown.
The player should experience a delay of about 3 seconds before the message shows up.

### Expected Test Result

Moving to "Exit" triggers the win condition, and after a 3-second delay,
the message "You win!" is shown, followed by the program closing.
Moving to "HUD" or other final rooms triggers the same win condition behavior.
Moving to non-final rooms does not trigger any win condition.
The program closes after the win message is closed.

---


