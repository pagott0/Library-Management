import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LibrarySystem extends Application {
    private Book[] books;
    private Patron[] patrons;
    private Loan[] loans;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize data structures
        books = new Book[100];
        patrons = new Patron[100];
        loans = new Loan[100];

        // Create GUI components
        TextField bookTitleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        TextField categoryField = new TextField();
        Button addButton = new Button("Add Book");
        ListView<Book> bookListView = new ListView<>();
        ListView<Patron> patronListView = new ListView<>();
        ListView<Loan> loanListView = new ListView<>();

        // Add event handlers
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Add book to database
                Book book = new Book(bookTitleField.getText(), authorField.getText(), isbnField.getText(), categoryField.getText());
                // Add book to list view
                bookListView.getItems().add(book);
            }
        });

        // Create GUI layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        HBox bookHBox = new HBox(10);
        bookHBox.getChildren().addAll(new Label("Book Title:"), bookTitleField);
        bookHBox.getChildren().addAll(new Label("Author:"), authorField);
        bookHBox.getChildren().addAll(new Label("ISBN:"), isbnField);
        bookHBox.getChildren().addAll(new Label("Category:"), categoryField);
        bookHBox.getChildren().add(addButton);
        root.getChildren().add(bookHBox);

        root.getChildren().add(bookListView);
        root.getChildren().add(patronListView);
        root.getChildren().add(loanListView);

        // Set scene and show GUI
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}