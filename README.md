Apiary Blueprint Manager
===============================

Apiary Blueprint Manager is a plugin for Android studio, or any other 
IntelliJ IDEA based IDE. This plugin was created as my bachelor thesis. 
It helps keep the code up-to-date with Apiary documentation, by notifying 
about changes and helps by generating code based on Apiary documentation.

Build from source
=================

These steps were tested on:

OS: Linux Mint 17.1 Cinnamon 64-bit  
Internal Java Platform: 1.8  
IntelliJ IDEA version: 14.0.4  

1. Open IntelliJ IDEA.  
2. Import Project -> Path to plugin source code.  
3. Choose "Create project from existing sources".  
4. On "select project SDK" window, add new "IntelliJ Platform Plugin SDK" with path set to IntelliJ IDEA.  
5. After completing project import, one should be able to compile the project by opening terminal at the bottom of IntelliJ IDEA. Make sure terminal path is set to project root folder.  
6. You can compile the output .jar file by: "./gradlew makeJar" command.  

In case of any problem, please feal free to contact me on [tuxilero@gmail.com](tuxilero@gmail.com)

Dependencies
============

IntelliJ IDEA or Android Studio.
Java 1.7 JRE or later.
For best look and feel use with Dracula, or any other dark theme.

Usage
=====

Usage is described detailed here:
(http://tuxilero.blogspot.cz/p/abmdoc.html)

Example
=======

Example video how to use this plugin can be found here:
(https://www.youtube.com/watch?v=ZHRyQUN5bUc)


Developed by
============

[Lukáš Hermann](http://tuxilero.blogspot.cz/)

License
=======

    Copyright 2015 Lukáš Hermann

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
