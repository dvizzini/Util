#!/bin/bash

echo "compiling tests..."
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
  echo "Errors compiling source files. See build_errors.txt for more details. Exiting with status 1"
  cat build_errors.txt
  if [ -f bin ]
  then
    echo "bin file exists"
    if [ -s bin/com/danielvizzini/DateUtil.class]
    then
      echo "DateUtil.class exists"
    else
      echo "DateUtil.class dne"
    fi
  else
    echo "bin file dne"
  fi
  exit 1
fi

echo "running tests and checking to see if they pass..."
java -cp bin:lib/junit-4.10.jar org.junit.runner.JUnitCore com.danielvizzini.util.AllTests > test_results.txt

if [ ! -s `sed -n '2p' test_results.txt | grep 'E'` ]
then
  echo "Errors detected. See test_results.txt for details. Exiting with status 1."
  exit 1
fi
echo "Test passed. Exiting with status 0."
exit 0
