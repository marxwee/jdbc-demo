package cn.edu.hzau.jdbc.demo;

import java.sql.*;
import java.util.Objects;

public class FirstExample {
    //JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.137.100:3306/test?useSSL=false";

    //Database credentials
    private static final String USER_NAME = "normal";
    private static final String PASSWORD = "Abcd-1234";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //1. register jdbc driver
            Class.forName(JDBC_DRIVER);

            //2. open a connection
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

            //3. execute a query
            stmt = conn.createStatement();
            String sql = "select id, age, first, last from employee";
            rs = stmt.executeQuery(sql);

            //4. extract data from result set
            while (rs.next()) {
                //retrieve by column name
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                //display values
                System.out.print("id: " + id);
                System.out.print(", age: " + age);
                System.out.print(", first: " + first);
                System.out.println(", last: " + last);
            }
        } catch (ClassNotFoundException e) {
            //handle errors for Class.forName
            e.printStackTrace();
        } catch (SQLException e) {
            //handle errors for jdbc
            e.printStackTrace();
        } finally {
            //close resources
            try {
                if (Objects.nonNull(rs)) {
                    rs.close();
                }
                if (Objects.nonNull(stmt)) {
                    stmt.close();
                }
                if (Objects.nonNull(conn)) {
                    conn.close();
                }
            } catch (SQLException e) {
                //nothing we can do
                e.printStackTrace();
            }
        }
    }
}
