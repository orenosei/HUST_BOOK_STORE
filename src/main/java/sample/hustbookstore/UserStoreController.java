package sample.hustbookstore;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class UserStoreController extends StoreController{
    @Override
    public int[] updateRowColumn(int column, int row) {
        if (column == 3) {
            column = 0;
            row += 1;
        }
        return new int[]{column, row};
    }
}
