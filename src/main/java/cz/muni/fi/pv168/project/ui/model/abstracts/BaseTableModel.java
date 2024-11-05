package cz.muni.fi.pv168.project.ui.model.abstracts;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTableModel<T extends Entity> extends AbstractTableModel {

    private final List<T> items;
    private final CrudService<T> crudService;

    public BaseTableModel(CrudService<T> crudService) {
        this.crudService = crudService;
        this.items = new ArrayList<>(crudService.findAll());
    }

    @Override
    public int getRowCount() {
        return items.size();
    }



    public T getEntity(int rowIndex) {
        return items.get(rowIndex);
    }

    public void updateRow(T task) {
        crudService.update(task); //TODO validation
//                .intoException();
        int rowIndex = items.indexOf(task);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void addRow(T task){
        int newRowIndex = items.size();
        crudService.create(task); //TODO validation
//                        .intoException();
        items.add(task);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void deleteRow(int rowIndex) {
        var taskToBeDeleted = getEntity(rowIndex);
        crudService.deleteById(taskToBeDeleted.getId());
        items.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public List<T> getAllRows() {
        return items;
    }

    public void deleteAllRows(){
        var totalRows = getRowCount();
        for (int i = 0; i < totalRows; i++) {
            deleteRow(0);
        }
    }
}
