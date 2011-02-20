SizeOf is a simple java agent what can be used to calculate the memory
size of java objects.  The agent is implemented with the
java.lang.instrument introduced with java 5.

Here is a simple howto:

1) use the class SizeOf in your code to test the size of your java object:

	// calculate object size
	SizeOf.sizeOf(<your object>)
	SizeOf.deepSizeOf(<your object>)

Optionally, configure SizeOf's behavior with
SizeOf.skipStaticField(false) or SizeOf.skipFlyWeightObject(true)
(defaults are true and false, respectively).
	
2) start the jvm with the argument: -javaagent:<path to>/SizeOf.jar

Improvements and suggestions are welcome!
