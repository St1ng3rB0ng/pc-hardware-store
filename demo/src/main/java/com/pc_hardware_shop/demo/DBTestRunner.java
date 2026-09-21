package com.pc_hardware_shop.demo;

import com.pc_hardware_shop.demo.entity.Category;
import com.pc_hardware_shop.demo.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBTestRunner {
    @Bean
    public CommandLineRunner testDBConnection(CategoryRepository categoryRepository){
        return args -> {
//            Category c = new Category();
//            c.setName("Monitors");
//
//            Category savedCategory = categoryRepository.save(c);

            System.out.println("Table categories:");
            categoryRepository.findAll().forEach(
                    category -> {
                        System.out.println("ID: "+category.getId() +" | Name: "+category.getName());
                    }
            );
        };

    }
}
