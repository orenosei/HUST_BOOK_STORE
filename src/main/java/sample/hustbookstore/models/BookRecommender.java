package sample.hustbookstore.models;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class BookRecommender {
    private final Analyzer analyzer = new StandardAnalyzer();
    private final Directory indexDir = FSDirectory.open(Paths.get("book_index"));

    public BookRecommender() throws IOException {
    }

    public void searchSimilarBooks(String processedQueryText, int topN) throws Exception {
        DirectoryReader reader = DirectoryReader.open(indexDir);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(QueryParser.escape(processedQueryText));

        TopDocs results = searcher.search(query, topN);
        for (ScoreDoc scoreDoc : results.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String title = doc.get("name");
            String author = doc.get("author");
            float score = scoreDoc.score;

            System.out.printf(" %s - %s (score: %.2f)\n", title, author, score);
        }

        reader.close();
    }
}
