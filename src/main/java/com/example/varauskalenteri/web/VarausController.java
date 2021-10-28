package com.example.varauskalenteri.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.varauskalenteri.domain.Varaus;
import com.example.varauskalenteri.domain.VarausRepository;
import com.example.varauskalenteri.domain.Kategoria;
import com.example.varauskalenteri.domain.KategoriaRepository;

@Controller
public class VarausController {
	@Autowired
	private VarausRepository repository;
	@Autowired
	private KategoriaRepository catrep;
	
    @RequestMapping(value="/login")
    public String login() {	
        return "kirjaudu";
    }

	// perussivu
	@RequestMapping(value={"/index", "/varauskalenteri"})
	public String varausList(Model model) {
		// lisätään varaukset modeliin
		model.addAttribute("varaukset", repository.findAll());
		model.addAttribute("kategoriat", catrep.findAll());
		// return .html
		return "varauskalenteri";
	}

	// lisäyslomake /add
	@PostMapping("/add")
	public String postVaraus(String alku, String loppu, String varaaja, String selitys, String cat) {
		// luodaan olio parametreinä saaduista string-muuttujista
		Optional<Kategoria> katsu = catrep.findById(Long.parseLong(cat));
		Kategoria uusiKatsu = katsu.get();
		Varaus uusvaraus = new Varaus(alku, loppu, varaaja, selitys, uusiKatsu);
		// tallennus
		repository.save(uusvaraus);
		// uudelleenohjaus perussivulle
		return "redirect:/varauskalenteri";
	}
	
	@GetMapping("/edit/{id}/{thing}/{val}")
	public String editVaraus(@PathVariable String val, @PathVariable String thing, @PathVariable String id) {
		// id string longiksi
		long iidee = Long.parseLong(id);
		
		Optional<Varaus> paivitettava = repository.findById(iidee);
		Varaus paivitettavaVaraus = paivitettava.get();
		
		switch(thing) {
			case "kategoria":
				Optional<Kategoria> katsu = catrep.findById(Long.parseLong(val));
				Kategoria uusiKatsu = katsu.get();
				paivitettavaVaraus.setCategory(uusiKatsu);
				break;
			case "alku":
				paivitettavaVaraus.setAlku(val);
				break;
			case "loppu":
				paivitettavaVaraus.setLoppu(val);
				break;
			case "varaaja":
				paivitettavaVaraus.setVaraaja(val);
				break;
			case "selitys":
				paivitettavaVaraus.setSelitys(val);
				break;
		}
		
		repository.save(paivitettavaVaraus);
		
		// uudelleenohjaus perussivulle
		return "redirect:/varauskalenteri";
	}
	
	// REST hae varaukset
	@GetMapping("/varaukset")
    public @ResponseBody List<Varaus> varauskalenteriREST() {	
        return (List<Varaus>) repository.findAll();
    }    
	
	// REST hae kirja id:llä
	@GetMapping("/books/{id}")
    public @ResponseBody Optional<Varaus> etsiVarausREST(@PathVariable("id") Long iidee) {	
    	return repository.findById(iidee);
    }   
	
	// poistolinkki /delete/id
	@GetMapping("/delete/{no}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteVaraus(@PathVariable String no) {
		// string longiksi
		long id = Long.parseLong(no);
		// poisto
		repository.deleteById(id);
		// uudelleenohjaus perussivulle
		return "redirect:/varauskalenteri";
	}
}