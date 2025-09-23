package ejemplos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {
    Fibonacci f = new  Fibonacci();
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void testFibonacci(int lim) {
        String resultado1 = "0,1,1";
        String resultado2 = "0,1,1,2,3";
        String resultado3 = "0,1,1,2,3,5";

        if(lim  == 1){
            assertEquals(resultado1, f.fibonacci(lim));
        }

        if(lim == 3){
            assertEquals(resultado2, f.fibonacci(lim));
        }

        if(lim == 5){
            assertEquals(resultado3, f.fibonacci(lim));
        }
    }
}