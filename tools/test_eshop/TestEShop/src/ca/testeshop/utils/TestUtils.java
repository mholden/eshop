package ca.testeshop.utils;

import java.nio.file.*;

public class TestUtils {
	
	public static final String defaultPassword = "Pass@word1";
	public static final String defaultEmail = "test@testeshop.ca";
	
	TestUtils() {
		
	}
	
	public static void failIf(boolean condition, String message) throws Exception {
		if (condition) {
			System.out.println(" FAIL");
			if (message != null)
				System.out.println(message);
			throw new Exception("Test failed");
		}
	}
	
	public static void log(String toLog, String fileName) throws Exception {
		if (fileName != null)
			Files.write(Paths.get(fileName), toLog.getBytes(), StandardOpenOption.APPEND);
		else 
			System.out.println(toLog);
	}
}
