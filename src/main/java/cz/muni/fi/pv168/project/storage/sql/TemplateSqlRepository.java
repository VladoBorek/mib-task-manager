package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.TemplateEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.TemplateMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladimir Borek
 */
public class TemplateSqlRepository implements Repository<Template> {
    private final DataAccessObject<TemplateEntity> templateDao;
    private final TemplateMapper templateMapper;

    public TemplateSqlRepository(DataAccessObject<TemplateEntity> templateDao,
                                 TemplateMapper templateMapper) {
        this.templateDao = templateDao;
        this.templateMapper = templateMapper;
    }

    @Override
    public List<Template> findAll() {
        return templateDao
                .findAll()
                .stream()
                .map(templateMapper::mapToBusiness)
                .toList();
    }

    @Override
    public Template create(Template newEntity) {
        return templateMapper.mapToBusiness(templateDao.create(templateMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(Template entity) {
        TemplateEntity existingEntity = templateDao.findById(entity.getId())
                .orElseThrow(() -> new DataStorageException("Template not found, id: " + entity.getId()));
        TemplateEntity updatedEntity = templateMapper.mapExistingEntityToDatabase(entity, existingEntity.id());

        templateDao.update(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        templateDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        templateDao.deleteAll();
    }

    @Override
    public Optional<Template> findById(Long id) {
        return templateDao.
                findById(id)
                .map(templateMapper::mapToBusiness);
    }
}
