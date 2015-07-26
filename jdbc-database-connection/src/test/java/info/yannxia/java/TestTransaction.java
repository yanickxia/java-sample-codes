package info.yannxia.java;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Created by Yann.Xia on 2015/7/26.
 */
public class TestTransaction extends BaseTestConfig {

    @Test
    public void testTransaction() {
        HSQLDBJdbcConnection hsqldbJdbcConnection = new HSQLDBJdbcConnection();
        Connection connection = hsqldbJdbcConnection.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.addBatch("INSERT INTO T_SAMPLE VALUES(2, '2')");
            statement.addBatch("INSERT INTO T_SAMPLE VALUES(2, '2')");
            statement.executeBatch();
        } catch (SQLException e) {
            logger.info("catch sql exception");
            try {
                connection.rollback();
                logger.info("roll back database");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            logger.info("close database");
        }

        Map<String, Object> result = hsqldbJdbcConnection.executeQuery("select count(*) as c from T_SAMPLE");

        Assert.assertTrue("transaction error", Integer.valueOf(result.get("C").toString()).equals(1));
        logger.info("test transaction ok");
    }

}
