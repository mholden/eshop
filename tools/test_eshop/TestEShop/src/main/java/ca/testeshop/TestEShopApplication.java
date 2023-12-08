package ca.testeshop;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ca.testeshop.utils.TestUtils;

@SpringBootApplication
public class TestEShopApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(TestEShopApplication.class, args).close();
	}
	
	@Override
    public void run(String... args) throws Exception { 
    	try {
    		
    		Logger.getLogger("sun.net.www.protocol.http.HttpURLConnection").setLevel(Level.OFF);;
    		
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
				//return;
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
