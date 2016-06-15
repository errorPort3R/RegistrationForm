
import com.theironyard.javawithclojure.jhporter.Main;
import com.theironyard.javawithclojure.jhporter.User;
import org.h2.tools.Server;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static com.theironyard.javawithclojure.jhporter.Main.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by jeffryporter on 6/15/16.
 */
public class MainTest
{

    Connection startConnection() throws SQLException
    {
        Connection conn = DriverManager.getConnection(("jdbc:h2:mem:test"));
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void TestInsertSelectAndDelete() throws SQLException
    {

        Connection conn = startConnection();
        User a = new User(null,"Alice", "17 Princess St", "aliceCooper@gmail.com");
        User b = new User(null,"Bob", "120 Market Street", "bobRoss@yahoo.com");
        Main.insertUser(conn, a);
        Main.insertUser(conn, b);
        ArrayList<User> users = new ArrayList<>();
        users = Main.selectUsers(conn);

        assertTrue(users != null);
        assertTrue(users.get(0).getUsername().equals("Alice"));

        Main.deleteUser(conn,users.get(0).getId());
        users = new ArrayList<>();
        users = Main.selectUsers(conn);
        conn.close();

        assertTrue(users.size() == 1);
    }

    @Test
    public void testUpdate() throws SQLException
    {
        Connection conn = startConnection();
        User a = new User(1,"Alice", "17 Princess St", "aliceCooper@gmail.com");
        User b = new User(1,"Bob", "120 Market Street", "bobRoss@yahoo.com");
        Main.insertUser(conn, a);
        Main.updateUser(conn,b);
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.get(0).getUsername().equals("Bob"));
        assertTrue(users.get(0).getEmail().equals("bobRoss@yahoo.com"));
        assertTrue(users.get(0).getAddress().equals("120 Market Street"));
    }



}
