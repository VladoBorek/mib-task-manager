package cz.muni.fi.pv168.project.storage.sql.db.actions;

import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import org.tinylog.Logger;

/**
 * @author Vladimir Borek
 */
public class CreateDatabase {
    public static void main(String[] args) {
        var dbManager = DatabaseManager.createProductionInstance();
        dbManager.initSchema();
        Logger.info("Database created...");
        Logger.info("Database connection string: {}", dbManager.getDatabaseConnectionString());
    }
}
