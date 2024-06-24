import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A classe MainGUI representa a interface gráfica do usuário (GUI) para o sistema de gerenciamento de biblioteca.
 *
 * <p>Esta classe fornece métodos para:
 * <ul>
 * <li>Carregar e salvar dados da biblioteca.</li>
 * <li>Exibir telas de login e registro.</li>
 * <li>Inicializar a GUI principal com abas para gerenciamento de livros, patronos, empréstimos e multas atrasadas.</li>
 * </ul>
 * </p>
 */

public class MainGUI {
    private Library library;
    private User currentUser;
    private boolean isAdmin;

    //Construtor da GUI, carrega os dados e mostra a tela de login
    public MainGUI() {
        this.library = new Library();
        loadLibraryData();
        showLoginScreen();
    }

    //Metódo para carregar todos os dados do banco de dados
    private void loadLibraryData() {
        library.loadBooksFromFile("books.dat");
        library.loadPatronsFromFile("patrons.dat");
        library.loadLoansFromFile("loans.dat");
        library.loadUsersFromFile("users.dat");
    }

    //Metódo para salvar todos os dados no banco de dados
    private void saveLibraryData() {
        library.saveBooksToFile("books.dat");
        library.savePatronsToFile("patrons.dat");
        library.saveLoansToFile("loans.dat");
        library.saveUsersToFile("users.dat");
    }

    // Método para exibir a tela de login
    private void showLoginScreen() {
        // Criação do frame (janela) para a tela de login
        JFrame frame = new JFrame("Library Management System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Define o tamanho da janela

        // Criação de um painel com layout GridBagLayout para organizar os componentes
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preencher horizontalmente
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

        // Adição do rótulo "Username" e campo de texto para inserir o nome de usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20); // Campo de texto com 20 colunas
        panel.add(usernameField, gbc);

        // Adição do rótulo "Password" e campo de senha para inserir a senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20); // Campo de senha com 20 colunas
        panel.add(passwordField, gbc);

        // Adição do botão de login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // O botão ocupará duas colunas
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o nome de usuário e senha inseridos pelo usuário
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Autentica o usuário
                currentUser = library.authenticateUser(username, password);

                // Se a autenticação for bem-sucedida, fecha a tela de login e inicia a GUI principal
                if (currentUser != null) {
                    frame.dispose(); // Fecha a janela de login
                    initGUI(); // Inicia a interface gráfica principal
                } else {
                    // Mostra uma mensagem de erro se o login falhar
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });
        panel.add(loginButton, gbc);

        // Adição do botão de registro
        gbc.gridy = 3;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método para exibir a tela de registro
                showRegisterScreen();
            }
        });
        panel.add(registerButton, gbc);

        // Adiciona o painel ao frame e exibe a janela
        frame.add(panel);
        frame.setVisible(true);
    }


    // Método para exibir a tela de registro
    private void showRegisterScreen() {
        // Criação do frame (janela) para a tela de registro
        JFrame frame = new JFrame("Library Management System - Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Define a operação de fechamento para liberar recursos ao fechar a janela
        frame.setSize(400, 300); // Define o tamanho da janela

        // Criação de um painel com layout GridBagLayout para organizar os componentes
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preencher horizontalmente
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

        // Adição do rótulo "Username" e campo de texto para inserir o nome de usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20); // Campo de texto com 20 colunas
        panel.add(usernameField, gbc);

        // Adição do rótulo "Password" e campo de senha para inserir a senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20); // Campo de senha com 20 colunas
        panel.add(passwordField, gbc);

        // Adição do rótulo "Confirm Password" e campo de senha para confirmar a senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(20); // Campo de senha com 20 colunas
        panel.add(confirmPasswordField, gbc);

        // Adição do rótulo "Role" e botões de rádio para selecionar a função (admin ou bibliotecário)
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        JPanel rolePanel = new JPanel(new GridLayout(1, 2)); // Painel para organizar os botões de rádio
        JRadioButton adminRadioButton = new JRadioButton("Admin");
        JRadioButton librarianRadioButton = new JRadioButton("Librarian");
        ButtonGroup roleGroup = new ButtonGroup(); // Grupo de botões para garantir que apenas um botão de rádio possa ser selecionado por vez
        roleGroup.add(adminRadioButton);
        roleGroup.add(librarianRadioButton);
        rolePanel.add(adminRadioButton);
        rolePanel.add(librarianRadioButton);
        panel.add(rolePanel, gbc);

        // Adição do botão de registro
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // O botão ocupará duas colunas
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém os valores inseridos pelo usuário
                String username = usernameField.getText().trim();
                String password = (new String(passwordField.getPassword())).trim();
                String confirmPassword = (new String(confirmPasswordField.getPassword())).trim();
                if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Fill in all fields to register.");
                    return;
                }
                if(!adminRadioButton.isSelected() && !librarianRadioButton.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Choose a role, admin or librarian.");
                    return;
                }
                String role = adminRadioButton.isSelected() ? "admin" : "librarian"; // Define o papel baseado na seleção do botão de rádio

                // Verifica se as senhas coincidem
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                    return;
                }

                List<User> usersInDb = library.getAllUsers();
                for(User user : usersInDb) {
                    if(Objects.equals(user.getUsername(), username) || Objects.equals(user.getUsername().trim(), username)) {
                        JOptionPane.showMessageDialog(frame, "Username already exists, change and try again.");
                        return;
                    }
                }

                // Tenta registrar o usuário e exibe mensagens de sucesso ou erro
                if (library.registerUser(username, password, role)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful. You can now log in.");
                    frame.dispose(); // Fecha a janela de registro após o registro bem-sucedido
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed. Username might be taken.");
                }
            }
        });
        panel.add(registerButton, gbc);

        // Adiciona o painel ao frame e exibe a janela
        frame.add(panel);
        frame.setVisible(true);
    }


    // Método para inicializar a interface gráfica do usuário (GUI)
    private void initGUI() {
        this.isAdmin = !currentUser.getRole().equals("librarian");
        // Criação do frame (janela) principal para o sistema de gerenciamento de biblioteca
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação de fechamento para encerrar a aplicação ao fechar a janela
        frame.setSize(800, 1000); // Define o tamanho da janela

        // Criação de um JTabbedPane para gerenciar múltiplos painéis com abas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Adição de abas ao tabbedPane, cada aba associada a um painel diferente
        tabbedPane.addTab("Books", createBookPanel()); // Aba para gerenciamento de livros
        tabbedPane.addTab("Patrons", createPatronPanel()); // Aba para gerenciamento de usuários
        tabbedPane.addTab("Loans", createLoanPanel()); // Aba para gerenciamento de empréstimos
        tabbedPane.addTab("Overdue Fines", createOverdueFinesPanel()); // Aba para gerenciamento de multas por atraso

        // Adiciona o tabbedPane ao frame principal
        frame.add(tabbedPane);
        frame.setVisible(true); // Torna a janela visível

        // Adiciona um gancho de desligamento para salvar os dados da biblioteca quando a aplicação for encerrada
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveLibraryData));
    }

    // JTextArea para exibir as multas vencidas, declarada fora do método para permitir atualizações
    JTextArea overdueFines = new JTextArea(10, 50);

    private JPanel createOverdueFinesPanel() {
        // Cria um novo JPanel com BorderLayout para organizar os componentes
        JPanel panel = new JPanel(new BorderLayout());

        // Configura o JTextArea como não editável
        overdueFines.setEditable(false);

        // Criação de um painel de entrada de dados com FlowLayout
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JButton setAsPaidButton = new JButton("Set as Paid");

        // Adiciona um ActionListener ao botão "Set as Paid" para tratar o evento de clique
        setAsPaidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText(); // Obtém o texto do campo de ID

                // Verifica se o campo de ID está vazio
                if(id.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "ID required to set fine as paid."); // Exibe uma mensagem de erro
                    return;
                }

                try {
                    int numberId = Integer.parseInt(id); // Converte o ID para um número inteiro
                    boolean deleted = library.deleteOverdueFine(numberId); // Tenta deletar a multa com o ID fornecido

                    // Verifica se a multa foi deletada com sucesso
                    if(deleted) {
                        updateOverdueFines(overdueFines); // Atualiza a exibição das multas vencidas
                        JOptionPane.showMessageDialog(panel, "Fine marked as paid successfully!"); // Exibe uma mensagem de sucesso
                    } else {
                        JOptionPane.showMessageDialog(panel, "Fine with this ID does not exist."); // Exibe uma mensagem de erro
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(panel, "ID must be a number."); // Exibe uma mensagem de erro caso o ID não seja um número
                    return;
                }
            }
        });

        // Adiciona os componentes de entrada de dados ao painel de entrada
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(setAsPaidButton);

        // Adiciona o painel de entrada ao painel principal na parte superior (NORTH)
        panel.add(inputPanel, BorderLayout.NORTH);
        // Adiciona o JTextArea para exibir as multas vencidas ao painel principal no centro (CENTER), com rolagem
        panel.add(new JScrollPane(overdueFines), BorderLayout.CENTER);

        // Atualiza a exibição das multas vencidas ao iniciar o painel
        updateOverdueFines(overdueFines);

        return panel; // Retorna o painel configurado
    }

    private JPanel createBookPanel() {
        // Cria um JPanel principal e define seu layout como BoxLayout no eixo Y (vertical)
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Painel para adicionar livros
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adiciona o campo de entrada para o título do livro
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        // Adiciona o campo de entrada para o autor do livro
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        JTextField authorField = new JTextField(20);
        formPanel.add(authorField, gbc);

        // Adiciona o campo de entrada para o ISBN do livro
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnField = new JTextField(20);
        ((AbstractDocument) isbnField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]+")) { // Verifica se a string contém apenas números
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        formPanel.add(isbnField, gbc);

        // Adiciona o campo de entrada para a categoria do livro
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JTextField categoryField = new JTextField(20);
        formPanel.add(categoryField, gbc);

        // Botão para adicionar um novo livro
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String isbn = isbnField.getText().trim();
                String category = categoryField.getText().trim();

                if (!isAdmin) {
                    JOptionPane.showMessageDialog(panel, "You do not have permission to add books, only admins can do it.");
                    return;
                }

                // Verifica se todos os campos estão preenchidos
                if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "All fields are required.");
                    return;
                }

                // Adiciona o livro à biblioteca e verifica se foi bem-sucedido
                boolean successfulAddedBook = library.addBook(new Book(title, author, isbn, category));
                if (successfulAddedBook) {
                    JOptionPane.showMessageDialog(panel, "Book added successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Book with this ISBN already exists.");
                }
            }
        });
        formPanel.add(addButton, gbc);

        if (!isAdmin) {
            titleField.setEditable(false);
            authorField.setEditable(false);
            isbnField.setEditable(false);
            categoryField.setEditable(false);
            addButton.setEnabled(false);
            addButton.setToolTipText("Only admins can add books");
        }

        // Mensagem informando que o ISBN deve ser único
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("ISBN must be unique, you cannot add a book with an existing ISBN"), gbc);

        // Adiciona o painel de formulário ao painel principal
        panel.add(formPanel);

        // Adiciona um espaço vertical de 20 pixels entre os painéis
        panel.add(Box.createVerticalStrut(20));

        // Painel para excluir livros
        JPanel deletePanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        // Adiciona o campo de entrada para o ISBN do livro a ser excluído
        deletePanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnToDeleteField = new JTextField(20);
        ((AbstractDocument) isbnToDeleteField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]+")) { // Verifica se a string contém apenas números
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        deletePanel.add(isbnToDeleteField, gbc);

        // Botão para excluir um livro
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnToDeleteField.getText();

                if (!isAdmin) {
                    JOptionPane.showMessageDialog(panel, "You do not have permission to delete books, only admins can do it.");
                    return;
                }

                // Verifica se o campo de ISBN está preenchido
                if (isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "ISBN required to delete book.");
                    return;
                }

                // Exclui o livro da biblioteca e verifica se foi bem-sucedido
                boolean successfulDeletedBook = library.deleteBook(isbn);
                if (successfulDeletedBook) {
                    JOptionPane.showMessageDialog(panel, "Book deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Book with this ISBN does not exist.");
                }
            }
        });
        deletePanel.add(deleteButton, gbc);

        // Botão para atualizar um livro
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton updateButton = new JButton("Update Book");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnToDeleteField.getText();

                if (!isAdmin) {
                    JOptionPane.showMessageDialog(panel, "You do not have permission to update books, only admins can do it.");
                    return;
                }

                // Verifica se o campo de ISBN está preenchido
                if (isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "ISBN required to update book.");
                    return;
                }

                // Procura o livro pelo ISBN
                Book book = library.searchBookByISBN(isbn);
                if (book == null) {
                    JOptionPane.showMessageDialog(panel, "Book with this ISBN does not exist.");
                    return;
                }

                // Cria um diálogo popup para atualizar o livro
                JDialog updateDialog = new JDialog();
                updateDialog.setTitle("Update Book");
                updateDialog.setSize(400, 300);
                updateDialog.setLayout(new GridBagLayout());
                GridBagConstraints dialogGbc = new GridBagConstraints();
                dialogGbc.fill = GridBagConstraints.HORIZONTAL;
                dialogGbc.insets = new Insets(5, 5, 5, 5);

                // Campo de entrada para atualizar o título do livro
                dialogGbc.gridx = 0;
                dialogGbc.gridy = 0;
                updateDialog.add(new JLabel("Title:"), dialogGbc);
                dialogGbc.gridx = 1;
                JTextField updateTitleField = new JTextField(book.getTitle(), 20);
                updateDialog.add(updateTitleField, dialogGbc);

                // Campo de entrada para atualizar o autor do livro
                dialogGbc.gridx = 0;
                dialogGbc.gridy = 1;
                updateDialog.add(new JLabel("Author:"), dialogGbc);
                dialogGbc.gridx = 1;
                JTextField updateAuthorField = new JTextField(book.getAuthor(), 20);
                updateDialog.add(updateAuthorField, dialogGbc);

                // Campo de entrada para atualizar a categoria do livro
                dialogGbc.gridx = 0;
                dialogGbc.gridy = 2;
                updateDialog.add(new JLabel("Category:"), dialogGbc);
                dialogGbc.gridx = 1;
                JTextField updateCategoryField = new JTextField(book.getCategory(), 20);
                updateDialog.add(updateCategoryField, dialogGbc);

                // Botão para confirmar a atualização do livro
                dialogGbc.gridx = 0;
                dialogGbc.gridy = 3;
                dialogGbc.gridwidth = 2;
                JButton confirmUpdateButton = new JButton("Update");
                confirmUpdateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newTitle = updateTitleField.getText().trim();
                        String newAuthor = updateAuthorField.getText().trim();
                        String newCategory = updateCategoryField.getText().trim();

                        // Verifica se todos os campos estão preenchidos
                        if (newTitle.isEmpty() || newAuthor.isEmpty() || newCategory.isEmpty()) {
                            JOptionPane.showMessageDialog(updateDialog, "All fields are required.");
                            return;
                        }

                        // Atualiza os dados do livro
                        book.setTitle(newTitle);
                        book.setAuthor(newAuthor);
                        book.setCategory(newCategory);

                        // Atualiza o livro na biblioteca e verifica se foi bem-sucedido
                        if (library.updateBook(book)) {
                            JOptionPane.showMessageDialog(updateDialog, "Book updated successfully!");
                            updateDialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(updateDialog, "Failed to update book.");
                        }
                    }
                });
                updateDialog.add(confirmUpdateButton, dialogGbc);

                // Torna o diálogo visível
                updateDialog.setVisible(true);
            }
        });
        deletePanel.add(updateButton, gbc);
        if (!isAdmin) {
            isbnToDeleteField.setEditable(false);
            deleteButton.setEnabled(false);
            updateButton.setEnabled(false);
            deleteButton.setToolTipText("Only admins can update books");
            updateButton.setToolTipText("Only admins can delete books");
        }

        // Adiciona o painel de exclusão ao painel principal
        panel.add(deletePanel);

        // Adiciona um espaço vertical de 20 pixels entre os painéis
        panel.add(Box.createVerticalStrut(20));

        // Painel para pesquisar livros
        JPanel searchPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Criteria:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Title", "Author", "ISBN", "Category"});
        searchPanel.add(searchCriteria, gbc);

        // Campo de entrada para o critério de pesquisa
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField, gbc);

        // Botão para pesquisar um livro
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton searchButton = new JButton("Search Book");
        JTextArea searchResults = new JTextArea(10, 50);
        searchResults.setEditable(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                String criteria = (String) searchCriteria.getSelectedItem();
                ArrayList<Book> books = null;
                Book book = null;

                // Executa a pesquisa com base no critério selecionado
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

                // Exibe os resultados da pesquisa no JTextArea
                if (!"ISBN".equals(criteria) && books != null && books.size() > 0) {
                    StringBuilder bookString = new StringBuilder();
                    for (Book bookItem : books) {
                        bookString.append(bookItem.toString()).append("\n\n");
                    }
                    searchResults.setText(bookString.toString());
                } else if ("ISBN".equals(criteria) && book != null) {
                    searchResults.setText(book.toString());
                } else {
                    searchResults.setText("No results found.");
                }
            }
        });
        searchPanel.add(searchButton, gbc);

        // Botão para mostrar todos os livros
        JButton showAllBooksButton = new JButton("Show all books");
        showAllBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Book> books = library.getAllBooks();

                // Exibe todos os livros no JTextArea
                if (books != null && books.size() > 0) {
                    StringBuilder bookString = new StringBuilder();
                    for (Book bookItem : books) {
                        bookString.append(bookItem.toString()).append("\n\n");
                    }
                    searchResults.setText(bookString.toString());
                } else {
                    searchResults.setText("No results found.");
                }
            }
        });
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchPanel.add(showAllBooksButton, gbc);

        // Adiciona o painel de pesquisa ao painel principal
        panel.add(searchPanel);

        // Adiciona um espaço vertical de 20 pixels antes da área de resultados da pesquisa
        panel.add(Box.createVerticalStrut(20));

        // Area de resultados da pesquisa
        panel.add(new JScrollPane(searchResults));

        return panel; // Retorna o painel configurado
    }


    private JPanel createPatronPanel() {
        // Cria um painel principal com layout vertical.
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Painel para adicionar patronos
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adiciona um rótulo e campo de texto para o nome do patrono.
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Adiciona um rótulo e campo de texto para o contato do patrono.
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Contact:"), gbc);
        gbc.gridx = 1;
        JTextField contactField = new JTextField(20);
        formPanel.add(contactField, gbc);

        // Adiciona um botão para adicionar o patrono.
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Patron");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();

                // Verifica se os campos de nome e contato estão vazios
                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Name and contact info cannot be empty.");
                    return;
                }

                // Tenta adicionar o patrono à biblioteca
                boolean patronAdded = library.addPatron(new Patron(name, contact));
                if (patronAdded) {
                    JOptionPane.showMessageDialog(panel, "Patron added successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Patron with this name or contact info already exists.");
                }
            }
        });
        formPanel.add(addButton, gbc);

        // Adiciona uma nota informativa
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Note: Patron with the same name or contact info cannot be added."), gbc);

        panel.add(formPanel);

        // Adiciona um espaço vertical entre os painéis
        panel.add(Box.createVerticalStrut(20));  // Adiciona 20 pixels de espaço vertical

        // Painel para deletar e atualizar patronos
        JPanel actionPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        // Adiciona rótulos e campos de texto para nome e contato do patrono a ser deletado/atualizado
        actionPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameToDeleteField = new JTextField(20);
        actionPanel.add(nameToDeleteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        actionPanel.add(new JLabel("Contact:"), gbc);
        gbc.gridx = 1;
        JTextField contactInfoToDeleteField = new JTextField(20);
        actionPanel.add(contactInfoToDeleteField, gbc);

        // Adiciona um botão para deletar o patrono
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton deleteButton = new JButton("Delete Patron");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameToDeleteField.getText();
                String contact = contactInfoToDeleteField.getText();

                // Verifica se os campos de nome e contato estão vazios
                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Name and contact info required to delete patron.");
                    return;
                }

                // Tenta deletar o patrono da biblioteca
                boolean deleted = library.deletePatron(name, contact);
                if (deleted) {
                    JOptionPane.showMessageDialog(panel, "Patron deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Patron with this name and contact info does not exist.");
                }
            }
        });
        actionPanel.add(deleteButton, gbc);

        // Adiciona um botão para atualizar o patrono
        gbc.gridy = 3;
        JButton updateButton = new JButton("Update Patron");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameToDeleteField.getText();
                String contact = contactInfoToDeleteField.getText();

                // Verifica se os campos de nome e contato estão vazios
                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Name and contact info required to update patron.");
                    return;
                }

                // Procura o patrono na biblioteca
                Patron patron = library.searchPatron(name, contact);
                if (patron == null) {
                    JOptionPane.showMessageDialog(panel, "Patron with this name and contact info does not exist.");
                    return;
                }

                // Solicita novo nome e contato para o patrono
                String newName = JOptionPane.showInputDialog(panel, "Enter new name:", patron.getName());
                String newContact = JOptionPane.showInputDialog(panel, "Enter new contact:", patron.getContactInfo());

                if (newName == null || newContact == null) {
                    // O usuário cancelou a entrada
                    return;
                }

                // Verifica se ambos os campos estão vazios
                if (newName.trim().isEmpty() || newContact.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Name and contact cannot be blank.");
                    return;
                }

                // Atualiza o nome e contato do patrono
                if (!newName.isEmpty()) {
                    patron.setName(newName);
                }
                if (!newContact.isEmpty()) {
                    patron.setContactInfo(newContact);
                }
                JOptionPane.showMessageDialog(panel, "Patron updated successfully!");
            }
        });
        actionPanel.add(updateButton, gbc);

        panel.add(actionPanel);

        // Adiciona um espaço antes da área de resultados de pesquisa
        panel.add(Box.createVerticalStrut(20));  // Adiciona 20 pixels de espaço vertical

        // Painel de pesquisa
        JPanel searchPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        searchPanel.add(new JLabel("Criteria:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Name", "Contact Information"});
        searchPanel.add(searchCriteria, gbc);

        // Adiciona um rótulo e campo de texto para a pesquisa
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField, gbc);

        // Adiciona um botão de pesquisa
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

                // Procura o patrono de acordo com o critério selecionado
                switch (criteria) {
                    case "Name":
                        patron = library.searchPatronByName(query);
                        break;
                    case "Contact Information":
                        patron = library.searchPatronByContactInfo(query);
                        break;
                }

                if (patron != null) {
                    String patronString = patron.toString();
                    searchResults.setText(patronString);
                } else {
                    searchResults.setText("No results found.");
                }
            }
        });
        searchPanel.add(searchButton, gbc);

        // Adiciona um botão para mostrar todos os patronos
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
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchPanel.add(showAllPatronsButton, gbc);

        panel.add(searchPanel);

        // Area de resultados de pesquisa
        searchResults.setEditable(false);
        panel.add(new JScrollPane(searchResults));

        return panel;
    }


    private JPanel createLoanPanel() {
        // Cria um painel principal com layout BorderLayout.
        JPanel panel = new JPanel(new BorderLayout());

        // Cria um painel de formulário com layout GridBagLayout.
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adiciona um rótulo e campo de texto para o ISBN do livro.
        formPanel.add(new JLabel("Book ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnField = new JTextField(20);
        ((AbstractDocument) isbnField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        formPanel.add(isbnField, gbc);

        // Adiciona um rótulo e campo de texto para o nome do patrono.
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Patron Name:"), gbc);
        gbc.gridx = 1;
        JTextField patronIdField = new JTextField(20);
        formPanel.add(patronIdField, gbc);

        // Adiciona um rótulo e campo de texto para a data de vencimento.
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Due Date (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;

        JFormattedTextField dueDateField = null;
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            dueDateField = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
            // Tratar a exceção caso o formato da máscara seja inválido
            // Exemplo: exibir uma mensagem de erro para o usuário
            JOptionPane.showMessageDialog(panel, "Erro ao criar campo de data.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        formPanel.add(dueDateField, gbc);
        // ---->>> Fim das alterações <<<----

        // Adiciona um botão para realizar o checkout de um livro.
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JTextArea activeLoans = new JTextArea(10, 50);
        JButton checkoutButton = new JButton("Check Out");
        JFormattedTextField finalDueDateField = dueDateField;
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String patronId = patronIdField.getText();
                String dueDateStr = finalDueDateField.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date dueDate = sdf.parse(dueDateStr);
                    if (library.checkOutBook(isbn, patronId, dueDate)) {
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

        // Adiciona um botão para realizar o check-in de um livro.
        gbc.gridy = 4;
        JButton checkinButton = new JButton("Check In");
        checkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String patronId = patronIdField.getText();

                if (library.checkInBook(isbn, patronId)) {
                    JOptionPane.showMessageDialog(panel, "Book checked in successfully!");
                    updateActiveLoans(activeLoans);
                    updateOverdueFines(overdueFines);
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to check in book.");
                }
            }
        });
        formPanel.add(checkinButton, gbc);

        // Adiciona o painel de formulário ao painel principal na posição norte.
        panel.add(formPanel, BorderLayout.NORTH);

        // Configura a área de texto para exibir os empréstimos ativos.
        activeLoans.setEditable(false);
        panel.add(new JScrollPane(activeLoans), BorderLayout.CENTER);

        // Atualiza a lista de empréstimos ativos.
        updateActiveLoans(activeLoans);

        return panel;
    }

    private void updateActiveLoans(JTextArea activeLoans) {
        // Obtém a lista de todos os empréstimos associados ao usuário atual.
        List<Loan> loans = library.getAllLoans();

        // Usa um StringBuilder para construir uma string contendo informações sobre os empréstimos ativos.
        StringBuilder sb = new StringBuilder();

        // Percorre cada empréstimo na lista.
        for (Loan loan : loans) {
            // Verifica se o empréstimo não foi devolvido.
            if (!loan.isReturned()) {
                // Obtém o livro associado ao empréstimo.
                Book book = loan.getBook();

                // Obtém o patrono associado ao empréstimo.
                Patron patron = loan.getPatron();

                // Adiciona as informações do empréstimo ao StringBuilder.
                sb.append("Book: ").append(book.getTitle())
                        .append(" | Author: ").append(book.getAuthor())
                        .append(" | ISBN: ").append(book.getISBN())
                        .append(" | Patron: ").append(patron.getName())
                        .append(" | Contact Info: ").append(patron.getContactInfo())
                        .append(" | Loan Emission Date: ").append(loan.getLoanDate())
                        .append(" | Due Date: ").append(loan.getDueDate())
                        .append("\n\n");
            }
        }

        // Define o texto da área de texto de empréstimos ativos com as informações construídas.
        activeLoans.setText(sb.toString());
    }

    private void updateOverdueFines(JTextArea overdueFines) {
        // Obtém a lista de todos os empréstimos associados ao usuário atual.
        List<Loan> loans = library.getAllLoans();

        // Usa um StringBuilder para construir uma string contendo informações sobre multas de empréstimos atrasados.
        StringBuilder sb = new StringBuilder();

        // Inicializa um contador de ID para os empréstimos atrasados.
        int id = 0;

        // Percorre cada empréstimo na lista.
        for (Loan loan : loans) {
            // Verifica se o empréstimo está atrasado.
            if (loan.isOverdue()) {
                // Incrementa o ID do empréstimo atrasado.
                id++;

                // Obtém o livro associado ao empréstimo.
                Book book = loan.getBook();

                // Obtém o patrono associado ao empréstimo.
                Patron patron = loan.getPatron();

                // Adiciona as informações do empréstimo atrasado ao StringBuilder.
                sb.append("ID: ").append(id)
                        .append(" || Book: ").append(book.getTitle())
                        .append(" | Author: ").append(book.getAuthor())
                        .append(" | ISBN: ").append(book.getISBN())
                        .append(" | Patron: ").append(patron.getName())
                        .append(" | Contact Info: ").append(patron.getContactInfo())
                        .append(" | Due Date: ").append(loan.getDueDate())
                        .append(" | Return Date: ").append(loan.getReturnDate())
                        .append(" | Fine: $").append(loan.getFine())
                        .append("\n\n");
            }
        }

        // Define o texto da área de texto de multas atrasadas com as informações construídas.
        overdueFines.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
