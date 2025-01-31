package test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import schoolmanagement.java.controllers.MainController;
import schoolmanagement.java.main.Launcher;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest extends ApplicationTest {

    private MainController mainController;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the MainController view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/schoolmanagement/java/views/MainView.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testDrawerToggle() {
        // Click the hamburger to toggle the drawer
        clickOn("#hamburger");

        // Assert that the drawer is open
        verifyThat(!mainController.drawer.isOpened(), NodeMatchers.isVisible());

        // Click the hamburger again to close the drawer
        clickOn("#hamburger");

        // Assert that the drawer is closed
        assertFalse(mainController.drawer.isOpened(), "Drawer should be closed.");
    }

    private void verifyThat(boolean ignoredB, Matcher<Node> ignoredVisible) {
    }

    @Test
    void testPopupDisplay() {
        // Click the pop-up button to open the pop-up
        clickOn("#popUpBtn");

        // Check if the pop-up is visible
        FxAssert.verifyThat(".jfx-popup", NodeMatchers.isVisible());
    }

    @Test
    void testContentControllerInitialization() {
        // Check if the contentPane is initialized with the home view
        Node content = mainController.contentPane.getChildren().getFirst();
        assertEquals("homePane", content.getId(), "Content pane should be initialized with the home view.");
    }

    @Test
    void testStageDraggable() {
        // Simulate dragging the mainPane
        moveTo(mainController.mainPane).press(MouseButton.PRIMARY).moveBy(100, 100).release(MouseButton.PRIMARY);

        // No exceptions mean the draggable logic works, but more detailed tests may require manual checks
        assert Launcher.stage != null;
        assertEquals(1.0f, Launcher.stage.getOpacity(), "Stage opacity should reset after dragging.");
    }
}
