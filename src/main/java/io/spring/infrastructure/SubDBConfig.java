package io.spring.infrastructure;

import io.spring.core.articlehistory.ArticleHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "subEntityManager", transactionManagerRef = "subTransactionManager", basePackages = "io.spring.core.articlehistory")
public class SubDBConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource subDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.sub.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.sub.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.sub.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.sub.datasource.password"));

        return dataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean subEntityManager() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties = new HashMap<>();

        localContainerEntityManagerFactoryBean.setDataSource(subDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("io.spring.core");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.sub.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));

        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager subTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(subEntityManager().getObject());
        return transactionManager;
    }
}
