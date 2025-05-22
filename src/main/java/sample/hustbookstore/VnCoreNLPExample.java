package sample.hustbookstore;

import sample.hustbookstore.models.BookProcessor;
import vn.pipeline.*;
import java.io.*;
public class VnCoreNLPExample {
    public static void main(String[] args) throws IOException {

//        // "wseg", "pos", "ner", and "parse" refer to as word segmentation, POS tagging, NER and dependency parsing, respectively.
//        String[] annotators = {"wseg", "pos", "ner", "parse"};
//        VnCoreNLP pipeline = new VnCoreNLP(annotators);
//
//        String str = "Duy Vũ bàn bên thỉnh thoảng lại trêu ghẹo Minh Thành bằng Java. Tên nhóm xàm vãi.";
//
//        Annotation annotation = new Annotation(str);
//        pipeline.annotate(annotation);
//
//        System.out.println(annotation.toString());
//
//        //Write to file
//        PrintStream outputPrinter = new PrintStream("output.txt");
//        pipeline.printToFile(annotation, outputPrinter);
//
//        // You can also get a single sentence to analyze individually
//        Sentence firstSentence = annotation.getSentences().get(0);
//        System.out.println(firstSentence.toString());
        System.out.println(BookProcessor.tokenize("Harry Potter Và Hòn Đá Phù Thuỷ J.K.Rowling Mystery, Fantasy Khi một lá thư được gởi đến cho cậu bé Harry Potter bình thường và bất hạnh, cậu khám phá ra một bí mật đã được che giấu suốt cả một thập kỉ. Cha mẹ cậu chính là phù thủy và cả hai đã bị lời nguyền của Chúa tể Hắc ám giết hại khi Harry mới chỉ là một đứa trẻ, và bằng cách nào đó, cậu đã giữ được mạng sống của mình. Thoát khỏi những người giám hộ Muggle không thể chịu đựng nổi để nhập học vào trường Hogwarts, một trường đào tạo phù thủy với những bóng ma và phép thuật, Harry tình cờ dấn thân vào một cuộc phiêu lưu đầy gai góc khi cậu phát hiện ra một con chó ba đầu đang canh giữ một căn phòng trên tầng ba. Rồi Harry nghe nói đến một viên đá bị mất tích sở hữu những sức mạnh lạ kì, rất quí giá, vô cùng nguy hiểm, mà cũng có thể là mang cả hai đặc điểm trên."));
    }
}
