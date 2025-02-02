package test2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import schoolmanagement.java.controllers.SignUpController;
import schoolmanagement.java.models.Users.Users;
import schoolmanagement.java.utils.Alerts;
import schoolmanagement.java.dao.UsersDao;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class SignUpControllerTest {
    private SignUpController controller;
    private UsersDao mockUsersDao;
    private Alerts mockAlerts;

    @BeforeEach
    void setUp() {
        controller = new SignUpController();

        // Mocking dependencies
        mockUsersDao = mock(UsersDao.class);
        mockAlerts = mock(Alerts.class);
        controller.usersDao = mockUsersDao;

        // Mock Alerts singleton
        try {
            Field instanceField = Alerts.class.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);
            instanceField.set(null, mockAlerts);
        } catch (Exception _) {

        }
    }

    @Test
    void handleSignUpBtn_ShouldShowError_WhenFieldsAreEmpty() {
        // Simulate empty fields
        controller.usernameField = new JFXTextField();
        controller.passwordTextField = new JFXPasswordField();
        controller.firstNameField = new JFXTextField();
        controller.lastNameField = new JFXTextField();
        controller.emailField = new JFXTextField();
        controller.mobileNoField = new JFXTextField();
        controller.signUpBtn = new JFXButton();
        controller.detailsPane = new VBox();

        // Simulate empty fields
        controller.usernameField.setText("");
        controller.passwordTextField.setText("");
        controller.firstNameField.setText("");
        controller.lastNameField.setText("");
        controller.emailField.setText("");
        controller.mobileNoField.setText("");

        // Call the method under test
        controller.handleSignUpBtn(new ActionEvent());

        // Verify that the alert was triggered for missing fields
        verify(mockAlerts, times(1)).jfxBluredAlert(eq(controller.signUpBtn), eq(controller.detailsPane),
                eq("Error"), eq("All fields must be filled"));
    }

    @Test
    void handleSignUpBtn_ShouldShowError_WhenUserAlreadyExists() {
        // Simulate valid input
        controller.usernameField.setText("existingUser");
        controller.passwordTextField.setText("validPassword");
        controller.firstNameField.setText("John");
        controller.lastNameField.setText("Doe");
        controller.emailField.setText("john.doe@example.com");
        controller.mobileNoField.setText("1234567890");

        // Mock the userExist method to return true (user already exists)
        when(mockUsersDao.userExist("existingUser")).thenReturn(true);

        // Call the method under test
        controller.handleSignUpBtn(new ActionEvent());

        // Verify that the alert was triggered for existing user
        verify(mockAlerts, times(1)).jfxBluredAlert(eq(controller.signUpBtn), eq(controller.detailsPane),
                eq("error"), eq("User Already exist"));
    }

    @Test
    void handleSignUpBtn_ShouldSaveUser_WhenValid() {
        // Simulate valid input
        controller.usernameField.setText("newUser");
        controller.passwordTextField.setText("validPassword");
        controller.firstNameField.setText("John");
        controller.lastNameField.setText("Doe");
        controller.emailField.setText("john.doe@example.com");
        controller.mobileNoField.setText("1234567890");

        // Mock userExist to return false (user does not exist)
        when(mockUsersDao.userExist("newUser")).thenReturn(false);
        when(mockUsersDao.saveUser((Users) any())).thenReturn(true);

        // Call the method under test
        controller.handleSignUpBtn(new ActionEvent());

        // Verify that the user is saved (saveUser is called)
        verify(mockUsersDao, times(1)).saveUser((Users) any());
    }
}
