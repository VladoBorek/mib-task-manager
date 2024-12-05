package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.util.ActionType;

import java.util.Collection;

/**
 * @author Vladimir Borek
 */
public class TransactionalImportService implements ImportService {
    private final ImportService importService;

    private final TransactionExecutor transactionExecutor;

    public TransactionalImportService(ImportService importService, TransactionExecutor transactionExecutor) {
        this.importService = importService;
        this.transactionExecutor = transactionExecutor;
    }

    //TODO: just temporary implementation
    @Override
    public void importData(String filePath, ActionType type, boolean deleteData) {
        transactionExecutor.executeInTransaction(() -> importService.importData(filePath, type, deleteData));
    }

    @Override
    public Collection<Format> getFormats() {
        return importService.getFormats();
    }
}
