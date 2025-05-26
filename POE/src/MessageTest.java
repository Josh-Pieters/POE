import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void checkMessage()
    {
        String message = "Sometimes, when the wind whispers through the trees just before dusk, it feels as if the universe is leaning in to tell a secret. The colors shift, the air stills, and there's a moment—brief but infinite—where everything aligns, and you're reminded that even chaos has its rhythm.";
        Message msg = new Message("+12345678901", message);
        assertTrue(msg.checkMessage(message));
    }

    @Test
    void generateMessageID()
    {
        Message msg = new Message("+12345678901", "Valid message content for testing.");
        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    void checkMessageID()
    {
        Message msg = new Message("+12345678901", "Another message content.");
        assertTrue(msg.checkMessageID());
    }

    @Test
    void sendMessage()
    {
        Message msg = new Message("+12345678901", "Testing send message interaction.");
        String response = msg.sendMessage();  // Waits for user input
        assertTrue(response.equals("Send Message") ||
                response.equals("Disregard Message") ||
                response.equals("Store Message to send later"));
    }

    @Test
    void messageHash()
    {
        Message msg = new Message("+12345678901", "Hitonight");
        String hash = msg.getMessageHash();
        assertNotNull(hash);
        assertTrue(hash.contains(":"));
    }
    @Test
    void storeMessage()
    {
        Message msg = new Message("+12345678901", "Testing store functionality.");
        msg.storeMessage();
    }
}