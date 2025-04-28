package com.ramyakata.microservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ramyakata.microservice.domain.CalculationInput;
import com.ramyakata.microservice.domain.CalculationOutput;
import com.ramyakata.microservice.exception.CalculationException;
import com.ramyakata.microservice.svc.ICalculation;

@SpringBootTest
class ClaculationServiceApplicationTests {

	@Autowired
    private ICalculation calculationService;
	
	@Test
    void testCalculateWithValidMonthly() throws CalculationException {

		
        CalculationInput input = new CalculationInput();
        input.setBaseAmount(1000);
        input.setDiscountPercentage(10);
        input.setNumberOfMonths("monthly");

        CalculationOutput output = calculationService.calculate(input);

        assertNotNull(output);
        assertEquals(1000, output.getBaseAmount());
        assertEquals(10, output.getDiscountPercentage());
        assertEquals("monthly", output.getNumberOfMonths());
        assertEquals(7.0, output.getTax());
        assertEquals(963, output.getTotalAmount());  // (1000 - 100) * 1
    }

    @Test
    void testCalculateWithValidQuarterly() throws CalculationException {
        CalculationInput input = new CalculationInput();
        input.setBaseAmount(1000);
        input.setDiscountPercentage(10);
        input.setNumberOfMonths("quarterly");

        CalculationOutput output = calculationService.calculate(input);

        assertNotNull(output);
        assertEquals(1000, output.getBaseAmount());
        assertEquals(10, output.getDiscountPercentage());
        assertEquals("quarterly", output.getNumberOfMonths());
        assertEquals(9.0, output.getTax());  // Tax rate for 1000 is 7%
        assertEquals(2943, output.getTotalAmount());  // (1000 - 100) * 3 + tax
     }

    @Test
    void testCalculateWithValidSemiAnnually() throws CalculationException {
        CalculationInput input = new CalculationInput();
        input.setBaseAmount(1000);
        input.setDiscountPercentage(10);
        input.setNumberOfMonths("half-yearly");

        CalculationOutput output = calculationService.calculate(input);

        assertNotNull(output);
        assertEquals(1000, output.getBaseAmount());
        assertEquals(10, output.getDiscountPercentage());
        assertEquals("half-yearly", output.getNumberOfMonths());  // (1000 - 100) * 6
        assertEquals(11.0, output.getTax());  // Tax rate for 1000 is 7%
        assertEquals(5994, output.getTotalAmount());  // (1000 - 100) * 3 + tax
    }

    @Test
    void testCalculateWithValidAnnually() throws CalculationException {
        CalculationInput input = new CalculationInput();
        input.setBaseAmount(1000);
        input.setDiscountPercentage(10);
        input.setNumberOfMonths("annually");

        CalculationOutput output = calculationService.calculate(input);

        assertNotNull(output);
        assertEquals(1000, output.getBaseAmount());
        assertEquals(10, output.getDiscountPercentage());
        assertEquals("annually", output.getNumberOfMonths());
        assertEquals(11.0, output.getTax());  // Tax rate for 1000 is 7%
        assertEquals(11988, output.getTotalAmount()); 
    }

    @Test
    void testCalculateWithNullInput() {
        CalculationInput input = null;

        assertThrows(CalculationException.class, () -> {
            calculationService.calculate(input);
        });
    }

    @Test
    void testCalculateWithInvalidMonths() {
        CalculationInput input = new CalculationInput();
        input.setBaseAmount(1000);
        input.setDiscountPercentage(10);
        input.setNumberOfMonths("invalid-months");

        assertThrows(CalculationException.class, () -> {
            calculationService.calculate(input);
        });
    }
    

}
