#!/bin/bash

# get commit message
message="${1}"

if [ -z "$message" ]; then
  echo "Usage: ./push.sh git_commit_message"
  exit 1
fi

echo "compiling jar file..."
rm -rf Util.jar bin/*
javac -d bin -sourcepath src src/com/danielvizzini/Util/*.java
jar cf Util.jar -C bin .

echo "generate the javadocs..."
rm -rf doc/*
javadoc -d doc/ -quiet src/com/danielvizzini/util/* 2> javadoc_warnings.txt

if [ -s javadoc_warnings.txt ]
then
  echo "Javadocs have warnings. See javadoc_warnings.txt for more details. Exiting with status 1."
  exit 1
fi

# do familiar git routines
git add .
git commit -am "$message"
echo "Changes commited. Exiting with status 0."
exit 0
