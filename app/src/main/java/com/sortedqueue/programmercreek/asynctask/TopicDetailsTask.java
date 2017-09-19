package com.sortedqueue.programmercreek.asynctask;

import com.sortedqueue.programmercreek.database.SubTopics;
import com.sortedqueue.programmercreek.database.TopicDetails;

import java.util.ArrayList;

/**
 * Created by Alok on 18/09/17.
 */

public class TopicDetailsTask {


    private final String mTopic;

    public TopicDetailsTask(String topic ) {
        this.mTopic = topic;
    }

    private ArrayList<TopicDetails> getTopicDetailsForTopic( ) {

        int topicIndex = 1;
        String programLanguage = "java";

        ArrayList<TopicDetails> topicDetailsArrayList = new ArrayList<>();
        TopicDetails topicDetails = new TopicDetails( programLanguage + "_" + topicIndex, programLanguage, "Java Basics"  );
        int subTopic = 1;
        ArrayList<SubTopics> subTopics = new ArrayList<>();
        SubTopics subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "What is Java programming language?",
                "Java is a general-purpose computer programming language that is concurrent, class-based, object-oriented, and specifically designed to have as few implementation dependencies as possible. It is intended to let application developers “write once, run anywhere” (WORA), meaning that compiled Java code can run on all platforms that support Java without the need for recompilation."  );
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "For example",
                "You can write and compile a Java program on UNIX and run it on Microsoft Windows, Macintosh, or UNIX machine without any modifications to the source code. WORA is achieved by compiling a Java program into an intermediate language called bytecode. The format of bytecode is platform-independent. A virtual machine, called the Java Virtual Machine (JVM), is used to run the bytecode on each platform.",
                "",
                "",
                "",
                "single_choice",
                "What intermediate language is Java converted to?",
                "sourcecode|||bytecode|||decode",
                "bytecode");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Development",
                "Java was originally developed by James Gosling at Sun Microsystems (which has since been acquired by Oracle Corporation) and released in 1995 as a core component of Sun Microsystems’ Java platform. The language derives much of its syntax from C and C++, but it has fewer low-level facilities than either of them.\n" +
                        "\n" +
                        "Oracle Corporation is the current owner of the official implementation of the Java SE platform, following their acquisition of Sun Microsystems on January 27, 2010. This implementation is based on the original implementation of Java by Sun. The Oracle implementation is available for Microsoft Windows, Mac OS X, Linux and Solaris.\n" +
                        "\n" +
                        "The Oracle implementation is packaged into two different distributions: \n\n" +
                        "The Java Runtime Environment (JRE) which contains the parts of the Java SE platform required to run Java programs and is intended for end users, " +
                        "\n\nand the Java Development Kit (JDK), which is intended for software developers and includes development tools such as the Java compiler, Javadoc, Jar, and a debugger.\n" +
                        "\n",
                "",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Taking out the garbage",
                "Java uses an automatic garbage collector to manage memory in the object lifecycle. The programmer determines when objects are created, and the Java runtime is responsible for recovering the memory once objects are no longer in use. Once no references to an object remain, the unreachable memory becomes eligible to be freed automatically by the garbage collector. Something similar to a memory leak may still occur if a programmer’s code holds a reference to an object that is no longer needed, typically when objects that are no longer needed are stored in containers that are still in use. If methods for a nonexistent object are called, a “null pointer exception” is thrown. Garbage collection may happen at any time. Ideally, it will occur when a program is idle. It is guaranteed to be triggered if there is insufficient free memory on the heap to allocate a new object; this can cause a program to stall momentarily. Explicit memory management is not possible in Java.",
                "",
                "",
                "",
                "single_choice",
                "How does Java manage memory in the object lifecycle?",
                "trash can|||garbage collector",
                "garbage collector");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "First step",
                "The traditional “Hello, world!” program can be written in Java as:",
                "public class HelloWorldApplication {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello World!\"); \n" +
                        "    }\n" +
                        "}",
                "Hello World!",
                "",
                "rearrange",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Hello world explained",
                "Source files must be named after the public class they contain, appending the suffix .java, for example, HelloWorldApplication.java. \n\n" +
                        "It must first be compiled into bytecode, using a Java compiler, producing a file named HelloWorldApplication.class. Only then can it be executed, or ‘launched’. \n\n" +
                        "The Java source file may only contain one public class, but it can contain multiple classes with other than public access and any number of public inner classes. When the source file contains multiple classes, make one class ‘public’ and name the source file with that public class name.",
                "",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        topicDetails.setSubTopicsArrayList(subTopics);
        topicDetailsArrayList.add(topicDetails);


        topicDetails = new TopicDetails( programLanguage + "_" + topicIndex, programLanguage, "Working with Class[class]"  );
        subTopic = 1;
        subTopics = new ArrayList<>();
        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to create a class in Java?",
                "Classes are the basic units of programming in the object-oriented paradigm. In this tutorial, we will look into some basic yet important stuffs, you need to know, while writing a class in java."  );
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Components of class",
                "In Java, classes are used as templates to create objects. Let’s discuss how to define a class. A class in Java may consist of five components:\n\n" +
                        "1. Fields\n" +
                        "2. Methods\n" +
                        "3. Constructors\n" +
                        "4. Static initializers\n" +
                        "5. Instance initializers",
                "",
                "",
                "",
                "single_choice",
                "What intermediate language is Java converted to?",
                "sourcecode|||bytecode|||decode",
                "bytecode");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Components explained",
                "1. Fields and methods are also known as members of the class. Rest 3 components are used to during initialization of class i.e. creating objects using class template. " +
                        "\n\n2. Constructors are used to create objects of a class. You must have at least one constructor for a class (if you do declare explicitely then JVM inject default contructor for you). " +
                        "\n\n3. Initializers are used to initialize fields of a class. You can have zero or more initializers of static or instance types.",
                "",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare class?",
                "The general syntax for declaring a class in Java is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // Body of the class goes here\n" +
                        "}",
                "A class declaration may have zero or more modifiers. The keyword class is used to declare a class. The <<class name>> is a user-defined name of the class, which should be a valid identifier. Each class has a body, which is specified inside a pair of braces ({}). The body of a class contains its different components, for example, fields, methods, etc.",
                "",
                "single_choice",
                "What keyword is used while declaring a class in Java?",
                "class|||Class|||<<modifiers>>",
                "class");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Example",
                "An example of class declaration will be :",
                "// Main.java filename\n" +
                        "class Main {\n" +
                        "    // Empty body for now; Write you own\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Example",
                "Suppose every object of human class has two properties: a name and a gender. The human class should include declarations of two fields: one to represent name and one to represent gender. So the fields declared would look like this:",
                "// Human.java\n" +
                        "class Human {\n" +
                        "        String name;\n" +
                        "        String gender;\n" +
                        "}",
                "Here the Human class declares two fields: name and gender. Both fields are of the String type. Every instance (or object) of the Human class will have a copy of these two fields.",
                "",
                "multi_choice",
                "Identify all the valid fields : ",
                "String name;|||String gender;|||age;|||Human human;",
                "String name;|||String gender;|||Human human;",
                "age isn't a valid field since it doesn't have a preceding datatype, rest all the options are correct.");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "How to declare fields in a Class",
                "Fields of a class represent properties (also called attributes) of objects of that class. The fields are declared inside the body of the class. The general syntax to declare a field in a class is:",
                "<<modifiers>> class <<class name>> {\n" +
                        "        // A field declaration\n" +
                        "        <<modifiers>> <<data type>> <<field name>> = <<initial value>>;\n" +
                        "}",
                "",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        topicDetails.setSubTopicsArrayList(subTopics);
        topicDetailsArrayList.add(topicDetails);

        return topicDetailsArrayList;

    }
}
