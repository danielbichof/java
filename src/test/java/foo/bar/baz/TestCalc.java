package foo.bar.baz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import foo.bar.baz.Calc;

class CalcJUnitTests {
    private final Calc calculator = new Calc();

    @Test
    void addition()
    {
        assertEquals(2, calculator.sum(1,1));
    }
}
