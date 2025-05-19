package sample.hustbookstore.models;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class BookIndexer {
    private final Analyzer analyzer = new StandardAnalyzer();
    private final Directory indexDir = FSDirectory.open(Paths.get("book_index"));
    private final IndexWriterConfig config = new IndexWriterConfig(analyzer);
    private final IndexWriter writer = new IndexWriter(indexDir, config);

    public BookIndexer() throws IOException {
    }

    public void indexBooks(List<Book> books) throws IOException {
        for (Book book : books) {
            Document doc = new Document();
            String processedText = BookProcessor.tokenize(book.combinedText); // Từ bước 1
            doc.add(new TextField("content", processedText, Field.Store.YES));
            doc.add(new StoredField("name", book.getName()));
            doc.add(new StoredField("author", book.getAuthor()));
            writer.addDocument(doc);
        }
        writer.close();
    }
}

