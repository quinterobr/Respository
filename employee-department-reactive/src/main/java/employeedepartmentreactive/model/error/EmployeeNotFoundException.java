package employeedepartmentreactive.model.error;

import lombok.Getter;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {
    @Getter
    private final int employeeId;
    private static final String MESSAGE = "employee not found";

    public EmployeeNotFoundException(int id) {
        super(MESSAGE);
        this.employeeId = id;
    }
}
