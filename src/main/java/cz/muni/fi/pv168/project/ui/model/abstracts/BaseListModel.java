package cz.muni.fi.pv168.project.ui.model.abstracts;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

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
        crudService.create(item).intoException(); //TODO ADD ERROR POPUP
        items.add(item);
    }

    public void remove(T item) {
        crudService.deleteById(item.getId());
        items.remove(item);
    }

    public void update(T item) {
        crudService.update(item).intoException(); //TODO ADD ERROR POPUP
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
        crudService.validate(entity).intoException(); //TODO ADD ERROR POPUP

        return true;  // return false if exception
    }
}
