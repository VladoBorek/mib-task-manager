package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.Column;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseTableModel;
import cz.muni.fi.pv168.project.ui.model.abstracts.EntityTableModel;

import java.util.List;

public class TemplateTableModel extends BaseTableModel<Template> implements EntityTableModel<Template> {

    private final List<Column<Template, ?>> columns = List.of(
            Column.readonly("Template Name", String.class, Template::getTemplateName),
            Column.readonly("Task Name", String.class, Template::getName),
            Column.readonly("Category", Category.class, Template::getCategory),
            Column.readonly("Assigned to", String.class, Template::getAssignedTo),
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

    public void remove(Template template) {
        int rowIndex = getRowIndex(template);
        if (rowIndex != -1) {
            deleteRow(rowIndex);
        }
    }

    private int getRowIndex(Template template) {
        for (int i = 0; i < getRowCount(); i++) {
            if (getEntity(i).equals(template)) {
                return i;
            }
        }
        return -1;
    }
}
