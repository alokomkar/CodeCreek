package com.sortedqueue.programmercreek.v2.base

import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent

import java.util.*

fun getSixthContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("",
            "Qn. Choose all applicable : Java is ?" +
                    "Object oriented|||" +
                    "Distributed|||" +
                    "Multithreaded|||" +
                    "Architecture neutral|||" +
                    "Not dynamic",
            SimpleContent.mcq,
            "Object oriented|||" +
                    "Distributed|||" +
                    "Multithreaded|||" +
                    "Architecture neutral"))
    simpleContentList.add(SimpleContent("",
            "Qn. What's this comment type?/**\n" +
                    " * The HelloWorldApp class implements an application that\n" +
                    " * simply displays \"Hello World!\" to the standard output.\n" +
                    " */\n??" +
                    "Single line comment|||" +
                    "Multi line comment|||" +
                    "Not a comment",
            SimpleContent.codeMcq,
            "Multi line comment"))

    simpleContentList.add(SimpleContent("",
            "Qn. Complete the syntax?" +
                    "class HelloWorldApp {\n" +
                    "}\n" +
                    "Output : HelloWorldApp class creation",
            SimpleContent.syntaxLearn))

    simpleContentList.add(SimpleContent("",
            "Qn. Complete the syntax?" +
                    "    public static void main(String[] args) {\n" +
                    "    }\n" +
                    "Output : main method creation - entry point to any project",
            SimpleContent.syntaxLearn))

    simpleContentList.add(SimpleContent("",
            "Qn. Rearrange in the right order?" +
                    "class HelloWorldApp {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello World!\");\n" +
                    "    }\n" +
                    "}",
            SimpleContent.rearrange))



    simpleContentList.add(SimpleContent("",
            "The <<API>> is a large collection of ready-made software " +
                    "components that provide many useful capabilities. " +
                    "It is grouped into libraries of related <<classes and interfaces>>; these libraries are known as <<packages>>.",
            SimpleContent.fillBlanks))

    //rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )

    return simpleContentList

}

fun getFifthContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("",
            "A Closer Look at the \"Hello World!\" Application",
            SimpleContent.header))
    simpleContentList.add(SimpleContent("",
            "/**\n" +
                    " * The HelloWorldApp class implements an application that\n" +
                    " * simply displays \"Hello World!\" to the standard output.\n" +
                    " */\n" +
                    "class HelloWorldApp {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello World!\"); //Display the string.\n" +
                    "    }\n" +
                    "}",
            SimpleContent.code))
    simpleContentList.add(SimpleContent("",
            "The \"Hello World!\" application consists of three primary components: " +
                    "\n\nSource code comments " +
                    "\n\nThe HelloWorldApp class definition " +
                    "\n\nThe main method",
            SimpleContent.content))

    simpleContentList.add(SimpleContent("", "Comments are ignored by the compiler but are useful to other programmers. The Java programming language supports three kinds of comments:", SimpleContent.header))

    simpleContentList.add(SimpleContent("", "/* text */", SimpleContent.code))
    simpleContentList.add(SimpleContent("", "The compiler ignores everything from /* to */.", SimpleContent.bullets))
    simpleContentList.add(SimpleContent("", "/** documentation */", SimpleContent.code))
    simpleContentList.add(SimpleContent("", "This indicates a documentation comment (doc comment, for short). The compiler ignores this kind of comment, just like it ignores comments that use /* and */. The javadoc tool uses doc comments when preparing automatically generated documentation.", SimpleContent.bullets))
    simpleContentList.add(SimpleContent("", "// text", SimpleContent.code))
    simpleContentList.add(SimpleContent("", "The compiler ignores everything from // to the end of the line.", SimpleContent.bullets))

    simpleContentList.add(SimpleContent("",
            "Qn. What's this comment type?/**\n" +
                    " * The HelloWorldApp class implements an application that\n" +
                    " * simply displays \"Hello World!\" to the standard output.\n" +
                    " */\n??" +
                    "Single line comment|||" +
                    "Multi line comment|||" +
                    "Not a comment",
            SimpleContent.codeMcq,
            "Multi line comment"))

    return simpleContentList


}

fun getFourthContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "We can't promise you fame, fortune, or even a job if you learn the Java programming language. Still, it is likely to make your programs better and requires less effort than other languages. We believe that Java technology will help you do the following:", SimpleContent.header ))
    simpleContentList.add(SimpleContent("",
            "Get started quickly: Although the Java programming language is a powerful object-oriented language, it's easy to learn, especially for programmers already familiar with C or C++.\n" +
                    "Write less code: Comparisons of program metrics (class counts, method counts, and so on) suggest that a program written in the Java programming language can be four times smaller than the same program written in C++.\n" +
                    "Write better code: The Java programming language encourages good coding practices, and automatic garbage collection helps you avoid memory leaks. Its object orientation, its JavaBeans™ component architecture, and its wide-ranging, easily extendible API let you reuse existing, tested code and introduce fewer bugs.\n" +
                    "Develop programs more quickly: The Java programming language is simpler than C++, and as such, your development time could be up to twice as fast when writing in it. Your programs will also require fewer lines of code.\n" +
                    "Avoid platform dependencies: You can keep your program portable by avoiding the use of libraries written in other languages.\n" +
                    "Write once, run anywhere: Because applications written in the Java programming language are compiled into machine-independent bytecodes, they run consistently on any Java platform.\n" +
                    "Distribute software more easily: With Java Web Start software, users will be able to launch your applications with a single click of the mouse. An automatic version check at startup ensures that users are always up to date with the latest version of your software. If an update is available, the Java Web Start software will automatically update their installation.", SimpleContent.content ))

    return( simpleContentList )
}

fun getThirdContent()  : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "The general-purpose, high-level Java programming language is a powerful software platform. Every full implementation of the Java platform gives you the following features:", SimpleContent.header ))
    simpleContentList.add(SimpleContent("", "The Java platform has two components:\n" +
            "\n" +
            "The Java Virtual Machine\n" +
            "The Java Application Programming Interface (API)", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "Development Tools: The development tools provide everything you'll need for compiling, running, monitoring, debugging, and documenting your applications. As a new developer, the main tools you'll be using are the javac compiler, the java launcher, and the javadoc documentation tool.\n" +
                    "\n" +
                    "Application Programming Interface (API): The API provides the core functionality of the Java programming language. It offers a wide array of useful classes ready for use in your own applications. It spans everything from basic objects, to networking and security, to XML generation and database access, and more.\n" +
                    "Deployment Technologies: The JDK software provides standard mechanisms such as the Java Web Start software and Java Plug-In software for deploying your applications to end users.\n" +
                    "\n" +
                    "User Interface Toolkits: The JavaFX, Swing, and Java 2D toolkits make it possible to create sophisticated Graphical User Interfaces (GUIs).\n" +
                    "\n" +
                    "Integration Libraries: Integration libraries such as the Java IDL API, JDBC API, Java Naming and Directory Interface (JNDI) API, Java RMI, and Java Remote Method Invocation over Internet Inter-ORB Protocol Technology (Java RMI-IIOP Technology) enable database access and manipulation of remote objects."
            , SimpleContent.content))


    return( simpleContentList )
}

fun getSecondContent() : ArrayList<SimpleContent> {

    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "A platform is the hardware or software environment in which a program runs. We've already mentioned some of the most popular platforms like Microsoft Windows, Linux, Solaris OS, and Mac OS. Most platforms can be described as a combination of the operating system and underlying hardware. The Java platform differs from most other platforms in that it's a software-only platform that runs on top of other hardware-based platforms.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("", "The Java platform has two components:\n" +
            "\n" +
            "The Java Virtual Machine\n" +
            "The Java Application Programming Interface (API)", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "The API is a large collection of ready-made software components that provide many useful capabilities. It is grouped into libraries of related classes and interfaces; these libraries are known as packages."
            , SimpleContent.content))

    simpleContentList.add(SimpleContent("",
            "https://docs.oracle.com/javase/tutorial/figures/getStarted/getStarted-jvm.gif",
            SimpleContent.image))

    simpleContentList.add(SimpleContent("",
            "As a platform-independent environment, the Java platform can be a bit slower than native code. However, advances in compiler and virtual machine technologies are bringing performance close to that of native code without threatening portability.\n" +
                    "\n" +
                    "The terms\"Java Virtual Machine\" and \"JVM\" mean a Virtual Machine for the Java platform.",
            SimpleContent.bullets))


    return( simpleContentList )
}

fun getFirstContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "The Java programming language is a high-level language that can be characterized by all of the following buzzwords:", SimpleContent.header ))
    simpleContentList.add(SimpleContent("",
            "Simple\n" +
                    "Object oriented\n" +
                    "Distributed\n" +
                    "Multithreaded\n" +
                    "Dynamic\n" +
                    "Architecture neutral\n" +
                    "Portable\n" +
                    "High performance\n" +
                    "Robust\n" +
                    "Secure", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "In the Java programming language, all source code is first written in plain text files ending with the .java extension. Those source files are then compiled into .class files by the javac compiler. A .class file does not contain code that is native to your processor; it instead contains bytecodes — the machine language of the Java Virtual Machine1 (Java VM). The java launcher tool then runs your application with an instance of the Java Virtual Machine."
            , SimpleContent.content))

    simpleContentList.add(SimpleContent("",
            "https://docs.oracle.com/javase/tutorial/figures/getStarted/getStarted-compiler.gif",
            SimpleContent.image))

    simpleContentList.add(SimpleContent("",
            "Because the Java VM is available on many different operating systems, the same .class files are capable of running on Microsoft Windows, the Solaris™ Operating System (Solaris OS), Linux, or Mac OS. Some virtual machines, such as the Java SE HotSpot at a Glance, perform additional steps at runtime to give your application a performance boost. This includes various tasks such as finding performance bottlenecks and recompiling (to native code) frequently used sections of code.",
            SimpleContent.content))

    simpleContentList.add(SimpleContent("",
            "https://docs.oracle.com/javase/tutorial/figures/getStarted/helloWorld.gif",
            SimpleContent.image))
    return( simpleContentList )

}

fun getOOFirstContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "What Is an Object?", SimpleContent.header ))
    simpleContentList.add(SimpleContent("",
            "It is a basic unit of Object Oriented Programming and represents the real life entities.  A typical Java program creates many objects, which as you know, interact by invoking methods. An object consists of :", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "State : It is represented by attributes of an object. It also reflects the properties of an object.\n" +
                    "Behavior : It is represented by methods of an object. It also reflects the response of an object with other objects.\n" +
                    "Identity : It gives a unique name to an object and enables one object to interact with other objects."
            , SimpleContent.bullets))

    simpleContentList.add(SimpleContent("",
            "https://www.guru99.com/images/java/052016_0704_ObjectsandC6.jpg",
            SimpleContent.image))

    simpleContentList.add(SimpleContent("",
            "Objects correspond to things found in the real world. For example, a graphics program may have objects such as “circle”, “square”, “menu”. An online shopping system might have objects such as “shopping cart”, “customer”, and “product”.",
            SimpleContent.content))

    simpleContentList.add(SimpleContent("",
            "/*Here's a sample class Car\n" +
                    "with attributes : car type and wheel count*/\n" +
                    "class Car {\n" +
                    " private String carType;\n" +
                    " private int wheelCount;\n" +
                    "}",
            SimpleContent.code))
    return( simpleContentList )

}

fun getOOSecondContent() : ArrayList<SimpleContent> {

    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "What is a class?", SimpleContent.header ))
    simpleContentList.add(SimpleContent("", "A class is a user defined blueprint or prototype from which objects are created.  It represents the set of properties or methods that are common to all objects of one type. In general, class declarations can include these components, in order:", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "Modifiers : A class can be public or has default access (Refer this for details).\n" +
                    "Class name: The name should begin with a initial letter (capitalized by convention).\n" +
                    "Superclass(if any): The name of the class’s parent (superclass), if any, preceded by the keyword extends. A class can only extend (subclass) one parent.\n" +
                    "Interfaces(if any): A comma-separated list of interfaces implemented by the class, if any, preceded by the keyword implements. A class can implement more than one interface.\n" +
                    "Body: The class body surrounded by braces, { }."
            , SimpleContent.bullets))

    simpleContentList.add(SimpleContent("",
            "class Car{\n" +
                    "\tint year;\n" +
                    "\tString make;\n" +
                    "\tdouble speed;\n" +
                    "\n" +
                    "\tpublic Car(int y,String m,double s){\n" +
                    "\t\tthis.year = y;\n" +
                    "\t\tthis.make = m;\n" +
                    "\t\tthis.speed = s;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic double getSpeed(){\n" +
                    "\t\treturn this.speed;\n" +
                    "\t}\n" +
                    "\tpublic String getMake(){\n" +
                    "\t\treturn this.make;\n" +
                    "\t}\n" +
                    "\tpublic int getYear(){\n" +
                    "\t\treturn this.year;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void accelerate(){\n" +
                    "\t\tthis.speed+=1;\n" +
                    "\t}\n" +
                    "}",
            SimpleContent.code))

    simpleContentList.add(SimpleContent("",
            "When an object of a class is created, the class is said to be instantiated. All the instances share the attributes and the behavior of the class. But the values of those attributes, i.e. the state are unique for each object. A single class may have any number of instances.",
            SimpleContent.content))

    simpleContentList.add(SimpleContent("",
            "private Car audiCar;\n" +
                    "private Car bmwCar;",
            SimpleContent.code))

    return( simpleContentList )
}

fun getOOThirdContent()  : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "Inheritance with Java", SimpleContent.header ))
    simpleContentList.add(SimpleContent("", "Inheritance is an important pillar of OOP(Object Oriented Programming). It is the mechanism in java by which one class is allow to inherit the features(fields and methods) of another class.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "Super Class: The class whose features are inherited is known as super class(or a base class or a parent class).\n" +
                    "Sub Class: The class that inherits the other class is known as sub class(or a derived class, extended class, or child class). The subclass can add its own fields and methods in addition to the superclass fields and methods.\n" +
                    "Reusability: Inheritance supports the concept of “reusability”, i.e. when we want to create a new class and there is already a class that includes some of the code that we want, we can derive our new class from the existing class. By doing this, we are reusing the fields and methods of the existing class."
            , SimpleContent.content))
    simpleContentList.add(SimpleContent("", "How to use it?", SimpleContent.header ))
    simpleContentList.add(SimpleContent("", "The keyword used is 'extends'", SimpleContent.header ))
    simpleContentList.add(SimpleContent("", "class Bike extends BaseVehicle  \n" +
            "{  \n" +
            "   //methods and fields  \n" +
            "}  ", SimpleContent.code ))

    return( simpleContentList )
}

fun getOOFourthContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("", "Types of Inheritance in Java", SimpleContent.header ))
    simpleContentList.add(SimpleContent("",
            "Single Inheritance", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "In single inheritance, subclasses inherit the features of one superclass. In image below, the class A serves as a base class for the derived class B.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "https://2.bp.blogspot.com/-qS-HZLkthPY/W5Tya5lIqEI/AAAAAAAACuo/ZAOF0JwnW2geHk0srDoUb_Fau2yWOz82ACK4BGAYYCw/s320/inheritance2.png", SimpleContent.image ))

    simpleContentList.add(SimpleContent("",
            "Multilevel Inheritance", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "In Multilevel Inheritance, a derived class will be inheriting a base class and as well as the derived class also act as the base class to other class. In below image, the class A serves as a base class for the derived class B, which in turn serves as a base class for the derived class C. In Java, a class cannot directly access the grandparent’s members.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "https://4.bp.blogspot.com/-JdAdUXvcR_U/W5TyawlOojI/AAAAAAAACus/MVjnoYWV_uYR8_4PKMKlT7IJneQLY9XEwCK4BGAYYCw/s320/inheritance3.png", SimpleContent.image ))

    simpleContentList.add(SimpleContent("",
            "Hierarchical Inheritance", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "In Hierarchical Inheritance, one class serves as a superclass (base class) for more than one sub class.In below image, the class A serves as a base class for the derived class B,C and D.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "https://2.bp.blogspot.com/-13yQtslDHC8/W5TyazUgQxI/AAAAAAAACuw/WX9yGmSWJNw-LJ9_ZL1AKKFpuIr6WnhrQCK4BGAYYCw/s320/inheritance4.png", SimpleContent.image ))

    simpleContentList.add(SimpleContent("",
            "Multiple Inheritance (Through Interfaces) :", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "In Multiple inheritance ,one class can have more than one superclass and inherit features from all parent classes. Please note that Java does not support multiple inheritance with classes. In java, we can achieve multiple inheritance only through Interfaces. In image below, Class C is derived from interface A and B.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "https://4.bp.blogspot.com/-Syfee0rd2VM/W5TyarEvYpI/AAAAAAAACug/sVoikqv5R1orrrx8DdmX616FcP2QcWojACK4BGAYYCw/s320/inheritance2-1.png", SimpleContent.image ))

    simpleContentList.add(SimpleContent("",
            "Hybrid Inheritance(Through Interfaces) :", SimpleContent.bullets ))
    simpleContentList.add(SimpleContent("",
            "It is a mix of two or more of the above types of inheritance. Since java doesn’t support multiple inheritance with classes, the hybrid inheritance is also not possible with classes. In java, we can achieve hybrid inheritance only through Interfaces.", SimpleContent.content ))
    simpleContentList.add(SimpleContent("",
            "https://1.bp.blogspot.com/-1nKymZ1Xjx4/W5TyagVELDI/AAAAAAAACuk/14Hgc3JsoXgzCvzOvMzOPdKVLWIw41e-gCK4BGAYYCw/s320/inheritance-5.png", SimpleContent.image ))


    return( simpleContentList )
}

fun getOOFifthContent() : ArrayList<SimpleContent> {
    val simpleContentList = ArrayList<SimpleContent>()
    simpleContentList.add(SimpleContent("",
            "A Closer Look at the \"Hello World!\" Application",
            SimpleContent.header))
    simpleContentList.add(SimpleContent("",
            "/**\n" +
                    " * The HelloWorldApp class implements an application that\n" +
                    " * simply displays \"Hello World!\" to the standard output.\n" +
                    " */\n" +
                    "class HelloWorldApp {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello World!\"); //Display the string.\n" +
                    "    }\n" +
                    "}",
            SimpleContent.code))
    simpleContentList.add(SimpleContent("",
            "The \"Hello World!\" application consists of three primary components: " +
                    "\n\nSource code comments " +
                    "\n\nThe HelloWorldApp class definition " +
                    "\n\nThe main method",
            SimpleContent.content))

    simpleContentList.add(SimpleContent("", "Comments are ignored by the compiler but are useful to other programmers. The Java programming language supports three kinds of comments:", SimpleContent.header))

    simpleContentList.add(SimpleContent("", "/* text */", SimpleContent.code))
    simpleContentList.add(SimpleContent("", "The compiler ignores everything from /* to */.", SimpleContent.bullets))
    simpleContentList.add(SimpleContent("", "/** documentation */", SimpleContent.code))
    simpleContentList.add(SimpleContent("", "This indicates a documentation comment (doc comment, for short). The compiler ignores this kind of comment, just like it ignores comments that use /* and */. The javadoc tool uses doc comments when preparing automatically generated documentation.", SimpleContent.bullets))
    simpleContentList.add(SimpleContent("", "// text", SimpleContent.code))
    simpleContentList.add(SimpleContent("", "The compiler ignores everything from // to the end of the line.", SimpleContent.bullets))

    return simpleContentList


}