package test2;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTreeTableView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.controllers.ViewStudentsController;

import javafx.collections.FXCollections;
import schoolmanagement.java.utils.RecursiveStudent;

import static org.mockito.Mockito.*;

class ViewStudentsControllerTest {

    private ViewStudentsController controller;
    private StudentsDao mockDao;

    @BeforeEach
    void setUp() {
        controller = new ViewStudentsController();
        mockDao = mock(StudentsDao.class); // Create a mock of StudentsDao
        controller.studentsDao = mockDao; // Inject the mock into the controller
    }

    @Test
    void testRemoveItem_SuccessfulDeletion() {
        // Arrange
        controller.id = 1; // Simulate a selected student with ID=1
        doNothing().when(mockDao).deleteStudent("1"); // Mock void method behavior

        // Add a student to the list
        controller.list = FXCollections.observableArrayList();
        controller.list.add(new RecursiveStudent("1", "John Doe", "Mathematics"));

        // Simulate a student selection
        controller.treeTableView = mock(JFXTreeTableView.class);
        when(controller.treeTableView.getSelectionModel().getSelectedIndex()).thenReturn(0);

        // Act
        controller.removeItem();

        // Assert
        verify(mockDao, times(1)).deleteStudent("1"); // Ensure deleteStudent was called once
        assert controller.list.isEmpty(); // Verify the student was removed from the list
    }

    /**
     *
     */
    @Test
    void testOnDelete() {
        // Arrange
        controller.id = 2; // Simulate a selected student with ID=2
        doNothing().when(mockDao).deleteStudent("2"); // Mock void method behavior

        // Act
        controller.onDelete(); // Simulate triggering the delete dialog

        // Confirm deletion by calling removeItem
        controller.removeItem();

        // Assert
        verify(mockDao, times(1)).deleteStudent("2"); // Ensure deleteStudent was called once
    }

    @Test
    void testOnDeleteDialog_CancelDeletion() {
        // Arrange
        controller.id = 3; // Simulate a selected student with ID=3
        doNothing().when(mockDao).deleteStudent("3"); // Mock void method behavior

        // Mocking dialog actions
        JFXDialog mockDialog = mock(JFXDialog.class);
        mockDialog.close();

        // Act
        // Simulate cancel action (no call to removeItem should occur)
        controller.onDelete();

        // Assert
        verify(mockDao, times(0)).deleteStudent("3"); // Ensure deleteStudent was NOT called
    }
}
