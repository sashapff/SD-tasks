package sashapff.utils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
    @Id
    private Integer id;
    private Integer stock = 0;

    public Company(Integer id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public Company() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getStocks() {
        return stock;
    }

    public void addShares(int toAdd) {
        stock += toAdd;
    }

    public void subtractShares(int toSubtract) {
        stock -= toSubtract;
    }
}
