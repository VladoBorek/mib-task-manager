package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.service.validation.common.NotNegativeIntegerValidator;

import java.util.List;

public class LogTimeInfoValidator implements Validator<LogTimeInfo> {
    @Override
    public ValidationResult validate(LogTimeInfo loggedTime) {
        var validators = List.of(
                Validator.extracting(
                        LogTimeInfo::getLoggedTime, new NotNegativeIntegerValidator("Logged time value"))
        );

        return Validator.compose(validators).validate(loggedTime);
    }
}
