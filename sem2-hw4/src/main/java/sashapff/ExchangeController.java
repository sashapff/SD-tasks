package sashapff;

import sashapff.database.CompanyDB;
import sashapff.database.ShareDB;
import sashapff.database.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sashapff.utils.Company;
import sashapff.utils.StockReport;

@RestController
public class ExchangeController {
    @Autowired
    private CompanyDB companyDB;
    @Autowired
    private UserDB userDB;
    @Autowired
    private ShareDB shareDB;

    @PostMapping("/exchange/add_company")
    public String addCompany(@RequestParam(value = "id") int id,
                                @RequestParam(value = "stock") int stock) {
        if (companyDB.existsById(id)) {
            return "Company already exists!";
        }
        companyDB.save(new Company(id, stock));
        return "Successful create company";
    }

    @GetMapping("/exchange/report_company/{id}")
    public StockReport getCompanyReport(@PathVariable int id) {
        if (!companyDB.existsById(id)) {
            System.err.println("No such company!");
            return null;
        }
        Company company = companyDB.findById(id).get();
        return new StockReport(company.getId(), ((int) (Math.random() * 1000)), company.getStocks());
    }

    @PostMapping("/exchange/add_shares")
    public String addShares(@RequestParam(value = "id") int id,
                             @RequestParam(value = "number") int number) {
        if (!companyDB.existsById(id)) {
            return "No such company!";
        }
        Company company = companyDB.findById(id).get();
        company.addShares(number);
        companyDB.save(company);
        return "Successful add shares";
    }

}
