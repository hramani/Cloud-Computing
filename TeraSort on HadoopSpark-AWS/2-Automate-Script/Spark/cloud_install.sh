#!/bin/bash
clear
echo -e "*** Cloud setup ***\n"

echo -e "Downloading setup files...\n"

echo -e "1. JAVA Jdk\n"
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u73-b02/jdk-8u73-linux-x64.tar.gz
echo -e "Done !\n"

echo -e "2. Hadoop\n"
wget http://mirror.reverse.net/pub/apache/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz
echo -e "Done !\n"

echo -e "3. Spark\n"
wget http://d3kbcqa49mib13.cloudfront.net/spark-1.6.0-bin-hadoop2.6.tgz
echo -e "Done !\n"

echo -e "4. Scala\n"
wget http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz
echo -e "Done !\n"

echo -e "5. Gensort\n"
wget http://www.ordinal.com/try.cgi/gensort-linux-1.5.tar.gz
echo -e "Done !\n"

echo -e "Downloads complete...\n"

echo -e "Installing Java... \n"
sudo tar -xvzf jdk-8u73-linux-x64.tar.gz
ln -s jdk1.8.0_73 jdk

echo -e "Installing Hadoop... \n"
sudo tar -xvzf hadoop-2.7.2.tar.gz
ln -s hadoop-2.7.2 hadoop

echo -e "Insatlling Spark... \n"
sudo tar -xvzf spark-1.6.0-bin-hadoop2.6.tgz
ln -s spark-1.6.0-bin-hadoop2.6 spark

echo -e "Insatlling Scala... \n"
sudo tar -xvzf scala-2.11.8.tgz
ln -s scala-2.11.8 scala 

echo -e "Extracting Gensort... \n"
sudo tar -xvzf gensort-linux-1.5.tar.gz

mkdir Gensort
mv ~/64 ~/32 ~/
sudo chown -R ubuntu ~/*
sudo chgrp -R ubuntu ~/*


bashName="~/.bashrc"
sudo chmod 777 "$bashName"

echo 'export PATH=~/hadoop/bin:~/hadoop/sbin:~/jdk/bin:~/scala/bin:~/spark/bin:$PATH' >> ~/.bashrc 
echo 'export HADOOP_HOME=~/hadoop' >> ~/.bashrc
echo 'export JAVA_HOME=~/jdk' >> ~/.bashrc
echo 'export SCALA_HOME=~/scala' >> ~/.bashrc
echo 'export SPARK_HOME=~/spark' >> ~/.bashrc
echo

source ~/.bashrc

java -version
echo
hadoop version
echo
scala -version
echo

echo "Setup complete !"



