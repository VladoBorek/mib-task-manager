package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;
import java.util.List;

public class CategoryModel extends AbstractListModel<Category> {

    private List<Category> categoryList;

    public CategoryModel(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getSize() {
        return categoryList.size();
    }

    @Override
    public Category getElementAt(int index) {
        return categoryList.get(index);
    }
}
