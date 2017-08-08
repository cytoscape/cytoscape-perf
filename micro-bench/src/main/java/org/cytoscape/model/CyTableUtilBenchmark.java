package org.cytoscape.model;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Fork(1)
public class CyTableUtilBenchmark {

	@Param({"10","100","1000","10000"})
	public int networkSize;
	
	private CyNetwork network;
	
	@Setup
	public void createNetwork() {
		NetworkTestSupport networkTestSupport = new NetworkTestSupport();
		network = networkTestSupport.getNetworkFactory().createNetwork();
		
		for(int i = 0; i < networkSize; i++) {
			network.addNode();
		}
		
		int edges = 0;
		outer:
		for(CyNode source : network.getNodeList()) {
			for(CyNode target : network.getNodeList()) {
				network.addEdge(source, target, false	);
				if(edges++ == networkSize)
					break outer;
			}
		}
		
		for(CyNode node : network.getNodeList()) {
			if(node.getSUID() % 2 == 0) {
				network.getRow(node).set(CyNetwork.SELECTED, true);
			}
		}
		for(CyEdge edge : network.getEdgeList()) {
			if(edge.getSUID() % 2 == 0) {
				network.getRow(edge).set(CyNetwork.SELECTED, true);
			}
		}
	}
	
	
	@Benchmark
	public void testCyTableUtil(Blackhole bh) {
		bh.consume(CyTableUtil.getNodesInState(network, CyNetwork.SELECTED, true));
		bh.consume(CyTableUtil.getEdgesInState(network, CyNetwork.SELECTED, true));
	}
	
}
