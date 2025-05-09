package sample.hustbookstore.models;

import java.util.Date;


public class Voucher {
    private String code;
    private int remaining;
    private float discount;
    private Date duration;
    private int voucher_id;

    public Voucher(String code, int remaining, float discount, Date duration, int voucher_id) {
        this.code = code;
        this.remaining = remaining;
        this.discount = discount;
        this.duration = duration;
        this.voucher_id = voucher_id;
    }

    public String getCode() {return code;}

    public void setCode(String code) {this.code = code;}

    public int getRemaining() {return remaining;}

    public void setRemaining(int remaining) {this.remaining = remaining;}

    public float getDiscount() {return discount;}

    public void setDiscount(float discount) {this.discount = discount;}

    public Date getDuration() {return (java.sql.Date) duration;}

    public void setDuration(Date duration) {this.duration = duration;}

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

}
