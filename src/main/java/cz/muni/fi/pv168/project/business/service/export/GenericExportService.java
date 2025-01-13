package cz.muni.fi.pv168.project.business.service.export;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;

import java.util.Collection;

/**
 * Generic synchronous implementation of the {@link ExportService}
 */
public class GenericExportService implements ExportService {

    private final CrudService<Task> taskCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;

    private final FormatMapping<BatchExporter> exporters;

    public GenericExportService(
            CrudService<Task> taskCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Template> templateCrudService,
            CrudService<TimeUnit> timeUnitCrudService,
            Collection<BatchExporter> exporters
    ) {
        this.taskCrudService = taskCrudService;
        this.categoryCrudService = categoryCrudService;
        this.templateCrudService = templateCrudService;
        this.timeUnitCrudService = timeUnitCrudService;
        this.exporters = new FormatMapping<>(exporters);
    }

    @Override
    public Collection<Format> getFormats() {
        return exporters.getFormats();
    }

    @Override
    public void exportData(String filePath) {
        var exporter = getExporter(filePath);
        var batch = new Batch(taskCrudService.findAll(),
                categoryCrudService.findAll(),
                templateCrudService.findAll(),
                timeUnitCrudService.findAll());
        exporter.exportBatch(batch, filePath);
    }

    private BatchExporter getExporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var exporter = exporters.findByExtension(extension);
        if (exporter == null)
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        return exporter;
    }
}
