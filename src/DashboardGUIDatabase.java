
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author J
 */
public class DashboardGUIDatabase {

    Connection conn;

    public DashboardGUIDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        conn = DriverManager.
                getConnection("jdbc:h2:~/p2pdash_gui_db", "sa", "");
        Statement s = conn.createStatement();
        s.executeUpdate("create table if not exists s (KEY VARCHAR(50), VALUE VARCHAR(255))");
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public void saveJarPath(String jarPath) throws SQLException {
        System.out.println("Saving Jar Path: " + jarPath);
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select 1 from s where KEY='JAR'");
        if (rs.next() && rs.getBoolean(1)) {
            // update
            s = conn.createStatement();
            s.executeUpdate("update s set value='" + jarPath + "' where key='JAR'");
        } else {
            // insert
            s = conn.createStatement();
            s.executeUpdate("insert into s values ('JAR','" + jarPath + "')");
        }
    }

    public String readJarPath() throws SQLException {
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select VALUE from s where KEY='JAR'");
        if (rs.next()) {
            return rs.getNString(1);
        }
        return new String();
    }

}
