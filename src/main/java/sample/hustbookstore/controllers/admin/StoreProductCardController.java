package sample.hustbookstore.controllers.admin;

import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.models.Product;
import sample.hustbookstore.utils.cacheHandler.ImageCache;
import sample.hustbookstore.controllers.base.ProductCardSetData;

public class StoreProductCardController implements ProductCardSetData {

    @FXML
    private AnchorPane productCard;

    @FXML
    private ImageView productImage;

    @FXML
    private TextArea productName;

    @FXML
    private TextField productImportPrice;

    @FXML
    private TextField productSellPrice;

    private Product prodData;

    @Override
    public void setData(Product prodData) {
        this.prodData = prodData;
        productName.setText(prodData.getName());
        productSellPrice.setText(String.valueOf(prodData.getSellPrice()));
        productImportPrice.setText(String.valueOf(prodData.getImportPrice()));

        String imagePath = prodData.getImage();
        Image image = ImageCache.loadImage(imagePath);
        productImage.setImage(image);
    }

}
