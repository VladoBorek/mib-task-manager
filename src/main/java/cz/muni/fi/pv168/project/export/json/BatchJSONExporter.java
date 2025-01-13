package cz.muni.fi.pv168.project.export.json;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.export.json.exporters.JSONExporters;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


/**
 * Handles the export of the application data to JSON file
 *
 * @author Nikol Otáhalů
 */
public class BatchJSONExporter implements BatchExporter {
    private static final Format FORMAT = new Format("JSON", List.of("json"));

    @Override
    public void exportBatch(Batch batch, String filePath) {

        try (var writer = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8)) {
            var exportObject = new JSONObject();

            exportObject.put("tasks", exportCollection(batch.tasks(), JSONExporters::taskObject));
            exportObject.put("templates", exportCollection(batch.templates(), JSONExporters::templateObject));
            exportObject.put("categories", exportCollection(batch.categories(), JSONExporters::categoryObject));
            exportObject.put("time_units", exportCollection(batch.timeUnits(), JSONExporters::timeUnitObject));

            writer.write(exportObject.toString(4));
        } catch (IOException exception) {
            throw new DataManipulationException("Unable to write to file", exception);
        }
    }

    private JSONArray exportCollection(Collection<?> batchCollection,
                                       Function<Entity, JSONObject> createObject) {
        var itemArray = new JSONArray();
        for (var item : batchCollection) {
            itemArray.put(createObject.apply((Entity) item));
        }
        return itemArray;
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
