package sample.hustbookstore.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import sample.hustbookstore.models.Book;

import java.io.IOException;
import java.nio.file.Paths;

public class BookRecommender {
    private final Analyzer analyzer = new StandardAnalyzer();
    private final Directory indexDir = FSDirectory.open(Paths.get("book_index"));

    public BookRecommender() throws IOException {
    }

    public ObservableList<Book> searchSimilarBooks(String processedQueryText, int topN) throws Exception {
        ObservableList<Book> recommendBooks = FXCollections.observableArrayList();

        DirectoryReader reader = DirectoryReader.open(indexDir);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(QueryParser.escape(processedQueryText));

        TopDocs results = searcher.search(query, topN);
        for (ScoreDoc scoreDoc : results.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);

            String id = doc.get("id");
            String isbn = doc.get("isbn");
            String image = doc.get("image");
            String title = doc.get("name");
            String author = doc.get("author");
            String genre = doc.get("genre");
            String description = doc.get("description");
            Double price = Double.parseDouble(doc.get("price"));

            Book book = new Book(id, isbn, image, title, author, genre, description, price);
            recommendBooks.add(book);

        }

        reader.close();
        return recommendBooks;
    }
}
