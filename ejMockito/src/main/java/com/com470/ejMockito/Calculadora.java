package com.com470.ejMockito;

public class Calculadora {
    CalculadoraGoogle calculadoraGoogle;
    public int CalculadoraNuestra(int a, int b)
    {
        return calculadoraGoogle.sumar(a,b);
    }
}
