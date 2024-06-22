
import java.io.Serializable;

/**
 * A classe Book representa um livro na biblioteca.
 * Ela contém informações sobre o título, autor, ISBN, categoria, disponibilidade e dono dos dados do usuário.
 * Esta classe implementa a interface Serializable para permitir a serialização dos objetos Book.
 */

public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private String category;
    private boolean isAvailable;

    //Construtor para os livros
    public Book(String title, String author, String ISBN, String category) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.category = category;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    //Crio o método toString com Override para definir como que seja o output dessa classe.
    @Override
    public String toString() {
        return "Book: " + title + " | Author: " + author + " | ISBN: " + ISBN + " | Category: " + category + " | Available: " + isAvailable;
    }

}
