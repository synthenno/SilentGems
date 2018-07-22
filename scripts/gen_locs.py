import sys
import re
import os

GEMS = {'ruby': "Ruby",
        'garnet': "Garnet",
        'topaz': "Topaz",
        'amber': "Amber",
        'heliodor': "Heliodor",
        'peridot': "Peridot",
        'beryl': "Beryl",
        'indicolite': "Indicolite",
        'aquamarine': "Aquamarine",
        'sapphire': "Sapphire",
        'iolite': "Iolite",
        'amethyst': "Amethyst",
        'agate': "Agate",
        'morganite': "Morganite",
        'onyx': "Onyx",
        'opal': "Opal",
        'carnelian': "Carnelian",
        'spinel': "Spinel",
        'citrine': "Citrine",
        'jasper': "Jasper",
        'golden_beryl': "Golden Beryl",
        'moldavite': "Moldavite",
        'malachite': "Malachite",
        'turquoise': "Turquoise",
        'moonstone': "Moonstone",
        'blue_topaz': "Blue Topaz",
        'tanzanite': "Tanzanite",
        'violet_sapphire': "Violet Sapphire",
        'lepidolite': "Lepidolite",
        'ametrine': "Ametrine",
        'black_diamond': "Black Diamond",
        'alexandrite': "Alexandrite",
        'pyrope': "Pyrope",
        'coral': "Coral",
        'sunstone': "Sunstone",
        'cats_eye': "Cat's Eye",
        'zircon': "Zircon",
        'jade': "Jade",
        'chrysoprase': "Chrysoprase",
        'apatite': "Apatite",
        'fluorite': "Fluorite",
        'kyanite': "Kyanite",
        'sodalite': "Sodalite",
        'ammolite': "Ammolite",
        'kunzite': "Kunzite",
        'rose_quartz': "Rose Quartz",
        'tektite': "Tektite",
        'pearl': "Pearl"}

#
PREFIX = 'tile'

def getName(gem):
    return gem + '_ore'

def getTranslation(gem):
    return GEMS[gem] + ' Ore'

lines = []

for gem in GEMS:
    lines.append('%s.silentgems.%s.name=%s' % (PREFIX, getName(gem), getTranslation(gem)))

for line in sorted(lines):
    print line