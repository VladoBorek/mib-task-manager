package cz.muni.fi.pv168.project.export.json.parsers;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JSONArrayParsers {
    public static void importTaskArray(JSONArray taskArray,
                                       HashMap<String, Task> tasks,
                                       HashMap<String, Category> categories,
                                       HashMap<String, TimeUnit> timeUnits) {
        for (int i = 0; i < taskArray.length(); i++) {
            var task = JSONObjectParsers.parseTask(categories, timeUnits, taskArray.getJSONObject(i));
            tasks.put(task.toString(), task);
        }
    }

    public static void importTemplateArray(JSONArray templateArray,
                                           HashMap<String, Template> templates,
                                           HashMap<String, Category> categories,
                                           HashMap<String, TimeUnit> timeUnits) {
        for (int i = 0; i < templateArray.length(); i++) {
            var template = JSONObjectParsers.parseTemplate(templates, categories, timeUnits, templateArray.getJSONObject(i));
            templates.put(template.getTemplateName(), template);
        }
    }

    public static void importCategoryArray(JSONArray categoryArray,
                                           HashMap<String, Category> categories) {
        for (int i = 0; i < categoryArray.length(); i++) {
            var category = JSONObjectParsers.parseCategory(categories, categoryArray.getJSONObject(i));
            categories.put(category.getName(), category);
        }
    }

    public static void importTimeUnitArray(JSONArray timeUnitArray,
                                           HashMap<String, TimeUnit> timeUnits) {
        for (int i = 0; i < timeUnitArray.length(); i++) {
            var timeUnit = JSONObjectParsers.parseTimeUnit(timeUnits, timeUnitArray.getJSONObject(i));
            timeUnits.put(timeUnit.getName(), timeUnit);
        }
    }

    public static List<LogTimeInfo> importWorkLogs(JSONArray workLogArray,
                                                   Task task,
                                                   HashMap<String, TimeUnit> timeUnits) {
        var workLogList = new ArrayList<LogTimeInfo>();
        for (int i = 0; i < workLogArray.length(); i++) {
            var workLog = JSONObjectParsers.parseWorkLog(timeUnits, task, workLogArray.getJSONObject(i));
            workLogList.add(workLog);
        }
        return workLogList;
    }
}
