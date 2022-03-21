package sashapff.utils;

import java.util.ArrayList;
import java.util.List;

public class UserSharesReport {
    private int id;
    private final List<ShareReport> shares = new ArrayList<>();

    public UserSharesReport() {}

    public UserSharesReport(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<ShareReport> getShares() {
        return shares;
    }

    public void addShare(ShareReport share) {
        this.shares.add(share);
    }

}
