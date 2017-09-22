from cyrest import *


version = getCytoscapeVersion()
print("Cytoscape Version: " + version)

networkSUID = createEmptyNetwork()
nodeSUIDs = createNodes(networkSUID, 10000)

colNames = createColumns(networkSUID, 'Boolean', 30)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Boolean')

print('Done')
