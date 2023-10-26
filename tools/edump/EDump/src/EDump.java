import java.sql.*;
import java.util.*;

class EDump {
	static final String USER = "root";
	static final String PASS = "password";
	static final int NHEADERCOLS = 9;
	static final Map<String, String> defaultCommands = new HashMap<String, String>()
	{
		private static final long serialVersionUID = 1L;
	{
		put("catalogdb.catalogitem", "select CI.id, CI.name, CI.price from catalogdb.catalogitem CI");
		put("identitydb.USER_ENTITY", "select UE.id, UE.username from identitydb.USER_ENTITY UE");
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
		
		query = "show tables";
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
		
		query = "show databases";
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
	        	c = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306", USER, PASS);
	        	dumpDBs(c);
	        	return;
	        }
	        
	        c = DriverManager.getConnection("jdbc:mysql://" + hostName + ":3306/" + dbName, USER, PASS);
	        
	        if (tableName == null) {
	        	dumpTables(c);
	        } else {
	        	dumpTable(c, dbName + "." + tableName, dumpAllColumns, quiet);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
