package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.*;
import java.util.List;

public class CategoryListModel extends AbstractListModel<Category> {

    private final List<Category> categoryList;

    public CategoryListModel(List<Category> categoryList) {
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

    public Category[] toArray() {
        return categoryList.toArray(new Category[0]);
    }

    public void addCategory(Category category) {
        categoryList.add(category);
    }
}
