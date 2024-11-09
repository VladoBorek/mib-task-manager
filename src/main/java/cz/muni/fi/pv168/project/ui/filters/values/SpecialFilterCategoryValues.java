package cz.muni.fi.pv168.project.ui.filters.values;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.abstracts.EntityWithCategory;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatchers;

import java.util.Objects;

public enum SpecialFilterCategoryValues {
    ALL(EntityMatchers.all());

    private final EntityMatcher<? extends EntityWithCategory> matcher;

    SpecialFilterCategoryValues(EntityMatcher<? extends EntityWithCategory> matcher) {
        this.matcher = Objects.requireNonNull(matcher, "matcher cannot be null");
    }

    public EntityMatcher<? extends EntityWithCategory> getMatcher() {
        return matcher;
    }
}
