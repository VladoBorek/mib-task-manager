package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

    private final List<Task> tasks;
    private final CrudService<Task> taskCrudService;

    private final List<Column<Task, ?>> columns = List.of(
            Column.readonly("TASK NAME", String.class, Task::getNameOfTask),
            Column.readonly("STATUS", Status.class, Task::getStatus),
            Column.readonly("CATEGORY", Category.class, Task::getCategory),
            Column.readonly("CUSTOMER", String.class, Task::getCustomer),
            Column.readonly("ASSIGNED TO", String.class, Task::getAssignedToString),
            Column.readonly("LOGGED TIME", Integer.class, Task::getConvertedLoggedTime),
            Column.readonly("ALLOCATED TIME", Integer.class, Task::getConvertedAllocatedTime),
            Column.readonly("TIME UNIT", TimeUnit.class, Task::getTimeUnit),
            Column.readonly("% DONE", Float.class, Task::getPercentage),
            Column.readonly("DUE DATE", LocalDate.class, Task::getDueDate)
            );

    public TaskTableModel(CrudService<Task> taskCrudService) {
        this.taskCrudService = taskCrudService;
        this.tasks = new ArrayList<>(taskCrudService.findAll());
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var task = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(task);
    }

    public Task getEntity(int rowIndex) {
        return tasks.get(rowIndex);
    }

    public void updateRow(Task task) {
        taskCrudService.update(task); //TODO validation
//                .intoException();
        int rowIndex = tasks.indexOf(task);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void addRow(Task task){
        int newRowIndex = tasks.size();
        taskCrudService.create(task); //TODO validation
//                        .intoException();
        tasks.add(task);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void deleteRow(int rowIndex) {
        var taskToBeDeleted = getEntity(rowIndex);
        taskCrudService.deleteById(taskToBeDeleted.getId());
        tasks.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }
}
