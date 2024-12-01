package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;
import cz.muni.fi.pv168.project.ui.utils.ActionType;

import java.util.Collection;

/**
 * Generic synchronous implementation of the {@link ImportService}.
 */
public class GenericImportService implements ImportService {

    private final CrudService<Task> taskCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            CrudService<Task> taskCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Template> templateCrudService,
            CrudService<TimeUnit> timeUnitCrudService,
            Collection<BatchImporter> importers
    ) {
        this.taskCrudService = taskCrudService;
        this.categoryCrudService = categoryCrudService;
        this.templateCrudService = templateCrudService;
        this.timeUnitCrudService = timeUnitCrudService;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath, ActionType type, boolean deleteData) {
        if (deleteData) {
            switch (type) {
                case TASK -> taskCrudService.deleteAll();
                case CATEGORY -> categoryCrudService.deleteAll();
                case TEMPLATE -> templateCrudService.deleteAll();
                case TIME_UNIT -> timeUnitCrudService.deleteAll();
            }
        }
        var currentBatch = new Batch(
                null, categoryCrudService.findAll(),
                null, timeUnitCrudService.findAll());
        var batch = getImporter(filePath).importBatch(filePath, type, currentBatch);
        batch.tasks().forEach(this::createTask);
        batch.categories().forEach(this::createCategory);
        batch.templates().forEach(this::createTemplate);
        batch.timeUnits().forEach(this::createTimeUnit);
    }

    private void createTask(Task task) {
        taskCrudService.create(task)
                .intoException();
    }

    private void createCategory(Category category) {
        categoryCrudService.create(category)
                .intoException();
    }

    private void createTemplate(Template template) {
        templateCrudService.create(template)
                .intoException();
    }

    private void createTimeUnit(TimeUnit timeUnit) {
        timeUnitCrudService.create(timeUnit)
                .intoException();
    }

    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }

        return importer;
    }
}
