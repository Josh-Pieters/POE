import org.json.simple.JSONObject;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
public class Message
{
    private static int messageCount = 0;
    private static ArrayList<Message> messages = new ArrayList<Message>();
    private String messageID;
    private String recipient;
    private String messageContent;
    private String messageHash;

    public Message(String recipient, String messageContent)
    {
        checkMessage(messageContent);
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageHash();
        messageCount++;
        messages.add(this);
    }

    public static ArrayList<Message> getAllMessages() {
        return messages;
    }


    public String getMessageID()
    {
        return messageID;
    }

    public int getNumMessagesSent()
    {
        return messageCount;
    }

    public String getRecipient()
    {
        return recipient;
    }

    public String getMessageText()
    {
        return messageContent;
    }

    public String getMessageHash()
    {
        return messageHash;
    }

    public boolean checkMessage(String message)
    {
        return (message.length() > 250);
    }

    public String generateMessageID()
    {
        String id = "";
        for (int i = 0; i < 10; i++)
        {
            id = id + (int)(Math.random() * 10);
        }
        return id;
    }

    public boolean checkMessageID()
    {
        return messageID.length() <= 10;
    }


    public String sendMessage()
    {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = 0;
        while (true)
        {
            String input = JOptionPane.showInputDialog("Choose an option: \n1. Send Message\n2. Disregard Message\n3. Store Message to send later");
            if (input == null) return options[1];
            if (input.equals("1")) return options[0];
            if (input.equals("2")) return options[1];
            if (input.equals("3")) return options[2];
        }

    }

    public void displayMessage()
    {
        String details = "Message Details:\n" +
            "Message ID: " + (getMessageID()) + "\n" +
            "Message Hash: " + (getMessageHash()) + "\n" +
            "Recipient: " + (getRecipient()) + "\n" +
            "Message: " + (getMessageText()) + "\n" +
            "Message Count: " + (returnTotalMessages());

        JOptionPane.showMessageDialog(null, details);
    }

    public void  messageHash()
    {
    if (messageID != null && messageContent != null && !messageContent.isEmpty())
    {
        String firstTwoID = messageID.substring(0, 2);
        String firstLetter = messageContent.substring(0, 1).toUpperCase();
        String lastLetter = messageContent.substring(messageContent.length() - 1).toUpperCase();
        this.messageHash = (firstTwoID +":"+ messageCount+ firstLetter + lastLetter);
    } else
    {
    this.messageHash = "INVALID";
}
}

public void storeMessage()
    {
        JSONObject msg = new JSONObject();
        msg.put("MessageID", messageID);
        msg.put("Recipient", recipient);
        msg.put("Message", messageContent);

        if (messageHash == null)
        {
            messageHash();  // Ensure the hash is created before storing
        }
        msg.put("Hash", messageHash);

        try (FileWriter writer = new FileWriter("messages.json", true))
        {
            writer.write(msg.toJSONString() + System.lineSeparator());
        } catch (IOException e)
        {
            System.out.println("Failed to store message: " + e.getMessage());
        }
    }
public static int returnTotalMessages()
    {
        return messageCount;
    }

}
