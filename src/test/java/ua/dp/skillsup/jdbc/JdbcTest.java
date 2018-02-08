package ua.dp.skillsup.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Calendar;

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

        //CREATE TABLE
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
        statement.execute("DROP TABLE IF EXISTS POST;" +
                "CREATE TABLE POST\n" +
                "(\n" +
                "    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    AUTHOR_ID INT,\n" +
                "    TITLE VARCHAR(64) NOT NULL,\n" +
                "    CONTACT VARCHAR(60) NOT NULL,\n" +
                ");");
        statement.close();

        statement = conn.createStatement();
        statement.execute("ALTER TABLE POST ADD (TIMESTAMP timestamp)");
        statement.close();

        statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS LIKE_POST;" +
                "CREATE TABLE LIKE_POST\n" +
                "(\n" +
                "    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "    POST_ID INT,\n" +
                "    AUTHOR_ID INT,\n" +
                "    TIMESTAMP timestamp,\n" +
                ");");
        statement.close();


        //INSERT INTO
        statement = conn.createStatement();
        statement.execute("INSERT INTO user(USERNAME, PASSWORD, EMAIL) VALUES\n" +
                "  ('admin','$2a$08$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBooVoy',\n" +
                "   'admin@gmail.com'),"+
                "  ('user2','$2a$08$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBUser2',\n" +
                "   'user2@gmail.com'),"+
                "  ('user3','$2a$08$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBUser3',\n" +
                "   'user3@gmail.com'),"+
                "  ('user4','$2a$08$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBUser4',\n" +
                "   'user4@gmail.com'),"+
                "  ('user5','$1234$DobXCJtm7dQwfNz3J0ZILevNNXVslyrY2j0J2dKOBG56uPpBUser5',\n" +
                "   'user5@gmail.com');");
        statement.close();

        statement = conn.createStatement();
        statement.execute("INSERT INTO POST(AUTHOR_ID, TITLE, CONTACT, TIMESTAMP) VALUES\n" +
                "  (1,'Hello my name is Admin !!! ',         'CONTACT1', CURRENT_TIMESTAMP-1000)," +
                "  (1,'Admin write: -This is my post №1',   'CONTACT1', CURRENT_TIMESTAMP-100)," +
                "  (1,'Admin write: -This is my post №2',   'CONTACT1', CURRENT_TIMESTAMP-1)," +
                "  (2,'Hello my name is USER2 !!! ',         'CONTACT2', CURRENT_TIMESTAMP-50)," +
                "  (3,'Hello my name is USER3 !!! ',         'CONTACT3', CURRENT_TIMESTAMP-5)," +
                "  (4,'Hello my name is USER4 !!! ',         'CONTACT4', CURRENT_TIMESTAMP-10)," +
                "  (5,'Hello my name is USER5 !!! ',         'CONTACT5', CURRENT_TIMESTAMP-500)," +
                "  (5,'USER5 write: -This is my post №2',   'CONTACT5', CURRENT_TIMESTAMP-20)," +
                "  (5,'USER5 write: -This is my post №3',   'CONTACT5', CURRENT_TIMESTAMP-10)," +
                "  (5,'USER5 write: -This is my post №4',   'CONTACT5', CURRENT_TIMESTAMP-1)," +
                "   ;");
        statement.close();

        statement = conn.createStatement();
        statement.execute("INSERT INTO LIKE_POST(POST_ID, AUTHOR_ID, TIMESTAMP) VALUES\n" +
                "  (1,1, CURRENT_TIMESTAMP-1000)," +
                "  (1,2, CURRENT_TIMESTAMP-100)," +
                "  (1,3, CURRENT_TIMESTAMP-1)," +
                "  (2,1, CURRENT_TIMESTAMP-50)," +
                "  (3,2, CURRENT_TIMESTAMP-5)," +
                "  (4,3, CURRENT_TIMESTAMP-10)," +
                "  (5,1, CURRENT_TIMESTAMP-500)," +
                "  (5,2, CURRENT_TIMESTAMP-20)," +
                "  (5,3, CURRENT_TIMESTAMP-10)," +
                "  (5,4, CURRENT_TIMESTAMP-1)," +
                "  (5,5, CURRENT_TIMESTAMP-1000)," +
                "  (6,1, CURRENT_TIMESTAMP-100)," +
                "  (6,2, CURRENT_TIMESTAMP-1)," +
                "  (6,3, CURRENT_TIMESTAMP-50)," +
                "  (7,2, CURRENT_TIMESTAMP-5)," +
                "  (8,3, CURRENT_TIMESTAMP-10)," +
                "  (9,1, CURRENT_TIMESTAMP-500)," +
                "  (10,2, CURRENT_TIMESTAMP-20)," +
                "  (10,3, CURRENT_TIMESTAMP-10)," +
                "  (10,4, CURRENT_TIMESTAMP-1)," +
                "   ;");
        statement.close();

        //a) list of all post with columns (title, username)
        System.out.println("________");
        System.out.println("a) list of all post with columns (title, username)");

        ResultSet resultSet1 = conn.prepareStatement("SELECT " +
                "p.TITLE, " +
                "u.USERNAME, " +
                "p.TIMESTAMP " +
                "FROM USER u INNER JOIN POST p " +
                "ON u.ID = p.AUTHOR_ID").executeQuery();
        printResultSet(resultSet1);

        //b) post list (title, total_likes_received) with the most popular post at the top
        System.out.println("________");
        System.out.println("b) post list (title, total_likes_received) with the most popular post at the top");

        ResultSet resultSet2 = conn.prepareStatement("SELECT " +
                "p.TITLE, " +
                "COUNT(l.ID) as total_likes_received " +
                "FROM POST p INNER JOIN LIKE_POST l " +
                "ON p.ID = l.POST_ID " +
                "GROUP BY p.ID " +
                "ORDER BY total_likes_received  DESC").executeQuery();
        printResultSet(resultSet2);

        //c) post list (title, total_likes_received) with the most popular post at the top,
        // display only posts written during some time interval
        System.out.println("________");
        System.out.println("c) post list (title, total_likes_received) with the most popular post at the top, ");
        System.out.println("display only posts written during some time interval");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015,1,1);
        Timestamp BeginTimestamp = new Timestamp(calendar.getTime().getTime());
        calendar.set(2017,1,1);
        Timestamp EndTimestamp = new Timestamp(calendar.getTime().getTime());

        PreparedStatement prepState = conn.prepareStatement("SELECT " +
                "p.TITLE, " +
                "COUNT(l.ID) as total_likes_received " +
                "FROM POST p INNER JOIN LIKE_POST l " +
                "ON p.ID = l.POST_ID " +
                "WHERE p.TIMESTAMP>? AND p.TIMESTAMP<? " +
                "GROUP BY p.ID " +
                "ORDER BY total_likes_received  DESC");
        prepState.setTimestamp(1, BeginTimestamp);
        prepState.setTimestamp(2, EndTimestamp);
        ResultSet resultSet3 = prepState.executeQuery();
        printResultSet(resultSet3);

        //d) post list (title, total_likes_received) with the most popular post at the top,
        // display only posts that have more than 5 likes
        System.out.println("________");
        System.out.println("d) post list (title, total_likes_received) with the most popular post at the top, ");
        System.out.println("display only posts that have more than 5 likes");

        ResultSet resultSet4 = conn.prepareStatement("SELECT " +
                "p.TITLE, " +
                "COUNT(l.ID) as total_likes_received " +
                "FROM POST p INNER JOIN LIKE_POST l " +
                "ON p.ID = l.POST_ID " +
                "GROUP BY p.ID "+
                "HAVING COUNT(l.ID)>5").executeQuery();
        printResultSet(resultSet4);

        //e) user list (username, total_likes_received) with the most popular user at the top
        System.out.println("________");
        System.out.println("e) user list (username, total_likes_received) with the most popular user at the top");

        System.out.println("Version E.1 :");
        String countLikeForPostUsers = "SELECT " +
                "p.AUTHOR_ID as AUTHOR_ID, " +
                "COUNT(l.ID) as All_like " +
                "FROM POST p INNER JOIN LIKE_POST l " +
                "ON p.ID = l.POST_ID " +
                "GROUP BY p.ID";

        ResultSet resultSet5 = conn.prepareStatement("SELECT " +
                "u.USERNAME, " +
                "SUM(countLike.All_like) as total_likes_received " +
                "FROM USER u INNER JOIN ("+countLikeForPostUsers+") countLike " +
                "ON u.ID = countLike.AUTHOR_ID " +
                "GROUP BY u.ID " +
                "ORDER BY total_likes_received DESC").executeQuery();
        printResultSet(resultSet5);

        System.out.println("");
        System.out.println("Version E.2 :");
        ResultSet resultSet8 = conn.prepareStatement("SELECT " +
                "u.USERNAME, " +
                "COUNT(l.ID) as total_likes_received " +
                "FROM POST p " +
                "INNER JOIN USER u ON p.AUTHOR_ID = u.ID " +
                "INNER JOIN LIKE_POST l ON p.ID = l.POST_ID " +
                "GROUP BY u.ID " +
                "ORDER BY total_likes_received DESC").executeQuery();
        printResultSet(resultSet8);


        //f) user list (username, total_likes_given) with the most active user at the top
        System.out.println("________");
        System.out.println("f) user list (username, total_likes_given) with the most active user at the top");

        ResultSet resultSet6 = conn.prepareStatement("SELECT " +
                "u.USERNAME, " +
                "COUNT(l.ID) as total_likes_given " +
                "FROM USER u INNER JOIN LIKE_POST l " +
                "ON u.ID = l.AUTHOR_ID " +
                "GROUP BY u.ID " +
                "ORDER BY total_likes_given DESC").executeQuery();
        printResultSet(resultSet6);

        //g) user list (username, average_likes_per_post) with the most popular user at the top
        System.out.println("________");
        System.out.println("g) user list (username, average_likes_per_post) with the most popular user at the top");

        String countLikeForPostUsers1 = "SELECT " +
                "p.AUTHOR_ID as AUTHOR_ID, " +
                "p.ID as POST_ID, " +
                "COUNT(l.ID) as All_like " +
                "FROM POST p INNER JOIN LIKE_POST l " +
                "ON p.ID = l.POST_ID " +
                "GROUP BY p.ID";

        ResultSet resultSet7 = conn.prepareStatement("SELECT " +
                "u.USERNAME, " +
                "AVG(countLike.All_like) as average_likes_per_post " +
                "FROM USER u INNER JOIN ("+countLikeForPostUsers1+") countLike " +
                "ON u.ID = countLike.AUTHOR_ID " +
                "GROUP BY u.ID " +
                "ORDER BY average_likes_per_post DESC").executeQuery();
        printResultSet(resultSet7);




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
