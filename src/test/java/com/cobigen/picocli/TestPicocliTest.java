package com.cobigen.picocli;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPicocliTest {
	/** Test resources root path */
    private static String testFileRootPath = "src/test/resources/testdata/unittest/";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMain() {
		try {
			 File baseFile = new File(testFileRootPath + "EmployeeEntity.java");
			 String args = "main";
			TestPicocli.main(args);
			Scanner in = new Scanner(System.in);
			String s = in. nextLine();
			assertEquals(baseFile, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
