package com.example.varauskalenteri;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.varauskalenteri.domain.Varaus;
import com.example.varauskalenteri.domain.VarausRepository;

import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VarausRepositoryTests {

    @Autowired
    private VarausRepository repository;

    @Test
    public void etsiTestiVaraus() {
        List<Varaus> varaus = repository.findByVaraaja("Testivaraaja");
        
        assertThat(varaus).hasSize(1);

    }
    /*
    @Test
    public void createAndDeleteBook() {
    	Varaus newbook = new Varaus("99999", "Valekirja", "Valekirjailija", "2021");
    	repository.save(newbook);
    	assertThat(newbook.getId()).isNotNull();
    	List<Varaus> varaus = repository.findByTitle("Valekirja");
        assertThat(varaus).hasSize(1);
    	repository.deleteById(newbook.getId());
    	List<Varaus> books2 = repository.findByTitle("Valekirja");
        assertThat(books2).hasSize(0);
    }
     */

}