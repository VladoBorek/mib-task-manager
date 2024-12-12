package cz.muni.fi.pv168.project.export.json.exporters;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class JSONExporters {

    public static JSONObject taskObject(Entity entity){
        var task = (Task) entity;
        var object = new JSONObject();
        object.put("id", task.getId());
        object.put("status", Status.valueOf(task.getStatus().toString()));
        object.put("description", task.getDescription());
        object.put("customer", task.getCustomer());
        object.put("task_name", task.getName());
        object.put("assigned_to", task.getAssignedTo());
        object.put("logged_time", task.getLoggedTime());
        object.put("allocated_time", task.getAllocatedTime());
        object.put("due_date", task.getDueDate());
        object.put("category", categoryObject(task.getCategory()));
        object.put("time_unit", timeUnitObject(task.getTimeUnit()));
        //TODO uncomment this when Task has list of WorkLogs, to include worLogs export
        //object.put("work_logs", workLogsArray(task.getWorkLogs()));
        return object;
    }
    public static JSONObject categoryObject(Entity entity){
        var category = (Category) entity;
        var object = new JSONObject();
        object.put("category_name", category.getName());
        object.put("category_color", category.getColor().getRGB());
        return object;
    }
    public static JSONObject templateObject(Entity entity){
        var template = (Template) entity;
        var object = new JSONObject();
        object.put("template_name", template.getTemplateName());
        object.put("template_assigned_to", template.getAssignedTo());
        object.put("template_allocated_time", template.getAllocatedTime());
        object.put("template_task_name", template.getName());
        object.put("template_description", template.getDescription());
        object.put("category", categoryObject(template.getCategory()));
        object.put("time_unit", timeUnitObject(template.getTimeUnit()));
        return object;
    }
    public static JSONObject timeUnitObject(Entity entity){
        var timeUnit = (TimeUnit) entity;
        var object = new JSONObject();
        object.put("time_unit_name", timeUnit.getName());
        object.put("time_unit_short_name", timeUnit.getShortName());
        object.put("time_unit_rate", timeUnit.getRate());
        return object;
    }
    public static JSONObject workLogObject(Entity entity){
        var workLog = (LogTimeInfo) entity;
        var object = new JSONObject();
        object.put("work_log_user_name", workLog.getUsername());
        object.put("work_log_user_id", workLog.getUserId());
        object.put("work_log_logged_time", workLog.getLoggedTime());
        return object;
    }

    public static JSONArray workLogArray(List<LogTimeInfo> workLogs){
        var array = new JSONArray();
        workLogs.forEach(workLog -> array.put(workLogObject(workLog)));
        return array;
    }

}
