package com.wjiec.springaio.orm;

import com.wjiec.springaio.orm.model.Administrator;
import com.wjiec.springaio.orm.repository.AdministratorRepository;
import com.wjiec.springaio.orm.repository.JpaRepository;
import com.wjiec.springaio.orm.repository.NoSpringRepository;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println(dataSource);

        initScripts(dataSource);

        NoSpringRepository noSpringRepository = context.getBean(NoSpringRepository.class);
        Administrator administrator = noSpringRepository.save(Administrator.builder()
            .username("spring").password("spring")
            .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build());
        System.out.println(administrator);

        JpaRepository jpaRepository = context.getBean(JpaRepository.class);
        jpaRepository.addAdministrator(Administrator.builder()
            .username("spring").password("spring")
            .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build());
        System.out.println(noSpringRepository.findAll());
        System.out.println(jpaRepository.findById(administrator.getId()));

        AdministratorRepository administratorRepository = context.getBean(AdministratorRepository.class);
        System.out.println(administratorRepository);
        System.out.println(administratorRepository.findByUsernameOrPassword("admin", "wrong"));
        System.out.println(administratorRepository.findByUsernameOrPassword("wrong", "spring"));
        System.out.println(administratorRepository.findByUsernameIn(List.of("invalid", "user", "spring")));
        System.out.println(administratorRepository.findAllEqualsAccounts("spring"));
        administratorRepository.check();
    }

    private static void initScripts(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.setSeparator(";");
        populator.setSqlScriptEncoding("UTF-8");
        populator.addScript(new FileUrlResource(Application.class.getResource("/schema.sql")));
        populator.addScript(new FileUrlResource(Application.class.getResource("/data.sql")));

        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setDriverClassName("org.h2.Driver");
        properties.setUrl("jdbc:h2:mem:springaio");
        properties.setUsername("sa");
        properties.setSqlScriptEncoding(StandardCharsets.UTF_8);

        DataSourceBuilder<?> builder = properties.initializeDataSourceBuilder();

        return builder.build();
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(Application.class.getPackageName());
        factoryBean.setHibernateProperties(new Properties(){{
            setProperty("dialect", H2Dialect.class.getName());
            setProperty("show_sql", "true");
            setProperty("format_sql", "true");
            setProperty("current_session_context_class", "thread");
        }});

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(LocalSessionFactoryBean factoryBean) {
        return new HibernateTransactionManager(Objects.requireNonNull(factoryBean.getObject()));
    }

    @Bean
    public BeanPostProcessor persistentTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabase(Database.H2);
        vendorAdapter.setDatabasePlatform(H2Dialect.class.getName());

        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setPackagesToScan(Application.class.getPackageName());

        return factoryBean;
    }

}
