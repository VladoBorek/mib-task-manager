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
                        Template::getName, new StringLengthValidator(1, 25, "Template task name"))
        );

        return Validator.compose(validators).validate(template);
    }
}
