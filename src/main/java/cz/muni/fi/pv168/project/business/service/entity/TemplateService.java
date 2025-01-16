package cz.muni.fi.pv168.project.business.service.entity;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import java.util.Objects;

/**
 * @author Vladimir Borek
 */
public class TemplateService {
    private final DependencyProvider provider;

    public TemplateService(DependencyProvider provider) {
        this.provider = provider;
    }

    public boolean isNameDuplicate(Template template) {
        return provider.getTemplateRepository().findAll().stream()
                .anyMatch(temp -> temp.getTemplateName().equalsIgnoreCase(template.getTemplateName()) && !(Objects.equals(temp.getId(), template.getId())));
    }
}
