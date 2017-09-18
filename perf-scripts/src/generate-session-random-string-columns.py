from cyrest import *


version = getCytoscapeVersion()
print("Cytoscape Version: " + version)

networkSUID = createEmptyNetwork()
nodeSUIDs = createNodes(networkSUID, 10000)

colNames = createColumns(networkSUID, 'String', 10)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'String')

print('Done')
