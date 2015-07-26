package info.yannxia.java;

import info.yannxia.java.model.Product;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by Yann.Xia on 2015/7/26.
 */
public class TestHibernateConnection extends BaseTestConfig {

    @Test
    public void testHibernate() {
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        Product product = (Product) session.createQuery("from  Product ").list().get(0);
        session.getTransaction().commit();
        logger.debug("get product name :" + product.getName());
        logger.info("session is ok");
    }
}
