package io.stoptheworld.dbtest.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    private static final String DEFAULT_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";

    @Bean(name = "testDataSource")
    @ConfigurationProperties(prefix = "datasource.test")
    public DataSource getTestDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "testEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getTestEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, String> propertiesHashMap = new HashMap<>();
        propertiesHashMap.put("hibernate.ejb.naming_strategy", DEFAULT_NAMING_STRATEGY);
        return builder.dataSource(getTestDataSource())
                .packages("io.stoptheworld.dbtest.domain.test")
                .persistenceUnit("testPersistenceUnit")
                .properties(propertiesHashMap)
                .build();
    }

    @Bean(name = "testTransactionManager")
    PlatformTransactionManager getTestTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(getTestEntityManagerFactory(builder).getObject());
    }

    @Configuration
    @EnableJpaRepositories(basePackages = { "io.stoptheworld.dbtest.repository.test", "io.stoptheworld.dbtest.domain.test" },
                           entityManagerFactoryRef = "testEntityManagerFactory",
                           transactionManagerRef = "testTransactionManager")
    static class TestDbJpaRepositoriesConfig { }

}
