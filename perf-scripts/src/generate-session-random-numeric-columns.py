from cyrest import *


version = getCytoscapeVersion()
print("Cytoscape Version: " + version)

networkSUID = createEmptyNetwork()
nodeSUIDs = createNodes(networkSUID, 10000)

colNames = createColumns(networkSUID, 'Integer', 10)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Integer')
    
colNames = createColumns(networkSUID, 'Long', 10)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Long')
    
colNames = createColumns(networkSUID, 'Double', 10)
for colName in colNames:
    fillColumn(networkSUID, nodeSUIDs, colName, 'Double')

print('Done')
