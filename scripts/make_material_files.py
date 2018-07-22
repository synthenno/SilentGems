import sys
import re
import os

FILE_TEXT = """{
    "type": "%s",
    "stats": [
        {"name": "durability",      "value": %s},
        {"name": "enchantability",  "value": %s},
        {"name": "harvest_level",   "value": %s},
        {"name": "harvest_speed",   "value": %s},
        {"name": "melee_damage",    "value": %s},
        {"name": "magic_damage",    "value": %s},
        {"name": "attack_speed",    "value": %s},
        {"name": "armor",           "value": %s},
        {"name": "armor_toughness", "value": %s},
        {"name": "magic_armor",     "value": %s},
        {"name": "ranged_damage",   "value": %s},
        {"name": "ranged_speed",    "value": %s},
        {"name": "rarity",          "value": %s}
    ],
    "crafting_items": {
        "normal": {
            "item": "silentgems:%s"
        },
        "small": {
            "item": "silentgear:%s_shard"
        }
    },
    "display": {
        "hidden": false,
        "texture_suffix": "%s",
        "texture_color": "%s",
        "broken_color": "%s"
    },
    "availability": {
        "enabled": true,
        "tier": %s,
        "tool_blacklist": []
    }
}"""

SPREADSHEET_PATH = "materials/spreadsheet.csv"

with open(SPREADSHEET_PATH) as f:
    lines = f.readlines()
    lines = [x.strip() for x in lines]

    for line in lines:
        col = line.split(',')
        gem = col[15]
        text = FILE_TEXT % ('main', col[3], col[4], col[5], col[6], col[7], col[8], col[9], col[10],
                            col[11], col[12], col[13], col[14], col[2], gem, gem, gem, col[16],
                            col[17], col[1])

        filename = "materials/main_" + gem + ".json"
        print(filename)
        json_file = open(filename, 'w')
        json_file.write(text)
        json_file.write('\n')
        json_file.close()
