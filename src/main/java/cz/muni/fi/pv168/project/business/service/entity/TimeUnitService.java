package cz.muni.fi.pv168.project.business.service.entity;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.repository.Repository;

/**
 * @author Vladimir Borek
 */
public class TimeUnitService {
    private final Repository<Task> taskRepository;
    private final Repository<Template> templateRepository;

    public TimeUnitService(Repository<Task> taskRepository, Repository<Template> templateRepository) {
        this.taskRepository = taskRepository;
        this.templateRepository = templateRepository;
    }

    public boolean isTimeUnitInUse(TimeUnit timeUnit) {
        boolean usedInTasks = taskRepository.findAll().stream()
                .anyMatch(task -> task.getTimeUnit().equals(timeUnit));

        boolean usedInTemplates = templateRepository.findAll().stream()
                .anyMatch(template -> template.getTimeUnit().equals(timeUnit));

        return usedInTasks || usedInTemplates;
    }
}
