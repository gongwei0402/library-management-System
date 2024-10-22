package com.cogent.entity;

import java.util.Date;

public class BorrowedBook {
    private int borrowBooksId;
    private int userId;
    private  int bookId;
    private Date borrowDate;
    private Date returnDate;

    public BorrowedBook(int borrowBooksId, int userId, int bookId, Date borrowDate, Date returnDate) {
        this.borrowBooksId = borrowBooksId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getBorrowBooksId() {
        return borrowBooksId;
    }

    public void setBorrowBooksId(int borrowBooksId) {
        this.borrowBooksId = borrowBooksId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
