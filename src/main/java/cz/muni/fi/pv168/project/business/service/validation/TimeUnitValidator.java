package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;
import cz.muni.fi.pv168.project.business.service.validation.common.TimeConversionRateValidator;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import java.util.List;


public class TimeUnitValidator implements Validator<TimeUnit> {
    private final DependencyProvider provider;

    public TimeUnitValidator(DependencyProvider provider) {
        this.provider = provider;
    }

    @Override
    public ValidationResult validate(TimeUnit unit) {
        if (unit == null) {
            return ValidationResult.failed("Time unit must not be empty");
        }

        var validators = List.of(
                Validator.extracting(
                        TimeUnit::getName, new StringLengthValidator(1, 10, "Time unit name")),
                Validator.extracting(
                        TimeUnit::getShortName, new StringLengthValidator(1, 3, "Time unit short")),
                Validator.extracting(
                        TimeUnit::getRate, new TimeConversionRateValidator())
        );

        var validationResult = Validator.compose(validators).validate(unit);

        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (provider.getTimeUnitService().isNameDuplicate(unit.getName())) {
            return ValidationResult.failed("Time unit name must be unique.");
        }

        return ValidationResult.success();
    }
}
