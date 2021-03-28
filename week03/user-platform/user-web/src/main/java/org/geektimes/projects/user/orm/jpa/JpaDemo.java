package org.geektimes.projects.user.orm.jpa;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.geektimes.projects.user.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class JpaDemo {
//    Q: 如何实现依赖注入？

//    Spring Data JPA 依赖注入
//    @Resource
//    private EntityManager entityManager;

//    @PersistenceContext(name = "emf")
//    private EntityManager entityManager;

//    @Resource(name = "primaryDataSource")
//    private DataSource dataSource;

    // 依赖注入的来源：EntityManagerFactory 是如何构建出来的，例如 Spring Data JPA 中是如何构建出来从而支持依赖注入的
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("emf", getProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User();
        user.setName("小马哥");
        user.setPassword("******");
        user.setEmail("mercyblitz@gmail.com");
        user.setPhoneNumber("123456789");

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(user);  // User#setId()
        transaction.commit();

        System.out.println(entityManager.find(User.class, user.getId()));
    }

    private static Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private static DataSource getDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("/Users/kai/data/derby/db/user-platform");
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}
