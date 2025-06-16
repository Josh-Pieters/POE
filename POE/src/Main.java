import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
    private static String[] sentMessages = new String[100]; // Store sent messages
    private static String[] disregardedMessages = new String[100]; // Store disregarded messages
    private static String[] storedMessages = new String[100]; // Store messages for later
    private static String[] messageHashes = new String[100]; // Store message hashes
    private static String[] messageIDs = new String[100];
    private static int sentMessageCount = 0;
    private static int storedMessageCount = 0;
    private static int disregardedMessageCount = 0;

    static class MessageData {
        String Message;
        String Recipient;
        String Hash;
        String MessageID;
    }

    public static void main(String[] args)
    {
        readStoredMessagesFromFile();

        Scanner sc = new Scanner(System.in);
        String userName;
        String password;
        String firstName;
        String lastName;
        String cellphoneNumber;
        String recipientCellphoneNumber = "";
        String message = "";
        int messageAmount=0;
        Login userLogin = new Login();
        int sentMessageCount = 0;
        int storedMessageCount = 0;
        int disregardedMessageCount = 0;




        System.out.println("\n----------Registration----------");

        //First name Input and Validation
        do {
            System.out.println("Enter First Name: ");
            firstName = sc.nextLine();
                if (firstName.isBlank())
                {
                    JOptionPane.showMessageDialog(null, "First Name cannot be blank, please enter a valid first name.");
                }
            } while (firstName.isBlank());

        //Last name Input and Validation
        do {
            System.out.println("Enter Last Name: ");
            lastName = sc.nextLine();
            if (lastName.isBlank())
            {
                JOptionPane.showMessageDialog(null, "Last Name cannot be blank, please enter a valid last name.");
            }
        } while (lastName.isBlank());

        //Cellphone Input and Validation
        JOptionPane.showMessageDialog(null, "The number must contain the international code(+123456789342)!");
        System.out.println("Enter your cellphone number:");
        cellphoneNumber = sc.nextLine();

        if (validateCellphone(cellphoneNumber)) {
            JOptionPane.showMessageDialog(null, "Cellphone number successfully added.");
        } else {
            JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code.");
        }

        //Username Input & Validation
        do {
            JOptionPane.showMessageDialog(null, "Username needs to contain a underscore and under 5 characters!");
            System.out.println("Enter Username: ");
            userName = sc.nextLine();

            userLogin.registerUser(firstName, lastName, userName, "");

            if (!userLogin.checkUserName()) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted. Ensure it contains an underscore and is no more than 5 characters.");
            }
        } while (!userLogin.checkUserName());

        JOptionPane.showMessageDialog(null, "Username successfully added");

        // Password Input & Validation
        do {
            JOptionPane.showMessageDialog(null, "Password needs to contain a capital letter, number, special character and at least 8 characters!");
            System.out.println("Enter Password: ");
            password = sc.nextLine();

            userLogin.registerUser(firstName, lastName, userName, password); // Temporary registration for checking password

            if (!userLogin.checkPasswordComplexity()) {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted. Ensure it has at least 8 characters, a capital letter, a number, and a special character.");
            }
        } while (!userLogin.checkPasswordComplexity());

        JOptionPane.showMessageDialog(null, "Password successfully captured");

        //Register the user
        userLogin.registerUser(firstName, lastName, userName, password);

        // Login protocol
        String loginUsername, loginPassword;
        boolean isLoggedIn = false;

        while (!isLoggedIn)
        {
            System.out.println("\n----------Login----------");
            System.out.println("Please enter username: ");
            loginUsername = sc.nextLine();
            System.out.println("Please enter password: ");
            loginPassword = sc.nextLine();

            isLoggedIn = userLogin.loginUser(loginUsername, loginPassword);
            JOptionPane.showMessageDialog(null, userLogin.returnLoginStatus(loginUsername, loginPassword));


        }

        int option = 0;

        while (option != 9) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Send Message");
            System.out.println("2. Check recently sent messages");
            System.out.println("3. Exit");
            System.out.println("4. Display sender and recipient of all sent messages");
            System.out.println("5. Display the longest sent message");
            System.out.println("6. Delete a message using message hash");
            System.out.println("7. Search for a message by ID");
            System.out.println("8. Search for all messages sent to a particular recipient");
            System.out.println("9. Display full report of all sent messages");
            System.out.print("Enter option: ");

            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            if (option == 1) {
                // Collect recipient number
                do {
                    System.out.println("Please enter the number you want to send a message to (e.g., +1234567890):");
                    recipientCellphoneNumber = sc.nextLine();
                } while (!validateCellphone(recipientCellphoneNumber));

                // Collect message content
                do
                {
                    System.out.println("Please enter the message you want to send (between 50 and 250 characters):");
                    message = sc.nextLine();
                } while (!( message.length() <= 250));

                Message sendMessage = new Message(recipientCellphoneNumber, message);

                String decision = sendMessage.sendMessage();

                if (decision.equals("Send Message"))
                {
                    sendMessage.displayMessage();
                    sentMessages[sentMessageCount] = message;
                    messageHashes[sentMessageCount] = sendMessage.getMessageHash();
                    messageIDs[sentMessageCount] = sendMessage.getMessageID();
                    sentMessageCount++;
                } else if (decision.equals("Store Message to send later"))
                {
                    if (storedMessageCount < storedMessages.length) {
                        storedMessages[storedMessageCount++] = message;
                        System.out.println("Message stored to send later.");
                    }
                    else
                    {
                        System.out.println("Storage full. Cannot store more messages.");
                    }
                } else
                {
                    disregardedMessages[disregardedMessageCount++] = message;
                    System.out.println("Message discarded.");
                }

                messageAmount++;
            } else if (option == 2)
            {
                System.out.println("------------------------------");
                System.out.println("\t\tComing Soon");
                System.out.println("------------------------------");
            } else if (option == 3)
            {
                System.out.println("Thank you for using QuickChat!");
            } else if (option == 4)
            {
                displaySendersAndRecipients(cellphoneNumber);
            }

            else if (option == 5)
            {
                displayLongestMessage();
            }

            else if (option == 6)
            {
                System.out.print("Enter the message hash to delete: ");
                String hashToDelete = sc.nextLine();
                deleteMessageByHash(hashToDelete);
            }

            else if (option == 7)
            {
                System.out.print("Enter the message ID to search: ");
                String idToSearch = sc.nextLine();
                searchByMessageID(idToSearch);
            }

            else if (option == 8)
            {
                System.out.print("Enter the recipient number to search for: ");
                String recipientToSearch = sc.nextLine();
                searchMessagesByRecipient(recipientToSearch);
            }

            else if (option == 9)
            {
                displayFullMessageReport();
            }

            else
            {
                System.out.println("Invalid option. Please try again.");
            }

        }
    }
    public static void readStoredMessagesFromFile() {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line.trim());
            }

            String json = jsonContent.toString();

            if (json.startsWith("[") && json.endsWith("]")) {
                // Remove the outer brackets
                json = json.substring(1, json.length() - 1).trim();
                // Split JSON objects (rough split by "},{")
                String[] messageObjects = json.split("\\},\\{");

                for (int i = 0; i < messageObjects.length; i++) {
                    String msgObj = messageObjects[i];
                    // Fix brackets after split
                    if (i == 0 && !msgObj.startsWith("{")) {
                        msgObj = "{" + msgObj + "}";
                    } else if (i == messageObjects.length - 1 && !msgObj.endsWith("}")) {
                        msgObj = "{" + msgObj + "}";
                    } else {
                        msgObj = "{" + msgObj + "}";
                    }
                    String message = extractFieldFromJson(msgObj, "Message");
                    if (message != null && storedMessageCount < storedMessages.length) {
                        storedMessages[storedMessageCount++] = message;
                    }
                }
            } else if (json.startsWith("{") && json.endsWith("}")) {
                String message = extractFieldFromJson(json, "Message");
                if (message != null && storedMessageCount < storedMessages.length) {
                    storedMessages[storedMessageCount++] = message;
                }
            }

            System.out.println("Loaded " + storedMessageCount + " stored messages from file.");
        } catch (IOException e) {
            System.out.println("No stored messages loaded. Reason: " + e.getMessage());
        }
    }

    // Helper method to extract value of a JSON field (assumes simple flat JSON, no nested or escaped quotes)
    private static String extractFieldFromJson(String json, String fieldName)
    {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*\"";
        int startIndex = json.indexOf(pattern);
        if (startIndex < 0) return null;
        startIndex += pattern.length();
        int endIndex = json.indexOf("\"", startIndex);
        if (endIndex < 0) return null;
        return json.substring(startIndex, endIndex);
    }

    public static boolean validateCellphone(String number)
    {
        String regex = "^\\+\\d{1,3}\\d{1,10}$"; // Country code (+1 to +999) followed by up to 10 digits
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static void displaySendersAndRecipients(String senderNumber) {
        System.out.println("\n--- Sender and Recipient of All Sent Messages ---");
        for (Message msg : Message.getAllMessages()) {
            System.out.println("Sender: " + senderNumber + " -> Recipient: " + msg.getRecipient());
        }
    }

    public static void displayLongestMessage() {
        ArrayList<Message> messages = Message.getAllMessages();
        if (messages.isEmpty()) {
            System.out.println("No messages sent yet.");
            return;
        }

        Message longest = messages.get(0);
        for (Message msg : messages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }

        System.out.println("\n--- Longest Sent Message ---");
        System.out.println("Recipient: " + longest.getRecipient());
        System.out.println("Message ID: " + longest.getMessageID());
        System.out.println("Length: " + longest.getMessageText().length());
        System.out.println("Message: " + longest.getMessageText());
    }

    public static void deleteMessageByHash(String hash) {
        ArrayList<Message> messages = Message.getAllMessages();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getMessageHash().equals(hash)) {
                messages.remove(i);
                System.out.println("Message with hash " + hash + " deleted successfully.");
                return;
            }
        }
        System.out.println("Message with hash " + hash + " not found.");
    }

    public static void searchByMessageID(String id) {
        for (Message msg : Message.getAllMessages()) {
            if (msg.getMessageID().equals(id)) {
                System.out.println("\n--- Message Found ---");
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessageText());
                return;
            }
        }
        System.out.println("Message with ID " + id + " not found.");
    }

    public static void searchMessagesByRecipient(String recipient) {
        boolean found = false;
        System.out.println("\n--- Messages Sent to " + recipient + " ---");
        for (Message msg : Message.getAllMessages()) {
            if (msg.getRecipient().equals(recipient)) {
                System.out.println("Message ID: " + msg.getMessageID());
                System.out.println("Message: " + msg.getMessageText());
                System.out.println("---");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for recipient: " + recipient);
        }
    }

    public static void displayFullMessageReport() {
        System.out.println("\n--- Full Message Report ---");
        for (Message msg : Message.getAllMessages()) {
            System.out.println("Message ID: " + msg.getMessageID());
            System.out.println("Hash: " + msg.getMessageHash());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("---------------------------");
        }
    }


}

