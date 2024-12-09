package cz.muni.fi.pv168.project.business.service.export;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.util.ActionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Generic synchronous implementation of the {@link ImportService}.
 */
public class GenericImportService implements ImportService {

    private final CrudService<Task> taskCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;
    private final CrudService<LogTimeInfo> logTimeInfoCrudService;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            CrudService<Task> taskCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Template> templateCrudService,
            CrudService<TimeUnit> timeUnitCrudService,
            CrudService<LogTimeInfo> logTimeInfoCrudService,
            Collection<BatchImporter> importers
    ) {
        this.taskCrudService = taskCrudService;
        this.categoryCrudService = categoryCrudService;
        this.templateCrudService = templateCrudService;
        this.timeUnitCrudService = timeUnitCrudService;
        this.logTimeInfoCrudService = logTimeInfoCrudService;
        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath, ActionType type, boolean deleteData) {

        if (deleteData) {
            switch (type) {
                case TASK -> {  logTimeInfoCrudService.deleteAll();
                                taskCrudService.deleteAll();}
                case CATEGORY -> categoryCrudService.deleteAll();
                case TEMPLATE -> templateCrudService.deleteAll();
                case TIME_UNIT -> timeUnitCrudService.deleteAll();
                case WORK_LOG -> logTimeInfoCrudService.deleteAll();
            }
        }
        var currentBatch = new Batch(
                taskCrudService.findAll(),
                categoryCrudService.findAll(),
                templateCrudService.findAll(),
                timeUnitCrudService.findAll(),
                logTimeInfoCrudService.findAll());
        try {
            var importBatch = getImporter(filePath).importBatch(filePath, type, currentBatch);

            var filteredCategories = importBatch.categories().stream().filter(category -> !currentBatch.categories().contains(category)).toList();
            var filteredTemplates = importBatch.templates().stream().filter(template -> !currentBatch.templates().contains(template)).toList();
            var filteredTimeUnits = importBatch.timeUnits().stream().filter(timeUnit -> !currentBatch.timeUnits().contains(timeUnit)).toList();
            var filteredLogTimeInfos = importBatch.logTimeInfos().stream().filter(logTimeInfo -> !currentBatch.logTimeInfos().contains(logTimeInfo)).toList();
            var filteredTasks = importBatch.tasks().stream().filter(task -> !currentBatch.tasks().contains(task)).toList();

            var taskImportFirstID = 0L;
            if (type == ActionType.TASK){
                taskImportFirstID = filteredTasks.get(0).getId();
            }

            filteredCategories.forEach(this::createCategory);
            filteredTemplates.forEach(this::createTemplate);
            filteredTimeUnits.forEach(this::createTimeUnit);
            filteredTasks.forEach(this::createTask);

            var updatedLogs = updateLogTaskIDs(filteredLogTimeInfos, filteredTasks, taskImportFirstID);
            updatedLogs.forEach(this::createLogTimeInfo);
        } catch (DataManipulationException dmex){
            throw new BatchOperationException("Import failed because of:\n" + dmex.getMessage());
        } catch (ValidationException vex){
            throw new BatchOperationException("Some items you tried to import failed validations\n" + vex.getValidationErrors());
        }
    }

    private List<LogTimeInfo> updateLogTaskIDs(List<LogTimeInfo> filteredLogTimeInfos, List<Task> filteredTasks, Long taskImportFirstID) {
        if (filteredTasks.size() == 0){
            return filteredLogTimeInfos;
        }

        var firstID = taskCrudService.findAll().get(taskCrudService.findAll().size() - filteredTasks.size()).getId();
        var offset = firstID - taskImportFirstID;

        var updatedLogs = new ArrayList<LogTimeInfo>();
        filteredLogTimeInfos.forEach(log -> updatedLogs.add(
                new LogTimeInfo(
                        log.getLoggedTime(),
                        new User(log.getUsername(),
                        log.getUserId()),
                        log.getTaskID() + offset)));
        return updatedLogs;
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

    private void createLogTimeInfo(LogTimeInfo logTimeInfo) {
        logTimeInfoCrudService.create(logTimeInfo)
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
