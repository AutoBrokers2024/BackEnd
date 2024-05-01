package com.fastporte.fastportewebservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.service.impl.DriverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DriverController.class)
@ActiveProfiles("test")
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverServiceImpl driverService;
    private List<Driver> driverList;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    @BeforeEach
    void setUp() {
        driverList = new ArrayList<>();
        driverList.add(new Driver(1L, "Antonio", "Martinez",
                "Antonio Martinez", "photo", "am@gmail.com",
                "983654313", "Amazonas",
                date, "pass321",
                "I'm a driver"));
        driverList.add(new Driver(1L, "Juan", "Perez",
                "Juan Perez", "photo", "jp@gmail.com",
                "987654312", "Lima",
                date, "pass123",
                "I'm very good at my job"));
        driverList.add(new Driver(1L, "Joselyn Sofia", "Maldonado",
                "Joselyn Maldonado", "photo", "jm@gmail.com",
                "937028312", "Ica",
                date, "pass456",
                "How said Neon: GO GO GO"));
        driverList.add(new Driver(1L, "Marco", "Gonzales",
                "Marco Gonzales", "photo", "mg@gmail.com",
                "939268312", "Piura",
                date, "pass654",
                "You want to hire me"));
        driverList.add(new Driver(1L, "Juan", "Garcia",
                "Juan Garcia", "photo", "jg@gmail.com",
                "982136724", "Cusco",
                date, "pass987",
                "Fast and secure"));
    }

//    @Test
//    void findAllDriversTest() throws Exception {
//        given(driverService.getAll()).willReturn(driverList);
//        mockMvc.perform(get("/api/drivers"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void findDriverById() throws Exception {
//        Long driverId = 1L;
//        Driver driver = new Driver(1L, "Mario", "Gomez",
//                "Mario Gomez", "photo", "mg@gmail.com",
//                "987432651", "Amazonas",
//                date, "pass789",
//                "I'm the best driver");
//        given(driverService.getById(driverId)).willReturn(Optional.of(driver));
//        mockMvc.perform(get("/api/drivers/{id}", driverId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void insertDriverTest() throws Exception {
//        Driver driver = new Driver(1L, "Mario", "Gomez",
//                "Mario Gomez", "photo", "mg@gmail.com",
//                "987432651", "Amazonas",
//                date, "pass789",
//                "I'm the best driver");
//        mockMvc.perform(post("/api/drivers")
//                        .content(asJsonString(driver))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void updateDriverTest() throws Exception {
//        Long id = 1L;
//        Driver driver = new Driver(1L, "Antonio", "Martinez",
//                "Antonio Martinez", "photo", "am@gmail.com",
//                "983654313", "Amazonas",
//                date, "pass321",
//                "I'm the best driver");
//        given(driverService.getById(id)).willReturn(Optional.of(driver));
//        mockMvc.perform(put("/api/drivers/{id}", id)
//                        .content(asJsonString(driver))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteDriverTest() throws Exception {
//        Long id = 1L;
//        Driver driver = new Driver(1L, "Mario", "Gomez",
//                "Mario Gomez", "photo", "mg@gmail.com",
//                "987432651", "Amazonas",
//                date, "pass789",
//                "I'm the best driver");
//        given(driverService.getById(id)).willReturn(Optional.of(driver));
//        mockMvc.perform(delete("/api/drivers/{id}", id))
//                .andExpect(status().isOk());
//    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
