package com.example.varauskalenteri.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.varauskalenteri.domain.Varaus;
import com.example.varauskalenteri.domain.VarausRepository;
import com.example.varauskalenteri.domain.Kategoria;
import com.example.varauskalenteri.domain.KategoriaRepository;
import com.example.varauskalenteri.domain.User;
import com.example.varauskalenteri.domain.UserRepository;

@Controller
public class VarausController {
	@Autowired
	private VarausRepository repository;
	@Autowired
	private KategoriaRepository catrep;
	@Autowired
	private UserRepository urep;
	
    @RequestMapping(value="/login")
    public String login() {	
        return "kirjaudu";
    }

	// perussivu
	@RequestMapping(value={"/index", "/varauskalenteri"})
	public String varausList(Model model, @RequestParam(value = "v", required = false) String v, @RequestParam(value = "kk", required = false) String kk) {
		// varausten, kategorioiden ja nykyhetken modeliin lisäys tehdään aina:
		model.addAttribute("varaukset", repository.findAll());
		model.addAttribute("kategoriat", catrep.findAll());
		model.addAttribute("nykyhetki", LocalDateTime.now());
		
		LocalDate kkeka;
		int vikapv;
		
		// kuukausi- ja vuositiedot tai niiden puuttuminen
		if (kk == null && v == null) { // tämä vuosi, tämä kuukausi
			Calendar cal = Calendar.getInstance();
			vikapv = cal.getActualMaximum(Calendar.DATE);
			model.addAttribute("vikapv", vikapv);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			
			LocalDate nyt = LocalDate.now();
			kkeka = nyt.with(TemporalAdjusters.firstDayOfMonth());
			model.addAttribute("kkeka", kkeka);

		} else if (v != null && kk != null) { // vuosi ja kuukausi annettu
			
			Calendar cal = Calendar.getInstance();
			int kuukausi = Integer.parseInt(kk);
			cal.set(Calendar.MONTH, kuukausi-1);
			int vuosi = Integer.parseInt(v);
			cal.set(Calendar.YEAR, vuosi);
			
			vikapv = cal.getActualMaximum(Calendar.DATE);
			model.addAttribute("vikapv", vikapv);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			
			kkeka = LocalDate.of(vuosi, kuukausi, 1);
			model.addAttribute("kkeka", kkeka);
			
		} else { // tämä vuosi, kuukausi annettu
			Calendar cal = Calendar.getInstance();
			int kuukausi = Integer.parseInt(kk);
			cal.set(Calendar.MONTH, kuukausi-1);
			
			vikapv = cal.getActualMaximum(Calendar.DATE);
			model.addAttribute("vikapv", vikapv);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			
			int haluttuvuosi = cal.get(Calendar.YEAR);
			
			kkeka = LocalDate.of(haluttuvuosi, kuukausi, 1);
			model.addAttribute("kkeka", kkeka);
			
		}
		// return .html
		return "varauskalenteri";
	}
	

	// lisäyslomake /add
	@PostMapping("/add")
	public String postVaraus(String salku, String sloppu, String selitys, String u, String cat) {
		// lasketaan timezone offset UTC:sta
		TimeZone tz = TimeZone.getTimeZone("Europe/Helsinki");
		int offset = tz.getOffset(new Date().getTime()) / 1000 / 60;

		// luodaan ldt oliot parametreinä saaduista string-muuttujista
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		
		LocalDateTime alku = LocalDateTime.parse(salku, formatter);
		LocalDateTime loppu = LocalDateTime.parse(sloppu, formatter);
		
		// aikavyöhykkeen korjaus UTC-offsetilla
		alku = alku.plusMinutes(offset);
		loppu = loppu.plusMinutes(offset);
		
		// kaiva kategoria ja user
		Optional<Kategoria> katsu = catrep.findById(Long.parseLong(cat));
		Kategoria uusiKatsu = katsu.get();
		User user = urep.findByUsername(u);
		
		Varaus uusvaraus = new Varaus(alku, loppu, selitys, user, uusiKatsu);
		// tallennus
		repository.save(uusvaraus);
		// uudelleenohjaus perussivulle
		return "redirect:/varauskalenteri";
	}
	
	// varauksen muokkaus
	@PostMapping("/edit")
	public String editVaraus(String malku, String mloppu, String mselitys, Long mid) {
		// lasketaan timezone offset UTC:sta
		TimeZone tz = TimeZone.getTimeZone("Europe/Helsinki");
		int offset = tz.getOffset(new Date().getTime()) / 1000 / 60;
		
		// luodaan ldt oliot parametreinä saaduista string-muuttujista
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		
		LocalDateTime alku = LocalDateTime.parse(malku, formatter);
		LocalDateTime loppu = LocalDateTime.parse(mloppu, formatter);
		
		// aikavyöhykkeen korjaus UTC-offsetilla
		alku = alku.plusMinutes(offset);
		loppu = loppu.plusMinutes(offset);

		// etsitään varaus
		Optional<Varaus> muokattava = repository.findById(mid);
		Varaus muoks = muokattava.get();
		
		// set-kutsut tietojen päivitystä varten
		muoks.setAlku(alku);
		muoks.setLoppu(loppu);
		muoks.setSelitys(mselitys);
		
		repository.save(muoks);

		// uudelleenohjaus perussivulle
		return "redirect:/varauskalenteri";
	}
	
	// REST hae varaukset
	@GetMapping("/varaukset")
    public @ResponseBody List<Varaus> varauskalenteriREST() {	
        return (List<Varaus>) repository.findAll();
    }    
	
	// REST hae varaus id:llä
	@GetMapping("/varaukset/{id}")
    public @ResponseBody Optional<Varaus> etsiVarausREST(@PathVariable("id") Long iidee) {
    	return repository.findById(iidee);
    }   
	
	// REST hae varaus id:llä
	@GetMapping("/kayttajat/{uname}")
    public @ResponseBody User etsiUserREST(@PathVariable("uname") String uname) {
    	return urep.findByUsername(uname);
    } 
	
	// poistolinkki /delete/id
	@GetMapping("/delete/{no}")
	public String deleteVaraus(@PathVariable String no) {
		// string longiksi
		long id = Long.parseLong(no);
		// poisto
		repository.deleteById(id);
		// uudelleenohjaus perussivulle
		return "redirect:/varauskalenteri";
	}
}