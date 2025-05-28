package sample.hustbookstore.models;

import javafx.collections.ObservableList;
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
    private final IndexWriter writer;

    // Khởi tạo writer để ghi vào thư mục chỉ mục "book_index"
    public BookIndexer() throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get("book_index")); // nơi lưu index
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer()); // dùng standard analyzer
        writer = new IndexWriter(dir, config); // writer dùng để ghi các Document vào chỉ mục
    }

    // Phương thức index toàn bộ danh sách Book
    public void indexBooks(ObservableList<Book> books) throws Exception {
        for (Book book : books) {
            Document doc = new Document(); // mỗi Book sẽ được chuyển thành 1 Document

            // Lưu thông tin từng trường (Field) vào Document, có đánh dấu lưu trữ (Field.Store.YES)
            doc.add(new StringField("id", book.getID(), Field.Store.YES));
            doc.add(new StringField("image", book.getImage(), Field.Store.YES));
            doc.add(new TextField("title", book.getName(), Field.Store.YES));
            doc.add(new TextField("author", book.getAuthor(), Field.Store.YES));
            doc.add(new TextField("genre", book.getGenre(), Field.Store.YES));
            doc.add(new TextField("description", book.getDescription(), Field.Store.YES));

            // Gộp các trường lại và xử lý bằng VnCoreNLP → dùng cho tìm kiếm chính xác hơn
            doc.add(new TextField("content", book.getcombinedText(), Field.Store.NO));
            // content dùng để tìm kiếm, không cần lưu lại trong index (Store.NO)

            writer.addDocument(doc); // thêm Document vào chỉ mục
        }

        writer.commit(); // ghi chắc chắn vào đĩa
        writer.close();  // đóng writer
    }
}

