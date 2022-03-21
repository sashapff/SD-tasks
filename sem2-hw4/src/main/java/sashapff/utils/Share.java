package sashapff.utils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "purchased_shares")
@IdClass(Share.ShareId.class)
public class Share {
    @Id
    private Integer userId;
    @Id
    private Integer companyId;
    private Integer number = 0;

    public static class ShareId implements Serializable {
        Integer userId;
        Integer companyId;

        public ShareId(Integer userId, Integer companyId) {
            this.userId = userId;
            this.companyId = companyId;
        }
    }

    public Share() {
    }

    public Share(Integer userId, Integer companyId, Integer number) {
        this.userId = userId;
        this.companyId = companyId;
        this.number = number;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getNumber() {
        return number;
    }



    public void addShares(Integer toAdd) {
        number += toAdd;
    }

    public void subtractShares(Integer toSubtract) {
        number -= toSubtract;
    }
}
