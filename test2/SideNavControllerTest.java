package test2;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.*;
import schoolmanagement.java.controllers.SideNavController;
import schoolmanagement.java.utils.FxmlHandlers;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SideNavControllerTest {

    static {
        Logger.getLogger(SideNavControllerTest.class.getName());
    }

    private SideNavController sideNavController;
    private JFXButton mockHomeButton, mockAddButton, mockViewButton;

    @BeforeAll
    public void setupClass() {
        // Mock SideNavController and its buttons
        sideNavController = new SideNavController();
        mockHomeButton = new JFXButton("Home");
        mockAddButton = new JFXButton("Add");
        mockViewButton = new JFXButton("View");

        // Inject mock buttons into the controller
        sideNavController.homeBtn = mockHomeButton;
        sideNavController.addBtn = mockAddButton;
        sideNavController.viewBtn = mockViewButton;

        sideNavController.initialize();
    }

    @Test
    public void testLoadPanes() {
        // Verify if the panes are loaded successfully
        assertNotNull(sideNavController.getHomePane(), "Home pane should be loaded.");
        assertNotNull(sideNavController.getAddPane(), "Add pane should be loaded.");
        assertNotNull(sideNavController.getViewPane(), "View pane should be loaded.");
    }

    @Test
    public void testHomeButtonClickLoadsHomePane() {
        // Simulate Home button click
        mockHomeButton.fire();

        // Check if the HomePane was loaded
        StackPane homePane = (StackPane) sideNavController.getHomePane();
        assertNotNull(homePane, "Home pane should not be null.");
        assertTrue(FxmlHandlers.isPaneVisible(homePane), "Home pane should be visible.");
    }

    @Test
    public void testAddButtonClickLoadsAddPane() {
        // Simulate Add button click
        mockAddButton.fire();

        // Check if the AddPane was loaded
        StackPane addPane = (StackPane) sideNavController.getAddPane();
        assertNotNull(addPane, "Add pane should not be null.");
        assertTrue(FxmlHandlers.isPaneVisible(addPane), "Add pane should be visible.");
    }

    @Test
    public void testViewButtonClickLoadsViewPane() {
        // Simulate View button click
        mockViewButton.fire();

        // Check if the ViewPane was loaded
        StackPane viewPane = (StackPane) sideNavController.getViewPane();
        assertNotNull(viewPane, "View pane should not be null.");
        assertTrue(FxmlHandlers.isPaneVisible(viewPane), "View pane should be visible.");
    }
}
