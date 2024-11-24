package cz.muni.fi.pv168.project.business.service.validation.common;

import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

/**
 * @author Maroš Pavlík
 */
public class NotNegativeIntegerValidator extends PropertyValidator<Integer> {

    public NotNegativeIntegerValidator(String name) {
        super(name);
    }

    @Override
    public ValidationResult validate(Integer value) {
        ValidationResult result = new ValidationResult();
        if (value == null) {
            result.add(getName() + " must not be null.");
        } else if (value < 0) {
            result.add(getName() + " must not be negative.");
        }
        return result;
    }
}
