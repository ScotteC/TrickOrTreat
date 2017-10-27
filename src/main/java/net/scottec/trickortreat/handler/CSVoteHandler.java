package net.scottec.trickortreat.handler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Titian
 */
public class CSVoteHandler {
    private String host;
    private int port;
    private String user;
    private String password;
    private String database;

    private Connection connection;

    public CSVoteHandler() {
        File file = new File("plugins/csvote/", "database.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String db = "database.";
        cfg.addDefault(db + "host", "localhost");
        cfg.addDefault(db + "port", 3306);
        cfg.addDefault(db + "user", "user");
        cfg.addDefault(db + "password", "password");
        cfg.addDefault(db + "database", "database");
        cfg.addDefault("config.server", "server");
        cfg.options().copyDefaults(true);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.host = cfg.getString(db + "host");
        this.port = cfg.getInt(db + "port");
        this.user = cfg.getString(db + "user");
        this.password = cfg.getString(db + "password");
        this.database = cfg.getString(db + "database");

        this.connection = this.openConnection();
    }

    private synchronized Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://"
                            + this.host + ":" + this.port + "/" + this.database,
                    this.user, this.password);
            System.out.println("CS-Vote > Database connected");
            return connection;
        } catch (SQLException|ClassNotFoundException e) {
            System.out.println("CS-Vote > Connection refused");
            return null;
        }
    }

    private synchronized Connection getConnection() {
        try {
            if (this.connection.isClosed()) {
                System.out.println("DB-Connection closed -> Reconnect...");
                this.connection = openConnection();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    public int dbUpdate(String sql) {
        try{
            return this.getConnection().createStatement().executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.err.println("Failed to send update " + sql);
            return 0;
        }
    }

    public void closeConnection() {
        try {
            if (!this.connection.isClosed())
                this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connection = null;
        }
    }

    public void addMoney(String player, int amount) {
//        int systime = (int) System.currentTimeMillis();
        dbUpdate("UPDATE csvote_players SET COINS = COINS + " + amount + " WHERE NAME = '" + player + "';");
    }
}
