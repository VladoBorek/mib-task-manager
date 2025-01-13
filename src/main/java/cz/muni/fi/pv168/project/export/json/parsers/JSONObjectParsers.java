package cz.muni.fi.pv168.project.export.json.parsers;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.export.GenericParsers;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;

public class JSONObjectParsers {
    public static Task parseTask(HashMap<String, Category> categories,
                                 HashMap<String, TimeUnit> timeUnits,
                                 JSONObject object) {
        var category = parseCategory(categories,
                object.getJSONObject("category"));

        var timeUnit = parseTimeUnit(timeUnits,
                object.getJSONObject("time_unit"));

        var task = new Task(
                object.getLong("id"),
                Status.valueOf(object.getString("status")),
                object.getString("description"),
                category,
                object.getString("customer"),
                object.getString("task_name"),
                object.getString("assigned_to"),
                object.getDouble("logged_time"),
                object.getDouble("allocated_time"),
                timeUnit,
                LocalDate.parse(object.getString("due_date"))
        );

        var workLogList = JSONArrayParsers.importWorkLogs(object.getJSONArray("work_logs"), task, timeUnits);
        workLogList.forEach(task::addLog);
        return task;
    }

    public static Template parseTemplate(HashMap<String, Template> templates,
                                         HashMap<String, Category> categories,
                                         HashMap<String, TimeUnit> timeUnits,
                                         JSONObject object) {
        var category = parseCategory(categories,
                object.getJSONObject("category"));

        var timeUnit = parseTimeUnit(timeUnits,
                object.getJSONObject("time_unit"));

        return GenericParsers.parseTemplate(
                templates,
                category,
                timeUnit,
                object.getString("template_name"),
                object.getString("template_task_name"),
                object.getDouble("template_allocated_time"),
                object.getString("template_description"),
                object.getString("template_assigned_to")
        );
    }

    public static Category parseCategory(HashMap<String, Category> categories,
                                         JSONObject object) {
        return GenericParsers.parseCategory(categories,
                object.getString("category_name"),
                object.getInt("category_color")
        );
    }

    public static TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                         JSONObject object) {
        return GenericParsers.parseTimeUnit(timeUnits,
                object.getString("time_unit_name"),
                object.getString("time_unit_short_name"),
                object.getInt("time_unit_rate")
        );
    }

    public static LogTimeInfo parseWorkLog(HashMap<String, TimeUnit> timeUnits,
                                           Task task,
                                           JSONObject object) {
        User user = new User(
                object.getString("work_log_user_name"),
                object.getLong("work_log_user_id")
        );
        var timeUnit = parseTimeUnit(timeUnits,
                object.getJSONObject("time_unit"));
        return GenericParsers.parseWorkLog(
                object.getDouble("work_log_logged_time"),
                user,
                task,
                timeUnit
        );
    }
}
