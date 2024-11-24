package cz.muni.fi.pv168.project.ui.filters.matchers.task;

import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;

import java.util.Set;

public class TaskStatusMatcher extends EntityMatcher<Task> {
    private final Set<Status> allowedStatuses;

    public TaskStatusMatcher(Set<Status> allowedStatuses) {
        this.allowedStatuses = allowedStatuses;
    }

    @Override
    public boolean evaluate(Task task) {
        return allowedStatuses.contains(task.getStatus());
    }
}