package cz.muni.fi.pv168.project.ui.workers;

import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.ui.actions.menu.export.Exporter;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;

import javax.swing.SwingWorker;
import java.util.Collection;
import java.util.Objects;

/**
 * Implementation of asynchronous exporter for UI.
 */
public class AsyncExporter implements Exporter {

    private final ExportService exportService;
    private final Runnable onFinish;

    public AsyncExporter(ExportService exportService, Runnable onFinish) {
        this.exportService = Objects.requireNonNull(exportService);
        this.onFinish = onFinish;
    }

    @Override
    public Collection<Format> getFormats() {
        return exportService.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var asyncWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                ExceptionHandler.exceptionPopUpHandler(
                        () -> exportService.exportData(filePath),
                        "Export status",
                        "Export has finished.",
                        "Export has failed."
                );
                return null;
            }

            @Override
            protected void done() {
                super.done();
            }
        };
        asyncWorker.execute();
    }
}

