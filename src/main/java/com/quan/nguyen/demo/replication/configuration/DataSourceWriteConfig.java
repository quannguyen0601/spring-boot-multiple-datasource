package com.quan.nguyen.demo.replication.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryWrite",
        transactionManagerRef = "transactionManagerWrite",
        basePackages = {"com.quan.nguyen.demo.replication.repo.write"}
)
public class DataSourceWriteConfig {

    @Bean(name = "dataSourceWrite")
    @Primary
    @ConfigurationProperties(value = "spring.datasource-write")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryWrite")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier("dataSourceWrite") DataSource dataSource, JpaVendorAdapter jpaVendor) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(jpaVendor);
        em.setPackagesToScan("com.quan.nguyen.demo.replication.repo.write", "com.quan.nguyen.demo.replication.core.model");

        return em;
    }


    @Primary
    @Bean(name = "transactionManagerWrite")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryWrite") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}