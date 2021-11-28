#!/bin/bash

shopt -s expand_aliases

#change directory to the code top level directory
cd code

#alias manual - run 'alias --help'
#PWD is the dynamic environment variable storing the current working directory
alias mvn="\"$PWD/maven/bin/mvn\""

if [[ $1 == "setup" ]]
then
	#SETUP Maven
	echo "Setting up Maven"
	if [ -d "maven" ] 
	then
		#rm manual - run 'rm --help'
		rm -r maven/
	fi
	#curl manual - run 'curl --help'
	curl -o maven.zip https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.zip
	#unzip manual - run 'unzip --help'
	unzip maven.zip
	#remove maven.zip because its been extracted
	rm maven.zip
	#mv manual - run 'mv --help'
	mv apache-maven-* maven
	
	#SETUP Projects
	for project in *
	do
		if [ -f $project"/setup.sh" ] 
		then 
			echo "Setting up "$project
			cd $project
			bash "setup.sh"
			cd ..
		fi
	done
#Compile projects
elif [[ $1 == "compile" ]]
then
	for project in *
	do
		if [ -f $project"/pom.xml" ] 
		then 
			echo "Compiling "$project
			cd $project
			mvn clean compile
			cd ..
		fi
	done
#Test projects
elif [[ $1 == "test" ]]
then
	for project in *
	do
		if [ -f $project"/pom.xml" ] 
		then 
			echo "Testing "$project
			cd $project
			mvn clean test
			cd ..
		fi
	done
#Run System
elif [[ $1 == "run" ]]
then
	# Launch server
	echo "Launching Weather App Server"
	cd "weatherapp_service"
	mvn clean package -q
	java -jar target/weatherapp_service-1.0-SNAPSHOT.jar > serverlog.txt &
	cd ..
	# Wait 3 seconds to ensure server has launched
	sleep 3
	# Launch client application
	echo "Launching Weather App GUI"
	cd "weatherapp_gui"
	mvn javafx:run -q
	cd ..
#Terminate Server
elif [[ $1 == "kill" ]]
then
	echo "Terminating Java Server Process"
	# Process List (ps) is piped to grep, which searches for a line
	# containing the 'java'.  Awk then grabs the first column of the row,
	# which is the pid.
	if kill $(ps | grep 'java' | awk '{print $1}')
	then
		echo "Terminated"
	# For when the server is not running, or if there is some unexpected behavior.
	else
		echo "Error Terminating Server"
	fi
else
	echo "Expected Usage: config.sh [command]"
	echo "Possible commands:"
	echo "  setup       will setup the system including all project "
	echo "               specific setup defined in a project's setup.sh"
	echo "  compile     will compile all projects"
	echo "  test        will test all projects"
	echo "  run         will launch the client system and locally launch"
	echo "               any necessary services"
	echo "  kill        will terminate the server process"
fi
