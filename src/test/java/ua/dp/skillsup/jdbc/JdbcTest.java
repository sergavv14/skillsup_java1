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
                "    POST_ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
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
                "t2.TITLE, " +
                "t1.USERNAME, " +
                "t2.TIMESTAMP " +
                "FROM USER t1 INNER JOIN POST t2 " +
                "ON (t1.ID = t2.AUTHOR_ID)").executeQuery();
        printResultSet(resultSet1);

        /*
        ________
        a) list of all post with columns (title, username)
        TITLE|USERNAME|TIMESTAMP|
        Hello my name is Admin !!! |admin|2015-04-27 22:06:47.555|
        Admin write: -This is my post №1|admin|2017-10-13 22:06:47.555|
        Admin write: -This is my post №2|admin|2018-01-20 22:06:47.555|
        Hello my name is USER2 !!! |user2|2017-12-02 22:06:47.555|
        Hello my name is USER3 !!! |user3|2018-01-16 22:06:47.555|
        Hello my name is USER4 !!! |user4|2018-01-11 22:06:47.555|
        Hello my name is USER5 !!! |user5|2016-09-08 22:06:47.555|
        USER5 write: -This is my post №2|user5|2018-01-01 22:06:47.555|
        USER5 write: -This is my post №3|user5|2018-01-11 22:06:47.555|
        USER5 write: -This is my post №4|user5|2018-01-20 22:06:47.555|
         */


        //b) post list (title, total_likes_received) with the most popular post at the top
        System.out.println("________");
        System.out.println("b) post list (title, total_likes_received) with the most popular post at the top");

        ResultSet resultSet2 = conn.prepareStatement("SELECT " +
                "t1.TITLE, " +
                "COUNT(t2.ID) as total_likes_received " +
                "FROM POST t1 INNER JOIN LIKE_POST t2 " +
                "ON (t1.POST_ID = t2.POST_ID) " +
                "GROUP BY t1.POST_ID " +
                "ORDER BY total_likes_received  DESC").executeQuery();
        printResultSet(resultSet2);

        /*
        ________
        b) post list (title, total_likes_received) with the most popular post at the top
        TITLE|TOTAL_LIKES_RECEIVED|
        Hello my name is USER3 !!! |5|
        Hello my name is Admin !!! |3|
        Hello my name is USER4 !!! |3|
        USER5 write: -This is my post №4|3|
        Admin write: -This is my post №1|1|
        Admin write: -This is my post №2|1|
        Hello my name is USER2 !!! |1|
        Hello my name is USER5 !!! |1|
        USER5 write: -This is my post №2|1|
        USER5 write: -This is my post №3|1|
         */


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
                "t1.TITLE, " +
                "COUNT(t2.ID) as total_likes_received " +
                "FROM POST t1 INNER JOIN LIKE_POST t2 " +
                "ON (t1.POST_ID = t2.POST_ID) " +
                "WHERE (t1.TIMESTAMP>?) AND (t1.TIMESTAMP<?) " +
                "GROUP BY t1.POST_ID " +
                "ORDER BY total_likes_received  DESC");
        prepState.setTimestamp(1, BeginTimestamp);
        prepState.setTimestamp(2, EndTimestamp);
        ResultSet resultSet3 = prepState.executeQuery();
        printResultSet(resultSet3);

        /*
        ________
        c) post list (title, total_likes_received) with the most popular post at the top,
        display only posts written during some time interval
        TITLE|TOTAL_LIKES_RECEIVED|
        Hello my name is Admin !!! |3|
        Hello my name is USER5 !!! |1|
         */


        //d) post list (title, total_likes_received) with the most popular post at the top,
        // display only posts that have more than 5 likes
        System.out.println("________");
        System.out.println("d) post list (title, total_likes_received) with the most popular post at the top, ");
        System.out.println("display only posts that have more than 5 likes");

        ResultSet resultSet4 = conn.prepareStatement("SELECT " +
                "t1.TITLE, " +
                "COUNT(t2.ID) as total_likes_received " +
                "FROM POST t1 INNER JOIN LIKE_POST t2 " +
                "ON (t1.POST_ID = t2.POST_ID) " +
                "GROUP BY t1.POST_ID "+
                "HAVING COUNT(t2.ID)>5").executeQuery();
        printResultSet(resultSet4);

        /*
        ________
        d) post list (title, total_likes_received) with the most popular post at the top,
        display only posts that have more than 5 likes
        TITLE|TOTAL_LIKES_RECEIVED|
         */


        //e) user list (username, total_likes_received) with the most popular user at the top
        System.out.println("________");
        System.out.println("e) user list (username, total_likes_received) with the most popular user at the top");


        String countLikeForPostUsers = "SELECT " +
                "t1.AUTHOR_ID as AUTHOR_ID, " +
                "COUNT(t2.ID) as All_like " +
                "FROM POST t1 INNER JOIN LIKE_POST t2 " +
                "ON (t1.POST_ID = t2.POST_ID) " +
                "GROUP BY t1.POST_ID";

        ResultSet resultSet5 = conn.prepareStatement("SELECT " +
                "tt1.USERNAME, " +
                "SUM(tt2.All_like) as total_likes_received " +
                "FROM USER tt1 INNER JOIN ("+countLikeForPostUsers+") tt2 " +
                "ON (tt1.ID = tt2.AUTHOR_ID) " +
                "GROUP BY tt1.ID " +
                "ORDER BY total_likes_received DESC").executeQuery();
        printResultSet(resultSet5);

        /*
        ________
        e) user list (username, total_likes_received) with the most popular user at the top
        USERNAME|TOTAL_LIKES_RECEIVED|
        user5|6|
        admin|5|
        user3|5|
        user4|3|
        user2|1|
         */


        //f) user list (username, total_likes_given) with the most active user at the top
        System.out.println("________");
        System.out.println("f) user list (username, total_likes_given) with the most active user at the top");

        ResultSet resultSet6 = conn.prepareStatement("SELECT " +
                "t1.USERNAME, " +
                "COUNT(t2.ID) as total_likes_given " +
                "FROM USER t1 INNER JOIN LIKE_POST t2 " +
                "ON (t1.ID = t2.AUTHOR_ID) " +
                "GROUP BY t1.ID " +
                "ORDER BY total_likes_given DESC").executeQuery();
        printResultSet(resultSet6);

        /*
        ________
        f) user list (username, total_likes_given) with the most active user at the top
        USERNAME|TOTAL_LIKES_GIVEN|
        user2|6|
        user3|6|
        admin|5|
        user4|2|
        user5|1|
         */


        //g) user list (username, average_likes_per_post) with the most popular user at the top
        System.out.println("________");
        System.out.println("g) user list (username, average_likes_per_post) with the most popular user at the top");

        String countLikeForPostUsers1 = "SELECT " +
                "t1.AUTHOR_ID as AUTHOR_ID, " +
                "t1.POST_ID as POST_ID, " +
                "COUNT(t2.ID) as All_like " +
                "FROM POST t1 INNER JOIN LIKE_POST t2 " +
                "ON (t1.POST_ID = t2.POST_ID) " +
                "GROUP BY t1.POST_ID";

        ResultSet resultSet7 = conn.prepareStatement("SELECT " +
                "tt1.USERNAME, " +
                "AVG(tt2.All_like) as average_likes_per_post " +
                "FROM USER tt1 INNER JOIN ("+countLikeForPostUsers1+") tt2 " +
                "ON (tt1.ID = tt2.AUTHOR_ID) " +
                "GROUP BY tt1.ID " +
                "ORDER BY average_likes_per_post DESC").executeQuery();
        printResultSet(resultSet7);

        /*
        ________
        g) user list (username, average_likes_per_post) with the most popular user at the top
        USERNAME|AVERAGE_LIKES_PER_POST|
        user3|5|
        user4|3|
        admin|2|
        user5|2|
        user2|1|
         */


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
