package src.ca.ucalgary.seng300.Profile;


import org.junit.Test;
import org.junit.Assert;
import src.ca.ucalgary.seng300.Profile.utils.ValidationUtils;

//Aiming for close to 100% coverage.
public class ValidationUtilsTest {
//NEED TO CHECK EMAIL REGEX TEST NOT WORKING WELL.

    @Test
    public void testValidEmail() {
        //Testing for email with correct format.
        Assert.assertTrue(ValidationUtils.isValidEmail("test@example.com")); //Basic email
        Assert.assertTrue(ValidationUtils.isValidEmail("user.name123@example.com")); //Email with dot and numbers.

    }
    @Test
    public void testInValidEmail() {
        //Testing for email with invalid format.
        Assert.assertFalse(ValidationUtils.isValidEmail("user@.com")); //domain should not start with .
        Assert.assertFalse(ValidationUtils.isValidEmail("user@domain")); //needs to be a .something eg; .com
        Assert.assertFalse(ValidationUtils.isValidEmail("invalid-email")); //Missing @

    }
    @Test
    public void testEmptyOrNullEmail() {
        //Testing for null/empty
        Assert.assertFalse(ValidationUtils.isValidEmail(null)); //Null should be false
        Assert.assertFalse(ValidationUtils.isValidEmail("")); // ANy empty string should return false.
    }
    @Test
    public void testValidPassword() {
        //Testing for passwords that are valid.
        Assert.assertTrue(ValidationUtils.isValidPassword("FirstPassword01!")); //Should be true because it contains letter, numbers, special character, and minimum length of 8 characters
        Assert.assertTrue(ValidationUtils.isValidPassword("A1b2C3@d")); // //Should be true because it contains letter, numbers, special character, and minimum length of 8 characters
    }
    @Test
    public void testInValidPassword() {
        //Testing for invalid passwords.
        Assert.assertFalse(ValidationUtils.isValidPassword("shor1@")); // Less than 8 characters, but it contains letters, special characters and numbers.
        Assert.assertFalse(ValidationUtils.isValidPassword("nospecial1")); //No special characters but it contains numbers, letters and at least 8 characters
        Assert.assertFalse(ValidationUtils.isValidPassword("NoDigitNumber@")); //No numbers, but it contains a letter, special character  and minimum length of 8.
        Assert.assertFalse(ValidationUtils.isValidPassword("81%12343234543")); //No letters but it contains numbers, special character and a minimum length of 8.

    }
    @Test
    public void testEmptyOrNullPasswordl() {
        //Testing for null and empty strings.
        Assert.assertFalse(ValidationUtils.isValidPassword(null)); // Null  should return false
        Assert.assertFalse(ValidationUtils.isValidPassword("")); // Empty string input should return false
    }


    @Test
    public void testValidUsername() {
        //Testing basic valid username. (Just letters and numbers)
        Assert.assertTrue(ValidationUtils.isValidUsername("Username123")); // Has letters and numbers
        Assert.assertTrue(ValidationUtils.isValidUsername("U")); // Just letters
        Assert.assertTrue(ValidationUtils.isValidUsername("1")); //Just numbers
    }
    @Test
    public void testInValidUsername() {
        //Testing invalid usernames. (Usernames that contain anything that's not letters and numbers)
        Assert.assertFalse(ValidationUtils.isValidUsername("Invalid Username")); // Has space, not allowed
        Assert.assertFalse(ValidationUtils.isValidUsername("Invalid@Username")); // Has special character @
        Assert.assertFalse(ValidationUtils.isValidUsername("Invalid-Usenamer")); // Has -
    }
    @Test
    public void testEmptyOrNullUsername() {
        //Testing for null and empty strings.
        Assert.assertFalse(ValidationUtils.isValidUsername(null)); // Null  should return false
        Assert.assertFalse(ValidationUtils.isValidUsername("")); // Empty string should return false
    }




}