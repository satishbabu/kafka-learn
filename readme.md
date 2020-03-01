# Kafka on Windows and WSL


##Setup:
### WSL
* Downloaded apache kafka_2.13-2.4.0 from kafka website and extracted it
* Created a directory to store data
   	mkdir data/zookeeper
* Updated zookper property dataDir 
   	`vi config/zookeeper.properties`
    `dataDir=/mnt/c/projects/kafka/kafka_2.13-2.4.0/data/zookeeper`
* Updated kafka property log.dirs
    `vi config/server.properties`
    `log.dirs=/mnt/c/projects/kafka/kafka_2.13-2.4.0/data/kafka`
* Start zookeeper and then kafka in separate terminal window 
    `zookeeper-server-start.sh config/zookeeper.properties`
    `kafka-server-start.sh config/server.properties`
* Had 2 issues
   1. Couldn't connect the Java running in Windows host to kafka running in WSL. Firewall entry didn't help.
   2. The data files gets corrupted. All data files has to be cleaned up and servers need to be restarted

### Windows
* Same as above but using the windows batch programs under bin\windows directory
* Running from windows solves the first problem (connecting from java program to kafka).  Haven't stopped my server yet to check issue 2.

### Other setup
* Create Topic for testing
`kafka-topics --zookeeper localhost:2181 --topic topic1 --create --partitions 3 --replication-factor 1`
`kafka-topics --zookeeper localhost:2181 --list`
`kafka-topics --zookeeper localhost:2181 topic1 --describe`

`kafka-topics.sh --zookeeper localhost:2181 --list`
`kafka-topics --zookeeper localhost:2181 first_topic --describe`     
 




