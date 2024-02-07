package ca.edump.main;

import java.sql.*;
import java.util.*;

import redis.clients.jedis.Jedis;

class Main {
	static final String USER = "root";
	static final String PASS = "password";
	static final int NHEADERCOLS = 9;
	static final Map<String, String> defaultCommands = new HashMap<String, String>()
	{
		private static final long serialVersionUID = 1L;
	{
		put("basketdb.basketitem", "select BI.id, UE.username, CI.name, CI.price from basketdb.basketitem BI " + 
				"left join identitydb.USER_ENTITY UE on UE.id = BI.user_id " + 
				"left join catalogdb.catalogitem CI on CI.id = BI.catalog_item_id");
		put("catalogdb.catalogitem", "select CI.id, CI.name, CI.price, CI.image_id from catalogdb.catalogitem CI");
		put("identitydb.USER_ENTITY", "select UE.id, UE.username, UE.realm_id from identitydb.USER_ENTITY UE");
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
	
	private static void dumpRedisDb() throws Exception {
		Jedis redisDb = new Jedis();
		Set<String> keys;
		
		keys = redisDb.keys("*");
		for (String key : keys) {
			String keyType = redisDb.type(key);
			switch (keyType) {
				case "hash":
					System.out.println(key + " : " + redisDb.hgetAll(key));
					break;
				case "set":
					System.out.println(key + " : IGNORING set KEY TYPE" /*redisDb.smembers(key)*/ );
					break;
				default:
					System.out.println(key + " : KEY TYPE " + keyType + " NOT YET SUPPORTED");
					break;
			}
		}
		
		redisDb.close();
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

			if (dbName.equals("basketdb")) {
				dumpRedisDb();
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
