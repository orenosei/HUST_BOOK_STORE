package sample.hustbookstore.utils.cacheHandler;

import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static final Map<String, Image> memoryCache = new HashMap<>();
    private static final String CACHE_DIR = "src/cache/images";
    private static final String NOT_FOUND_IMAGE_PATH = "/sample/hustbookstore/img/notfound.jpg";

    static {
        try {
            Files.createDirectories(Paths.get(CACHE_DIR));
        } catch (Exception e) {
            System.err.println("Failed to create cache directory: " + e.getMessage());
        }
    }

    public static Image loadImage(String imagePath) {
        return loadImage(imagePath, 400, 640);
    }

    public static Image loadImage(String imagePath, double width, double height) {
        if (imagePath == null || imagePath.isEmpty()) {
            return getNotFoundImage(width, height);
        }

        String cacheKey = imagePath + "|" + width + "|" + height;
        if (memoryCache.containsKey(cacheKey)) {
            return memoryCache.get(cacheKey);
        }

        try {
            if (!imagePath.startsWith("http")) {
                URL resourceUrl = ImageCache.class.getResource(imagePath);
                if (resourceUrl == null && !imagePath.startsWith("/")) {
                    resourceUrl = ImageCache.class.getResource("/" + imagePath);
                }

                if (resourceUrl != null) {
                    Image image = new Image(resourceUrl.toExternalForm(), width, height, true, true);
                    memoryCache.put(cacheKey, image);
                    return image;
                }
                return getNotFoundImage(width, height);
            }

            Path cachedPath = getCachedImagePath(imagePath);
            if (Files.exists(cachedPath)) {
                Image image = new Image(cachedPath.toUri().toString(), width, height, true, true);
                memoryCache.put(cacheKey, image);
                return image;
            }

            URL url = new URL(imagePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (InputStream in = connection.getInputStream()) {
                byte[] imageData = in.readAllBytes();
                Files.write(cachedPath, imageData);

                Image image = new Image(new ByteArrayInputStream(imageData), width, height, true, true);
                memoryCache.put(cacheKey, image);
                return image;
            }
        } catch (Exception e) {
            System.err.println("Image download error: " + e.getMessage());
            return getNotFoundImage(width, height);
        }
    }

    private static Image getNotFoundImage(double width, double height) {
        String cacheKey = NOT_FOUND_IMAGE_PATH + "|" + width + "|" + height;
        return memoryCache.computeIfAbsent(cacheKey, key ->
                new Image(ImageCache.class.getResource(NOT_FOUND_IMAGE_PATH).toExternalForm(), width, height, true, true)
        );
    }

    private static Path getCachedImagePath(String imageUrl) {
        String filename = Math.abs(imageUrl.hashCode()) + "." + getFileExtension(imageUrl);
        return Paths.get(CACHE_DIR, filename);
    }

    private static String getFileExtension(String url) {
        try {
            String path = new URL(url).getPath();
            int dotIndex = path.lastIndexOf('.');
            if (dotIndex > 0) {
                return path.substring(dotIndex + 1);
            }
        } catch (Exception e) {
            // Ignore
        }
        return "jpg";
    }
}