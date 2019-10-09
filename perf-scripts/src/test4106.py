from cyrest import *


version = getCytoscapeVersion()
print("Cytoscape Version: " + version)

for i in range(0, 10):
	networkSUID = createEmptyNetwork()
	nodeSUIDs = createNodes(networkSUID, 10000)
	deleteNetwork(networkSUID)

print("Done")