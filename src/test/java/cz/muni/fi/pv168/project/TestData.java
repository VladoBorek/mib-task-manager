package cz.muni.fi.pv168.project;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.export.json.BatchJSONExporter;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;


public class TestData {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz ";
    private static final String NUMBERS = "0123456789";
    private static final Integer COUNT = 25000;
    private static final Integer WORKLOG_COUNT = 20;


    public static String generateRandomWord(int length) {
        Random random = new Random();
        StringBuilder word = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHABET.length());
            word.append(ALPHABET.charAt(index));
        }
        return word.toString();
    }
    public static Integer generateRandomNumber() {
        Random random = new Random();
        var length = random.nextInt(1, 3);
        StringBuilder word = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(NUMBERS.length());
            word.append(NUMBERS.charAt(index));
        }
        return Integer.parseInt(word.toString());
    }

    public static Object getRandomObject(List items){
        Random random = new Random();
        return items.get(random.nextInt(items.size()));
    }
    private static List<TimeUnit> generateTimeUnits(Integer count){
        ArrayList<TimeUnit> items = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            items.add(new TimeUnit(i,
                    generateRandomWord(5),
                    generateRandomWord(3),
                    new Random().nextInt(1,1440)
            ));
        }
        return items;
    }
    private static List<User> generateUsers(Integer count) {

        ArrayList<User> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(new User(generateRandomWord(10),
                            generateRandomNumber().longValue())
            );
        }
        return items;
    }

    private static List<LogTimeInfo> generateWorkLogs(List<TimeUnit> timeUnits,
                                                      List<User> users,
                                                      Integer count,
                                                      Task task) {

        ArrayList<LogTimeInfo> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(new LogTimeInfo(
                    generateRandomNumber().doubleValue(),
                    (User) getRandomObject(users),
                    task.getId(),
                    (TimeUnit) getRandomObject(timeUnits)
            ));
        }
        return items;
    }
    private static List<Task> generateTasks(List<Category> categories,
                                            List<User> users,
                                     List<TimeUnit> timeUnits,
                                     Integer count) {
        ArrayList<Task> items = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            var task = new Task(i,
                    Status.TO_DO,
                    generateRandomWord(50),
                    (Category) getRandomObject(categories),
                    generateRandomWord(10),
                    generateRandomWord(10),
                    generateRandomWord(10),
                    0.0,
                    generateRandomNumber().doubleValue(),
                    (TimeUnit) getRandomObject(timeUnits),
                    LocalDate.now()
            );
            var workLogs = generateWorkLogs(timeUnits,users, WORKLOG_COUNT, task);
            workLogs.forEach(task::addLog);
            double totalLoggedTime = 0;
            for (var log: workLogs) {
                totalLoggedTime += log.getLoggedTime();
            }
            task.setLoggedTime(totalLoggedTime);
            items.add(task);
        }
        return items;
    }

    private static List<Category> generateCategories(Integer count) {
        ArrayList<Category> items = new ArrayList<>();
        Random random = new Random();
        for (long i = 0; i < count; i++) {
            items.add(new Category(i,
                    generateRandomWord(10),
                    new Color(random.nextInt(0, 256),
                    random.nextInt(0, 256),
                    random.nextInt(0, 256)))
            );
        }
        return items;
    }

    private static List<Template> generateTemplates(List<Category> categories,
                                             List<TimeUnit> timeUnits,
                                             Integer count) {
        ArrayList<Template> items = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            items.add(new Template(i,
                    generateRandomWord(10),
                    (Category) getRandomObject(categories),
                    generateRandomNumber().doubleValue(),
                    (TimeUnit) getRandomObject(timeUnits),
                    generateRandomWord(10),
                    generateRandomWord(50),
                    generateRandomWord(10))
            );
        }
        return items;
    }

    public static void main(String[] args) {
        var exporter = new BatchJSONExporter();

        var users = generateUsers(COUNT);
        var timeUnits = generateTimeUnits(COUNT);
        var categories = generateCategories(COUNT);
        var templates = generateTemplates(categories, timeUnits, COUNT);
        var tasks = generateTasks(categories, users,timeUnits, COUNT);
        var batch = new Batch(
                tasks,
                categories,
                templates,
                timeUnits
        );
        exporter.exportBatch(batch, "src/test/resources/export/export" + COUNT +".json");
    }



}
