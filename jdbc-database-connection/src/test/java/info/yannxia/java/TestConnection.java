package info.yannxia.java;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Yann.Xia on 2015/7/26.
 */
public class TestConnection extends BaseTestConfig {

    @Test
    public void testConnection() {
        HSQLDBJdbcConnection HSQLDBJdbcConnection = new HSQLDBJdbcConnection();
        Map<String, Object> result = HSQLDBJdbcConnection.executeQuery("select * from T_SAMPLE");

        Assert.assertTrue("test connection error", result.get("NAME").equals("hello"));
        logger.info("test connection ok");
    }

}
