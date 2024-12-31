package cz.muni.fi.pv168.project.ui.filters.values;

import cz.muni.fi.pv168.project.business.model.abstracts.WorkItem;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatchers;

import java.util.Objects;

public enum SpecialFilterCategoryValues {
    ALL(EntityMatchers.all());

    private final EntityMatcher<? extends WorkItem> matcher;

    SpecialFilterCategoryValues(EntityMatcher<? extends WorkItem> matcher) {
        this.matcher = Objects.requireNonNull(matcher, "matcher cannot be null");
    }

    public EntityMatcher<? extends WorkItem> getMatcher() {
        return matcher;
    }
}
