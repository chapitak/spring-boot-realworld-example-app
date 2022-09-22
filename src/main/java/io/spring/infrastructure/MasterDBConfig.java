package io.spring.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(
    entityManagerFactoryRef = "MainEntityManager",
    transactionManagerRef = "MainTransactionManager",
    basePackages = "io.spring.core")
public class MasterDBConfig {

  @Autowired private Environment env;

  @Primary
  @Bean
  public DataSource MainDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("spring.datasource.url"));
    dataSource.setUsername(env.getProperty("spring.datasource.username"));
    dataSource.setPassword(env.getProperty("spring.datasource.password"));
    return dataSource;
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean MainEntityManager() {
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    HashMap<String, Object> properties = new HashMap<>();
    localContainerEntityManagerFactoryBean.setDataSource(MainDataSource());
    localContainerEntityManagerFactoryBean.setPackagesToScan("io.spring.core");
    localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
    //        properties.put("hibernate.hbm2ddl.auto",
    // env.getProperty("spring.main.hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
    localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);
    return localContainerEntityManagerFactoryBean;
  }

  @Primary
  @Bean
  public PlatformTransactionManager MainTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(MainEntityManager().getObject());
    return transactionManager;
  }
}
