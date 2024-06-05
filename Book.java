
import java.io.Serializable;

public class Book implements Serializable {
  private String title;
  private String author;
  private String ISBN;
  private String category;
  private boolean isAvailable;

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

  @Override
  public String toString() {
      return "Book: " + title + " | Author: " + author + " | ISBN: " + ISBN + " | Category: " + category + " | Available: " + isAvailable;
  }

}
