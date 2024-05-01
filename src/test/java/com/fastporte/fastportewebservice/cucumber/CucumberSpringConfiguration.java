package com.fastporte.fastportewebservice.cucumber;

import com.fastporte.fastportewebservice.FastPorteWebServiceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = FastPorteWebServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = FastPorteWebServiceApplication.class,
        loader = SpringBootContextLoader.class)
public class CucumberSpringConfiguration {
}
