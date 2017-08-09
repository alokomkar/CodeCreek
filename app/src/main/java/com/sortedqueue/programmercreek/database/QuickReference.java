package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.sortedqueue.programmercreek.util.AuxilaryUtils;

import java.util.ArrayList;

/**
 * Created by Alok on 03/08/17.
 */

public class QuickReference implements Parcelable {

    private String header;
    private String content;

    public boolean isExpanded;

    public QuickReference(String header, String content) {
        this.header = header;
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getContentArray() {
        return AuxilaryUtils.splitProgramIntolines(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.header);
        dest.writeString(this.content);
    }

    protected QuickReference(Parcel in) {
        this.header = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<QuickReference> CREATOR = new Parcelable.Creator<QuickReference>() {
        @Override
        public QuickReference createFromParcel(Parcel source) {
            return new QuickReference(source);
        }

        @Override
        public QuickReference[] newArray(int size) {
            return new QuickReference[size];
        }
    };

    public static ArrayList<QuickReference> getSQLQuickReference() {
        ArrayList<QuickReference> quickReferences = new ArrayList<>();
        quickReferences.add( new QuickReference("Structure of a C Program", "#include(stdio.h) /* include IO library */\n" +
                "#include... /* include other files */\n" +
                "#define.. /* define constants */\n" +
                "/* Declare global variables*/)\n" +
                "(variable type)(variable list);\n" +
                "/* Define program functions */\n" +
                "(type returned)(function name)(parameter list)\n" +
                "(declaration of parameter types)\n" +
                " {\n" +
                "(declaration of local variables);\n" +
                "(body of function code);\n" +
                " }\n" +
                "/* Define main function*/\n" +
                "main ((optional argc and argv arguments))\n" +
                "(optional declaration parameters)\n" +
                "{\n" +
                "(declaration of local variables);\n" +
                "(body of main function code);\n" +
                "}"));
        quickReferences.add( new QuickReference("Comments", "Format: /*(body of comment) */\n" +
                "Example: /*This is a comment in C*/"));
        quickReferences.add( new QuickReference("Constant Declarations", "Format: #define(constant name)(constant value)\n" +
                "Example: #define MAXIMUM 1000"));
        quickReferences.add( new QuickReference("Type Definitions", "Format: typedef(datatype)(symbolic name);\n" +
                "Example: typedef int KILOGRAMS;"));
        quickReferences.add( new QuickReference("Variables", "Declarations:\n" +
                "Format: (variable type)(name 1)(name 2),...;\n" +
                "Example: int firstnum, secondnum;\n" +
                "char alpha;\n" +
                "int firstarray[10];\n" +
                "int doublearray[2][5];\n" +
                "char firststring[1O];\n" +
                "Initializing:\n" +
                "Format: (variable type)(name)=(value);\n" +
                "Example: int firstnum=5;\n" +
                "Assignments:\n" +
                "Format: (name)=(value);\n" +
                "Example: firstnum=5;\n" +
                "Alpha='a';"));
        quickReferences.add( new QuickReference("Unions", "Declarations:\n" +
                "Format: union(tag)\n" +
                "{(type)(member name);\n" +
                " (type)(member name);\n" +
                "...\n" +
                "}(variable name);\n" +
                "Example: union demotagname\n" +
                "{int a;\n" +
                "float b;\n" +
                "}demovarname;\n" +
                "Assignment:\n" +
                "Format: (tag).(member name)=(value);\n" +
                "demovarname.a=1;\n" +
                "demovarname.b=4.6;"));
        quickReferences.add( new QuickReference("Structures", "Declarations:\n" +
                "Format: struct(tag)\n" +
                "{(type)(variable);\n" +
                " (type)(variable);\n" +
                "...\n" +
                "}(variable list);\n" +
                "Example: struct student\n" +
                "{int idnum;\n" +
                "int finalgrade;\n" +
                "char lettergrade;\n" +
                "} first,second,third;\n" +
                "Assignment:\n" +
                "Format: (variable name).(member)=(value);\n" +
                "Example: first.idnum=333;\n" +
                "second.finalgrade=92;"));
        quickReferences.add( new QuickReference("Operators", "Symbol Operation Example\n" +
                "+,-,*,/ arithmetic 1 = b + c;\n" +
                "% mod a = b % c;\n" +
                "> greater than if (a > b)\n" +
                ">= greater than or equal if (a >= b)\n" +
                "< less than if (a <b)\n" +
                "<= less than or equal if (a <= b)\n" +
                "== equality if ( == b)\n" +
                "= assignment a=25;\n" +
                "!= not equal if (a != b)\n" +
                "! not if (!a)\n" +
                "&& logical and if (a) && (b)\n" +
                "logical or if (a) (b)\n" +
                "++ increment ++ a;\n" +
                "-- decrement -- a;\n" +
                "& bitwise and a = b & c;\n" +
                "bitwise or a = b - c;\n" +
                "bitwise xor\n" +
                ">> shift-right a = b >> 2;\n" +
                "<< shift-left a = b << 2;\n" +
                "~ one's complement a = ~b"));
        quickReferences.add( new QuickReference("Input and Output", "Output\n" +
                "Print Formats:\n" +
                "String: print(\"(literal string)\");\n" +
                "String+newline: print (\"(string)\\n\");\n" +
                "Variables: printf(\"(conversion specs)\",(variables));\n" +
                "Print Examples:\n" +
                "print(\"firstvar+secondvar=%d\\n\",thirdvar);\n" +
                "Print Conversion Specifications:\n" +
                "%d decimal\n" +
                "%u unsigned decimal\n" +
                "%o octal\n" +
                "Ÿ\n" +
                "a = b Ÿ c\n" +
                "%h hex\n" +
                "%e exponential\n" +
                "%f float\n" +
                "%g shorter of %e or %f\n" +
                "%c char\n" +
                "%s string\n" +
                "Print Escape Sequences:\n" +
                "\\n newline\n" +
                "\\t tab\n" +
                "\\r carriage return\n" +
                "\\f form feed\n" +
                "\\b backspace\n" +
                "\\' output\n" +
                "\\\\ output \\\n" +
                "Input:\n" +
                "Scanf Format:\n" +
                "scanf(\"(conversion specs)\",&(varl),&(var2),...);\n" +
                "Scanf Example:\n" +
                "scanf(\"%d %d %d\",&first,&second,&third);\n" +
                "Scanf Conversion Specifications:\n" +
                "%d decimal integer expected\n" +
                "%o octalinteger expected\n" +
                "%x hex integer expected\n" +
                "%h short integer expected\n" +
                "%c character expected\n" +
                "%s string expected\n" +
                "%r real value expected\n" +
                "%e exponential notation expected\n" +
                "Primitive Input and Output Examples:\n" +
                "Get a character from standard input: c = getchar();\n" +
                "Put a character on standard output: putcher(c);"));

        quickReferences.add( new QuickReference("FOR LOOP Format:", "for ((first expr);(second expr);(third expr))\n" +
                "(simple statement);\n" +
                "for ((first expr);(second expr);(third expr))\n" +
                "{\n" +
                "(compound statement);") );
        quickReferences.add( new QuickReference("WHILE LOOP Format:", "while ((condition))\n" +
                " (simple statement);\n" +
                "while ((condition))\n" +
                "{\n" +
                "(compound statement);") );
        quickReferences.add( new QuickReference("DO WHILE LOOP Format:", "do\n" +
                "(simple statement)'\n" +
                "while ((condition))\n" +
                "do {\n" +
                " (compound statement);\n" +
                "}\n" +
                "while ((condition));") );
        quickReferences.add( new QuickReference("IF CONDITIONAL Format:", "if ((condition))\n" +
                "(simple statement);\n" +
                "if ((condition))\n" +
                "{\n" +
                " (compound statement);\n" +
                "}") );
        quickReferences.add( new QuickReference("IF... ELSE CONDITIONAL Format:", "if ((condition))\n" +
                "(statement 1);\n" +
                "else\n" +
                "(statement 2);") );
        quickReferences.add( new QuickReference("SWITCH Format:", "switch ((expression))\n" +
                "{case (value 1):(statement 1);\n" +
                "case (value 2):(statement 2);\n" +
                "...\n" +
                "default:(default statement);\n" +
                "}") );

        quickReferences.add( new QuickReference("Function Definitions", "Format:\n" +
                "(type returned)(function name)((parameter list))\n" +
                "(declaration of parameter list variables)\n" +
                "{\n" +
                " (declaration of local variables);\n" +
                " (body of function code);\n" +
                "}\n" +
                "Example:\n" +
                "Int. adder(a,b)\n" +
                "int a,b;\n" +
                " {int c;\n" +
                " c = a + b;\n" +
                " return (c);\n" +
                " }\n" +
                "Pointers\n" +
                "Declaration of pointer variable:\n" +
                "Format: (type)*(variable name);\n" +
                "Examples: int *p;\n" +
                "struct student *classmember;"));
        return quickReferences;
    }

    public static ArrayList<QuickReference> getJavaQuickReference() {
        ArrayList<QuickReference> quickReferences = new ArrayList<>();
        quickReferences.add( new QuickReference("Structure of a C Program", "#include(stdio.h) /* include IO library */\n" +
                "#include... /* include other files */\n" +
                "#define.. /* define constants */\n" +
                "/* Declare global variables*/)\n" +
                "(variable type)(variable list);\n" +
                "/* Define program functions */\n" +
                "(type returned)(function name)(parameter list)\n" +
                "(declaration of parameter types)\n" +
                " {\n" +
                "(declaration of local variables);\n" +
                "(body of function code);\n" +
                " }\n" +
                "/* Define main function*/\n" +
                "main ((optional argc and argv arguments))\n" +
                "(optional declaration parameters)\n" +
                "{\n" +
                "(declaration of local variables);\n" +
                "(body of main function code);\n" +
                "}"));
        quickReferences.add( new QuickReference("Comments", "Format: /*(body of comment) */\n" +
                "Example: /*This is a comment in C*/"));
        quickReferences.add( new QuickReference("Constant Declarations", "Format: #define(constant name)(constant value)\n" +
                "Example: #define MAXIMUM 1000"));
        quickReferences.add( new QuickReference("Type Definitions", "Format: typedef(datatype)(symbolic name);\n" +
                "Example: typedef int KILOGRAMS;"));
        quickReferences.add( new QuickReference("Variables", "Declarations:\n" +
                "Format: (variable type)(name 1)(name 2),...;\n" +
                "Example: int firstnum, secondnum;\n" +
                "char alpha;\n" +
                "int firstarray[10];\n" +
                "int doublearray[2][5];\n" +
                "char firststring[1O];\n" +
                "Initializing:\n" +
                "Format: (variable type)(name)=(value);\n" +
                "Example: int firstnum=5;\n" +
                "Assignments:\n" +
                "Format: (name)=(value);\n" +
                "Example: firstnum=5;\n" +
                "Alpha='a';"));
        quickReferences.add( new QuickReference("Unions", "Declarations:\n" +
                "Format: union(tag)\n" +
                "{(type)(member name);\n" +
                " (type)(member name);\n" +
                "...\n" +
                "}(variable name);\n" +
                "Example: union demotagname\n" +
                "{int a;\n" +
                "float b;\n" +
                "}demovarname;\n" +
                "Assignment:\n" +
                "Format: (tag).(member name)=(value);\n" +
                "demovarname.a=1;\n" +
                "demovarname.b=4.6;"));
        quickReferences.add( new QuickReference("Structures", "Declarations:\n" +
                "Format: struct(tag)\n" +
                "{(type)(variable);\n" +
                " (type)(variable);\n" +
                "...\n" +
                "}(variable list);\n" +
                "Example: struct student\n" +
                "{int idnum;\n" +
                "int finalgrade;\n" +
                "char lettergrade;\n" +
                "} first,second,third;\n" +
                "Assignment:\n" +
                "Format: (variable name).(member)=(value);\n" +
                "Example: first.idnum=333;\n" +
                "second.finalgrade=92;"));
        quickReferences.add( new QuickReference("Operators", "Symbol Operation Example\n" +
                "+,-,*,/ arithmetic 1 = b + c;\n" +
                "% mod a = b % c;\n" +
                "> greater than if (a > b)\n" +
                ">= greater than or equal if (a >= b)\n" +
                "< less than if (a <b)\n" +
                "<= less than or equal if (a <= b)\n" +
                "== equality if ( == b)\n" +
                "= assignment a=25;\n" +
                "!= not equal if (a != b)\n" +
                "! not if (!a)\n" +
                "&& logical and if (a) && (b)\n" +
                "logical or if (a) (b)\n" +
                "++ increment ++ a;\n" +
                "-- decrement -- a;\n" +
                "& bitwise and a = b & c;\n" +
                "bitwise or a = b - c;\n" +
                "bitwise xor\n" +
                ">> shift-right a = b >> 2;\n" +
                "<< shift-left a = b << 2;\n" +
                "~ one's complement a = ~b"));
        quickReferences.add( new QuickReference("Input and Output", "Output\n" +
                "Print Formats:\n" +
                "String: print(\"(literal string)\");\n" +
                "String+newline: print (\"(string)\\n\");\n" +
                "Variables: printf(\"(conversion specs)\",(variables));\n" +
                "Print Examples:\n" +
                "print(\"firstvar+secondvar=%d\\n\",thirdvar);\n" +
                "Print Conversion Specifications:\n" +
                "%d decimal\n" +
                "%u unsigned decimal\n" +
                "%o octal\n" +
                "Ÿ\n" +
                "a = b Ÿ c\n" +
                "%h hex\n" +
                "%e exponential\n" +
                "%f float\n" +
                "%g shorter of %e or %f\n" +
                "%c char\n" +
                "%s string\n" +
                "Print Escape Sequences:\n" +
                "\\n newline\n" +
                "\\t tab\n" +
                "\\r carriage return\n" +
                "\\f form feed\n" +
                "\\b backspace\n" +
                "\\' output\n" +
                "\\\\ output \\\n" +
                "Input:\n" +
                "Scanf Format:\n" +
                "scanf(\"(conversion specs)\",&(varl),&(var2),...);\n" +
                "Scanf Example:\n" +
                "scanf(\"%d %d %d\",&first,&second,&third);\n" +
                "Scanf Conversion Specifications:\n" +
                "%d decimal integer expected\n" +
                "%o octalinteger expected\n" +
                "%x hex integer expected\n" +
                "%h short integer expected\n" +
                "%c character expected\n" +
                "%s string expected\n" +
                "%r real value expected\n" +
                "%e exponential notation expected\n" +
                "Primitive Input and Output Examples:\n" +
                "Get a character from standard input: c = getchar();\n" +
                "Put a character on standard output: putcher(c);"));

        quickReferences.add( new QuickReference("FOR LOOP Format:", "for ((first expr);(second expr);(third expr))\n" +
                "(simple statement);\n" +
                "for ((first expr);(second expr);(third expr))\n" +
                "{\n" +
                "(compound statement);") );
        quickReferences.add( new QuickReference("WHILE LOOP Format:", "while ((condition))\n" +
                " (simple statement);\n" +
                "while ((condition))\n" +
                "{\n" +
                "(compound statement);") );
        quickReferences.add( new QuickReference("DO WHILE LOOP Format:", "do\n" +
                "(simple statement)'\n" +
                "while ((condition))\n" +
                "do {\n" +
                " (compound statement);\n" +
                "}\n" +
                "while ((condition));") );
        quickReferences.add( new QuickReference("IF CONDITIONAL Format:", "if ((condition))\n" +
                "(simple statement);\n" +
                "if ((condition))\n" +
                "{\n" +
                " (compound statement);\n" +
                "}") );
        quickReferences.add( new QuickReference("IF... ELSE CONDITIONAL Format:", "if ((condition))\n" +
                "(statement 1);\n" +
                "else\n" +
                "(statement 2);") );
        quickReferences.add( new QuickReference("SWITCH Format:", "switch ((expression))\n" +
                "{case (value 1):(statement 1);\n" +
                "case (value 2):(statement 2);\n" +
                "...\n" +
                "default:(default statement);\n" +
                "}") );

        quickReferences.add( new QuickReference("Function Definitions", "Format:\n" +
                "(type returned)(function name)((parameter list))\n" +
                "(declaration of parameter list variables)\n" +
                "{\n" +
                " (declaration of local variables);\n" +
                " (body of function code);\n" +
                "}\n" +
                "Example:\n" +
                "Int. adder(a,b)\n" +
                "int a,b;\n" +
                " {int c;\n" +
                " c = a + b;\n" +
                " return (c);\n" +
                " }\n" +
                "Pointers\n" +
                "Declaration of pointer variable:\n" +
                "Format: (type)*(variable name);\n" +
                "Examples: int *p;\n" +
                "struct student *classmember;"));
        return quickReferences;
    }

    public static ArrayList<QuickReference> getCPPQuickReference() {
        ArrayList<QuickReference> quickReferences = new ArrayList<>();
        quickReferences.add( new QuickReference("C++ Fundamental Data Types", "bool Boolean type (values are true or false)\n" +
                "char Character\n" +
                "short Usually a 2-byte integer\n" +
                "int Usually a 4-byte integer\n" +
                "long Same as long int (long size >= int size)\n" +
                "float Single precision floating point usually 4 bytes\n" +
                "double double precision floating point usually 8 bytes"));
        quickReferences.add( new QuickReference("Type Modifiers", "C++ has type modifiers unsigned and long. unsigned may be\n" +
                "applied to integral types (including char). long may be\n" +
                "applied to int or double. An unsigned data type only allows\n" +
                "non-negative integers to be stored (integers >= 0).\n" +
                "Default integral data type is int.\n" +
                "Default floating-point data type is double.\n" +
                "“integral” and “floating-point” are examples of categories of data. bool,\n" +
                "char, int, double etc. are C++ data types."));
        quickReferences.add( new QuickReference("Literals", "Examples of literals in C++ Data Type\n" +
                "true, false bool\n" +
                "‘2’, ‘\\n’, ‘A’ char\n" +
                "2, 3, 75 int\n" +
                "2.0, .5, 5. double\n" +
                "“2”, “”, “Hello World!” c-string"));
        quickReferences.add( new QuickReference("Auto", "C++11 auto keyword for declaring variables:\n" +
                "auto a=5; auto b=2.5; //a is type int, b is type double\n" +
                "Type is determined by the initializer for the variable."));
        quickReferences.add( new QuickReference("Casting and String representation", "Converting between data types using a C++ cast: <type>, (expression)\n" +
                "int x=3, y=5; cout << “x / y is “ << static_cast<double>(x) / y;\n" +
                "output: x / y is .6\n" +
                "Strings are represented in C++ as either char arrays or string objects.\n" +
                "#include<cstring> functions for c-strings.\n" +
                "#include<string> functions for string objects\n" +
                "char firstNname[16]; //firstName is a “c-string”\n" +
                "string lastName; //lastName is a string object "));
        quickReferences.add( new QuickReference("C++ Operators", "Assignment Operators\n" +
                " = Assignment\n" +
                " += Combined addition/assignment\n" +
                " -= Combined subtraction/assignment\n" +
                " *= Combined multiplication/assignment\n" +
                " /= Combined division/assignment\n" +
                " %= Combined modulus/assignment\n" +
                "Arithmetic Operators\n" +
                " + Addition\n" +
                " - Subtraction\n" +
                " * Multiplication\n" +
                " / Division (floating-point or integer)\n" +
                "2.0/3.0 =.666667 (floating-point), 2/3 = 0 (integer)\n" +
                " % Modulus (integer remainder)\n" +
                "17 % 3 = 2, 12 % 15 = 12\n" +
                "Relational Operators\n" +
                " < Less than\n" +
                " <= Less than or equal to\n" +
                " > Greater than\n" +
                " >= Greater than or equal to\n" +
                " == Equal to\n" +
                " != Not equal to\n" +
                "Logical Operators\n" +
                " && AND\n" +
                " || OR\n" +
                " ! NOT\n" +
                "Increment/Decrement\n" +
                " ++ Increment\n" +
                " -- Decrement\n" +
                "Increment/Decrement (used in prefix and postfix mode)\n" +
                "prefix: inc(dec) variable, then use in larger expression\n" +
                "postfix: use in larger expression, then inc(dec) variable"));
        quickReferences.add( new QuickReference("Selection Structures", "\uF0B7 Unary or Binary selection\n" +
                "\uF0B7 if unary\n" +
                "\uF0B7 if-else binary\n" +
                "\uF0B7 Case structure\n" +
                "\uF0B7 Switch\n" +
                "\uF0B7 Simple selection\n" +
                "\uF0B7 One condition\n" +
                "\uF0B7 score > 90\n" +
                "\uF0B7 Compound selection\n" +
                "\uF0B7 Multiple conditions\n" +
                "joined with AND / OR\n" +
                "operators\n" +
                "\uF0B7 score < 0 || score > 100"));
        quickReferences.add( new QuickReference("Random Number Generation", "#include<cstdlib>\n" +
                "#include<ctime>\n" +
                "//seed srand function\n" +
                "srand(time(0)); //only do once!\n" +
                "expression: 6 + rand() % 37\n" +
                "shift=6, scale=37, range: [6, 42]\n" +
                "0..36 + 6 --> 6 .. 42\n" +
                "range: [-3, 52]\n" +
                "shift = -3, scale = 56 (52-(-3) + 1)\n" +
                "expression: -3 + rand() % 56\n" +
                "0..55 + -3 --> -3 .. 52"));
        quickReferences.add( new QuickReference("Forms of the if Statement", "Simple if Example\n" +
                "if (expression) \n" +
                " statement; \n" +
                "if (x < y)\n" +
                "x++;\n" +
                "if/else Example\n" +
                "if (expression) \n" +
                " statement;\n" +
                "else\n" +
                " statement;\n" +
                "if (x < y)\n" +
                "x++;\n" +
                "else\n" +
                "x--;\n" +
                "" +
                "if/else if (nested if) Example\n" +
                "if (expression)    if (x < y)\n" +
                " statement;        x++;\n" +
                "else               else\n" +
                " if (expression)   if (x < z)\n" +
                " statement;        x--;\n" +
                " else              else\n" +
                " statement;        y++;\n" +
                "To conditionally execute more than one statement, you must\n" +
                "create a compound statement (block) by enclosing the\n" +
                "statements in braces ( this is true for loops as well ):\n" +
                "The\n" +
                "\"expression\" in\n" +
                "the parentheses\n" +
                "for an\n" +
                "if statement\n" +
                " or\n" +
                "loop\n" +
                "is often also\n" +
                "referred to as a\n" +
                "\"condition\""));

        quickReferences.add( new QuickReference("Conditional Operator ", "(Simplified if-else)\n" +
                "Form: expr1 ? expr2 : expr3;\n" +
                "Example: x = a < b ? a : b;\n" +
                "The statement above works like:\n" +
                "if (a < b) x = a;\n" +
                "else x = b;") );
        quickReferences.add( new QuickReference("Escape Sequences", "Special characters in Java\n" +
                "\\n newline character '\\n'\n" +
                "\\t tab character '\\t'\n" +
                "\\\" double quote '\\\"'\n" +
                "\\' single quote '\\''\n" +
                "\\\\ backslash '\\\\'") );
        quickReferences.add( new QuickReference("Arithmetic Operator Precedence", "( )\n" +
                " ----------\n" +
                " *, /, %\n" +
                " ----------\n" +
                " +, -\n" +
                "Precedence of operator types in a mixed\n" +
                "expression:\n" +
                "(1) arithmetic (2) relational\n" +
                "(3) logical (4) assignment") );
        quickReferences.add( new QuickReference("Loop Structure Information", "\uF0B7 C++ Pre-test loops\n" +
                "\uF0B7 while, for\n" +
                "\uF0B7 C++ Post-test loop\n" +
                "\uF0B7 do…while\n" +
                "Loop Control:\n" +
                "\uF0B7 Counter-controlled\n" +
                "aka definite loops have\n" +
                "3 expressions:\n" +
                "\uF0B7 Initialization, Test, Update\n" +
                "\uF0B7 Sentinel-controlled\n" +
                "aka indefinite loops have 2 expressions:\n" +
                "\uF0B7 Test ,Update (Alter)\n" +
                "\uF0B7 C++ Loop Early Exit:\n" +
                "\uF0B7 break statement\n" +
                "\uF0B7 C++ also has a continue statement to skip\n" +
                "statements and proceed to the testexpression.") );
        quickReferences.add( new QuickReference("The switch/case Construct ( break and default are optional )", "Form: Example:\n" +
                "switch (expression) switch (choice)\n" +
                "{ {\n" +
                " case int-constant : case 0 :\n" +
                " statement(s); cout << “You selected 0.” << endl;\n" +
                " [ break; ] break;\n" +
                " case int-constant : case 1:\n" +
                " statement(s); cout << “You selected 1.” << endl;\n" +
                " [ break; ] break;\n" +
                " [ default : default :\n" +
                " statement; ] cout << “Select 0 or 1.” << endl;\n" +
                "} }\n" +
                "The type of the \"expression\" is integral - usually an expression of type int but it could also be\n" +
                "an expression of type char.\n" +
                "Use the break keyword to exit the structure (avoid “falling through” other cases).\n" +
                "Use the default keyword to provide a default case if none of the case expressions match\n" +
                "(similar to trailing “else” in an if-else-if statement).") );

        quickReferences.add( new QuickReference("C++11 ranged for loop: ", "Form: for ( type [&] variable : collection ) stmt;" +
                "The collection can be a C-style array, array object, vector object, etc.\n" +
                "array<int, 20> myArray; //declare a 20 element array class object of type int\n" +
                "for (int param : myArray) //would iterate over all 20 elements\n" +
                "Each iteration variable param would be assigned the value of the current element\n" +
                "in myArray. Access to the collection is read-only unless a reference variable is used;\n" +
                "Reference variable example: for (int & param : MyArray)\n" +
                " param *= 2;"));

        quickReferences.add( new QuickReference("The for Loop", "Form:\n" +
                "for (init; test; update) \n" +
                "{\n" +
                " statement; \n" +
                " statement; \n" +
                "}\n" +
                "Example:\n" +
                "for (count = 0; count < 10; count++)\n" +
                "{\n" +
                "cout << \"The value of count is \";\n" +
                "cout << count << endl;\n" +
                "}"));
        quickReferences.add( new QuickReference("The while Loop", "Form:    Example:\n" +
                "init;          int x=0;\n" +
                "while (test)   while (x < 100)\n" +
                "{              {\n" +
                " statement(s);     cout << x << endl;\n" +
                " update;           x++;\n" +
                "}               }"));
        quickReferences.add( new QuickReference("The do-while Loop", "Form: Example:\n" +
                "do                     do\n" +
                " statement;             cout << x++ <<\n" +
                "endl;\n" +
                "while (expression);    while (x < 100);\n" +
                "do                     do\n" +
                "{                      {\n" +
                " statement;                cout << x << endl;\n" +
                " statement;                x++;\n" +
                "} while (expression);  } while (x < 100);"));
        quickReferences.add( new QuickReference("Using cin and cout", "Using cin / cout Requires iostream header file: #include<iostream>\n" +
                "Stream Manipulators: Parameterized stream manipulators require: #include<iomanip>\n" +
                "Name :Description\n" +
                "fixed :changes mode to fixed; numbers display with integer and decimal\n" +
                "digits\n" +
                "setprecision( ) : sets the number of significant digits or decimal digits if used in\n" +
                "conjunction with fixed.\n" +
                "setw( ) : [not persistent] sets field width (used for input and output )\n" +
                "left : sets left justification\n" +
                "right : sets right justification\n" +
                "showpoint : forces decimal point & trailing zeros to display\n" +
                "scientific : sets scientific notation\n\n" +
                "Creating and using file stream objects: Requires fstream header file: #include<fstream>\n" +
                "\n" +
                "Classes for creating file stream objects Use >> and << operators!\n\n" +
                "ifstream create an object for use with an input file inFile >> num1 >> num2;\n\n" +
                "ofstream create an object for use with an output file outFile << sum << endl;\n\n" +
                "Member Functions for file stream classes\n" +
                ".open( ) infile.open(\"data.txt\")\n" +
                ".close( ) infile.close()\n" +
                ".fail( ) infile.fail() test for stream failure ( T/F )\n" +
                ".clear( ) infile.clear() //reset stream status to good\n" +
                ".eof( ) infile.eof() //test for end of file condition ( T/F )\n" +
                ".peek( ) read next character but don't remove it from the input buffer\n" +
                ".unget( ) put last character read back into the input buffer. Replaced the\n" +
                "putback( ) function.\n" +
                ".read(address, size) read from a binary file binFile.read(data, sizeof(data));\n" +
                ".write(address,size) write to a binary file binFile.write(data, sizeof(data));"));
        quickReferences.add( new QuickReference("Member functions for input formatting using a stream object\n" +
                "( such as cin )", 
                ".getline(array, size) Reads at most size-1 characters.\n" +
                "Appends '\\0'. Stops at '\\n' by default.\n" +
                "Consumes the newline character\n" +
                ".get(array, size) Reads at most size-1 characters.\n" +
                "Appends '\\0'. Stops at '\\n' by default.\n" +
                "Does not consume the newline\n" +
                "character\n" +
                ".get(ch) reads a character ( including whitespace )\n" +
                ".get( ) reads a character: char = cin.get( );\n" +
                ".ignore( ) removes last character entered from buffer\n" +
                ".ignore(50,'\\n') removes last 50 characters from input\n" +
                " buffer or until it sees a newline character"));
        quickReferences.add( new QuickReference("String - getline()", "The string class provides a getline( ) function to input a\n" +
                "string. This is not the same as cin.getline( )!\n" +
                " getline(<stream_object>, <string>);\n" +
                " Example: string s; getline(cin, s);"));

        return quickReferences;
    }

    public static ArrayList<QuickReference> getCQuickReference() {
        ArrayList<QuickReference> quickReferences = new ArrayList<>();
        quickReferences.add( new QuickReference("Structure of a C Program", "#include(stdio.h) /* include IO library */\n" +
                "#include... /* include other files */\n" +
                "#define.. /* define constants */\n" +
                "/* Declare global variables*/)\n" +
                "(variable type)(variable list);\n" +
                "/* Define program functions */\n" +
                "(type returned)(function name)(parameter list)\n" +
                "(declaration of parameter types)\n" +
                " {\n" +
                "(declaration of local variables);\n" +
                "(body of function code);\n" +
                " }\n" +
                "/* Define main function*/\n" +
                "main ((optional argc and argv arguments))\n" +
                "(optional declaration parameters)\n" +
                "{\n" +
                "(declaration of local variables);\n" +
                "(body of main function code);\n" +
                "}"));
        quickReferences.add( new QuickReference("Comments", "Format: /*(body of comment) */\n" +
                "Example: /*This is a comment in C*/"));
        quickReferences.add( new QuickReference("Constant Declarations", "Format: #define(constant name)(constant value)\n" +
                "Example: #define MAXIMUM 1000"));
        quickReferences.add( new QuickReference("Type Definitions", "Format: typedef(datatype)(symbolic name);\n" +
                "Example: typedef int KILOGRAMS;"));
        quickReferences.add( new QuickReference("Variables", "Declarations:\n" +
                "Format: (variable type)(name 1)(name 2),...;\n" +
                "Example: int firstnum, secondnum;\n" +
                "char alpha;\n" +
                "int firstarray[10];\n" +
                "int doublearray[2][5];\n" +
                "char firststring[1O];\n" +
                "Initializing:\n" +
                "Format: (variable type)(name)=(value);\n" +
                "Example: int firstnum=5;\n" +
                "Assignments:\n" +
                "Format: (name)=(value);\n" +
                "Example: firstnum=5;\n" +
                "Alpha='a';"));
        quickReferences.add( new QuickReference("Unions", "Declarations:\n" +
                "Format: union(tag)\n" +
                "{(type)(member name);\n" +
                " (type)(member name);\n" +
                "...\n" +
                "}(variable name);\n" +
                "Example: union demotagname\n" +
                "{int a;\n" +
                "float b;\n" +
                "}demovarname;\n" +
                "Assignment:\n" +
                "Format: (tag).(member name)=(value);\n" +
                "demovarname.a=1;\n" +
                "demovarname.b=4.6;"));
        quickReferences.add( new QuickReference("Structures", "Declarations:\n" +
                "Format: struct(tag)\n" +
                "{(type)(variable);\n" +
                " (type)(variable);\n" +
                "...\n" +
                "}(variable list);\n" +
                "Example: struct student\n" +
                "{int idnum;\n" +
                "int finalgrade;\n" +
                "char lettergrade;\n" +
                "} first,second,third;\n" +
                "Assignment:\n" +
                "Format: (variable name).(member)=(value);\n" +
                "Example: first.idnum=333;\n" +
                "second.finalgrade=92;"));
        quickReferences.add( new QuickReference("Operators", "Symbol Operation Example\n" +
                "+,-,*,/ arithmetic 1 = b + c;\n" +
                "% mod a = b % c;\n" +
                "> greater than if (a > b)\n" +
                ">= greater than or equal if (a >= b)\n" +
                "< less than if (a <b)\n" +
                "<= less than or equal if (a <= b)\n" +
                "== equality if ( == b)\n" +
                "= assignment a=25;\n" +
                "!= not equal if (a != b)\n" +
                "! not if (!a)\n" +
                "&& logical and if (a) && (b)\n" +
                "logical or if (a) (b)\n" +
                "++ increment ++ a;\n" +
                "-- decrement -- a;\n" +
                "& bitwise and a = b & c;\n" +
                "bitwise or a = b - c;\n" +
                "bitwise xor\n" +
                ">> shift-right a = b >> 2;\n" +
                "<< shift-left a = b << 2;\n" +
                "~ one's complement a = ~b"));
        quickReferences.add( new QuickReference("Input and Output", "Output\n" +
                "Print Formats:\n" +
                "String: print(\"(literal string)\");\n" +
                "String+newline: print (\"(string)\\n\");\n" +
                "Variables: printf(\"(conversion specs)\",(variables));\n" +
                "Print Examples:\n" +
                "print(\"firstvar+secondvar=%d\\n\",thirdvar);\n" +
                "Print Conversion Specifications:\n" +
                "%d decimal\n" +
                "%u unsigned decimal\n" +
                "%o octal\n" +
                "Ÿ\n" +
                "a = b Ÿ c\n" +
                "%h hex\n" +
                "%e exponential\n" +
                "%f float\n" +
                "%g shorter of %e or %f\n" +
                "%c char\n" +
                "%s string\n" +
                "Print Escape Sequences:\n" +
                "\\n newline\n" +
                "\\t tab\n" +
                "\\r carriage return\n" +
                "\\f form feed\n" +
                "\\b backspace\n" +
                "\\' output\n" +
                "\\\\ output \\\n" +
                "Input:\n" +
                "Scanf Format:\n" +
                "scanf(\"(conversion specs)\",&(varl),&(var2),...);\n" +
                "Scanf Example:\n" +
                "scanf(\"%d %d %d\",&first,&second,&third);\n" +
                "Scanf Conversion Specifications:\n" +
                "%d decimal integer expected\n" +
                "%o octalinteger expected\n" +
                "%x hex integer expected\n" +
                "%h short integer expected\n" +
                "%c character expected\n" +
                "%s string expected\n" +
                "%r real value expected\n" +
                "%e exponential notation expected\n" +
                "Primitive Input and Output Examples:\n" +
                "Get a character from standard input: c = getchar();\n" +
                "Put a character on standard output: putcher(c);"));

        quickReferences.add( new QuickReference("FOR LOOP Format:", "for ((first expr);(second expr);(third expr))\n" +
                "(simple statement);\n" +
                "for ((first expr);(second expr);(third expr))\n" +
                "{\n" +
                "(compound statement);") );
        quickReferences.add( new QuickReference("WHILE LOOP Format:", "while ((condition))\n" +
                " (simple statement);\n" +
                "while ((condition))\n" +
                "{\n" +
                "(compound statement);") );
        quickReferences.add( new QuickReference("DO WHILE LOOP Format:", "do\n" +
                "(simple statement)'\n" +
                "while ((condition))\n" +
                "do {\n" +
                " (compound statement);\n" +
                "}\n" +
                "while ((condition));") );
        quickReferences.add( new QuickReference("IF CONDITIONAL Format:", "if ((condition))\n" +
                "(simple statement);\n" +
                "if ((condition))\n" +
                "{\n" +
                " (compound statement);\n" +
                "}") );
        quickReferences.add( new QuickReference("IF... ELSE CONDITIONAL Format:", "if ((condition))\n" +
                "(statement 1);\n" +
                "else\n" +
                "(statement 2);") );
        quickReferences.add( new QuickReference("SWITCH Format:", "switch ((expression))\n" +
                "{case (value 1):(statement 1);\n" +
                "case (value 2):(statement 2);\n" +
                "...\n" +
                "default:(default statement);\n" +
                "}") );

        quickReferences.add( new QuickReference("Function Definitions", "Format:\n" +
                "(type returned)(function name)((parameter list))\n" +
                "(declaration of parameter list variables)\n" +
                "{\n" +
                " (declaration of local variables);\n" +
                " (body of function code);\n" +
                "}\n" +
                "Example:\n" +
                "Int. adder(a,b)\n" +
                "int a,b;\n" +
                " {int c;\n" +
                " c = a + b;\n" +
                " return (c);\n" +
                " }\n" +
                "Pointers\n" +
                "Declaration of pointer variable:\n" +
                "Format: (type)*(variable name);\n" +
                "Examples: int *p;\n" +
                "struct student *classmember;"));
        return quickReferences;
    }
}
