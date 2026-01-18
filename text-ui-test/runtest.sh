#!/usr/bin/env bash

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]
then
    mkdir bin
fi

# Delete output from previous run
if [ -e "text-ui-test/ACTUAL.TXT" ]
then
    rm text-ui-test/ACTUAL.TXT
fi

# Compile the code into the bin folder
# Note: We point to src/main/java and include the exception subfolder
if ! javac -cp src/main/java -Xlint:none -d bin src/main/java/*.java src/main/java/exception/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# Run the program, feed commands from input.txt and redirect to ACTUAL.TXT
# Changed 'Duke' to 'Dicky' to match your class name
java -classpath bin Dicky < text-ui-test/input.txt > text-ui-test/ACTUAL.TXT

# Compare the output to the expected output
# We use -w to ignore trailing whitespace which often causes false failures
diff text-ui-test/ACTUAL.TXT text-ui-test/EXPECTED.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi