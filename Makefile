JAVAC=javac
RM=rm -f

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

program: $(classes)

all: program

clean:
	$(RM) *.class

%.class: %.java
	$(JAVAC) $<