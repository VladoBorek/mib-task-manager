package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import java.util.List;

public class CategoryValidator implements Validator<Category> {

    @Override
    public ValidationResult validate(Category category) {
        var validators = List.of(
                Validator.extracting(
                        Category::getName, new StringLengthValidator(1, 20, "Category name"))
        );

        return Validator.compose(validators).validate(category);
    }
}
