package test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolmanagement.java.controllers.AddStudentController;
import schoolmanagement.java.dao.DepartmentsDao;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.models.Departments;
import schoolmanagement.java.models.Students;
import schoolmanagement.java.utils.Alerts;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static schoolmanagement.java.utils.Alerts.*;

public class AddStudentControllerTest extends javafx.application.Application {

    private AddStudentController controller;
    private StudentsDao mockStudentsDao;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/AddStudent.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setup() {
        mockStudentsDao = mock(StudentsDao.class);
        DepartmentsDao mockDepartmentsDao = mock(DepartmentsDao.class);

        // Mock dependencies in the controller
        controller.studentsDao = mockStudentsDao;
        controller.departmentsDao = mockDepartmentsDao;

        // Mock department data
        when(mockDepartmentsDao.getDepartments()).thenReturn(Arrays.asList(
                new Departments("Mathematics"),
                new Departments("Physics")
        ));

        // Set up Alerts utility for testing
        mock(Alerts.class);
    }


    @Test
    public void testAddStudentValidData() {
        // Simulate user input
        controller.firstNameField.setText("John");
        controller.lastNameField.setText("Doe");
        controller.emailAddressField.setText("john.doe@example.com");
        controller.mobileNoField.setText("1234567890");
        controller.locationField.setText("New York");
        controller.amountField.setText("50000");
        controller.maleRadio.setSelected(true);
        controller.degreeRadio.setSelected(true);
        controller.departmentsComboBox.getSelectionModel().select(new Departments("Mathematics"));

        // Simulate save action
        controller.onSave();

        // Verify the student data was saved via the DAO
        verify(mockStudentsDao, times(1)).saveStudent(any(Students.class));

        // Check if success alert was triggered
        verify(INSTANCE, times(1)).jfxBluredAlert(any(), any(), eq("Success"), eq("Student data successfully saved"));
    }

    @Test
    public void testAddStudentInvalidEmail() {
        // Simulate user input with an invalid email
        controller.firstNameField.setText("John");
        controller.lastNameField.setText("Doe");
        controller.emailAddressField.setText("invalid-email");
        controller.mobileNoField.setText("1234567890");
        controller.locationField.setText("New York");
        controller.amountField.setText("50000");
        controller.maleRadio.setSelected(true);
        controller.degreeRadio.setSelected(true);
        controller.departmentsComboBox.getSelectionModel().select(new Departments("Mathematics"));

        // Simulate save action
        controller.onSave();

        // Verify no student data was saved due to validation failure
        verify(mockStudentsDao, never()).saveStudent(any(Students.class));

        // Check if error alert was triggered for invalid email
        verify(INSTANCE, times(1)).jfxAlert(any(), eq("Error"), eq("Invalid Email Format"));
    }

    @Test
    public void testCalculateAmount() {
        // Simulate user input for the amount
        controller.amountField.setText("100000");
        controller.calculateAmount(null);

        // Verify the calculated balance is correct
        assertEquals("N 100000.00", controller.balanceField.getText());
    }
}