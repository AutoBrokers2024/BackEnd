package com.fastporte.fastportewebservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastporte.fastportewebservice.entities.Client;
import com.fastporte.fastportewebservice.service.impl.ClientServiceImpl;
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

@WebMvcTest(controllers = ClientController.class)
@ActiveProfiles("test")
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientServiceImpl clientService;
    private List<Client> clientList;

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    @BeforeEach
    void setUp() {
        clientList = new ArrayList<>();
        clientList.add(new Client(1L, "Antonio", "Martinez",
                "Antonio Martinez", "photo", "am@gmail.com",
                "983654313", "Amazonas",
                date, "pass321",
                "I want to have the best service"));
        clientList.add(new Client(1L, "Juan", "Perez",
                "Juan Perez", "photo", "jp@gmail.com",
                "987654312", "Lima",
                date, "pass123",
                "I work with a lot of merchandise"));
        clientList.add(new Client(1L, "Joselyn Sofia", "Maldonado",
                "Joselyn Maldonado", "photo", "jm@gmail.com",
                "937028312", "Ica",
                date, "pass456",
                "I need moving services"));
        clientList.add(new Client(1L, "Marco", "Gonzales",
                "Marco Gonzales", "photo", "mg@gmail.com",
                "939268312", "Piura",
                date, "pass654",
                "I like to travel around my country"));
        clientList.add(new Client(1L, "Juan", "Garcia",
                "Juan Garcia", "photo", "jg@gmail.com",
                "982136724", "Cusco",
                date, "pass987",
                "My business is the most important thing"));
    }

    @Test
   void findAllClientsTest() throws Exception {
        given(clientService.getAll()).willReturn(clientList);
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());
    }

    @Test
    void findClientById() throws Exception {
        Long clientId = 1L;
        Client client = new Client(1L, "Mario", "Gomez",
                "Mario Gomez", "photo", "mg@gmail.com",
                "987432651", "Amazonas",
                date, "pass789",
                "I work with a lot of merchandise");
        given(clientService.getById(clientId)).willReturn(Optional.of(client));
        mockMvc.perform(get("/api/clients/{id}", clientId))
               .andExpect(status().isOk());
    }
//
//    @Test
//    void insertClientTest() throws Exception {
//        Client client = new Client(1L, "Mario", "Gomez",
//                "Mario Gomez", "photo", "mg@gmail.com",
//                "987432651", "Amazonas",
//                date, "pass789",
//                "I work with a lot of merchandise");
//        mockMvc.perform(post("/api/clients")
//                        .content(asJsonString(client))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void updateClientTest() throws Exception {
//        Long id = 1L;
//        Client client = new Client(1L, "Antonio", "Martinez",
//                "Antonio Martinez", "photo", "am@gmail.com",
//                "983654313", "Amazonas",
//                date, "pass321",
//                "I work with a lot of merchandise");
//        given(clientService.getById(id)).willReturn(Optional.of(client));
//        mockMvc.perform(put("/api/clients/{id}", id)
//                        .content(asJsonString(client))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteClientTest() throws Exception {
//        Long id = 1L;
//        Client client = new Client(1L, "Mario", "Gomez",
//                "Mario Gomez", "photo", "mg@gmail.com",
//                "987432651", "Amazonas",
//                date, "pass789",
//                "I work with a lot of merchandise");
//        given(clientService.getById(id)).willReturn(Optional.of(client));
//        mockMvc.perform(delete("/api/clients/{id}", id))
//                .andExpect(status().isOk());
//    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
