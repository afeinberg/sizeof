java.SizeOf 0.2 (April 2007)
===========================

-improved usability: the new method deepSizeOf doesn't throw any checked exception

-added support for (known) flyweight objects (many thanks to Dr. Heinz Kabutz - www.javaspecialists.co.za) 

java.SizeOf 0.1 (June 2006)
===========================

http://sourceforge.net/projects/sizeof

java.SizeOf is a simple java agent what can be used to calculate the memory size
of java objects.
The agent is implemented with the java.lang.instrument introduced with java 5.
Here is a simple howto:

1) use the class SizeOf in your code to test the size of your java object:

	//configuration steps
	SizeOf.skipStaticField(true);
	SizeOf.setMinSizeToLog(10);
	
	//calculate object size
	SizeOf.iterativeSizeOf(<your object>)
	
2) start the jvm with the argument: -javaagent:<path to>/SizeOf.jar

To avoid the dependencies of your code to SizeOf the best use of the agent is in 
conjunction with aspect.

The project is in early stage of development but it's quite usable.
Improvements and suggestions are welcome!

Have fun!
 Marco e Nicola




