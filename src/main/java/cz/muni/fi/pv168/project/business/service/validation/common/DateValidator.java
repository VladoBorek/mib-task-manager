package cz.muni.fi.pv168.project.business.service.validation.common;

import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import java.time.LocalDate;


public class DateValidator extends PropertyValidator<LocalDate> {

    public DateValidator() {
        super("Date");
    }

    @Override
    public ValidationResult validate(LocalDate date) {
        ValidationResult result = new ValidationResult();
        if (date == null) {
            result.add(getName() + " must not be null.");
        }
        return result;
    }
}
