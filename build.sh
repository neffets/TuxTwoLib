#!/bin/bash

if [ -z "$1" ];
then
    echo "Usage: $0 Versionnumber"
    echo "       ./build.sh 1.14.4"
    exit 1
fi

VERSION="$1"
APIVERSION=$(grep ${VERSION} version.txt | head -1 | cut -d" " -f1)
echo "Update mc $VERSION to cb $APIVERSION"
bash make-version.sh "$VERSION" "$APIVERSION"

(
mkdir -p ../spigot-build
cd ../spigot-build
# https://hub.spigotmc.org/jenkins/job/BuildTools
wget -O BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
docker run -it -v "$PWD":/usr/src/spigot-build -w /usr/src/spigot-build openjdk:8 java -jar BuildTools.jar --rev "$VERSION"
)

