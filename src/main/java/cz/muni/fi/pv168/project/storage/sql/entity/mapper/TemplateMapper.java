package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TemplateEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;
import cz.muni.fi.pv168.project.util.Constants;

/**
 * Mapper from the {@link TemplateEntity} to {@link Template}.
 *
 * @author Maroš Pavlík
 */
public class TemplateMapper implements EntityMapper<TemplateEntity, Template> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper;

    public TemplateMapper(DataAccessObject<CategoryEntity> categoryDao,
                          EntityMapper<CategoryEntity, Category> categoryMapper,
                          DataAccessObject<TimeUnitEntity> timeUnitDao,
                          EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.timeUnitDao = timeUnitDao;
        this.timeUnitMapper = timeUnitMapper;
    }

    @Override
    public Template mapToBusiness(TemplateEntity dbTemplate) {
        var category = categoryDao
                .findById(dbTemplate.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        dbTemplate.categoryId()));

        TimeUnit timeUnit;
        if (dbTemplate.timeUnitId() == null) {
            timeUnit = Constants.BASE_TIME_UNIT;
        } else {
            timeUnit = timeUnitDao
                    .findById(dbTemplate.timeUnitId())
                    .map(timeUnitMapper::mapToBusiness)
                    .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                            dbTemplate.timeUnitId()));
        }

        return new Template(
                dbTemplate.id(),
                dbTemplate.taskName(),
                category,
                dbTemplate.allocatedTime(),
                timeUnit,
                dbTemplate.templateName(),
                dbTemplate.description(),
                dbTemplate.assignedTo()
        );
    }

    @Override
    public TemplateEntity mapNewEntityToDatabase(Template businessTemplate) {
        return mapExistingEntityToDatabase(businessTemplate, businessTemplate.getId());
    }

    @Override
    public TemplateEntity mapExistingEntityToDatabase(Template businessTemplate, Long dbId) {
        var categoryEntity = categoryDao
                .findById(businessTemplate.getCategory().getId())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        businessTemplate.getCategory().getId()));


        Long timeUnitId;
        if (businessTemplate.getTimeUnit().equals(Constants.BASE_TIME_UNIT)) {
            timeUnitId = null;
        } else {
            var timeUnitEntity = timeUnitDao
                    .findById(businessTemplate.getTimeUnit().getId())
                    .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                            businessTemplate.getTimeUnit().getId()));
            timeUnitId = timeUnitEntity.id();
        }

        return new TemplateEntity(
                dbId,
                businessTemplate.getDescription(),
                categoryEntity.id(),
                businessTemplate.getTemplateName(),
                businessTemplate.getName(),
                businessTemplate.getAssignedTo(),
                businessTemplate.getAllocatedTime(),
                timeUnitId
        );
    }
}
