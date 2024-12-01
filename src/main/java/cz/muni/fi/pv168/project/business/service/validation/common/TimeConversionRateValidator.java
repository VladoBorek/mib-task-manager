package cz.muni.fi.pv168.project.business.service.validation.common;

import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;


public class TimeConversionRateValidator extends PropertyValidator<Integer> {

    public TimeConversionRateValidator() {
        super("Conversion rate");
    }

    @Override
    public ValidationResult validate(Integer rate) {
        var result = new ValidationResult();
        if (rate < 1) {
            result.add("Time unit conversion rate must be larger than 1");
        }

        return result;
    }
}
