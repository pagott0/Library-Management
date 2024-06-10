import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Classe Library que implementa Serializable para permitir a serialização dos objetos
public class Library implements Serializable {
    // Listas para armazenar livros, patronos, empréstimos e usuários
    private List<Book> books;
    private List<Patron> patrons;
    private List<Loan> loans;
    private User loggedInUser;
    private List<User> users;

    // Construtor da classe Library, inicializa as listas e carrega os usuários a partir de um arquivo
    public Library() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.loggedInUser = null;
        this.users = new ArrayList<>();
        loadUsersFromFile("users.dat");
    }

    // ------------------------ Métodos relacionados à gestão de usuários ------------------------
    // Adiciona um usuário à lista
    public void addUser(User user) {
        users.add(user);
    }

    // Autentica um usuário verificando o nome de usuário e senha
    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                return user;
            }
        }
        return null;
    }

    // Retorna o usuário atualmente logado
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Faz logout do usuário atual
    public void logout() {
        loggedInUser = null;
    }

    // Registra um novo usuário, salva os usuários no arquivo após o registro
    public boolean registerUser(String username, String password, String role) {
        users.add(new User(username, password, role));
        saveUsersToFile("users.dat");
        return true;
    }

    // --------------------- Métodos para gerenciamento de livros ---------------------
    // Retorna todos os livros, ou apenas os livros do usuário logado se ele não for admin
    public List<Book> getAllBooks(User currentUser) {
        if (currentUser.getRole().equals("admin")) {
            return books;
        } else {
            List<Book> userBooks = new ArrayList<>();
            for (Book book : books) {
                if (book.getUserDataOwner().equals(currentUser.getUsername())) {
                    userBooks.add(book);
                }
            }
            return userBooks;
        }
    }

    // Adiciona um livro se não houver um livro com o mesmo ISBN do mesmo usuário
    public boolean addBook(Book book) {
        for (Book b : books) {
            if (b.getISBN().equals(book.getISBN()) && (loggedInUser.getRole().equals("admin") || b.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return false;
            }
        }
        books.add(book);
        return true;
    }

    // Busca um livro pelo ISBN, considerando as permissões do usuário logado
    public Book searchBook(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn) && (loggedInUser.getRole().equals("admin") || book.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return book;
            }
        }
        return null;
    }

    // Deleta um livro pelo ISBN
    public boolean deleteBook(String isbn) {
        Book book = searchBook(isbn);
        if (book != null) {
            books.remove(book);
            return true;
        }
        return false;
    }

    // Atualiza as informações de um livro
    public boolean updateBook(Book updatedBook) {
        for (Book book : books) {
            if (book.getISBN().equals(updatedBook.getISBN()) && (loggedInUser.getRole().equals("admin") || book.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                return true;
            }
        }
        return false;
    }

    // Busca livros pelo título
    public ArrayList<Book> searchBookByTitle(String title) {
        ArrayList<Book> booksList = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && (loggedInUser.getRole().equals("admin") || book.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                booksList.add(book);
            }
        }
        return booksList;
    }

    // Busca livros pelo autor
    public ArrayList<Book> searchBookByAuthor(String author) {
        ArrayList<Book> booksList = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author) && (loggedInUser.getRole().equals("admin") || book.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                booksList.add(book);
            }
        }
        return booksList;
    }

    // Busca um livro pelo ISBN
    public Book searchBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN) && (loggedInUser.getRole().equals("admin") || book.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return book;
            }
        }
        return null;
    }

    // Busca livros pela categoria
    public ArrayList<Book> searchBookByCategory(String category) {
        ArrayList<Book> booksList = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category) && (loggedInUser.getRole().equals("admin") || book.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                booksList.add(book);
            }
        }
        return booksList;
    }

    // ---------------------- Métodos para gerenciamento de empréstimos ------------------------
    // Retorna todos os empréstimos, ou apenas os empréstimos do usuário logado se ele não for admin
    public List<Loan> getAllLoans(User currentUser) {
        if (currentUser.getRole().equals("admin")) {
            return loans;
        } else {
            List<Loan> userLoans = new ArrayList<>();
            for (Loan loan : loans) {
                if (loan.getUserDataOwner().equals(currentUser.getUsername())) {
                    userLoans.add(loan);
                }
            }
            return userLoans;
        }
    }

    // Realiza o empréstimo de um livro, verificando se o livro está disponível e se o patrono existe
    public boolean checkOutBook(String isbn, String patronName, Date dueDate, String currentUser) {
        Book book = searchBookByISBN(isbn);
        Patron patron = searchPatronByName(patronName);

        if (book != null && patron != null && !isBookLoaned(isbn)) {
            Loan loan = new Loan(book, patron, new Date(), dueDate, currentUser);
            loans.add(loan);
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    // Realiza a devolução de um livro
    public boolean checkInBook(String isbn, String patronName) {
        for (Loan loan : loans) {
            if (loan.getBook().getISBN().equals(isbn) && loan.getPatron().getName().equals(patronName) && !loan.isReturned() && (loggedInUser.getRole().equals("admin") || loan.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                loan.returnBook(new Date());
                loan.getBook().setAvailable(true);
                return true;
            }
        }
        return false;
    }

    // Verifica se um livro está emprestado
    private boolean isBookLoaned(String isbn) {
        for (Loan loan : loans) {
            if (loan.getBook().getISBN().equals(isbn) && !loan.isReturned() && (loggedInUser.getRole().equals("admin") || loan.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return true;
            }
        }
        return false;
    }

    // Deleta uma multa por atraso, definindo a data de devolução como a data de vencimento
    public boolean deleteOverdueFine(int id) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.isOverdue() && (loggedInUser.getRole().equals("admin") || loan.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                count++;
                if (count == id) {
                    loan.setReturnDate(loan.getDueDate());
                    return true;
                }
            }
        }
        return false;
    }

    // ----------------------------- Métodos para gerenciamento de patronos ----------------------------
    // Retorna todos os patronos, ou apenas os patronos do usuário logado se ele não for admin
    public List<Patron> getAllPatrons(User currentUser) {
        if (currentUser.getRole().equals("admin")) {
            return patrons;
        } else {
            List<Patron> userPatrons = new ArrayList<>();
            for (Patron patron : patrons) {
                if (patron.getUserDataOwner().equals(currentUser.getUsername())) {
                    userPatrons.add(patron);
                }
            }
            return userPatrons;
        }
    }

    // Busca um patrono pelo nome e contato
    public Patron searchPatron(String name, String contactInfo) {
        for (Patron patron : patrons) {
            if (patron.getName().equals(name) && patron.getContactInfo().equals(contactInfo) && (loggedInUser.getRole().equals("admin") || patron.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return patron;
            }
        }
        return null;
    }

    // Deleta um patrono pelo nome e contato
    public boolean deletePatron(String name, String contactInfo) {
        Patron patron = searchPatron(name, contactInfo);
        if (patron != null) {
            patrons.remove(patron);
            return true;
        }
        return false;
    }

    // Adiciona um novo patrono
    public boolean addPatron(Patron patron) {
        for (Patron p : patrons) {
            if ((p.getName().equalsIgnoreCase(patron.getName()) || p.getContactInfo().equalsIgnoreCase(patron.getContactInfo())) && (loggedInUser.getRole().equals("admin") || p.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return false;
            }
        }
        patrons.add(patron);
        return true;
    }

    // Edita as informações de um patrono
    public void editPatron(String name, String newName, String newContactInfo) {
        for (Patron patron : patrons) {
            if (patron.getName().equalsIgnoreCase(name) && (loggedInUser.getRole().equals("admin") || patron.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                patron.setName(newName);
                patron.setContactInfo(newContactInfo);
                return;
            }
        }
    }

    // Busca um patrono pelo nome
    public Patron searchPatronByName(String name) {
        for (Patron patron : patrons) {
            if (patron.getName().equalsIgnoreCase(name) && (loggedInUser.getRole().equals("admin") || patron.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return patron;
            }
        }
        return null;
    }

    // Busca um patrono pelo contato
    public Patron searchPatronByContactInfo(String contactInfo) {
        for (Patron patron : patrons) {
            if (patron.getContactInfo().equalsIgnoreCase(contactInfo) && (loggedInUser.getRole().equals("admin") || patron.getUserDataOwner().equals(loggedInUser.getUsername()))) {
                return patron;
            }
        }
        return null;
    }

    // ---------------------- Métodos para salvar e carregar dados de arquivos ----------------------------
    // Salva a lista de livros em um arquivo
    public void saveBooksToFile(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(books);
            System.out.println("Books saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a lista de livros de um arquivo
    @SuppressWarnings("unchecked")
    public void loadBooksFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            books = (List<Book>) inputStream.readObject();
            System.out.println("Books loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Books could not be loaded from the database, file 'books.dat' not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Salva a lista de patronos em um arquivo
    public void savePatronsToFile(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(patrons);
            System.out.println("Patrons saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a lista de patronos de um arquivo
    public void loadPatronsFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            patrons = (List<Patron>) inputStream.readObject();
            System.out.println("Patrons loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Patrons could not be loaded from the database, file 'patrons.dat' not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Salva a lista de empréstimos em um arquivo
    public void saveLoansToFile(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(loans);
            System.out.println("Loans saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a lista de empréstimos de um arquivo
    public void loadLoansFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            loans = (List<Loan>) inputStream.readObject();
            System.out.println("Loans loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Loans could not be loaded from the database, file 'loans.dat' not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Salva a lista de usuários em um arquivo
    public void saveUsersToFile(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(users);
            System.out.println("Users saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a lista de usuários de um arquivo
    public void loadUsersFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            users = (List<User>) inputStream.readObject();
            System.out.println("Users loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Users could not be loaded from the database, file 'users.dat' not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
