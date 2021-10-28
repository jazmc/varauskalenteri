package com.example.bookstore.web;

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

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@Controller
public class BookController {
	@Autowired
	private BookRepository repository;
	@Autowired
	private CategoryRepository catrep;
	
    @RequestMapping(value="/login")
    public String login() {	
        return "kirjaudu";
    }

	// perussivu /booklist
	@RequestMapping(value={"/index", "/booklist"})
	public String bookList(Model model) {
		// lisätään kirjat modeliin
		model.addAttribute("books", repository.findAll());
		model.addAttribute("categories", catrep.findAll());
		// booklist.html
		return "booklist";
	}

	// lisäyslomake /add
	@PostMapping("/add")
	public String postBook(String title, String author, String isbn, String year, String cat) {
		// luodaan kirjaolio parametreinä saaduista string-muuttujista
		Optional<Category> katsu = catrep.findById(Long.parseLong(cat));
		Category uusiKatsu = katsu.get();
		Book newbook = new Book(isbn, title, author, year, uusiKatsu);
		// tallennus
		repository.save(newbook);
		// uudelleenohjaus perussivulle
		return "redirect:/booklist";
	}
	
	@GetMapping("/edit/{id}/{thing}/{val}")
	public String editBook(@PathVariable String val, @PathVariable String thing, @PathVariable String id) {
		// id string longiksi
		long iidee = Long.parseLong(id);
		
		Optional<Book> paivitettava = repository.findById(iidee);
		Book paivitettavaKirja = paivitettava.get();
		
		switch(thing) {
			case "category":
				Optional<Category> katsu = catrep.findById(Long.parseLong(val));
				Category uusiKatsu = katsu.get();
				paivitettavaKirja.setCategory(uusiKatsu);
				break;
			case "title":
				paivitettavaKirja.setTitle(val);
				break;
			case "author":
				paivitettavaKirja.setAuthor(val);
				break;
			case "year":
				paivitettavaKirja.setYear(val);
				break;
			case "isbn":
				paivitettavaKirja.setIsbn(val);
				break;
		}
		
		repository.save(paivitettavaKirja);
		
		// uudelleenohjaus perussivulle
		return "redirect:/booklist";
	}
	
	// REST hae kirjat
	@GetMapping("/books")
    public @ResponseBody List<Book> booklistREST() {	
        return (List<Book>) repository.findAll();
    }    
	
	// REST hae kirja id:llä
	@GetMapping("/books/{id}")
    public @ResponseBody Optional<Book> findBookREST(@PathVariable("id") Long iidee) {	
    	return repository.findById(iidee);
    }   
	
	// poistolinkki /delete/id
	@GetMapping("/delete/{no}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteBook(@PathVariable String no) {
		// string longiksi
		long id = Long.parseLong(no);
		// poisto
		repository.deleteById(id);
		// uudelleenohjaus perussivulle
		return "redirect:/booklist";
	}
}