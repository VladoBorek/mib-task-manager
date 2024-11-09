package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;
import cz.muni.fi.pv168.project.business.service.validation.common.TimeConversionRateValidator;

import java.util.List;


public class TimeUnitValidator implements Validator<TimeUnit> {
    @Override
    public ValidationResult validate(TimeUnit unit) {
        var validators = List.of(
                Validator.extracting(
                        TimeUnit::getName, new StringLengthValidator(1, 10, "Time unit name")),
                Validator.extracting(
                        TimeUnit::getShortName, new StringLengthValidator(1, 3, "Time unit short")),
                Validator.extracting(
                        TimeUnit::getRate, new TimeConversionRateValidator())
        );

        return Validator.compose(validators).validate(unit);
    }
}
