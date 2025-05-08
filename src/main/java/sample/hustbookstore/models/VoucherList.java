package sample.hustbookstore.models;

import java.util.List;

public class VoucherList {
    private List<Voucher> vouchers;

//    public VoucherList(List<Voucher> vouchers) {
//        this.vouchers = vouchers;
//    }

    public int voucherCount() {
        return vouchers.size();
    }

    public List<Voucher> getVouchers() {
        // select * from voucher;
        // vouchers = nhung gi lay duoc tu database
        return vouchers;
    }

    public void addVoucher(Voucher voucher) {}

    public void removeVoucher(Voucher voucher) {}

    public void updateVoucher(Voucher voucher) {}

}
