# compile all the java files
classes:
	javac *.java
# default target
make: classes

# removes all compiled files
clean:
	rm -f *.class


