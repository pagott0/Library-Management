public class Loan {
    private Book book;
    private Patron patron;
    private Date checkoutDate;
    private Date dueDate;

    public Loan(Book book, Patron patron, Date checkoutDate, Date dueDate) {
        this.book = book;
        this.patron = patron;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    // Getters and setters
}