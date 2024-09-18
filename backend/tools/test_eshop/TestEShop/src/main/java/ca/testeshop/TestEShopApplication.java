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

import ca.testeshop.services.Services;
import ca.testeshop.utils.TestUtils;


//
// mvn -DskipTests clean package
// java -jar target/test-eshop-0.0.1-SNAPSHOT.jar
//

@SpringBootApplication
public class TestEShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TestEShopApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws Exception {
		try {

			Logger.getLogger("sun.net.www.protocol.http.HttpURLConnection").setLevel(Level.OFF);

			Options options = new Options();
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd;
			String testName = null, userName = null;
			Services.location backEndLocation = Services.location.LOCAL;
			TestEShop testEShop;

			// define the options and parse the command line

			// testName option
			options.addOption(
				Option
				.builder("t")
				.hasArg(true)
				.required(false)
				.desc("test name")
				.build()
			);

			// userName option
			options.addOption(
				Option
				.builder("u")
				.hasArg(true)
				.required(false)
				.desc("user name")
				.build()
			);

			// list option
			options.addOption(
				Option
				.builder("l")
				.required(false)
				.desc("list tests")
				.build()
			);
			
			// quiet option
			options.addOption(Option.builder("q")
					.required(false)
					.desc("mute tests")
					.build());
			
			// back end option
			options.addOption(Option.builder("b")
					.required(false)
					.hasArg(true)
					.desc("back end")
					.build());

			cmd = parser.parse(options, args);

			if (cmd.hasOption("u"))
				userName = cmd.getOptionValue("u");
			
			if (cmd.hasOption("b")) {
				switch (cmd.getOptionValue("b")) {
					case "local":
						backEndLocation = Services.location.LOCAL;
						break;
					case "dev":
						backEndLocation = Services.location.DEV;
						break;
					default:
						throw new Exception("unsupported back end " + cmd.getOptionValue("b"));
				}
			}

			// TODO: prompt for user password
			testEShop = new TestEShop(userName, TestUtils.defaultPassword, backEndLocation);

			if (cmd.hasOption("l")) { // list tests
				testEShop.listTests();
				// return;
			} else { // run one or all tests
				if (cmd.hasOption("t"))
					testName = cmd.getOptionValue("t");
				if (cmd.hasOption("q"))
					testEShop.quiet = true;

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
