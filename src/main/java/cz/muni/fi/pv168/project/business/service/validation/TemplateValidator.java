package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import java.util.List;


public class TemplateValidator implements Validator<Template> {
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
                        Template::getCategory, new CategoryValidator()),
                Validator.extracting(
                        Template::getTimeUnit, new TimeUnitValidator())
        );

        return Validator.compose(validators).validate(template);
    }
}
