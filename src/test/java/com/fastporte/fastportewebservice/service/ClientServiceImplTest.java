package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Client;
import com.fastporte.fastportewebservice.repository.IClientRepository;
import com.fastporte.fastportewebservice.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
    @Mock
    private IClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
//    @Test
//    public void saveTest() {
//        Client client = new Client(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I work with a lot of merchandise");
//        given(clientRepository.save(client)).willReturn(client);
//        Client savedClient = null;
//        try {
//            savedClient = clientService.save(client);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertThat(savedClient).isNotNull();
//        assertEquals(client, savedClient);
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        Long id = 1L;
//        clientService.delete(id);
//        verify(clientRepository, times(1)).deleteById(id);
//    }
//
//    @Test
//    public void getAllTest() throws Exception {
//        List<Client> list = new ArrayList<>();
//        list.add(new Client(1L, "Antonio", "Martinez",
//                "Antonio Martinez", "photo", "am@gmail.com",
//                "983654313", "Amazonas",
//                date, "pass321",
//                "I work with a lot of merchandise"));
//        list.add(new Client(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "My business is the most important thing"));
//        list.add(new Client(1L, "Joselyn Sofia", "Maldonado",
//                "Joselyn Maldonado", "photo", "jm@gmail.com",
//                "937028312", "Ica",
//                date, "pass456",
//                "I like to travel around my country"));
//        given(clientRepository.findAll()).willReturn(list);
//        List<Client> listExpected = clientService.getAll();
//        assertEquals(listExpected, list);
//    }
//
//    @Test
//    public void getByIdTest() throws Exception {
//        Long id = 1L;
//        Client client = new Client(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I work with a lot of merchandise");
//        given(clientRepository.findById(id)).willReturn(Optional.of(client));
//        Optional<Client> clientExpected = clientService.getById(id);
//        assertThat(clientExpected).isNotNull();
//        assertEquals(clientExpected, Optional.of(client));
//    }
//
//    @Test
//    public void findByEmailAndPasswordTest() throws Exception {
//        String email = "jp@gmail.com";
//        String password = "pass123";
//        Client client = new Client(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I work with a lot of merchandise");
//        given(clientRepository.findByEmailAndPassword(email, password)).willReturn(client);
//        Client clientExpected = clientService.findByEmailAndPassword(email, password);
//        assertThat(clientExpected).isNotNull();
//        assertEquals(clientExpected, client);
//    }
}
