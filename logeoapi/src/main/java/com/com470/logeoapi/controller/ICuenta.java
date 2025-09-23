package com.com470.logeoapi.controller;

public interface ICuenta {
    boolean claveCorrecta(String candidata);
    void entrarCuenta();
    void bloquearCuenta();
    boolean estaBloqueada();
    boolean estaEnUso();
}
