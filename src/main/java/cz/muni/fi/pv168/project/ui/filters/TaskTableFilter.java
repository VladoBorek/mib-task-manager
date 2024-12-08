package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatchers;
import cz.muni.fi.pv168.project.ui.filters.matchers.entityWithCategory.EntityCategoryMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.task.TaskDueDateMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.task.TaskStatusMatcher;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EmployeeTable.
 */
public final class TaskTableFilter {
    private final TaskCompoundMatcher taskCompoundMatcher;

    public TaskTableFilter(TableRowSorter<TaskTableModel> rowSorter, UIDataManager data) {
        taskCompoundMatcher = new TaskCompoundMatcher(rowSorter, data);
        rowSorter.setRowFilter(taskCompoundMatcher);
    }

    public void filterCategory(Either<SpecialFilterCategoryValues, Category> selectedItem) {
        selectedItem.apply(
                l -> taskCompoundMatcher.setCategoryMatcher((EntityMatcher<Task>) l.getMatcher()),
                r -> taskCompoundMatcher.setCategoryMatcher(new EntityCategoryMatcher<Task>(r))
        );
    }

    public void filterStatus(boolean showToDo, boolean showInProgress, boolean showComplete, boolean showOnHold) {
        EnumSet<Status> statuses = EnumSet.noneOf(Status.class);
        if (showToDo) statuses.add(Status.TO_DO);
        if (showInProgress) statuses.add(Status.IN_PROGRESS);
        if (showComplete) statuses.add(Status.COMPLETED);
        if (showOnHold) statuses.add(Status.ON_HOLD);

        taskCompoundMatcher.setStatusMatcher(new TaskStatusMatcher(statuses));
    }

    public void filterDueDate(LocalDate fromDate, LocalDate toDate) {
        taskCompoundMatcher.setDueDateMatcher(new TaskDueDateMatcher(fromDate, toDate));
    }

    /**
     * Container class for all matchers for the EmployeeTable.
     * <p>
     * This Matcher evaluates to true, if all contained {@link EntityMatcher} instances
     * evaluate to true.
     */
    private static class TaskCompoundMatcher extends EntityMatcher<Task> {

        private final TableRowSorter<TaskTableModel> rowSorter;
        private EntityMatcher<Task> categoryMatcher = EntityMatchers.all();
        private EntityMatcher<Task> statusMatcher = EntityMatchers.all();
        private EntityMatcher<Task> dueDateMatcher = EntityMatchers.all();

        private final StatisticsTableModel statisticsTableModel;

        private TaskCompoundMatcher(TableRowSorter<TaskTableModel> rowSorter, UIDataManager data) {
            this.rowSorter = rowSorter;
            this.statisticsTableModel = data.getStatisticsTableModel();
        }

        private void setCategoryMatcher(EntityMatcher<Task> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
            statisticsTableModel.refreshStatistics();
        }

        private void setStatusMatcher(EntityMatcher<Task> statusMatcher) {
            this.statusMatcher = statusMatcher;
            rowSorter.sort();
            statisticsTableModel.refreshStatistics();

        }

        private void setDueDateMatcher(EntityMatcher<Task> dueDateMatcher) {
            this.dueDateMatcher = dueDateMatcher;
            rowSorter.sort();
            statisticsTableModel.refreshStatistics();
        }

        @Override
        public boolean evaluate(Task task) {
            return Stream.of(categoryMatcher, statusMatcher, dueDateMatcher)
                    .allMatch(m -> m.evaluate(task));
        }
    }
}
