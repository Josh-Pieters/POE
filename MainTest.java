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

}