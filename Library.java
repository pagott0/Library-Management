import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;

    public Library() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    // Book Management
    public void addBook(Book book) {
        books.add(book);
    }

    public void saveBooksToFile(String filename) {
      try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
          outputStream.writeObject(books);
          System.out.println("Books saved successfully!");
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @SuppressWarnings("unchecked")
  public void loadBooksFromFile(String filename) {
      try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
          books = (List<Book>) inputStream.readObject();
          System.out.println("Books loaded successfully!");
      } catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
      }
  }

  public void savePatronsToFile(String filename) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
        outputStream.writeObject(patrons);
        System.out.println("Patrons saved successfully!");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void loadPatronsFromFile(String filename) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
        patrons = (List<Patron>) inputStream.readObject();
        System.out.println("Patrons loaded successfully!");
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}

public void saveLoansToFile(String filename) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
        outputStream.writeObject(loans);
        System.out.println("Loans saved successfully!");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void loadLoansFromFile(String filename) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
        loans = (List<Loan>) inputStream.readObject();
        System.out.println("Loans loaded successfully!");
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}

    public void editBook(String ISBN, String title, String author, String category) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                book.setTitle(title);
                book.setAuthor(author);
                book.setCategory(category);
                return;
            }
        }
    }

    public void deleteBook(String ISBN) {
        books.removeIf(book -> book.getISBN().equals(ISBN));
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public Book searchBookByAuthor(String author) {
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                return book;
            }
        }
        return null;
    }

    public Book searchBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    public Book searchBookByCategory(String category) {
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                return book;
            }
        }
        return null;
    }

    // Patron Management
    public void addPatron(Patron patron) {
        patrons.add(patron);
    }

    public void editPatron(String name, String newName, String newContactInfo) {
        for (Patron patron : patrons) {
            if (patron.getName().equalsIgnoreCase(name)) {
                patron.setName(newName);
                patron.setContactInfo(newContactInfo);
                return;
            }
        }
    }

    public void deletePatron(String name) {
        patrons.removeIf(patron -> patron.getName().equalsIgnoreCase(name));
    }

    public Patron searchPatronByName(String name) {
        for (Patron patron : patrons) {
            if (patron.getName().equalsIgnoreCase(name)) {
                return patron;
            }
        }
        return null;
    }

    public Patron searchPatronByContactInfo(String contactInfo) {
        for (Patron patron : patrons) {
            if (patron.getContactInfo().equalsIgnoreCase(contactInfo)) {
                return patron;
            }
        }
        return null;
    }

    // Loan Management
    public void checkOutBook(String ISBN, String patronName) {
        Book book = searchBookByISBN(ISBN);
        Patron patron = searchPatronByName(patronName);

        if (book != null && book.isAvailable() && patron != null) {
            Loan loan = new Loan(book, patron, new java.util.Date());
            loans.add(loan);
            book.setAvailable(false);
        }
    }

    public void checkInBook(String ISBN) {
        for (Loan loan : loans) {
            if (loan.getBook().getISBN().equals(ISBN) && !loan.isReturned()) {
                loan.setReturned(true);
                loan.getBook().setAvailable(true);
                loan.setReturnDate(new java.util.Date());
                return;
            }
        }
    }

    public List<Loan> getOverdueLoans() {
        List<Loan> overdueLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isReturned() && isOverdue(loan)) {
                overdueLoans.add(loan);
            }
        }
        return overdueLoans;
    }

    private boolean isOverdue(Loan loan) {
        long loanPeriod = 14 * 24 * 60 * 60 * 1000L; // 2 weeks
        long currentTime = System.currentTimeMillis();
        long loanTime = loan.getLoanDate().getTime();
        return currentTime - loanTime > loanPeriod;
    }
}
