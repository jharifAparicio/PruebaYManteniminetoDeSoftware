package org.sqa.test.springboot.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.sqa.test.springboot.app.Datos.*;

import org.sqa.test.springboot.app.exceptions.DineroInsuficienteException;
import org.sqa.test.springboot.app.models.Banco;
import org.sqa.test.springboot.app.models.Cuenta;
import org.sqa.test.springboot.app.repositories.BancoRepository;
import org.sqa.test.springboot.app.repositories.CuentaRepository;
import org.sqa.test.springboot.app.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class pringbootTestApplicationTests {

    @Test
    void name() {
    }
}
