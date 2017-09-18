package org.cytoscape.model.internal;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.TableTestSupport;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Fork(1)
public class TableGetSetBenchmark {

	private static final String COL_NAME_INTEGER = "col_Integer";
	private static final String COL_NAME_BOOLEAN = "col_Boolean";
	private static final String COL_NAME_STRING  = "col_String";
	
	@Param({"100", "1000", "10000"})
	public int tableSize;
	
	private CyTable table;
	
	
	@Setup
	public void createTable() {
		TableTestSupport tableTestSupport = new TableTestSupport();
		table = tableTestSupport.getTableFactory().createTable("test", CyNetwork.SUID, Long.class, true, false);
		table.createColumn(COL_NAME_INTEGER, Integer.class, false);
		table.createColumn(COL_NAME_BOOLEAN, Boolean.class, false);
		table.createColumn(COL_NAME_STRING,  String.class,  false);
		for(long i = 0; i < tableSize; i++) {
			table.getRow(i); // pre-create all the CyRow objects
		}
	}
	

	@Benchmark
	public void testSetInteger() {
		for(long i = 0; i < tableSize; i++) {
			CyRow row = table.getRow(i);
			row.set(COL_NAME_INTEGER, 99);
		} 
	}
	
	@Benchmark
	public void testGetInteger(Blackhole bh) {
		for(int i = 0; i < tableSize; i++) {
			CyRow row = table.getRow(Long.valueOf(i));
			bh.consume(row.get(COL_NAME_INTEGER, Integer.class));
		} 
	}
	
	@Benchmark
	public void testSetBoolean() {
		for(long i = 0; i < tableSize; i++) {
			CyRow row = table.getRow(i);
			row.set(COL_NAME_BOOLEAN, true);
		} 
	}
	
	@Benchmark
	public void testGetBoolean(Blackhole bh) {
		for(int i = 0; i < tableSize; i++) {
			CyRow row = table.getRow(Long.valueOf(i));
			bh.consume(row.get(COL_NAME_BOOLEAN, Boolean.class));
		} 
	}
	
	@Benchmark
	public void testSetString() {
		for(long i = 0; i < tableSize; i++) {
			CyRow row = table.getRow(i);
			row.set(COL_NAME_STRING, "MyString");
		} 
	}
	
	@Benchmark
	public void testGetString(Blackhole bh) {
		for(int i = 0; i < tableSize; i++) {
			CyRow row = table.getRow(Long.valueOf(i));
			bh.consume(row.get(COL_NAME_STRING, String.class));
		} 
	}
	
	
	
}
