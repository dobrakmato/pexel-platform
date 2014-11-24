Pexel Platform [![Build Status](https://travis-ci.org/dobrakmato/pexel-platform.svg?branch=master)](https://travis-ci.org/dobrakmato/pexel-platform)
==============
[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/dobrakmato/pexel-platform?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
Platform for sweet deployment & managment of minecraft minigame networks.
----------------------------

![http://i.imgur.com/NQ3lwbB.png](http://i.imgur.com/NQ3lwbB.png)

Pexel Platform is architecture for developing minigames and software for easy and sweet managment of servers in minigame network. 

**NOTE: Project is currently in active development phase.**

Deployment of new game (slave) server is thing of 10 minutes.

- Download supported minecraft server software. *(30 seconds)*
- Download slave plugin. *(30 seconds)*
- Enter master server ip, slave server name and access key. *(30 seconds)*
- Start minecraft server. *(30 seconds)*
- In web administration of master server add minigames and maps to new slave server. *(1 minute)*
- Files will be transfered in some seconds/minutes (depending of size) automatically. *(2 - 6+ minutes)*
- Slave server will be restarted and configured after file transfer. *(1 minute)*
- Slave server is ready. Players can now join games hosted on this slave server.

Master
----------
**Master** part runs as Bungee plugin (*Bungee must be patched with packed patcher*) or as standalone application. It also opens REST API server for remote administration of minigame network. You can also use webadmin (in dev) for administration. 

Slave
--------
**Slave** can run as Bukkit/Spigot or Sponge plugin (More to come). It comes with many useful utilities for mingiame developers (Teams, Kits, Inventory Menus, BlockRollbacker...) and structure for minigames and arenas.

Minigames can be loaded and registered as bukkit/spigot/sponge plugins or as jar files loaded by slave plugin.

BungeePatcher
--------
**BungeePatcher** does replace original **BugneeSecurityManager** in BungeeCord.jar with custom one. This is done because Bungee's security manager does not allows calling many functions that **Master** parts requires (Scheduling, Threading, Network stuff). 

### Usage
- Just drop *bungeepatcher.jar* to folder containing your *BungeeCord.jar* file and run patcher using command `java -jar bungeepatcher.jar`. 
- Patcher will automatically compile custom SecurityManager and will replace it in jar. (You have to launch patcher each time BungeeCord.jar gets updated).

Useful links
-----------------
Pexel Project is developed by Matej Kormuth and It is open to contributions, just open pull request!

- Utilities: <http://pexel.eu/platform>
- Cloud structure: <https://docs.google.com/drawings/d/1jlMk-iWtGW1szIe3lzYmohyaQfKAAk-zryIe8zczvP4>
