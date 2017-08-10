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
        quickReferences.add(new QuickReference("AND / OR", "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "WHERE condition\n" +
                "AND|OR condition"));
        quickReferences.add(new QuickReference("ALTER TABLE", "ALTER TABLE table_name\n" +
                "ADD|DROP COLUMN column_name datatype"));
        quickReferences.add(new QuickReference("AS (alias) ", "SELECT column_name AS column_alias\n" +
                "FROM table_name\n" +
                "or\n" +
                "SELECT column_name\n" +
                "FROM table_name AS table_alias"));
        quickReferences.add(new QuickReference("BETWEEN ", "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "WHERE column_name\n" +
                "BETWEEN value1 AND value2"));
        quickReferences.add(new QuickReference("CREATE TABLE ", "CREATE TABLE table_name\n" +
                "(\n" +
                "column_name1 data_type,\n" +
                "...\n" +
                ")"));
        quickReferences.add(new QuickReference("CREATE INDEX ", "CREATE INDEX index_name\n" +
                "ON table_name (column_name)"));
        quickReferences.add(new QuickReference("CREATE VIEW ", "CREATE VIEW view_name AS\n" +
                "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "WHERE condition"));
        quickReferences.add(new QuickReference("DELETE", "DELETE FROM table_name\n" +
                "WHERE some_column=some_value"));
        quickReferences.add(new QuickReference("DROP INDEX ", "DROP INDEX index_name (MySQL)"));
        quickReferences.add(new QuickReference("DROP TABLE ", "DROP TABLE table_name"));
        quickReferences.add(new QuickReference("GROUP BY ", "SELECT column_name, aggregate_function(column_name)\n" +
                "FROM table_name\n" +
                "WHERE column_name operator value\n" +
                "GROUP BY column_name"));
        quickReferences.add(new QuickReference("HAVING", "SELECT column_name, aggregate_function(column_name)\n" +
                "FROM table_name\n" +
                "WHERE column_name operator value\n" +
                "GROUP BY column_name\n" +
                "HAVING aggregate_function(column_name) operator value"));
        quickReferences.add(new QuickReference("IN ", "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "WHERE column_name\n" +
                "IN (value1,value2,..)"));
        quickReferences.add(new QuickReference("INSERT INTO ", "INSERT INTO table_name\n" +
                "VALUES (value1, value2, value3,....)\n" +
                "or\n" +
                "INSERT INTO table_name\n" +
                "(column1, column2, column3,...)\n" +
                "VALUES (value1, value2, value3,....)"));
        quickReferences.add(new QuickReference("INNER JOIN", "SELECT column_name(s)\n" +
                "FROM table_name1\n" +
                "INNER JOIN table_name2\n" +
                "ON table_name1.column_name=table_name2.column_name"));
        quickReferences.add(new QuickReference("LEFT JOIN", "SELECT column_name(s)\n" +
                "FROM table_name1\n" +
                "LEFT JOIN table_name2\n" +
                "ON table_name1.column_name=table_name2.column_name"));
        quickReferences.add(new QuickReference("RIGHT JOIN ", "SELECT column_name(s)\n" +
                "FROM table_name1\n" +
                "RIGHT JOIN table_name2\n" +
                "ON table_name1.column_name=table_name2.column_name"));
        quickReferences.add(new QuickReference("FULL JOIN", "SELECT column_name(s)\n" +
                "FROM table_name1\n" +
                "FULL JOIN table_name2\n" +
                "ON table_name1.column_name=table_name2.column_name"));
        quickReferences.add(new QuickReference("LIKE ", "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "WHERE column_name LIKE pattern"));
        quickReferences.add(new QuickReference("ORDER BY ", "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "ORDER BY column_name [ASC|DESC]"));
        quickReferences.add(new QuickReference("SELECT", "SELECT column_name(s)\n" +
                "FROM table_name"));
        quickReferences.add(new QuickReference("SELECT *", "SELECT *\n" +
                "FROM table_name"));
        quickReferences.add(new QuickReference("SELECT DISTINCT", "SELECT DISTINCT column_name(s)\n" +
                "FROM table_name"));
        quickReferences.add(new QuickReference("SELECT TOP ", "SELECT TOP number|percent column_name(s)\n" +
                "FROM table_name"));
        quickReferences.add(new QuickReference("TRUNCATE TABLE", "TRUNCATE TABLE table_name"));
        quickReferences.add(new QuickReference("UNION ", "SELECT column_name(s) FROM table_name1\n" +
                "UNION\n" +
                "SELECT column_name(s) FROM table_name2"));
        quickReferences.add(new QuickReference("UNION ALL ", "SELECT column_name(s) FROM table_name1\n" +
                "UNION ALL\n" +
                "SELECT column_name(s) FROM table_name2"));
        quickReferences.add(new QuickReference("UPDATE ", "UPDATE table_name\n" +
                "SET column1=value, column2=value,...\n" +
                "WHERE some_column=some_value"));
        quickReferences.add(new QuickReference("WHERE", "SELECT column_name(s)\n" +
                "FROM table_name\n" +
                "WHERE column_name operator value"));
        return quickReferences;
    }

    public static ArrayList<QuickReference> getJavaQuickReference() {
        ArrayList<QuickReference> quickReferences = new ArrayList<>();
        quickReferences.add( new QuickReference("Arithmetic Operators", "+ Addition\n" +
                "- Subtraction\n" +
                "/ Division (int / floating-point)\n" +
                " 2/3 = 0, 2.0/3.0 =.666667\n" +
                "* Multiplication\n" +
                "% Modulus (integer remainder)"));
        quickReferences.add( new QuickReference("Relational/Equality Operators", "< Less than\n" +
                "<= Less than or equal to\n" +
                "> Greater than\n" +
                ">= Greater than or equal to\n" +
                "== Equal to\n" +
                "!= Not equal to"));
        quickReferences.add( new QuickReference("Logical Operators", "! NOT\n" +
                "&& AND\n" +
                "|| OR"));
        quickReferences.add( new QuickReference("Assignment Operators", "= simple assignment\n" +
                "+= addition/assignment\n" +
                "-= subtraction/assignment\n" +
                "*= multiplication/assignment\n" +
                "/= division/assignment\n" +
                "%= modulus/assignment"));
        quickReferences.add( new QuickReference("Increment ++ /Decrement -- operators used in prefix and postfix modes", "Declarations:\n" +
                "++/-- prefix mode - inc(dec) variable, use variable in the larger expression\n" +
                " ++/-- postfix mode - use variable in larger expression, inc(dec) variable"));
        quickReferences.add( new QuickReference("Object Creation", "Object Creation: ( new ) new int[ 10 ], new GradeBook(\"CIS 182\")\n" +
                "The new operator creates an object and returns a reference (address of an object)"));
        quickReferences.add( new QuickReference("Java Types [value/reference ]", "A value type stores a value of a primitive type int x = 3;\n" +
                " A reference type stores the address of an object Circle c = new Circle(2);\n" +
                " A reference variable is created using a class name: GradeBook myGradeBook;"));
        quickReferences.add( new QuickReference("Primitive Data Types ( Java value types )", "boolean flag / logical true, false [ boolean literals ]\n" +
                " char character 'A', 'n', '!' [ char literals ]\n" +
                " byte, short, int, long integral 2, 3, 5000, 0 [ int literals ]\n" +
                " float, double floating-point 123.456, .93 [ double literals ]"));

        quickReferences.add( new QuickReference("Default numeric literal types", "integral: int int x = 3; //3 is an int literal\n" +
                " floating-point: double double y = 2.5; //2.5 is a double literal\n" +
                "Most commonly used reference type in Java is String. String name = \"Jack\";") );
        quickReferences.add( new QuickReference("Input using Scanner class", "Scanner input = new Scanner ( System.in ); //keyboard input\n" +
                " input methods: next(), nextLine(), nextInt(), nextDouble()") );
        quickReferences.add( new QuickReference("Output methods for System.out or PrintWriter objects", "print(), println(), printf() [formatted output]") );
        quickReferences.add( new QuickReference("Input/Output using JOptionPane", "Input/Output using JOptionPane class [ package javax.swing ]\n" +
                " String numString; int num;\n" +
                " numString = JOptionPane.showInputDialog(\"Enter a number\");\n" +
                " num = Integer.parseInt(numString);\n" +
                " JOptionPane.showMessageDialog(null, \"Number is \" + num);") );
        quickReferences.add( new QuickReference("Conversion from a String to a number using Wrapper Classes", "double d = Double.parseDouble(dString);\n" +
                " float f = Float.parseFloat(fString);\n" +
                " int j = Integer.parseInt(jString);") );
        quickReferences.add( new QuickReference("Java formatted output", "Java formatted output [ printf( ) and String.format( ) methods ]\n" +
                "3 components: format string and optionally: format-specifiers ( fs )\n" +
                "and an argument list ( al )\n" +
                "\uF0B7 fs: \" ... % [flags] [width] [precision] format-specifier ... \"\n" +
                "\uF0B7 al: comma separated list of expressions\n" +
                "Format-specifiers: s (string), d (integer), f (floating-point)\n" +
                "Example: System.out.printf(\"Total is %,10.2f\\n\", total);") );

        quickReferences.add( new QuickReference("Java Numeric Conversions and Casts:", "Widening conversions are done implicitly.\n" +
                " double x; int y = 100;\n" +
                " x = y; // value of y implicitly converted to a double.\n" +
                "Narrowing conversions must be done explicitly using a cast.\n" +
                " double x = 100; int y;\n" +
                " y = (int) x; // value of x explicitly cast to an int\n" +
                "In mixed expressions, numeric conversion happens implicitly.\n" +
                "double is the “highest” primitive data type, byte is the “lowest”"));
        quickReferences.add( new QuickReference("Escape Sequences", "Special characters in Java\n" +
                "\\n newline character '\\n'\n" +
                "\\t tab character '\\t'\n" +
                "\\\" double quote '\\\"'\n" +
                "\\' single quote '\\''\n" +
                "\\\\ backslash '\\\\'") );
        quickReferences.add( new QuickReference("Operator Precedence",
                " ( )\n" +
                "----------\n" +
                " *, /, % [ mathematical ]\n" +
                "----------\n" +
                " +, -\n" +
                "Logical operators: !, &&, ||\n" +
                "(1) mathematical (2) relational (3) logical") );

        quickReferences.add( new QuickReference("Loops in Java",
                "The while Loop ( pre-test loop )\n" +
                        "Form: \n" +
                        "init; \n" +
                        "while (test) \n" +
                        "{ \n" +
                        " statement; \n" +
                        " update; \n" +
                        "} \n" +
                        "\n" +
                        "Example:\n" +
                        "x = 0;\n" +
                        "while (x < 10)\n" +
                        "{\n" +
                        "\tsum += x;\n" +
                        "\tx++;\n" +
                        "}\n" +
                        "\n" +
                        "The do-while Loop ( post-test loop )\n" +
                        "Form: \n" +
                        "init; \n" +
                        "do \n" +
                        "{ \n" +
                        " statement; \n" +
                        " update; \n" +
                        "} while (test); \n" +
                        "\n" +
                        "Example:\n" +
                        "x = 0;\n" +
                        "do\n" +
                        "{\n" +
                        "\tsum += x;\n" +
                        "\tx++;\n" +
                        "} while (x < 10);\n" +
                        "\n" +
                        "The for Loop ( pre-test loop )\n" +
                        "Form: \n" +
                        "for (init; test; update) \n" +
                        "{ \n" +
                        " statement; \n" +
                        "} \n" +
                        "\n" +
                        "Example:\n" +
                        "for (int count=1; count<=10; count++)\n" +
                        "{\n" +
                        "\tSystem.out.println( count );\n" +
                        "}\n" +
                        "\n" +
                        "Enhanced for loop: \n" +
                        "\n" +
                        "for (parameter : collection)\n" +
                        " statement;\n" +
                        "\n" +
                        "int scores[ ] = {85, 92, 76, 66, 94}; //collection is the array scores\n" +
                        "for ( int number : scores ) //parameter is the variable number\n" +
                        " System.out.println(number);") );

        quickReferences.add( new QuickReference("Forms of the if Statement",
                "Simple if \n" +
                        "if (expression) \n" +
                        " statement;\n" +
                        "\n" +
                        "Example\n" +
                        "if (x < y)\n" +
                        " x++;\n" +
                        "\n" +
                        "if/else \n" +
                        "if (expression) \n" +
                        " statement; \n" +
                        "else \n" +
                        " statement; \n" +
                        "\n" +
                        "Example\n" +
                        "if (x < y)\n" +
                        "  x++;\n" +
                        "else \n" +
                        "  x--;\n" +
                        "\n" +
                        "if/else if (nested if) \n" +
                        "if (expression) \n" +
                        " statement; \n" +
                        "else \n" +
                        " if (expression) \n" +
                        " statement; \n" +
                        " else \n" +
                        " statement; \n" +
                        "\n" +
                        "Example\n" +
                        "if (x < y)\n" +
                        " x++;\n" +
                        "else\n" +
                        "if (x < z)\n" +
                        " x--;\n" +
                        "else\n" +
                        " y++;\n" +
                        "\n" +
                        "To conditionally execute more than one statement, you must\n" +
                        "create a compound statement (block) by enclosing the statements\n" +
                        "in braces ( this is true for loops as well ):\n" +
                        "Form Example\n" +
                        "if (expression) \n" +
                        "{ \n" +
                        " statement; \n" +
                        " statement; \n" +
                        "} \n" +
                        "\n" +
                        "Example\n" +
                        "if (x < y)\n" +
                        "{\n" +
                        "  x++;\n" +
                        "  System.out.println( x );\n" +
                        "}\n" +
                        "\nThe\n" +
                        "\"expression\" in\n" +
                        "the parentheses\n" +
                        "for an\n" +
                        "if statement\n" +
                        " or\n" +
                        "loop\n" +
                        "is often also\n" +
                        "referred to as a\n" +
                        "\"condition") );

        quickReferences.add( new QuickReference(
                "The switch/case Construct ( break and default are optional )",
                "Form: \n" +
                        "switch (expression) \n" +
                        "{ \n" +
                        " case int-constant : \n" +
                        " statement(s); \n" +
                        " [ break; ] break;\n" +
                        " case int-constant : \n" +
                        " statement(s); \n" +
                        " [ break; ] \n" +
                        " [ default : \n" +
                        " statement; ] \n" +
                        "} \n" +
                        "\n" +
                        "Example:\n" +
                        "switch (choice)\n" +
                        "{\n" +
                        "   case 0 :\n" +
                        "   System.out.println( “You selected 0.” );\n" +
                        "   break;\n" +
                        "\n" +
                        "   case 1:\n" +
                        "   System.out.println( “You selected 1.” );\n" +
                        "   break;\n" +
                        "\n" +
                        "   default :\n" +
                        "   System.out.println( “You did not select 0 or 1.” );\n" +
                        "   \t\n" +
                        "}" +"\n\nThe \"expression\" and “int-constant” are usually type int or char. Java 7\n" +
                        "adds the ability to use a string. switch(behavior) { case “good”: … }\n" +
                        "Use the break keyword to exit the structure (avoid “falling through” other\n" +
                        "cases). Use the default keyword to provide a default case if none of the\n" +
                        "case expressions match (similar to trailing “else” in an if-else-if\n" +
                        "statement).") );
        quickReferences.add( new QuickReference("Selection and Loop Structures", "Selection:\n" +
                "\uF0B7 Unary or single selection\n" +
                "\uF0B7 Binary or dual selection\n" +
                "\uF0B7 Case structure possible when\n" +
                "branching on a variable\n" +
                "\uF0B7 Simple selection\n" +
                "\uF0B7 One condition\n" +
                "\uF0B7 Compound selection\n" +
                "\uF0B7 Multiple conditions joined\n" +
                "with AND / OR operators\n" +
                "Looping:\n" +
                "\uF0B7 Java Pre-test loops\n" +
                "\uF0B7 Test precedes loop body\n" +
                "\uF0B7 while\n" +
                "\uF0B7 for\n" +
                "\uF0B7 Java Post-test loop\n" +
                "\uF0B7 Test follows loop body\n" +
                "\uF0B7 do-while\n" +
                "Loop Control:\n" +
                "\uF0B7 3 types of expressions that\n" +
                "are used to control loops:\n" +
                "\uF0B7 initialization ( init )\n" +
                "\uF0B7 test\n" +
                "\uF0B7 update\n" +
                "\uF0B7 Counter-controlled loops,\n" +
                "aka definite loops, work with\n" +
                "a loop control variable (lcv)\n" +
                "\uF0B7 Sentinel-controlled loops,\n" +
                "aka indefinite loops, work\n" +
                "with a sentinel value\n" +
                "\uF0B7 Java Loop Early Exit:\n" +
                "\uF0B7 break statement\n" +
                "Note: The break statement can\n" +
                "be used with a switch\n" +
                "statement or a loop in\n" +
                "Java. Loops may also use\n" +
                "a continue statement."));
        quickReferences.add( new QuickReference("Java Arrays", "Java Arrays: Create an array ( 2 ways )\n" +
                "1. <type> <array-name>[ ] = new <type>[size];\n" +
                "2. <type> <array-name>[ ] = { <initializer-list> };\n" +
                "//create an array of 20 elements.\n" +
                "int myArray[ ] = new int[20];\n" +
                "//create an array of 3 elements set to the values in the initializer list.\n" +
                "int myArray[ ] = { 1, 2, 3 };\n" +
                "String stooges[ ] = { \"Moe\", \"Larry\", \"Curly\" };\n" +
                " //assign value of first element in myArray to the integer variable x.\n" +
                "int x = myArray[0];\n" +
                "//assign value of the last element in myArray to the integer variable y.\n" +
                "int y = myArray[ myArray.length-1 ];\n" +
                "All arrays have a public field named length which holds the number of elements in the array.\n" +
                "Given this declaration: int x[][][];\n" +
                "x.length is the number of elements in the array in the first dimension.\n" +
                "x[m].length is the number of elements for a specific array in the second dimension.\n" +
                "x[m][n].length is the number of elements for a specific array in the third dimension.\n\n" +
                "Use the ArrayList class to\n" +
                "create a dynamically\n" +
                "resizable array.\n" +
                "The Arrays class has static\n" +
                "methods that can be used\n" +
                "with arrays and ArrayLists to\n" +
                "search, sort, copy, compare\n" +
                "for equality, etc.\n" +
                "int num[ ]; … <stmts> ….\n" +
                "Create a new initialized\n" +
                "array and assign to num.\n" +
                "num = new int[ ]{1,2,3,4,5};"));
        quickReferences.add( new QuickReference("Java Methods", "Java Methods: <type> <method-name> ( [ <type> parameter1, [ <type parameter2, … ] ] )\n" +
                "Methods that will not return a value will have the return type void in the method header.\n" +
                "void printHeadings( ) //no parameters, return type is void\n" +
                "{ <method body> }\n" +
                "void printDetailLine( String name, int number, double gpa ) //3 parameters, return type is void\n" +
                "{ <method body> }\n" +
                "int getCount( ) //no parameters, return type is int\n" +
                "{ <method body> }\n" +
                "double max( double x, double y ) //2 parameters, return type is double\n" +
                "{ <method body> }\n" +
                "When a method is called, the data is passed to the parameters (if any) using arguments\n" +
                "//Arguments: \"Jack Wilson\", 100, 3.50 passed to Parameters: name, number, gpa for Method:\n" +
                "printDetailLine (see method header above) : printDetailLine( \"Jack Wilson\", 100, 3.50);\n" +
                "A method may be declared with one variable length parameter. It must be the last parameter\n" +
                "declared. The syntax of the declaration is <type> ... <parameter-name>.\n" +
                "Examples: int... numbers, double ... values, String ...names //implicit array creation"));
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
