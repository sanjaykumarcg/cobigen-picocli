package com.cobigen.picocli;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPicocliTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMain() {
		try {
			TestPicocli.main("C:\\\\Users\\\\syadav9\\\\Desktop\\\\employeemanagement\\\\dataaccess\\\\api\\\\EmployeeEntity.java");
			Scanner in = new Scanner(System.in);
			String s = in. nextLine();
			assertEquals("C:\\\\Users\\\\syadav9\\\\Desktop\\\\employeemanagement\\\\dataaccess\\\\api\\\\EmployeeEntity.java", s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
