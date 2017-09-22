from cyrest import *


version = getCytoscapeVersion()
print("Cytoscape Version: " + version)

networkSUID = createEmptyNetwork()
nodeSUIDs = createNodes(networkSUID, 10000)

colNames = createColumns(networkSUID, 'Integer', 10, isList=True)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Integer', isList=True)
    
colNames = createColumns(networkSUID, 'Long', 10, isList=True)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Long', isList=True)
    
colNames = createColumns(networkSUID, 'Double', 10, isList=True)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Double', isList=True)

print('Done')
