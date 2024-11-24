package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.TemplateValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.category.AddCategoryAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.AddTimeUnitAction;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.renderers.CategoryComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TemplateDialog extends EntityDialog<Template> {
    private final JTextField nameField = new JTextField();
    private final JTextField templateNameField = new JTextField();
    private final DataManager data;
    private JPanel timeUnitPanel;
    private JPanel categoryPanel;
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();
    private final Template template;
    private final JPanel infoPanel = new JPanel();
    private final JPanel timePanel = new JPanel();


    public TemplateDialog(DataManager data, Template template) {
        super.getPanel().setLayout(new BorderLayout());
        super.getPanel().add(infoPanel, BorderLayout.CENTER);
        super.getPanel().add(timePanel, BorderLayout.SOUTH);

        this.data = data;
        this.timeUnitComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnits()));
        this.categoryComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategories()));

        setupInfoPanel();
        setupTimePanel();

        infoPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        timePanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        this.template = template;
        if (template != null) {
            setValues();
        }
    }

    private void setupInfoPanel() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.add(super.getLabelPanel());
        infoPanel.add(super.getComponentPanel());

        setupCategoryPanel();
        addFields();
    }

    private void setupCategoryPanel() {
        var addCategoryButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddCategoryAction(data, categoryComboBox));

        categoryComboBox.setRenderer(new CategoryComboboxRenderer());
        categoryComboBox.addActionListener(e -> {
            CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        });
        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);

        categoryPanel = createTwoPartPanel(categoryComboBox, addCategoryButton);
    }

    private void setupTimePanel() {
        var addTimeUnitButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddTimeUnitAction(data, timeUnitComboBox));

        timeUnitPanel = createTwoPartPanel(timeUnitComboBox, addTimeUnitButton);

        timePanel.setLayout(new BorderLayout());
        timePanel.add(setupTimeTitlesPanel(), BorderLayout.NORTH);
        timePanel.add(setupTimeInfoPanel(), BorderLayout.SOUTH);
    }

    private JPanel setupTimeTitlesPanel() {
        JPanel timeTitlesPanel = new JPanel(new GridLayout(1, 2));

        timeTitlesPanel.add(new JLabel("Allocated time"));
        timeTitlesPanel.add(new JLabel("Time unit"));

        return timeTitlesPanel;
    }

    private JPanel setupTimeInfoPanel() {
        JPanel timeInfoPanel = new JPanel();

        timeInfoPanel.setLayout(new GridLayout(1, 2));
        timeInfoPanel.add(this.allocatedTimeField);
        timeInfoPanel.add(timeUnitPanel);

        return timeInfoPanel;
    }

    private void setValues() {
        nameField.setText(template.getName());
        templateNameField.setText(template.getTemplateName());
        categoryComboBox.setSelectedItem(template.getCategory());
        allocatedTimeField.setValue(template.getAllocatedTime());
        timeUnitComboBox.setSelectedItem(template.getTimeUnit());
    }

    private void addFields() {
        add("Template name", templateNameField);
        add("Task name", nameField);
        add("Category", categoryPanel);
    }

    private boolean validateFields() {
        if ((nameField.getText().trim().isEmpty())
                || (templateNameField.getText().trim().isEmpty())
                || (allocatedTimeField.getText().trim().isEmpty())
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
        Validator<Template> templateValidator = new TemplateValidator();
        var newTemplate = new Template(null, nameField.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                allocatedTimeField.getValue(),
                (TimeUnit) timeUnitComboBox.getSelectedItem(),
                templateNameField.getText());
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
