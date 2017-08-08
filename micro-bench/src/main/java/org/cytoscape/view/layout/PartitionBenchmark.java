/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.cytoscape.view.layout;


import java.util.ArrayList;
import java.util.List;

import org.cytoscape.ding.NetworkViewTestSupport;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@Fork(1)
public class PartitionBenchmark {

	private CyNetworkView networkView;
	private EdgeWeighter edgeWeighter;
	
	
	@Setup
	public void createNetwork() {
		NetworkViewTestSupport networkSupport = new NetworkViewTestSupport();
		CyNetwork network = networkSupport.getNetworkFactory().createNetwork();
		
		List<CyNode> partition1 = new ArrayList<>(100);
		List<CyNode> partition2 = new ArrayList<>(100);
		
		for(int i = 0; i < 100; i++) {
			partition1.add(network.addNode());
			partition2.add(network.addNode());
		}
		
		for(CyNode n1 : partition1) {
			for(CyNode n2 : partition1) {
				network.addEdge(n1, n2, false);
			}
		}
		
		for(CyNode n1 : partition2) {
			for(CyNode n2 : partition2) {
				network.addEdge(n1, n2, false);
			}
		}
		
		edgeWeighter = new EdgeWeighter();
		networkView = networkSupport.getNetworkViewFactory().createNetworkView(network);
	}
	
	
	@Benchmark
	public Object testPartition() {
		return PartitionUtil.partition(networkView, false, edgeWeighter);
	}
	
}
