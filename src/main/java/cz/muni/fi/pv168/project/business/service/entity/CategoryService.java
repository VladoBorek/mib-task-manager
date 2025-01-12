package cz.muni.fi.pv168.project.business.service.entity;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.repository.Repository;

/**
 * @author Vladimir Borek
 */
public class CategoryService {
    private final Repository<Task> taskRepository;
    private final Repository<Template> templateRepository;

    public CategoryService(Repository<Task> taskRepository, Repository<Template> templateRepository) {
        this.taskRepository = taskRepository;
        this.templateRepository = templateRepository;
    }

    public boolean isCategoryInUse(Category category) {
        boolean usedInTasks = taskRepository.findAll().stream()
                .anyMatch(task -> task.getCategory().equals(category));

        boolean usedInTemplates = templateRepository.findAll().stream()
                .anyMatch(template -> template.getCategory().equals(category));

        return usedInTasks || usedInTemplates;
    }
}
