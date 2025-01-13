package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.InfoPanelFactory;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.TimePanelFactory;
import cz.muni.fi.pv168.project.ui.utils.UIElements;
import cz.muni.fi.pv168.project.util.Constants;
import org.tinylog.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.*;

/**
 * Dialog for adding and editing templates
 */
public class TemplateDialog extends EntityDialog<Template> {
    private final JTextField taskNameField = new JTextField();
    private final JTextField templateNameField = new JTextField();
    private final JTextField assignedToField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final UIDataManager data;
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final JFormattedTextField allocatedTimeField = UIElements.createDecimalFormattedTextField();
    private final Template template;

    public TemplateDialog(UIDataManager data, Template template) {
        this.data = data;
        this.template = template;

        this.timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnitListModel()));
        this.categoryComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategoryListModel()));

        setUpUI();

        if (template != null) {
            setValues();
        } else {
            timeUnitComboBox.setSelectedItem(Constants.BASE_TIME_UNIT);
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
        allocatedTimeField.setText(String.valueOf(template.getConvertedAllocatedTime()));
        timeUnitComboBox.setSelectedItem(template.getTimeUnit());
    }

    @Override
    public Template getEntity() {
        var timeunit = (TimeUnit) Objects.requireNonNull(timeUnitComboBox.getSelectedItem());
        double allocatedTime = Double.parseDouble(allocatedTimeField.getText());
        Validator<Template> templateValidator = data.getDependencyProvider().getTemplateValidator();

        var newTemplate = new Template(null, taskNameField.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                allocatedTime * timeunit.getRate(),
                timeunit,
                templateNameField.getText(),
                descriptionArea.getText(),
                assignedToField.getText());

        var validation = templateValidator.validate(newTemplate);

        if (!validation.isValid()) {
            Logger.error("Template failed Validation " + validation.getValidationErrors());
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return newTemplate;
    }

}
