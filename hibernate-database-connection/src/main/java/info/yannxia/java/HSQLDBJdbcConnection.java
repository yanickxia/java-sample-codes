package info.yannxia.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yann.Xia on 2015/7/26.
 */
public class HSQLDBJdbcConnection {
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String CONNECTION_URL = "jdbc:hsqldb:mem:sample";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private CallableStatement callableStatement = null;
    private ResultSet resultSet = null;

    static {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public int executeUpdate(String sql) {
        int affectedLine = 0;

        try {
            // 获得连接
            connection = this.getConnection();
            // 调用SQL
            preparedStatement = connection.prepareStatement(sql);
            // 执行
            affectedLine = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAll();
        }
        return affectedLine;
    }

    private ResultSet executeQueryRS(String sql) {
        try {
            connection = this.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }


    public List<Map<String, Object>> executeQuerys(String sql) {
        ResultSet rs = executeQueryRS(sql);
        ResultSetMetaData rsmd = null;

        int columnCount = 0;
        try {
            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        // 创建List
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAll();
        }

        return list;
    }


    public Map<String, Object> executeQuery(String sql) {
        ResultSet rs = executeQueryRS(sql);
        ResultSetMetaData rsmd = null;

        int columnCount = 0;
        try {
            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        // 创建List
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAll();
        }

        return list.get(0);
    }


    private void closeAll() {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}