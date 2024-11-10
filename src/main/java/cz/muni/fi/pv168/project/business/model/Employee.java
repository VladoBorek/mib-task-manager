package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;

/**
 * @author Maroš Pavlík
 */
public class Employee {

    private String name;
    private int employeeId;
    public Employee(String name, int employeeId) {
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
        if (employeeId == 0) {
            return "Not assigned";
        }
        return employeeId + " - " + name;
    }
}
