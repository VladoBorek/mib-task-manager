package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.Column;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseTableModel;
import cz.muni.fi.pv168.project.ui.model.abstracts.EntityTableModel;

import java.time.LocalDate;
import java.util.List;

import static cz.muni.fi.pv168.project.ui.dialog.task.InspectTaskDialog.DECIMAL_FORMAT;

public class TaskTableModel extends BaseTableModel<Task> implements EntityTableModel<Task> {
    private final List<Column<Task, ?>> columns = List.of(
            Column.readonly("Task Name", String.class, Task::getName),
            Column.readonly("Status", Status.class, Task::getStatus),
            Column.readonly("Category", Category.class, Task::getCategory),
            Column.readonly("Customer", String.class, Task::getCustomer),
            Column.readonly("Assigned to", String.class, Task::getAssignedTo),
            Column.readonly("Logged time", String.class, task -> DECIMAL_FORMAT.format(task.getConvertedLoggedTime()) + " " + task.getTimeUnit().getShortName()),
            Column.readonly("Allocated time", String.class, task -> DECIMAL_FORMAT.format(task.getConvertedAllocatedTime()) + " " + task.getTimeUnit().getShortName()),
            Column.readonly("% done", Float.class, Task::getTaskCompletionPercentage),
            Column.readonly("Due date", LocalDate.class, Task::getDueDate)
    );

    public TaskTableModel(CrudService<Task> taskCrudService) {
        super(taskCrudService);
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var item = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(item);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }
}
