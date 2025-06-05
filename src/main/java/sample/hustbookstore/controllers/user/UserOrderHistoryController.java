package sample.hustbookstore.controllers.user;

import sample.hustbookstore.controllers.base.BaseOrderHistoryController;
import sample.hustbookstore.models.Bill;
import sample.hustbookstore.utils.dao.BillListDAO;
import java.util.List;
import static sample.hustbookstore.LaunchApplication.localUser;

public class UserOrderHistoryController extends BaseOrderHistoryController {

    @Override
    public void initializeData() {
        List<Bill> bills = BillListDAO.getUserBills(localUser.getUserId());
        originalBillList.setAll(bills);
    }
}