package com.quan.nguyen.demo.replication.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryRead",
        transactionManagerRef = "transactionManagerRead",
        basePackages = {"com.quan.nguyen.demo.replication.repo.read"}
)
public class DataSourceReadConfig {

    @Bean(name = "dataSourceRead")
    @ConfigurationProperties(value = "spring.datasource-read")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryRead")
    @ConfigurationProperties(value = "spring")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier("dataSourceRead") DataSource dataSource, JpaVendorAdapter jpaVendor) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(jpaVendor);
        em.setPackagesToScan("com.quan.nguyen.demo.replication.repo.read", "com.quan.nguyen.demo.replication.core.model");

        return em;
    }


    @Bean(name = "transactionManagerRead")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryRead") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}