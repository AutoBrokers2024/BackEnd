package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.repository.IDriverRepository;
import com.fastporte.fastportewebservice.service.impl.DriverServiceImpl;
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
public class DriverServiceImplTest {
    @Mock
    private IDriverRepository driverRepository;
    @InjectMocks
    private DriverServiceImpl driverService;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
//    @Test
//    public void saveTest() {
//        Driver driver = new Driver(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I'm a good driver");
//        given(driverRepository.save(driver)).willReturn(driver);
//        Driver savedDriver = null;
//        try {
//            savedDriver = driverService.save(driver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertThat(savedDriver).isNotNull();
//        assertEquals(driver, savedDriver);
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        Long id = 1L;
//        driverService.delete(id);
//        verify(driverRepository, times(1)).deleteById(id);
//    }
//
//    @Test
//    public void getAllTest() throws Exception {
//        List<Driver> list = new ArrayList<>();
//        list.add(new Driver(1L, "Antonio", "Martinez",
//                "Antonio Martinez", "photo", "am@gmail.com",
//                "983654313", "Amazonas",
//                date, "pass321",
//                "My services are the best"));
//        list.add(new Driver(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I'm a good driver"));
//        list.add(new Driver(1L, "Joselyn Sofia", "Maldonado",
//                "Joselyn Maldonado", "photo", "jm@gmail.com",
//                "937028312", "Ica",
//                date, "pass456",
//                "Fast and secure"));
//        given(driverRepository.findAll()).willReturn(list);
//        List<Driver> listExpected = driverService.getAll();
//        assertEquals(listExpected, list);
//    }
//
//    @Test
//    public void getByIdTest() throws Exception {
//        Long id = 1L;
//        Driver driver = new Driver(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I'm a good driver");
//        given(driverRepository.findById(id)).willReturn(Optional.of(driver));
//        Optional<Driver> driverExpected = driverService.getById(id);
//        assertThat(driverExpected).isNotNull();
//        assertEquals(driverExpected, Optional.of(driver));
//    }
//
//    @Test
//    public void findByEmailAndPasswordTest() throws Exception {
//        String email = "jp@gmail.com";
//        String password = "pass123";
//        Driver driver = new Driver(1L, "Juan", "Perez",
//                "Juan Perez", "photo", "jp@gmail.com",
//                "987654312", "Lima",
//                date, "pass123",
//                "I'm a good driver");
//        given(driverRepository.findByEmailAndPassword(email, password)).willReturn(driver);
//        Driver driverExpected = driverService.findByEmailAndPassword(email, password);
//        assertThat(driverExpected).isNotNull();
//        assertEquals(driverExpected, driver);
//    }
}
