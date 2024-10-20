package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.DataManager;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;
import javax.xml.crypto.Data;

public class TemplateDialog extends EntityDialog<Template>{

    private final JTextField nameField = new JTextField();
    private final JTextField templateNameField = new JTextField();
    private final DataManager data;
    private final JComboBox<Category> categoryComboBox;
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final TimeUnit timeUnit = new CustomTimeUnit();
    private final Template template;

    public TemplateDialog(DataManager data, Template template) {
        this.data = data;
        this.timeUnitComboBox = new JComboBox<>(data.getTimeUnits().toArray());
        this.categoryComboBox = new JComboBox<>(data.getCategories().toArray());
        this.template = template;
        if (template != null) {
            setValues();
        }
        addFields();
        setPanel();
    }

    private void setValues()
    {
        nameField.setText(template.getName());
        templateNameField.setText(template.getTemplateName());
        categoryComboBox.setSelectedItem(template.getCategory());
        allocatedTimeField.setValue(template.getAllocatedTime());

        timeUnit.setName(template.getTimeUnit().getName());
        timeUnit.setRate(template.getTimeUnit().getRate());

        timeUnitComboBox.setSelectedItem(timeUnit);
    }

    private void addFields(){
        add("Template name", templateNameField);
        add("Task name", nameField);
        add("Category", new JComboBox<>(data.getCategories().toArray()));
        add("Allocated time", allocatedTimeField);
        add("Time unit", timeUnitComboBox);

    }

    @Override
    Template getEntity() {
        return new Template(nameField.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                allocatedTimeField.getValue(),
                (TimeUnit) timeUnitComboBox.getSelectedItem(),
                templateNameField.getText());
    }
}
