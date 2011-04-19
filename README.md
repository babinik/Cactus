Cactus Obfuscator
=================

### version 0.1 


##### Cactus is a java multi-module application dedicated to process and obfuscate JavaScript and Css code.

It includes:  
1. Maven plugin;  
2. Console tool.

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