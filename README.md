-----------------------------
|   INSTALL DEPENDENCIES
-----------------------------

sudo apt-get -y install default-jdk maven zookeeper zookeeperd redis-server python-software-properties python-pip python tree 

sudo pip install flask redis

-----------------------------
|   	PACKAGING
-----------------------------

FROM THE ROOT FOLDER :

cd FluProject/

mvn clean

mvn package

-----------------------------
|     RUN THE TOPOLOGY
-----------------------------

FROM THE ROOT FOLDER :

cd FluProject/

RUN THE GENERATED JAR THAT SHOULD BE LOCATED IN THE TARGET FOLDER :
(type tree if you don't find it)

storm jar target/twitter-flu-uk-storm-0.0.1-SNAPSHOT-jar-with-dependencies.jar twitter.storm.FluDetectorTopology

----------------------------------------------------------------
|     RUN THE PYTHON SCRIPT TO RECEIVE MESSAGES FROM REDIS
----------------------------------------------------------------

FROM THE ROOT FOLDER :

cd viz_uk/

python app.py

----------------------------------------------------------------
|      VISUALIZE THE STREAM FROM REDIS 
----------------------------------------------------------------

TYPE IN BROWSER :

localhost:5000/stream

----------------------------------------------------------------
|      VISUALIZE THE INTERACTIVE MAP WITH LIVE UPDATE 
----------------------------------------------------------------

WHILE THE PYTHON SCRIPT IS RUNNING AND THE TOPOLOGY IS RUNNING TOO ,
TYPE IN BROWSER :

localhost:5000/map

