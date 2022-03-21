package sashapff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import sashapff.utils.StockReport;
import sashapff.utils.UserSharesReport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ExchangeTest {

    @Container
    public GenericContainer container = new GenericContainer(DockerImageName.parse("sem2-hw4:1.0-SNAPSHOT"))
            .withExposedPorts(8080);

    @Autowired
    private ExchangeController exchangeController;
    @Autowired
    private UserController userController;
    @Autowired
    private TestRestTemplate restTemplate;

    private String addUserUrl = "/user/add_user?id=%d";
    private String addMoneyUrl = "/user/add_money?id=%d&money=%d";
    private String getSharesReportUrl = "user/shares_report/%d";
    private String getMoneyReportUrl = "/user/money_report/%d";
    private String buySharesUrl = "/user/buy_shares?userId=%d&companyId=%d&number=%d";
    private String sellSharesUrl = "/user/sell_shares?userId=%d&companyId=%d&number=%d";

    private String addCompanyUrl = "/exchange/add_company?id=%d&stock=%d";
    private String getCompanyReportUrl = "/exchange/report_company/%d";
    private String addSharesUrl = "/exchange/add_shares?id=%d&number=%d";

    @BeforeEach
    public void setUp() {
        int port = container.getFirstMappedPort();
        String prefix = "http://localhost:" + port;

        addUserUrl = prefix + addUserUrl;
        addMoneyUrl = prefix + addMoneyUrl;
        getSharesReportUrl = prefix + getSharesReportUrl;
        getMoneyReportUrl = prefix + getMoneyReportUrl;
        buySharesUrl = prefix + buySharesUrl;
        sellSharesUrl = prefix + sellSharesUrl;

        addCompanyUrl = prefix + addCompanyUrl;
        getCompanyReportUrl = prefix + getCompanyReportUrl;
        addSharesUrl = prefix + addSharesUrl;
    }

    @Test
    public void testAddUser() {
        assertThat(restTemplate.postForObject(
                String.format(addUserUrl, 1), null, String.class)).isEqualTo("Successful create user account");
        assertThat(restTemplate.postForObject(
                String.format(addUserUrl, 1), null, String.class))
                .isEqualTo("User already exists!");
    }

    @Test
    public void testAddCompany() {
        assertThat(restTemplate.postForObject(
                String.format(addCompanyUrl, 1, 1000), null, String.class)).isEqualTo("Successful create company");
        assertThat(restTemplate.postForObject(
                String.format(addCompanyUrl, 1, 100), null, String.class))
                .isEqualTo("Company already exists!");
    }

    @Test
    public void testAddMoney() {
        assertThat(restTemplate.postForObject(String.format(addMoneyUrl, 1, 100), null, String.class))
                .isEqualTo("No such user!");
        assertThat(restTemplate.postForObject(
                String.format(addUserUrl, 1), null, String.class)).isEqualTo("Successful create user account");
        assertThat(restTemplate.postForObject(String.format(addMoneyUrl, 1, 1, 100), null, String.class))
                .isEqualTo("Successful add money to user account");
    }

    @Test
    public void testSellShares() {
        assertThat(restTemplate.postForObject(String.format(sellSharesUrl, 1, 1, 100), null, String.class))
                .isEqualTo("No such user!");
        assertThat(restTemplate.postForObject(
                String.format(addUserUrl, 1), null, String.class)).isEqualTo("Successful create user account");
        assertThat(restTemplate.postForObject(String.format(sellSharesUrl, 1, 1, 100), null, String.class))
                .isEqualTo("No such company!");
    }

    @Test
    public void testBuyShares() {
        assertThat(restTemplate.postForObject(String.format(buySharesUrl, 1, 1, 100), null, String.class))
                .isEqualTo("No such user!");
        assertThat(restTemplate.postForObject(
                String.format(addUserUrl, 1), null, String.class)).isEqualTo("Successful create user account");
        assertThat(restTemplate.postForObject(String.format(buySharesUrl, 1, 1, 100), null, String.class))
                .isEqualTo("No such company!");
    }

    @Test
    public void testGetSharesReport() {
        assertThat(restTemplate.getForObject(String.format(getSharesReportUrl, 1), UserSharesReport.class)).isNull();
    }

    @Test
    public void testGetCompanyReport() {
        assertThat(restTemplate.getForObject(String.format(getCompanyReportUrl, 1), StockReport.class)).isNull();
    }

    @Test
    public void testBuyAndSell() {
        restTemplate.postForObject(
                String.format(addCompanyUrl, 1, 1000), null, String.class);
        restTemplate.postForObject(
                String.format(addUserUrl, 1), null, String.class);

        assertThat(restTemplate.postForObject(String.format(buySharesUrl, 1, 1, 10000), null, String.class))
                .isEqualTo("Not enough money!");
        restTemplate.postForObject(String.format(addMoneyUrl, 1, 10000000), null, String.class);
        restTemplate.postForObject(String.format(buySharesUrl, 1, 1, 10), null, String.class);

        restTemplate.postForObject(String.format(sellSharesUrl, 1, 1, 2), null, String.class);
    }
}