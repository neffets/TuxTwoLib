#!/bin/bash

if [ -z "$1" ];
then
    echo "Usage: $0 Versionnumber ApiVersionnumber"
    echo "       ./mver 1.14.4 v1_14_R1"
    exit 1
fi

VERSION="$1"
APIVERSION=$(grep ${VERSION} version.txt | head -1 | cut -d" " -f1)
if [ "x$2" != "x" ];
then
    APIVERSION="$2"
fi
echo "Update mc $VERSION to cb $APIVERSION"

find src -name "*.java" |xargs -n1 perl -pi -e "s#(net.minecraft.server|org.bukkit.craftbukkit)\\.v\\d_\\d\\d_R\\d+#\\1.${APIVERSION}#g"

perl -pi -e "s#^version: .*#version: ${VERSION}#" src/plugin.yml
perl -pi -e "s#currentMCversion = \"\\d.[\\d+\\.]*\"#currentMCversion = \"${VERSION}\"#;" src/Tux2/TuxTwoLib/TuxTwoLib.java
perl -pi -e "s#currentNMS = \"v\\d_\\d+_R\\d+\"#currentNMS = \"${APIVERSION}\"#;" src/Tux2/TuxTwoLib/TuxTwoLib.java
perl -pi -e "s#spigot\\-api\\-\\d+.\\d+\.\\d+\\-#spigot-api-${VERSION}-#;" .classpath

