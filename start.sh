#!/bin/bash
set encoding=utf-8
CLASSPATH=conf/
for i in lib/*.jar; do
        CLASSPATH=${CLASSPATH}:$i
done
for i in *.jar; do
        CLASSPATH=${CLASSPATH}:$i
done
echo ${CLASSPATH}
/usr/java/jdk1.8.0_60/bin/java -cp ${CLASSPATH} org.hellosix.south.door >> logs/start.log 2>&1 < /dev/null &
echo $! > pid
