package cz.muni.fi.pv168.project.business.service.validation;

import java.util.List;
import java.util.function.Function;

/**
 * Validator interface for validating the model
 *
 * @param <M> Model type
 */
@FunctionalInterface
public interface Validator<M> {
    ValidationResult validate(M model);

    /**
     * Chains this validator with given validator
     *
     * @param other validator to chain this one with
     * @return New {@link Validator} that validates both the original and the other's conditions
     */
    default Validator<M> and(Validator<M> other) {
        return compose(List.of(this, other));
    }

    default <T> Validator<M> and(Function<M, T> extractor, Validator<T> other) {
        return and(extracting(extractor, other));
    }

    static <M, T> Validator<M> extracting(Function<M, T> extractor, Validator<T> other) {
        return model -> other.validate(extractor.apply(model));
    }

    static <M> Validator<M> compose(List<Validator<M>> validators) {
        return model -> validators
                .stream()
                .map(x -> x.validate(model))
                .reduce(new ValidationResult(), (r, e) -> {
                    r.add(e.getValidationErrors());
                    return r;
                });
    }
}
