package sashapff.utils;

public class ShareReport {
    private Integer companyId;
    private Integer number;
    private Integer price;

    public ShareReport() {
    }

    public ShareReport(Integer companyId, Integer number, Integer price) {
        this.companyId = companyId;
        this.number = number;
        this.price = price;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getPrice() {
        return price;
    }
}
