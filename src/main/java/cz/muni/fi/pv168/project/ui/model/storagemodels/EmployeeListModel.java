package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseListModel;

import javax.swing.*;
import java.util.List;

/**
 * @author Maroš Pavlík
 */
public class EmployeeListModel extends BaseListModel<Employee> {

    public EmployeeListModel(List<Employee> employees, CrudService<Employee> employeeCrudService) {
        super(employees, employeeCrudService);
    }
    public Employee[] toArray() {
        return items.toArray(new Employee[0]);

    }
}
