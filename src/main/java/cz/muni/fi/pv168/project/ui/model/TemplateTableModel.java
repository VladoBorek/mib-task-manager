package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TemplateTableModel extends BaseTableModel<Template> {

    private final List<Column<Template, ?>> columns = List.of(
            Column.readonly("Template Name", String.class, Template::getTemplateName),
            Column.readonly("Task Name", String.class, Template::getName),
            Column.readonly("Category", Category.class, Template::getCategory),
            Column.readonly("Allocated Time", String.class, Template::getConvertedAllocatedTimeString)
            );

    public TemplateTableModel(CrudService<Template> crudService) {
        super(crudService);
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var item = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(item);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }
}
