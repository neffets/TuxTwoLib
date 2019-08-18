#!/bin/bash

if [ -z "$1" ];
then
    echo "Usage: $0 Versionnumber"
    echo "       ./mver 1.12.2-b8"
    exit 1
fi

VERSION="$1"
_VERSION="v"$( echo $VERSION | perl -p -e 's#\.#_#; s#\.#_R#;' )
echo "Update mc $VERSION to cb $_VERSION"

(
mkdir -p ../spigot-build
cd ../spigot-build
# https://hub.spigotmc.org/jenkins/job/BuildTools
wget -O BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -jar BuildTools.jar "$VERSION"
)


