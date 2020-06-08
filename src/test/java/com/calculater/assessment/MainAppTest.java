package com.calculater.assessment;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.calculater.assessment.exceptions.NegativeNotAllowedException;


@SpringBootTest
public class MainAppTest {

	@InjectMocks
	StringCalculator mainApp;
	
	@Test
	public void addMethodTest() throws Exception
	{
	  int sum =0;
	  
	  sum =mainApp.add("6,7,9\n8");
	  assertEquals(sum, 30);
	  
	  sum =mainApp.add("6,\n8");
	  assertEquals(sum, 0);
	  
	  sum =mainApp.add("8,2000\n8,100");
	  assertEquals(sum, 116);
	  
	  
	  try{
		  mainApp.add("6,-7,9\n8");
	  }
	  catch(NegativeNotAllowedException nex)
	  {
		  assertEquals(nex.getMessage(),"Negative number not allowed");
	  }
 
	  
	  sum=mainApp.add("//[a]\na100a100a200");
	  assertEquals(sum, 400);
	  
	  sum=mainApp.add("//[a]\na100a100aaa200");
	  assertEquals(sum, 0);
	  
	  try{
		  mainApp.add("//[a]\na100a-100a200");
	  }
	  catch(NegativeNotAllowedException nex)
	  {
		  assertEquals(nex.getMessage(),"Negative number not allowed");
	  }
	}
	
}
