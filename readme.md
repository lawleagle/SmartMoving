# Smart Moving

This is as an unofficial fork of Divisor's [Smart Moving](https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1274224-smart-moving) mod, based on version 15.6 for Minecraft 1.7.10. This fork's goal is to fix bugs and improve [compatibility](https://github.com/makamys/SmartMoving/wiki/Compatibility-List) with other mods.

A fork of [PlayerAPI](https://github.com/makamys/PlayerAPI) is available which further improves compatibility.

## Description

The Smart moving mod provides various additional moving possibilities:

* Climbing only via gaps in the walls
* Climbing ladders with different speeds depending on ladder coverage and/or neighbour blocks
* Alternative animations for flying and falling
* Climbing along ceilings and up vines
* Jumping up & back while climbing
* Configurable sneaking
* Alternative swimming
* Alternative diving
* Alternative flying
* Faster sprinting
* Side & Back jumps
* Charged jumps
* Wall jumping
* Head jumps
* Crawling
* Sliding

Exact behavior depends on configuration (see chapter 'Configuration' below)



## Required Mods

This mod requires

* [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.7.10.html) and
* [PlayerAPI](https://github.com/makamys/PlayerAPI) core 1.3 or higher
* [SmartRender](https://github.com/makamys/SmartRender) 2.1 or higher

to be installed too. Additionally

* [RenderPlayerAPI](https://github.com/makamys/RenderPlayerAPI) core 1.0 or higher

can be installed to improve the compatibiltity with other mods.



## Configuration

The file `smart_moving_options.txt` can be used to configure the behavior this mod.
It is located in your `.minecraft` folder next to Minecraft's `options.txt`.
If does not exist at Minecraft startup time it is automatically generated.

You can use its content to manipulate this mod's various features.
