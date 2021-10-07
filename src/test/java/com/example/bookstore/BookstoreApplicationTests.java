package com.example.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bookstore.web.BookController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookstoreApplicationTests {

    @Autowired
    private BookController controller;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }	
}
