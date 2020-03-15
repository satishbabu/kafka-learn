# Kafka on Windows and WSL

# Few simple programs that send and receive messages from kafka

## Setup:
* The WSL setup was not working well and it was failing often.  So switched to running in windows directly
* Downloaded apache kafka_2.13-2.4.0 from kafka website and extracted it
* Created a directory to store data
   	mkdir data/zookeeper
* Updated zookper property dataDir  (config/zookeeper.properties) 
~~~~
dataDir=C:\projects\kafka\kafka_2.13-2.4.0\data\zookeeper
~~~~

* Updated kafka property log.dirs (config/server.properties)
~~~~
log.dirs=/mnt/c/projects/kafka/kafka_2.13-2.4.0/data/kafka
~~~~

## Start Zookeeper and Kafka
* Open a powershell and start Zooker
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\
zookeeper-server-start config/zookeeper.properties
~~~~

* Open another powershell and start kafka
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\
kafka-server-start config/server.properties
~~~~

## Create topic 
* Open third powershell and create topic called topic1.  This topic is used in the program
~~~~
kafka-topics --zookeeper localhost:2181 --topic topic1 --create --partitions 3 --replication-factor 1
kafka-topics --zookeeper localhost:2181 --list
kafka-topics --zookeeper localhost:2181 topic1 --describe
~~~~

## Run it 
* Then go to powershell and run following to send few messages which uses provided console program
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\bin\windows
kafka-console-producer --broker-list localhost:9092 --topic topic1
~~~~

* Then go to powershell and run following to listen to messages which uses provided console program
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\bin\windows
??
~~~~

### Run programs from IntelliJ
* SimpleProducer - sends a messages
* SimpleConsumer - consumes all messages one by one
* ReplayMessages - replays 5 messages
* SimpleProducerCallBack - Demonstrates a callback function to be run after a message is sent successfully
* ProducerLoadGenerator - Sends 1M messages and prints out stats every 1000 messages and also at the end

## Clean up
* Things break once in a while so clean up everything.  Stop kafka and zookeeper.  Remove the kafka and zookeper files (**data is lost**) and recreate topic
* Open a powershell
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\
Remove-Item -Path .\data\kafka\ -Recurse
Remove-Item -Path .\data\zookeeper\ -Recurse
kafka-topics --zookeeper localhost:2181 --topic topic1 --create --partitions 3 --replication-factor 1
~~~~



### WSL
* Kafka files got corrupted often and it usually happened when I put laptop in sleep mode without shutting it down. So, I abandoned using it in WSL.
* Downloaded apache kafka_2.13-2.4.0 from kafka website and extracted it
* Created a directory to store data
   	mkdir data/zookeeper
* Updated zookper property dataDir 
~~~~
vi config/zookeeper.properties
dataDir=/mnt/c/projects/kafka/kafka_2.13-2.4.0/data/zookeeper
~~~~

* Updated kafka property log.dirs
~~~~
vi config/server.properties
log.dirs=/mnt/c/projects/kafka/kafka_2.13-2.4.0/data/kafka
~~~~

* Start zookeeper and then kafka in separate terminal window 
~~~~
zookeeper-server-start.sh config/zookeeper.properties
kafka-server-start.sh config/server.properties
~~~~
* Had 2 issues
   1. Couldn't connect the Java running in Windows host to kafka running in WSL. Firewall entry didn't help.
   1. The data files gets corrupted. All data files has to be cleaned up and servers need to be restarted

### Windows
* Same as above but using the windows batch programs under bin\windows directory
* Running from windows solves the first problem (connecting from java program to kafka).  Haven't stopped my server yet to check issue 2.


#### Start
* One powershell
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\
zookeeper-server-start config/zookeeper.properties
~~~~

* Another powershell
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\
kafka-server-start config/server.properties
~~~~

* Things break once in a while so clean up everything.  Stop kafka and zookeeper.  Remove the kafka and zookeper files (**data is lost**) and recreate topic
* Open a powershell
~~~~
cd C:\projects\kafka\kafka_2.13-2.4.0\
Remove-Item -Path .\data\kafka\ -Recurse
Remove-Item -Path .\data\zookeeper\ -Recurse
kafka-topics --zookeeper localhost:2181 --topic topic1 --create --partitions 3 --replication-factor 1
~~~~

### Other commands
~~~~
kafka-topics --zookeeper localhost:2181 --topic topic1 --create --partitions 3 --replication-factor 1
kafka-topics --zookeeper localhost:2181 --list
kafka-topics --zookeeper localhost:2181 topic1 --describe
~~~~
