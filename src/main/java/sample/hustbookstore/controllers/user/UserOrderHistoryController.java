package sample.hustbookstore.controllers.user;

import sample.hustbookstore.controllers.base.BaseOrderHistoryController;
import sample.hustbookstore.models.Bill;
import sample.hustbookstore.utils.dao.BillList;
import java.util.List;
import static sample.hustbookstore.LaunchApplication.localUser;

public class UserOrderHistoryController extends BaseOrderHistoryController {

    @Override
    public void initializeData() {
        List<Bill> bills = BillList.getUserBills(localUser.getUserId());
        originalBillList.setAll(bills);
    }
}