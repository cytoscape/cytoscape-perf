package org.cytoscape.model.internal;

import java.util.Random;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.TableTestSupport;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Threads(1)
@Fork(1)
@Warmup(iterations=5)
@Measurement(iterations=5)
public class TableGetSetStringBenchmark {

	private static final String COL_NAME_STRING  = "col_String";
	
	@Param({"1000"})
//	@Param({"100", "1000", "10000", "100000"})
	public int tableSize;
	
	private CyTable table;
	private String[] sameStrings;
	private String[] diffStrings;
	
	@Setup
	public void createTable() {
		TableTestSupport tableTestSupport = new TableTestSupport();
		table = tableTestSupport.getTableFactory().createTable("test", CyNetwork.SUID, Long.class, true, false);
		table.createColumn(COL_NAME_STRING,  String.class,  false);
		for(long i = 0; i < tableSize; i++) {
			table.getRow(i); // pre-create all the CyRow objects
		}
	}
	
	@Setup(Level.Iteration) 
	public void setUpStrings() {
		sameStrings = new String[tableSize];
		diffStrings = new String[tableSize];
		
		for(int i = 0; i < sameStrings.length; i++) {
			sameStrings[i] = new String("MyString__");
		}
		
		for(int i = 0; i < diffStrings.length; i++) {
			sameStrings[i] = randomString(10);
		}
	}
	
	private static String randomString(int n) {
		char[] alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
		Random random = new Random();
		StringBuilder sb = new StringBuilder(n);
		for(int i = 0; i < n; i++) {
			sb.append(alphabet[random.nextInt(alphabet.length)]);
		}
		return sb.toString();
	}

	
	@Benchmark
	public void testSetSameString() {
		for(int i = 0; i < tableSize; i++) {
			CyRow row = table.getRow((long)i);
			row.set(COL_NAME_STRING, sameStrings[i]);
		} 
	}
	
	@Benchmark
	public void testSetDifferentString() {
		for(int i = 0; i < tableSize; i++) {
			CyRow row = table.getRow((long)i);
			row.set(COL_NAME_STRING, diffStrings[i]);
		} 
	}
	
	
}
