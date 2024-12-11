package cz.muni.fi.pv168.project.export.json.parsers;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import org.json.JSONObject;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;

public class JSONObjectParsers {
    public static Task parseTask(HashMap<String, Category> categories,
                                 HashMap<String, TimeUnit> timeUnits,
                                 HashMap<String, LogTimeInfo> workLogs,
                                 JSONObject object){
        var category = parseCategory(categories,
                object.getJSONObject("category"));

        var timeUnit = parseTimeUnit(timeUnits,
                object.getJSONObject("time_unit"));

        return new Task(
                object.getLong("id"),
                Status.valueOf(object.getString("status")),
                object.getString("description"),
                category,
                object.getString("customer"),
                object.getString("task_name"),
                object.getString("assigned_to"),
                object.getInt("logged_time"),
                object.getInt("allocated_time"),
                timeUnit,
                LocalDate.parse(object.getString("due_date"))
        );
    }

    public static Template parseTemplate(HashMap<String, Template> templates,
                                         HashMap<String, Category> categories,
                                         HashMap<String, TimeUnit> timeUnits,
                                         JSONObject object){
        var category = parseCategory(categories,
                object.getJSONObject("category"));

        var timeUnit = parseTimeUnit(timeUnits,
                object.getJSONObject("time_unit"));

        return parseTemplate(
                templates,
                category,
                timeUnit,
                object.getString("template_name"),
                object.getString("template_task_name"),
                object.getInt("template_allocated_time"),
                object.getString("template_description"),
                object.getString("template_assigned_to")
        );
    }

    public static Category parseCategory(HashMap<String, Category> categories,
                                         JSONObject object){
        return parseCategory(categories,
                object.getString("category_name"),
                object.getInt("category_color")
        );
    }

    public static TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                         JSONObject object){
        return parseTimeUnit(timeUnits,
                object.getString("time_unit_name"),
                object.getString("time_unit_short_name"),
                object.getInt("time_unit_rate")
        );
    }

    public static LogTimeInfo parseWorkLog(HashMap<String, LogTimeInfo> workLogs,
                                           JSONObject object){
        User user = new User(
                object.getString("work_log_user_name"),
                object.getLong("work_log_user_id")
        );
        return parseWorkLog(workLogs,
                object.getInt("work_log_logged_time"),
                user,
                object.getLong("work_log_task_id")
        );
    }


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
    private static Template parseTemplate(HashMap<String, Template> templates,
                                   Category category,
                                   TimeUnit timeUnit,
                                   String templateName,
                                   String taskName,
                                   Integer allocated_time,
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
        return templates.computeIfAbsent(templateNew.toString(), template -> templateNew);
    }

    /**
     * Parses {@link Category} from provided values
     *
     * @param categories Map of {@link Category} from this import
     * @param name       {@link String} name of the category
     * @param color      {@link Color} color of the category
     * @return new {@link Category} with the provided values
     */
    private static Category parseCategory(HashMap<String, Category> categories,
                                   String name, Integer color) {
        var categoryNew = new Category(null, name, new Color(color));
        return categories.computeIfAbsent(categoryNew.toString(), category -> categoryNew);
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
    private static TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                   String name, String shortName, Integer rate) {

        var timeUnitNew = new TimeUnit(null, name, shortName, rate);
        return timeUnits.computeIfAbsent(timeUnitNew.toString(), timeUnit -> timeUnitNew);
    }

    /**
     * Parse  {@link LogTimeInfo} from provided values
     *
     * @param workLogs         Map of {@link LogTimeInfo} from this import
     * @param loggedTime       value of logged time
     * @param user             {@link User} user associated with the {@link LogTimeInfo}
     * @param taskID           ID of associated new {@link Task}
     * @return new {@link TimeUnit} with the provided values
     */
    private static LogTimeInfo parseWorkLog(HashMap<String, LogTimeInfo> workLogs,
                                     Integer loggedTime,
                                     User user,
                                     Long taskID)
    {
        var workLogNew = new LogTimeInfo(loggedTime, user, taskID);
        return workLogs.computeIfAbsent(workLogNew.toString(), log -> workLogNew);
    }
}
