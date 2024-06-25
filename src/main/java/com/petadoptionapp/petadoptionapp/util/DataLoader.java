//package com.petadoptionapp.petadoptionapp.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//
//@Component
//public class DataLoader implements CommandLineRunner { // żeby opóźnić wczytywanie danych z data.sql
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    public void run(String... args) throws Exception {
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("src/main/resources/data.sql"));
//        resourceDatabasePopulator.execute(dataSource);
//    }
//}
