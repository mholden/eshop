package ca.testeshop;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.*;

import ca.testeshop.services.*;
import ca.testeshop.tests.*;
import ca.testeshop.utils.TestUtils;

public class TestEShop {
	
	public String username;
	public String password;
	public Services services;
	public Boolean quiet = false;
	public String quietFile = "/tmp/test-eshop.quiet";
	
	static final Set<Class<?>> tests = new HashSet<Class<?>>() {
		private static final long serialVersionUID = 1L; {
		add(TestHomePage.class);
		add(TestLogin.class);
		add(TestBasket.class);
		add(TestOrders.class);
		add(TestRegistration.class);
		add(TestCatalogCRUD.class);
		add(TestSecurity.class);
		add(TestContent.class);
		// ... add your new test here
	}};
	
	private void init(Services.location location) throws Exception {
		services = new Services(location);
	}
	
	public TestEShop() throws Exception {
		init(Services.location.LOCAL);
	}
	
	public TestEShop(String username, String password, Services.location location) throws Exception {
		init(location);
		this.username = username;
		this.password = password;
	}
	
	public void listTests() throws Exception {
		Iterator<Class<?>> testsIterator;
		Class<?> test;
		
		testsIterator = tests.iterator();
		while (testsIterator.hasNext()) {
			test = testsIterator.next();
			System.out.println(test.getSimpleName());
		}
	}
	
	public void runTest(Class<?> c) throws Exception {
		Constructor<?> constructor = c.getConstructor(String.class, String.class, Services.class);
		Test test = (Test) constructor.newInstance(username, password, services);
		String testName = c.getSimpleName();
		PrintStream original = null, originalErr = null;
		Boolean fail;
		Exception _e = null;
		
		System.out.print(testName);
		if (quiet) {
			original = System.out;
			originalErr = System.err;
		    //System.setOut(new PrintStream(new FileOutputStream("/dev/null")));
			System.setOut(new PrintStream(new FileOutputStream("/tmp/TestEShop-" + testName + ".out")));
			System.setErr(System.out);
			System.out.print(testName);
		}
		fail = false;
		try {
			test.run();
			if (test.asyncChannel.get() != null && test.asyncChannel.get().exceptions.size() != 0) {
				throw new Exception("Async channel exception(s)");
			}
		} catch (Exception e) {
			fail = true;
			_e = e;
		}
		if (quiet) {
			System.setOut(original);
			System.setErr(originalErr);
		}
		if (fail) {
			System.out.println(" FAIL");
			throw _e;
		} else {
			System.out.println(" PASS");
		}
	}
	
	public void runTests() throws Exception {
		Iterator<Class<?>> testsIterator;
		Class<?> test;
		Integer nFails = 0;
		
		testsIterator = tests.iterator();
		while (testsIterator.hasNext()) {
			test = testsIterator.next();
			try {
				runTest(test);
			} catch (Exception e) {
				nFails++;
			}
		}
		TestUtils.failIf(nFails != 0, null);
	}
}
