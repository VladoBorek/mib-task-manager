package cz.muni.fi.pv168.project.ui.filters.values;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatcher;
import cz.muni.fi.pv168.project.ui.filters.matchers.EntityMatchers;

import java.util.Objects;

public enum SpecialFilterCategoryValues {
    ALL(EntityMatchers.all());

    private final EntityMatcher<Task> matcher;

    SpecialFilterCategoryValues(EntityMatcher<Task> matcher) {
        this.matcher = Objects.requireNonNull(matcher, "matcher cannot be null");
    }

    public EntityMatcher<Task> getMatcher() {
        return matcher;
    }
}
