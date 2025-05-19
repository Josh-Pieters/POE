import javax.swing.*;

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String firstName;
    private String lastName;

    // Parameter order
    public void registerUser(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.storedUsername = username;
        this.storedPassword = password;

    }

    public boolean checkUserName()
    {
        return storedUsername.contains("_") && storedUsername.length() <= 5;
    }

    public boolean checkPasswordComplexity()
    {
        return  storedPassword.length() >= 8 &&
                storedPassword.matches(".*[A-Z].*") &&
                storedPassword.matches(".*[0-9].*") &&
                storedPassword.matches(".*[!@#$%^&*()-+=].*");
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return storedUsername.equals(enteredUsername) && storedPassword.equals(enteredPassword);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword))
        {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else
        {
            return "Username or password incorrect, please try again.";
        }
    }
}
