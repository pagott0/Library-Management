import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private static final String BOOKS_FILE = "books.dat";
    private static final String PATRONS_FILE = "patrons.dat";
    private static final String LOANS_FILE = "loans.dat";
    private static final String USERS_FILE = "users.dat";

    private Library library;

    public MainGUI() {
        this.library = new Library();
        library.addUser(new User("admin", "password", "Administrator")); //TODO: add users manually
        loadLibraryData();
        initGUI();
    }

    private void initGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        if (showLoginDialog(frame)) {
            JTabbedPane tabbedPane = new JTabbedPane();

            tabbedPane.addTab("Books", createBookPanel());
            tabbedPane.addTab("Patrons", createPatronPanel());
            tabbedPane.addTab("Loans", createLoanPanel());

            frame.add(tabbedPane);
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid login. Exiting...");
            System.exit(0);
        }
    }

    private boolean showLoginDialog(JFrame parent) {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = library.authenticateUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(parent, "Welcome, " + user.getRole() + "!");
                return true;
            }
        }
        return false;
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField categoryField = new JTextField();

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                String category = categoryField.getText();

                library.addBook(new Book(title, author, isbn, category));
                JOptionPane.showMessageDialog(panel, "Book added successfully!");
            }
        });

        formPanel.add(addButton);

        // Search fields
        JPanel searchPanel = new JPanel(new GridLayout(2, 2));
        JTextField searchField = new JTextField();
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Title", "Author", "ISBN", "Category"});
        JTextArea searchResults = new JTextArea(10, 50);
        searchResults.setEditable(false);

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Criteria:"));
        searchPanel.add(searchCriteria);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                String criteria = (String) searchCriteria.getSelectedItem();
                Book book = null;

                switch (criteria) {
                    case "Title":
                        book = library.searchBookByTitle(query);
                        break;
                    case "Author":
                        book = library.searchBookByAuthor(query);
                        break;
                    case "ISBN":
                        book = library.searchBookByISBN(query);
                        break;
                    case "Category":
                        book = library.searchBookByCategory(query);
                        break;
                }

                if (book != null) {
                    searchResults.setText(book.toString());
                } else {
                    searchResults.setText("No results found.");
                }
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(searchResults), BorderLayout.SOUTH);
        searchPanel.add(searchButton);

        return panel;
    }

    private JPanel createPatronPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);

        JButton addButton = new JButton("Add Patron");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String contact = contactField.getText();

                library.addPatron(new Patron(name, contact));
                JOptionPane.showMessageDialog(panel, "Patron added successfully!");
            }
        });

        formPanel.add(addButton);

        // Search fields
        JPanel searchPanel = new JPanel(new GridLayout(2, 2));
        JTextField searchField = new JTextField();
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Name", "Contact Information"});
        JTextArea searchResults = new JTextArea(10, 50);
        searchResults.setEditable(false);

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Criteria:"));
        searchPanel.add(searchCriteria);

        JButton searchButton = new JButton("Search");
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

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(searchResults), BorderLayout.SOUTH);
        searchPanel.add(searchButton);

        return panel;
    }

    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 2));

        JTextField isbnField = new JTextField();
        JTextField patronNameField = new JTextField();

        formPanel.add(new JLabel("Book ISBN:"));
        formPanel.add(isbnField);
        formPanel.add(new JLabel("Patron Name:"));
        formPanel.add(patronNameField);

        JButton checkoutButton = new JButton("Check Out Book");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String patronName = patronNameField.getText();

                library.checkOutBook(isbn, patronName);
                JOptionPane.showMessageDialog(panel, "Book checked out successfully!");
            }
        });

        JButton checkinButton = new JButton("Check In Book");
        checkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();

                library.checkInBook(isbn);
                JOptionPane.showMessageDialog(panel, "Book checked in successfully!");
            }
        });

        formPanel.add(checkoutButton);
        formPanel.add(checkinButton);
        panel.add(formPanel, BorderLayout.NORTH);

        return panel;
    }

    private void loadLibraryData() {
        library.loadBooksFromFile(BOOKS_FILE);
        library.loadPatronsFromFile(PATRONS_FILE);
        library.loadLoansFromFile(LOANS_FILE);
        library.loadUsersFromFile(USERS_FILE);
    }

    private void saveLibraryData() {
        library.saveBooksToFile(BOOKS_FILE);
        library.savePatronsToFile(PATRONS_FILE);
        library.saveLoansToFile(LOANS_FILE);
        library.saveUsersToFile(USERS_FILE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI mainGUI = new MainGUI();
                // Salvar dados ao fechar a GUI
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    mainGUI.saveLibraryData();
                }));
            }
        });
    }
}
