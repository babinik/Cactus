Cactus Obfuscator
=================

### version 0.1

##### Cactus is a java multi-module application dedicated to process and obfuscate JavaScript and Css code.


####Contents:  
####1. Maven plugin 
####2. Console tool
####3. Configuration file

Cactus can be used as a maven plugin inside java projects being managed by maven as well as standalone CLI utility for js/css obfuscation.   

## 1. Maven plugin

If you haven't [maven](http://maven.apache.org/ "apache maven") installed please [download](http://maven.apache.org/download.html) and 
[install](http://maven.apache.org/download.html#Installation) it first.

Having *maven* installed and configured we can go to **cactus-plugin** installation:

1. Fetching source code and installing.

	* clone the cactus repo, open terminal/CLI and run:      
		`git clone git@github.com:nbabinski/Cactus.git`     
		`cd Cactus`     	
	* install:     
		`mvn install` 

2. Using already compiled cactus-plugin.jar.

	* download cactus-plugin.jar
	* the open terminal/CLI and run the following maven command:  
  		`mvn install:install-file -Dfile=DOWNLOAD_FOLDER/cactus-plugin.jar -DgroupId=com.cactus -DartifactId=cactus-plugin -Dpackaging=maven-plugin -Dversion=0.1`    
  	where DOWNLOAD_FOLDER stands for your download folder, where cactus-plugin.jar file was downloaded. 
  
If all is OK and you see:
 
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 0.702s
	[INFO] Finished at: Tue Apr 19 01:36:31 EEST 2011
	[INFO] Final Memory: 2M/48M
	
string *BUILD SUCCESS* means cactus plugin was successfully installed.
  
Congratulations, now you have cactus-plugin installed.

Cactus plugin is executed at *process-sources* life-cycle phase.

Now we can configure your pom.xml file to use cactus plugin:

	<build>
		<plugins>
		...
			<plugin>
				<groupId>com.cactus</groupId>
				<artifactId>cactus-plugin</artifactId>
				<version>0.1</version>
				<configuration>
					<jsBaseDirectory>src/main/webapp/js</jsBaseDirectory>
					<outputDirectory>cache</outputDirectory>					
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>obfuscate</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		...
		</plugins>        
	</build>

Configuration includes:    
*baseDirectory*   -  _(optional)_ Defaults ${project.basedir}    
*jsBaseDirectory* -  _(required)_ Relative path from baseDirectory to the JavaScript sources and where is cactus.xml configuration file.    
*outputDirectory* -  _(optional)_ Relative path from jsBaseDirectory to output directory for obfuscated/processed files. Defaults to the jsBaseDirectory. Directory should be existed.    				
*mode*            -  _(optional)_ Mode PRODUCTION/DEBUG. Defaults to PRODUCTION, means it obfuscates all files. In DEBUG mode files only glued but aren't obfuscated.    

From this point you can try _mvn package_ to pack your project.    
Also cactus plugin can be used without project, all we need to create cactus.xml configuration file and being in the same folder (where cactus.xml is) run:

	mvn com.cactus:cactus-plugin:obfuscate

## 2. Console tool

Cactus console tool have the same purpose as cactus-plugin - obfuscate js files.
Here we don't need to use maven. All we need is jvm (Java Virtual Machine).

How to run it:    
1. create cactus.xml configuration file
2. run cactus-tool:
    
	java -jar cactus-tool.jar

By default tool doesn't need additional parameters if it is run from the same folder where cactus.xml file is.
    
But supports the following parameters:    
*-d* - _(optional)_ The path to folder contains cactus.xml file. All files in cactus.xml are specified with relative path from this folder. If source directory wasn't specified the tool tries to get cactus.xml from the current working directory.                 
*-o* - _(optional)_ Relative path to destination/output folder. If output directory wasn't specified all output will be going to -d (source directory). _This parameter should be going after -d parameter (if -d is used)_ 	 
*-m* - _(optional)_ The mode: PRODUCTION/DEBUG. Default mode is  PRODUCTION - obfuscation is ON.    

	java -jar cactus-tool.jar -d ../web/js -o cache
	
## 3. Configuration file

For version 0.1 cactus is configured using _xml_ based file.
