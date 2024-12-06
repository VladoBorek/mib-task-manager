package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.TemplateValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.InfoPanelFactory;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.TimePanelFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.*;

/**
 * Dialog for adding and editing templates
 */
public class TemplateDialog extends EntityDialog<Template> {
    private final JTextField taskNameField = new JTextField();
    private final JTextField templateNameField = new JTextField();
    private final JTextField assignedToField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final DataManager data;
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();
    private final Template template;

    public TemplateDialog(DataManager data, Template template) {
        this.data = data;
        this.template = template;

        this.timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnits()));
        this.categoryComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategories()));

        setUpUI();

        if (template != null) {
            setValues();
        }
    }

    private void setUpUI() {
        JPanel descriptionPanel = createDescriptionPanel(descriptionArea, 200, 50);
        JPanel timePanel = TimePanelFactory.createPanel(allocatedTimeField,
                setupTimeUnitTwoPartPanel(timeUnitComboBox, data));
        JPanel infoPanel = InfoPanelFactory.createPanel(taskNameField, templateNameField, assignedToField,
                setupCategoryTwoPartPanel(categoryComboBox, data));

        super.getPanel().setLayout(new BorderLayout());
        super.getPanel().add(infoPanel, BorderLayout.NORTH);
        super.getPanel().add(descriptionPanel, BorderLayout.CENTER);
        super.getPanel().add(timePanel, BorderLayout.SOUTH);


        infoPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        descriptionPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        timePanel.setBorder(new EmptyBorder(5, 0, 0, 0));
    }

    private void setValues() {
        taskNameField.setText(template.getName());
        templateNameField.setText(template.getTemplateName());
        categoryComboBox.setSelectedItem(template.getCategory());
        assignedToField.setText(template.getAssignedTo());
        descriptionArea.setText(template.getDescription());
        allocatedTimeField.setValue(template.getAllocatedTime());
        timeUnitComboBox.setSelectedItem(template.getTimeUnit());
    }

    // TODO: zbavit sa validateFields a implementovat to nejak vo validatore tu aj v Tasku

    private boolean validateFields() {
        if ((taskNameField.getText().trim().isEmpty())
                || (templateNameField.getText().trim().isEmpty())
                || (allocatedTimeField.getText().trim().isEmpty())
                || (assignedToField.getText().trim().isEmpty())
                || (categoryComboBox.getSelectedItem() == null)
                || (timeUnitComboBox.getSelectedItem() == null)

        ) {
            PopUp.infoDialog(
                    "Please fill all information",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public Template getEntity() {
        if (!validateFields()) {
            return null;
        }

        Validator<Template> templateValidator = new TemplateValidator();
        var newTemplate = new Template(null, taskNameField.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                allocatedTimeField.getValue(),
                (TimeUnit) timeUnitComboBox.getSelectedItem(),
                templateNameField.getText(),
                descriptionArea.getText(),
                assignedToField.getText());

        var validation = templateValidator.validate(newTemplate);

        if (!validateFields()) {
            return null;
        }
        if (!validation.isValid()) {
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return newTemplate;
    }

}
