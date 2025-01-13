package cz.muni.fi.pv168.project.ui.workers;

import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.ui.actions.menu.export.Importer;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;

import javax.swing.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Implementation of asynchronous exporter for UI.
 */
public class AsyncImporter implements Importer {

    private final ImportService importService;
    private final Runnable onFinish;

    public AsyncImporter(ImportService importService, Runnable onFinish) {
        this.importService = Objects.requireNonNull(importService);
        this.onFinish = onFinish;
    }

    @Override
    public Collection<Format> getFormats() {
        return importService.getFormats();
    }

    @Override
    public void importData(String filePath, boolean deleteData) {
        var asyncWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                ExceptionHandler.exceptionPopUpHandler(
                        () -> importService.importData(filePath, deleteData),
                        "Import status",
                        "Import of" + filePath + "successfully finished.",
                        "Import of " + filePath + " has failed."
                );
                return null;
            }

            @Override
            protected void done() {
                super.done();
                onFinish.run();
            }
        };
        asyncWorker.execute();
    }
}

