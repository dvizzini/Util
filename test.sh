#!/bin/bash

echo "compiling tests..."
if [ -d bin ]
then
  rm -rf bin/*
else
  mkdir bin
fi

javac -d bin -sourcepath src src/com/danielvizzini/util/*.java 2> build_errors.txt
javac -d bin -sourcepath test -classpath bin:lib/junit-4.10.jar test/com/danielvizzini/util/*.java 2>> build_errors.txt

if [ `echo $?` != 0 ]
then
  echo "Errors compiling source files. Printing build_errors.txt for more details. Exiting with status 1"
  cat build_errors.txt
  exit 1
fi

echo "running tests and checking to see if they pass..."
java -cp bin:lib/junit-4.10.jar org.junit.runner.JUnitCore com.danielvizzini.util.AllTests > test_results.txt

if [ `echo $?` != 0 ]
then
  echo "Errors detected. Printing test_results.txt for details. Exiting with status 1."
  cat test_results.txt
  exit 1
fi
echo "Test passed. Exiting with status 0."
exit 0
