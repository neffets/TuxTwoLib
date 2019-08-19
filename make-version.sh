#!/bin/bash

if [ -z "$1" ];
then
    echo "Usage: $0 Versionnumber"
    echo "       ./mver 1.13.2"
    exit 1
fi

VERSION="$1"
_VERSION="v"$( echo $VERSION | perl -p -e 's#\.#_#; s#\.#_R#;' )
echo "Update mc $VERSION to cb $_VERSION"

find src -name "*.java" |xargs -n1 perl -pi -e "s#(net.minecraft.server|org.bukkit.craftbukkit)\\.v\\d_\\d\\d_R\\d+#\\1.${_VERSION}#g"

perl -pi -e "s#^version: .*#version: ${VERSION}#" src/plugin.yml
perl -pi -e "s#currentMCversion = \"\\d.\\d+\"#currentMCversion = \"${VERSION}\"#;" src/Tux2/TuxTwoLib/TuxTwoLib.java
perl -pi -e "s#currentNMS = \"v\\d_\\d+_R\\d+\"#currentNMS = \"${_VERSION}\"#;" src/Tux2/TuxTwoLib/TuxTwoLib.java

