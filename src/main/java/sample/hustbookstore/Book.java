package sample.hustbookstore;

import java.util.Date;

public class Book extends Product
{
    private String isbn;
    private String genre;
    private Date publishedDate;
    private String author;

    public Book(String ID, String name, String distributor, Double sellPrice, Double importPrice, int stock, String type, String image, String description, Date addedDate) {
        super(ID, name, distributor, sellPrice, importPrice, stock, type, image, description, addedDate);
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

}
