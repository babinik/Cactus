Cactus Obfuscator
=================
#### Open Source GPL 3.0 licence 

### version 0.1

##### Cactus is a java multi-module application dedicated to process and obfuscate JavaScript and Css code.

Cactus uses:
    
* [Apache Maven](http://maven.apache.org/) - plugin(version 3.*) creation/integration  
* [YUI Compressor](http://http://developer.yahoo.com/yui/compressor/ "YUI Compressor") - js/css files obfuscation
* [Simple XML](http://simple.sourceforge.net/) - XML mapping/(de)serialization

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
*baseDirectory*    -  _(optional)_ Defaults ${project.basedir}    
*jsBaseDirectory*  -  _(required)_ Relative path from baseDirectory to the JavaScript/Css sources.
*confingDirectory* -  _(optional)_ Path to the folder where is cactus.xml configuration file, by default it is the same as jsBaseDirectory    
*outputDirectory*  -  _(optional)_ Relative path from jsBaseDirectory to output directory for obfuscated/processed files. Defaults to the jsBaseDirectory. Directory should be existed.    				
*mode*             -  _(optional)_ Mode PRODUCTION/DEBUG. Defaults to PRODUCTION, means it obfuscates all files. In DEBUG mode files only glued but aren't obfuscated.    

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

By default tool requires only one paremeter -c the path to the folder which contains cactus.xml file.
    
But supports the following parameters:    

*-c* - _(required)_ The path to folder contains cactus.xml file.    
*-d* - _(optional)_ All files in cactus.xml are specified with relative path from -c folder. 
*-o* - _(optional)_ Relative path to destination/output folder. If output directory wasn't specified all output will be going to -d (source directory). _This parameter should be going after -d parameter (if -d is used)_ 	 
*-m* - _(optional)_ The mode: PRODUCTION/DEBUG. Default mode is  PRODUCTION - obfuscation is ON.    

	java -jar cactus-tool.jar -d ../web/js -o cache
	
## 3. Configuration file

For version 0.1 cactus is configured using _xml_ based file.

cactus.xml:    

	<?xml version="1.0" encoding="UTF-8"?>
	<cactus>
		<js>
			<needles>		
				<needle>
					<output>ext-3.1.0.js</output>
					<files>
						<file>https://ajax.googleapis.com/ajax/libs/ext-core/3.1.0/ext-core-debug.js</file>
					</files>
				</needle>
				<needle>
					<output>jquery-1.5.2.js</output>
					<files>
						<file>https://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.js</file>
					</files>
				</needle>
			</needles>
		</js>

		<css>
			<needles>		
				<needle>
					<output>test.css</output>
					<files>
						<file>YOUR_CSS_FILE_HERE</file>
					</files>
				</needle>
			</needles>		
		</css>
	</cactus>

The above is example of simple cactus configuration file which contains 3 outputs (needles): _ext-3.1.0.js_, _jquery-1.5.2.js_ and _test.css_

After running:    
	
	mkdir cactus-test
	cd cactus-test
	//copy cactus.xml and cactus-tool.jar in cactus-test folder 
	java -jar cactus-tool.jar -c PATH_TO_CACTUS_CONFIG_FOLDER
	
will be gotten all files and created two output needles.

*&lt;file&gt;* tag accepts URLs, folder paths and file names.

All folders should be relative from the folder BASE folder (-c). 
For example we have the following folders structure:   

	|-root
	|--batch    
	|  | - cactus-tool.jar  
	|--webapp     
	|----js    
	|   |--cactus.xml
   	|   |--boo.js    
	|   |--foo.js
	|   |--utils
	|   |  |--array.js
	|   |  |--string.js
	|   |  |--parser
	|   |  |  |--xmlparser.js
	|   |--cache

And the following cactus.xml file:

	<?xml version="1.0" encoding="UTF-8"?>
	<cactus>
		<js>
			<needles>		
				<needle>
					<output>test.js</output>
					<files>
						<file>boo.js</file>
						<file>utils</file> <!-- get all js files from utils folder-->
						<file>utils/parser/xmlparser.js</file>
					</files>
				</needle>
			</needles>
		</js>
	</cactus>
	
Running the tool from root/batch folder    

	java -jar cactus-tool.jar -c ../webapp/js -d ../webapp/js -o cache
Here we have cactus.xml configuration file in the js folder that is why -c and -d parameters are equal.

We get _test.js_ file inside root/webappjs/cache folder, which has boo.js, all files js from utils and utils/parser/xmlparser.js file obfuscated.

The sequence of *&lt;file&gt;* tags is important it reflects on the order of file's content in the output needle file.
	
	
   
