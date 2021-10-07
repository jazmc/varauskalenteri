package com.example.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository repository;

    @Test
    public void findBookByAuthor() {
        List<Book> books = repository.findByTitle("Muukalainen");
        
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Diana Gabaldon");
    }
    
    @Test
    public void createBook() {
    	Book newbook = new Book("99999", "Valekirja", "Valekirjailija", "2021");
    	repository.save(newbook);
    	assertThat(newbook.getId()).isNotNull();
    }
     

}