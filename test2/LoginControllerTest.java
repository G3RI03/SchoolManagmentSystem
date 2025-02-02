package test2;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import schoolmanagement.java.controllers.LoginController;
import schoolmanagement.java.utils.Alerts;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class LoginControllerTest {
    private LoginController controller;
    private Alerts mockAlerts;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize the controller
        controller = new LoginController();

        // Create a mock instance of Alerts
        mockAlerts = mock(Alerts.class);

        // Use reflection to set Alerts.INSTANCE to the mock
        Field instanceField = Alerts.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true); // Make the field accessible
        instanceField.set(null, mockAlerts); // Replace the singleton instance
    }

    @Test
    void handleLoginBtn_ShouldShowErrorAlert_WhenUserNameIsEmpty() {
        // Mocking UI components
        controller.userNameTextField = new JFXTextField();
        controller.detailsPane = new VBox();
        controller.loginBtn = new JFXButton();

        // Simulate empty username input
        controller.userNameTextField.setText("");

        // Call the method under test
        controller.handleLoginBtn(new ActionEvent());

        // Verify that the alert was shown with the correct parameters
        verify(mockAlerts, times(1)).jfxBluredAlert(
                eq(controller.loginBtn),
                eq(controller.detailsPane),
                eq("Error"),
                eq("User name field cannot be empty")
        );
    }

    @Test
    void handleLoginBtn_ShouldNotShowAlert_WhenUserNameIsNotEmpty() {
        // Mocking UI components
        controller.userNameTextField = new JFXTextField();
        controller.detailsPane = new VBox();
        controller.loginBtn = new JFXButton();

        // Simulate valid username input
        controller.userNameTextField.setText("JohnDoe");

        // Call the method under test
        controller.handleLoginBtn(new ActionEvent());

        // Verify that the alert was not shown
        verify(mockAlerts, never()).jfxBluredAlert(any(), any(), any(), any());
    }
}
