
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
        s.executeUpdate("create table if not exists s (KEY VARCHAR(255), VALUE VARCHAR(255))");
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public void savePathForKey(String key, String path) throws SQLException {
        
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select 1 from s where KEY='" + key + "'");
        if (rs.next() && rs.getBoolean(1)) {
            // update
            s = conn.createStatement();
            s.executeUpdate("update s set value='" + path + "' where key='" + key + "'");
        } else {
            // insert
            s = conn.createStatement();
            s.executeUpdate("insert into s (KEY,VALUE) values ('" + key + "','" + path + "')");
        }
    }

    public String readPathForKey(String key) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("select VALUE from s where KEY='" + key + "'");
        if (rs.next()) {
            return rs.getNString(1);
        }
        return new String();
    }

}
