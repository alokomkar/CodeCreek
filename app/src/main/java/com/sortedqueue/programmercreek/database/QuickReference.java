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
