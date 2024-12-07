package cz.muni.fi.pv168.project.ui.model.abstracts;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTableModel<T extends Entity> extends AbstractTableModel {

    private List<T> items;
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


    public void updateRow(T item) throws ValidationException {
        crudService.update(item).intoException();
        int rowIndex = items.indexOf(item);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }


    public void addRow(T item) throws ValidationException {
        int newRowIndex = items.size();
        crudService.create(item).intoException();
        items.add(item);
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

    public void refresh() {
        this.items = new ArrayList<>(crudService.findAll());
        fireTableDataChanged();
    }
}
