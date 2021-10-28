package com.example.varauskalenteri;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.varauskalenteri.web.VarausController;


/*
 * 
 * 	Application is running live at https://jazmcbookstore.herokuapp.com/
 * 
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VarauskalenteriApplicationTests {

    @Autowired
    private VarausController controller;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }	
}
