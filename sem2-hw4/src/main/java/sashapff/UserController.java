package sashapff;

import sashapff.database.CompanyDB;
import sashapff.database.ShareDB;
import sashapff.database.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sashapff.utils.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDB userDB;
    @Autowired
    private ShareDB shareDB;
    @Autowired
    private CompanyDB companyDB;

    @PostMapping("/user/add_user")
    public String addCompany(@RequestParam(value = "id") int id) {
        if (userDB.existsById(id)) {
            return "User already exists!";
        }
        userDB.save(new User(id));
        return "Successful create user account";
    }

    @PostMapping("/user/add_money")
    public String addMoney(@RequestParam(value = "id") int id,
                           @RequestParam(value = "money") int money) {
        if (!userDB.existsById(id)) {
            return "No such user!";
        }
        User user = userDB.findById(id).get();
        user.addMoney(money);
        userDB.save(user);
        return "Successful add money to user account";
    }

    @GetMapping("/user/shares_report/{id}")
    public UserSharesReport getSharesReport(@PathVariable int id) {
        if (!userDB.existsById(id)) {
            System.err.println("No such user!");
            return null;
        }
        User user = userDB.findById(id).get();
        UserSharesReport report = new UserSharesReport(user.getId());
        List<Share> userShares = (List<Share>) shareDB.getSharesByUserId(id);
        for (Share share : userShares) {
            report.addShare(new ShareReport(
                    share.getCompanyId(),
                    share.getNumber(),
                    ((int) (Math.random() * 1000)) * share.getNumber()
            ));
        }
        return report;
    }

    @GetMapping("/user/money_report/{id}")
    public Integer getMoneyReport(@PathVariable int id) {
        if (!userDB.existsById(id)) {
            System.err.println("No such user!");
            return null;
        }
        List<Share> userShares = (List<Share>) shareDB.getSharesByUserId(id);
        Integer money = userDB.findById(id).get().getMoney();
        for (Share share : userShares) {
            money += ((int) (Math.random() * 1000)) * share.getNumber();
        }
        return money;
    }

    @PostMapping("/user/buy_shares")
    public String buyShares(@RequestParam(value = "userId") int userId,
                            @RequestParam(value = "companyId") int companyId,
                            @RequestParam(value = "number") int number) {
        if (!userDB.existsById(userId)) {
            return "No such user!";
        }
        if (!companyDB.existsById(companyId)) {
            return "No such company!";
        }
        User user = userDB.findById(userId).get();
        Company company = companyDB.findById(companyId).get();
        int price = ((int) (Math.random() * 1000));
        if (price * number > user.getMoney()) {
            return "Not enough money!";
        }
        if (number > company.getStocks()) {
            return "Not enough shares!";
        }
        if (!shareDB.existsById(new Share.ShareId(userId, companyId))) {
            shareDB.save(new Share(userId, companyId, 0));
        }
        Share share = shareDB.getSharesByUserIdAndCompanyId(userId, companyId).get();
        share.addShares(number);
        shareDB.save(share);
        user.subtractMoney(price * number);
        userDB.save(user);
        company.subtractShares(number);
        companyDB.save(company);
        return "Successful buy shares";
    }

    @PostMapping("/user/sell_shares")
    public String sellShares(@RequestParam(value = "userId") int userId,
                             @RequestParam(value = "companyId") int companyId,
                             @RequestParam(value = "number") int number) {
        if (!userDB.existsById(userId)) {
            return "No such user!";
        }
        if (!companyDB.existsById(companyId)) {
            return "No such company!";
        }
        User user = userDB.findById(userId).get();
        Company company = companyDB.findById(companyId).get();
        int price = ((int) (Math.random() * 1000));
        if (!shareDB.existsById(new Share.ShareId(userId, companyId))) {
            return "Not enough shares!";
        }
        Share share = shareDB.getSharesByUserIdAndCompanyId(userId, companyId).get();
        if (share.getNumber() < number) {
            return "Not enough shares!";
        }
        share.subtractShares(number);
        shareDB.save(share);
        user.addMoney(price * number);
        userDB.save(user);
        company.addShares(number);
        companyDB.save(company);
        return "Successful buy shares";
    }

}
