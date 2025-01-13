package cz.muni.fi.pv168.project.export;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.util.ColorService;

import java.awt.*;
import java.util.HashMap;

public class GenericParsers {

    /**
     * Parses {@link Template} from provided values
     *
     * @param templates      Map of {@link Template} from this import
     * @param category       {@link Category} of the template
     * @param timeUnit       {@link TimeUnit} of the template
     * @param templateName   {@link String} name of the template
     * @param taskName       {@link String} default name for {@link Task} created with this template
     * @param allocated_time {@link Integer} default allocated time of the template
     * @param description    {@link String} description for the template
     * @param assignedTo     {@link String} assigned person for the template
     * @return new {@link Template} with the provided values
     */
    public static Template parseTemplate(HashMap<String, Template> templates,
                                         Category category,
                                         TimeUnit timeUnit,
                                         String templateName,
                                         String taskName,
                                         Double allocated_time,
                                         String description,
                                         String assignedTo) {
        var templateNew = new Template(null,
                taskName,
                category,
                allocated_time,
                timeUnit,
                templateName,
                description,
                assignedTo
        );
        return templates.computeIfAbsent(templateNew.getTemplateName(), template -> templateNew);
    }

    /**
     * Parses {@link Category} from provided values
     *
     * @param categories Map of {@link Category} from this import
     * @param name       {@link String} name of the category
     * @param color      {@link Color} color of the category
     * @return new {@link Category} with the provided values
     */
    public static Category parseCategory(HashMap<String, Category> categories,
                                         String name, Integer color) {
        var categoryNew = new Category(null, name, ColorService.customColorFromAWTColor(new Color(color)));
        return categories.computeIfAbsent(categoryNew.getName(), category -> categoryNew);
    }

    /**
     * Parses {@link TimeUnit} from provided values
     *
     * @param timeUnits Map of {@link TimeUnit} from this import
     * @param name      {@link String} name of the time unit
     * @param shortName {@link String} short name of the time unit
     * @param rate      {@link Integer} conversion rate of time unit to {@link TimeUnit}
     * @return new {@link TimeUnit} with the provided values
     */
    public static TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                         String name, String shortName, Integer rate) {

        var timeUnitNew = new TimeUnit(null, name, shortName, rate);
        return timeUnits.computeIfAbsent(timeUnitNew.getName(), timeUnit -> timeUnitNew);
    }

    /**
     * Parse  {@link LogTimeInfo} from provided values
     *
     * @param loggedTime value of logged time
     * @param user       {@link User} user associated with the {@link LogTimeInfo}
     * @param task       {@link Task} associated with the {@link LogTimeInfo}
     * @return new {@link TimeUnit} with the provided values
     */
    public static LogTimeInfo parseWorkLog(Double loggedTime,
                                           User user,
                                           Task task,
                                           TimeUnit timeUnit) {
        return new LogTimeInfo(loggedTime, user, task.getId(), timeUnit);
    }
}
