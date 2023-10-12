package ca.testeshop.tests;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import ca.testeshop.services.*;
import ca.testeshop.utils.EShopResponse;
import ca.testeshop.utils.HttpUtils;
import ca.testeshop.utils.TestUtils;

public class TestRegistration extends Test {
	
	public TestRegistration() {
		
	}
	
	public TestRegistration(String username, String password, Services services) {
		super(username, password, services);
	}
	
	// test registration followed by login
	private void testSpecificCase1() throws Exception {
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase1");
		
		doUserRegistrationAndLogin();
		
		System.out.println("user: " + getUserName());
		
		response = identityService.getUserInfo();
		//response.dump();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
	}
	
	// make sure it works back-to-back
	private void testSpecificCase2() throws Exception {
		Integer iters;
		EShopResponse response;
		
		System.out.println("\ntestSpecificCase2");
		
		iters = 0;
		while (iters < 3) {
			doUserRegistrationAndLogin();
			
			System.out.println("user: " + getUserName());
			
			response = identityService.getUserInfo();
			//response.dump();
			TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
			iters++;
		}
	}
	
	private void _testSpecificCase3() throws Exception {
		EShopResponse response;
		
		System.out.println("\n_testSpecificCase3");
		
		doUserRegistrationAndLogin();
		
		System.out.println("user: " + getUserName() + " thread " + Thread.currentThread().getId());
		
		response = identityService.getUserInfo();
		//response.dump();
		TestUtils.failIf(response.httpCode != HttpURLConnection.HTTP_OK, response.toString());
	}
	
	// multi-threaded test
	private void testSpecificCase3() throws Exception {
		List<Thread> threads;
		ConcurrentLinkedQueue<Exception> exceptions = new ConcurrentLinkedQueue<Exception>();
		Thread thread;
		
		System.out.println("\ntestSpecificCase3");
		
		threads = new LinkedList<Thread>();
		for (int i = 0; i < 64; i++) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						_testSpecificCase3();
					} catch (Exception e) {
						exceptions.add(e);
					}
				}
			});
			threads.add(thread);
			thread.start();
		}

		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).join();
		}

		if (exceptions.size() != 0) {
			System.out.println("caught " + exceptions.size() + " exceptions");
			for (Exception e : exceptions) {
				e.printStackTrace();
			}
			throw new Exception("One or more thread(s) threw exception(s)");
		}
	}
	
	public void run() throws Exception {
		testSpecificCase1();
		testSpecificCase2();
		testSpecificCase3();
	}
}
