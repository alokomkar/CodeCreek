package com.sortedqueue.programmercreek.asynctask

import android.os.AsyncTask

import com.sortedqueue.programmercreek.database.lessons.BitModule
import com.sortedqueue.programmercreek.database.lessons.Lesson

import java.util.ArrayList

/**
 * Created by Alok on 28/08/17.
 */

class LessonFetchTask(private val mLessonListener: LessonFetcherTaskListener) : AsyncTask<Void, Void, ArrayList<Lesson>>() {

    interface LessonFetcherTaskListener {
        fun onSuccess(lessons: ArrayList<Lesson>)
    }

    //http://howtodoinjava.com/java-tutorials-list-howtodoinjava/
    // Single line syntax indicator - ask user to complete the same syntax..
    /*lesson = new Lesson();
        lesson.setProgramLanguage(programLanguage);
        lesson.setLessonId("lesson_" + programLanguage + lessonIndex++ );
        lesson.setTitle("Collection in Java");
        lessons.add(lesson);

        lesson = new Lesson();
        lesson.setProgramLanguage(programLanguage);
        lesson.setLessonId("lesson_" + programLanguage + lessonIndex++ );
        lesson.setTitle("NIO[New IO] in Java");
        lessons.add(lesson);*///For Java Introduction
    //https://howtodoinjava.com/core-java/basics/what-is-java-programming-language/
    val lessons: ArrayList<Lesson>
        get() {

            val lessons = ArrayList<Lesson>()
            val programLanguage = "java"
            var lessonIndex = 1
            var moduleIndex = 1

            var lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Object Oriented Principles - Static Keyword"

            var bitModules = ArrayList<BitModule>()
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Java Static Keyword – Everything You Should Know",
                    "Static keyword in java can be applied on variables, methods, blocks, import " +
                            "and inner classes. In this tutorial, we will learn the effect " +
                            "of using static keyword in these places in detail."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Variable",
                    "To declare a variable static, use static keyword in variable declaration. static variable syntax is:",
                    "ACCESS_MODIFER static DATA_TYPE VARNAME;",
                    "random"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Variable",
                    "For example, a public static variable of Integer type is declared in this way.",
                    "public static Integer staticVar;", "random"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Variable",
                    "The most important thing about static variables is that they belong to class level. What it means is that there can be only one copy of variable in runtime. When you define a static variable in class definition, each instance of class will have access to that single copy. Separate instances of class will not have their own local copy, like they have for non-static variables.\n" +
                            "\n" +
                            "Let’s understand with an example:",
                    "public class JavaStaticExample \n" +
                            "{\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        DataObject objOne = new DataObject();\n" +
                            "        objOne.staticVar = 10;\n" +
                            "        objOne.nonStaticVar = 20;\n" +
                            "         \n" +
                            "        DataObject objTwo = new DataObject();\n" +
                            "         \n" +
                            "        System.out.println(objTwo.staticVar);       //10\n" +
                            "        System.out.println(objTwo.nonStaticVar);    //null\n" +
                            "         \n" +
                            "        DataObject.staticVar = 30;  //Direct Access\n" +
                            "         \n" +
                            "        System.out.println(objOne.staticVar);       //30\n" +
                            "        System.out.println(objTwo.staticVar);       //30\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class DataObject {\n" +
                            "    public static Integer staticVar;\n" +
                            "    public Integer nonStaticVar;\n" +
                            "}",
                    "fill",
                    "Output:\n" +
                            " \n" +
                            "10\n" +
                            "null\n" +
                            "30\n" +
                            "30\n\n" +
                            "Notice how we changed the value to 30, and both objects now see the updated value which is 30."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Variable",
                    "Another thing you should have noticed that how we are able to access static variable with its classname i.e. DataObject.staticVar. We don’t need to create any instance to access static variables. It clearly shows that static variables belong to class scope."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Method",
                    "To declare a static method, use static keyword in method declaration. Static method syntax is:",
                    "ACCESS_MODIFER static RETURN_TYPE METHOD_NAME;", "random"
            ))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Method",
                    "For example, a public static variable of Integer type is declared in this way.",
                    "public static Integer getStaticVar(){\n" +
                            "    return staticVar;\n" +
                            "}"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Method",
                    "Few things to remember.\n" +
                            "\n" +
                            "1. You can access only static variables inside static methods. If you try to access any non-static variable, the compiler error will be generated with message “Cannot make a static reference to the non-static field nonStaticVar“.\n" +
                            "2. Static methods can be accessed via it’s class reference, and there is no need to create an instance of class. Though you can access using instance reference as well but it will have not any difference in comparison to access via class reference.\n" +
                            "3. Static methods also belong to class level scope.",
                    "public class JavaStaticExample \n" +
                            "{\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        DataObject.staticVar = 30;  //Direct Access\n" +
                            "         \n" +
                            "        Integer value1 = DataObject.getStaticVar(); //access with class reference\n" +
                            " \n" +
                            "        DataObject objOne = new DataObject();\n" +
                            "        Integer value2 = objOne.getStaticVar();     //access with instance reference\n" +
                            "         \n" +
                            "        System.out.println(value1);\n" +
                            "        System.out.println(value2);\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class DataObject \n" +
                            "{\n" +
                            "    public Integer nonStaticVar;\n" +
                            "    public static Integer staticVar;    //static variable\n" +
                            "     \n" +
                            "    public static Integer getStaticVar(){\n" +
                            "        return staticVar;\n" +
                            "    }\n" +
                            "}",
                    "fill",
                    "Output:\n" +
                            " \n" +
                            "30\n" +
                            "30"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Import Statement",
                    "The normal import declaration imports classes from packages, so that they can be used without package reference. Similarly the static import declaration imports static members from classes and allowing them to be used without class reference.\n" +
                            "\n" +
                            "A static import declaration also comes in two flavors: single-static import and static-import-on-demand. A single-static import declaration imports one static member from a type. A static-import-on-demand declaration imports all static members of a type.",
                    "//Single-static-import declaration:\n" +
                            "  \n" +
                            "import static <<package name>>.<<type name>>.<<static member name>>;\n" +
                            "  \n" +
                            "//Static-import-on-demand declaration:\n" +
                            "  \n" +
                            "import static <<package name>>.<<type name>>.*;"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Method",
                    "For example, a public static variable of Integer type is declared in this way.",
                    "public static Integer getStaticVar(){\n" +
                            "    return staticVar;\n" +
                            "}"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Method",
                    "For example, System.out is",
                    "//Static import statement\n" +
                            "import static java.lang.System.out;\n" +
                            " \n" +
                            "public class JavaStaticExample \n" +
                            "{\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        DataObject.staticVar = 30;  \n" +
                            " \n" +
                            "        out.println(DataObject.staticVar);  //Static import statement example\n" +
                            "    }\n" +
                            "}\n" +
                            "class DataObject \n" +
                            "{\n" +
                            "    public static Integer staticVar;    //static variable\n" +
                            "}",
                    "fill",
                    "Output:\n" +
                            " \n" +
                            "30"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Block",
                    "Static blocks are portion of class initialization code, which are wrapped with static keyword. General syntax is:",
                    "static {\n" +
                            "    //initialize static members of class\n" +
                            "}"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Block",
                    "Static blocks are executed when the class is loaded in the memory. A class can have multiple static blocks and these will be executed in the same sequence in which they appear in class definition.",
                    "import static java.lang.System.out;\n" +
                            " \n" +
                            "class DataObject \n" +
                            "{\n" +
                            "    public Integer nonStaticVar;\n" +
                            "    public static Integer staticVar;    //static variable\n" +
                            "     \n" +
                            "    //It will be executed first\n" +
                            "    static {\n" +
                            "        staticVar = 40;\n" +
                            "        //nonStaticVar = 20;    //Not possible to access non-static members\n" +
                            "    }\n" +
                            "     \n" +
                            "    //It will be executed second\n" +
                            "    static {\n" +
                            "        out.println(staticVar);\n" +
                            "    }\n" +
                            "}",
                    "fill",
                    "Output:\n" +
                            " \n" +
                            "40"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Class",
                    "In java, you can have a static class as inner class. Just like other static members, nested classed belong with class scope so the inner static class can be accessed without having an object of outer class.",
                    "public class JavaStaticExample \n" +
                            "{\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        //Static inner class example\n" +
                            "        System.out.println( DataObject.StaticInnerClas.innerStaticVar );\n" +
                            "    }\n" +
                            "}\n" +
                            "class DataObject \n" +
                            "{\n" +
                            "    public Integer nonStaticVar;\n" +
                            "    public static Integer staticVar;    //static variable\n" +
                            "     \n" +
                            "    static class StaticInnerClas {\n" +
                            "        Integer innerNonStaticVar = 60; \n" +
                            "        static Integer innerStaticVar = 70;     //static variable inside inner class\n" +
                            "    }\n" +
                            "}", "fill"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Static Class",
                    "Please note that an static inner class cannot access the non-static members of outer class. It can access only static members from outer class.",
                    "public class JavaStaticExample \n" +
                            "{\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        //Static inner class example\n" +
                            "        DataObject.StaticInnerClas.accessOuterClass();\n" +
                            "    }\n" +
                            "}\n" +
                            "class DataObject \n" +
                            "{\n" +
                            "    public Integer nonStaticVar;\n" +
                            "    public static Integer staticVar;    //static variable\n" +
                            "         \n" +
                            "    static {\n" +
                            "        staticVar = 40;\n" +
                            "        //nonStaticVar = 20;    //Not possible to access non-static members\n" +
                            "    }\n" +
                            " \n" +
                            "    public static Integer getStaticVar(){\n" +
                            "        return staticVar;\n" +
                            "    }\n" +
                            "     \n" +
                            "    static class StaticInnerClas \n" +
                            "    {   \n" +
                            "        public static void accessOuterClass()\n" +
                            "        {\n" +
                            "            System.out.println(DataObject.staticVar);       //static variable of outer class\n" +
                            "            System.out.println(DataObject.getStaticVar());  //static method of outer class\n" +
                            "        }\n" +
                            "    }\n" +
                            "}",
                    "fill",
                    "Output:\n" +
                            " \n" +
                            "40"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Summary",
                    "Let’s summarize everything about static usage in java.\n" +
                            "\n" +
                            "1. Static members belong to class. No need to create class instance to access static members.\n\n" +
                            "2. Static members (variables and methods) can be accessed inside static methods and static blocks only.\n\n" +
                            "3. Non-static members cannot be accessed inside static methods, blocks and inner classes.\n\n" +
                            "4. A class can have multiple static blocks and they will be executed in order they appear in class definition.\n\n" +
                            "5. A class can be static only if its declared as inner class inside outer class.\n\n" +
                            "6. Static imports can be used to import all static members from a class. These members can be referred without any class reference."))

            lesson.bitModules = bitModules

            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Constructors - Object Oriented Principles"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Constructors",
                    "Java constructors are special methods (without return type) which allow you to fully initialize the object state before it can be used by other classes inside application. Constructors in java are invoked using new keyword.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What are Constructors",
                    "Constructors are special method like (no exactly methods) constructs which helps programmer in writing object initialization code, before the object is available for use by other objects in the application.\n" +
                            "\n" +
                            "Whenever application needs a new instance of any class, JVM allocates a memory area inside heap. Then JVM executes the invoked constructor (class can have multiple constructors) and initialize the object state. Inside constructor, you can access all object attributes and assign them to their default values or any desired values.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Initializing a constructor - \"new\" keyword",
                    "Initializing an Object\n" +
                            "\n" +
                            "Here's the code for the Point class:\n" +
                            "\n",

                    "public class Point {\n" +
                            "    public int x = 0;\n" +
                            "    public int y = 0;\n" +
                            "    //constructor\n" +
                            "    public Point(int a, int b) {\n" +
                            "        x = a;\n" +
                            "        y = b;\n" +
                            "    }\n" +
                            "}\n",
                    "fill",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Default Constructor (no-arg constructor)",
                    "In case, programmer does not provide any constructor in class definition – JVM provides a default constructor to the class in runtime.\n" +
                            "\n" +
                            "Programmer also can override default constructor in class. Let’s look at the syntax.",
                    "public class Employee \n" +
                            "{   \n" +
                            "    public Employee() {\n" +
                            "         \n" +
                            "    }\n" +
                            "}",
                    "",
                    "In default constructor, name of the constructor MUST match the class name and it should not have any parameters."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Parameterized Constructor via Constructor Overloading",
                    "As stated above, there can be multiple constructors inside a class. This is possible by having overloaded constructors. In constructor overloading, you can pass list of arguments as per requirements i.e. how many ways a class can be initialized.",
                    "public class Employee {\n" +
                            "    private String firstName;\n" +
                            "    private String lastName;\n" +
                            " \n" +
                            "    public Employee() { //constructor 1\n" +
                            " \n" +
                            "    }\n" +
                            " \n" +
                            "    public Employee(String firstName) { //constructor 2\n" +
                            " \n" +
                            "    }\n" +
                            " \n" +
                            "    public Employee(String firstName, String lastName) { //constructor 3\n" +
                            " \n" +
                            "    }\n" +
                            "}",
                    "",
                    "In above class, we have defined 3 constructors to handle 3 situations – how the employee object might be required to create by the application i.e. without name, with first name only and with first and last name both.\n" +
                            "Employee employee1 = new Employee();\n" +
                            "Employee employee2 = new Employee(\"Mark\");\n" +
                            "Employee employee3 = new Employee(\"Mark\", \"Wahlberg\");"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Constructor Rules",
                    "There are few mandatory rules for creating the constructors in java.\n" +
                            "\n" +
                            "\n1. Constructor name MUST be same as name of the class.\n" +
                            "\n2. There cannot be any return type in constructor definition.\n" +
                            "\n3. There cannot be any return statement in constructor.\n" +
                            "\n4. Constructors can be overloaded by different arguments.\n" +
                            "\n5. If you want to use super() i.e. super class constructor then it must be first statement inside constructor.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Constructor Chaining",
                    "In java, it’s possible to call other constructors inside a constructor. It’s just like method calling but without any reference variable (obviously as instance is fully initialized as of now).\n" +
                            "\n" +
                            "Now we can call constructors of either same class or of parent class. Both uses different syntax.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Call same class constructor",
                    "To call other constructors from same class, use this keyword. For example,",
                    "public Employee() { \n" +
                            "     \n" +
                            "}\n" +
                            " \n" +
                            "public Employee(String firstName) { \n" +
                            "    this();     //calling default constructor\n" +
                            "}\n" +
                            " \n" +
                            "public Employee(String firstName, String lastName) {\n" +
                            "    this(firstName);    //calling constructor with single argument of String type\n" +
                            "}",
                    "fill",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Call super class constructor",
                    "To call constructors from super or parent class, use super keyword. The usage of super keyword is similar to this keyword – only difference is that super refers to super class and this refers to current instance.",
                    "public Employee() { \n" +
                            "    //refer to Object class constructor \n" +
                            "    //as it is parent class for every class\n" +
                            "    super();    \n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Private Constructors",
                    "Sometimes you want to protect the constructor from being called by other classes. Altogether you want that nobody should be able to create a new instance of the class.\n" +
                            "\n" +
                            "Why anybody would want that? Well, it’s necessary for singleton pattern. In singleton, an application wants to have one and only one instance of any class.\n" +
                            "\n" +
                            "A common singleton class definition looks like this:",
                    "public class DemoSingleton implements Serializable \n" +
                            "{\n" +
                            "    private static final long serialVersionUID = 1L;\n" +
                            "  \n" +
                            "    private DemoSingleton() {\n" +
                            "        // private constructor\n" +
                            "    }\n" +
                            "  \n" +
                            "    private static class DemoSingletonHolder {\n" +
                            "        public static final DemoSingleton INSTANCE = new DemoSingleton();\n" +
                            "    }\n" +
                            "  \n" +
                            "    public static DemoSingleton getInstance() {\n" +
                            "        return DemoSingletonHolder.INSTANCE;\n" +
                            "    }\n" +
                            "  \n" +
                            "    protected Object readResolve() {\n" +
                            "        return getInstance();\n" +
                            "    }\n" +
                            "}",
                    "fill",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Inheritance : Object Oriented"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Inheritance",
                    "Inheritance in java (IS-A relationship) is referred to the ability where child objects inherit or acquire all the properties and behaviors from parent object. In object oriented programming, inheritance is used to promote the code re-usability.\n" +
                            "\n" +
                            "In this tutorial, we will learn about inheritance types and how inheritance is achieved in java programming.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Inheritance in detail",
                    "As said before, inheritance is all about inheriting the common state and behavior of parent class (super class) by it’s derived class (sub class or child class). A sub class can inherit all non-private members from super class, by default.\n" +
                            "\n" +
                            "In java, extends keyword is used for inheritance between classes. let’s see a quick inheritance example.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Java Inheritance Example",
                    "Let’s say we have Employee class. This class has all common attributes and methods which all employees must have within organization. There can be other specialized employees as well e.g. Manager. Managers are regular employees of organization but, additionally, they have few more attributes over other employees e.g. they have reportees or subordinates.\n" +
                            "\n" +
                            "Let’s design above classes.\n" +
                            "\n" +
                            "Employee.java",
                    "Employee.java\n" +
                            "\n" +
                            "public class Employee \n" +
                            "{   \n" +
                            "    private Long id;\n" +
                            "    private String firstName;\n" +
                            "    private String lastName;\n" +
                            "     \n" +
                            "    public Long getId() {\n" +
                            "        return id;\n" +
                            "    }\n" +
                            "    public void setId(Long id) {\n" +
                            "        this.id = id;\n" +
                            "    }\n" +
                            "    public String getFirstName() {\n" +
                            "        return firstName;\n" +
                            "    }\n" +
                            "    public void setFirstName(String firstName) {\n" +
                            "        this.firstName = firstName;\n" +
                            "    }\n" +
                            "    public String getLastName() {\n" +
                            "        return lastName;\n" +
                            "    }\n" +
                            "    public void setLastName(String lastName) {\n" +
                            "        this.lastName = lastName;\n" +
                            "    }\n" +
                            "    @Override\n" +
                            "    public String toString() {\n" +
                            "        return \"Employee [id=\" + id + \", firstName=\" + firstName + \", lastName=\" + lastName + \"]\";\n" +
                            "    }\n" +
                            "}\n" +
                            "Manager.java\n" +
                            "\n" +
                            "import java.util.List;\n" +
                            " \n" +
                            "public class Manager extends Employee \n" +
                            "{\n" +
                            "    private List<Employee> subordinates;\n" +
                            " \n" +
                            "    public List<Employee> getSubordinates() {\n" +
                            "        return subordinates;\n" +
                            "    }\n" +
                            " \n" +
                            "    public void setSubordinates(List<Employee> subordinates) {\n" +
                            "        this.subordinates = subordinates;\n" +
                            "    }\n" +
                            " \n" +
                            "    @Override\n" +
                            "    public String toString() {\n" +
                            "        return \"Manager [subordinates=\" + subordinates + \", details=\" + super.toString() + \"]\";\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "How it works?",
                    "In previous implementation, employees have common attributes like id, first name and last name; while manager has it’s specialized subordinates attribute only. To inherit all non-private members from Employee class (in this case getter and setter methods), Manager extends Employee is used.\n" +
                            "\n" +
                            "Let’s see how it works?",
                    "public class Main {\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        Manager mgr = new Manager();\n" +
                            "        mgr.setId(1L);\n" +
                            "        mgr.setFirstName(\"Mark\");\n" +
                            "        mgr.setLastName(\"Wahlberg\");\n" +
                            "         \n" +
                            "        System.out.println(mgr);\n" +
                            "    }\n" +
                            "}",
                    "Fill",
                    "Output:\n" +
                            " \n" +
                            "Manager [subordinates=null, details=Employee [id=1, firstName=Mark, lastName=Wahlberg]]\n\n" +
                            "Clearly, Manager class is able to use members of Employee class. This very behavior is called inheritance. Simple, isn’t it?\n" +
                            "\n" +
                            "Now consider if we do not use inheritance. Then we would have defined id, firstName and lastName in both classes. It would have caused code duplication which always create problems in code maintenance."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Types of Inheritance in Java",
                    "In java, inheritance can be one of four types – depending on classes hierarchy. Let’s learn about all four types of inheritances.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Single Inheritance",
                    "This one is simple. There is one Parent class and one Child class. One child class extends one parent class. It’s single inheritance. The above example code (employee and manager) is example of single inheritance.",
                    "",
                    "",
                    "",
                    "http://howtodoinjava.com/wp-content/uploads/2017/06/Single-Inheritance-Example.png"))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Multilevel Inheritance",
                    "In multilevel inheritance, there will be inheritance between more than three classes in such a way that a child class will act as parent class for another child class. Let’s understand with a diagram.",
                    "",
                    "",
                    "In above example, Class B extends class A, so class B is child class of class A. But C extends B, so B is parent class of C. So B is parent class as well as child class also.", "http://howtodoinjava.com/wp-content/uploads/2017/06/Multilevel-Inheritance.png"))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Hierarchical Inheritance",
                    "In hierarchical inheritance, there is one super class and more than one sub classes extend the super class.",
                    "",
                    "",
                    "These subclasses B, C, D will share the common members inherited from A, but they will not be aware of members from each other.",
                    "http://howtodoinjava.com/wp-content/uploads/2017/06/Hierarchical-Inheritance.png"))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Multiple inheritance",
                    "In multiple inheritance, a class can inherit the behavior from more than one parent classes as well. Let’s understand with diagram.",
                    "",
                    "",
                    "In diagram, D is extending class A and B, both. In this way, D can inherit the non-private members of both the classes.\n" +
                            "\n" +
                            "BUT, in java, you cannot use extends keyword with two classes. So, how multiple inheritance will work?\n" +
                            "\n" +
                            "Till JDK 1.7, multiple inheritance was not possible in java. But from JDK 1.8 onwards, multiple inheritance is possible via use of interfaces with default methods.", "http://howtodoinjava.com/wp-content/uploads/2017/06/Multiple-inheritance.png"))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Hierarchical Inheritance",
                    "In hierarchical inheritance, there is one super class and more than one sub classes extend the super class.",
                    "",
                    "",
                    "These subclasses B, C, D will share the common members inherited from A, but they will not be aware of members from each other.",
                    "http://howtodoinjava.com/wp-content/uploads/2017/06/Hierarchical-Inheritance.png"))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Accessing Inherited Super Class Members",
                    "Now we know that using four types of inheritance mechanisms, we can access non-private members of parent classes. Let’s see how individual member can be accessed.",
                    "",
                    "",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Constructors",
                    "Constructors of super class can be called via super keyword. There are only two rules:\n" +
                            "\n" +
                            "\n1. super() call must be made from child class constructor.\n" +
                            "\n2. super() call must be first statement inside constructor.",
                    "public class Manager extends Employee \n" +
                            "{\n" +
                            "    public Manager() \n" +
                            "    {\n" +
                            "        //This must be first statement inside constructor\n" +
                            "        super();\n" +
                            "         \n" +
                            "        //Other code after super class\n" +
                            "    }\n" +
                            "}",
                    "",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Fields",
                    "In java, non-private member fields can be inherited in child class. You can access them using dot operator e.g. manager.id. Here id attribute is inherited from parent class Employee.\n" +
                            "\n" +
                            "You need to be careful when dealing with fields with same name in parent and child class. Remember that java fields cannot be overridden. Having same name field will hide the field from parent class – while accessing via child class.",
                    "//In this case, attribute accessed will be decided based on the class of reference type.\n" +
                            "\n" +
                            "ReferenceClass variable = new ActualClass();\n" +
                            "//In above case, member field will be accessed from ReferenceClass. e.g.\n" +
                            "\n" +
                            "//Parent class\n" +
                            "public class Employee \n" +
                            "{   \n" +
                            "    public Long id = 10L;\n" +
                            "}\n" +
                            " \n" +
                            "//Child class\n" +
                            "public class Manager extends Employee \n" +
                            "{\n" +
                            "    public Long id = 20L;   //same name field\n" +
                            "}\n" +
                            " \n" +
                            "public class Main {\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        Employee manager = new Manager();\n" +
                            "        System.out.println(manager.id);     //Reference of type Employee\n" +
                            "         \n" +
                            "        Manager mgr = new Manager();\n" +
                            "        System.out.println(mgr.id);     //Reference of type Manager\n" +
                            "    }\n" +
                            "}",
                    "",
                    "Output:\n" +
                            " \n" +
                            "10\n" +
                            "20",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Methods",
                    "Opposite to field access, method access uses the type of actual object created in runtime.\n" +
                            "\n" +
                            "java]ReferenceClass variable = new ActualClass();[/java]\n" +
                            "\n" +
                            "In above case, member method will be accessed from ActualClass. e.g.",
                    "public class Employee \n" +
                            "{   \n" +
                            "    private Long id = 10L;\n" +
                            "     \n" +
                            "    public Long getId() {\n" +
                            "        return id;\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "public class Manager extends Employee \n" +
                            "{\n" +
                            "    private Long id = 20L;\n" +
                            " \n" +
                            "    public Long getId() {\n" +
                            "        return id;\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "public class Main \n" +
                            "{\n" +
                            "    public static void main(String[] args) \n" +
                            "    {\n" +
                            "        Employee employee = new Employee();     //Actual object is Employee Type\n" +
                            "        System.out.println(employee.getId());\n" +
                            "         \n" +
                            "        Employee manager = new Manager();       //Actual object is Manager Type\n" +
                            "        System.out.println(manager.getId());\n" +
                            "         \n" +
                            "        Manager mgr = new Manager();       //Actual object is Manager Type\n" +
                            "        System.out.println(mgr.getId());\n" +
                            "    }\n" +
                            "}",
                    "Fill",
                    "Output:\n" +
                            " \n" +
                            "10\n" +
                            "20\n" +
                            "20",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Summary",
                    "Let’s summarize what we learned about java inheritance:\n" +
                            "\n" +
                            "\n* Inheritance is also known IS-A relationship.\n" +
                            "\n* It provides child class the ability to inherit non-private members of parent class.\n" +
                            "\n* In java, inheritance is achieved via extends keyword.\n" +
                            "\n* From Java 8 onward, you can use interfaces with default methods to achieve multiple inheritance.\n" +
                            "\n* Member fields are accessed from reference type class.\n" +
                            "\n* Member methods are accessed from actual instance types.",
                    "",
                    "",
                    "",
                    ""))
            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "OOPs Concepts : Abstraction\n" +
                    "Encapsulation\n" +
                    "Inheritance\n" +
                    "Polymorphism"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Abstraction",
                    "Abstraction is very easy to understand when you relate it to read world scenario. For example, when you drive your car you do not have to be concerned with the exact internal working of your car. What you are concerned with is interacting with your car via its interfaces like steering wheel, brake pedal, accelerator pedal etc. Here the knowledge you have of your car is abstract.\n" +
                            "\n" +
                            "In computer science, abstraction is the process by which data and programs are defined with a representation similar in form to its meaning (semantics), while hiding away the implementation details.\n" +
                            "\n" +
                            "In more simple terms, abstraction is to hide information that is not relevant or rather show only relevant information and to simplify it by comparing it to something similar in the real world.",
                    "",
                    "",
                    "Abstraction captures only those details about an object that are relevant to the current perspective.",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Two Ways of Abstraction",
                    "1. Data abstraction\n" +
                            "\n" +
                            "Data abstraction is the way to create complex data types from multiple smaller data types – which is more close to real life entity. e.g. An Employee class can be a complex object of having various small associations.",
                    "public class Employee\n" +
                            "{\n" +
                            "    private Department department;\n" +
                            "    private Address address;\n" +
                            "    private Education education;\n" +
                            "    //So on...\n" +
                            "}",
                    "",
                    "So, if you want to fetch information of a employee, you ask it from Employee object – as you do in real life, ask the person itself.",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "2nd Way",
                    "Control abstraction\n" +
                            "\n" +
                            "Control abstraction is achieved by hiding the sequence of actions for a complex task – inside a simple method call, so logic to perform the task can be hidden from client and could be changed in future without impacting the client code.",
                    "public class EmployeeManager\n" +
                            "{\n" +
                            "    public Address getPrefferedAddress(Employee e)\n" +
                            "    {\n" +
                            "        //Get all addresses from database\n" +
                            "        //Apply logic to determine which address is preferred\n" +
                            "        //Return address\n" +
                            "    }\n" +
                            "}",
                    "",
                    "In above example, tomorrow if you want to change the logic so that everytime domestic address is always the preferred address, you will change the logic inside getPrefferedAddress() method, and client will be unaffected.",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Encapsulation",
                    "Wrapping data and methods within classes in combination with implementation hiding (through access control) is often called encapsulation. The result is a data type with characteristics and behaviors. Encapsulation essentially has both i.e. information hiding and implementation hiding.\n\n" +
                            "Information hiding is done through using access control keywords (public, private, protected) and implementation hiding is achieved through creation of interface for a class. Information hiding gives the designer the freedom to modify how the responsibility is fulfilled by a object. This is especially valuable at points where the design (or even the requirements) are likely to change.\n" +
                            "\n" +
                            "Let’s take an example to make it more clear.",
                    "//Information hiding\n" +
                            "\n" +
                            "class InformationHiding\n" +
                            "{\n" +
                            "    //Restrict direct access to inward data\n" +
                            "    private ArrayList items = new ArrayList();\n" +
                            " \n" +
                            "    //Provide a way to access data - internal logic can safely be changed in future\n" +
                            "    public ArrayList getItems(){\n" +
                            "        return items;\n" +
                            "    }\n" +
                            "}\n" +
                            "//Implementation hiding\n" +
                            "\n" +
                            "interface ImplemenatationHiding {\n" +
                            "    Integer sumAllItems(ArrayList items);\n" +
                            "}\n" +
                            "class InformationHiding implements ImplemenatationHiding\n" +
                            "{\n" +
                            "    //Restrict direct access to inward data\n" +
                            "    private ArrayList items = new ArrayList();\n" +
                            " \n" +
                            "    //Provide a way to access data - internal logic can safely be changed in future\n" +
                            "    public ArrayList getItems(){\n" +
                            "        return items;\n" +
                            "    }\n" +
                            " \n" +
                            "    public Integer sumAllItems(ArrayList items) {\n" +
                            "        //Here you may do N number of things in any sequence\n" +
                            "        //Which you do not want your clients to know\n" +
                            "        //You can change the sequence or even whole logic\n" +
                            "        //without affecting the client\n" +
                            "    }\n" +
                            "}",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Inheritance",
                    "Inheritance in java is a mechanism in which one object acquires the properties and behaviors of parent object. It’s essentially creating parent-child relationship between classes. In java, you will use inheritance mainly for code re-usability and maintainability.\n" +
                            "\n" +
                            "Keyword “extends” is used to inherit a class in java. The “extends” keyword indicates that you are making a new class that derives from an existing class. In the terminology of Java, a class that is inherited is called a super class. The new class is called a subclass.\n" +
                            "\n" +
                            "A subclass inherits all the non-private members (fields, methods, and nested classes) from its superclass. Constructors are not members, so they are not inherited by subclasses, but the constructor of the superclass can be invoked from the subclass.",
                    "class Employee\n" +
                            "{\n" +
                            "    private Department department;\n" +
                            "    private Address address;\n" +
                            "    private Education education;\n" +
                            "    //So on...\n" +
                            "}\n" +
                            "class Manager extends Employee {\n" +
                            "    private List<Employee> reportees;\n" +
                            "}",
                    "",
                    "In above example, Manager is specialized version of Employee and reuses department, address and education from Employee class as well as define it’s own reportees list.",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Polymorphism",
                    "Polymorphism is the ability by which, we can create functions or reference variables which behaves differently in different programmatic context.\n" +
                            "\n" +
                            "In java language, polymorphism is essentially considered into two versions:\n" +
                            "\n" +
                            "1. Compile time polymorphism (static binding or method overloading)\n" +
                            "2. Runtime polymorphism (dynamic binding or method overriding)",
                    "",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Polymorphism - Example",
                    "In simple words, polymorphism is the ability by which, we can create functions or reference variables which behaves differently in different programmatic context.\n" +
                            "\n" +
                            "Polymorphism is one of the major building blocks of object oriented programming along with inheritance, abstraction and encapsulation.\n" +
                            "\n" +
                            "“Subtype polymorphism, often referred to as simply polymorphism in the context of object-oriented programming, is the ability to create a variable, a function, or an object that has more than one form.” – Wikipedia",
                    "",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Polymorphism in Java",
                    "An example of polymorphism is referring the instance of subclass, with reference variable of super-class. e.g.",
                    "Object o = new Object(); //o can hold the reference of any subtype\n" +
                            "Object o = new String();\n" +
                            "Object o = new Integer();",
                    "",
                    "Here, String is subclass of Object class. This is basic example of polymorphism.\n" +
                            "\n" +
                            "In java language, polymorphism is essentially considered into two versions.\n" +
                            "\n" +
                            "1. Compile time polymorphism (static binding or method overloading)\n" +
                            "2. Runtime polymorphism (dynamic binding or method overriding)",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Compile Time Polymorphism (static binding or method overloading)",
                    "As the meaning is implicit, this is used to write the program in such a way, that flow of control is decided in compile time itself. It is achieved using method overloading.\n" +
                            "\n" +
                            "In method overloading, an object can have two or more methods with same name, BUT, with their method parameters different. These parameters may be different on two bases:",
                    "",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Parameters type",
                    "Type of method parameters can be different. e.g. java.util.Math.max() function comes with following versions:",
                    "public static double Math.max(double a, double b){..}\n" +
                            "public static float Math.max(float a, float b){..}\n" +
                            "public static int Math.max(int a, int b){..}\n" +
                            "public static long Math.max(long a, long b){..}",
                    "",
                    "The actual method to be called is decided on compile time based on parameters passed to function in program.",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Parameters count",
                    "Functions accepting different number of parameters. e.g. in employee management application, a factory can have these methods:",
                    "EmployeeFactory.create(String firstName, String lastName){...}\n" + "EmployeeFactory.create(Integer id, String firstName, String lastName){...}",
                    "",
                    "Both methods have same name “create” but actual method invoked will be based on parameters passed in program.",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Runtime Polymorphism (dynamic binding or method overriding)",
                    "Runtime polymorphism is essentially referred as method overriding. Method overriding is a feature which you get when you implement inheritance in your program.\n" +
                            "\n" +
                            "A simple example can be from real world e.g. animals. An application can have Animal class, and its specialized sub classes like Cat and Dog. These subclasses will override the default behavior provided by Animal class + some of its own specific behavior.",
                    "public class Animal {\n" +
                            "    public void makeNoise()\n" +
                            "    {\n" +
                            "        System.out.println(\"Some sound\");\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class Dog extends Animal{\n" +
                            "    public void makeNoise()\n" +
                            "    {\n" +
                            "        System.out.println(\"Bark\");\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class Cat extends Animal{\n" +
                            "    public void makeNoise()\n" +
                            "    {\n" +
                            "        System.out.println(\"Meawoo\");\n" +
                            "    }\n" +
                            "}\n" +
                            "//Now which makeNoise() method will be called, depends on type of actual instance created on runtime e.g.\n" +
                            "\n" +
                            "public class Demo\n" +
                            "{\n" +
                            "    public static void main(String[] args) {\n" +
                            "        Animal a1 = new Cat();\n" +
                            "        a1.makeNoise(); //Prints Meowoo\n" +
                            "         \n" +
                            "        Animal a2 = new Dog();\n" +
                            "        a2.makeNoise(); //Prints Bark\n" +
                            "    }\n" +
                            "}",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Important points",
                    "1. Polymorphism is the ability to create a variable, a function, or an object that has more than one form.\n\n" +
                            "2. In java, polymorphism is divided into two parts : method overloading and method overriding.\n\n" +
                            "3. Some may argue that method overloading is not polymorphism. Then what does the term compile time “polymorphism” means??\n\n" +
                            "4. Another term operator overloading is also there, e.g. “+” operator can be used to add two integers as well as concat two sub-strings. Well, this is the only available support for operator overloading in java, and you can not have your own custom defined operator overloading in java.\n\n",
                    "",
                    "",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)



            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Java Access Modifiers"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Access Modifiers",
                    "Java provides 4 access modifiers to set access levels for classes, variables, methods and constructors i.e. public, private, protected and default. Access level modifiers determine whether other classes can use a particular field or invoke a particular method.\n\n" +
                            "Let’s quickly compare these access modifiers in nutshell.\n" +
                            "\n" +
                            "1. public – accessible everywhere\n" +
                            "2. protected – accessible in the same package and in sub-classes\n" +
                            "3. default – accessible only in the same package\n" +
                            "4. private – accessible only in the same class\n" +
                            "\n\nThe access specifiers can be strictly ordered as below :\n" +
                            "\n",

                    "",
                    "",
                    "public > protected > package-private (or default) > private"))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "public",
                    "Public are accessible from everywhere. A class, method, constructor, interface etc declared public can be accessed from any other class.\n" +
                            "\n" +
                            "However if the public class we are trying to access is in a different package, then the public class still need to be imported.",
                    "public class HelloWorld\n" +
                            "{\n" +
                            "   private String format;\n" +
                            " \n" +
                            "   public String getFormat() {\n" +
                            "      return this.format;\n" +
                            "   }\n" +
                            "   public void setFormat(String format) {\n" +
                            "      this.format = format;\n" +
                            "   }\n" +
                            "}",
                    "",
                    "In above example, getFormat() and setFormat() methods are public, so they can be accessed anywhere."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "protected",
                    "Protected are accessible by the classes of the same package and the subclasses residing in any package. Protected access gives the subclass a chance to use the helper method or variable, while preventing a non-related class from trying to use it.",
                    "public class HelloWorld\n" +
                            "{\n" +
                            "   private String format;\n" +
                            " \n" +
                            "   protected String getFormat() {\n" +
                            "      return this.format;\n" +
                            "   }\n" +
                            "   protected void setFormat(String format) {\n" +
                            "      this.format = format;\n" +
                            "   }\n" +
                            "}",
                    "",
                    "In above given example of HelloWorld, variable format is declared protected, so it can be accessed by all the classes present in same package where HelloWorld.java is present, as well as sub-classes present in other packages as well."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "default (Package Private)",
                    "Default access modifier means we do not explicitly declare an access modifier for a class, field, method, etc. Default are accessible by the classes of the same package.\n" +
                            "\n" +
                            "Please note that the fields in an interface are implicitly public static final and the methods in an interface are by default public.",
                    "public class HelloWorld\n" +
                            "{\n" +
                            "   String format;\n" +
                            " \n" +
                            "   public String getFormat() {\n" +
                            "      return this.format;\n" +
                            "   }\n" +
                            "   public void setFormat(String format) {\n" +
                            "      this.format = format;\n" +
                            "   }\n" +
                            "}",
                    "",
                    "In above given example of HelloWorld, variable format is declared default, so it can be accessed by all the classes present in same package where HelloWorld.java is present."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "private",
                    "Private access modifier is the most restrictive access level. (Top most) Classes and interfaces cannot be private. private members are accessible within the same class only. Methods, Variables and Constructors that are declared private can only be accessed within the declared class itself.\n" +
                            "\n" +
                            "In above given example of HelloWorld, variable format is declared private, so no class can access it directly. It must be accessed though public methods getFormat() and setFormat().\n" +
                            "\n" +
                            "Access levels affect you in two ways. First, when you use classes that come from another source, access levels determine which members of those classes your own classes can use. Second, when you write a class, you need to decide what access level every member variable and every method in your class should have.\n" +
                            "\n" +
                            "Local variables and formal parameters cannot take access specifiers. Since they are inherently inaccessible to the outside according to scoping rules, they are effectively private.\n" +
                            "\n" +
                            "If other programmers use your class, you want to ensure that errors from misuse cannot happen. Access levels can help you do this.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Levels of Access Control",
                    "There are two levels of access control.\n" +
                            "\n" +
                            "1. Class level — Allowed modifiers are public, or package-private (default).\n" +
                            "2. Method level — Allowed modifiers are public, private, protected, or package-private (default).\n" +
                            "A class may be declared with the modifier public, in which case that class is visible to all classes everywhere. If a class has no modifier (the default, also known as package-private), it is visible only within its own package.\n" +
                            "\n" +
                            "For members, there are two additional access modifiers: private and protected. The private modifier specifies that the member can only be accessed in its own class. The protected modifier specifies that the member can only be accessed within its own package (as with package-private) and, in addition, by a subclass of its class in another package.",
                    "",
                    "",
                    "Both private and protected can be (and frequently are) applied to nested classes and interfaces, just never top-level classes and interfaces."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Instances of same class can access private members of each other",
                    "The official doc and most of reference variables talk about using these access modifiers for controlling the access level “when used in some other class”. Most of us misinterpret this information wrongly, and start thinking in terms of instances of separate class.\n" +
                            "\n" +
                            "\n" +
                            "Remember, access control is compile time feature and checked when you compile your program. It is applied at class level and not at instance level.\n" +
                            "To establish this fact, let’s create two instances of a class and try to access the private members of each other.",
                    "public class AccessControlDemo\n" +
                            "{\n" +
                            "    //Private member variable\n" +
                            "    private String privateMemberVariable = null;\n" +
                            "     \n" +
                            "    //Private member method\n" +
                            "    private String privateMethod(){\n" +
                            "        return privateMemberVariable;\n" +
                            "    }\n" +
                            "     \n" +
                            "    public AccessControlDemo(String str) {\n" +
                            "        privateMemberVariable = str;\n" +
                            "    }\n" +
                            "     \n" +
                            "    public void demoAccessOtherClass(AccessControlDemo otherInstance)\n" +
                            "    {\n" +
                            "        //Access private members of second instance\n" +
                            "        System.out.println(\"Private member variable :\" + otherInstance.privateMemberVariable);\n" +
                            "        System.out.println(\"Private member method :\" + otherInstance.privateMethod());\n" +
                            "    }\n" +
                            "     \n" +
                            "    public static void main(String[] args) {\n" +
                            "        AccessControlDemo firstInstance = new AccessControlDemo(\"first instance\");\n" +
                            "        AccessControlDemo secondInstance = new AccessControlDemo(\"second instance\");\n" +
                            "         \n" +
                            "        firstInstance.demoAccessOtherClass(secondInstance);\n" +
                            "    }\n" +
                            "}",
                    "",
                    "Private member variable :second instance\n" +
                            "Private member method :second instance\n\n" +
                            "As you can see, we are able to access the private members of another instance from same class."))


            lesson.bitModules = bitModules
            lessons.add(lesson)

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Method Overloading vs Overriding"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overloading vs Overriding",
                    "Method overloading deals with the notion of having two or more methods in the same class with the same name but different arguments.",

                    "void foo(int a)\n" + "void foo(int a, float b)",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overriding",
                    "Method overriding means having two methods with the same arguments, but different implementations. One of them would exist in the parent class, while another will be in the derived, or child class. The @Override annotation, while not required, can be helpful to enforce proper overriding of a method at compile time.",
                    "class Parent {\n" +
                            "    void foo(double d) {\n" +
                            "        // do something\n" +
                            "    }\n" +
                            "}\n" +
                            "\n" +
                            "class Child extends Parent {\n" +
                            "\n" +
                            "    @Override\n" +
                            "    void foo(double d){\n" +
                            "        // this method is overridden.  \n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overloading Rules",
                    "Here are the rules which you keep in mind while overloading any method in java:\n" +
                            "\n" +
                            "1) First and important rule to overload a method in java is to change method signature. Method signature is made of number of arguments, type of arguments and order of arguments if they are of different types.",
                    "public class DemoClass {\n" +
                            "    // Overloaded method\n" +
                            "    public Integer sum(Integer a, Integer b) {\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            " \n" +
                            "    // Overloading method\n" +
                            "    public Integer sum(Float a, Integer b) {  //Valid\n" +
                            "        return null;\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overloading Rules",
                    "2) Return type of method is never part of method signature, so only changing the return type of method does not amount to method overloading.",
                    "public class DemoClass {\n" +
                            "    // Overloaded method\n" +
                            "    public Integer sum(Integer a, Integer b) {\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            " \n" +
                            "    // Overloading method\n" +
                            "    public Float sum(Integer a, Integer b) {     //Not valid; Compile time error\n" +
                            "        return null;\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overloading Rules",
                    "3) Thrown exceptions from methods are also not considered when overloading a method. So your overloaded method throws the same exception, a different exception or it simply does no throw any exception; no effect at all on method loading.",
                    "public class DemoClass {\n" +
                            "    // Overloaded method\n" +
                            "    public Integer sum(Integer a, Integer b) throws NullPointerException{\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            " \n" +
                            "    // Overloading method\n" +
                            "    public Integer sum(Integer a, Integer b) throws Exception{  //Not valid; Compile time error\n" +
                            "        return null;\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overriding Rules",
                    "We read above the rules for method overloading, now its time to list down the rules which you should keep remember while overriding a method in java.\n" +
                            "\n" +
                            "1) The method argument list in overridden and overriding methods must be exactly same If they don’t match, you will end up with an overloaded method.\n" +
                            "\n" +
                            "2) The return type of overriding method can be child class of return type declared in overridden method.",
                    "public class SuperClass {\n" +
                            "    //Overriden method\n" +
                            "    public Number sum(Integer a, Integer b) {\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class SubClass extends SuperClass {\n" +
                            "    //Overriding method\n" +
                            "    @Override\n" +
                            "    public Integer sum(Integer a, Integer b) {\n" +
                            "    //Integer extends Number; so it's valid\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overriding Rules",
                    "We read above the rules for method overloading, now its time to list down the rules which you should keep remember while overriding a method in java.\n" +
                            "\n" +
                            "3) Above all rules, private, static and final methods can not be overridden in java in any way. As simple as that !!",
                    "public class SuperClass {\n" +
                            "    private Integer sum(Integer a, Integer b) {   \n" +
                            "    //private method; overriding not possible\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class SubClass extends SuperClass {\n" +
                            "    //Overriding method\n" +
                            "    public Integer sum(Integer a, Integer b) {  \n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overriding Rules",
                    "4) Overriding method can not throw checked Exception higher in hierarchy than thrown by overridden method. Let’s say for example overridden method in parent class throws FileNotFoundException, the overriding method in child class can throw FileNotFoundException; but it is not allowed to throw IOException or Exception, because IOException or Exception are higher in hierarchy i.e. super classes of FileNotFoundException.\n" +
                            "\n" +
                            "More to it, you can omit the exception declaration from overriding method. It’s allowed and perfectly valid. Also overriding method can throw any unchecked (runtime) exception, regardless of whether the overridden method declares the exception.",
                    "public class SuperClass {\n" +
                            "    //Overriden method\n" +
                            "    public Integer sum(Integer a, Integer b) throws FileNotFoundException {\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class SubClass extends SuperClass {\n" +
                            "    //Overriding method\n" +
                            "    public Integer sum(Integer a, Integer b) throws IOException {       //Not valid; Compile time error\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "    //Exception IOException is not compatible with throws clause in SuperClass.sum(Integer, Integer)\n" +
                            "    public Integer sum(Integer a, Integer b)  {                     //It's valid; Don't declare the exception at all is permitted.\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Method Overriding Rules",
                    "5) Also note that overriding method can not reduce the access scope of overridden method. Put in simple words, if overridden method in parent class is protected, then overriding method in child class can not be private. It must be either protected (same access) or public (wider access).",
                    "public class SuperClass {\n" +
                            "    //Overriden method\n" +
                            "    protected Integer sum(Integer a, Integer b) {\n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "class SubClass extends SuperClass {\n" +
                            "    //Overriding method\n" +
                            "    //Not valid; Compile time error &quot;Cannot reduce the visibility of the inherited method from SuperClass&quot;\n" +
                            "    private Integer sum(Integer a, Integer b)  {   \n" +
                            "        return a + b;\n" +
                            "    }\n" +
                            "}",
                    "",
                    "Not to repeat again that method overriding is legal when talking in terms on parent classes and child classes. It does not happen within same class."))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Concurrency : the beginning"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Concurrency? What's that?",
                    "In simple words, concurrency is the ability to run several programs or several parts of a program in parallel. Concurrency enable a program to achieve high performance and throughput by utilizing the untapped capabilities of underlying operating system and machine hardware. e.g. modern computers has several CPU’s or several cores within one CPU, program can utilize all cores for some part of processing; thus completing task much before in time in comparison to sequential processing.\n" +
                            "\n" +
                            "The backbone of java concurrency are threads. A thread is a lightweight process which has its own call stack, but can access shared data of other threads in the same process. A Java application runs by default in one process. Within a Java application you can work with many threads to achieve parallel processing or concurrency.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What makes java application concurrent?",
                    "The very first class, you will need to make a java class concurrent, is java.lang.Thread class. This class is the basis of all concurrency concepts in java. Then you have java.lang.Runnable interface to abstract the thread behavior out of thread class.\n" +
                            "\n" +
                            "Other classes you will need to build advance applications can be found at java.util.concurrent package added in Java 1.5.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Is java concurrency really that simple?",
                    "Concurrent applications usually have more complex design in comparison to single threaded application. Code executed by multiple threads accessing shared data need special attention. Errors arising from incorrect thread synchronization are very hard to detect, reproduce and fix. They usually shows up in higher environments like production, and replicating the error is sometimes not possible in lower environments.\n" +
                            "\n" +
                            "Apart from complex defects, concurrency requires more resources to run the application. So make sure, you have sufficient resources in your kitty.",
                    "",
                    "",
                    ""))


            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Multi-threading Evolution",
                    "As per JDK 1.x release, there were only few classes present in this initial release. To be very specific, there classes/interfaces were:\n" +
                            "\n" +
                            "    java.lang.Thread\n" +
                            "    java.lang.ThreadGroup\n" +
                            "    java.lang.Runnable\n" +
                            "    java.lang.Process\n" +
                            "    java.lang.ThreadDeath\n" +
                            "    and some exception classes\n" +
                            "\n" +
                            "e.g.\n" +
                            "\n" +
                            "    java.lang.IllegalMonitorStateException\n" +
                            "    java.lang.IllegalStateException\n" +
                            "    java.lang.IllegalThreadStateException.\n" +
                            "\n" +
                            "It also had few synchronized collections e.g. java.util.Hashtable.\n" +
                            "\n" +
                            "JDK 1.2 and JDK 1.3 had no noticeable changes related to multi-threading. (Correct me if I have missed anything).\n" +
                            "\n" +
                            "JDK 1.4, there were few JVM level changes to suspend/resume multiple threads with single call. But no major API changes were present.\n" +
                            "\n" +
                            "JDK 1.5 was first big release after JDK 1.x; and it had included multiple concurrency utilities. Executor, semaphore, mutex, barrier, latches, concurrent collections and blocking queues; all were included in this release itself. The biggest change in java multi-threading applications cloud happened in this release.\n" +
                            "\n" +
                            "    Read full set of changes in this link: http://docs.oracle.com/javase/1.5.0/docs/guide/concurrency/overview.html\n" +
                            "\n" +
                            "JDK 1.6 was more of platform fixes than API upgrades. So new change was present in JDK 1.6.\n" +
                            "\n" +
                            "JDK 1.7 added support for ForkJoinPool which implemented work-stealing technique to maximize the throughput. Also Phaser class was added.\n" +
                            "\n" +
                            "JDK 1.8 is largely known for Lambda changes, but it also had few concurrency changes as well. Two new interfaces and four new classes were added in java.util.concurrent package e.g. CompletableFuture and CompletionException.\n" +
                            "\n" +
                            "The Collections Framework has undergone a major revision in Java 8 to add aggregate operations based on the newly added streams facility and lambda expressions; resulting in large number of methods added in almost all Collection classes, and thus in concurrent collections as well.",
                    "",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Concurrency : Thread safety"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Thread Safety",
                    "Defining thread safety is surprisingly tricky. A quick Google search turns up numerous “definitions” like these:\n" +
                            "\n" +
                            "\n1. Thread-safe code is code that will work even if many Threads are executing it simultaneously.\n" +
                            "\n2. A piece of code is thread-safe if it only manipulates shared data structures in a manner that guarantees safe execution by multiple threads at the same time.\n" +
                            "\n" +
                            "And there are more similar definitions.\n" +
                            "\n" +
                            "Don’t you think that definitions like above actually does not communicate anything meaningful and even add some more confusion. Though these definitions can’t be ruled out just like that, because they are not wrong. But the fact is they do not provide any practical help or perspective. How do we make a difference between a thread-safe class and an unsafe one? What do we even mean by “safe”?",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What is Correctness in thread safety?",
                    "At the heart of any reasonable definition of thread safety is the concept of correctness. So, before understanding the thread-safety we should understand first, this “correctness“.\n" +
                            "\n" +
                            "    Correctness means that a class conforms to its specification.\n" +
                            "\n" +
                            "You will agree that a good class specification will have all information about a class’s state at any given time and it’s post condition if some operation is performed on it. Since we often don’t write adequate specifications for our classes, how can we possibly know they are correct? We can’t, but that doesn’t stop us from using them anyway once we’ve convinced ourselves that “the code works”. This “code confidence” is about as close as many of us get to correctness.\n" +
                            "\n" +
                            "Having optimistically defined “correctness” as something that can be recognized, we can now define thread safety in a somewhat less circular way: a class is thread-safe when it continues to behave correctly when accessed from multiple threads.\n" +
                            "\n" +
                            "    A class is thread-safe if it behaves correctly when accessed from multiple threads, regardless of the scheduling or interleaving of the execution of those threads by the runtime environment, and with no additional synchronization or other coordination on the part of the calling code.\n" +
                            "\n" +
                            "If the loose use of “correctness” here bothers you, you may prefer to think of a thread-safe class as one that is no more broken in a concurrent environment than in a single-threaded environment. Thread-safe classes encapsulate any needed synchronization so that clients need not provide their own",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Example: A Stateless Servlet",
                    "A good example of thread safe class is java servlets which have no fields and references, no fields from other classes etc. They are stateless.",
                    "public class StatelessFactorizer implements Servlet \n" +
                            "{\n" +
                            "    public void service(ServletRequest req, ServletResponse resp) \n" +
                            "    {\n" +
                            "        BigInteger i = extractFromRequest(req);\n" +
                            "        BigInteger[] factors = factor(i);\n" +
                            "        encodeIntoResponse(resp, factors);\n" +
                            "    }\n" +
                            "}",
                    "fill",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Example: A Stateless Servlet",
                    "The transient state for a particular computation exists solely in local variables that are stored on the thread’s stack and are accessible only to the executing thread. One thread accessing a StatelessFactorizer cannot influence the result of another thread accessing the same StatelessFactorizer; because the two threads do not share state, it is as if they were accessing different instances. Since the actions of a thread accessing a stateless object cannot affect the correctness of operations in other threads, stateless objects are thread-safe.",
                    "",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Concurrency : Object level Locking vs. Class level Locking"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Synchronization",
                    "Synchronization refers to multi-threading. A synchronized block of code can only be executed by one thread at a time.\n" +
                            "\n" +
                            "Java supports multiple threads to be executed. This may cause two or more threads to access the same fields or objects. Synchronization is a process which keeps all concurrent threads in execution to be in synch. Synchronization avoids memory consistence errors caused due to inconsistent view of shared memory. When a method is declared as synchronized; the thread holds the monitor for that method’s object If another thread is executing the synchronized method, your thread is blocked until that thread releases the monitor.\n" +
                            "\n" +
                            "Synchronization in java is achieved using synchronized keyword. You can use synchronized keyword in your class on defined methods or blocks. Keyword can not be used with variables or attributes in class definition.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Object level locking",
                    "Object level locking is mechanism when you want to synchronize a non-static method or non-static code block such that only one thread will be able to execute the code block on given instance of the class. This should always be done to make instance level data thread safe. This can be done as below :",
                    "public class DemoClass\n" +
                            "{\n" +
                            "    public synchronized void demoMethod(){}\n" +
                            "}\n" +
                            " \n" +
                            "or\n" +
                            " \n" +
                            "public class DemoClass\n" +
                            "{\n" +
                            "    public void demoMethod(){\n" +
                            "        synchronized (this)\n" +
                            "        {\n" +
                            "            //other thread safe code\n" +
                            "        }\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "or\n" +
                            " \n" +
                            "public class DemoClass\n" +
                            "{\n" +
                            "    private final Object lock = new Object();\n" +
                            "    public void demoMethod(){\n" +
                            "        synchronized (lock)\n" +
                            "        {\n" +
                            "            //other thread safe code\n" +
                            "        }\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Class level locking",
                    "Class level locking prevents multiple threads to enter in synchronized block in any of all available instances on runtime. This means if in runtime there are 100 instances of  DemoClass, then only one thread will be able to execute demoMethod() in any one of instance at a time, and all other instances will be locked for other threads. This should always be done to make static data thread safe.",
                    "public class DemoClass\n" +
                            "{\n" +
                            "    public synchronized static void demoMethod(){}\n" +
                            "}\n" +
                            " \n" +
                            "or\n" +
                            " \n" +
                            "public class DemoClass\n" +
                            "{\n" +
                            "    public void demoMethod(){\n" +
                            "        synchronized (DemoClass.class)\n" +
                            "        {\n" +
                            "            //other thread safe code\n" +
                            "        }\n" +
                            "    }\n" +
                            "}\n" +
                            " \n" +
                            "or\n" +
                            " \n" +
                            "public class DemoClass\n" +
                            "{\n" +
                            "    private final static Object lock = new Object();\n" +
                            "    public void demoMethod(){\n" +
                            "        synchronized (lock)\n" +
                            "        {\n" +
                            "            //other thread safe code\n" +
                            "        }\n" +
                            "    }\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Some Important Notes",
                    "1. Synchronization in java guarantees that no two threads can execute a synchronized method which requires same lock simultaneously or concurrently.\n" +
                            "2. synchronized keyword can be used only with methods and code blocks. These methods or blocks can be static or non-static both.\n" +
                            "3. When ever a thread enters into java synchronized method or block it acquires a lock and whenever it leaves java synchronized method or block it releases the lock. Lock is released even if thread leaves synchronized method after completion or due to any Error or Exception.\n" +
                            "4. java synchronized keyword is re-entrant in nature it means if a java synchronized method calls another synchronized method which requires same lock then current thread which is holding lock can enter into that method without acquiring lock.\n" +
                            "5. Java Synchronization will throw NullPointerException if object used in java synchronized block is null. For example, in above code sample if lock is initialized as null, the synchronized (lock) will throw NullPointerException.\n" +
                            "6. Synchronized methods in Java put a performance cost on your application. So use synchronization when it is absolutely required. Also, consider using synchronized code blocks for synchronizing only critical section of your code.\n" +
                            "7. It’s possible that both static synchronized and non static synchronized method can run simultaneously or concurrently because they lock on different object.\n" +
                            "8. According to the Java language specification you can not use java synchronized keyword with constructor it’s illegal and result in compilation error.\n" +
                            "9. Do not synchronize on non final field on synchronized block in Java. because reference of non final field may change any time and then different thread might synchronizing on different objects i.e. no synchronization at all. Best is to use String class, which is already immutable and declared final.",
                    "",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Threading : Working With wait(), notify() and notifyAll()"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Multi Threading",
                    "Multithreading in java is pretty complex topic and requires a lot of attention while writing application code dealing with multiple threads accessing one/more shared resources at any given time. Java 5, introduced some classes like BlockingQueue and Executors which take away some of the complexity by providing easy to use APIs. Programmers using these classes will feel a lot more confident than programmers directly handling synchronization stuff using wait() and notify() method calls. I will also recommend to use these newer APIs over synchronization yourself, BUT many times we are required to do so for various reasons e.g. maintaining legacy code. A good knowledge around these methods will help you in such situation when arrived. In this tutorial, I am discussing some concepts around methods wait(), notify() and notifyAll().",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What are wait(), notify() and notifyAll() methods?",
                    "Before moving into concepts, lets note down few very basic definitions involved for these methods.\n" +
                            "\n" +
                            "The Object class in Java has three final methods that allow threads to communicate about the locked status of a resource. These are :\n" +
                            "\n" +
                            "wait() : It tells the calling thread to give up the lock and go to sleep until some other thread enters the same monitor and calls notify(). The wait() method releases the lock prior to waiting and reacquires the lock prior to returning from the wait() method. The wait() method is actually tightly integrated with the synchronization lock, using a feature not available directly from the synchronization mechanism. In other words, it is not possible for us to implement the wait() method purely in Java: it is a native method.\n" +
                            "General syntax for calling wait() method is like this:",
                    "synchronized( lockObject )\n" +
                            "{\n" +
                            "    while( ! condition )\n" +
                            "    {\n" +
                            "        lockObject.wait();\n" +
                            "    }\n" +
                            "     \n" +
                            "    //take the action here;\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "notify() method",
                    "notify() : It wakes up one single thread that called wait() on the same object. It should be noted that calling notify() does not actually give up a lock on a resource. It tells a waiting thread that that thread can wake up. However, the lock is not actually given up until the notifier’s synchronized block has completed. So, if a notifier calls notify() on a resource but the notifier still needs to perform 10 seconds of actions on the resource within its synchronized block, the thread that had been waiting will need to wait at least another additional 10 seconds for the notifier to release the lock on the object, even though notify() had been called.\n" + "General syntax for calling notify() method is like this:",
                    "synchronized(lockObject)\n" +
                            "{\n" +
                            "    //establish_the_condition;\n" +
                            " \n" +
                            "    lockObject.notify();\n" +
                            "     \n" +
                            "    //any additional code if needed\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "notifyAll() method",
                    "notifyAll() : It wakes up all the threads that called wait() on the same object. The highest priority thread will run first in most of the situation, though not guaranteed. Other things are same as notify() method above.\n" + "General syntax for calling notify() method is like this:",
                    "synchronized(lockObject)\n" +
                            "{\n" +
                            "    establish_the_condition;\n" +
                            " \n" +
                            "    lockObject.notifyAll();\n" +
                            "}",
                    "",
                    "In general, a thread that uses the wait() method confirms that a condition does not exist (typically by checking a variable) and then calls the wait() method. When another thread establishes the condition (typically by setting the same variable), it calls the notify() method. The wait-and-notify mechanism does not specify what the specific condition/ variable value is. It is on developer’s hand to specify the condition to be checked before calling wait() or notify()."))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Multi Threading : Producer Consumer example"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Multi Threading : Producer Consumer example",
                    "Multithreading in java is pretty complex topic and requires a lot of attention while writing application code dealing with multiple threads accessing one/more shared resources at any given time. Java 5, introduced some classes like BlockingQueue and Executors which take away some of the complexity by providing easy to use APIs. Programmers using these classes will feel a lot more confident than programmers directly handling synchronization stuff using wait() and notify() method calls. I will also recommend to use these newer APIs over synchronization yourself, BUT many times we are required to do so for various reasons e.g. maintaining legacy code. A good knowledge around these methods will help you in such situation when arrived. In this tutorial, I am discussing some concepts around methods wait(), notify() and notifyAll().",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What are wait(), notify() and notifyAll() methods?",
                    "In this exercise, we will solve producer consumer problem using wait() and notify() methods. To keep program simple and to keep focus on usage of wait() and notify() methods, we will involve only one producer and one consumer thread.\n" +
                            "\n" +
                            "Other features of the program are :\n" +
                            "\n" +
                            "1) Producer thread produce a new resource in every 1 second and put it in ‘taskQueue’.\n" +
                            "2) Consumer thread takes 1 seconds to process consumed resource from ‘taskQueue’.\n" +
                            "3) Max capacity of taskQueue is 5 i.e. maximum 5 resources can exist inside ‘taskQueue’ at any given time.\n" +
                            "4) Both threads run infinitely.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Designing Producer Thread",
                    "Below is the code for producer thread based on our requirements :",
                    "class Producer implements Runnable\n" +
                            "{\n" +
                            "   private final List<Integer> taskQueue;\n" +
                            "   private final int           MAX_CAPACITY;\n" +
                            " \n" +
                            "   public Producer(List<Integer> sharedQueue, int size)\n" +
                            "   {\n" +
                            "      this.taskQueue = sharedQueue;\n" +
                            "      this.MAX_CAPACITY = size;\n" +
                            "   }\n" +
                            " \n" +
                            "   @Override\n" +
                            "   public void run()\n" +
                            "   {\n" +
                            "      int counter = 0;\n" +
                            "      while (true)\n" +
                            "      {\n" +
                            "         try\n" +
                            "         {\n" +
                            "            produce(counter++);\n" +
                            "         }\n" +
                            "         catch (InterruptedException ex)\n" +
                            "         {\n" +
                            "            ex.printStackTrace();\n" +
                            "         }\n" +
                            "      }\n" +
                            "   }\n" +
                            " \n" +
                            "   private void produce(int i) throws InterruptedException\n" +
                            "   {\n" +
                            "      synchronized (taskQueue)\n" +
                            "      {\n" +
                            "         while (taskQueue.size() == MAX_CAPACITY)\n" +
                            "         {\n" +
                            "            System.out.println(\"Queue is full \" + Thread.currentThread().getName() + \" is waiting , size: \" + taskQueue.size());\n" +
                            "            taskQueue.wait();\n" +
                            "         }\n" +
                            "           \n" +
                            "         Thread.sleep(1000);\n" +
                            "         taskQueue.add(i);\n" +
                            "         System.out.println(\"Produced: \" + i);\n" +
                            "         taskQueue.notifyAll();\n" +
                            "      }\n" +
                            "   }\n" +
                            "}",
                    "",
                    "1) Here “produce(counter++)” code has been written inside infinite loop so that producer keeps producing elements at regular interval.\n" +
                            "2) We have written the produce() method code following the general guideline to write wait() method as mentioned in first section.\n" +
                            "3) Once the wait() is over, producer add an element in taskQueue and called notifyAll() method. Because the last-time wait() method was called by consumer thread (that’s why producer is out of waiting state), consumer gets the notification.\n" +
                            "4) Consumer thread after getting notification, if ready to consume the element as per written logic.\n" +
                            "5) Note that both threads use sleep() methods as well for simulating time delays in creating and consuming elements."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Designing Consumer Thread",
                    "Below is the code for consumer thread based on our requirements :",
                    "class Consumer implements Runnable\n" +
                            "{\n" +
                            "   private final List<Integer> taskQueue;\n" +
                            " \n" +
                            "   public Consumer(List<Integer> sharedQueue)\n" +
                            "   {\n" +
                            "      this.taskQueue = sharedQueue;\n" +
                            "   }\n" +
                            " \n" +
                            "   @Override\n" +
                            "   public void run()\n" +
                            "   {\n" +
                            "      while (true)\n" +
                            "      {\n" +
                            "         try\n" +
                            "         {\n" +
                            "            consume();\n" +
                            "         } catch (InterruptedException ex)\n" +
                            "         {\n" +
                            "            ex.printStackTrace();\n" +
                            "         }\n" +
                            "      }\n" +
                            "   }\n" +
                            " \n" +
                            "   private void consume() throws InterruptedException\n" +
                            "   {\n" +
                            "      synchronized (taskQueue)\n" +
                            "      {\n" +
                            "         while (taskQueue.isEmpty())\n" +
                            "         {\n" +
                            "            System.out.println(\"Queue is empty \" + Thread.currentThread().getName() + \" is waiting , size: \" + taskQueue.size());\n" +
                            "            taskQueue.wait();\n" +
                            "         }\n" +
                            "         Thread.sleep(1000);\n" +
                            "         int i = (Integer) taskQueue.remove(0);\n" +
                            "         System.out.println(\"Consumed: \" + i);\n" +
                            "         taskQueue.notifyAll();\n" +
                            "      }\n" +
                            "   }\n" +
                            "}",
                    "",
                    "1) Here “consume()” code has been written inside infinite loop so that consumer keeps consuming elements whenever it finds something in taskQueue..\n" +
                            "2) Once the wait() is over, consumer removes an element in taskQueue and called notifyAll() method. Because the last-time wait() method was called by producer thread (that’s why producer is in waiting state), producer gets the notification.\n" +
                            "3) Producer thread after getting notification, if ready to produce the element as per written logic."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Test the Application",
                    "Now lets test producer and consumer threads.",
                    "public class ProducerConsumerExampleWithWaitAndNotify\n" +
                            "{\n" +
                            "   public static void main(String[] args)\n" +
                            "   {\n" +
                            "      List<Integer> taskQueue = new ArrayList<Integer>();\n" +
                            "      int MAX_CAPACITY = 5;\n" +
                            "      Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), \"Producer\");\n" +
                            "      Thread tConsumer = new Thread(new Consumer(taskQueue), \"Consumer\");\n" +
                            "      tProducer.start();\n" +
                            "      tConsumer.start();\n" +
                            "   }\n" +
                            "}",
                    "",
                    "Output:\n" +
                            " \n" +
                            "Produced: 0\n" +
                            "Consumed: 0\n" +
                            "Queue is empty Consumer is waiting , size: 0\n" +
                            "Produced: 1\n" +
                            "Produced: 2\n" +
                            "Consumed: 1\n" +
                            "Consumed: 2\n" +
                            "Queue is empty Consumer is waiting , size: 0\n" +
                            "Produced: 3\n" +
                            "Produced: 4\n" +
                            "Consumed: 3\n" +
                            "Produced: 5\n" +
                            "Consumed: 4\n" +
                            "Produced: 6\n" +
                            "Consumed: 5\n" +
                            "Consumed: 6\n" +
                            "Queue is empty Consumer is waiting , size: 0\n" +
                            "Produced: 7\n" +
                            "Consumed: 7\n" +
                            "Queue is empty Consumer is waiting , size: 0\n\n" +
                            "//Output varies on each run"))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Interview Questions : wait(), notify() and notifyAll() Methods"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What happens when notify() is called and no thread is waiting?",
                    "In general practice, this will not be the case in most scenarios if these methods are used correctly. Though if the notify() method is called when no other thread is waiting, notify() simply returns and the notification is lost.\n" +
                            "\n" +
                            "Since the wait-and-notify mechanism does not know the condition about which it is sending notification, it assumes that a notification goes unheard if no thread is waiting. A thread that later executes the wait() method has to wait for another notification to occur.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Can there be a race condition during the period that the wait() method releases OR reacquires the lock?",
                    "The wait() method is tightly integrated with the lock mechanism. The object lock is not actually freed until the waiting thread is already in a state in which it can receive notifications. It means only when thread state is changed such that it is able to receive notifications, lock is held. The system prevents any race conditions from occurring in this mechanism.\n" +
                            "\n" +
                            "Similarly, system ensures that lock should be held by object completely before moving the thread out of waiting state.",
                    "",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "If a thread receives a notification, is it guaranteed that the condition is set correctly?",
                    "Simply, no. Prior to calling the wait() method, a thread should always test the condition while holding the synchronization lock. Upon returning from the wait() method, the thread should always retest the condition to determine if it should wait again. This is because another thread can also test the condition and determine that a wait is not necessary — processing the valid data that was set by the notification thread.\n" +
                            "\n" +
                            "This is a common case when multiple threads are involved in the notifications. More particularly, the threads that are processing the data can be thought of as consumers; they consume the data produced by other threads. There is no guarantee that when a consumer receives a notification that it has not been processed by another consumer. As such, when a consumer wakes up, it cannot assume that the state it was waiting for is still valid. It may have been valid in the past, but the state may have been changed after the notify() method was called and before the consumer thread woke up. Waiting threads must provide the option to check the state and to return back to a waiting state in case the notification has already been handled. This is why we always put calls to the wait() method in a loop.",
                    "",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "What happens when more than one thread is waiting for notification? Which threads actually get the notification when the notify() method is called?",
                    "It depends on many factors.Java specification doesn’t define which thread gets notified. In runtime, which thread actually receives the notification varies based on several factors, including the implementation of the Java virtual machine and scheduling and timing issues during the execution of the program. There is no way to determine, even on a single processor platform, which of multiple threads receives the notification.\n" +
                            "\n" +
                            "Just like the notify() method, the notifyAll() method does not allow us to decide which thread gets the notification: they all get notified. When all the threads receive the notification, it is possible to work out a mechanism for the threads to choose among themselves which thread should continue and which thread(s) should call the wait() method again.",
                    "",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Does the notifyAll() method really wake up all the threads?",
                    "Yes and no. All of the waiting threads wake up, but they still have to reacquire the object lock. So the threads do not run in parallel: they must each wait for the object lock to be freed. Thus, only one thread can run at a time, and only after the thread that called the notifyAll() method releases its lock.",
                    "",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Why would you want to wake up all of the threads if only one is going to execute at all?",
                    "There are a few reasons. For example, there might be more than one condition to wait for. Since we cannot control which thread gets the notification, it is entirely possible that a notification wakes up a thread that is waiting for an entirely different condition. By waking up all the threads, we can design the program so that the threads decide among themselves which thread should execute next. Another option could be when producers generate data that can satisfy more than one consumer. Since it may be difficult to determine how many consumers can be satisfied with the notification, an option is to notify them all, allowing the consumers to sort it out among themselves.",
                    "",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Thread Vs. Runnable"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Difference between Runnable vs Thread in Java",
                    "In java language, as we all know that there are two ways to create threads. One using Runnable interface and another by extending Thread class. Let’s identify the differences between both ways i.e Runnable vs Thread.",
                    "",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Java Code to Create Threads using Runnable Interface and Thread class",
                    "Let’s quickly check the java code of usage of both techniques.",
                    "public class DemoRunnable implements Runnable {\n" +
                            "    public void run() {\n" +
                            "        //Code\n" +
                            "    }\n" +
                            "}\n" +
                            "//with a \"new Thread(demoRunnable).start()\" call\n" +
                            " \n" +
                            "public class DemoThread extends Thread {\n" +
                            "    public DemoThread() {\n" +
                            "        super(\"DemoThread\");\n" +
                            "    }\n" +
                            "    public void run() {\n" +
                            "        //Code\n" +
                            "    }\n" +
                            "}\n" +
                            "//with a \"demoThread.start()\" call",
                    "",
                    ""))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Difference between Runnable vs Thread",
                    "There has been a good amount of debate on which is better way. Well, I also tried to find out and below is my learning:\n" +
                            "\n" +
                            "\n1. Implementing Runnable is the preferred way to do it. Here, you’re not really specializing or modifying the thread’s behavior. You’re just giving the thread something to run. That means composition is the better way to go.\n" +
                            "\n2. Java only supports single inheritance, so you can only extend one class.\n" +
                            "\n3. Instantiating an interface gives a cleaner separation between your code and the implementation of threads.\n" +
                            "\n4. Implementing Runnable makes your class more flexible. If you extend thread then the action you’re doing is always going to be in a thread. However, if you extend Runnable it doesn’t have to be. You can run it in a thread, or pass it to some kind of executor service, or just pass it around as a task within a single threaded application.\n" +
                            "\n5. By extending Thread, each of your threads has a unique object associated with it, whereas implementing Runnable, many threads can share the same runnable instance.",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Concurrency : yield() and join()"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Difference between yield() and join()",
                    "yield() method\n" +
                            "\n" +
                            "Theoretically, to ‘yield’ means to let go, to give up, to surrender. A yielding thread tells the virtual machine that it’s willing to let other threads be scheduled in its place. This indicates that it’s not doing something too critical. Note that it’s only a hint, though, and not guaranteed to have any effect at all.\n" +
                            "\n" +
                            "yield() is defined as following in Thread.java.",
                    "/**\n" +
                            "  * A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore\n" +
                            "  * this hint. Yield is a heuristic attempt to improve relative progression between threads that would otherwise over-utilize a CPU.\n" +
                            "  * Its use should be combined with detailed profiling and benchmarking to ensure that it actually has the desired effect.\n" +
                            "  */\n" +
                            " \n" +
                            "public static native void yield();",
                    "",
                    "Let’s list down important points from above definition:\n" +
                            "\n" +
                            "\n1. Yield is a Static method and Native too.\n" +
                            "\n2. Yield tells the currently executing thread to give a chance to the threads that have equal priority in the Thread Pool.\n" +
                            "\n3. There is no guarantee that Yield will make the currently executing thread to runnable state immediately.\n" +
                            "\n4. It can only make a thread from Running State to Runnable State, not in wait or blocked state."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "yield() method example usage",
                    "In below example program, I have created two threads named producer and consumer for no specific reason. Producer is set to minimum priority and consumer is set to maximum priority. I will run below code with/without commenting the line Thread.yield(). Without yield(), though the output changes sometimes, but usually first all consumer lines are printed and then all producer lines.\n" +
                            "\n" +
                            "With using yield() method, both prints one line at a time and pass the chance to another thread, almost all the time.",
                    "package test.core.threads;\n" +
                            " \n" +
                            "public class YieldExample\n" +
                            "{\n" +
                            "   public static void main(String[] args)\n" +
                            "   {\n" +
                            "      Thread producer = new Producer();\n" +
                            "      Thread consumer = new Consumer();\n" +
                            "       \n" +
                            "      producer.setPriority(Thread.MIN_PRIORITY); //Min Priority\n" +
                            "      consumer.setPriority(Thread.MAX_PRIORITY); //Max Priority\n" +
                            "       \n" +
                            "      producer.start();\n" +
                            "      consumer.start();\n" +
                            "   }\n" +
                            "}\n" +
                            " \n" +
                            "class Producer extends Thread\n" +
                            "{\n" +
                            "   public void run()\n" +
                            "   {\n" +
                            "      for (int i = 0; i < 5; i++)\n" +
                            "      {\n" +
                            "         System.out.println(\"I am Producer : Produced Item \" + i);\n" +
                            "         Thread.yield();\n" +
                            "      }\n" +
                            "   }\n" +
                            "}\n" +
                            " \n" +
                            "class Consumer extends Thread\n" +
                            "{\n" +
                            "   public void run()\n" +
                            "   {\n" +
                            "      for (int i = 0; i < 5; i++)\n" +
                            "      {\n" +
                            "         System.out.println(\"I am Consumer : Consumed Item \" + i);\n" +
                            "         Thread.yield();\n" +
                            "      }\n" +
                            "   }\n" +
                            "}",
                    "",
                    "Output of above program “without” yield() method\n" +
                            "\n" +
                            "I am Consumer : Consumed Item 0\n" +
                            " I am Consumer : Consumed Item 1\n" +
                            " I am Consumer : Consumed Item 2\n" +
                            " I am Consumer : Consumed Item 3\n" +
                            " I am Consumer : Consumed Item 4\n" +
                            " I am Producer : Produced Item 0\n" +
                            " I am Producer : Produced Item 1\n" +
                            " I am Producer : Produced Item 2\n" +
                            " I am Producer : Produced Item 3\n" +
                            " I am Producer : Produced Item 4\n" +
                            "Output of above program “with” yield() method added\n" +
                            "\n" +
                            "I am Producer : Produced Item 0\n" +
                            " I am Consumer : Consumed Item 0\n" +
                            " I am Producer : Produced Item 1\n" +
                            " I am Consumer : Consumed Item 1\n" +
                            " I am Producer : Produced Item 2\n" +
                            " I am Consumer : Consumed Item 2\n" +
                            " I am Producer : Produced Item 3\n" +
                            " I am Consumer : Consumed Item 3\n" +
                            " I am Producer : Produced Item 4\n" +
                            " I am Consumer : Consumed Item 4"))
            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "join() method",
                    "The join() method of a Thread instance can be used to “join” the start of a thread’s execution to the end of another thread’s execution so that a thread will not start running until another thread has ended. If join() is called on a Thread instance, the currently running thread will block until the Thread instance has finished executing.",
                    "//Waits for this thread to die.\n" +
                            " \n" +
                            "public final void join() throws InterruptedException",
                    "",
                    "Giving a timeout within join(), will make the join() effect to be nullified after the specific timeout. When the timeout is reached, the main thread and taskThread are equally probable candidates to execute. However, as with sleep, join is dependent on the OS for timing, so you should not assume that join will wait exactly as long as you specify.\n" +
                            "\n" +
                            "Like sleep, join responds to an interrupt by exiting with an InterruptedException."))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "join() method example usage",
                    "Sample Program using join()",
                    "package test.core.threads;\n" +
                            " \n" +
                            "public class JoinExample\n" +
                            "{\n" +
                            "   public static void main(String[] args) throws InterruptedException\n" +
                            "   {\n" +
                            "      Thread t = new Thread(new Runnable()\n" +
                            "         {\n" +
                            "            public void run()\n" +
                            "            {\n" +
                            "               System.out.println(\"First task started\");\n" +
                            "               System.out.println(\"Sleeping for 2 seconds\");\n" +
                            "               try\n" +
                            "               {\n" +
                            "                  Thread.sleep(2000);\n" +
                            "               } catch (InterruptedException e)\n" +
                            "               {\n" +
                            "                  e.printStackTrace();\n" +
                            "               }\n" +
                            "               System.out.println(\"First task completed\");\n" +
                            "            }\n" +
                            "         });\n" +
                            "      Thread t1 = new Thread(new Runnable()\n" +
                            "         {\n" +
                            "            public void run()\n" +
                            "            {\n" +
                            "               System.out.println(\"Second task completed\");\n" +
                            "            }\n" +
                            "         });\n" +
                            "      t.start(); // Line 15\n" +
                            "      t.join(); // Line 16\n" +
                            "      t1.start();\n" +
                            "   }\n" +
                            "}",
                    "",
                    "Output:\n" +
                            " \n" +
                            "First task started\n" +
                            "Sleeping for 2 seconds\n" +
                            "First task completed\n" +
                            "Second task completed"))

            lesson.bitModules = bitModules
            lessons.add(lesson)

            moduleIndex = 1

            lesson = Lesson()
            lesson.programLanguage = programLanguage
            lesson.lessonId = "lesson_" + programLanguage + lessonIndex++
            lesson.title = "Difference between sleep() and wait()?"

            bitModules = ArrayList<BitModule>()

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Difference between sleep() and wait()?",
                    "sleep() is a method which is used to hold the process for few seconds or the time you wanted but in case of wait() method thread goes in waiting state and it won’t come back automatically until we call the notify() or notifyAll().\n" +
                            "\n" +
                            "The major difference is that wait() releases the lock or monitor while sleep() doesn’t releases any lock or monitor while waiting. Wait is used for inter-thread communication while sleep is used to introduce pause on execution, generally.\n" +
                            "\n" +
                            "Thread.sleep() sends the current thread into the “Not Runnable” state for some amount of time. The thread keeps the monitors it has acquired — i.e. if the thread is currently in a synchronized block or method no other thread can enter this block or method. If another thread calls t.interrupt() it will wake up the sleeping thread. Note that sleep is a static method, which means that it always affects the current thread (the one that is executing the sleep method). A common mistake is to call t.sleep() where t is a different thread; even then, it is the current thread that will sleep, not the t thread.\n" +
                            "\n" +
                            "object.wait() sends the current thread into the “Not Runnable” state, like sleep(), but with a twist. Wait is called on an object, not a thread; we call this object the “lock object.” Before lock.wait() is called, the current thread must synchronize on the lock object; wait() then releases this lock, and adds the thread to the “wait list” associated with the lock. Later, another thread can synchronize on the same lock object and call lock.notify(). This wakes up the original, waiting thread. Basically, wait()/notify() is like sleep()/interrupt(), only the active thread does not need a direct pointer to the sleeping thread, but only to the shared lock object.",
                    "synchronized(LOCK) {   \n" +
                            "    Thread.sleep(1000); // LOCK is held\n" +
                            "}\n" +
                            "\n" +
                            "synchronized(LOCK) {   \n" +
                            "    LOCK.wait(); // LOCK is not held\n" +
                            "}",
                    "",
                    ""))

            bitModules.add(BitModule(
                    lesson.lessonId + "_module_" + moduleIndex++,
                    programLanguage,
                    "Let categorize all above points :",
                    "Call on:\n" +
                            "\n" +
                            "    wait(): Call on an object; current thread must synchronize on the lock object.\n" +
                            "    sleep(): Call on a Thread; always currently executing thread.\n" +
                            "Synchronized:\n" +
                            "\n" +
                            "    wait(): when synchronized multiple threads access same Object one by one.\n" +
                            "    sleep(): when synchronized multiple threads wait for sleep over of sleeping thread.\n" +
                            "Hold lock:\n" +
                            "\n" +
                            "    wait(): release the lock for other objects to have chance to execute.\n" +
                            "    sleep(): keep lock for at least t times if timeout specified or somebody interrupt.\n" +
                            "Wake-up condition:\n" +
                            "\n" +
                            "    wait(): until call notify(), notifyAll() from object\n" +
                            "    sleep(): until at least time expire or call interrupt().\n" +
                            "Usage:\n" +
                            "\n" +
                            "    sleep(): for time-synchronization and;\n" +
                            "    wait(): for multi-thread-synchronization.",
                    "",
                    "",
                    ""))

            lesson.bitModules = bitModules
            lessons.add(lesson)
            return lessons

        }

    override fun doInBackground(vararg voids: Void): ArrayList<Lesson> {
        return lessons
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(lessons: ArrayList<Lesson>) {
        super.onPostExecute(lessons)
        mLessonListener.onSuccess(lessons)
    }
}
