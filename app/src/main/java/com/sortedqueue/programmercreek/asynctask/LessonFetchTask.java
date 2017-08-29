package com.sortedqueue.programmercreek.asynctask;

import android.os.AsyncTask;

import com.sortedqueue.programmercreek.database.lessons.BitModule;
import com.sortedqueue.programmercreek.database.lessons.Lesson;

import java.util.ArrayList;

/**
 * Created by Alok on 28/08/17.
 */

public class LessonFetchTask extends AsyncTask<Void, Void, ArrayList<Lesson>> {

    private final LessonFetcherTaskListener mLessonListener;

    public interface LessonFetcherTaskListener {
        void onSuccess( ArrayList<Lesson> lessons );
    }

    public LessonFetchTask( LessonFetcherTaskListener lessonFetcherTaskListener ) {
        this.mLessonListener = lessonFetcherTaskListener;
    }

    //http://howtodoinjava.com/java-tutorials-list-howtodoinjava/
    public ArrayList<Lesson> getLessons() {

        ArrayList<Lesson> lessons = new ArrayList<>();
        String programLanguage = "java";
        int lessonIndex = 1;
        int moduleIndex = 1;

        Lesson lesson = new Lesson();
        lesson.setProgramLanguage(programLanguage);
        lesson.setLessonId("lesson_" + programLanguage + lessonIndex++ );
        lesson.setTitle("Object Oriented Principles");

        ArrayList<BitModule> bitModules = new ArrayList<>();
        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Java Static Keyword – Everything You Should Know",
                "Static keyword in java can be applied on variables, methods, blocks, import " +
                        "and inner classes. In this tutorial, we will learn the effect " +
                        "of using static keyword in these places in detail."));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Variable",
                "To declare a variable static, use static keyword in variable declaration. static variable syntax is:",
                "ACCESS_MODIFER static DATA_TYPE VARNAME;"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Variable",
                "For example, a public static variable of Integer type is declared in this way.",
                "public static Integer staticVar;")); // Single line syntax indicator - ask user to complete the same syntax..

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                "Output:\n" +
                        " \n" +
                        "10\n" +
                        "null\n" +
                        "30\n" +
                        "30\n\n" +
                        "Notice how we changed the value to 30, and both objects now see the updated value which is 30."));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Variable",
                "Another thing you should have noticed that how we are able to access static variable with its classname i.e. DataObject.staticVar. We don’t need to create any instance to access static variables. It clearly shows that static variables belong to class scope."));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Method",
                "To declare a static method, use static keyword in method declaration. Static method syntax is:",
                "ACCESS_MODIFER static RETURN_TYPE METHOD_NAME;"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Method",
                "For example, a public static variable of Integer type is declared in this way.",
                "public static Integer getStaticVar(){\n" +
                        "    return staticVar;\n" +
                        "}"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                "Output:\n" +
                        " \n" +
                        "30\n" +
                        "30"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                        "import static <<package name>>.<<type name>>.*;"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Method",
                "For example, a public static variable of Integer type is declared in this way.",
                "public static Integer getStaticVar(){\n" +
                        "    return staticVar;\n" +
                        "}"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                "Output:\n" +
                        " \n" +
                        "30"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Static Block",
                "Static blocks are portion of class initialization code, which are wrapped with static keyword. General syntax is:",
                "static {\n" +
                        "    //initialize static members of class\n" +
                        "}"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                "Output:\n" +
                        " \n" +
                        "40"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                        "}"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
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
                        "Output:\n" +
                                " \n" +
                                "40"));

        bitModules.add(new BitModule(
                lesson.getLessonId() + "_module_" + moduleIndex++,
                programLanguage,
                "Summary",
                "Let’s summarize everything about static usage in java.\n" +
                        "\n" +
                        "1. Static members belong to class. No need to create class instance to access static members.\n" +
                        "2. Static members (variables and methods) can be accessed inside static methods and static blocks only.\n" +
                        "3. Non-static members cannot be accessed inside static methods, blocks and inner classes.\n" +
                        "4. A class can have multiple static blocks and they will be executed in order they appear in class definition.\n" +
                        "5. A class can be static only if its declared as inner class inside outer class.\n" +
                        "6. Static imports can be used to import all static members from a class. These members can be referred without any class reference."));

        lessons.add(lesson);

        lesson = new Lesson();
        lesson.setProgramLanguage(programLanguage);
        lesson.setLessonId("lesson_" + programLanguage + lessonIndex++ );
        lesson.setTitle("Concurrency in Java");
        lessons.add(lesson);

        lesson = new Lesson();
        lesson.setProgramLanguage(programLanguage);
        lesson.setLessonId("lesson_" + programLanguage + lessonIndex++ );
        lesson.setTitle("Collection in Java");
        lessons.add(lesson);

        lesson = new Lesson();
        lesson.setProgramLanguage(programLanguage);
        lesson.setLessonId("lesson_" + programLanguage + lessonIndex++ );
        lesson.setTitle("NIO[New IO] in Java");
        lessons.add(lesson);
        
        return lessons;

    }

    @Override
    protected ArrayList<Lesson> doInBackground(Void... voids) {
        return getLessons();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Lesson> lessons) {
        super.onPostExecute(lessons);
        mLessonListener.onSuccess(lessons);
    }
}
