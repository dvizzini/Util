#!/bin/bash

# get commit message
message="${1}"

if [ -z "$message" ]; then
  echo "Usage: ./compile.sh git_commit_message"
  exit
fi

# compile sources and tests
if [ -d bin ]
then
  rm -rf bin/*
else
  mkdir bin
fi

javac -d bin -sourcepath src src/com/danielvizzini/Util/*.java 2> build_errors.txt
javac -d bin -sourcepath test -classpath bin:lib/junit-4.10.jar test/com/danielvizzini/util/*.java 2>> build_errors.txt

if [ -s build_errors.txt ]
then
  echo "Errors compiling source files. See build_errors.txt for more details."
  exit
fi

#run tests and check to see if they pass
java -cp bin:lib/junit-4.10.jar org.junit.runner.JUnitCore com.danielvizzini.util.AllTests > test_results.txt

if [ ! -s `sed -n '2p' test_results.txt | grep 'E'` ]
then
  echo "Errors detected. See test_results.txt for details. Stopping script."
  exit
fi

# compile jar file
rm -rf Util.jar bin/*
javac -d bin -sourcepath src src/com/danielvizzini/Util/*.java
jar cf Util.jar -C bin .

# generate the javadocs
rm -rf doc/*
javadoc -d doc/ -quiet src/com/danielvizzini/util/* 2> javadoc_errors.txt

if [ -s javadoc_errors.txt ]
then
  echo "Exiting becuase javadocs have warnings. See javadoc_errors.txt for more details."
  exit
fi

# do familiar git routines
git add .
git commit -am "$message"
git push
