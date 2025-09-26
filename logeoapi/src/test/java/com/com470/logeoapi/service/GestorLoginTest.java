package com.com470.logeoapi.service;

import com.com470.logeoapi.controller.ExcepcionCuentaEnUso;
import com.com470.logeoapi.controller.ExcepcionUsuarioDesconocido;
import com.com470.logeoapi.controller.ICuenta;
import com.com470.logeoapi.controller.IRepositorioCuentas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorLoginTest {
    private IRepositorioCuentas repo;
    private ICuenta cuenta;
    private GestorLogin gestorLogin;

    @BeforeEach
    void setUp() {
        repo = mock(IRepositorioCuentas.class);
        cuenta = mock(ICuenta.class);
        gestorLogin = new GestorLogin(repo);
    }

    @DisplayName("Usuario Desconocido")
    @Test
    void testUsuarioDesconocido() {
        when(repo.buscar("user1")).thenReturn(null);

        assertThrows(ExcepcionUsuarioDesconocido.class,
                () -> gestorLogin.acceder("user1", "clave"));
    }

    @DisplayName("cuenta en uso")
    @Test
    void testCuentaEnUso() {
        when(repo.buscar("user1")).thenReturn(cuenta);
        when(cuenta.estaEnUso()).thenReturn(true);

        assertThrows(ExcepcionCuentaEnUso.class, () -> gestorLogin.acceder("user1", "clave"));
    }

    @DisplayName("Acceso Correcto")
    @Test
    void testAccesoCorrecto() {
        // buscamos la cuenta
        when(repo.buscar("user1")).thenReturn(cuenta);
        // la cuenta no debe estar en uso
        when(cuenta.estaEnUso()).thenReturn(false);
        // debe estar desbloqueada
        when(cuenta.estaBloqueada()).thenReturn(false);
        when(cuenta.claveCorrecta("wrong")).thenReturn(true);

        gestorLogin.acceder("user1", "wrong");
        verify(cuenta).entrarCuenta();
        verify(cuenta, never()).bloquearCuenta();
    }

    @DisplayName("bloque tras tres fallos")
    @Test
    void testBloqueoTrasTresFallos() {
        when(repo.buscar("user1")).thenReturn(cuenta);
        when(cuenta.estaEnUso()).thenReturn(false);
        when(cuenta.estaBloqueada()).thenReturn(false);
        when(cuenta.claveCorrecta("wrong")).thenReturn(false);

        gestorLogin.acceder("user1", "wrong");
        gestorLogin.acceder("user1", "wrong");
        gestorLogin.acceder("user1", "wrong");

        verify(cuenta).bloquearCuenta();
    }

    @DisplayName("fallos reinicio con otro usuario")
    @Test
    void testFallosSeReinicianConOtroUsuario() {
        ICuenta cuenta2 = mock(ICuenta.class);

        when(repo.buscar("user1")).thenReturn(cuenta);
        when(repo.buscar("user2")).thenReturn(cuenta2);

        when(cuenta.estaEnUso()).thenReturn(false);
        when(cuenta.estaBloqueada()).thenReturn(false);
        when(cuenta.claveCorrecta(anyString())).thenReturn(false);

        when(cuenta2.estaEnUso()).thenReturn(false);
        when(cuenta2.estaBloqueada()).thenReturn(false);
        when(cuenta2.claveCorrecta(anyString())).thenReturn(false);

        gestorLogin.acceder("user1", "wrong");
        gestorLogin.acceder("user2", "wrong"); // debería resetear los fallos
        gestorLogin.acceder("user2", "wrong");
        gestorLogin.acceder("user2", "wrong");

        verify(cuenta2).bloquearCuenta(); // se bloquea cuenta2, no cuenta1
        verify(cuenta, never()).bloquearCuenta();
    }
}