package sample.hustbookstore.utils;

import javafx.collections.ObservableList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import sample.hustbookstore.models.Book;

import java.io.IOException;
import java.nio.file.Paths;

public class BookIndexer {
    private final IndexWriter writer;

    public BookIndexer() throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get("book_index"));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        writer = new IndexWriter(dir, config);
        writer.deleteAll();
    }

    public void indexBooks(ObservableList<Book> books) throws Exception {
        for (Book book : books) {
            Document doc = new Document();

            doc.add(new StringField("id", book.getID(), Field.Store.YES));
            doc.add(new StringField("isbn", book.getIsbn(), Field.Store.YES));
            doc.add(new StringField("image", book.getImage(), Field.Store.YES));
            doc.add(new TextField("name", book.getName(), Field.Store.YES));
            doc.add(new TextField("author", book.getAuthor(), Field.Store.YES));
            doc.add(new TextField("genre", book.getGenre(), Field.Store.YES));
            doc.add(new TextField("description", book.getDescription(), Field.Store.YES));
            doc.add(new TextField("price", book.getSellPrice().toString(), Field.Store.YES));

            doc.add(new TextField("content", BookProcessor.tokenize(book.getcombinedText()), Field.Store.YES));

            writer.addDocument(doc);
        }

        writer.commit();
        writer.close();
    }
}

