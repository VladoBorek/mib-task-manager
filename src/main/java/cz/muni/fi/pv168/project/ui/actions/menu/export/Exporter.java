package cz.muni.fi.pv168.project.ui.actions.menu.export;

import cz.muni.fi.pv168.project.business.service.export.format.Format;

import java.util.Collection;

/**
 * Generic mechanism, allowing to export data to a file.
 */
public interface Exporter {

    /**
     * Exports data to a file.
     *
     * @param filePath absolute path of the export file (to be created or overwritten)
     */
    void exportData(String filePath);

    /**
     * Gets all available formats for export.
     */
    Collection<Format> getFormats();
}
