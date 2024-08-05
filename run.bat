@echo off
javac -cp "lib/gson-2.11.0.jar" -d bin src/*.java src/GameObjects/*.java
java -cp "bin;lib/gson-2.11.0.jar" src.Main