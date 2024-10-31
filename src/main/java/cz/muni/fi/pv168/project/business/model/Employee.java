package cz.muni.fi.pv168.project.business.model;

/**
 * @author Maroš Pavlík
 */
public class Employee extends Entity {

    private String name;
    private int employeeId;
    public Employee(Long id, String name, int employeeId) {
        super(id);
        this.name = name;
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Not assigned";
        }
        return id + " - " + name;
    }
}
