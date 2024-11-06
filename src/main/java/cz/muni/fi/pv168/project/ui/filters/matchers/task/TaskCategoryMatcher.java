package cz.muni.fi.pv168.project.ui.filters.matchers.task;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;

public class TaskCategoryMatcher extends EntityMatcher<Task> {
    private final Category category;

    public TaskCategoryMatcher(Category category) {
        this.category = category;
    }

    @Override
    public boolean evaluate(Task task) {
        return task.getCategory() == category;
    }
}