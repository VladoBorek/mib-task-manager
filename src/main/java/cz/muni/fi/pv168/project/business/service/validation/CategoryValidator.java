package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import java.util.List;

public class CategoryValidator implements Validator<Category> {

    private final DependencyProvider provider;

    public CategoryValidator(DependencyProvider provider) {
        this.provider = provider;
    }

    @Override
    public ValidationResult validate(Category category) {
        if (category == null) {
            return ValidationResult.failed("Category must not be empty");
        }

        var validators = List.of(
                Validator.extracting(
                        Category::getName, new StringLengthValidator(1, 20, "Category name"))
        );

        var validationResult = Validator.compose(validators).validate(category);

        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (provider.getCategoryService().isNameDuplicate(category.getName())) {
            return ValidationResult.failed("Category name must be unique.");
        }

        return ValidationResult.success();
    }
}
