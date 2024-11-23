package cz.muni.fi.pv168.project.ui.model.storagemodels;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.Column;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseTableModel;

import java.util.List;

/**
 * @author Vladimir Borek
 */
public class LogTimeInfoTableModel extends BaseTableModel<LogTimeInfo> {
    private final List<Column<LogTimeInfo, ?>> columns = List.of(
            Column.readonly("ID TASK", Long.class, LogTimeInfo::getTaskID),
            Column.readonly("ID", Long.class, LogTimeInfo::getUserId),
            Column.readonly("Name", String.class, LogTimeInfo::getUsername),
            Column.readonly("Logged Time", Integer.class, LogTimeInfo::getLoggedTime)
    );

    public LogTimeInfoTableModel(CrudService<LogTimeInfo> crudService) {
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
