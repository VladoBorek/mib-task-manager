package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.entity.CategoryService;
import cz.muni.fi.pv168.project.business.service.entity.TemplateService;
import cz.muni.fi.pv168.project.business.service.entity.TimeUnitService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericImportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.LogTimeInfoValidator;
import cz.muni.fi.pv168.project.business.service.validation.TaskValidator;
import cz.muni.fi.pv168.project.business.service.validation.TemplateValidator;
import cz.muni.fi.pv168.project.business.service.validation.TimeUnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.export.json.BatchJSONExporter;
import cz.muni.fi.pv168.project.export.json.BatchJSONImporter;
import cz.muni.fi.pv168.project.storage.sql.CategorySqlRepository;
import cz.muni.fi.pv168.project.storage.sql.LogTimeInfoSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.TaskSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.TemplateSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.TimeUnitSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.TransactionalImportService;
import cz.muni.fi.pv168.project.storage.sql.dao.CategoryDao;
import cz.muni.fi.pv168.project.storage.sql.dao.LogTimeInfoDao;
import cz.muni.fi.pv168.project.storage.sql.dao.TaskDao;
import cz.muni.fi.pv168.project.storage.sql.dao.TemplateDao;
import cz.muni.fi.pv168.project.storage.sql.dao.TimeUnitDao;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionConnectionSupplier;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutorImpl;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionManagerImpl;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.CategoryMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.LogTimeInfoMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.TaskMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.TemplateMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.TimeUnitMapper;

import java.util.List;


/**
 * @author Vladimir Borek
 */
public class CommonDependencyProvider implements DependencyProvider {
    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final ImportService importService;
    private final ExportService exportService;

    private final Repository<Category> categories;
    private final Repository<LogTimeInfo> logTimeInfos;
    private final Repository<Task> tasks;
    private final Repository<Template> templates;
    private final Repository<TimeUnit> timeUnits;

    private final CrudService<Category> categoryCrudService;
    private final CrudService<LogTimeInfo> logTimeInfoCrudService;
    private final CrudService<Task> taskCrudService;
    private final CrudService<Template> templateCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;

    private final Validator<Category> categoryValidator = new CategoryValidator(this);
    private final Validator<LogTimeInfo> logTimeInfoValidator = new LogTimeInfoValidator();
    private final Validator<Task> taskValidator = new TaskValidator();
    private final Validator<Template> templateValidator = new TemplateValidator(this);
    private final Validator<TimeUnit> timeUnitValidator = new TimeUnitValidator(this);


    CommonDependencyProvider(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;

        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        // SETUP REPOSITORIES
        var categoryMapper = new CategoryMapper();
        var categoryDao = new CategoryDao(transactionConnectionSupplier);

        var timeUnitMapper = new TimeUnitMapper();
        var timeUnitDao = new TimeUnitDao(transactionConnectionSupplier);

        var logTimeInfoMapper = new LogTimeInfoMapper(timeUnitDao, timeUnitMapper);
        var logTimeInfoDao = new LogTimeInfoDao(transactionConnectionSupplier);

        var taskMapper = new TaskMapper(categoryDao, categoryMapper, timeUnitDao, timeUnitMapper, logTimeInfoDao, logTimeInfoMapper);
        var taskDao = new TaskDao(transactionConnectionSupplier);

        var templateMapper = new TemplateMapper(categoryDao, categoryMapper, timeUnitDao, timeUnitMapper);
        var templateDao = new TemplateDao(transactionConnectionSupplier);

        this.categories = new CategorySqlRepository(categoryDao, categoryMapper);
        this.logTimeInfos = new LogTimeInfoSqlRepository(logTimeInfoDao, logTimeInfoMapper);
        this.tasks = new TaskSqlRepository(taskDao, taskMapper);
        this.templates = new TemplateSqlRepository(templateDao, templateMapper);
        this.timeUnits = new TimeUnitSqlRepository(timeUnitDao, timeUnitMapper);

        // SETUP CRUD SERVICES
        this.categoryCrudService = new BaseCrudService<>(this.categories, this.categoryValidator);
        this.logTimeInfoCrudService = new BaseCrudService<>(this.logTimeInfos, this.logTimeInfoValidator);
        this.taskCrudService = new BaseCrudService<>(this.tasks, this.taskValidator);
        this.templateCrudService = new BaseCrudService<>(this.templates, this.templateValidator);
        this.timeUnitCrudService = new BaseCrudService<>(this.timeUnits, this.timeUnitValidator);

        // SETUP IMPORT EXPORT SERVICES
        this.exportService = new GenericExportService(taskCrudService, categoryCrudService, templateCrudService,
                timeUnitCrudService, List.of(new BatchJSONExporter()));
        var genericImportService = new GenericImportService(taskCrudService, categoryCrudService, templateCrudService,
                timeUnitCrudService, logTimeInfoCrudService, List.of(new BatchJSONImporter()));
        this.importService = new TransactionalImportService(genericImportService, transactionExecutor);
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return this.transactionExecutor;
    }

    @Override
    public ImportService getImportService() {
        return this.importService;
    }

    @Override
    public ExportService getExportService() {
        return this.exportService;
    }

    @Override
    public TimeUnitService getTimeUnitService() {
        return new TimeUnitService(this);
    }

    @Override
    public CategoryService getCategoryService() {
        return new CategoryService(this);
    }

    @Override
    public TemplateService getTemplateService() {
        return new TemplateService(this);
    }

    @Override
    public Repository<Category> getCategoryRepository() {
        return this.categories;
    }

    @Override
    public Repository<LogTimeInfo> getLogTimeInfoRepository() {
        return this.logTimeInfos;
    }

    @Override
    public Repository<Task> getTaskRepository() {
        return this.tasks;
    }

    @Override
    public Repository<Template> getTemplateRepository() {
        return this.templates;
    }

    @Override
    public Repository<TimeUnit> getTimeUnitRepository() {
        return this.timeUnits;
    }

    @Override
    public CrudService<Category> getCategoryCrudService() {
        return this.categoryCrudService;
    }

    @Override
    public CrudService<LogTimeInfo> getLogTimeInfoCrudService() {
        return this.logTimeInfoCrudService;
    }

    @Override
    public CrudService<Task> getTaskCrudService() {
        return this.taskCrudService;
    }

    @Override
    public CrudService<Template> getTemplateCrudService() {
        return this.templateCrudService;
    }

    @Override
    public CrudService<TimeUnit> getTimeUnitCrudService() {
        return this.timeUnitCrudService;
    }

    @Override
    public Validator<Category> getCategoryValidator() {
        return this.categoryValidator;
    }

    @Override
    public Validator<LogTimeInfo> getLogTimeInfoValidator() {
        return this.logTimeInfoValidator;
    }

    @Override
    public Validator<Task> getTaskValidator() {
        return this.taskValidator;
    }

    @Override
    public Validator<Template> getTemplateValidator() {
        return this.templateValidator;
    }

    @Override
    public Validator<TimeUnit> getTimeUnitValidator() {
        return this.timeUnitValidator;
    }

}
