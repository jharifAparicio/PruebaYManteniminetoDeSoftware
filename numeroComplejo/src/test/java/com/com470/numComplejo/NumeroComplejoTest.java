package com.com470.numComplejo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NumeroComplejoTest {
    @ParameterizedTest
    @CsvSource({
            "3,4,1,2,4,6",
            "5,6,2,3,7,9",
            "1,1,1,1,2,2",
            "2,3,4,5,6,8",
            "9,8,7,6,16,14",
            "0,0,5,5,5,5",
            "7,2,3,8,10,10",
            "4,4,4,4,8,8",
            "10,5,0,0,10,5",
            "6,3,2,7,8,10"
    })

    void suma(int parteReal1, int parteImaginaria1, int parteReal2, int parteImaginaria2, int ResultReal,  int ResultImaginaria) {
        NumeroComplejo Numero1 = new NumeroComplejo(parteReal1, parteImaginaria1);
        NumeroComplejo Numero2 = new NumeroComplejo(parteReal2, parteImaginaria2);
        NumeroComplejo resultadoEsperado = new  NumeroComplejo(ResultReal, ResultImaginaria);
        NumeroComplejo resultadoSuma = Numero1.sumar(Numero2);
        assertEquals(resultadoEsperado.getParteReal(), resultadoSuma.getParteReal());
        assertEquals(resultadoEsperado.getParteImaginaria(), resultadoSuma.getParteImaginaria());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/resta.csv")
    void resta(int ParteReal1, int parteImaginaria1, int parteReal2, int parteImaginaria2, int ResultReal,  int ResultImaginaria){
        NumeroComplejo Numero1 = new NumeroComplejo(ParteReal1, parteImaginaria1);
        NumeroComplejo Numero2 = new NumeroComplejo(parteReal2, parteImaginaria2);
        NumeroComplejo resultadoEsperado = new  NumeroComplejo(ResultReal, ResultImaginaria);
        NumeroComplejo resultadoResta = Numero1.restar(Numero2);
        assertEquals(resultadoEsperado.getParteReal(), resultadoResta.getParteReal());
        assertEquals(resultadoEsperado.getParteImaginaria(), resultadoResta.getParteImaginaria());
    }
}