package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.model.CategoryComboboxRenderer;

import javax.swing.*;
import java.util.Objects;

public class TemplateDialog extends EntityDialog<Template>{
    private final JTextField nameField = new JTextField();
    private final JTextField templateNameField = new JTextField();
    private final DataManager data;
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();
    private final Template template;

    public TemplateDialog(DataManager data, Template template) {
        this.data = data;
        this.timeUnitComboBox = new JComboBox<>(data.getTimeUnits().toArray());
        this.categoryComboBox = new JComboBox<>(data.getCategories().toArray());

        categoryComboBox.setRenderer(new CategoryComboboxRenderer());
        categoryComboBox.addActionListener(e -> {
            CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        });
        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);

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
        timeUnitComboBox.setSelectedItem(template.getTimeUnit());
    }

    private void addFields(){
        add("Template name", templateNameField);
        add("Task name", nameField);
        add("Category", categoryComboBox);
        add("Allocated time", allocatedTimeField);
        add("Time unit", timeUnitComboBox);

    }

    private boolean validateFields() {
        if ((nameField.getText().trim().isEmpty())
                || (templateNameField.getText().trim().isEmpty())
                || (allocatedTimeField.getText().trim().isEmpty())
        ) {
            JOptionPane.showMessageDialog(null, "Please fill all information", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    Template getEntity() {

        if (!validateFields()) {
            return null;
        }
        var template = this.template;
        if (template != null) {
            template.setTemplateName(templateNameField.getText());
            template.setName(nameField.getText());
            template.setTimeUnit((TimeUnit) timeUnitComboBox.getSelectedItem());
            template.setCategory((Category) categoryComboBox.getSelectedItem());
            template.setAllocatedTime(allocatedTimeField.getValue());
        } else {
            template = new Template(null, nameField.getText(),
                    (Category) categoryComboBox.getSelectedItem(),
                    allocatedTimeField.getValue(),
                    (TimeUnit) timeUnitComboBox.getSelectedItem(),
                    templateNameField.getText());
        }
        return template;
    }
}
