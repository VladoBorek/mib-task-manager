package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;

/**
 * @author Vladimir Borek
 */
public class ProductionDependencyProvider extends CommonDependecnyProvider {
    public ProductionDependencyProvider() {
        super(createDatabaseManager());
    }

    private static DatabaseManager createDatabaseManager() {
        DatabaseManager databaseManager = DatabaseManager.createProductionInstance();
        databaseManager.initSchema();
        return databaseManager;
    }
}
