package org.cytoscape.model.internal;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.TableTestSupport;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@Fork(1)
public class TableLookupBooleanBenchmark {

	@Param({"100", "1000", "10000"})
	public int tableSize;
	
	private CyTable table;
	
	
	@Setup
	public void createTable() {
		TableTestSupport tableTestSupport = new TableTestSupport();
		table = tableTestSupport.getTableFactory().createTable("test", CyNetwork.SUID, Long.class, true, false);
		table.createColumn(CyNetwork.SELECTED, Boolean.class, false);
		for(int i = 0; i < tableSize; i++) {
			table.getRow(Long.valueOf(i)).set(CyNetwork.SELECTED, i % 2 == 0);
		}
	}
	

	@Benchmark
	public int testCountMatchingRows() {
		return table.countMatchingRows(CyNetwork.SELECTED, true);
	}
	
	@Benchmark
	public Object testGetMatchingRows() {
		return table.getMatchingRows(CyNetwork.SELECTED, true);
	}
	
//	@Benchmark
//	public Object testGetMatchingKeys() {
//		return table.getMatchingKeys(CyNetwork.SELECTED, true, Long.class);
//	}
	
	
}
