package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import schoolmanagement.java.dao.DepartmentsDao;
import schoolmanagement.java.models.Departments;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DepartmentsDaoTest {

    private DepartmentsDao departmentsDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class); // Mock the JdbcTemplate
        departmentsDao = new DepartmentsDao();
        departmentsDao.setJdbcTemplate(jdbcTemplate); // Inject the mocked JdbcTemplate
    }

    @Test
    void testGetDepartments() throws SQLException {
        // Mock the ResultSet behavior
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true, true, false); // Simulate 2 rows
        when(mockResultSet.getString(1)).thenReturn("1", "2");
        when(mockResultSet.getString(2)).thenReturn("Mathematics", "Physics");

        // Mock the query method of JdbcTemplate
        when(jdbcTemplate.query(anyString(), (ResultSetExtractor<Object>) any())).thenAnswer(invocation -> {
            var resultSetExtractor = invocation.getArgument(1);
            return resultSetExtractor.getClass();
        });

        // Call the method
        List<Departments> departments = departmentsDao.getDepartments();

        // Assertions
        assertNotNull(departments);
        assertEquals(2, departments.size());
        assertEquals("Mathematics", departments.get(0).getDepartment());
        assertEquals("Physics", departments.get(1).getDepartment());

        // Verify behavior
        verify(jdbcTemplate, times(1)).query((String) eq("select * from departments"), (ResultSetExtractor<Object>) any());
    }

    @Test
    void testDeleteDepartment() {
        // Mock the update method
        when(jdbcTemplate.update(anyString())).thenReturn(1); // Simulate 1 row deleted

        // Call the method
        int rowsDeleted = departmentsDao.deleteDepartment("Mathematics");

        // Assertions
        assertEquals(1, rowsDeleted);

        // Verify behavior
        verify(jdbcTemplate, times(1)).update(eq("delete from departments where department = 'Mathematics' "));
    }

    @Test
    void testSaveDepartment() {
        // Mock the execute method
        when(jdbcTemplate.execute(anyString(),
                (PreparedStatementCallback<Object>) any())).thenAnswer(invocation -> {
            var preparedStatementCallback = invocation.getArgument(1);
            return preparedStatementCallback.toString();
        });

        // Call the method
        boolean isSaved = Boolean.TRUE.equals(departmentsDao.saveDepartment("Chemistry"));

        // Assertions
        assertTrue(isSaved);

        // Verify behavior
        verify(jdbcTemplate, times(1)).execute((String) eq("insert into departments values(?,?)"), (PreparedStatementCallback<Object>) any());
    }
}
