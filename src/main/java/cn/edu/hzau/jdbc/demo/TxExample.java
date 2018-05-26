package cn.edu.hzau.jdbc.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class TxExample {
    //JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.137.100:3306/test?useSSL=false";

    //Database credentials
    private static final String USER_NAME = "normal";
    private static final String PASSWORD = "Abcd-1234";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String sql1 = "insert into employee values(106, 20, 'Rita', 'Taz')";
            stmt.executeUpdate(sql1);

            //assume the id is duplicated
            String Sql2 = "insert into employee values(106, 22, 'Sita', 'Singh')";
            stmt.executeUpdate(Sql2);

            //if there is no error
            conn.commit();
        } catch (ClassNotFoundException e) {
            //handle errors for Class.forName
            e.printStackTrace();
        } catch (SQLException e) {
            //if there is any error
            if (Objects.nonNull(conn)) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    //nothing we can do
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            //close resources
            try {
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
