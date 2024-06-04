import java.io.Serializable;
import java.util.Date;

public class Loan implements Serializable {
    private Book book;
    private Patron patron;
    private Date loanDate;
    private Date returnDate;
    private boolean isReturned;

    public Loan(Book book, Patron patron, Date loanDate) {
        this.book = book;
        this.patron = patron;
        this.loanDate = loanDate;
        this.isReturned = false;
    }

    // Getters and Setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book +
                ", patron=" + patron +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", isReturned=" + isReturned +
                '}';
    }
}
