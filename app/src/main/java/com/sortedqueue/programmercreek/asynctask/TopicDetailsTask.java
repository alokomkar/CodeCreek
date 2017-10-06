package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.database.SubTopics;
import com.sortedqueue.programmercreek.database.TopicDetails;
import com.sortedqueue.programmercreek.database.lessons.Lesson;
import com.sortedqueue.programmercreek.util.CommonUtils;

import java.util.ArrayList;

import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.MULTI_CHOICE;
import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.REARRANGE;
import static com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants.SINGLE_CHOICE;

/**
 * Created by Alok on 18/09/17.
 */

public class TopicDetailsTask extends AsyncTask<Void, Void, ArrayList<TopicDetails>> {


    private final String mTopic;
    private Context mContext;
    private TopicDetailsListener mTopicDetailsListener;

    public TopicDetailsTask(Context context, String topic, TopicDetailsListener topicDetailsListener ) {
        this.mContext = context;
        this.mTopic = topic;
        this.mTopicDetailsListener = topicDetailsListener;
    }

    public interface TopicDetailsListener {
        void onSuccess( ArrayList<TopicDetails> topicDetails );
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
                SINGLE_CHOICE,
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
                SINGLE_CHOICE,
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
                "",
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
                SINGLE_CHOICE,
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
                SINGLE_CHOICE,
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
                MULTI_CHOICE,
                "Identify all the valid fields : ",
                "String name;|||String gender;|||age;|||Human human;",
                "String name;|||String gender;|||Human human;",
                "age isn't a valid field since it doesn't have a preceding datatype, rest all the options are correct.");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Creating Instances of a Class",
                "The following is the general syntax to create an instance of a class:",
                "<<Class>> <<variable>> = new <<Call to Class Constructor>>;\n" +
                        " \n" +
                        "//e.g.\n" +
                        " \n" +
                        "Human h = new Human();",
                "When you do not add a constructor to a class, the Java compiler adds one for you. The constructor that is added by the Java compiler is called a default constructor. The default constructor accepts no arguments. The name of the constructor of a class is the same as the class name. The new operator is followed by a call to the constructor of the class whose instance is being created. The new operator creates an instance of a class by allocating the memory on heap.",
                "",
                SINGLE_CHOICE,
                "Identify default constructor :",
                "new Human( \"Kylie\", \"female\");|||new Human();|||new Human(\"James\");",
                "new Human();",
                "Default constructor has no parameters");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "The null Reference Type",
                "Java has a special reference type called null type. It has no name. Therefore, you cannot define a variable of the null reference type. The null reference type has only one value defined by Java, which is the null literal. It is simply null. The null reference type is assignment compatible with any other reference type. That is, you can assign a null value to a variable of any reference type. Practically, a null value stored in a reference type variable means that the reference variable is referring to no object.",
                "// Assign null value to john\n" +
                        "Human john = null;  // john is not referring to any object\n" +
                        "john = new Human(); // Now, john is referring to a valid Human object",
                "Note that null is a literal of null type. You cannot assign null to a primitive type variable, and that’s why java compiler does not allow you to compare a primitive value to a null value.",
                "",
                SINGLE_CHOICE,
                "Can null be assigned to primitive data types such as int?",
                "Yes|||No",
                "No",
                "You cannot assign null to a primitive type variable");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Constructors",
                "A constructor is a named block of code that is used to initialize an object of a class immediately after the object is created. The general syntax for a constructor declaration is:",
                "<<Modifiers>> <<Constructor Name>>(<<parameters list>>) throws <<Exceptions list>> {\n" +
                        "        // Body of constructor goes here\n" +
                        "}",
                "A constructor can have its access modifier as public, private, protected, or package-level (no modifier). The constructor name is the same as the simple name of the class. The constructor name is followed by a pair of opening and closing parentheses, which may include parameters. Optionally, the closing parenthesis may be followed by the keyword throws, which in turn is followed by a comma-separated list of exceptions.",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Special note on Constructors",
                "Unlike a method, a constructor does not have a return type. You cannot even specify void as a return type for a constructor. If there is any return type then it is method. Remember that if the name of a construct is the same as the simple name of the class, it could be a method or a constructor. If it specifies a return type, it is a method. If it does not specify a return type, it is a constructor",
                "",
                "",
                "",
                MULTI_CHOICE,
                "Identify valid constructors : ",
                "new Human( \"Kylie\", \"female\");|||new Human();|||new Human(\"James\");|||Human getInstance();",
                "new Human( \"Kylie\", \"female\");|||new Human();|||new Human(\"James\");",
                "getInstance() refers to a instance creation method with return type Human.");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "Instance Initialization Block",
                "You saw that a constructor is used to initialize an instance of a class. An instance initialization block, also called instance initializer, is also used to initialize objects of a class. An instance initializer is simply a block of code inside the body of a class, but outside any methods or constructors. An instance initializer does not have a name. Its code is simply placed inside an opening brace and a closing brace.\n" +
                        "\n" +
                        "Note that an instance initializer is executed in instance context and the keyword this is available inside the instance initializer.",
                "// An instance initializer\n" +
                        "{\n" +
                        "        /* Other code for the instance initializer goes here */\n" +
                        "}",
                "You can have multiple instance initializers for a class. All of them are executed automatically in textual order for every object you create. Code for all instance initializers are executed before any constructor.\n" +
                        "\n" +
                        "An instance initializer cannot have a return statement. It cannot throw checked exceptions unless all declared constructors list those checked exceptions in their throws clause.",
                "",
                "",
                "",
                "",
                "");
        subTopics.add(subTopics1);

        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "static Initialization Block",
                "A static initialization block is also known as a static initializer. It is similar to an instance initialization block. It is used to initialize a class. An instance initializer is executed once per object whereas a static initializer is executed only once for a class when the class definition is loaded into JVM. To differentiate it from an instance initializer, you need to use the static keyword in the beginning of its declaration.\n" +
                        "\n" +
                        "You can have multiple static initializers in a class. All static initializers are executed in textual order in which they appear, and execute before any instance initializers.",
                "// An static initializer\n" +
                        "static {\n" +
                        "        /* Other code for the static initializer goes here */\n" +
                        "}",
                "A static initializer cannot throw checked exceptions and it cannot have a return statement.",
                "",
                REARRANGE,
                "Rearrange in the right execution order",
                "Hello|||Namasthe|||Hola",
                "Namasthe|||Hola|||Hello",
                "All static initializers are executed in textual order in which they appear",
                "static{ System.out.println(\"Namasthe\")}\nstatic{ System.out.println(\"Hola\")}\nstatic{ System.out.println(\"Hello\")}");
        subTopics.add(subTopics1);


        subTopics1 = new SubTopics(
                topicDetails.getTopicId() + "_" + subTopic++,
                programLanguage,
                "static Initialization Block",
                "A static initialization block is also known as a static initializer. It is similar to an instance initialization block. It is used to initialize a class. An instance initializer is executed once per object whereas a static initializer is executed only once for a class when the class definition is loaded into JVM. To differentiate it from an instance initializer, you need to use the static keyword in the beginning of its declaration.\n" +
                        "\n" +
                        "You can have multiple static initializers in a class. All static initializers are executed in textual order in which they appear, and execute before any instance initializers.",
                "// An static initializer\n" +
                        "static {\n" +
                        "        /* Other code for the static initializer goes here */\n" +
                        "}",
                "A static initializer cannot throw checked exceptions and it cannot have a return statement.",
                "",
                REARRANGE,
                "Rearrange in the right execution order",
                "Hello|||Namasthe|||Hola",
                "Namasthe|||Hola|||Hello",
                "All static initializers are executed in textual order in which they appear",
                "static{ System.out.println(\"Namasthe\")}\nstatic{ System.out.println(\"Hola\")}\nstatic{ System.out.println(\"Hello\")}");
        subTopics.add(subTopics1);

        topicDetails.setSubTopicsArrayList(subTopics);
        topicDetailsArrayList.add(topicDetails);

        return topicDetailsArrayList;

    }

    @Override
    protected ArrayList<TopicDetails> doInBackground(Void... voids) {
        return getTopicDetailsForTopic();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<TopicDetails> topicDetails) {
        super.onPostExecute(topicDetails);
        mTopicDetailsListener.onSuccess(topicDetails);
    }
}
