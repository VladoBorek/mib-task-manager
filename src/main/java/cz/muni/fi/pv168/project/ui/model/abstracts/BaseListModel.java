package cz.muni.fi.pv168.project.ui.model.abstracts;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maroš Pavlík
 */
public abstract class BaseListModel<T extends Entity> extends AbstractListModel<T> {

    protected final List<T> items;
    protected final CrudService<T> crudService;

    public BaseListModel(CrudService<T> crudService) {
        this.crudService = crudService;
        this.items = new ArrayList<>(crudService.findAll());
    }

    public BaseListModel(List<T> items, CrudService<T> crudService) {
        this.crudService = crudService;
        this.items = items;
    }



    public void add(T item) {
        try {
            crudService.create(item)
                    .intoException();
        } catch (ValidationException e){
            PopUp.infoDialog(e.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        items.add(item);
    }

    public void remove(T item) {
        crudService.deleteById(item.getId());
        items.remove(item);
    }

    public void update(T item) {
        try {
            crudService.update(item)
                    .intoException();
        } catch (ValidationException e){
            PopUp.infoDialog(e.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public T getElementAt(int index) {
        return items.get(index);
    }

    public boolean justValidate(T entity) {
        try {
            crudService.validate(entity).intoException();
        } catch (ValidationException e){
            PopUp.infoDialog(e.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;  // return false if exception
    }
}
