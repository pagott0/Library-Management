import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainGUI {
    private Library library;
    private User currentUser;

    public MainGUI() {
        this.library = new Library();
        loadLibraryData();
        showLoginScreen();
    }

    private void loadLibraryData() {
        library.loadBooksFromFile("books.dat");
        library.loadPatronsFromFile("patrons.dat");
        library.loadLoansFromFile("loans.dat");
        library.loadUsersFromFile("users.dat");
    }

    private void saveLibraryData() {
        library.saveBooksToFile("books.dat");
        library.savePatronsToFile("patrons.dat");
        library.saveLoansToFile("loans.dat");
        library.saveUsersToFile("users.dat");
    }

    private void showLoginScreen() {
        JFrame frame = new JFrame("Library Management System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                currentUser = library.authenticateUser(username, password);

                if (currentUser != null) {
                    frame.dispose();
                    initGUI();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterScreen();
            }
        });
        panel.add(registerButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showRegisterScreen() {
      JFrame frame = new JFrame("Library Management System - Register");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(400, 300);
  
      JPanel panel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(5, 5, 5, 5);
  
      gbc.gridx = 0;
      gbc.gridy = 0;
      panel.add(new JLabel("Username:"), gbc);
      gbc.gridx = 1;
      JTextField usernameField = new JTextField(20);
      panel.add(usernameField, gbc);
  
      gbc.gridx = 0;
      gbc.gridy = 1;
      panel.add(new JLabel("Password:"), gbc);
      gbc.gridx = 1;
      JPasswordField passwordField = new JPasswordField(20);
      panel.add(passwordField, gbc);
  
      gbc.gridx = 0;
      gbc.gridy = 2;
      panel.add(new JLabel("Confirm Password:"), gbc);
      gbc.gridx = 1;
      JPasswordField confirmPasswordField = new JPasswordField(20);
      panel.add(confirmPasswordField, gbc);
  
      gbc.gridx = 0;
      gbc.gridy = 3;
      panel.add(new JLabel("Role:"), gbc);
      gbc.gridx = 1;
      JPanel rolePanel = new JPanel(new GridLayout(1, 2));
      JRadioButton adminRadioButton = new JRadioButton("Admin");
      JRadioButton librarianRadioButton = new JRadioButton("Librarian");
      ButtonGroup roleGroup = new ButtonGroup();
      roleGroup.add(adminRadioButton);
      roleGroup.add(librarianRadioButton);
      rolePanel.add(adminRadioButton);
      rolePanel.add(librarianRadioButton);
      panel.add(rolePanel, gbc);
  
      gbc.gridx = 0;
      gbc.gridy = 4;
      gbc.gridwidth = 2;
      JButton registerButton = new JButton("Register");
      registerButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String username = usernameField.getText();
              String password = new String(passwordField.getPassword());
              String confirmPassword = new String(confirmPasswordField.getPassword());
              String role = adminRadioButton.isSelected() ? "admin" : "librarian";
  
              if (!password.equals(confirmPassword)) {
                  JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                  return;
              }
  
              if (library.registerUserWithReturn(username, password, role)) {
                  JOptionPane.showMessageDialog(frame, "Registration successful. You can now log in.");
                  frame.dispose();
              } else {
                  JOptionPane.showMessageDialog(frame, "Registration failed. Username might be taken.");
              }
          }
      });
      panel.add(registerButton, gbc);
  
      frame.add(panel);
      frame.setVisible(true);
  }
  

    private void initGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Books", createBookPanel());
        tabbedPane.addTab("Patrons", createPatronPanel());
        tabbedPane.addTab("Loans", createLoanPanel());
        tabbedPane.addTab("Overdue Fines", createOverdueFinesPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(this::saveLibraryData));
    }

    JTextArea overdueFines = new JTextArea(10, 50); // fora do método createOverdueFinesPanel para conseguir atualizar nos check outs e check ins na pág loans usando o método updateOverdueFines
    private JPanel createOverdueFinesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        overdueFines.setEditable(false);
        panel.add(new JScrollPane(overdueFines), BorderLayout.CENTER);

        updateOverdueFines(overdueFines);

        return panel;
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        JTextField authorField = new JTextField(20);
        formPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnField = new JTextField(20);
        formPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JTextField categoryField = new JTextField(20);
        formPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                String category = categoryField.getText();

                if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "All fields are required.");
                    return;
                }

                boolean sucessfulAddedBook = library.addBook(new Book(title, author, isbn, category));
                if(sucessfulAddedBook) {
                  JOptionPane.showMessageDialog(panel, "Book added successfully!");
                } else {
                  JOptionPane.showMessageDialog(panel, "Book with this ISBN already exists.");
                }
            }
        });
        formPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("ISBN must be unique, you can not add a book with a existing ISBN"), gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Search fields
        JPanel searchPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Criteria:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Title", "Author", "ISBN", "Category"});
        searchPanel.add(searchCriteria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton searchButton = new JButton("Search");
        JTextArea searchResults = new JTextArea(10, 50);
        searchResults.setEditable(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                String criteria = (String) searchCriteria.getSelectedItem();
                ArrayList<Book> books = null;
                Book book = null;

                switch (criteria) {
                    case "ISBN":
                        book = library.searchBookByISBN(query);
                        break;
                    case "Title":
                        books = library.searchBookByTitle(query);
                        break;
                    case "Author":
                        books = library.searchBookByAuthor(query);
                        break;
                    case "Category":
                        books = library.searchBookByCategory(query);
                        break;
                }

                if (criteria != "ISBN" && books != null && books.size() > 0) {
                  String bookString = "";
                  for (Book bookItem : books) {
                    bookString += bookItem.toString() + "\n\n";
                  }
                  searchResults.setText(bookString);
                } else if (criteria == "ISBN" && book != null) {
                  searchResults.setText(book.toString());
                } else {
                    searchResults.setText("No results found.");
                }
            }
        });
        searchPanel.add(searchButton, gbc);

        JButton showAllBooksButton = new JButton("Show all books");
        showAllBooksButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String query = searchField.getText();
              String criteria = (String) searchCriteria.getSelectedItem();
              List<Book> books = library.getAllBooks();

              if (books != null && books.size() > 0) {
                String bookString = "";
                for (Book bookItem : books) {
                  bookString += bookItem.toString() + "\n\n";
                }
                searchResults.setText(bookString);
              } else {
                  searchResults.setText("No results found.");
              }
          }
      });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchPanel.add(showAllBooksButton, gbc);

        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(searchResults), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPatronPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Contact:"), gbc);
        gbc.gridx = 1;
        JTextField contactField = new JTextField(20);
        formPanel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Patron");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String contact = contactField.getText();

                if(name.isEmpty() || contact.isEmpty()) {
                  JOptionPane.showMessageDialog(panel, "Name and contact info cannot be empty.");
                  return;
                }

                boolean patronAdded = library.addPatron(new Patron(name, contact));
                if(patronAdded) {
                  JOptionPane.showMessageDialog(panel, "Patron added successfully!");
                } else {
                  JOptionPane.showMessageDialog(panel, "Patron with this name or contact info already exists.");
                }
            }
        });
        formPanel.add(addButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Note: Patron with the same name or contact info cannot be added."), gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Search fields
        JPanel searchPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Criteria:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Name", "Contact Information"});
        searchPanel.add(searchCriteria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton searchButton = new JButton("Search");
        JTextArea searchResults = new JTextArea(10, 50);
        searchResults.setEditable(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                String criteria = (String) searchCriteria.getSelectedItem();
                Patron patron = null;

                switch (criteria) {
                    case "Name":
                        patron = library.searchPatronByName(query);
                        break;
                    case "Contact Information":
                        patron = library.searchPatronByContactInfo(query);
                        break;
                }

                if (patron != null) {
                    searchResults.setText(patron.toString());
                } else {
                    searchResults.setText("No results found.");
                }
            }
        });
        searchPanel.add(searchButton, gbc);

        JButton showAllPatronsButton = new JButton("Show all patrons");
        showAllPatronsButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              List<Patron> patrons = library.getAllPatrons();

              if (patrons != null && patrons.size() > 0) {
                String patronString = "";
                for (Patron patronItem : patrons) {
                  patronString += patronItem.toString() + "\n\n";
                }
                searchResults.setText(patronString);
              } else {
                  searchResults.setText("No results found.");
              }
          }
      });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchPanel.add(showAllPatronsButton, gbc);

        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(searchResults), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Book ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnField = new JTextField(20);
        formPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Patron Name:"), gbc);
        gbc.gridx = 1;
        JTextField patronIdField = new JTextField(20);
        formPanel.add(patronIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Due Date (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        JTextField dueDateField = new JTextField(20);
        formPanel.add(dueDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JTextArea activeLoans = new JTextArea(10, 50);
        JButton checkoutButton = new JButton("Check Out");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String patronId = patronIdField.getText();
                String dueDateStr = dueDateField.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date dueDate = sdf.parse(dueDateStr);
                    if (library.checkOutBookWithReturn(isbn, patronId, dueDate)) {
                        JOptionPane.showMessageDialog(panel, "Book checked out successfully!");
                        updateActiveLoans(activeLoans);
                        updateOverdueFines(overdueFines);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Failed to check out book.");
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid date format.");
                }
            }
        });
        formPanel.add(checkoutButton, gbc);

        gbc.gridy = 4;
        JButton checkinButton = new JButton("Check In");
        checkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();

                if (library.checkInBookWithReturn(isbn)) {
                    JOptionPane.showMessageDialog(panel, "Book checked in successfully!");
                    updateActiveLoans(activeLoans);
                    updateOverdueFines(overdueFines);
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to check in book.");
                }
            }
        });
        formPanel.add(checkinButton, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // List of active loans
        activeLoans.setEditable(false);
        panel.add(new JScrollPane(activeLoans), BorderLayout.CENTER);

        updateActiveLoans(activeLoans);


        return panel;
    }

    private void updateActiveLoans(JTextArea activeLoans) {
      List<Loan> loans = library.getAllLoans();
      StringBuilder sb = new StringBuilder();
      for (Loan loan : loans) {
          if (!loan.isReturned()) {
              Book book = loan.getBook();
              Patron patron = loan.getPatron();
              sb.append("Book: ").append(book.getTitle()).append(" | Author: ").append(book.getAuthor()).append(" | ISBN: ").append(book.getISBN()).append(" | Patron: ").append(patron.getName()).append(" | Contact Info: ").append(patron.getContactInfo()).append(" | Loan Emission Date: ").append(loan.getLoanDate()).append(" | Due Date: ").append(loan.getDueDate()).append("\n\n");
          }
      }
      activeLoans.setText(sb.toString());
  }

  private void updateOverdueFines(JTextArea overdueFines) {
      List<Loan> loans = library.getAllLoans();
      StringBuilder sb = new StringBuilder();
      for (Loan loan : loans) {
          if (loan.isOverdue()) {
              Book book = loan.getBook();
              Patron patron = loan.getPatron();
              sb.append("Book: ").append(book.getTitle()).append(" | Author: ").append(book.getAuthor()).append(" | ISBN: ").append(book.getISBN()).append(" | Patron: ").append(patron.getName()).append(" | Contact Info: ").append(patron.getContactInfo()).append(" | Due Date: ").append(loan.getDueDate()).append(" | Return Date: ").append(loan.getReturnDate()).append(" | Fine: $").append(loan.getFine()).append("\n\n");
          }
      }
      overdueFines.setText(sb.toString());
  }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
