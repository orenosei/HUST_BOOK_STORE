package sample.hustbookstore.models;

import java.time.LocalDate;

public class Book extends Product
{
    private String isbn;
    private String genre;
    private LocalDate publishedDate;
    private String author;

    public Book(String ID, String name, String distributor, Double sellPrice,
                Double importPrice, int stock, String type, String image, String description,
                LocalDate addedDate, int restrictedAge,
                String isbn, String genre, LocalDate publishedDate, String author) {
        super(ID, name, distributor, sellPrice, importPrice, stock, type, image, description, addedDate, restrictedAge);
        this.isbn = isbn;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.author = author;
    }

    public String combinedText = name + " " + author + " " + genre + " " + description;

//    public Book(String name, String distributor, Double sellPrice,String type, String image, String description) {
//        super(name, distributor, sellPrice, type, image, description);
//    }

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

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

}
