package com.calculater.assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.calculater.assessment.exceptions.NegativeNotAllowedException;

@SpringBootTest
class AssessmentApplicationTests {

	@InjectMocks
	StringCalculator stringCalculator;

	@Test
	public void addMethodTest() throws Exception {
		int sum = 0;

		sum = stringCalculator.add("6,7,9\n8");
		assertEquals(sum, 30);

		// , trailing \n will return 0
		sum = stringCalculator.add("6,\n8");
		assertEquals(sum, 0);

		// More than 1000 value will discarded
		sum = stringCalculator.add("8,2000\n8,100");
		assertEquals(sum, 116);

		// If negative number is present then exception will thrown
		try {
			stringCalculator.add("6,-7,9\n8");
		} catch (NegativeNotAllowedException nex) {
			assertEquals(nex.getMessage(), "Negative number not allowed");
		}

		// single delimiter string
		sum = stringCalculator.add("//[a]\n100a100a200");
		assertEquals(sum, 400);

		// single delimiter string - if delimiter differ than in bracket will return 0
		sum = stringCalculator.add("//[a]\n100a100aaa200");
		assertEquals(sum, 0);

		// single delimiter string - If negative number is present then exception will
		// thrown
		try {
			stringCalculator.add("//[a]\n100a-100a200");
		} catch (NegativeNotAllowedException nex) {
			assertEquals(nex.getMessage(), "Negative number not allowed");
		}

		// multiple delimiter string
		sum = stringCalculator.add("//[a][#]\n100a100#200a200");
		assertEquals(sum, 600);

		sum = stringCalculator.add("//[a][#][##]\n100a100##200#200");
		assertEquals(sum, 600);

		// multiple delimiter string - if delimiter differ than in bracket will return 0
		sum = stringCalculator.add("//[aa][#]\n100a100aaa200##400");
		assertEquals(sum, 0);

		// multiple delimiter string - If negative number is present then exception will
		// thrown
		try {
			stringCalculator.add("//[a][#]\n100a-100#200");
		} catch (NegativeNotAllowedException nex) {
			assertEquals(nex.getMessage(), "Negative number not allowed");
		}

	}

}
