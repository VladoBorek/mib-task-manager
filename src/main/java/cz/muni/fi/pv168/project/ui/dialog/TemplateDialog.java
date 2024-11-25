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
    private final JTextField taskNameField = new JTextField();
    private final JTextField templateNameField = new JTextField();
    private final JTextField assignedToField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final DataManager data;
    private JPanel timeUnitPanel;
    private JPanel categoryPanel;
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitComboBox;
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();
    private final Template template;
    private final JPanel infoPanel = new JPanel();
    private final JPanel descriptionPanel = new JPanel();
    private final JPanel timePanel = new JPanel();


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
        super.getPanel().setLayout(new BorderLayout());
        super.getPanel().add(infoPanel, BorderLayout.NORTH);
        super.getPanel().add(descriptionPanel, BorderLayout.CENTER);
        super.getPanel().add(timePanel, BorderLayout.SOUTH);

        setupTwoPartPanels();
        setupInfoPanel();
        setupDescriptionPanel();
        setupTimePanel();

        infoPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        descriptionPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        timePanel.setBorder(new EmptyBorder(5, 0, 0, 0));
    }

    private void setupTwoPartPanels() {
        categoryComboBox.setRenderer(new CategoryComboboxRenderer());

        var addCategoryButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddCategoryAction(data, categoryComboBox));
        var addTimeUnitButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddTimeUnitAction(data, timeUnitComboBox));

        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        categoryComboBox.addActionListener(e -> CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox));

        categoryPanel = createTwoPartPanel(categoryComboBox, addCategoryButton);
        timeUnitPanel = createTwoPartPanel(timeUnitComboBox, addTimeUnitButton);
    }

    private void setupInfoPanel() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.add(super.getLabelPanel());
        infoPanel.add(super.getComponentPanel());

        addInfoFields();
    }

    private void setupDescriptionPanel() {
        descriptionPanel.setLayout(new BorderLayout());

        JPanel titleDescriptionPanel = new JPanel(new BorderLayout());
        titleDescriptionPanel.add(new JLabel("Description:"));

        JPanel textDescriptionPanel = new JPanel(new BorderLayout());
        textDescriptionPanel.add(new JScrollPane(descriptionArea));

        descriptionPanel.add(titleDescriptionPanel, BorderLayout.NORTH);
        descriptionPanel.add(textDescriptionPanel, BorderLayout.CENTER);

        descriptionArea.setPreferredSize(new Dimension(200, 50));
        descriptionArea.setMinimumSize(new Dimension(200, 50));
        descriptionArea.setMaximumSize(new Dimension(200, 50));

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
    }

    private void setupTimePanel() {
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
        taskNameField.setText(template.getName());
        templateNameField.setText(template.getTemplateName());
        categoryComboBox.setSelectedItem(template.getCategory());
        assignedToField.setText(template.getAssignedTo());
        descriptionArea.setText(template.getDescription());
        allocatedTimeField.setValue(template.getAllocatedTime());
        timeUnitComboBox.setSelectedItem(template.getTimeUnit());
    }

    private void addInfoFields() {
        add("Template name", templateNameField);
        add("Task name", taskNameField);
        add("Category", categoryPanel);
        add("Assigned to", assignedToField);
    }

    private boolean validateFields() {
        if ((taskNameField.getText().trim().isEmpty())
                || (templateNameField.getText().trim().isEmpty())
                || (allocatedTimeField.getText().trim().isEmpty())
                || (assignedToField.getText().trim().isEmpty())
                // || (descriptionArea.getText().trim().isEmpty()) can be empty
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
