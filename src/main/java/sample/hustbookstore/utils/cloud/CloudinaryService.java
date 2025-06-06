package sample.hustbookstore.utils.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.util.Map;

public class CloudinaryService {
    private static Cloudinary cloudinary;

    static {
        try {
            Dotenv dotenv = Dotenv.load();
            String cloudName = dotenv.get("CLOUDINARY_CLOUD_NAME");
            String apiKey = dotenv.get("CLOUDINARY_API_KEY");
            String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

            if (cloudName != null && apiKey != null && apiSecret != null) {
                cloudinary = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                ));
            }
        } catch (Exception e) {
            System.err.println("CloudinaryService not initialized: " + e.getMessage());
        }
    }

    public static String uploadImage(File file) throws Exception {
        if (cloudinary == null) throw new IllegalStateException("Cloudinary not initialized");
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File not found");
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }

}