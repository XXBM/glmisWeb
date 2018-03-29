package com.glmis.domain.test;

/**
 * Created by ibs on 16/11/19.
 */


import java.io.Serializable;

public class Book implements Serializable {

    private String bookName;

    private String author;

    private double price;

    private int pages;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}