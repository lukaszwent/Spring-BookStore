package com.example.shopApp.books;

import com.example.shopApp.shoppingCart.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final ShoppingCartService cartService;
    private final BookService bookService;


    @PostMapping(consumes = {"application/x-www-form-urlencoded"} )
    void addBook(Book addedBook, Model model, HttpServletResponse response) throws IOException {
        Book book = bookService.insertBook(addedBook);

        if(book == null){
            model.addAttribute("error","Nie można dodać książki która juz istnieje w bazie");
        }

        response.sendRedirect("/books");
    }

    @GetMapping("/{id}")
    String getOneBook(@PathVariable int id, Model model){

        Book book = bookService.getBook(id);

        if(book ==null){
            return "books";
        }

        model.addAttribute("cart",cartService.getNumberOfItems());
        model.addAttribute("TYPE","normal");
        model.addAttribute("book",book);
        return "book";
    }

    @GetMapping
    String getBooks(Pageable page,Model model){

        page = PageRequest.of(page.getPageNumber(),12);

        Page<Book> bookList = bookService.findSomeBooks(page);

        model.addAttribute("TYPE","normal");
        model.addAttribute("books",bookList);
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "books";
    }

    @GetMapping("/bestsellers")
    String getBestsellers(Pageable page,Model model){

        page = PageRequest.of(page.getPageNumber(),12);

        Page<Book> bookList = bookService.findBestsellers(page);

        model.addAttribute("TYPE","bestsellers");
        model.addAttribute("books",bookList);
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "books";
    }

    @GetMapping("/new")
    String getNewBooks(Pageable page, Model model){

        page = PageRequest.of(page.getPageNumber(),12, Sort.Direction.DESC,"date");

        Page<Book> bookList = bookService.findNewBooks(page);

        model.addAttribute("TYPE","new");
        model.addAttribute("books",bookList);
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "books";
    }

    @GetMapping("/promotion")
    String getBooksWithPromotion(Pageable page,Model model){
        page = PageRequest.of(page.getPageNumber(),12);

        Page <Book> bookList = bookService.findPromotionBooks(page);

        model.addAttribute("TYPE","promotion");
        model.addAttribute("books",bookList);
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "books";
    }

    @GetMapping("/categories/{category}")
    String getBooksCategories(@PathVariable String category,Pageable page,Model model){

            page = PageRequest.of(page.getPageNumber(),12);
            Page<Book> bookList = bookService.findBookByCategory(category,page);

            model.addAttribute("categories",bookService.getAllCategories());
        model.addAttribute("books",bookList);
        model.addAttribute("TYPE","categories");
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "books";
    }

    @GetMapping("/categories")
    String getBooksCategories(Pageable page,Model model){

        page = PageRequest.of(page.getPageNumber(),12);
       Page<Book> bookList = bookService.findSomeBooks(page);

        model.addAttribute("categories",bookService.getAllCategories());
        model.addAttribute("books",bookList);
        model.addAttribute("TYPE","categories");
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "books";
    }

    @PostMapping ("/{id}/delete")
    void removeBook(@PathVariable int id, Model model, HttpServletResponse response) throws IOException {

        bookService.removeBook(id);
        response.sendRedirect("/books");
    }

    @PostMapping(path = "/{id}/change",consumes = {"application/x-www-form-urlencoded"} )
    void updateBook(@PathVariable int id,Book updatedBook,Model model, HttpServletResponse response) throws IOException {

        bookService.update(id,updatedBook);
        response.sendRedirect("/books/"+id);
    }

    @GetMapping(path = "/form/new")
    String showAddingForm(Model model){

        model.addAttribute("cart",cartService.getNumberOfItems());
        model.addAttribute("book",new Book());
        return "formAddBook";
    }

    @GetMapping(path = "/{id}/edit")
    String showEditForm(@PathVariable int id, Model model){

        Book book = bookService.getBook(id);
        model.addAttribute("book",book);
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "formUpdateBook";
    }
}
