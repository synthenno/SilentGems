import re
from subprocess import call

NAME = 'tomahawk'

TEXTURE = NAME + '/' + NAME
MODE = 'item'

line = 'python makejson.py %s %s texture=%s layer=%d type=handheld'
commands = [];

# Rod
for rod in ['wood', 'bone', 'iron', 'gold', 'silver']:
    name = NAME + '_rod_' + rod
    texture = TEXTURE + '_rod_' + rod
    commands.append(line % (MODE, name, texture, 0))

for i in (range(48) + ['flint']):
    name = NAME + str(i)
    texture_id = ''
    texture_id = str(i)

    texture = TEXTURE + texture_id

    commands.append(line % (MODE, name, texture, 1))

for tip in ['iron', 'diamond', 'emerald', 'gold']:
    name = NAME + '_tip_' + tip
    texture = TEXTURE + '_tip_' + tip
    commands.append(line % (MODE, name, texture, 3))

for command in commands:
        call(command, shell=True)
