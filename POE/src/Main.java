import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {
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

        while (option != 3) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Send Message");
            System.out.println("2. Check recently sent messages");
            System.out.println("3. Exit");
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
                } while (!(message.length() >= 50 && message.length() <= 250));

                Message sendMessage = new Message(recipientCellphoneNumber, message);

                String decision = sendMessage.sendMessage();

                if (decision.equals("Send Message")) {
                    sendMessage.displayMessage();
                } else if (decision.equals("Store Message to send later")) {
                    //Store the message
                    sendMessage.messageHash();
                    sendMessage.storeMessage(); // save to JSON
                    System.out.println("Message stored to send later.");
                } else {
                    System.out.println("Message discarded.");
                }

                messageAmount++;
            } else if (option == 2) {
                System.out.println("------------------------------");
                System.out.println("\t\tComing Soon");
                System.out.println("------------------------------");
            } else if (option == 3) {
                System.out.println("Thank you for using QuickChat!");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }


    }

    public static boolean validateCellphone(String number)
    {
        String regex = "^\\+\\d{1,3}\\d{1,10}$"; // Country code (+1 to +999) followed by up to 10 digits
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}

