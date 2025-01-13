package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.entity.CategoryService;
import cz.muni.fi.pv168.project.business.service.entity.TemplateService;
import cz.muni.fi.pv168.project.business.service.entity.TimeUnitService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;

/**
 * @author Vladimir Borek
 * Interface for instance wiring
 */
public interface DependencyProvider {
    DatabaseManager getDatabaseManager();

    TransactionExecutor getTransactionExecutor();

    ImportService getImportService();

    ExportService getExportService();

    TimeUnitService getTimeUnitService();

    CategoryService getCategoryService();

    TemplateService getTemplateService();

    // --- Repositories ---
    Repository<Category> getCategoryRepository();

    Repository<LogTimeInfo> getLogTimeInfoRepository();

    Repository<Task> getTaskRepository();

    Repository<Template> getTemplateRepository();

    Repository<TimeUnit> getTimeUnitRepository();

    // --- CrudServices ---
    CrudService<Category> getCategoryCrudService();

    CrudService<LogTimeInfo> getLogTimeInfoCrudService();

    CrudService<Task> getTaskCrudService();

    CrudService<Template> getTemplateCrudService();

    CrudService<TimeUnit> getTimeUnitCrudService();

    // --- Validators ---
    Validator<Category> getCategoryValidator();

    Validator<LogTimeInfo> getLogTimeInfoValidator();

    Validator<Task> getTaskValidator();

    Validator<Template> getTemplateValidator();

    Validator<TimeUnit> getTimeUnitValidator();
}
