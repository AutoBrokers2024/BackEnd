package step;

import com.fastporte.fastportewebservice.entities.Driver;
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

public class DriverStepDefinitions {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;
    private String endpointPath;
    private ResponseEntity<String> responseEntity;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    @Given("The Driver Endpoint {string} is available")
    public void theDriverEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
    }

    @When("A Driver Request is sent with values {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void aDriverRequestIsSentWithValues(String name, String lastname, String username, String photo, String email, String phone, String region, String birthdate, String password, String description) {
        Date birthdateDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate, new java.text.ParsePosition(0));
        Driver driver = new Driver(0L, name, lastname, username, photo, email, phone, region, date, password, description);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Driver> request = new HttpEntity<>(driver, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

    @Then("A Driver with status {int} is received")
    public void aDriverWithStatusIsReceived(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("A Driver Delete is sent with id value {string}")
    public void aDriverDeleteIsSentWithIdValue(String idDriver) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idDriver);
        testRestTemplate.delete(endpointPath+"/{id}", params);
        responseEntity = new ResponseEntity<>(HttpStatus.OK);
    }

    @When("All Drivers who are registered in the DB")
    public void allDriversWhoAreRegisteredInTheDB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        responseEntity = testRestTemplate.exchange(endpointPath, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity);
    }

    @Then("List of Drivers with status {int} is received")
    public void listOfDriversWithStatusIsReceived(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("A Driver Selected is sent with id value {string}")
    public void aDriverSelectedIsSentWithIdValue(String idDriver) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idDriver);
        Driver driver = testRestTemplate.getForObject(endpointPath+"/{id}", Driver.class, params);
        responseEntity = new ResponseEntity<>(driver.toString(), HttpStatus.OK);
        System.out.println(driver.toString());
    }
}
