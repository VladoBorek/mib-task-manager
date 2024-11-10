package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.service.validation.common.PropertyValidator;


public class EmployeeValidator extends PropertyValidator<Employee> {
    public EmployeeValidator() {
        super("Employee");
    }

    @Override
    public ValidationResult validate(Employee employee) {
        var result = new ValidationResult();
        String name = employee.getName();
        int id = employee.getEmployeeId();

        if (name == null) {
            result.add("Assigned to NAME cannot be null"
            );
        } else if (id < 0){
            result.add("ID cannot be negative"
            );
        } else if (name.length() > 25 || name.length() < 0) {
            result.add("'%s' length is not between %d (inclusive) and %d (inclusive)"
                    .formatted(employee.getName(), 0, 25)
            );
        }
        return result;
    }
}
