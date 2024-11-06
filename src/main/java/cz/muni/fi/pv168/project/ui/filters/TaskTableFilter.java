package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatchers;
import cz.muni.fi.pv168.project.ui.filters.matchers.task.TaskCategoryMatcher;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.table.TableRowSorter;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EmployeeTable.
 */
public final class TaskTableFilter {
    private final TaskCompoundMatcher taskCompoundMatcher;

    public TaskTableFilter(TableRowSorter<TaskTableModel> rowSorter) {
        taskCompoundMatcher = new TaskCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(taskCompoundMatcher);
    }

    public void filterCategory(Either<SpecialFilterCategoryValues, Category> selectedItem) {
        selectedItem.apply(
                l -> taskCompoundMatcher.setCategoryMatcher(l.getMatcher()),
                r -> taskCompoundMatcher.setCategoryMatcher(new TaskCategoryMatcher(r))
        );
    }

    /**
     * Container class for all matchers for the EmployeeTable.
     *
     * This Matcher evaluates to true, if all contained {@link EntityMatcher} instances
     * evaluate to true.
     */
    private static class TaskCompoundMatcher extends EntityMatcher<Task> {

        private final TableRowSorter<TaskTableModel> rowSorter;
        private EntityMatcher<Task> categoryMatcher = EntityMatchers.all();

        private TaskCompoundMatcher(TableRowSorter<TaskTableModel> rowSorter) {
            this.rowSorter = rowSorter;
        }

        private void setCategoryMatcher(EntityMatcher<Task> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
        }

        @Override
        public boolean evaluate(Task employee) {
            return Stream.of(categoryMatcher)
                    .allMatch(m -> m.evaluate(employee));
        }
    }
}
