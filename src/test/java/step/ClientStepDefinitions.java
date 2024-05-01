package step;

import com.fastporte.fastportewebservice.entities.Client;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientStepDefinitions {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;
    private String endpointPath;
    private ResponseEntity<String> responseEntity;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
    @Given("The Client Endpoint {string} is available")
    public void theClientEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
    }

    @When("A Client Request is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void aClientRequestIsSentWithValues(String name, String lastname, String username, String photo, String email, String phone, String region, String birthdate, String password, String description) {
        Date birthdateDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate, new java.text.ParsePosition(0));
        Client client = new Client(0L, name, lastname, username, photo, email, phone, region, date, password, description);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Client> request = new HttpEntity<>(client, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

    @Then("A Client with status {int} is received")
    public void aClientWithStatusIsReceived(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("A Client Delete is sent with id value {string}")
    public void aClientDeleteIsSentWithIdValue(String idClient) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idClient);
        testRestTemplate.delete(endpointPath+"/{id}", params);
        responseEntity = new ResponseEntity<>(HttpStatus.OK);
    }

    @When("All Clients who are registered in the DB")
    public void allClientsWhoAreRegisteredInTheDB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        responseEntity = testRestTemplate.exchange(endpointPath, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity);
    }

    @Then("List of Clients with status {int} is received")
    public void listOfClientsWithStatusIsReceived(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("A Client Selected is sent with id value {string}")
    public void aClientSelectedIsSentWithIdValue(String idClient) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idClient);
        Client client = testRestTemplate.getForObject(endpointPath+"/{id}", Client.class, params);
        responseEntity = new ResponseEntity<>(client.toString(), HttpStatus.OK);
        System.out.println(client.toString());
    }
}
