package cz.muni.fi.pv168.project.ui.filters.matchers.task;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;

import java.time.LocalDate;

public class TaskDueDateMatcher extends EntityMatcher<Task> {
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public TaskDueDateMatcher(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public boolean evaluate(Task task) {
        LocalDate taskDueDate = task.getDueDate();
        boolean isAfterFromDate = fromDate == null || !taskDueDate.isBefore(fromDate);
        boolean isBeforeToDate = toDate == null || !taskDueDate.isAfter(toDate);
        return isAfterFromDate && isBeforeToDate;
    }
}