
original repo by @Tux2 : https://github.com/Tux2/TuxTwoLib

#  TuxTwoLib

This library does nothing on it's own and is re-compiled every minecraft version to provide a stable API for several of my plugins of some craftbukkit internals.


## Which Download do I Need?

All the downloads are versioned with the craftbukkit/spigot version they are for, followed by an optional revision number, and then build number. So if you are on Craftbukkit/Spigot for Minecraft server version 1.12, you would look for a version like this: 1.12-b1, with the b1 being the build number. (Always get the version with the highest build number for your version of minecraft) If an incompatibility exists in the builds, it also has a number corresponding to the build, like this: 1.12-R0.2-b1 would be compatible for all of 1.12-R0.2 builds and up for that version of minecraft.


## Features

* Self aware auto updater will only update the plugin if there is an update available for your minecraft version.
* Set it to auto update only on Minecraft version changing, whenever there is an update available, or not at all.
* Will warn you upon login and in console when your version of Minecraft is incompatible with the version installed.
* Will notify you when new updates are installed.
* Provides a method for getting offline player data


## Permissions

* **tuxtwolib.notices** - User can recieve notices about new updates and incompatible Minecraft version errors.


## Purpose

### For Developers

A recent "commit":https://github.com/Bukkit/CraftBukkit/commit/8f12382e8efc8c39a919af9180dd884caf3720ff to CraftBukkit now requires any plugins that accesses native "net.minecraft.server" or "org.bukkit.craftbukkit" classes to modify its imports for every new Minecraft version update.
This library abstracts these native classes so that whenever Minecraft updates, only this library needs to be updated. Plugins which use this library can potentially be coded to be compatible with multiple versions of Minecraft.

### For Server Admins

If you have multiple plugins that utilize native Minecraft code, you will now have to update each one of them for each update.
Fortunately, if you use any plugins which utilize this library, you only need to update one plugin for them. Just grab the version of this library made for your Minecraft version and you're done!


## How to Use:

### For Server Admins

1. Download the correct version of this plugin for your Minecraft version. (follow the instructions above)
    * You can find the different versions of this plugin by clicking the "Files" tab above
    * Make sure you remember where you downloaded the file to!
1. Select the file you just downloaded and "copy" it to your "/plugins" folder.
    * On Windows, you can use ctrl+c or right click then press "copy". Then use ctrl+v or right-click to paste it in the proper folder.
    * If you are using the terminal, you should know how to use the "cp" command or Midnight Commander.
    * If you need to use FTP, be sure to read the documentation. (connect to the correct IP, traverse directories, etc.)
1. Run your server as you usually would.
    * Typically this involves a run script or an admin panel. (this part is very important)

### For Developers

1. Check through this library's JavaDocs to see if we have the hooks you require.
    * Because this library is new, it likely does not yet have the hooks you need.
    * If we do not have them, create a Support Ticket for an enhancement. We can add it in for you!
1. If we do, just examine the JavaDocs and hook into it with your plugin.
    * Do not forget to add "depend: [TuxTwoLib]" to your plugin.yml! (This makes sure that the library is loaded before your plugin)
1. On your plugin's description make sure you tell users that they need to have this library installed on their server!

### Building Plugin

I recommend using eclipse.
* build the spigot api with "bash build.sh 1.14.4"
* add external jar to your project (from spigot-build/Spigot/Spigot-API/target the "shaded.jar")
* use "json-simple-1.1.1.jar" from https://code.google.com/archive/p/json-simple/
* "Build Project" in eclipse with ant-build (using the ./build.xml)

'''ATTENTION:''' if you are using the docker-image "dlord/spigot" you have to clarify for building: java compatibilty with java 1.8 !!


## Links

JavaDocs: http://javadocs.yu8.me/tuxtwolib/

Thread on how to use the book API: http://forums.bukkit.org/threads/simple-temp-book-api.93562/
A lot of classes taken from: http://forums.bukkit.org/threads/lib-prettyscarylib.110164/

FAQ
Q. I'm getting this in my console: INFO java.lang.NoClassDefFoundError
A. You downloaded the wrong version of the library, or a version that's compatible with your version of minecraft isn't released yet. Please double check and make sure you got the right download.

