package com.fastporte.fastportewebservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastporte.fastportewebservice.entities.*;
import com.fastporte.fastportewebservice.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContractController.class)
@ActiveProfiles("test")
public class ContractControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContractServiceImpl contractService;
    @MockBean
    private ClientServiceImpl clientService;
    @MockBean
    private DriverServiceImpl driverService;
    @MockBean
    private StatusContractServiceImpl statusContractService;
    @MockBean
    private NotificationServiceImpl notificationService;

    private List<Contract> contractList;

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
    @BeforeEach
    void setUp() {
        contractList = new ArrayList<>();
        contractList.add(
                new Contract(1L, "moving", "Lima", "Arequipa", date, time,
                        time, "500", "20", "Looking for a moving driver", true,
                        new Client(1L, "Antonio", "Martinez", "Antonio Martinez", "photo",
                                "am@gmail.com", "983654313", "Amazonas", date, "pass321", "I want to have the best service"),
                        new Driver(1L, "Roger", "Juarez", "Roger Juarez", "photo", "am@gmail.com",
                                "983654313", "Amazonas", date, "pass321",
                                "Hi, I'm Roger Juarez and I'm a driver"),
                        new StatusContract(1L, "OFFER"),
                        new Notification(1L, false)));
        contractList.add(
                new Contract(2L, "private", "Lima", "Ancash", date, time,
                        time, "500", "20", "Looking for a moving driver", true,
                        new Client(2L, "Rodrigo", "Sabino", "Rodrigo Sabino", "photo",
                                "rs@gmail.com", "983654312", "Junin", date, "pass321", "I want to have the best service"),
                        new Driver(2L, "Jean", "Lopez", "Jean Lopez", "photo", "jl@gmail.com",
                                "983654322", "Amazonas", date, "pass321",
                                "Hi, I'm Jean Lopez and I'm a driver"),
                        new StatusContract(2L, "PENDING"),
                        new Notification(1L, false)));
        contractList.add(
                new Contract(3L, "heavy load", "Lima", "Tacna", date, time,
                        time, "500", "20", "Looking for a heavy load driver", true,
                        new Client(3L, "Esteban", "Corrales", "Esteban Corrales", "photo",
                                "ec@gmail.com", "983654312", "Junin", date, "pass321", "I want to have the best service"),
                        new Driver(3L, "Pedro", "Socorro", "Pedro Socorro", "photo", "jl@gmail.com",
                                "983654322", "Amazonas", date, "pass321",
                                "Hi, I'm Pedro Socorro and I'm a driver"),
                        new StatusContract(3L, "HISTORY"),
                        new Notification(2L, true)));
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

    public static Time ParseTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Time result = null;
        try {
            result = (Time) format.parse(time);
        } catch (Exception ex) {
        }
        return result;
    }

    @Test
    void findAllContracts() throws Exception {
        given(contractService.getAll()).willReturn(contractList);
        mockMvc.perform(get("/api/contracts"))
                .andExpect(status().isOk());
    }

    @Test
    void findContractById() throws Exception {
        given(contractService.getById(1L)).willReturn(Optional.ofNullable(contractList.get(0)));
        mockMvc.perform(get("/api/contracts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void insertContract() throws Exception {
        Client client = new Client(1L, "Antonio", "Martinez", "Antonio Martinez", "photo",
                "am@gmail.com", "983654313", "Amazonas", date, "pass321", "I want to have the best service");
        Driver driver = new Driver(1L, "Roger", "Juarez", "Roger Juarez", "photo", "am@gmail.com",
                "983654313", "Amazonas", date, "pass321",
                "Hi, I'm Roger Juarez and I'm a driver");

        // Configurar los objetos simulados para que devuelvan el Client y el Driver
        given(clientService.getById(2L)).willReturn(Optional.of(client));
        given(driverService.getById(2L)).willReturn(Optional.of(driver));

        Contract contract = new Contract(4L, "moving", "Lima", "Arequipa", convertToLocalDateViaInstant(ParseDate("2021-07-21")), convertToLocalTimeViaSqlTime(ParseTime("05:50:00")),
                convertToLocalTimeViaSqlTime(ParseTime("17:50:00")), "500", "20", "Looking for a moving driver", true,
                new Client(1L, "Antonio", "Martinez", "Antonio Martinez", "photo",
                        "a@gmail.com", "983654313", "Amazonas", convertToLocalDateViaInstant(ParseDate("1998/05/01")), "pass321", "I want to have the best service"),

                new Driver(1L, "Roger", "Juarez", "Roger Juarez", "photo", "q@gmail.com", "983654313",
                        "Amazonas", convertToLocalDateViaInstant(ParseDate("1995/08/23")), "pass321", "Hi, I'm Roger Juarez and I'm a driver"),
                new StatusContract(1L, "OFFER"),
                new Notification(0L, false));

        given(contractService.save(contract)).willReturn(contract);
        mockMvc.perform(post("/api/contracts/add/1/1")
                        .content(asJsonString(contract))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalTime convertToLocalTimeViaSqlTime(Time timeToConvert) {
        if (timeToConvert == null) {
            return null;
        }
        return timeToConvert.toLocalTime();
    }

}