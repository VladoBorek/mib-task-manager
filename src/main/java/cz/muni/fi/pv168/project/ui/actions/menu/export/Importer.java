package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.util.Collection;

/**
 * Generic mechanism, allowing to import data from a file.
 */
public interface Importer {

    /**
     * Imports data from a file.
     *
     * @param filePath   absolute path of the export file (to be created or overwritten)
     * @param deleteData user option to delete existing data before importing new one
     * @throws BatchOperationException if the import cannot be done
     */
    void importData(String filePath, boolean deleteData);

    /**
     * Gets all available formats for import.
     */
    Collection<Format> getFormats();
}
