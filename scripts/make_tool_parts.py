import re
from subprocess import call

NAME = 'sword'
SUPER_TOOL = False

TEXTURE = NAME + '/' + NAME
MODE = 'item'

line = 'python makejson.py %s %s texture=%s layer=%d type=handheld'
commands = [];

# Rod
for rod in ['wood', 'bone', 'iron', 'gold', 'silver']:
    name = NAME + '_rod_' + rod
    if SUPER_TOOL and (rod == 'wood' or rod == 'bone'):
        texture = 'blank'
    else:
        texture = TEXTURE + '_rod_' + rod
    commands.append(line % (MODE, name, texture, 0))

for i in (range(48) + ['flint']):
    name = NAME + str(i)
    texture_id = ''
    texture_id = str(i)

    texture = TEXTURE + texture_id

    commands.append(line % (MODE, name, texture, 1))

for tip in ['iron', 'diamond', 'emerald', 'gold']:
    name = NAME + 'tip' + tip
    texture = TEXTURE + 'tip' + tip
    commands.append(line % (MODE, name, texture, 3))

for command in commands:
        call(command, shell=True)
