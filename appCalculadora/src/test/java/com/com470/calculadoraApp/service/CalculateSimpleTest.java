package com.com470.calculadoraApp.service;

import com.com470.calculadoraApp.model.OperationModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateSimpleTest {
CalculateSimple calculateSimple = new CalculateSimple();
    @Test
    void add() {
        // Given
        OperationModel model = new OperationModel(5,4);
        // When
        int resultado = calculateSimple.add(model);
        // Then
        assertEquals(9,resultado);
    }
    @Test
    void subtract() {
        // Given
        OperationModel model = new OperationModel(5,4);
        // When
        int resultado = calculateSimple.subtract(model);
        // Then
        assertEquals(1,resultado);
    }

    @Test
    void multiply() {
        // Given
        OperationModel model = new OperationModel(5,4);
        // When
        int resultado = calculateSimple.multiply(model);
        // Then
        assertEquals(20,resultado);
    }

    @Test
    void divide1() {
        // Given
        OperationModel model = new OperationModel(8,4);
        // When
        double resultado = calculateSimple.divide(model);
        // Then
        assertEquals(2,resultado);
    }

    @Test
    void divide2() {
        // Given
        OperationModel model = new OperationModel(0,4);
        // When
        double resultado = calculateSimple.divide(model);
        // Then
        assertEquals(0,resultado);
    }
    @Test
    void divide3() {
        // Given
        OperationModel model = new OperationModel(3,0);
        // When
        double resultado = calculateSimple.divide(model);
        // Then
        assertEquals(0,resultado);
    }

    @Test
    void factorial() {
    }

    @Test
    void fibonacci() {
    }

    @Test
    void sqrt() {
    }

    @Test
    void power() {
    }

    @Test
    void clearSimple() {
        // Given
        OperationModel model = new OperationModel(3,6);
        // When
        int resultado = calculateSimple.clearSimple(model).getA();
        int resultado2 = calculateSimple.clearSimple(model).getA();
        // Then
        assertEquals(0,resultado);
        assertEquals(0,resultado2);

    }

    @Test
    void clearAdvanced() {
        // Given
        OperationModel model = new OperationModel(5);
        // When
        int resultado = calculateSimple.clearAdvanced(model).getC();

        // Then
        assertEquals(0,resultado);

    }
}