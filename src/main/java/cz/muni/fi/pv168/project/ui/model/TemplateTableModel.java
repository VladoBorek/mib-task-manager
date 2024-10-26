package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.business.model.Statistic;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Borek
 */
public class TemplateTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Template Name", "TODO", "TODO", "TODO"};
    private final List<Template> templateData;
    public TemplateTableModel(List<Template> templateData) {
        this.templateData = templateData;
    }

    public TemplateTableModel() {
        this.templateData = new ArrayList<>(); // FOR NOW
        templateData.add(new Template("TODO", new Category("TODO", Color.WHITE), 50,
                new CustomTimeUnit(), "TODO"));
        templateData.add(new Template("TODO", new Category("TODO", Color.WHITE), 50,
                new CustomTimeUnit(), "TODO"));
        templateData.add(new Template("TODO", new Category("TODO", Color.WHITE), 50,
                new CustomTimeUnit(), "TODO"));
        templateData.add(new Template("TODO", new Category("TODO", Color.WHITE), 50,
                new CustomTimeUnit(), "TODO"));
    }

    @Override
    public int getRowCount() {
        return templateData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Template template = templateData.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> template.getTemplateName();
            case 1 -> template.getName();
            case 2 -> template.getCategory();
            case 3 -> template.getAllocatedTime();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

}
