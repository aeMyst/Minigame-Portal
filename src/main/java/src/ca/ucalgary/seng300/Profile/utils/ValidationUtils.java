package src.ca.ucalgary.seng300.Profile.utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class ValidationUtils {


    // Minimum eight characters, at least one letter, one number and one special character. Perhaps tell the user this is the requirements.
    private static final String password_Regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    // Found the regex in:  https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
    //double check to see if \\ worked
    
    
    //Basic email validation regex pattern.
    private static final String email_Regex = "^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
    //Found the regex in: https://support.boldsign.com/kb/article/15962/how-to-create-regular-expressions-regex-for-email-address-validation

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) { // Logic in case user doesnt enter anything or  empty strings
            return false;
        }
        return Pattern.matches(email_Regex, email);

    }
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) { // Logic in case user doesnt enter anything or  empty strings
            return false;
        }
        return Pattern.matches(password_Regex, password);
    }
}
