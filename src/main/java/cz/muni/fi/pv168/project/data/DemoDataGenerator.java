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

    private static final List<String> CUSTOMERS = List.of(
            "Rats in white",
            "Dogs in blue",
            "Upper management"
    );

    private static final List<String> EMPLOYEES = List.of(
            "Remy",
            "Spot",
            "Goofy",
            "Mickey"
    );

    public List<Category> getCategories(){ return CATEGORIES;}
    public List<String> getCustomers(){ return CUSTOMERS;}
    public List<String> getEmployees(){ return EMPLOYEES;}
}
