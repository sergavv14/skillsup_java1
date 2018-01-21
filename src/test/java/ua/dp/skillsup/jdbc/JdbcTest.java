package ua.dp.skillsup.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class JdbcTest {

    private Connection conn;

    @Before
    public void setUp() throws Exception {
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void testUser() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS USER;" +
                "CREATE TABLE USER\n" +
                "(\n" +
                "    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    USERNAME VARCHAR(64) NOT NULL UNIQUE,\n" +
                "    PASSWORD VARCHAR(60) NOT NULL,\n" +
                "    EMAIL VARCHAR(64) NOT NULL,\n" +
                ");");
        statement.close();

        statement = conn.createStatement();
        statement.execute("INSERT INTO user(USERNAME, PASSWORD, EMAIL) VALUES\n" +
                "  ('admin','$2a$08$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBooVoy',\n" +
                "   'helen.moore@gmail.com'),"+
                "  ('myAdmin','$1234$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBooVoy',\n" +
                "   'test@gmail.com');");
        statement.close();
        ResultSet resultSet = conn.prepareStatement("select * from user").executeQuery();

        statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS post;" +
                "CREATE TABLE post\n" +
                "(\n" +
                "    POST_ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    AUTHOR_ID INT,\n" +
                "    TITLE VARCHAR(64) NOT NULL,\n" +
                "    CONTACT VARCHAR(60) NOT NULL,\n" +
                ");");
        statement.close();

        statement = conn.createStatement();
        statement.execute("INSERT INTO post(AUTHOR_ID, TITLE, CONTACT) VALUES\n" +
                "  ('1','Hello This is my post №1','CONTACT1')," +
                "  ('2','Hello This is my post №2','CONTACT2')," +
                "  ('1','Hello This is my post №3','CONTACT3')" +
                "   ;");
        statement.close();

        ResultSet resultSet1 = conn.prepareStatement("select " +
                "c1.USERNAME, " +
                "c2.TITLE " +
                "FROM USER c1 INNER JOIN post c2" +
                "WHERE c1.ID=c2.AUTHOR_ID").executeQuery();
        printResultSet(resultSet1);



    }

    public void printResultSet(ResultSet resultSet) throws SQLException {
        //from result set give metadata
        ResultSetMetaData rsmd = resultSet.getMetaData();
        //columns count from metadata object
        int numOfCols = rsmd.getColumnCount();


        for (int i=1; i<=numOfCols; i++) {
            System.out.print(rsmd.getColumnName(i)+ "|");
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i=1; i<=numOfCols; i++) {
                System.out.print(resultSet.getObject(i) + "|");
            }
            System.out.println();
        }
    }
}
