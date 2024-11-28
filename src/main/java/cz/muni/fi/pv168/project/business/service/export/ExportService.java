package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.util.ActionType;

import java.util.Collection;

/**
 * Generic mechanism, allowing to export data to a file.
 */
public interface ExportService {

    /**
     * Exports data to a file.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     * @param type     type of items to be exported
     *
     * @throws BatchOperationException if the export cannot be done
     */
    void exportData(String filePath, ActionType type);

    /**
     * Gets all available formats for export.
     */
    Collection<Format> getFormats();
}
