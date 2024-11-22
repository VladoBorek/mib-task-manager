package cz.muni.fi.pv168.project.export.json;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;

import java.awt.*;
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
 * @author Nikol Otáhalů
 */
public class BatchJSONImporter implements BatchImporter {

    private static final Format FORMAT = new Format("JSON", List.of("json"));
    private static  final String TAB = "    ";
    @Override
    public Batch importBatch(String filePath, ActionType type, Batch currentData) {
        var tasks = new HashMap<String, Task>();
        var categories = new HashMap<String, Category>();
        var templates = new HashMap<String, Template>();
        var timeUnits = new HashMap<String, TimeUnit>();

        currentData.categories().forEach(category -> categories.put(category.getName(), category));
        currentData.timeUnits().forEach(timeUnit -> timeUnits.put(timeUnit.getName(), timeUnit));

        try(var reader = Files.newBufferedReader(Path.of(filePath))) {
            var stringArrayList = readFile(reader);
            var imported = getImportedItems(stringArrayList);

            for (var item: imported) {
                switch (type){
                    case TASK -> tasks.put(item.toString(),parseTask(categories, timeUnits, item));
                    case CATEGORY -> categories.put(item.toString(), parseCategory(categories, item));
                    case TEMPLATE -> templates.put(item.toString(),parseTemplate(templates, categories, timeUnits, item));
                    case TIME_UNIT -> timeUnits.put(item.toString(),parseTimeUnit(timeUnits, item));
                }
            }
            return new Batch(tasks.values(), categories.values(), templates.values(), timeUnits.values());
        } catch (IOException e) {
            throw new DataManipulationException("Unable to read file", e);
        }
    }

    /**
     * Reads the file with provided reader and stores the values into ArrayList
     * @param reader provided file reader
     * @return ArrayList containing separated imported items in Strings
     * @throws IOException if something goes wrong with reader
     */
    private static ArrayList<String> readFile(BufferedReader reader) throws IOException {
        var array = new ArrayList<String>();
        StringBuilder fileContent = new StringBuilder();
        String line;
        boolean addChar = false;
        while ((line = reader.readLine()) != null) {
            if (line.contains("}")){
                fileContent.append("\n");
                array.add(fileContent.toString());
                fileContent.setLength(0);
                addChar = false;
            }
            if (addChar){
                fileContent.append(line);
            }
            if (line.contains("{")){
                addChar = true;
            }
        }
        return array;
    }

    /**
     * Returns {@link HashMap} with values for one imported item
     * @param line string containing one imported item
     * @return {@link HashMap} with K,V of attribute and its value
     */
    private HashMap<String, Object> getImportedItem(String line)
    {
        HashMap<String, Object> hashMap = new HashMap<>();
        var attributesAndValues = line.split(","+ TAB + TAB);
        for (var att: attributesAndValues) {
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
     * @param items Items to be turned into {@link HashMap} items
     * @return {@link ArrayList} with {@link HashMap} for imported objects
     */
    private ArrayList<HashMap<String, Object>> getImportedItems(ArrayList<String> items)
    {
        var arrayList = new ArrayList<HashMap<String, Object>>();
        for (var item:items) {
            arrayList.add(getImportedItem(item));
        }
        return arrayList;
    }

    /**
     * Parses {@link Task} from provided values
     * @param categories Map of {@link Category} from this import
     * @param timeUnits Map of {@link TimeUnit} from this import
     * @param values Values for the parsing Task
     * @return {@link Task} with provided values
     */
    private Task parseTask(HashMap<String, Category>categories,
                           HashMap<String, TimeUnit> timeUnits,
                           HashMap<String, Object> values){
        var category = parseCategory(categories,
                (String) values.get("category_name"),
                Integer.parseInt((String) values.get("category_color")));

        var timeUnit = parseTimeUnit(timeUnits,
                (String) values.get("time_unit_name"),
                (String) values.get("time_unit_short_name"),
                Integer.parseInt((String) values.get("time_unit_rate")));
        //TODO add importing log time table

        return new Task(null,
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
     * @param categories Map of {@link Category} from this import
     * @param name {@link String} name of the category
     * @param color {@link Color} color of the category
     * @return new {@link Category} with the provided values
     */
    private Category parseCategory(HashMap<String, Category>categories,
                                   String name, Integer color){
        return categories.computeIfAbsent(name, category -> new Category(null, name, new Color(color)));
    }

    /**
     * Parses {@link Category} from provided values
     * @param categories Map of {@link Category} from this import
     * @param values Map containing values for the category
     * @return new {@link Category} with the provided values
     */
    private Category parseCategory(HashMap<String, Category>categories,
                                   HashMap<String, Object> values){
        return parseCategory(categories,
                    (String) values.get("category_name"),
                    Integer.parseInt((String) values.get("category_color")));
    }

    /**
     * Parses {@link Template} from provided values
     * @param templates Map of {@link Template} from this import
     * @param category {@link Category} of the template
     * @param timeUnit {@link TimeUnit} of the template
     * @param templateName {@link String} name of the template
     * @param taskName {@link String} default name for {@link Task} created with this template
     * @param allocated_time {@link Integer} default allocated time of the template
     * @return new {@link Template} with the provided values
     */
    private  Template parseTemplate(HashMap<String, Template>templates,
                                    Category category,
                                    TimeUnit timeUnit,
                                    String templateName,
                                    String taskName,
                                    Integer allocated_time)
    {
        return templates.computeIfAbsent(templateName,
                template -> new Template(null,
                        taskName,
                        category,
                        allocated_time,
                        timeUnit,
                        templateName));
    }

    /**
     * Parses {@link Template} from provided values
     * @param templates Map of {@link Template} from this import
     * @param categories Map of {@link Category} from this import
     * @param timeUnits Map of {@link TimeUnit} from this import
     * @param values Map containing values for the Template
     * @return new {@link Template} with the provided values
     */
    private Template parseTemplate(HashMap<String, Template>templates,
                                   HashMap<String, Category>categories,
                                   HashMap<String, TimeUnit> timeUnits,
                                   HashMap<String, Object> values){
        var category = parseCategory(categories,
                (String) values.get("category_name"),
                Integer.parseInt((String)  values.get("category_color")));

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
                        Integer.parseInt((String) values.get("allocated_time")));
    }



    /**
     * Parses {@link TimeUnit} from provided values
     * @param timeUnits Map of {@link TimeUnit} from this import
     * @param name {@link String} name of the time unit
     * @param shortName {@link String} short name of the time unit
     * @param rate {@link Integer} conversion rate of time unit to {@link TimeUnit#BASE_UNIT}
     * @return new {@link TimeUnit} with the provided values
     */
    private TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                   String name, String shortName, Integer rate){
        return timeUnits.computeIfAbsent(name,
                timeUnit -> new TimeUnit(null, name, shortName, rate));
    }

    /**
     * Parses {@link TimeUnit} from provided values
     * @param timeUnits Map of {@link TimeUnit} from this import
     * @param values Map containing values for the time unit
     * @return new {@link TimeUnit} with the provided values
     */
    private TimeUnit parseTimeUnit(HashMap<String, TimeUnit> timeUnits,
                                   HashMap<String, Object> values){

        return parseTimeUnit(timeUnits,
                    (String) values.get("time_unit_name"),
                    (String) values.get("time_unit_short_name"),
                    Integer.parseInt((String)  values.get("time_unit_rate")));
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
