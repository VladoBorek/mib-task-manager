package cz.muni.fi.pv168.project.business.service.entity;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

/**
 * @author Vladimir Borek
 */
public class TimeUnitService {
    private final DependencyProvider provider;


    public TimeUnitService(DependencyProvider provider) {
        this.provider = provider;
    }

    public boolean isTimeUnitInUse(TimeUnit timeUnit) {
        boolean usedInTasks = provider.getTaskRepository().findAll().stream()
                .anyMatch(task -> task.getTimeUnit().equals(timeUnit));

        boolean usedInTemplates = provider.getTemplateRepository().findAll().stream()
                .anyMatch(template -> template.getTimeUnit().equals(timeUnit));

        return usedInTasks || usedInTemplates;
    }

    public boolean isNameDuplicate(TimeUnit timeUnit) {
        return provider.getTimeUnitRepository().findAll().stream()
                .anyMatch(tu -> tu.getName().equalsIgnoreCase(timeUnit.getName()) && !timeUnit.equals(tu));
    }
}
