package cz.muni.fi.pv168.project.business.service.validation.common;

import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

/**
 * {@link Validator} that evaluates to true if given string length is inside bounds min and max
 */
public final class StringLengthValidator extends PropertyValidator<String> {
    private final int min;
    private final int max;

    public StringLengthValidator(int min, int max, String name) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public ValidationResult validate(String string) {
        var result = new ValidationResult();
        var length = string.length();

        if (min > length || length > max) {
            result.add("'%s' length is not between %d (inclusive) and %d (inclusive)"
                    .formatted(getName(string), min, max)
            );
        }

        return result;
    }
}
