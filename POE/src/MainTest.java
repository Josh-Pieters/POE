import static org.junit.jupiter.api.Assertions.*;

class MainTest {



    @org.junit.jupiter.api.Test
    void validateCellphone_Correctly()
    {
        String testData = "+27838968976";
        String expectedMessage = "Cell number successfully captured.";

        assertEquals(expectedMessage, validateCellPhone(testData));;
    }

    @org.junit.jupiter.api.Test
    void validateCellphone_Incorrectly()
    {
        String testData = "0838968976";
        String expectedMessage = "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";

        assertEquals(expectedMessage, validateCellPhone(testData));
    }

    private String validateCellPhone(String phone) {
        // Implement cell phone validation logic
        if (phone.startsWith("+") && phone.length() > 10) {
            return "Cell number successfully captured.";
        } else {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }
    }

    String[] messages = {
            "Did you get the cake?",
            "It is dinner time!",
            "Where are you? You are late! I have asked you to be on time.",
            "Ok, I am leaving without you."
    };

    String[] ids = {
            "0001", "0002", "0838884567", "0004"
    };

    String[] recipients = {
            "+27838884567", "+27838884567", "+27838884567", "+27111111111"
    };

    String[] hashes = {
            "HASH1", "HASH2", "HASH3", "HASH4"
    };

    // --- Array-based tests ---

    @org.junit.jupiter.api.Test
    void testMessagesArrayContainsExpectedData() {
        boolean found1 = false, found2 = false;

        for (String msg : messages) {
            if (msg.equals("Did you get the cake?")) found1 = true;
            if (msg.equals("It is dinner time!")) found2 = true;
        }

        assertTrue(found1);
        assertTrue(found2);
    }

    @org.junit.jupiter.api.Test
    void testLongestMessage() {
        String longest = "";
        for (String msg : messages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @org.junit.jupiter.api.Test
    void testSearchMessageByID() {
        String result = "";
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equals("0838884567")) {
                result = messages[i];
                break;
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", result);
    }

    @org.junit.jupiter.api.Test
    void testSearchByRecipient() {
        String[] expected = {
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."
        };

        String[] found = new String[2];
        int index = 0;

        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i].equals("+27838884567")) {
                found[index++] = messages[i];
            }
        }

        assertEquals(expected[0], found[0]);
        assertEquals(expected[1], found[1]);
    }

    @org.junit.jupiter.api.Test
    void testDeleteMessageByHash() {
        String hashToDelete = "HASH2";
        String deletedMessage = "";

        for (int i = 0; i < hashes.length; i++) {
            if (hashes[i].equals(hashToDelete)) {
                deletedMessage = messages[i];
                break;
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", deletedMessage);
    }

    @org.junit.jupiter.api.Test
    void testDisplayReport() {
        for (int i = 0; i < messages.length; i++) {
            assertNotNull(hashes[i]);
            assertNotNull(recipients[i]);
            assertNotNull(messages[i]);
        }
    }

}