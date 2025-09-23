package com.test.springboot.app.services;

import com.test.springboot.app.Datos;
import com.test.springboot.app.exceptions.DineroInsuficienteException;
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
    private CuentaServiceImpl cuentaService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
        Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
        Datos.BANCO.setTotalTransferencias(0);
    }

    @DisplayName("casos de transferencias positivas")
    @Test
    void TransferenciasOK() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
        BigDecimal saldoOrigen = cuentaService.revisarSaldo(1L);
        BigDecimal saldoDestino = cuentaService.revisarSaldo(2L);
        assertEquals("1000",saldoOrigen.toString());
        assertEquals("2000",saldoDestino.toString());
        cuentaService.transferir(1L,2L,new BigDecimal("100"), 1L);
        saldoOrigen = cuentaService.revisarSaldo(1L);
        saldoDestino = cuentaService.revisarSaldo(2L);
        assertEquals("900",saldoOrigen.toString());
        assertEquals("2100",saldoDestino.toString());

        int totalTransferencias = cuentaService.revisarTotalTransferencias(1L);
        assertEquals(1,totalTransferencias);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(3)).findById(2L);
        verify(bancoRepository, times(2)).findById(1L);
    }

    @DisplayName("caso de saldo insuficiente")
    @Test
    void SaldoInsuficiente() {
        when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
        when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
        when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);
        BigDecimal saldoOrigen = cuentaService.revisarSaldo(1L);
        BigDecimal saldoDestino = cuentaService.revisarSaldo(2L);
        assertEquals("1000",saldoOrigen.toString());
        assertEquals("2000",saldoDestino.toString());
        assertThrows(
                DineroInsuficienteException.class,
                ()->cuentaService.transferir(1L,2L,new BigDecimal("1200"), 1L)
        );
        saldoOrigen = cuentaService.revisarSaldo(1L);
        saldoDestino = cuentaService.revisarSaldo(2L);
        assertEquals("1000",saldoOrigen.toString());
        assertEquals("2000",saldoDestino.toString());
        int totalTransferencias = cuentaService.revisarTotalTransferencias(1L);
        assertEquals(0,totalTransferencias);

        verify(cuentaRepository, times(3)).findById(1L);
        verify(cuentaRepository, times(2)).findById(2L);
        verify(bancoRepository, times(1)).findById(1L);
    }
}