package cz.muni.fi.pv168.project.business.service.export.batch;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;

import java.util.Collection;

public record Batch(Collection<Task> tasks,
                    Collection<Category> categories,
                    Collection<Template> templates,
                    Collection<TimeUnit> timeUnits) {
}
