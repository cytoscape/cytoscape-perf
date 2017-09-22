from cyrest import *


version = getCytoscapeVersion()
print("Cytoscape Version: " + version)

networkSUID = createEmptyNetwork()
nodeSUIDs = createNodes(networkSUID, 10000)

colNames = createColumns(networkSUID, 'String', 30, isList=True)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'String', value=['MyValueStr','MyValueStr','MyValueStr','MyValueStr','MyValueStr'], isList=True)

print('Done')
