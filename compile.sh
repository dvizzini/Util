#!/bin/bash
export DEBIAN_FRONTEND=noninteractive

# get commit message
message="${1}"

if [ -z "$message" ]; then
  echo "Usage: ./compile.sh git_commit_message"
  exit
fi

#compile jar file
rm Util.jar
rm bin/com/danielvizzini/util/*Test.class
jar cf Util.jar -C bin .

#generate the javadocs
rm -rf doc/*
javadoc -quiet -sourcepath src/com/danielvizzini/util/* -d doc/ 2> javadoc_errors.txt

if [ -s javadoc_errors.txt ]
then
  echo "Exiting becuase javadocs have warnings. See javadoc_errors.txt for more details."
  exit
fi

#do familiar git routines
git add .
git commit -am "$message"
git push
