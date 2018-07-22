import re
from subprocess import call

GEMS = ['ruby', 'garnet', 'topaz', 'amber', 'heliodor', 'peridot', 'beryl', 'indicolite',
    'aquamarine', 'sapphire', 'iolite', 'amethyst', 'agate', 'morganite', 'onyx', 'opal',
    'carnelian', 'spinel', 'citrine', 'jasper', 'golden_beryl', 'moldavite', 'malachite',
    'turquoise', 'moonstone', 'blue_topaz', 'tanzanite', 'violet_sapphire', 'lepidolite',
    'ametrine', 'black_diamond', 'alexandrite', 'pyrope', 'coral', 'sunstone', 'cats_eye',
    'zircon', 'jade', 'chrysoprase', 'apatite', 'fluorite', 'kyanite', 'sodalite', 'ammolite',
    'kunzite', 'rose_quartz', 'tektite', 'pearl']

MODE = 'item'
TEXTURE_KEY = 'all'

def nameFor(gem):
    return gem + '_shard'

def textureFor(gem):
    return nameFor(gem)

for gem in GEMS:
    line = 'python makejson3.py %s %s texture=%s texture_key=%s' \
        % (MODE, nameFor(gem), textureFor(gem), TEXTURE_KEY)
    print(line)
    call(line, shell=True)
