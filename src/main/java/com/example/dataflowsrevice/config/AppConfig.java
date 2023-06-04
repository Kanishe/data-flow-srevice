package com.example.dataflowsrevice.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.yaml")
public class AppConfig {
    @Value("${spring.jpa.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.jpa.datasource.username}")
    private String username;

    @Value("${spring.jpa.datasource.password}")
    private String password;

    @Value("${spring.jpa.datasource.driverClassName}")
    private String driverClass;

    @Value("${spring.jpa.database-platform}")
    private String hibernateDialect;

    @Value("${spring.jpa.show-sql}")
    private String showSQL;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private String formatSql;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setUrl(datasourceUrl);
        return dataSource;
    }

    /**
     * Настраиваем hibernate
     *
     * @return
     */
    @Bean
    protected Properties hibernateProp() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("jpa.show-sql", showSQL);
        properties.put("hibernate.format_sql", formatSql);
        return properties;
    }

    /**
     * Создаем фабрику бинов для подключения к БД который использует созданный
     * <param> DataSource
     * LocalContainerEntityManagerFactoryBean является классом в Spring Framework, который предоставляет удобный способ
     * создания и настройки экземпляра EntityManagerFactory для JPA в контейнере приложений.
     * JPA (Java Persistence API) - это спецификация для работы с объектно-реляционным отображением (ORM) в Java
     * приложениях.
     * EntityManagerFactory представляет собой фабрику объектов EntityManager, которые используются для управления
     * персистентными сущностями в JPA.
     * LocalContainerEntityManagerFactoryBean предоставляет реализацию EntityManagerFactory для использования
     * в контейнере приложений, таком как Apache Tomcat или Spring Boot. Он выполняет следующие задачи:
     * Конфигурация и настройка: LocalContainerEntityManagerFactoryBean позволяет настраивать параметры JPA, такие как
     * тип базы данных, URL соединения, диалект SQL и другие свойства через удобный интерфейс. Он облегчает настройку
     * JPA для вашего приложения, скрывая детали конфигурации.
     * Создание EntityManagerFactory: LocalContainerEntityManagerFactoryBean создает и инициализирует экземпляр
     * EntityManagerFactory на основе указанных настроек. EntityManagerFactory является центральным объектом JPA,
     * который управляет жизненным циклом сущностей и выполнением операций с базой данных.
     * Интеграция с контейнером приложений: LocalContainerEntityManagerFactoryBean позволяет интегрировать
     * EntityManagerFactory с контейнером приложений, обеспечивая доступ к JNDI (Java Naming and Directory Interface)
     * и настройку транзакций через JTA (Java Transaction API). Это полезно, когда вы развертываете приложение
     * в контейнере приложений и хотите использовать возможности, предоставляемые контейнером.
     * Как результат, LocalContainerEntityManagerFactoryBean упрощает конфигурацию JPA в Spring-приложениях и
     * предоставляет центральный объект EntityManagerFactory, который может быть использован для работы с
     * персистентными сущностями в JPA.
     *
     * @return emf
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.dataflowsrevice");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(hibernateProp());
        return emf;
    }

    /**
     * JpaTransactionManager - это класс, который предоставляет управление транзакциями для приложений, использующих
     * Java Persistence API (JPA). JPA является стандартным интерфейсом для работы с базами данных в Java-приложениях.
     * JpaTransactionManager позволяет управлять транзакциями на уровне базы данных, обеспечивая целостность данных
     * и поддерживая ACID-свойства транзакций (атомарность, согласованность, изолированность и долговечность).*
     * Этот класс обычно используется в среде Spring Framework для управления транзакциями в приложении.
     * Он предоставляет методы для начала, фиксации и отката транзакций, а также позволяет настраивать различные
     * аспекты управления транзакциями, такие как изоляция и поведение в случае исключений.
     * В контексте JPA, JpaTransactionManager обеспечивает управление транзакциями на уровне EntityManager, который
     * является основным интерфейсом для выполнения операций с базой данных в JPA. Когда транзакция начинается,
     * JpaTransactionManager создает новый EntityManager или получает существующий из сеанса, связанного с текущим
     * потоком выполнения. Затем он управляет жизненным циклом транзакции, обеспечивая фиксацию или откат транзакции
     * при необходимости.
     * Использование JpaTransactionManager позволяет разработчикам легко управлять транзакциями в приложении на основе
     * JPA, обеспечивая надежность и целостность данных.
     *
     * @return jpaTransactionManager
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}