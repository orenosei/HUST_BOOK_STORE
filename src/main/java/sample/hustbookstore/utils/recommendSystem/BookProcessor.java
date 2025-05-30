package sample.hustbookstore.utils.recommendSystem;

import vn.pipeline.*;

import java.io.IOException;
import java.util.List;

public class BookProcessor {
    private static VnCoreNLP pipeline;

    static {
        try {
            String[] annotators = {"wseg"};
            pipeline = new VnCoreNLP(annotators);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String tokenize(String text) throws IOException {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        StringBuilder tokenized = new StringBuilder();
        List<Sentence> sentences = annotation.getSentences();
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                String form = word.getForm();
                if (!form.matches("\\p{Punct}")) {
                    tokenized.append(form).append(" ");
                }
            }
        }

        return tokenized.toString().trim().toLowerCase();
    }
}