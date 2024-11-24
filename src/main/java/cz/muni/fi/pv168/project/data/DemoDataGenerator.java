package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DemoDataGenerator {

    private static final List<Category> CATEGORIES = List.of(
            new Category(null, "Online", new Color(200, 1, 1)),
            new Category(null, "In-Person", new Color(2, 200, 2)),
            new Category(null, "Systems update", new Color(3, 3, 200))
    );
    private static final List<String> EMPLOYEES = List.of(
            "Not assigned",
            "Remy",
            "Ramy",
            "Romy"
    );

    private static final List<TimeUnit> TIME_UNITS = List.of(
            new TimeUnit(),
            new TimeUnit(null, "Hour", "h", 60),
            new TimeUnit(null, "Day", "d", 1440)
    );

    private static final List<Task> TASKS = List.of(
            new Task(1L, Status.TO_DO, "Develop the core application functionality for the 'Rats in White' project, focused on user interface improvements.", CATEGORIES.get(0), "Rats in White", "Application", EMPLOYEES.get(0), 2, 10, TIME_UNITS.get(0), LocalDate.now()),
            new Task(2L, Status.ON_HOLD, "Set up equipment and configure software on-site for the 'Dgs in Blue' project, pending further client instructions.", CATEGORIES.get(1), "Dgs in Blue", "On-site setup", EMPLOYEES.get(1), 12, 10, TIME_UNITS.get(1), LocalDate.now()),
            new Task(3L, Status.TO_DO, "Continue building additional features for the 'Rats in White' project, ensuring integration with backend services.", CATEGORIES.get(2), "Rats in White", "Application", EMPLOYEES.get(2), 0, 10, TIME_UNITS.get(2), LocalDate.now()),
            new Task(4L, Status.COMPLETED, "Finalize the application development for the 'Cats in Blue' project, including testing and deployment.", CATEGORIES.get(0), "Cats in Blue", "Application", EMPLOYEES.get(3), 10, 10, TIME_UNITS.get(0), LocalDate.now())
    );

    private static final List<LogTimeInfo> LOGS = List.of(
            new LogTimeInfo(2, new User(EMPLOYEES.get(1), 1L), 2L),
            new LogTimeInfo(2, new User(EMPLOYEES.get(2), 2L), 1L),
            new LogTimeInfo(2, new User(EMPLOYEES.get(3), 3L), 1L),
            new LogTimeInfo(2, new User(EMPLOYEES.get(1), 1L), 1L),
            new LogTimeInfo(2, new User(EMPLOYEES.get(1), 1L), 1L)
    );

    private static final List<String> CUSTOMERS = List.of(
            "Rats in white",
            "Dogs in blue",
            "Upper management"
    );

    public List<Category> getCategories() {
        return CATEGORIES;
    }

    public List<String> getCustomers() {
        return CUSTOMERS;
    }

    public List<String> getEmployees() {
        return EMPLOYEES;
    }

    public List<Task> getTasks() {
        return TASKS;
    }

    public List<TimeUnit> getTimeUnits() {
        return TIME_UNITS;
    }

    public List<LogTimeInfo> getLogs() {
        return LOGS;
    }
}
