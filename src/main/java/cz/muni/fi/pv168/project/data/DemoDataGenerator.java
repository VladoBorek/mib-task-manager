package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.Category;

import java.awt.*;
import java.util.List;

public class DemoDataGenerator {
    private static final List<Category> CATEGORIES= List.of(
            new Category("Online", new Color(1,1,1)),
            new Category("In-Person", new Color(2,2,2)),
            new Category("Systems update", new Color(3,3,3))
            );

    public List<Category> getCategories(){ return CATEGORIES;}
}
