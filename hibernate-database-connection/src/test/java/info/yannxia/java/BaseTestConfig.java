package info.yannxia.java;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Yann.Xia on 2015/7/26.
 */
public class BaseTestConfig {

    protected Logger logger;

    protected SessionFactory sessionFactory;

    public BaseTestConfig() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Before
    public void initDB() {
        HSQLDBJdbcConnection HSQLDBJdbcConnection = new HSQLDBJdbcConnection();
        HSQLDBJdbcConnection.executeUpdate("CREATE TABLE T_PRODUCT (id INTEGER IDENTITY NOT NULL PRIMARY KEY, name varchar(255), quantity INTEGER);");
        HSQLDBJdbcConnection.executeUpdate("INSERT INTO T_PRODUCT VALUES('1', '理财产品', 100)");

        logger.info("HSQL init OK");
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        logger.info("hibernate loading ok");
    }

    @After
    public void clearDB() {
        HSQLDBJdbcConnection HSQLDBJdbcConnection = new HSQLDBJdbcConnection();
        HSQLDBJdbcConnection.executeUpdate("DROP TABLE T_PRODUCT");

        logger.info("clear db ok");
    }
}
