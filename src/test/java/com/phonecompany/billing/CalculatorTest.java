package com.phonecompany.billing;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest{

    @Test
    public void test(){
        Calculator calculator = new Calculator();
        assertEquals(BigDecimal.valueOf(6.2), calculator.calculate("420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00\n"));
        assertEquals(BigDecimal.valueOf(0.0), calculator.calculate("420774577453,13-01-2020 03:32:35,13-01-2020 03:40:41"));
        assertEquals(BigDecimal.valueOf(0.0), calculator.calculate("420774577453,13-01-2020 03:32:35,13-01-2020 03:40:41\n420774577453,13-01-2020 15:57:54,13-01-2020 16:04:44"));
        assertEquals(BigDecimal.valueOf(3.3), calculator.calculate("420774577453,13-01-2020 03:32:35,13-01-2020 03:40:41\n420774577456,13-01-2020 15:57:54,13-01-2020 16:04:44"));
        assertEquals(BigDecimal.valueOf(9.3),
                calculator.calculate("420774577453,13-01-2020 09:32:48,13-01-2020 09:42:34\n420774221483,13-01-2020 03:32:35,13-01-2020 03:40:41\n420774577874,13-01-2020 15:57:54,13-01-2020 16:04:44")
        );
    }
}