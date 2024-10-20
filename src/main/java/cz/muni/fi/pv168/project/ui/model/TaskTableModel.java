package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Employee;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskTableModel extends AbstractTableModel {

    private final List<Task> tasks;
    private final List<Column<Task, ?>> columns = List.of(
            Column.readonly("STATUS", Status.class, Task::getStatus),
            Column.readonly("CATEGORY", Category.class, Task::getCategory),
            Column.readonly("CUSTOMER", String.class, Task::getCustomer),
            Column.readonly("TASK NAME", String.class, Task::getNameOfTask),
            Column.readonly("ASSIGNED TO", Employee.class, Task::getAssignedTo),
            Column.readonly("LOGGED TIME", Integer.class, Task::getLoggedTime),
            Column.readonly("ALLOCATED TIME", Integer.class, Task::getAllocatedTime),
            Column.readonly("TIME UNIT", TimeUnit.class, Task::getTimeUnit),
            Column.readonly("% DONE", Float.class, Task::getPercentage),
            Column.readonly("DUE DATE", LocalDate.class, Task::getDueDate)
            );

    public TaskTableModel(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
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
        int rowIndex = tasks.indexOf(task);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void addRow(Task task){
        int newRowIndex = tasks.size();
        tasks.add(task);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void deleteRow(int rowIndex) {
        tasks.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }



}
