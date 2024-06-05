import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Library implements Serializable {
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;
    private User loggedInUser;
    private List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.loggedInUser = null;
        this.users = new ArrayList<>();
        loadUsersFromFile("users.dat");
    }

    public void addUser(User user) {
      users.add(user);
  }

  public List<Loan> getAllLoans() {
    return loans;
  }

  public List<Book> getAllBooks() {
    return books;
  }

  public List<Patron> getAllPatrons() {
    return patrons;
  }

  public User authenticateUser(String username, String password) {
      for (User user : users) {
          if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
              loggedInUser = user;
              return user;
          }
      }
      return null;
  }

  public User getLoggedInUser() {
      return loggedInUser;
  }

  public void logout() {
      loggedInUser = null;
  }

  public void registerUser(String username, String password, String role) {
    users.add(new User(username, password, role));
    saveUsersToFile("users.dat"); // Salva imediatamente após registrar
}

public boolean registerUserWithReturn(String username, String password, String role) {
  users.add(new User(username, password, role));
  saveUsersToFile("users.dat"); // Salva imediatamente após registrar
  return true;
}

  public void saveUsersToFile(String filename) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
        outputStream.writeObject(users);
        System.out.println("Users saved successfully!");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void loadUsersFromFile(String filename) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
        users = (List<User>) inputStream.readObject();
        System.out.println("Users loaded successfully!");
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}

    // Book Management
    public boolean addBook(Book book) {
      for(Book b : books) {
          if(b.getISBN().equals(book.getISBN())) {
              return false;
          }
      }
        books.add(book);
        return true;
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

    public ArrayList<Book> searchBookByTitle(String title) {
        ArrayList<Book> booksList = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                booksList.add(book);
            }
        }
        return booksList;
    }

    public ArrayList<Book> searchBookByAuthor(String author) {
        ArrayList<Book> booksList = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                booksList.add(book);
            }
        }
        return booksList;
    }

    public Book searchBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    public ArrayList<Book> searchBookByCategory(String category) {
        ArrayList<Book> booksList = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                booksList.add(book);
            }
        }
        return booksList;
    }

    // Patron Management
    public boolean addPatron(Patron patron) {
        for (Patron p : patrons) {
            if (p.getName().equalsIgnoreCase(patron.getName()) || p.getContactInfo().equalsIgnoreCase(patron.getContactInfo())) {
                return false;
            }
        }
        patrons.add(patron);
        return true;
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
    /* public void checkOutBook(String ISBN, String patronName) {
        Book book = searchBookByISBN(ISBN);
        Patron patron = searchPatronByName(patronName);

        if (book != null && book.isAvailable() && patron != null) {
            Loan loan = new Loan(book, patron, new java.util.Date());
            loans.add(loan);
            book.setAvailable(false);
        }
    } */

    public boolean checkOutBookWithReturn(String isbn, String patronName, Date dueDate) {
      Book book = searchBookByISBN(isbn);
      Patron patron = searchPatronByName(patronName);

      if (book != null && patron != null && !isBookLoaned(isbn)) {
          Loan loan = new Loan(book, patron, new Date(), dueDate);
          loans.add(loan);
          book.setAvailable(false);
          return true;
      }
      return false;
  }

    /* public void checkInBook(String ISBN) {
        for (Loan loan : loans) {
            if (loan.getBook().getISBN().equals(ISBN) && !loan.isReturned()) {
                loan.setReturned(true);
                loan.getBook().setAvailable(true);
                loan.setReturnDate(new java.util.Date());
                return;
            }
        }
    } */

    public boolean checkInBookWithReturn(String isbn) {
      for (Loan loan : loans) {
          if (loan.getBook().getISBN().equals(isbn) && !loan.isReturned()) {
              loan.returnBook(new Date());
              loan.getBook().setAvailable(true);
              return true;
          }
      }
      return false;
  }

  private boolean isBookLoaned(String isbn) {
    for (Loan loan : loans) {
        if (loan.getBook().getISBN().equals(isbn) && !loan.isReturned()) {
            return true;
        }
    }
    return false;
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
