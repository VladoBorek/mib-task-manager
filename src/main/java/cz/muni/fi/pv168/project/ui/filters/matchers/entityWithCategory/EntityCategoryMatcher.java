package cz.muni.fi.pv168.project.ui.filters.matchers.entityWithCategory;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.abstracts.TaskBase;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;

public class EntityCategoryMatcher<T extends TaskBase> extends EntityMatcher<T> {
    private final Category category;

    public EntityCategoryMatcher(Category category) {
        this.category = category;
    }

    @Override
    public boolean evaluate(T entity) {
        return entity.getCategory() == category;
    }
}