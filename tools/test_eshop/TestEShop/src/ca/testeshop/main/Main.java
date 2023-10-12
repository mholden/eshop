package ca.testeshop.main;

import org.apache.commons.cli.*;

import ca.testeshop.utils.*;

public class Main {
	public static void main(String[] args) {
		try {
			Options options = new Options();
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd;
			String testName = null, userName = null;
			TestEShop testEShop;
			
			// define the options and parse the command line
			
			// testName option
			options.addOption(Option.builder("t")
									.hasArg(true)
									.required(false)
									.desc("test name")
									.build());
			
			// userName option
			options.addOption(Option.builder("u")
									.hasArg(true)
									.required(false)
									.desc("user name")
									.build());
			
			// list option
			options.addOption(Option.builder("l")
									.required(false)
									.desc("list tests")
									.build());
			 
			cmd = parser.parse(options, args);
			
			if (cmd.hasOption("u"))
				userName = cmd.getOptionValue("u");
			
			// TODO: prompt for user password
			testEShop = new TestEShop(userName, TestUtils.defaultPassword);
			
			if (cmd.hasOption("l")) { // list tests
				testEShop.listTests();
				return;
			} else { // run one or all tests
				if (cmd.hasOption("t"))
					testName = cmd.getOptionValue("t");
				
				if (testName != null)
					testEShop.runTest(Class.forName("ca.testeshop.tests." + testName));
				else
					testEShop.runTests();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
