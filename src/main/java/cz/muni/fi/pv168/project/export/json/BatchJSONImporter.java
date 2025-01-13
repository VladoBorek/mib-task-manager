package cz.muni.fi.pv168.project.export.json;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchImporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.export.json.parsers.JSONArrayParsers;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

/**
 * Class for importing items from JSON files
 *
 * @author Nikol Otáhalů
 */
public class BatchJSONImporter implements BatchImporter {

    private static final Format FORMAT = new Format("JSON", List.of("json"));

    @Override
    public Batch importBatch(String filePath, Batch currentData) {
        var tasks = new HashMap<String, Task>();
        var categories = new HashMap<String, Category>();
        var templates = new HashMap<String, Template>();
        var timeUnits = new HashMap<String, TimeUnit>();

        currentData.categories().forEach(category -> categories.put(category.getName(), category));
        currentData.templates().forEach(template -> templates.put(template.getTemplateName(), template));
        currentData.timeUnits().forEach(timeUnit -> timeUnits.put(timeUnit.getName(), timeUnit));

        try (var reader = Files.newBufferedReader(Path.of(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            var objectImport = new JSONObject(jsonContent.toString());
            var tasksArray = objectImport.getJSONArray("tasks");
            var templateArray = objectImport.getJSONArray("templates");
            var categoryArray = objectImport.getJSONArray("categories");
            var timeUnitArray = objectImport.getJSONArray("time_units");

            JSONArrayParsers.importCategoryArray(categoryArray, categories);
            JSONArrayParsers.importTimeUnitArray(timeUnitArray, timeUnits);
            JSONArrayParsers.importTemplateArray(templateArray, templates, categories, timeUnits);
            JSONArrayParsers.importTaskArray(tasksArray, tasks, categories, timeUnits);

            return new Batch(tasks.values(), categories.values(), templates.values(), timeUnits.values());
        } catch (IOException e) {
            throw new DataManipulationException("Unable to read file\n" + e.getMessage());
        } catch (Exception e) {
            throw new DataManipulationException("Failed to import items\n" + e.getMessage());
        }
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
