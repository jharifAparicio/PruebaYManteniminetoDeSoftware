package com.test.springboot.app.services;

import com.test.springboot.app.Datos;
import com.test.springboot.app.exceptions.DineroInsuficienteException;
import com.test.springboot.app.models.Banco;
import com.test.springboot.app.models.Cuenta;
import com.test.springboot.app.repositories.BancoRepository;
import com.test.springboot.app.repositories.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private BancoRepository bancoRepository;

    @InjectMocks
    private CuentaServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
        Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
        Datos.BANCO.setTotalTransferencias(0);
    }
    @DisplayName("casos de transferencia positivo")
    @Test
    void transferirTest() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000",saldoOrigen.toString());
        assertEquals("2000",saldoDestino.toString());

        service.transferir(1L,2L,new BigDecimal("100"),1L);
        saldoOrigen=service.revisarSaldo(1L);
        saldoDestino=service.revisarSaldo(2L);
        assertEquals("900",saldoOrigen.toString());
        assertEquals("2100",saldoDestino.toString());

        int total = service.revisarTotalTransferencias(1L);
        assertEquals(1,total);

        verify(cuentaRepository,times(3)).findById(1L);
        verify(cuentaRepository,times(3)).findById(2L);
        verify(bancoRepository,times(2)).findById(1L);
    }

    @DisplayName("casos de transferencia cuando el monto es mayor al saldo")
    @Test
    void transferirTest_cuando_el_monto_es_mayor_al_saldo() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000",saldoOrigen.toString());
        assertEquals("2000",saldoDestino.toString());

        assertThrows(DineroInsuficienteException.class, () -> {
            service.transferir(1L,2L,
                    new BigDecimal("1200"),1L);
        });

        saldoOrigen=service.revisarSaldo(1L);
        saldoDestino=service.revisarSaldo(2L);
        assertEquals("1000",saldoOrigen.toString());
        assertEquals("2000",saldoDestino.toString());

        int total = service.revisarTotalTransferencias(1L);
        assertEquals(0,total);

        verify(cuentaRepository,times(3)).findById(1L);
        verify(cuentaRepository,times(2)).findById(2L);
        verify(bancoRepository,never()).update(any(Banco.class));

    }

    @DisplayName("prueba del metodo buscar por ID")
    @Test
    void finById() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
        Cuenta nuevo = service.findById(1L);
        assertEquals(1L,nuevo.getId());
    }



}