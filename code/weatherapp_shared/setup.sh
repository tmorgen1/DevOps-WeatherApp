#!/bin/bash

shopt -s expand_aliases

#setup mvn alias
alias mvn="\"$PWD/../maven/bin/mvn\""

#running a maven install
mvn clean install
