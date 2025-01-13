package cz.muni.fi.pv168.project.business.service.entity;

import cz.muni.fi.pv168.project.wiring.DependencyProvider;

/**
 * @author Vladimir Borek
 */
public class TemplateService {
    private final DependencyProvider provider;

    public TemplateService(DependencyProvider provider) {
        this.provider = provider;
    }

    public boolean isNameDuplicate(String name) {
        return provider.getTemplateRepository().findAll().stream()
                .anyMatch(template -> template.getName().equalsIgnoreCase(name));
    }
}
