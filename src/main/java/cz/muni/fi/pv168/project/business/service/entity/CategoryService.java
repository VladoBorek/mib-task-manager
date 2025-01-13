package cz.muni.fi.pv168.project.business.service.entity;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

/**
 * @author Vladimir Borek
 */
public class CategoryService {
    private final DependencyProvider provider;

    public CategoryService(DependencyProvider provider) {
        this.provider = provider;
    }

    public boolean isCategoryInUse(Category category) {
        boolean usedInTasks = provider.getTaskRepository().findAll().stream()
                .anyMatch(task -> task.getCategory().equals(category));

        boolean usedInTemplates = provider.getTemplateRepository().findAll().stream()
                .anyMatch(template -> template.getCategory().equals(category));

        return usedInTasks || usedInTemplates;
    }

    public boolean isNameDuplicate(String name) {
        return provider.getCategoryRepository().findAll().stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
    }

}
