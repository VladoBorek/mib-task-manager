package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.Employee;
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
    private static final List<Employee> EMPLOYEES = List.of(
            new Employee("Not assigned", 0),
            new Employee("Remy", 1),
            new Employee("Ramy", 2),
            new Employee("Rumy", 3),
            new Employee("Romy", 4)
    );

    private static final List<Task> TASKS= List.of(
            new Task(Status.TO_DO, "Develop the core application functionality for the 'Rats in White' project, focused on user interface improvements.", CATEGORIES.get(0),"Rats in White", "Application", EMPLOYEES.get(0), 2, 10, new CustomTimeUnit("Day", 8), LocalDate.now()),
            new Task(Status.ON_HOLD,"Set up equipment and configure software on-site for the 'Dgs in Blue' project, pending further client instructions.", CATEGORIES.get(1),"Dgs in Blue", "On-site setup", EMPLOYEES.get(1), 12, 10, new CustomTimeUnit(), LocalDate.now()),
            new Task(Status.TO_DO, "Continue building additional features for the 'Rats in White' project, ensuring integration with backend services.", CATEGORIES.get(2),"Rats in White", "Application", EMPLOYEES.get(2), 0, 10, new CustomTimeUnit(), LocalDate.now()),
            new Task(Status.COMPLETED, "Finalize the application development for the 'Cats in Blue' project, including testing and deployment.", CATEGORIES.get(0),"Cats in Blue", "Application", EMPLOYEES.get(3), 10, 10, new CustomTimeUnit(), LocalDate.now())


            );

    private static final List<String> CUSTOMERS = List.of(
            "Rats in white",
            "Dogs in blue",
            "Upper management"
    );
    private static final List<TimeUnit> TIME_UNITS = List.of(
            new CustomTimeUnit(),
            new CustomTimeUnit("Minute", 60),
            new CustomTimeUnit("15 Minutes", 4)
    );


    public List<Category> getCategories() { return CATEGORIES;}
    public List<String> getCustomers() { return CUSTOMERS;}
    public List<Employee> getEmployees() { return EMPLOYEES;}
    public List<Task> getTasks() { return TASKS;}

    public List<TimeUnit> getTimeUnits() {return TIME_UNITS;}
}
