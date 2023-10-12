import java.sql.*;
import java.util.*;

class EDump {
	static final String USER = "sa";
	static final String PASS = "Pass@word";
	static final int NHEADERCOLS = 9;
	static final Map<String, String> defaultCommands = new HashMap<String, String>()
	{
		private static final long serialVersionUID = 1L;
	{
		put("AspNetUsers", "select id, username from AspNetUsers");
		put("catalog", "select C.id, C.description, C.price, CT.type, CB.brand from catalog C " + 
							"left join catalogtype CT on C.catalogtypeid = CT.id " + 
							"left join catalogbrand CB on C.catalogbrandid = CB.id");
		put("catalogbrand", "select id, brand from catalogbrand");
		put("catalogtype", "select id, type from catalogtype");
	}};
	
	private static void usage() throws Exception {
		System.out.println("java -jar EDump [-h <host-name>] [-d <db-name>] [-t <table-name>] [-q] [-a]");
		throw new Exception("invalid usage");
	}
	
	private static void dumpTable(Connection c, String tableName, Boolean dumpAllColumns, Boolean quiet) throws Exception {
		String query;
		ResultSet r;
		
		query = defaultCommands.get(tableName);
		if (query == null || dumpAllColumns)
			query = "select * from " + tableName;
		
		//System.out.println(query);
		
		r = c.createStatement().executeQuery(query);
		while (r.next()) {
			for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {
				if (!quiet || r.getObject(i) != null)
					System.out.print(r.getMetaData().getColumnName(i).toLowerCase() +  ": " + r.getObject(i) + " ");
			}
			System.out.println();
		}
		
		return;
	}
	
	private static void dumpTables(Connection c) throws Exception {
		String query;
		ResultSet r;
		
		query = "select name from sys.tables";
		r = c.createStatement().executeQuery(query);
		while (r.next()) {
			for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {
				if (r.getObject(i) != null)
					System.out.print(r.getMetaData().getColumnName(i).toLowerCase() +  ": " + r.getObject(i) + " ");
			}
			System.out.println();
		}
		
		return;
	}
	
	private static void dumpDBs(Connection c) throws Exception {
		String query;
		ResultSet r;
		
		query = "select name from sys.databases";
		r = c.createStatement().executeQuery(query);
		while (r.next()) {
			for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {
				if (r.getObject(i) != null)
					System.out.print(r.getMetaData().getColumnName(i).toLowerCase() +  ": " + r.getObject(i) + " ");
			}
			System.out.println();
		}
		
		return;
	}

	public static void main(String[] args) {
		try {
			Connection c;
			String tableName = null, hostName = null, dbName = null, arg;
			Boolean dumpAllColumns = false, quiet = false;
			int i = 0;

			// parse command line arguments
	        while (i < args.length && args[i].startsWith("-")) {
	            arg = args[i++];
	            if (arg.equals("-t")) {
	                if (i < args.length)
	                	tableName = args[i++];
	                else
	                    usage();
	            } else if (arg.equals("-d")) {
	                if (i < args.length)
	                	dbName = args[i++];
	                else
	                    usage();
	            } else if (arg.equals("-h")) {
	            	if (i < args.length)
	                	hostName = args[i++];
	                else
	                    usage();
	            } else if (arg.equals("-a")) {
	            	dumpAllColumns = true;
	            } else if (arg.equals("-q")) {
	            	quiet = true;
	            }
	        }

	        if (hostName == null)
	        	hostName = "localhost";
	        
	        if (dbName == null) {
	        	c = DriverManager.getConnection("jdbc:sqlserver://" + hostName + ":5433;encrypt=false", USER, PASS);
	        	dumpDBs(c);
	        	return;
	        }
	        
	        if (dbName.equals("IdentityDb")) {
	        	// silly inconsistency..
	        	dbName = "Microsoft.eShopOnContainers.Service." + dbName;
	        } else {
	        	dbName = "Microsoft.eShopOnContainers.Services." + dbName;
	        }
	        
	        c = DriverManager.getConnection("jdbc:sqlserver://" + hostName + ":5433;databaseName=" + dbName + ";encrypt=false", USER, PASS);
	        
	        if (tableName == null) {
	        	dumpTables(c);
	        } else {
	        	if (dbName.contains("OrderingDb")) {
	        		// another silly inconsistency..
	        		tableName = "ordering." + tableName;
	        	}
	        	dumpTable(c, tableName, dumpAllColumns, quiet);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
