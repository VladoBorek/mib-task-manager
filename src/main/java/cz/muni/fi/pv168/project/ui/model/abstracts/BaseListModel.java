package cz.muni.fi.pv168.project.ui.model.abstracts;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Maroš Pavlík
 */
public abstract class BaseListModel<T extends Entity> extends AbstractListModel<T> {

    protected List<T> items;
    protected final CrudService<T> crudService;

    public BaseListModel(List<T> items, CrudService<T> crudService) {
        this.crudService = crudService;
        this.items = items;
    }

    public void add(T item) throws ValidationException {
        crudService.create(item).intoException();
        items.add(item);
    }

    public void remove(T item) {
        crudService.deleteById(item.getId());
        items.remove(item);
    }


    public void update(T item) throws ValidationException {
        crudService.update(item).intoException();
    }

    public void refresh() {
        this.items = new ArrayList<>(crudService.findAll());
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public T getElementAt(int index) {
        return items.get(index);
    }
}
