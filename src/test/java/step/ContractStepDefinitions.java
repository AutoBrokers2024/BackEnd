package step;

import com.fastporte.fastportewebservice.entities.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractStepDefinitions {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;
    private String endpointPath;
    private ResponseEntity<String> responseEntity;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    @Given("The contract Endpoint {string} is available")
    public void theContractEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
    }

    @When("there are contracts registered in the DB")
    public void thereAreContractsRegisteredInTheDB() {
        responseEntity = testRestTemplate.getForEntity(endpointPath, String.class);
    }

    @Then("list of contracts with status code {int} is returned")
    public void listOfContractsWithStatusCodeIsReturned(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }
    @And("I need to get a list of contracts with status {string} and {string} id {int}")
    public void iNeedToGetAListOfContractsWithStatusAndId(String status, String user, int id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", status);
        params.put("user", user);
        params.put("id", String.valueOf(id));
        responseEntity = testRestTemplate.getForEntity(endpointPath+"/{status}/{user}/{id}", String.class, params);
    }

    @And("I need to get a contract with id {int}")
    public void iNeedToGetAContractWithId(int id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        responseEntity = testRestTemplate.getForEntity(endpointPath+"/{id}", String.class, params);
    }

    @Then("contract with id {int} and status code {int} is returned")
    public void contractWithIdAndStatusCodeIsReturned(int id, int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }
    @Then("a contract with status code {int} is returned")
    public void aContractWithStatusCodeIsReturned(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("I post a contract to the endpoint with values {long}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {int}, {int}, {int}, {int}, {int}")
    public void iPostAContractToTheEndpointWithValuesWithClientIdAndDriverId(long id, String amount, String contractDate, String description, String from, String quantity, String subject, String timeArrival, String timeDeparture, String to, int visible, int clientId, int driverId, int notificationId, long statusContractId) {
        Time timeDepartureParsed = new Time(new SimpleDateFormat("HH:mm").parse(timeDeparture, new java.text.ParsePosition(0)).getTime());
        Time timeArrivalParsed = new Time(new SimpleDateFormat("HH:mm").parse(timeArrival, new java.text.ParsePosition(0)).getTime());

        boolean visibleParsed = visible == 1;
        Contract contract = new Contract(
                id, subject, from, to, date, time, time, amount,
                quantity, description, visibleParsed, new Client(), new Driver(), new StatusContract(), new Notification());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Contract> request = new HttpEntity<>(contract, headers);

        Map<String, String> params = new HashMap<String, String>();
        params.put("clientId", String.valueOf(clientId));
        params.put("driverId", String.valueOf(driverId));

        responseEntity = testRestTemplate.postForEntity(endpointPath+"/{clientId}/{driverId}", request, String.class, params);

    }

    public static Date ParseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date result = null;
        try {
            result = format.parse(date);
        } catch (Exception ex) {
        }
        return result;
    }
}
