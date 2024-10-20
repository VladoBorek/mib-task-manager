package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.TimeUnit;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DemoDataGenerator {

    private static final List<Category> CATEGORIES= List.of(
            new Category("Online", new Color(200,1,1)),
            new Category("In-Person", new Color(2,200,2)),
            new Category("Systems update", new Color(3,3,200))
    );

    private static final List<Task> TASKS= List.of(
            new Task(Status.TO_DO, CATEGORIES.get(0),"Rats in White", "Application", "Remy", 2, 10, new CustomTimeUnit("Day", 8), LocalDate.now()),
            new Task(Status.ON_HOLD, CATEGORIES.get(1),"Dgs in Blue", "On-site setup", "", 12, 10, new CustomTimeUnit(), LocalDate.now()),
            new Task(Status.TO_DO, CATEGORIES.get(2),"Rats in White", "Application", "", 0, 10, new CustomTimeUnit(), LocalDate.now()),
            new Task(Status.COMPLETED, CATEGORIES.get(0),"Cats in Blue", "Application", "", 10, 10, new CustomTimeUnit(), LocalDate.now())


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

    public List<Category> getCategories() { return CATEGORIES;}
    public List<String> getCustomers() { return CUSTOMERS;}
    public List<String> getEmployees() { return EMPLOYEES;}

    public List<Task> getTasks() { return TASKS;}
}
