package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.Column;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseTableModel;
import cz.muni.fi.pv168.project.ui.model.abstracts.EntityTableModel;

import java.time.LocalDate;
import java.util.List;

public class TaskTableModel extends BaseTableModel<Task> implements EntityTableModel<Task> {
    private final List<Column<Task, ?>> columns = List.of(
            Column.readonly("TASK NAME", String.class, Task::getName),
            Column.readonly("STATUS", Status.class, Task::getStatus),
            Column.readonly("CATEGORY", Category.class, Task::getCategory),
            Column.readonly("CUSTOMER", String.class, Task::getCustomer),
            Column.readonly("ASSIGNED TO", String.class, Task::getAssignedTo),
            Column.readonly("LOGGED TIME", String.class, Task::getConvertedLoggedTimeString),
            Column.readonly("ALLOCATED TIME", String.class, Task::getConvertedAllocatedTimeString),
            Column.readonly("% DONE", Float.class, Task::getPercentage),
            Column.readonly("DUE DATE", LocalDate.class, Task::getDueDate)
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
