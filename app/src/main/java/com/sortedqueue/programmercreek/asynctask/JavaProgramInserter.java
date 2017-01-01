package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;

import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;

/**
 * Created by Alok on 21/12/16.
 */

public class JavaProgramInserter {

    private Context context;

    public JavaProgramInserter(Context context) {
        this.context = context;
    }

    /*public void insertPrograms( ) {
        CommonUtils.displayProgressDialog(context, "Inserting programs");
        int index = 1;

        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Largest of three numbers", "http://codescracker.com/cpp/program/cpp-program-find-greatest-of-three-numbers.htm", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "To find the discount ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "To find the case of a character", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Leap year or not", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Total days to year,month,days", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "To find area of an isosceles triangle", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Area and circumference of a circle", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Swapping two values ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "To generate electricity bill", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Factorial of a given number", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Result generator", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Sum", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Fibonacci series", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Sum and average", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Bubble sort", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Binary search", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Sum of two matrix", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Sum of rows and columns", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Vowels and consonants count", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Largest and second largest in an array", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Frequency of a given element", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Simple interest", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Function overloading", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Cube of a number", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Palindrome or not ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Prime or not", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "String copy", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Sine series ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new Program_Index(index++, "Polynomial", "https://programercreek.blogspot.in/", "c++"));
        CommonUtils.dismissProgressDialog();
    }*/

    public void insertProgramTables( ) {
        CommonUtils.displayProgressDialog(context, "Inserting program tables");
        int programIndex = 24;
        int lineNo = 1;
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"inline int cube(int a)",  "cube function definition - inline",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," return(a*a*a);",  "return(a*a*a);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"}",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int x,y;",  "Variable declaration - x,y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the value for x\"<<endl;",  "Print Enter the value for x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin>>x;",  "Read x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," y=cube(x);",  "Assign y=cube(x);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"The cube of \"<<x<<\" is \"<<y<<endl;",  "Print The cube of x is y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "void main() {", "Main declaration", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int n,rev=0,num,rem;",  "Variable declaration - n,rev,num,rem",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the number\"<<endl;",  "Print Enter the number",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin>>num;",  "Read num",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," n=num;",  "Assign n=num;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," while(num!=0)",  "while loop till num != 0",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  rem=num%10;",  "Assign rem=num%10;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  num=num/10;",  "Assign num=num/10;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  rev=(rev*10)+rem;",  "Assign rev=(rev*10)+rem;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," if(rev==n)",  "Check if rev = n?",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  cout<<n<<\"is a palindrome\"<<endl;",  "Print n is a palindroid",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," else",  "else",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  cout<<n<<\"is not a palindrome\"<<endl;",  "Print n is not a palindrome",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"int isprime(int num)",  "Function definition isprime",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start function isprime",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int i;",  "Variable declaration - i",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," for(i=2;i<=n/2;i++)",  "for loop from 2 to n/2",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  if(num%i==0)",  "Check if num%i = 0 ?",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  return 0;",  "return 0;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," return 1;",  "return 1;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"}",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int x,y,I;",  "Variable declaration - x,y,l",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<\"Enter a range\"<<endl;",  "Print Enter a range",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin>>x>>y;",  "Read x,y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"The prime numbers are \"<<endl;",  "Print The prime numbers are",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," for(i=x;i<=y;i++)",  "For loop from x to y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  if(isprime(i))",  "Check isprime(i)",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"   cout<<I<<endl;",  "Print l",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"void strcopy(char s1[50],char s2[50])",  "strcopy function definition",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start function strcopy",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int i=0;",  "Variable declaration - i",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," while(s1[i]!='\\0')",  "while loop till s1[i] != '\\0'",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  s2[i]=s1[i];",  "Assign s2[i]=s1[i];",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  i++;",  "Assign i++;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," s2[i]='\\0';",  "Assign s2[i]='\\0';",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"}",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," char str1[50],str2[50];",  "Variable declaration - str1[50],str2[50]",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the source string\"<<endl;",  "Print Enter the source string",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin.getline(str1);",  "Read line",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," strcopy(str1,str2);",  "call function strcopy(str1,str2);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Destination string is \"<<str2<<endl;",  "Print Destination string is",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"#include<math.h>",  "Header include - math.h",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int i,degree;",  "Variable declaration - i, degree",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," float x,sum=0,term,I,pi=3.142;",  "Variable declaration - x,y,sum,term,l,pi",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the value of degree \"<<endl;",  "Print Enter the value of degree",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin>>degree;",  "Read degree",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," x=degree*(pi/180);",  "Assign x=degree*(pi/180);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," term=x;",  "Assign term=x;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," sum=term;",  "Assign sum=term;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," for(i=3;i<=n;i+=2)",  "For loop from 3 to n",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  term=(term*x*x)/((i-1)*i);",  "Assign term=(term*x*x)/((i-1)*i);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  sum=sum+term;",  "Assign sum=sum+term;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"sine of \"<<degree<<\" is \"<<sum<<endl;",  "Print sine of degree is sum",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," int n,I,sum=0,a[10],x;",  "Variable declaration - n,l,sum,a[10],x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the no. of co-efficients \"<endl;",  "Print Enter no. of co-efficients",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin>>n;",  "Read n",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the co-efficients\"<<endl;",  "Print Enter the co-efficients",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," for(i=n;i>=0;i--)",  "for loop from n to 0",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  cin>>a[i];",  "Read a[i]",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Enter the value of x\"<<endl;",  "Print Enter the value of x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cin>>x;",  "Read x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," for(i=n;i>=0;i--)",  "for loop from n to 0",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++,"  sum=(sum+a[i])*x;",  "Assign sum=(sum+a[i])*x;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," sum=sum+a[0];",  "Assign sum=sum+a[0];",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++," cout<<\"Sum = \"<<sum<<endl;",  "Print Sum= ",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of main", "c++"));

        CommonUtils.dismissProgressDialog();
    }

    public void insertLanguageModules() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        int moduleId = 1;
        String programLanguage = new CreekPreferences(context).getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }

        moduleId = 1;
        String generatedId = programLanguage + "_" + moduleId++;
        ArrayList<ModuleOption> moduleOptions = new ArrayList<>();
        int index = 0;
        moduleOptions.add(new ModuleOption(index, ""));
        /*moduleOptions.add(new ModuleOption(index++, " numbers )"));
        moduleOptions.add(new ModuleOption(index++, "for("));
        moduleOptions.add(new ModuleOption(index++, ":"));
        moduleOptions.add(new ModuleOption(index++, " int x "));*/
        /*moduleOptions.add(new ModuleOption(index++, "1"));
        moduleOptions.add(new ModuleOption(index++, "2"));
        moduleOptions.add(new ModuleOption(index++, "3"));
        moduleOptions.add(new ModuleOption(index++, "4"));*/
        /*moduleOptions.add(new ModuleOption(index++, "Namasthe"));
        moduleOptions.add(new ModuleOption(index++, "e"));
        moduleOptions.add(new ModuleOption(index++, "Error"));*/


        firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_1",
                        generatedId,
                        programLanguage,
                        "Primitive Data types",
                        "There are eight primitive datatypes supported by Java. Primitive datatypes are predefined by the language and named by a keyword. Let us now look into the eight primitive data types in detail.\n" +
                                "\n" +
                                "byte\n" +
                                "Byte data type is an 8-bit signed two's complement integer\n" +
                                "\n" +
                                "Minimum value is -128 (-2^7)\n" +
                                "\n" +
                                "Maximum value is 127 (inclusive)(2^7 -1)\n" +
                                "\n" +
                                "Default value is 0\n" +
                                "\n" +
                                "Byte data type is used to save space in large arrays, mainly in place of integers, since a byte is four times smaller than an integer.\n" +
                                "\n" +
                                "Example: byte a = 100, byte b = -50\n" +
                                "\n" +
                                "short\n" +
                                "Short data type is a 16-bit signed two's complement integer\n" +
                                "\n" +
                                "Minimum value is -32,768 (-2^15)\n" +
                                "\n" +
                                "Maximum value is 32,767 (inclusive) (2^15 -1)\n" +
                                "\n" +
                                "Short data type can also be used to save memory as byte data type. A short is 2 times smaller than an integer\n" +
                                "\n" +
                                "Default value is 0.\n" +
                                "\n" +
                                "Example: short s = 10000, short r = -20000\n" +
                                "\n" +
                                "int\n" +
                                "Int data type is a 32-bit signed two's complement integer.\n" +
                                "\n" +
                                "Minimum value is - 2,147,483,648 (-2^31)\n" +
                                "\n" +
                                "Maximum value is 2,147,483,647(inclusive) (2^31 -1)\n" +
                                "\n" +
                                "Integer is generally used as the default data type for integral values unless there is a concern about memory.\n" +
                                "\n" +
                                "The default value is 0\n" +
                                "\n" +
                                "Example: int a = 100000, int b = -200000\n" +
                                "\n" +
                                "long\n" +
                                "Long data type is a 64-bit signed two's complement integer\n" +
                                "Minimum value is -9,223,372,036,854,775,808(-2^63)\n" +
                                "Maximum value is 9,223,372,036,854,775,807 (inclusive)(2^63 -1)\n" +
                                "This type is used when a wider range than int is needed\n" +
                                "Default value is 0L\n" +
                                "Example: long a = 100000L, long b = -200000L\n" +
                                "float\n" +
                                "Float data type is a single-precision 32-bit IEEE 754 floating point\n" +
                                "\n" +
                                "Float is mainly used to save memory in large arrays of floating point numbers\n" +
                                "\n" +
                                "Default value is 0.0f\n" +
                                "\n" +
                                "Float data type is never used for precise values such as currency\n" +
                                "\n" +
                                "Example: float f1 = 234.5f\n" +
                                "\n" +
                                "double\n" +
                                "double data type is a double-precision 64-bit IEEE 754 floating point\n" +
                                "\n" +
                                "This data type is generally used as the default data type for decimal values, generally the default choice\n" +
                                "\n" +
                                "Double data type should never be used for precise values such as currency\n" +
                                "\n" +
                                "Default value is 0.0d\n" +
                                "\n" +
                                "Example: double d1 = 123.4\n" +
                                "\n" +
                                "boolean\n" +
                                "boolean data type represents one bit of information\n" +
                                "There are only two possible values: true and false\n" +
                                "This data type is used for simple flags that track true/false conditions\n" +
                                "Default value is false\n" +
                                "Example: boolean one = true\n" +
                                "char\n" +
                                "char data type is a single 16-bit Unicode character\n" +
                                "Minimum value is '\\u0000' (or 0)\n" +
                                "Maximum value is '\\uffff' (or 65,535 inclusive)\n" +
                                "Char data type is used to store any character\n" +
                                "Example: char letterA = 'A'\n",
                        "",
                        "",
                        "",

                        "",
                        "",
                        moduleOptions
                        ));

        moduleOptions = new ArrayList<>();
        index = 0;

        /*moduleOptions.add(new ModuleOption(index++, "Singleton "));
        moduleOptions.add(new ModuleOption(index++, "public static "));
        moduleOptions.add(new ModuleOption(index++, " return singleton;\n"));
        moduleOptions.add(new ModuleOption(index++, "getInstance( ) {\n"));*/
        moduleOptions.add(new ModuleOption(index++, ""));


        /*firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_2",
                        generatedId,
                        programLanguage,
                        "Access Modifiers",
                        "Default Access Modifier - No Keyword\n" +
                                "Default access modifier means we do not explicitly declare an access modifier for a class, field, method, etc.\n" +
                                "\n" +
                                "A variable or method declared without any access control modifier is available to any other class in the same package. The fields in an interface are implicitly public static final and the methods in an interface are by default public.\n" +
                                "Private Access Modifier - Private\n" +
                                "Methods, variables, and constructors that are declared private can only be accessed within the declared class itself.\n" +
                                "\n" +
                                "Private access modifier is the most restrictive access level. Class and interfaces cannot be private.\n" +
                                "\n" +
                                "Variables that are declared private can be accessed outside the class, if public getter methods are present in the class.\n" +
                                "\n" +
                                "Using the private modifier is the main way that an object encapsulates itself and hides data from the outside world.\n" +
                                "Public Access Modifier - Public\n" +
                                "A class, method, constructor, interface, etc. declared public can be accessed from any other class. Therefore, fields, methods, blocks declared inside a public class can be accessed from any class belonging to the Java Universe.\n" +
                                "\n" +
                                "However, if the public class we are trying to access is in a different package, then the public class still needs to be imported. Because of class inheritance, all public methods and variables of a class are inherited by its subclasses.\n" +
                                "\n" +
                                "Protected Access Modifier - Protected\n" +
                                "Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses in other package or any class within the package of the protected members' class.\n" +
                                "\n" +
                                "The protected access modifier cannot be applied to class and interfaces. Methods, fields can be declared protected, however methods and fields in a interface cannot be declared protected.\n" +
                                "\n" +
                                "Protected access gives the subclass a chance to use the helper method or variable, while preventing a nonrelated class from trying to use it.\n" +
                                "Access Control and Inheritance\n" +
                                "The following rules for inherited methods are enforced −\n" +
                                "\n" +
                                "1. Methods declared public in a superclass also must be public in all subclasses.\n" +
                                "\n" +
                                "2. Methods declared protected in a superclass must either be protected or public in subclasses; they cannot be private.\n" +
                                "\n" +
                                "3. Methods declared private are not inherited at all, so there is no rule for them.",
                        "Example : \n" +
                                "public class Logger {\n" +
                                "String version = \"1.5.1\";\n" +
                                "//default access\n" +
                                "boolean processOrder() {\n" +
                                "   return true;\n" +
                                "}" +
                                "   private String format;\n" +
                                "//Private access\n" +
                                "   public String getFormat() {\n" +
                                "      return this.format;\n" +
                                "   }\n" +
                                "\n" +
                                "   //Public access" +
                                "   public void setFormat(String format) {\n" +
                                "      this.format = format;\n" +
                                "   }\n" +
                                "   //Protected access : can be overridden by the child class" +
                                "   protected boolean openSpeaker(Speaker sp) {\n" +
                                "      // implementation details\n" +
                                "   }" +
                                "}",
                        "Description : \n " +
                                "The above class illustrates all the access modifiers ",
                        "",

                        "",
                        "",
                        moduleOptions
                ));

        moduleOptions = new ArrayList<>();
        index = 0;

        *//*moduleOptions.add(new ModuleOption(index++, "Code readCode();\n"));
        moduleOptions.add(new ModuleOption(index++, "String program_Language;\n"));
        moduleOptions.add(new ModuleOption(index++, "public interface readCode\n"));
        moduleOptions.add(new ModuleOption(index++, " {\n"));*//*
        moduleOptions.add(new ModuleOption(index++, ""));


        firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_3",
                        generatedId,
                        programLanguage,
                        "Non Access Modifiers",
                        "Java provides a number of non-access modifiers to achieve many other functionalities.\n" +
                                "\n" +
                                "The static modifier for creating class methods and variables.\n" +
                                "\n" +
                                "The final modifier for finalizing the implementations of classes, methods, and variables.\n" +
                                "\n" +
                                "The abstract modifier for creating abstract classes and methods.\n" +
                                "\n" +
                                "The synchronized and volatile modifiers, which are used for threads.",
                        "Example : \n" +
                                "class Cloth\n" +
                                "{\n" +
                                " static String cloth_brand = \"Infinity\";" +
                                " final int MAX_PRICE = 999;    //final variable\n" +
                                " final int MIN_PRICE = 699;\n" +
                                " final void display()      //final method\n" +
                                " {\n" +
                                "  System.out.println(\"Maxprice is\" + MAX_PRICE );\n" +
                                "  System.out.println(\"Minprice is\" + MIN_PRICE);\n" +
                                " }\n" +
                                " //Static methods\n" +
                                " public static void main(String[] args) {\n" +
                                "  display();\n" +
                                " }" +
                                "}",
                        "Description : \n " +
                                "Above class contains examples for static variable, static method, final variable and final method",
                        "",

                        "",
                        "",
                        moduleOptions
                ));*/
        /*moduleOptions.add(new ModuleOption(index++, "Namasthe"));
        moduleOptions.add(new ModuleOption(index++, "e"));
        moduleOptions.add(new ModuleOption(index++, "Error"));*//*


        firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_3",
                        generatedId,
                        programLanguage,
                        "C++ goto statement",
                        "A goto statement provides an unconditional jump from the goto to a labeled statement in the same function.\n" +
                                "\n" +
                                "NOTE: Use of goto statement is highly discouraged because it makes difficult to trace the control flow of a program, making the program hard to understand and hard to modify. Any program that uses a goto can be rewritten so that it doesn't need the goto.",
                        "Example : \n" +
                                "// Local variable declaration:\n" +
                                "   int a = 10;\n" +
                                "\n" +
                                "   // do loop execution\n" +
                                "   LOOP:do {\n" +
                                "      if( a == 15) {\n" +
                                "         // skip the iteration.\n" +
                                "         a = a + 1;\n" +
                                "         goto LOOP;\n" +
                                "      }\n" +
                                " :  : \n" +
                                "      cout << \"value of a: \" << a << endl;\n" +
                                "      a = a + 1;\n" +
                                "   }while( a < 20 );",
                        "Output : \n value of a: 10\n" +
                                "value of a: 11\n" +
                                "value of a: 12\n" +
                                "value of a: 13\n" +
                                "value of a: 14\n" +
                                "value of a: 16\n" +
                                "value of a: 17\n" +
                                "value of a: 18\n" +
                                "value of a: 19",
                        "Predict the output for the program when if condition becomes true: \n" +
                                "for(...) {\n" +
                                "   for(...) {\n" +
                                "      while(...) {\n" +
                                "         if(...) goto stop;\n" +
                                "         .\n" +
                                "         .\n" +
                                "         .\n" +
                                "      }\n" +
                                "   }\n" +
                                "}\n" +
                                "stop:\n" +
                                "cout << \"Error in program.\\n\";",

                        "Hint : One good use for the goto is to exit from a deeply nested routine.",
                        "Error in program",
                        moduleOptions
                ));

        moduleOptions = new ArrayList<>();
        index = 0;

        moduleOptions.add(new ModuleOption(index++, "("));
        moduleOptions.add(new ModuleOption(index++, ")"));
        moduleOptions.add(new ModuleOption(index++, "true"));

        moduleOptions.add(new ModuleOption(index++, "while"));        *//*moduleOptions.add(new ModuleOption(index++, "Namasthe"));
        moduleOptions.add(new ModuleOption(index++, "e"));
        moduleOptions.add(new ModuleOption(index++, "Error"));*//*


        firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_4",
                        generatedId,
                        programLanguage,
                        "The Infinite Loop",
                        "A loop becomes infinite loop if a condition never becomes false. The for loop is traditionally used for this purpose. Since none of the three expressions that form the for loop are required, you can make an endless loop by leaving the conditional expression empty.",
                        "Example : \n" +
                                "#include <iostream>\n" +
                                "using namespace std;\n" +
                                " \n" +
                                "int main () {\n" +
                                "\n" +
                                "   for( ; ; ) {\n" +
                                "      printf(\"This loop will run forever.\\n\");\n" +
                                "   }\n" +
                                "\n" +
                                "   return 0;\n" +
                                "}",
                        "Output : \n When the conditional expression is absent, it is assumed to be true. You may have an initialization and increment expression, but C++ programmers more commonly use the for(;;) construct to signify an infinite loop.\n" +
                                "\n",
                        "Construct an infinite loop using while to print infinite programmer : \n" +
                                "//Your loop here\n" +
                                "cout << \"Infinite programmer\" << endl;",

                        "Prints Infinite programmer infinitely",
                        "while(true)",
                        moduleOptions
                ));


        moduleOptions = new ArrayList<>();
        index = 0;
       *//* moduleOptions.add(new ModuleOption(index++, "\"%d\""));
        moduleOptions.add(new ModuleOption(index++, "\"%s\""));
        moduleOptions.add(new ModuleOption(index++, "gets("));
        moduleOptions.add(new ModuleOption(index++, "getchar("));
        moduleOptions.add(new ModuleOption(index++, " &a"));*//*

        *//*moduleOptions.add(new ModuleOption(index++, "s;"));
        moduleOptions.add(new ModuleOption(index++, "extern "));*//*

        moduleOptions.add(new ModuleOption(index++, "compilation success"));
        moduleOptions.add(new ModuleOption(index++, "error: non-modifiable object"));

        *//*firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_2",
                        generatedId,
                        programLanguage,
                        "Type Qualifiers in C++",
                        "The type qualifiers provide additional information about the variables they precede.",
                        "Description : \n" +
                                "const : Objects of type const cannot be changed by your program during execution\n" +
                                "volatile : The modifier volatile tells the compiler that a variable's value may be changed in ways not explicitly specified by the program.\n" +
                                "restrict : A pointer qualified by restrict is initially the only means by which the object it points to can be accessed. Only C99 adds a new type qualifier called restrict.",
                        "Example : \n int n1 = 0;           // non-const object\n" +
                                "    const int n2 = 0;     // const object\n" +
                                "    int const n3 = 0;     // const object (same as n2)\n" +
                                "    volatile int n4 = 0;  // volatile object",
                        "What would be the output for if : n2 = 2; is assigned from above example",
                        "Hint : const cannot be changed",
                        "error: non-modifiable object",
                        moduleOptions
                ));*//*

        moduleOptions = new ArrayList<>();
        index = 0;
       *//* moduleOptions.add(new ModuleOption(index++, "\"%d\""));
        moduleOptions.add(new ModuleOption(index++, "\"%s\""));
        moduleOptions.add(new ModuleOption(index++, "gets("));
        moduleOptions.add(new ModuleOption(index++, "getchar("));
        moduleOptions.add(new ModuleOption(index++, " &a"));*//*

        *//*moduleOptions.add(new ModuleOption(index++, "s;"));
        moduleOptions.add(new ModuleOption(index++, "extern "));*//*
        moduleOptions.add(new ModuleOption(index++, " <<"));
        moduleOptions.add(new ModuleOption(index++, "cin"));
        moduleOptions.add(new ModuleOption(index++, " >> "));
        moduleOptions.add(new ModuleOption(index++, " \"undefined character\""));
        moduleOptions.add(new ModuleOption(index++, ";"));
        moduleOptions.add(new ModuleOption(index++, "cerr"));
        moduleOptions.add(new ModuleOption(index++, "cout"));


       *//* firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_3",
                        generatedId,
                        programLanguage,
                        "The standard error stream (cerr)",
                        "The predefined object cerr is an instance of ostream class. The cerr object is said to be attached to the standard error device, which is also a display screen but the object cerr is un-buffered and each stream insertion to cerr causes its output to appear immediately.\n" +
                                "\n" +
                                "The cerr is also used in conjunction with the stream insertion operator as shown in the following example.",
                        "Syntax : \n" +
                                "char str[] = \"Unable to read....\";\n" +
                                " \n" +
                                "cerr << \"Error message : \" << str << endl;",
                        "Output : \n Error message : Unable to read....",
                        "Print an error : undefined character",
                        "undefined character",
                        "cerr << \"undefined character\";",
                        moduleOptions
                ));*//*
        moduleOptions = new ArrayList<>();
        index = 0;*/
       /* moduleOptions.add(new ModuleOption(index++, "\"%d\""));
        moduleOptions.add(new ModuleOption(index++, "\"%s\""));
        moduleOptions.add(new ModuleOption(index++, "gets("));
        moduleOptions.add(new ModuleOption(index++, "getchar("));
        moduleOptions.add(new ModuleOption(index++, " &a"));*/




    }

    public void insertSyntaxModules( ) {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeSyntaxModule( new SyntaxModule());

    }

    public void insertProgramWiki() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        ArrayList<ProgramWiki> programWikis = new ArrayList<>();
        ProgramWiki programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Hello World");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "How \"Hello, World!\" program works?\n" +
                "\n" +
                "The #include <stdio.h> is a preprocessor command. This command tells compiler to include the contents of stdio.h (standard input and output) file in the program.\n\n" +
                "The stdio.h file contains functions such as scanf() and print() to take input and display output respectively.\n\n" +
                "If you use printf() function without writing #include <stdio.h>, the program will not be compiled.\n\n" +
                "The execution of a C program starts from the main() function.\n\n" +
                "The printf() is a library function to send formatted output to the screen. In this program, the printf() displays Hello, World! text on the screen.\n\n" +
                "The return 0; statement is the \"Exit status\" of the program. In simple terms, program ends with this statement.\n\n");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                "#include <stdio.h>\n" +
                        "int main()\n" +
                        "{\n" +
                        "   // printf() displays the string inside quotation\n" +
                        "   printf(\"Hello, World!\");\n" +
                        "   return 0;\n" +
                        "}");
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Hello, World!");
        programWikis.add(programWiki);

        WikiModel wikiModel = new WikiModel("c1", "Hello World", programWikis, "c");
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);

        programWikis = new ArrayList<>();
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("C program to add two numbers");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "Here are a few examples on how to add two numbers. In the expression (c = a + b) overflow may occur if sum of a and b is larger than maximum value which can be stored in variable c. ");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                        "#include<stdio.h>\n" +
                        " \n" +
                        "int main()\n" +
                        "{\n" +
                        "   int a, b, c;\n" +
                        " \n" +
                        "   printf(\"Enter two numbers to add\\n\");\n" +
                        "   scanf(\"%d%d\",&a,&b);\n" +
                        " \n" +
                        "   c = a + b;\n" +
                        " \n" +
                        "   printf(\"Sum of entered numbers = %d\\n\",c);\n" +
                        " \n" +
                        "   return 0;\n" +
                        "}\n" +
                        "\n");
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Enter two numbers to add\n4\n5\nSum of entered numbers = 9");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Addition without using third variable");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                        "#include <stdio.h>\n" +
                        " \n" +
                        "int main()\n" +
                        "{\n" +
                        "   int a, b, c;\n" +
                        "   char ch;\n" +
                        " \n" +
                        "   while (1) {\n" +
                        "      printf(\"Inut two integers\\n\");\n" +
                        "      scanf(\"%d%d\", &a, &b);\n" +
                        "      getchar();\n" +
                        " \n" +
                        "      c = a + b;\n" +
                        " \n" +
                        "      printf(\"(%d) + (%d) = (%d)\\n\", a, b, c);\n" +
                        " \n" +
                        "      printf(\"Do you wish to add more numbers (y/n)\\n\");\n" +
                        "      scanf(\"%c\", &ch);\n" +
                        " \n" +
                        "      if (ch == 'y' || ch == 'Y')\n" +
                        "         continue;\n" +
                        "      else\n" +
                        "       break;\n" +
                        "   }\n" +
                        " \n" +
                        "   return 0;\n" +
                        "}\n" +
                        "\n");
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Inut two integers\n" +
                "2 6\n" +
                "(2) + (6) = (8)\n" +
                "Do you wish to add more numbers (y/n)\n" +
                "y\n" +
                "Inut two integers\n" +
                "2 -6\n" +
                "(2) + (-6) = (-4)\n" +
                "Do you wish to add more numbers (y/n)\n" +
                "y\n" +
                "Inut two integers\n" +
                "-5 3\n" +
                "(-5) + (3) = (-2)\n" +
                "Do you wish to add more numbers (y/n)\n" +
                "y\n" +
                "Inut two integers\n" +
                "-5 -6\n" +
                "(-5) + (-6) = (-11)\n" +
                "Do you wish to add more numbers (y/n)\n" +
                "n");
        programWikis.add(programWiki);



        wikiModel = new WikiModel("c2", "C program to add two numbers", programWikis, "c" );
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);
    }
}
