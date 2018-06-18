mvn clean package;
Copy-Item D:\Github\Ardublock\ardublock\target\ardublock-all.jar 'D:\Github\Arduino-1.8.5\build\windows\arduino-1.8.5-windows\arduino-1.8.5\tools\ArduBlockToolJOIN\tool\ardublock-all.jar';
Start-Process 'D:\Github\Arduino-1.8.5\build\windows\arduino-1.8.5-windows\arduino-1.8.5\arduino.exe'