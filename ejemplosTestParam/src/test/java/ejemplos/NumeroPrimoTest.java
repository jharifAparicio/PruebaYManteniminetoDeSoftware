package ejemplos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NumeroPrimoTest {
    @ParameterizedTest
    @CsvSource({"2, true","3,true","22, false","15,false"})
    void validate(int numero, boolean expected) {
        NumeroPrimo np = new NumeroPrimo();
        assertEquals(expected, np.validate(numero));
    }
}