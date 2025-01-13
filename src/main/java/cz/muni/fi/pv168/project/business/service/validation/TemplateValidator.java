package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.validation.common.NotNegativeDoubleValidator;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import java.util.List;


public class TemplateValidator implements Validator<Template> {
    private final DependencyProvider provider;

    public TemplateValidator(DependencyProvider provider) {
        this.provider = provider;
    }

    @Override
    public ValidationResult validate(Template template) {
        var validators = List.of(
                Validator.extracting(
                        Template::getTemplateName, new StringLengthValidator(1, 25, "Template name")),
                Validator.extracting(
                        Template::getName, new StringLengthValidator(1, 25, "Template task name")),
                Validator.extracting(
                        Template::getDescription, new StringLengthValidator(0, 500, "Description")),
                Validator.extracting(
                        Template::getAssignedTo, new StringLengthValidator(1, 25, "AssignedTo")),
                Validator.extracting(
                        Template::getAllocatedTime, new NotNegativeDoubleValidator("AllocatedTime")
                )
        );

        var validationResult = Validator.compose(validators).validate(template);

        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (provider.getTemplateService().isNameDuplicate(template.getTemplateName())) {
            return ValidationResult.failed("Template name must be unique.");
        }

        return ValidationResult.success();
    }
}
