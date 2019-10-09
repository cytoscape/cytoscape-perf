package org.cytoscape.view.model;

import java.util.Random;

import org.cytoscape.ding.NetworkViewTestSupport;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.spacial.SpacialIndex2D;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class SimpleSpacialIndex2DBenchmark {
	
	private SpacialIndex2D<Long> spacialIndex;
	private float[] extents = new float[4];
	
	@Setup
	public void createNetwork() {
		NetworkViewTestSupport testSupport = new NetworkViewTestSupport();
		CyNetwork network = testSupport.getNetwork();
		for(int i = 0; i < 100; i++) {
			network.addNode();
		}
		CyNetworkView netView = testSupport.getNetworkViewFactory().createNetworkView(network);
		
		Random random = new Random();
		for(CyNode n : network.getNodeList()) {
			double x = random.nextDouble() * 10;
			double y = random.nextDouble() * 10;
			View<CyNode> nv = netView.getNodeView(n);
			setGeometry(nv, x, y, 10, 10);
		}
		
		this.spacialIndex = netView.createSnapshot().getSpacialIndex2D();
	}
	
	private static void setGeometry(View<CyNode> nv, double x, double y, double w, double h) {
		nv.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, x);
		nv.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, y);
		nv.setVisualProperty(BasicVisualLexicon.NODE_WIDTH, w);
		nv.setVisualProperty(BasicVisualLexicon.NODE_HEIGHT, h);
	}

	
//	@Benchmark
	public void testGetMBR() {
		spacialIndex.getMBR(extents);
	}
	
	@Benchmark
	public Object testQueryOverlap() {
		return spacialIndex.queryOverlap(0, 0, 10, 10);
	}
}
