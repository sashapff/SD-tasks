package sashapff.utils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    private Integer id;
    private Integer money = 0;

    public User() {
    }

    public User(Integer id, Integer money) {
        this.id = id;
        this.money = money;
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMoney() {
        return money;
    }

    public void addMoney(Integer money) {
        this.money += money;
    }

    public void subtractMoney(Integer money) {
        this.money -= money;
    }
}
