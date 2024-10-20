package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Employee;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maroš Pavlík
 */
public class EmployeeListModel extends AbstractListModel<Employee> {

    private final List<Employee> employees;

    public EmployeeListModel(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    public Employee[] toArray() {
        return employees.toArray(new Employee[0]);
    }

    @Override
    public int getSize() {
        return employees.size();
    }

    @Override
    public Employee getElementAt(int index) {
        return employees.get(index);
    }
}
