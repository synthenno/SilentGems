import sys
import re
import os

MOD_ID = 'silentgems'
DIR_OUTPUT_BLOCKSTATES = 'output/blockstates/'
DIR_OUTPUT_ITEMS = 'output/models/items/'

def createDirIfNeeded(name):
    """Create a directory if it does not exist."""
    if not os.path.exists(name):
        os.makedirs(name)

def createAllDirs():
    """Create all directories we may need."""
    createDirIfNeeded(DIR_OUTPUT_BLOCKSTATES)
    createDirIfNeeded(DIR_OUTPUT_ITEMS)

def writeBlockJSON(name, texture, textureKey):
    """Creates a Forge blockstate JSON for the block.

    Arguments:
    name -- The name to give the files (.json is automatically appended)
    texture -- The name of the texture to use
    textureKey -- The key for the texture
    """
    print('Writing block %s (texture %s)' % (name, texture))

    list = []
    list.append('{')
    list.append('  "forge_marker": 1,')
    list.append('  "defaults": {')
    list.append('    "textures": {')
    list.append('      "%s": "%s:blocks/%s"' % (textureKey, MOD_ID, texture))
    list.append('    },')
    list.append('    "model": "cube_all"')
    list.append('  },')
    list.append('  "variants": {')
    list.append('    "inventory": [{}],')
    list.append('    "normal": [{}]')
    list.append('  }')
    list.append('}')

    f = open(DIR_OUTPUT_BLOCKSTATES + name + '.json', 'w')
    for line in list:
        f.write(line + '\n')
    f.close()

def writeItemJSON(name, texture, layer=0, item_type='generated'):
    """Creates the JSON file needed for an item. Multi-layer models can be
    created, but the textures for all but the highest layer cannot be named. In
    those cases, you will need to either modify the files yourself, or modify
    this script to fit your needs.

    Arguments:
    name -- The name to give the file (.json is automatically appended)
    texture -- The name of the texture to use. Only the highest layer can be named.
    layer -- The index of the highest layer (layers start at 0)
    item_type -- The 'parent' model for the item.
    """
    print('Writing item %s (texture %s)' % (name, texture))

    f = open(DIR_OUTPUT_ITEMS + name + '.json', 'w')
    f.write('{\n')
    f.write('  "parent": "item/%s",\n' % item_type)
    f.write('  "textures": {\n')
    for i in range(0, layer):
        f.write('    "layer%d": "%s:items/blank",\n' % (i, MOD_ID))
    f.write('    "layer%d": "%s:items/%s"\n' % (layer, MOD_ID, texture))
    f.write('  }\n')
    f.write('}\n')
    f.close()



#
isBlock = False
name = ''
texture = ''
textureKey = 'all'
layer = 0
type = 'generated'

# read command line arguments
for arg in sys.argv:
    # lowercase argument for matching purposes
    argl = str.lower(arg)
    # try to match some optional arguments
    matchCount = re.compile('count=').match(argl)
    matchLayer = re.compile('layer=').match(argl)
    matchTexture = re.compile('texture=').match(argl)
    matchTextureKey = re.compile('texture_?key=').match(argl)
    matchType = re.compile('type=').match(argl)

    # Block or item? Default is item.
    if argl == 'block':
        isBlock = True
    elif argl == 'item':
        isBlock = False

    if matchLayer: # layer index
        layer = int(re.search('\d+', argl).group(0))
    elif matchTexture: # texture name
        texture = re.sub('texture=', '', argl)
    elif matchType: # item parent model
        type = re.sub('type=', '', argl)
    elif matchTextureKey: # texture key
        textureKey = re.sub('texture_?key=', '', argl)
    elif arg != 'makejson.py':
        name = arg

if name == '': # name must be provided in command line!
    print('No block/item name specified!')
    exit(1)
if texture == '': # if no texture specified, use file name
    texture = name

# create the output directories...
createAllDirs()

filename = name
textureName = texture

# write the file(s)!
if isBlock:
    writeBlockJSON(filename, textureName, textureKey)
else:
    writeItemJSON(filename, textureName, layer, type)
