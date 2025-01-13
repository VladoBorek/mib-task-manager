package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.util.ColorService;

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
                ColorService.customColorFromAWTColor(new Color(dbCategory.color()))
        );
    }

    @Override
    public CategoryEntity mapNewEntityToDatabase(Category businessCategory) {
        return new CategoryEntity(null, businessCategory.getName(), ColorService.customColorToAWTColor(businessCategory.getColor()).getRGB());
    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category businessCategory, Long dbId) {
        return new CategoryEntity(dbId, businessCategory.getName(), ColorService.customColorToAWTColor(businessCategory.getColor()).getRGB());
    }
}
