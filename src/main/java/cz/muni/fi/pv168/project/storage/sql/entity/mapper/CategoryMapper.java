package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;

import java.awt.*;

/**
 * Mapper from the {@link CategoryEntity} to {@link Category}.
 *
 * @author Maroš Pavlík
 */
public class CategoryMapper implements EntityMapper<CategoryEntity, Category> {
    @Override
    public Category mapToBusiness(CategoryEntity dbCategory) {
        return new Category(
                dbCategory.id(),
                dbCategory.name(),
                new Color(dbCategory.color())
        );
    }

    @Override
    public CategoryEntity mapNewEntityToDatabase(Category businessCategory) {
        return new CategoryEntity(null, businessCategory.getName(), businessCategory.getColor().getRGB());
    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category businessCategory, Long dbId) {
        return new CategoryEntity(dbId, businessCategory.getName(), businessCategory.getColor().getRGB());
    }
}
