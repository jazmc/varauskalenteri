package com.example.varauskalenteri.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface VarausRepository extends CrudRepository<Varaus, Long> {
	
 List<Varaus> findByVaraaja(String varaaja);
 
}