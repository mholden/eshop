package ca.testeshop;

import java.lang.reflect.*;
import java.util.*;

import ca.testeshop.services.*;
import ca.testeshop.tests.*;

public class TestEShop {
	
	public String username;
	public String password;
	public Services services;
	
	static final Set<Class<?>> tests = new HashSet<Class<?>>() {
		private static final long serialVersionUID = 1L; {
		add(TestHomePage.class);
		add(TestLogin.class);
		add(TestBasket.class);
		add(TestOrders.class);
		add(TestRegistration.class);
		add(TestCatalogCRUD.class);
		add(TestSecurity.class);
		// ... add your new test here
	}};
	
	private void init() throws Exception {
		services = new Services(Services.location.LOCAL);
	}
	
	public TestEShop() throws Exception {
		init();
	}
	
	public TestEShop(String username, String password) throws Exception {
		init();
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
		//Class<?> c = Class.forName("ca.testeshop.tests." + testName);
		Constructor<?> constructor = c.getConstructor(String.class, String.class, Services.class);
		Test test = (Test) constructor.newInstance(username, password, services);
		
		System.out.print(c.getSimpleName());
		try {
			test.run();
		} catch (Exception e) {
			throw e;
		}
		System.out.println(" PASS");
	}
	
	public void runTests() throws Exception {
		Iterator<Class<?>> testsIterator;
		Class<?> test;
		
		testsIterator = tests.iterator();
		while (testsIterator.hasNext()) {
			test = testsIterator.next();
			runTest(test);
		}
	}
}
