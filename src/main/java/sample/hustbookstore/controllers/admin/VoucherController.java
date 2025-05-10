package sample.hustbookstore.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.hustbookstore.LaunchApplication;
import sample.hustbookstore.models.AdminList;
import sample.hustbookstore.models.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static sample.hustbookstore.LaunchApplication.localAdmin;

public class VoucherController {
    @FXML
    private TableColumn<?, ?> codeAvailablecodeCol;

    @FXML
    private TableColumn<?, ?> codeDiscountCol;

    @FXML
    private TableColumn<?, ?> codeDurationCol;

    @FXML
    private TableColumn<?, ?> codeDurationCol1;

    @FXML
    private Button voucherAddBtn;

    @FXML
    private Button voucherClearBtn;

    @FXML
    private TextField voucherCode;

    @FXML
    private Button voucherDeleteBtn;

    @FXML
    private TextField voucherDiscount;

    @FXML
    private TextField voucherDuaration;

    @FXML
    private TextField voucherRemain;

    @FXML
    private Button voucherUpdateBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

//    Voucher vc = new Voucher;
//    vc.setCode("sdfsdfjsdfs")
}
