package DAL;

import java.sql.*;

public class DBRepo {
    private final DBConfig db;
    public DBRepo(DBConfig db) {
        this.db = db;
    }
    public void testConnection() {
        try (Connection c = db.getConnection()) {
            DatabaseMetaData md = c.getMetaData();
            System.out.println("✅ Connection OK: " + md.getURL());
            System.out.println("    Driver: " + md.getDriverName() + " - " + md.getDriverVersion());

        } catch (Exception e) {
            System.out.println("❌ Connection ERROR: " + e.getMessage());
            System.out.println("Tip: Check URL/USER/PASS and MySQL is running.");
        }
    }
}
