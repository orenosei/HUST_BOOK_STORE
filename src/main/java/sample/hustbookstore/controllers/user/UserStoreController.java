package sample.hustbookstore.controllers.user;

import sample.hustbookstore.controllers.admin.StoreController;

public class UserStoreController extends StoreController {
    @Override
    public int[] updateRowColumn(int column, int row) {
        if (column == 3) {
            column = 0;
            row += 1;
        }
        return new int[]{column, row};
    }
}
