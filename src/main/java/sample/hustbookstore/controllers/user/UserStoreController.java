package sample.hustbookstore.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.controllers.admin.StoreController;

public class UserStoreController extends StoreController {

    @FXML
    private AnchorPane rightPane;

    @Override
    protected String getRightPanelPath(){
        return "/sample/hustbookstore/user/arya-chat.fxml";
    }

    @Override
    protected String getProductCardPath() {
        return "/sample/hustbookstore/user/user-productCard-view.fxml";
    }

}
