package cz.muni.fi.pv168.project.business.model.abstracts;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.TimeUnit;

public abstract class EntityWithCategory extends Entity {
    private Category category;

    public EntityWithCategory(Long id, Category category) {
        super(id);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
