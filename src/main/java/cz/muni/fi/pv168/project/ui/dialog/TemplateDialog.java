package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;

public class TemplateDialog extends EntityDialog<Template>{

    private final JTextField nameField = new JTextField();

    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());

    private final CategoryListModel categories;
    private final JComboBox<Category> categoryComboBox;
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final TimeUnit timeUnit = new CustomTimeUnit();

    public TemplateDialog(CategoryListModel categories, TimeUnitListModel timeUnits) {
        this.timeUnitComboBox = new JComboBox<>(timeUnits.toArray());
        this.categories = categories;
        this.categoryComboBox = new JComboBox<>(categories.toArray());

        addFields();
        setPanel();
    }

    private void addFields(){
        add("Task name", nameField);
        add("Category", new JComboBox<>(categories.toArray()));
        add("Status", statusComboBox);
        add("Allocated time", allocatedTimeField);
        add("Time unit", new JLabel(timeUnit.getName()));
        add("Due date", datePicker);

    }

    @Override
    Template getEntity() {
        return new Template(nameField.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                allocatedTimeField.getValue(),
                (TimeUnit) timeUnitComboBox.getSelectedItem());
    }
}
