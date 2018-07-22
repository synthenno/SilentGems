import sys
import re
import os

GEMS = ['ruby', 'garnet', 'topaz', 'amber', 'heliodor', 'peridot', 'beryl', 'indicolite',
    'aquamarine', 'sapphire', 'iolite', 'amethyst', 'agate', 'morganite', 'onyx', 'opal',
    'carnelian', 'spinel', 'citrine', 'jasper', 'golden_beryl', 'moldavite', 'malachite',
    'turquoise', 'moonstone', 'blue_topaz', 'tanzanite', 'violet_sapphire', 'lepidolite',
    'ametrine', 'black_diamond', 'alexandrite', 'pyrope', 'coral', 'sunstone', 'cats_eye',
    'zircon', 'jade', 'chrysoprase', 'apatite', 'fluorite', 'kyanite', 'sodalite', 'ammolite',
    'kunzite', 'rose_quartz', 'tektite', 'pearl']

def getNewTextureName(gem):
    return gem + '_bricks'

def getGemIndex(filename):
    numbers = re.findall(r'\d+', filename)
    if not len(numbers) == 1: return -1;
    num = int(numbers[0])
    
    matchesDark = re.compile('.*dark\d+').match(filename)
    matchesLight = re.compile('.*light\d+').match(filename)
    
    if matchesDark:
        return num + 16
    elif matchesLight:
        return num + 32
    return num

PATH = 'textures'
pngRegex = re.compile('[^\.]+\.png$')
for filename in os.listdir(PATH):
    gemIndex = getGemIndex(filename)
    if gemIndex >= 0 and pngRegex.match(filename):
        newFilename = getNewTextureName(GEMS[gemIndex]) + '.png'
        print '%s -> %s' % (filename, newFilename)
        os.rename(PATH + '/' + filename, PATH + '/' + newFilename)
