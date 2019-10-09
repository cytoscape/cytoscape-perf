import requests
import json
import random
import string

baseurl = 'http://localhost:1234/v1'

def getCytoscapeVersion():
    r = requests.get(f'{baseurl}/version')
    r.raise_for_status()
    return r.json()['cytoscapeVersion']

def createEmptyNetwork(title='MyNetwork', collection='MyCollection'):
    r = requests.post(f'{baseurl}/networks', params={'collection':collection, 'title':title, 'format':'edgelist'})
    r.raise_for_status()
    return r.json()['networkSUID']

def deleteNetwork(networkSUID):
    r = requests.delete(f'{baseurl}/networks/{networkSUID}')
    r.raise_for_status()

def createNodes(networkSUID, count):
    nodes = [str(n) for n in range(1, count+1)]
    nodesJson = json.dumps(nodes)
    r = requests.post(f'{baseurl}/networks/{networkSUID}/nodes', data=nodesJson)
    r.raise_for_status()
    nodeSUIDs = [d['SUID'] for d in r.json()]
    return nodeSUIDs

def createColumns(networkSUID, colType, count, isList=False):
    colNames = [f'col_{colType}_{n}' for n in range(1, count+1)]
    if isList:
        colData = [{'name':name, 'type':colType, 'list':'true'} for name in colNames]
    else:
        colData = [{'name':name, 'type':colType} for name in colNames]
    colJson  = json.dumps(colData)
    r = requests.post(f'{baseurl}/networks/{networkSUID}/tables/defaultnode/columns', data=colJson)
    r.raise_for_status()
    return colNames
    
    
def fillColumn(networkSUID, nodeSUIDs, colName, colType, value=None, isList=False):
    randGen = {
        'Integer': ( lambda: random.randint(0,999) ),
        'Long'   : ( lambda: random.randint(0,999) ),
        'Double' : ( lambda: random.randint(0,999) ),
        'Boolean': ( lambda: random.choice(['true','false']) ),
        'String' : ( lambda: ''.join([random.choice(string.ascii_lowercase) for _ in range(0,10)]) ),
    }
    randGenList = {
        'Integer': ( lambda: [randGen['Integer']() for _ in range(0,5)] ),
        'Long'   : ( lambda: [randGen['Long']()    for _ in range(0,5)] ),
        'Double' : ( lambda: [randGen['Double']()  for _ in range(0,5)] ),
        'Boolean': ( lambda: [randGen['Boolean']() for _ in range(0,5)] ),
        'String' : ( lambda: [randGen['String']()  for _ in range(0,5)] ),
    }
    dataFun = (randGenList[colType] if isList else randGen[colType]) if value is None else lambda:value
    cellData = [{'SUID':suid, 'value':dataFun()} for suid in nodeSUIDs]
    cellJson = json.dumps(cellData)
    r = requests.put(f'{baseurl}/networks/{networkSUID}/tables/defaultnode/columns/{colName}', data=cellJson)
    r.raise_for_status()
