package cz.muni.fi.pv168.project.export.json;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.util.ActionType;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for importing items from JSON files
 *
 * @author Nikol Otáhalů
 */
public class BatchJSONImporter implements BatchImporter {

    private static final Format FORMAT = new Format("JSON", List.of("json"));
    private static final String TAB = "    ";

    @Override
    public Batch importBatch(String filePath, ActionType type, Batch currentData) {
        var tasks = new HashMap<String, Task>();
        var categories = new HashMap<String, Category>();
        var templates = new HashMap<String, Template>();
        var timeUnits = new HashMap<String, TimeUnit>();
        var workLogs = new HashMap<String, LogTimeInfo>();

        currentData.categories().forEach(category -> categories.put(category.getName(), category));
        currentData.timeUnits().forEach(timeUnit -> timeUnits.put(timeUnit.getName(), timeUnit));
        currentData.logTimeInfos().forEach(workLog -> workLogs.put(workLog.toString(), workLog));

        try (var reader = Files.newBufferedReader(Path.of(filePath))) {
            var stringArrayList = readFile(reader);
            var imported = getImportedItems(stringArrayList);

            for (var item : imported) {
                switch (type) {
                    case TASK ->
                            tasks.put(item.toString(),parseTask(categories, timeUnits, workLogs,item, currentData.tasks().size()));
                    case CATEGORY -> categories.put(item.toString(), parseCategory(categories, item));
                    case TEMPLATE ->
                            templates.put(item.toString(),parseTemplate(templates, categories, timeUnits, item));
                    case TIME_UNIT -> timeUnits.put(item.toString(),parseTimeUnit(timeUnits, item));
                    case WORK_LOG -> workLogs.put(item.toString(),parseWorkLog(workLogs, item, null, 0));
                }
            }
            return new Batch(tasks.values(), categories.values(), templates.values(), timeUnits.values(), workLogs.values());
        } catch (IOException e) {
            throw new DataManipulationException("Unable to read file\n" + e.getMessage());
        } catch (DataManipulationException e) {
            throw new DataManipulationException("Failed to import items\n" + e.getMessage());
        } catch (Exception e) {
        throw new DataManipulationException("Failed to process items\n" + e.getMessage());
    }
    }

    /**
     * Reads the file with provided reader and stores the values into ArrayList
     *
     * @param reader provided file reader
     * @return ArrayList containing separated imported items in Strings
     * @throws IOException if something goes wrong with reader
     */
    private static ArrayList<String> readFile(BufferedReader reader) throws IOException {
        var importStringArray = new ArrayList<String>();
        StringBuilder singleStringImportObject = new StringBuilder();
        String line;
        int lineIndex = 0;
        String lastLine = null;
        boolean addChar = false;
        while ((line = reader.readLine()) != null) {
            if (lineIndex == 0 && !line.equals("[")){
                throw new DataManipulationException("First line of imported file not in required format");
            }
            if (line.contains("}")) {
                if (line.chars().filter(c -> c == '}').count() != 1){
                    throw new DataManipulationException("Line in file missing EOL, line index (starting with 0) " + lineIndex);
                }
                singleStringImportObject.append("\n");
                importStringArray.add(singleStringImportObject.toString());
                singleStringImportObject.setLength(0);
                addChar = false;
            }
            if (addChar) {
                if (!line.contains(":")){
                    throw new DataManipulationException("Line in file missing separator (:), line index (starting with 0) " + lineIndex);
                }
                singleStringImportObject.append(line);
            }
            if (line.contains("{")) {
                if (line.chars().filter(c -> c == '{').count() != 1){
                    throw new DataManipulationException("Line in file missing EOL, line index (starting with 0) " + lineIndex);
                }
                addChar = true;
            }
            if (!addChar && !(line.contains("{") || line.contains("}") ||
                line.contains("[") || line.contains("]"))){
                throw new DataManipulationException("Line in file not in required format, line index (starting with 0) " + lineIndex);
            }
            lineIndex++;
            lastLine = line;
        }

        if (lastLine == null || !lastLine.equals("]")){
            throw new DataManipulationException("Last line of imported file not in required format");
        }
        return importStringArray;
    }

    /**
     * Returns {@link HashMap} with values for one imported item
     *
     * @param line string containing one imported item
     * @return {@link HashMap} with K,V of attribute and its value
     */
    private HashMap<String, Object> getImportedItem(String line) {
        HashMap<String, Object> hashMap = new HashMap<>();
        var attributesAndValues = line.split("," + TAB + TAB);
        for (var att : attributesAndValues) {
            var split = att.split(":");
            split[0] = split[0].replace('\"', ' ');
            split[1] = split[1].replace('\"', ' ');
            hashMap.put(split[0].trim(), split[1].trim());
        }
        return hashMap;
    }

    /**
     * Turns arrayList of {@link String} into {@link ArrayList} of {@link HashMap}
     * by applying {@link #getImportedItem(String)} to each item
     *
     * @param items Items to be turned into {@link HashMap} items
     * @return {@link ArrayList} with {@link HashMap} for imported objects
     */
    private ArrayList<HashMap<String, Object>> getImportedItems(ArrayList<String> items) {
        var arrayList = new ArrayList<HashMap<String, Object>>();
        for (var item : items) {
            arrayList.add(getImportedItem(item));
        }
        return arrayList;
    }

    /**
     * Parses {@link Task} from provided values
     *
     * @param categories   Map of {@link Category} from this import
     * @param timeUnits    Map of {@link TimeUnit} from this import
     * @param workLogs     Map of {@link LogTimeInfo} from this import
     * @param values       Values for the parsing Task
     * @param taskIDOffset offset of already stored tasks, so new {@link LogTimeInfo} is paired with correct Task
     * @return {@link Task} with provided values
     */
    private Task parseTask(HashMap<String, Category> categories,
                           HashMap<String, TimeUnit> timeUnits,
                           HashMap<String, LogTimeInfo> workLogs,
                           HashMap<String, Object> values,
                           int taskIDOffset) {
        var category = parseCategory(categories,
                (String) values.get("category_name"),
                Integer.parseInt((String) values.get("category_color")));

        var timeUnit = parseTimeUnit(timeUnits,
                (String) values.get("time_unit_name"),
                (String) values.get("time_unit_short_name"),
                Integer.parseInt((String) values.get("time_unit_rate")));

        var workLogCount =  Integer.parseInt((String) values.get("work_logs_count"));
        for (int i = 0; i < workLogCount; i++) {
            parseWorkLog(workLogs, values, i, taskIDOffset);
        }
        return new Task(
                Long.parseLong((String) values.get("id")),
                Status.valueOf((String) values.get("status")),
                (String) values.get("description"),
                category,
                (String) values.get("customer"),
                (String) values.get("task_name"),
                (String) values.get("assigned_to"),
                Integer.parseInt((String) values.get("logged_time")),
                Integer.parseInt((String) values.get("allocated_time")),
                timeUnit,
                LocalDate.parse((String) values.get("due_date"))
        );
    }

    /**
     * Parses {@link Category} from provided values
     *
     * @param categories Map of {@link Category} from this import
     * @param name       {@link String} name of the category
     * @param color      {@link Color} color of the category
     * @return new {@link Category} with the provided values
     */
    private Category parseCategory(HashMap<String, Category> categories,
                                   String name, Integer color) {
        return categories.computeIfAbsent(name, category -> new Category(null, name, new Color(color)));
    }

    /**
     * Parses {@link Category} from provided values
     *
     * @param categories Map of {@link Category} from this import
     * @param values     Map containing values for the category
     * @return new {@link Category} with the provided values
     */
    private Category parseCategory(HashMap<String, Category> categories,
                                   HashMap<String, Object> values) {
        return parseCategory(categories,
                (String) values.get("category_name"),
                Integer.parseInt((String) values.get("category_color")));
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
    private Template parseTemplate(HashMap<String, Template> templates,
                                   Category category,
                                   TimeUnit timeUnit,
                                   String templateName,
                                   String taskName,
                                   Integer allocated_time,
                                   String description,
                                   String assignedTo) {
        return templates.computeIfAbsent(templateName,
                template -> new Template(null,
                        taskName,
                        category,
                        allocated_time,
                        timeUnit,
                        templateName,
                        description,
                        assignedTo
                ));
    }

    /**
     * Parses {@link Template} from provided values
     *
     * @param templates  Map of {@link Template} from this import
     * @param categories Map of {@link Category} from this import
     * @param timeUnits  Map of {@link TimeUnit} from this import
     * @param values     Map containing values for the Template
     * @return new {@link Template} with the provided values
     */
    private Template parseTemplate(HashMap<String, Template> templates,
                                   HashMap<String, Category> categories,
                                   HashMap<String, TimeUnit> timeUnits,
                                   HashMap<String, Object> values) {
        var category = parseCategory(categories,
                (String) values.get("category_name"),
                Integer.parseInt((String) values.get("category_color")));

        var timeUnit = parseTimeUnit(timeUnits,
                (String) values.get("time_unit_name"),
                (String) values.get("time_unit_short_name"),
                Integer.parseInt((String) values.get("time_unit_rate")));

        return parseTemplate(
                templates,
                category,
                timeUnit,
                (String) values.get("template_name"),
                (String) values.get("template_task_name"),
                Integer.parseInt((String) values.get("template_allocated_time")),
                (String) values.get("template_description"),
                (String) values.get("template_assigned_to"));
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
    private TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                   String name, String shortName, Integer rate) {
        return timeUnits.computeIfAbsent(name,
                timeUnit -> new TimeUnit(null, name, shortName, rate));
    }

    /**
     * Parses {@link TimeUnit} from provided values
     *
     * @param timeUnits Map of {@link TimeUnit} from this import
     * @param values    Map containing values for the time unit
     * @return new {@link TimeUnit} with the provided values
     */
    private TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                   HashMap<String, Object> values) {

        return parseTimeUnit(timeUnits,
                (String) values.get("time_unit_name"),
                (String) values.get("time_unit_short_name"),
                Integer.parseInt((String) values.get("time_unit_rate")));
    }

    /**
     * Parse  {@link LogTimeInfo} from provided values
     *
     * @param workLogs     Map of {@link LogTimeInfo} from this import
     * @param loggedTime   value of logged time
     * @param user         {@link User} user associated with the {@link LogTimeInfo}
     * @param taskID       ID of associated new {@link Task}
     * @param taskIDOffset offset of already stored tasks, so new {@link LogTimeInfo} is paired with correct Task
     * @return new {@link TimeUnit} with the provided values
     */
    private LogTimeInfo parseWorkLog(HashMap<String, LogTimeInfo> workLogs,
                                     Integer loggedTime,
                                     User user,
                                     Long taskID,
                                     int taskIDOffset,
                                     String importedToString)
    {
        LogTimeInfo logTimeInfo = new LogTimeInfo(loggedTime, user, taskID + taskIDOffset);
        return workLogs.computeIfAbsent(importedToString, log -> logTimeInfo);
    }

    /**
     * Parse {@link LogTimeInfo} from provided values
     *
     * @param workLogs     Map of {@link LogTimeInfo} from this import
     * @param values       Map containing values for the time unit
     * @param order        order of LogTimeInfo when exporting inside of {@link #parseTask}
     * @param taskIDOffset offset of already stored tasks, so new {@link LogTimeInfo} is paired with correct Task
     * @return new {@link TimeUnit} with the provided values
     */
    private LogTimeInfo parseWorkLog(HashMap<String, LogTimeInfo> workLogs,
                                     HashMap<String, Object> values,
                                     Integer order,
                                     int taskIDOffset)
    {
        String stringOrder;
        if (order == null){
            stringOrder = "";
        } else {
            stringOrder = order.toString();
        }
        User user = new User(
                (String) values.get("work_log_user_name" + stringOrder),
                Long.parseLong((String) values.get("work_log_user_id" + stringOrder)));

        return parseWorkLog(workLogs,
                Integer.parseInt((String) values.get("work_log_logged_time" + stringOrder)),
                user,
                Long.parseLong((String) values.get("work_log_task_id" + stringOrder)),
                taskIDOffset,
                values.toString()
        );
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
