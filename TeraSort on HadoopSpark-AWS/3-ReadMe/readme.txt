*** Files Description ***
1. PA2_Report (Performance Evaluation) 
2. Hadoop_Config_Files (Hadoop configuration files)
3. SourceCode
4. PA2_Report: Performance evaluation report.
5. Scripts
6. Jars


*** Execution of Shared Memory, Hadoop and Spark Applications ***


1. Shared Memory:
	Sorce Code Location: folder 1-SouceCode/SharedMemory.java
	 Download the Gensort from the link (http://www.ordinal.com/try.cgi/gensort-linux-1.5.tar.gz) and Extract it

			$ tar -xvzf hadoop-2.7.2.tar.gz

	 Run the following command to generate dataset
			$ ./gensort -a <file_size> <file_name>

		e.g. For 1GB and 100bytes Record, we need (1*1024*1024*1024)/100 --> 10737418.2
			$ ./gensort -a 10737418 input.txt

	 Create empty 'input' and 'output' directories before hand
	make sure the input filename is "input.txt" and is in the same location
	 run the make file, which will compile and will run the java file
	 It will prompt you for Number of threads, you can add 2, 4, 8, 16

	 After successful completion, the output file will be generated inside the output directory and the merge output will be on the same 		directory.
	
	Run : make 
	detailed explaination is in the report
	Thread counts is hard coded default value is 2, please open the ShareMemory.java file and change nTh to 2,4,8,16 and then run make again 		for different scenarios
	Chunk sizes is calculated based on the type of instances and memory available, so we have to manually change size calculation function 		in the code to make the code run best. Default values for this will work but may not show expected values.


Hadoop:
	
	Login to all node. Using scp tools copy the following scripts on the root location.
			cloud_1.sh

	Run the shell file and type in Y or Yes as and when prompted
			$ sh cloud1.sh
	
	folder name 6-Hadoop_config_Files : 
	For Multinode Cluster, copy the following configuration files	in /hadoop/etc/hadoop
	(For Detailed information please refer report)
		a. core-site.xml
		b. hdfs-site.xml
		c. mapred-site.xml
		d. yarn-site.xml
		e. masters
		f. slaves

	Using FileZilla or scp tools copy the Hadooop Terasort application jar files on to the EBS mounted volume.
	Check if all the nodes are running using following command
		$ jps

	Generate the input file using Gensort

	Copy the input data file to HDFS using the following command.
			$ hdfs dfs -put <input_file_location> /input

	Run the application using the following command.
			$ hadoop jar hterasort.jar <input_file_name> /output

	Copy the output file from HDFS to local=.
			$ hdfs dfs -get /ouput <ouput_file_location>


	File location for hadoop java code :folder 1-SouceCode/hadoopTeraSort.java
	



Spark (single node):
	1. Login to your AWS account and launch i3.large or i3.4xlarge with larger EBS storage so as to generate 1 TB and 128 GB data on the 		instance.
	2. Please see report for hadoop and spark installation processes in detail.
	3. When the installation is done , generate tthe desired data using Gensort
	4. run the spark using the spark-shell
	5.edit the scala code provide the input file location on the hdfs or EBS
	6. load the scala script to run the scala code usng :load scode.scala
	7. calculate the time of computation with respect to each instance
	
	File location for scode.scala code :folder 1-SouceCode/scode.scala
	run spark-shell
	:load scode.scala 
	[node: change the file path related to your file path or hdfs path in the scoe.scala before you run the the spark-shell

Spark (Cluster mode):
	1. see the steps written in Report for bettwe understanding.
	2. Download spack-ec2 file from the apache website provided github file
	3. go through the instruction and create a cluster using ./spark-ec2 -k spark -i spark.pem -t i3.large -s 7 launch SparkName
	4. this will take some time, after the creationg of the cluster ssh in master and do required configurations related to the hadoop and 		spark install and down load few dependencies.
	5. Copies the changes into the slave using copy scrpt available in the master node.
	6. See report for hadoop and spark installation processes.
	7.install and update everything as done in Hadoop section above
	8. When the installation is done , generate tthe desired data using Gensort
	9. run the spark using the spark-shell
	10.edit the scala code provide the input file location on the hdfs or EBS
	11. load the scala script to run the scala code usng :load scode.scala
	12. calculate the time of computation with respect to each instance

	File location for scode.scala code :folder 1-SouceCode/scode.scala
	run spark-shell
	:load scode.scala 
	[node: change the file path related to your file path or hdfs path in the scoe.scala before you run the the spark-shell
	
