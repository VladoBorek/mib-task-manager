package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseListModel;
import java.util.ArrayList;

public class CategoryListModel extends BaseListModel<Category> {

    public CategoryListModel(ArrayList<Category> categories, CrudService<Category> categoryCrudService) {
        super(categories, categoryCrudService);
    }

    public Category[] toArray() {
        return items.toArray(new Category[0]);
    }
}
