package sample.hustbookstore.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.util.Map;

public class CloudinaryUploader {
    private Cloudinary cloudinary;

    public CloudinaryUploader() {
        // Tải các biến môi trường từ file .env
        Dotenv dotenv = Dotenv.load();

        // Lấy thông tin từ .env
        String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
        String apiKey = dotenv.get("CLOUDINARY_API_KEY");
        String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

        if (cloudName == null || apiKey == null || apiSecret == null) {
            throw new IllegalArgumentException("Missing Cloudinary configuration in .env file.");
        }

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String uploadImage(File file) throws Exception {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File does not exist or is null.");
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }

}
