package DAL;

import Exceptions.DataAccessException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {

    private String url;
    private String user;
    private String pass;

    public DBConfig(){
        Properties prop = new Properties();

        try(InputStream is = getClass().getResourceAsStream("/db.properties")){
            if(is == null){
                throw new DataAccessException("Could not find db.properties in resources");
            }
            prop.load(is);
        } catch (Exception e){
            throw new DataAccessException("Could not read db.properties",e);
        }
        this.url = prop.getProperty("db.url");
        this.user = prop.getProperty("db.user");
        this.pass = prop.getProperty("db.password");

    }
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, pass);
        }  catch (SQLException e) {
            throw new DataAccessException("Connection failed.", e);
        }

    }



}
