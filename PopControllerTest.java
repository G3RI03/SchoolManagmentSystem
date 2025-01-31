package test2;

import org.junit.jupiter.api.Test;
import schoolmanagement.java.controllers.PopUpController;

import static org.junit.jupiter.api.Assertions.*;

class PopUpControllerTest {

    private final PopUpController popUpController = new PopUpController();

    @Test
    void testValidLoginAttempts() {
        // Valid boundary values
        assertTrue(popUpController.isValidLoginAttempts(1), "1 is a valid login attempt.");
        assertTrue(popUpController.isValidLoginAttempts(5), "5 is a valid login attempt.");
    }

    @Test
    void testInvalidLoginAttemptsBelowRange() {
        // Boundary value just below the lower limit
        assertFalse(popUpController.isValidLoginAttempts(0), "0 is not a valid login attempt.");
    }

    @Test
    void testInvalidLoginAttemptsAboveRange() {
        // Boundary value just above the upper limit
        assertFalse(popUpController.isValidLoginAttempts(6), "6 is not a valid login attempt.");
    }

    @Test
    void testMiddleValueLoginAttempts() {
        // A value in the middle of the range
        assertTrue(popUpController.isValidLoginAttempts(3), "3 is a valid login attempt.");
    }
}
