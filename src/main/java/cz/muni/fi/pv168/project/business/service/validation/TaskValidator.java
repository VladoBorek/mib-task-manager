package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.service.validation.common.DateValidator;
import cz.muni.fi.pv168.project.business.service.validation.common.StringLengthValidator;

import java.util.List;

public class TaskValidator implements Validator<Task>{
    @Override
    public ValidationResult validate(Task task) {
        var validators = List.of(
                Validator.extracting(
                        Task::getNameOfTask, new StringLengthValidator(1, 25, "Task name")),
                Validator.extracting(
                        Task::getCustomer, new StringLengthValidator(2, 25, "Customer")),
                Validator.extracting(
                        Task::getDescription, new StringLengthValidator(0, 100, "Description")),
                Validator.extracting(
                        Task::getDueDate, new DateValidator()
                )
        );

        return Validator.compose(validators).validate(task);
    }
}
