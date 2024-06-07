import java.io.Serializable;
import java.util.Date;

public class Loan implements Serializable {
    private Book book;
    private Patron patron;
    private Date loanDate;
    private Date returnDate;
    private Date dueDate;
    private static final double FINE_AMOUNT = 100.0;
    private boolean isReturned;
    private String userDataOwner;

    public Loan(Book book, Patron patron, Date loanDate, Date dueDate, String userDataOwner) {
        this.book = book;
        this.patron = patron;
        this.loanDate = loanDate;
        this.isReturned = false;
        this.dueDate = dueDate;
        this.userDataOwner = userDataOwner;
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

    public Date getDueDate() {
      return dueDate;
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

    public void returnBook(Date returnDate) {
      this.returnDate = returnDate;
      this.isReturned = true;
  }

  public boolean isOverdue() {
    return returnDate != null && returnDate.after(dueDate);
}

public double getFine() {
  if (isOverdue()) {
      return FINE_AMOUNT;
  }
  return 0;
}

public String getUserDataOwner() {
    return userDataOwner;
}

public void setUserDataOwner(String userDataOwner) {
    this.userDataOwner = userDataOwner;
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
