package cz.muni.fi.pv168.project.export.json;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.util.ActionType;

import java.io.BufferedWriter;
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
    private static final String TAB = "    ";
    private static final String ITEM_START = TAB + "{\n";
    private static final String ITEM_END = "\n" + TAB + "}";
    private Collection<LogTimeInfo> logTimeInfos;


    @Override
    public void exportBatch(Batch batch, String filePath, ActionType type) {
        this.logTimeInfos = batch.logTimeInfos();

        try (var writer = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8)) {
            writer.write("[\n");

            switch (type) {
                case TASK -> writeBatch(batch.tasks(), this::createTaskItem, writer);
                case CATEGORY -> writeBatch(batch.categories(), this::createCategoryItem, writer);
                case TEMPLATE -> writeBatch(batch.templates(), this::createTemplateItem, writer);
                case TIME_UNIT -> writeBatch(batch.timeUnits(), this::createTimeUnitItem, writer);
                case WORK_LOG -> writeBatch(batch.logTimeInfos(), this::createLogTimeInfoItem, writer);
            }

            writer.write("]");
        } catch (IOException exception) {
            throw new DataManipulationException("Unable to write to file", exception);
        }

    }

    /**
     * Using writer, writes items from Batch collection into a file
     *
     * @param items  Collection from {@link Batch} to be written
     * @param create function that is applied to items, returns string which is the written with writer
     * @param writer Buffered writer, that writes the data into file
     * @throws IOException if the writer fails
     */
    private void writeBatch(Collection<?> items, Function<Object, String> create, BufferedWriter writer) throws IOException {
        int count = items.size();
        for (var item : items) {
            String itemString = create.apply(item);

            writer.write(ITEM_START + itemString + ITEM_END);
            count--;
            if (count != 0) {
                writer.write(",");
            }
            writer.newLine();
        }
    }

    /**
     * Creates line for the JSON file export
     *
     * @param attribute attribute for export
     * @param value     value of the attribute
     * @return String in JSON line format
     */
    private String createJSONLine(String attribute, Object value) {
        return TAB + TAB + "\"" + attribute + "\": " + "\"" + value.toString() + "\"";
    }

    /**
     * Turns {@link Task} object into a JSON format string
     *
     * @param object Object (Task) to be turned into string
     * @return String in JSON item format
     */
    private String createTaskItem(Object object) {
        var task = (Task) object;
        return String.join(",\n",
                createJSONLine("id", task.getId()),
                createJSONLine("status", Status.valueOf(task.getStatus().toString())),
                createJSONLine("description", task.getDescription()),
                createJSONLine("customer", task.getCustomer()),
                createJSONLine("task_name", task.getName()),
                createJSONLine("assigned_to", task.getAssignedTo()),
                createJSONLine("logged_time", task.getConvertedLoggedTime()),
                createJSONLine("allocated_time", task.getConvertedAllocatedTime()),
                createJSONLine("due_date", task.getDueDate()),
                createCategoryItem(task.getCategory()),
                createTimeUnitItem(task.getTimeUnit()),
                createTaskLogTimeInfos(logTimeInfos, task.getId())
        );
    }

    /**
     * Turns {@link Category} object into a JSON format string
     *
     * @param object Object (Category) to be turned into string
     * @return String in JSON item format
     */
    private String createCategoryItem(Object object) {
        var category = (Category) object;
        return String.join(",\n",
                createJSONLine("category_name", category.getName()),
                createJSONLine("category_color", category.getColor().getRGB()));
    }

    /**
     * Turns {@link Template} object into a JSON format string
     *
     * @param object Object (Template) to be turned into string
     * @return String in JSON item format
     */
    private String createTemplateItem(Object object) {
        var template = (Template) object;
        return String.join(",\n",
                createJSONLine("template_name", template.getTemplateName()),
                createJSONLine("template_assigned_to", template.getAssignedTo()),
                createJSONLine("template_allocated_time", template.getAllocatedTime()),
                createJSONLine("template_task_name", template.getName()),
                createJSONLine("template_description", template.getDescription()),
                createCategoryItem(template.getCategory()),
                createTimeUnitItem(template.getTimeUnit())
        );
    }

    /**
     * Turns {@link TimeUnit} object into a JSON format string
     *
     * @param object Object (TimeUnit) to be turned into string
     * @return String in JSON item format
     */
    private String createTimeUnitItem(Object object) {
        var timeUnit = (TimeUnit) object;
        return String.join(",\n",
                createJSONLine("time_unit_name", timeUnit.getName()),
                createJSONLine("time_unit_short_name", timeUnit.getShortName()),
                createJSONLine("time_unit_rate", timeUnit.getRate())
        );
    }

    /**
     * Turns {@link LogTimeInfo} from collection that are associated with {@link Task}.id into a JSON format string
     *
     * @param workLogs Collection of {@link LogTimeInfo} from the application
     * @param taskID   Task ID to be matched with WorkLogs
     * @return String in JSON item format of all Work logs associated with {@link Task}.id
     */
    private String createTaskLogTimeInfos(Collection<LogTimeInfo> workLogs, Long taskID){
        var countWorkLogs = workLogs.stream().filter(log -> log.getTaskID() == taskID).count();
        StringBuilder stringBuilder = new StringBuilder(createJSONLine("work_logs_count", countWorkLogs));
        if (countWorkLogs != 0){
            stringBuilder.append(",\n");
        }
        int index = 0;
        for (var log: workLogs) {
            if (log.getTaskID() == taskID) {
                if (index != 0){
                    stringBuilder.append(",\n");
                }
                stringBuilder.append(createLogTimeInfoItem(log, index));
                index++;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Turns {@link LogTimeInfo} into a JSON format string
     *
     * @param object Object (LogTimeInfo) to be turned into string
     * @param order  Order of the Work Log
     * @return String in JSON item format
     */
    private String createLogTimeInfoItem(Object object, Integer order){
        var workLog = (LogTimeInfo) object;
        String stringOrder;
        if (order == null){
            stringOrder = "";
        } else {
            stringOrder = order.toString();
        }
        return String.join(",\n",
                createJSONLine("work_log_user_name" + stringOrder, workLog.getUsername()),
                createJSONLine("work_log_user_id" + stringOrder, workLog.getUserId()),
                createJSONLine("work_log_logged_time" + stringOrder, workLog.getLoggedTime()),
                createJSONLine("work_log_task_id" + stringOrder, workLog.getTaskID())
        );
    }

    /**
     * Turns {@link LogTimeInfo} into a JSON format string
     *
     * @param object Object (LogTimeInfo) to be turned into string
     * @return String in JSON item format
     */
    private String createLogTimeInfoItem(Object object){
        return createLogTimeInfoItem(object, null);
    }

    @Override
    public Format getFormat() {
        return FORMAT;
    }
}
