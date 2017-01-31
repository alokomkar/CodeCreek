package com.sortedqueue.programmercreek.asynctask;

import android.app.Activity;
import android.content.Context;

import com.sortedqueue.programmercreek.database.LanguageModule;
import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.ProgramIndex;
import com.sortedqueue.programmercreek.database.ProgramTable;
import com.sortedqueue.programmercreek.database.ProgramWiki;
import com.sortedqueue.programmercreek.database.SyntaxModule;
import com.sortedqueue.programmercreek.database.WikiModel;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CommonUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alok on 21/12/16.
 */

public class JavaProgramInserter {

    private Context context;
    private ArrayList<ModuleOption> moduleOptions;

    public JavaProgramInserter(Context context) {
        this.context = context;
    }

    public void insertPrograms( ) {
        CommonUtils.displayProgressDialog(context, "Inserting programs");
        int index = 1;

        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Largest of three numbers", "http://codescracker.com/cpp/program/cpp-program-find-greatest-of-three-numbers.htm", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "To find the discount ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "To find the case of a character", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Leap year or not", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Total days to year,month,days", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "To find area of an isosceles triangle", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Area and circumference of a circle", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Swapping two values ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "To generate electricity bill", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Factorial of a given number", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Result generator", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Sum", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Fibonacci series", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Sum and average", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Bubble sort", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Binary search", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Sum of two matrix", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Sum of rows and columns", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Vowels and consonants count", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Largest and second largest in an array", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Frequency of a given element", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Simple interest", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Function overloading", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Cube of a number", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Palindrome or not ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Prime or not", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "String copy", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Sine series ", "https://programercreek.blogspot.in/", "c++"));
        firebaseDatabaseHandler.writeProgramIndex(new ProgramIndex(index++, "Polynomial", "https://programercreek.blogspot.in/", "c++"));
        CommonUtils.dismissProgressDialog();
    }

    public void insertProgramTables( ) {
        CommonUtils.displayProgressDialog((Activity) context, "Inserting program tables");
        int programIndex = 24;
        int lineNo = 1;
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"inline int cube(int a)",  "cube function definition - inline",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," return(a*a*a);",  "return(a*a*a);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"}",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int x,y;",  "Variable declaration - x,y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the value for x\"<<endl;",  "Print Enter the value for x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin>>x;",  "Read x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," y=cube(x);",  "Assign y=cube(x);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"The cube of \"<<x<<\" is \"<<y<<endl;",  "Print The cube of x is y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "void main() {", "Main declaration", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int n,rev=0,num,rem;",  "Variable declaration - n,rev,num,rem",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the number\"<<endl;",  "Print Enter the number",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin>>num;",  "Read num",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," n=num;",  "Assign n=num;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," while(num!=0)",  "while loop till num != 0",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  rem=num%10;",  "Assign rem=num%10;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  num=num/10;",  "Assign num=num/10;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  rev=(rev*10)+rem;",  "Assign rev=(rev*10)+rem;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," if(rev==n)",  "Check if rev = n?",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  cout<<n<<\"is a palindrome\"<<endl;",  "Print n is a palindroid",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," else",  "else",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  cout<<n<<\"is not a palindrome\"<<endl;",  "Print n is not a palindrome",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"int isprime(int num)",  "Function definition isprime",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start function isprime",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int i;",  "Variable declaration - i",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," for(i=2;i<=n/2;i++)",  "for loop from 2 to n/2",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  if(num%i==0)",  "Check if num%i = 0 ?",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  return 0;",  "return 0;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," return 1;",  "return 1;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"}",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int x,y,I;",  "Variable declaration - x,y,l",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<\"Enter a range\"<<endl;",  "Print Enter a range",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin>>x>>y;",  "Read x,y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"The prime numbers are \"<<endl;",  "Print The prime numbers are",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," for(i=x;i<=y;i++)",  "For loop from x to y",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  if(isprime(i))",  "Check isprime(i)",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"   cout<<I<<endl;",  "Print l",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"void strcopy(char s1[50],char s2[50])",  "strcopy function definition",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start function strcopy",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int i=0;",  "Variable declaration - i",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," while(s1[i]!='\\0')",  "while loop till s1[i] != '\\0'",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  s2[i]=s1[i];",  "Assign s2[i]=s1[i];",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  i++;",  "Assign i++;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," s2[i]='\\0';",  "Assign s2[i]='\\0';",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"}",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," char str1[50],str2[50];",  "Variable declaration - str1[50],str2[50]",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the source string\"<<endl;",  "Print Enter the source string",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin.getline(str1);",  "Read line",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," strcopy(str1,str2);",  "call function strcopy(str1,str2);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Destination string is \"<<str2<<endl;",  "Print Destination string is",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"#include<math.h>",  "Header include - math.h",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int i,degree;",  "Variable declaration - i, degree",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," float x,sum=0,term,I,pi=3.142;",  "Variable declaration - x,y,sum,term,l,pi",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the value of degree \"<<endl;",  "Print Enter the value of degree",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin>>degree;",  "Read degree",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," x=degree*(pi/180);",  "Assign x=degree*(pi/180);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," term=x;",  "Assign term=x;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," sum=term;",  "Assign sum=term;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," for(i=3;i<=n;i+=2)",  "For loop from 3 to n",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  term=(term*x*x)/((i-1)*i);",  "Assign term=(term*x*x)/((i-1)*i);",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  sum=sum+term;",  "Assign sum=sum+term;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"sine of \"<<degree<<\" is \"<<sum<<endl;",  "Print sine of degree is sum",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "}", "End of main", "c++"));

        programIndex++;
        lineNo = 1;

        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<iostream.h>", "Header include - iostream.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "#include<conio.h>", "Header include - conio.h", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"void main()",  "Main declaration",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"{",  "Start Main",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," int n,I,sum=0,a[10],x;",  "Variable declaration - n,l,sum,a[10],x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," clrscr();",  "Clear screen",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the no. of co-efficients \"<endl;",  "Print Enter no. of co-efficients",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin>>n;",  "Read n",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the co-efficients\"<<endl;",  "Print Enter the co-efficients",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," for(i=n;i>=0;i--)",  "for loop from n to 0",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  cin>>a[i];",  "Read a[i]",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Enter the value of x\"<<endl;",  "Print Enter the value of x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cin>>x;",  "Read x",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," for(i=n;i>=0;i--)",  "for loop from n to 0",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," {",  "Start",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"  sum=(sum+a[i])*x;",  "Assign sum=(sum+a[i])*x;",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," }",  "End",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," sum=sum+a[0];",  "Assign sum=sum+a[0];",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++," cout<<\"Sum = \"<<sum<<endl;",  "Print Sum= ",  "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, " getch();", "Wait for keyboard input", "c++"));
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++, "}", "End of main", "c++"));

        CommonUtils.dismissProgressDialog();
    }

    public void insertLanguageModules() {

        int moduleIndex = 1;
        String programLanguage = "sql";
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - Syntax",
                        "SQL is followed by unique set " +
                                "of rules and guidelines called Syntax. " +
                                "This tutorial gives you a quick start " +
                                "with SQL by listing all the basic SQL Syntax",
                        programLanguage));
        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - Data Types",
                        "SQL data type is an attribute " +
                                "that specifies type of " +
                                "data of any object. " +
                                "Each column, variable and expression " +
                                "has related data type in SQL.",  programLanguage));

        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - Expressions",
                        "An expression is a combination of " +
                                "one or more values, operators, and " +
                                "SQL functions that evaluate to a value.",  programLanguage));

        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - Database commands",
                        "In this module, we will take a closer look at commands involved in creation, selection and deletion of database",  programLanguage));

        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - Table commands",
                        "In this module, we will take a closer look at commands involved in creation and deletion of table",  programLanguage));

        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - CRUD Operations",
                        "In this module, we will take a closer look at commands involved in insertion, selection, update and deletion of rows of a table",  programLanguage));

        firebaseDatabaseHandler.writeLanguageModule(
                new LanguageModule(
                        "sql_" + moduleIndex++,
                        "SQL - Extras",
                        "In this module, we will explore sorting, ordering and filtering of rows of a table",  programLanguage));
    }

    public void insertSyntaxModules( ) {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        int moduleId;
        /*String programLanguage = new CreekPreferences(context).getProgramLanguage();
        if( programLanguage.equals("c++") ) {
            programLanguage = "cpp";
        }*/
        String programLanguage = "sql";
        new CreekPreferences(context).setProgramLanguage("sql");
        moduleId = 6;
        String generatedId = programLanguage + "_" + moduleId++;
        int syntaxIndex = 1;

        SyntaxModule syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - INSERT Query");
        syntaxModule.setSyntaxDescription(
                "The SQL INSERT INTO Statement is used to add new rows of data to a table in the database.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "There are two basic syntaxes of INSERT INTO statement as follows:\n" +
                        "\n" +
                        "INSERT INTO TABLE_NAME (column1, column2, column3,...columnN)  \n" +
                        "VALUES (value1, value2, value3,...valueN);\n" +
                        "Here, column1, column2,...columnN are the names of the columns in the table into which you want to insert data.\n" +
                        "\n" +
                        "You may not need to specify the column(s) name in the SQL query if you are adding values for all the columns of the table. But make sure the order of the values is in the same order as the columns in the table. The SQL INSERT INTO syntax would be as follows:\n" +
                        "\n" +
                        "INSERT INTO TABLE_NAME VALUES (value1,value2,value3,...valueN);\n\n" +
                        "Example:\n" +
                        "Following statements would create one record in CUSTOMERS table:\n" +
                        "\n" +
                        "INSERT INTO CUSTOMERS (ID,NAME,AGE,ADDRESS,SALARY)\n" +
                        "VALUES (1, 'Ramesh', 32, 'Ahmedabad', 2000.00 );\n\n" +
                        "You can also create a record in CUSTOMERS table using second syntax as follows:\n" +
                        "\n" +
                        "INSERT INTO CUSTOMERS \n" +
                        "VALUES (7, 'Muffy', 24, 'Indore', 10000.00 );");

        syntaxModule.setSyntaxCommandOutput("A new row in Customers will be created");
        syntaxModule.setSyntaxQuestion("Create a new row in table : codeTable[id, programLine] containing entries id : 1 and row : Infinite_Programmer");
        syntaxModule.setSyntaxQuestionOutput("A new database called infiniteDB will be created");
        syntaxModule.setSyntaxSolution("INSERT INTO CODETABLE VALUES (1, 'Infinite_Programmer');");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        int index = 0;
        String[] options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option+ " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);

        syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - SELECT Query");
        syntaxModule.setSyntaxDescription(
                "SQL SELECT statement is used to fetch the data from a database table which returns data in the form of result table. These result tables are called result-sets.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of SELECT statement is as follows:\n" +
                        "\n" +
                        "SELECT column1, column2, columnN FROM table_name;\n" +
                        "Here, column1, column2...are the fields of a table whose values you want to fetch. If you want to fetch all the fields available in the field, then you can use the following syntax:\n" +
                        "\n" +
                        "SELECT * FROM table_name;\n\n" +
                        "Following is an example, which would fetch ID, Name and Salary fields of the customers available in CUSTOMERS table:\n" +
                        "\n" +
                        "SQL> SELECT ID, NAME, SALARY FROM CUSTOMERS;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+----------+----------+\n" +
                        "| ID | NAME     | SALARY   |\n" +
                        "+----+----------+----------+\n" +
                        "|  1 | Ramesh   |  2000.00 |\n" +
                        "|  2 | Khilan   |  1500.00 |\n" +
                        "|  3 | kaushik  |  2000.00 |\n" +
                        "|  4 | Chaitali |  6500.00 |\n" +
                        "|  5 | Hardik   |  8500.00 |\n" +
                        "|  6 | Komal    |  4500.00 |\n" +
                        "|  7 | Muffy    | 10000.00 |\n" +
                        "+----+----------+----------+\n" +
                        "If you want to fetch all the fields of CUSTOMERS table, then use the following query:\n" +
                        "\n" +
                        "SQL> SELECT * FROM CUSTOMERS;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+");


        syntaxModule.setSyntaxCommandOutput("Shows relevant rows as per the query");
        syntaxModule.setSyntaxQuestion("Select all fields from table codeTable [id, programLine] ");
        syntaxModule.setSyntaxSolution("SELECT * FROM CODETABLE;");
        syntaxModule.setSyntaxQuestionOutput("| 1 | Infinite_Programmer |");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        index = 0;
        options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option+ " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);


        syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - WHERE Clause");
        syntaxModule.setSyntaxDescription(
                "The SQL WHERE clause is used to specify a condition while fetching the data from single table or joining with multiple tables.\n" +
                        "\n" +
                        "If the given condition is satisfied then only it returns specific value from the table. You would use WHERE clause to filter the records and fetching only necessary records.\n" +
                        "\n" +
                        "The WHERE clause is not only used in SELECT statement, but it is also used in UPDATE, DELETE statement, etc., which we would examine in subsequent chapters.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of SELECT statement with WHERE clause is as follows:\n" +
                        "\n" +
                        "SELECT column1, column2, columnN \n" +
                        "FROM table_name\n" +
                        "WHERE [condition]\n" +
                        "You can specify a condition using comparison or logical operators like >, <, =, LIKE, NOT, etc. Below examples would make this concept clear.\n" +
                        "\n" +
                        "Example:\n" +
                        "Consider the CUSTOMERS table having the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Following is an example which would fetch ID, Name and Salary fields from the CUSTOMERS table where salary is greater than 2000:\n" +
                        "\n" +
                        "SQL> SELECT ID, NAME, SALARY \n" +
                        "FROM CUSTOMERS\n" +
                        "WHERE SALARY > 2000;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+----------+----------+\n" +
                        "| ID | NAME     | SALARY   |\n" +
                        "+----+----------+----------+\n" +
                        "|  4 | Chaitali |  6500.00 |\n" +
                        "|  5 | Hardik   |  8500.00 |\n" +
                        "|  6 | Komal    |  4500.00 |\n" +
                        "|  7 | Muffy    | 10000.00 |\n" +
                        "+----+----------+----------+\n" +
                        "Following is an example, which would fetch ID, Name and Salary fields from the CUSTOMERS table for a customer with name Hardik. Here, it is important to note that all the strings should be given inside single quotes ('') where as numeric values should be given without any quote as in above example:\n" +
                        "\n" +
                        "SQL> SELECT ID, NAME, SALARY \n" +
                        "FROM CUSTOMERS\n" +
                        "WHERE NAME = 'Hardik';\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+----------+----------+\n" +
                        "| ID | NAME     | SALARY   |\n" +
                        "+----+----------+----------+\n" +
                        "|  5 | Hardik   |  8500.00 |\n" +
                        "+----+----------+----------+");


        syntaxModule.setSyntaxCommandOutput("Displays relevant data from table");
        syntaxModule.setSyntaxQuestion("Select all row values from codeTable[id, programLine] where id = 1");
        syntaxModule.setSyntaxSolution("SELECT ID, PROGRAMLINE FROM CODETABLE WHERE ID = 1;");
        syntaxModule.setSyntaxQuestionOutput("| 1 | Infinite_Programmer |");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        index = 0;
        options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option + " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);

        syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - AND and OR Conjunctive Operators");
        syntaxModule.setSyntaxDescription(
                "The SQL AND and OR operators are used to combine multiple conditions to narrow data in an SQL statement. These two operators are called conjunctive operators.\n" +
                        "\n" +
                        "These operators provide a means to make multiple comparisons with different operators in the same SQL statement.\n" +
                        "\n" +
                        "The AND Operator:\n" +
                        "The AND operator allows the existence of multiple conditions in an SQL statement's WHERE clause.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of AND operator with WHERE clause is as follows:\n" +
                        "\n" +
                        "SELECT column1, column2, columnN \n" +
                        "FROM table_name\n" +
                        "WHERE [condition1] AND [condition2]...AND [conditionN];\n" +
                        "You can combine N number of conditions using AND operator. For an action to be taken by the SQL statement, whether it be a transaction or query, all conditions separated by the AND must be TRUE.\n" +
                        "\n" +
                        "Example:\n" +
                        "Consider the CUSTOMERS table having the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Following is an example, which would fetch ID, Name and Salary fields from the CUSTOMERS table where salary is greater than 2000 AND age is less tan 25 years:\n" +
                        "\n" +
                        "SQL> SELECT ID, NAME, SALARY \n" +
                        "FROM CUSTOMERS\n" +
                        "WHERE SALARY > 2000 AND age < 25;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+-------+----------+\n" +
                        "| ID | NAME  | SALARY   |\n" +
                        "+----+-------+----------+\n" +
                        "|  6 | Komal |  4500.00 |\n" +
                        "|  7 | Muffy | 10000.00 |\n" +
                        "+----+-------+----------+");


        syntaxModule.setSyntaxCommandOutput("Displays all relevant rows matching values for specified query");
        syntaxModule.setSyntaxQuestion("Select all columns from codeTable[id, programLine] where id = 1 and programLine = 'Infinite_Programmer'");
        syntaxModule.setSyntaxQuestionOutput("| 1 | Infinite_Programmer |");
        syntaxModule.setSyntaxSolution("SELECT * FROM CODETABLE WHERE ID = 1 AND PROGRAMLINE = 'Infinite_Programmer';");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        index = 0;
        options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option + " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);


        syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - AND and OR Conjunctive Operators");
        syntaxModule.setSyntaxDescription(
                "The OR Operator:\n" +
                        "The OR operator is used to combine multiple conditions in an SQL statement's WHERE clause.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of OR operator with WHERE clause is as follows:\n" +
                        "\n" +
                        "SELECT column1, column2, columnN \n" +
                        "FROM table_name\n" +
                        "WHERE [condition1] OR [condition2]...OR [conditionN]\n" +
                        "You can combine N number of conditions using OR operator. For an action to be taken by the SQL statement, whether it be a transaction or query, only any ONE of the conditions separated by the OR must be TRUE.\n" +
                        "\n" +
                        "Example:\n" +
                        "Consider the CUSTOMERS table having the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Following is an example, which would fetch ID, Name and Salary fields from the CUSTOMERS table where salary is greater than 2000 OR age is less tan 25 years:\n" +
                        "\n" +
                        "SQL> SELECT ID, NAME, SALARY \n" +
                        "FROM CUSTOMERS\n" +
                        "WHERE SALARY > 2000 OR age < 25;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+----------+----------+\n" +
                        "| ID | NAME     | SALARY   |\n" +
                        "+----+----------+----------+\n" +
                        "|  3 | kaushik  |  2000.00 |\n" +
                        "|  4 | Chaitali |  6500.00 |\n" +
                        "|  5 | Hardik   |  8500.00 |\n" +
                        "|  6 | Komal    |  4500.00 |\n" +
                        "|  7 | Muffy    | 10000.00 |\n" +
                        "+----+----------+----------+");


        syntaxModule.setSyntaxCommandOutput("Displays all relevant rows matching values for specified query");
        syntaxModule.setSyntaxQuestion("Select all columns from codeTable[id, programLine] where id = 1 or programLine = 'Infinite_Programmer'");
        syntaxModule.setSyntaxQuestionOutput("| 1 | Infinite_Programmer |");
        syntaxModule.setSyntaxSolution("SELECT * FROM CODETABLE WHERE ID = 1 OR PROGRAMLINE = 'Infinite_Programmer';");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        index = 0;
        options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option + " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);

        syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - UPDATE Query");
        syntaxModule.setSyntaxDescription(
                "The SQL UPDATE Query is used to modify the existing records in a table.\n" +
                        "\n" +
                        "You can use WHERE clause with UPDATE query to update selected rows otherwise all the rows would be affected.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of UPDATE query with WHERE clause is as follows:\n" +
                        "\n" +
                        "UPDATE table_name\n" +
                        "SET column1 = value1, column2 = value2...., columnN = valueN\n" +
                        "WHERE [condition];\n" +
                        "You can combine N number of conditions using AND or OR operators.\n" +
                        "\n" +
                        "Example:\n" +
                        "Consider the CUSTOMERS table having the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Following is an example, which would update ADDRESS for a customer whose ID is 6:\n" +
                        "\n" +
                        "SQL> UPDATE CUSTOMERS\n" +
                        "SET ADDRESS = 'Pune'\n" +
                        "WHERE ID = 6;\n" +
                        "Now, CUSTOMERS table would have the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | Pune      |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "If you want to modify all ADDRESS and SALARY column values in CUSTOMERS table, you do not need to use WHERE clause and UPDATE query would be as follows:\n" +
                        "\n" +
                        "SQL> UPDATE CUSTOMERS\n" +
                        "SET ADDRESS = 'Pune', SALARY = 1000.00;\n" +
                        "Now, CUSTOMERS table would have the following records:\n" +
                        "\n" +
                        "+----+----------+-----+---------+---------+\n" +
                        "| ID | NAME     | AGE | ADDRESS | SALARY  |\n" +
                        "+----+----------+-----+---------+---------+\n" +
                        "|  1 | Ramesh   |  32 | Pune    | 1000.00 |\n" +
                        "|  2 | Khilan   |  25 | Pune    | 1000.00 |\n" +
                        "|  3 | kaushik  |  23 | Pune    | 1000.00 |\n" +
                        "|  4 | Chaitali |  25 | Pune    | 1000.00 |\n" +
                        "|  5 | Hardik   |  27 | Pune    | 1000.00 |\n" +
                        "|  6 | Komal    |  22 | Pune    | 1000.00 |\n" +
                        "|  7 | Muffy    |  24 | Pune    | 1000.00 |\n" +
                        "+----+----------+-----+---------+---------+");


        syntaxModule.setSyntaxCommandOutput("Displays all relevant number rows matching values for specified update query affected");
        syntaxModule.setSyntaxQuestion("Update programLine column from codeTable[id, programLine] where id = 1 to programLine = 'Learning_made_easy'");
        syntaxModule.setSyntaxQuestionOutput("| 1 | Learning_made_easy |");
        syntaxModule.setSyntaxSolution("UPDATE CODETABLE SET PROGRAMLINE = 'Learning_made_easy' WHERE ID = 1;");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        index = 0;
        options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option + " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);

        syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - DELETE Query");
        syntaxModule.setSyntaxDescription(
                "The SQL DELETE Query is used to delete the existing records from a table.\n" +
                        "\n" +
                        "You can use WHERE clause with DELETE query to delete selected rows, otherwise all the records would be deleted.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of DELETE query with WHERE clause is as follows:\n" +
                        "\n" +
                        "DELETE FROM table_name\n" +
                        "WHERE [condition];\n" +
                        "You can combine N number of conditions using AND or OR operators.\n" +
                        "\n" +
                        "Example:\n" +
                        "Consider the CUSTOMERS table having the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Following is an example, which would DELETE a customer, whose ID is 6:\n" +
                        "\n" +
                        "SQL> DELETE FROM CUSTOMERS\n" +
                        "WHERE ID = 6;\n" +
                        "Now, CUSTOMERS table would have the following records:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "If you want to DELETE all the records from CUSTOMERS table, you do not need to use WHERE clause and DELETE query would be as follows:\n" +
                        "\n" +
                        "SQL> DELETE FROM CUSTOMERS;\n" +
                        "Now, CUSTOMERS table would not have any record.");


        syntaxModule.setSyntaxCommandOutput("Displays all relevant number rows matching values for specified update query deleted");
        syntaxModule.setSyntaxQuestion("Delete row from codeTable[id, programLine] where id = 1");
        syntaxModule.setSyntaxQuestionOutput("1 row deleted");
        syntaxModule.setSyntaxSolution("DELETE FROM CODETABLE WHERE ID = 1;");
        syntaxModule.setSyntaxLanguage("sql");


        moduleOptions = new ArrayList<>();
        index = 0;
        options = syntaxModule.getSyntaxSolution().split(" ");
        for( String option : options ) {
            moduleOptions.add(new ModuleOption(index++, option + " "));
        }
        Collections.shuffle(moduleOptions);
        syntaxModule.setSyntaxOptions(moduleOptions);
        firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);



    }

    public void insertProgramWiki() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        ArrayList<ProgramWiki> programWikis = new ArrayList<>();
        ProgramWiki programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("C program to read a file ");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "C program to read a file: This program reads a file entered by the user and displays its contents on the screen, fopen function is used to open a file it returns a pointer to structure FILE. FILE is a predefined structure in stdio.h . If the file is successfully opened then fopen returns a pointer to file and if it is unable to open a file then it returns NULL. fgetc function returns a character which is read from the file and fclose function closes the file. Opening a file means we bring file from disk to ram to perform operations on it. The file must be present in the directory in which the executable file of this code sis present.\n" +
                "There are blank lines present at end of file. In our program we have opened only one file but you can open multiple files in a single program and in different modes as desired. File handling is very important when we wish to store data permanently on a storage device. All variables and data of program is lost when program exits so if that data is required later we need to use files.");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                        "\n" +
                                "\n" +
                                "#include <stdio.h>\n" +
                                "#include <stdlib.h>\n" +
                                " \n" +
                                "int main()\n" +
                                "{\n" +
                                "   char ch, file_name[25];\n" +
                                "   FILE *fp;\n" +
                                " \n" +
                                "   printf(\"Enter the name of file you wish to see\\n\");\n" +
                                "   gets(file_name);\n" +
                                " \n" +
                                "   fp = fopen(file_name,\"r\"); // read mode\n" +
                                " \n" +
                                "   if( fp == NULL )\n" +
                                "   {\n" +
                                "      perror(\"Error while opening the file.\\n\");\n" +
                                "      exit(EXIT_FAILURE);\n" +
                                "   }\n" +
                                " \n" +
                                "   printf(\"The contents of %s file are :\\n\", file_name);\n" +
                                " \n" +
                                "   while( ( ch = fgetc(fp) ) != EOF )\n" +
                                "      printf(\"%c\",ch);\n" +
                                " \n" +
                                "   fclose(fp);\n" +
                                "   return 0;\n" +
                                "}\n" +
                                "\n");
        programWiki.setOutput("" +
                "" +
                "Output\n" +
                "\n" +
                "Enter the name of the file your wish to see \nHello_world.txt\n" +
                "The contents of Hello_world.txt are :\nHello world" );

        programWikis.add(programWiki);


        WikiModel wikiModel = new WikiModel("c22", "C program to read a file ", programWikis, "c");
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);
        /***
         * ====================================================================================
         * **/
        programWikis = new ArrayList<>();
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Pattern matching using Pointers ");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "In this program, we are going to use pointers for string comparision/ pattern matching");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                        "#include<stdio.h>\n" +
                                "#include<conio.h>\n" +
                                "int match(char*, char*);\n" +
                                "main()\n" +
                                "{\n" +
                                "   char a[100], b[100];\n" +
                                "   int position;\n" +
                                "  printf(“Enter some text\\n”);\n" +
                                "  gets(a);\n" +
                                "  printf(“Enter a string to find\\n”);\n" +
                                "  gets(b);\n" +
                                "  position = match(a, b);\n" +
                                "  if(position!=-1)\n" +
                                "    printf(“Found at location %d\\n”, position+1);\n" +
                                "  else\n" +
                                "    printf(“Not found.\\n”);\n" +
                                "  getch();\n" +
                                "}\n" +
                                "\n" +
                                "int match(char *a, char *b)\n" +
                                "{\n" +
                                "   int c;\n" +
                                "   int position = 0;\n" +
                                "   char *x, *y;\n" +
                                "   x = a;\n" +
                                "   y = b;\n" +
                                "   while(*a)\n" +
                                "   {\n" +
                                "     while(*x==*y)\n" +
                                "    {\n" +
                                "      x++;\n" +
                                "      y++;\n" +
                                "     if(*x==”||*y==”)\n" +
                                "      break;\n" +
                                "    }\n" +
                                "    if(*y==”)\n" +
                                "     break;\n" +
                                "    a++;\n" +
                                "    position++;\n" +
                                "   x = a;\n" +
                                "   y = b;\n" +
                                "  }\n" +
                                "   if(*a)\n" +
                                "     return position;\n" +
                                "   else\n" +
                                "     return -1;\n" +
                                "}" );
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Enter some text\nHello world, how are you?\n" +
                "Enter a string to find\nhow\n" +
                "Found at location \n 14"
        );
        programWikis.add(programWiki);

        wikiModel = new WikiModel("c23", "Pattern matching using Pointers ", programWikis, "c" );
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);

        /***
         * ====================================================================================
         * **/

        programWikis = new ArrayList<>();
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("String concatenation");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "This program concatenates strings, for example if the first string is \"c \" and second string is \"program\" then on concatenating these two strings we get the string \"c program\". To concatenate two strings we use strcat function of string.h, to concatenate without using library function see another code below which uses pointers.");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                "#include <stdio.h>\n" +
                        "#include <string.h>\n" +
                        " \n" +
                        "int main()\n" +
                        "{\n" +
                        "   char a[1000], b[1000];\n" +
                        " \n" +
                        "   printf(\"Enter the first string\\n\");\n" +
                        "   gets(a);\n" +
                        " \n" +
                        "   printf(\"Enter the second string\\n\");\n" +
                        "   gets(b);\n" +
                        " \n" +
                        "   strcat(a,b);\n" +
                        " \n" +
                        "   printf(\"String obtained on concatenation is %s\\n\",a);\n" +
                        " \n" +
                        "   return 0;\n" +
                        "}" );
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Enter the first string\nInfinite\nEnter the second string\nProgrammer\nString obtained on concatenation is\nInfiniteProgrammer");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Concatenate strings without strcat function");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                "\n" +
                        "\n" +
                        "#include <stdio.h>\n" +
                        " \n" +
                        "void concatenate(char [], char []); \n" +
                        " \n" +
                        "int main()\n" +
                        "{\n" +
                        "   char p[100], q[100];\n" +
                        " \n" +
                        "   printf(\"Input a string\\n\");\n" +
                        "   gets(p);\n" +
                        " \n" +
                        "   printf(\"Input a string to concatenate\\n\");\n" +
                        "   gets(q);\n" +
                        " \n" +
                        "   concatenate(p, q); \n" +
                        " \n" +
                        "   printf(\"String obtained on concatenation is \\\"%s\\\"\\n\", p);\n" +
                        " \n" +
                        "   return 0;\n" +
                        "}\n" +
                        " \n" +
                        "void concatenate(char p[], char q[]) {\n" +
                        "   int c, d;\n" +
                        " \n" +
                        "   c = 0;\n" +
                        " \n" +
                        "   while (p[c] != '\\0') {\n" +
                        "      c++;   \n" +
                        "   }\n" +
                        " \n" +
                        "   d = 0;\n" +
                        " \n" +
                        "   while (q[d] != '\\0') {\n" +
                        "      p[c] = q[d];\n" +
                        "      d++;\n" +
                        "      c++; \n" +
                        "   }\n" +
                        " \n" +
                        "   p[c] = '\\0';\n" +
                        "}\n" +
                        "\n" );
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Input a string\nInfinite\nInput a string to concatenate\nProgrammer\nString obtained on concatenation is\nInfiniteProgrammer");


        wikiModel = new WikiModel("c24", "String concatenation", programWikis, "c" );
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);

        /***
         * ====================================================================================
         * **//*

        programWikis = new ArrayList<>();
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Insertion sort");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "Insertion sort in c: c program for insertion sort to sort numbers. This code implements insertion sort algorithm to arrange numbers of an array in ascending order. With a little modification it will arrange numbers in descending order.");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                "*//* insertion sort ascending order *//*\n" +
                        " \n" +
                        "#include <stdio.h>\n" +
                        " \n" +
                        "int main()\n" +
                        "{\n" +
                        "  int n, array[1000], c, d, t;\n" +
                        " \n" +
                        "  printf(\"Enter number of elements\\n\");\n" +
                        "  scanf(\"%d\", &n);\n" +
                        " \n" +
                        "  printf(\"Enter %d integers\\n\", n);\n" +
                        " \n" +
                        "  for (c = 0; c < n; c++) {\n" +
                        "    scanf(\"%d\", &array[c]);\n" +
                        "  }\n" +
                        " \n" +
                        "  for (c = 1 ; c <= n - 1; c++) {\n" +
                        "    d = c;\n" +
                        " \n" +
                        "    while ( d > 0 && array[d] < array[d-1]) {\n" +
                        "      t          = array[d];\n" +
                        "      array[d]   = array[d-1];\n" +
                        "      array[d-1] = t;\n" +
                        " \n" +
                        "      d--;\n" +
                        "    }\n" +
                        "  }\n" +
                        " \n" +
                        "  printf(\"Sorted list in ascending order:\\n\");\n" +
                        " \n" +
                        "  for (c = 0; c <= n - 1; c++) {\n" +
                        "    printf(\"%d\\n\", array[c]);\n" +
                        "  }\n" +
                        " \n" +
                        "  return 0;\n" +
                        "}" );
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Enter number of integers\n5\nEnter 5 integers\n5 4 3 2 1\nSorted list in ascending order\n1 2 3 4 5");
        programWikis.add(programWiki);



        wikiModel = new WikiModel("c20", "Insertion sort", programWikis, "c" );
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);

        *//***
         * ====================================================================================
         * **//*

        programWikis = new ArrayList<>();
        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_HEADER);
        programWiki.setHeader("Character's frequency");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM_EXPLANATION);
        programWiki.setProgramExplanation("" +
                "This program computes frequency of characters in a string i.e. which character is present how many times in a string. For example in the string \"code\" each of the character 'c', 'o', 'd', and 'e' has occurred one time. Only lower case alphabets are considered, other characters (uppercase and special characters) are ignored. You can easily modify this program to handle uppercase and special symbols.\n" +
                "Explanation of \"count[string[c]-'a']++\", suppose input string begins with 'a' so c is 0 initially and string[0] = 'a' and string[0]-'a' = 0 and we increment count[0] i.e. a has occurred one time and repeat this till complete string is scanned.");
        programWikis.add(programWiki);

        programWiki = new ProgramWiki();
        programWiki.setContentType(ProgramWiki.CONTENT_PROGRAM);
        programWiki.setProgramExample(
                "\n" +
                        "\n" +
                        "#include <stdio.h>\n" +
                        "#include <string.h>\n" +
                        " \n" +
                        "int main()\n" +
                        "{\n" +
                        "   char string[100];\n" +
                        "   int c = 0, count[26] = {0};\n" +
                        " \n" +
                        "   printf(\"Enter a string\\n\");\n" +
                        "   gets(string);\n" +
                        " \n" +
                        "   while (string[c] != '\\0')\n" +
                        "   {\n" +
                        "      *//** Considering characters from 'a' to 'z' only\n" +
                        "          and ignoring others *//*\n" +
                        " \n" +
                        "      if (string[c] >= 'a' && string[c] <= 'z') \n" +
                        "         count[string[c]-'a']++;\n" +
                        " \n" +
                        "      c++;\n" +
                        "   }\n" +
                        " \n" +
                        "   for (c = 0; c < 26; c++)\n" +
                        "   {\n" +
                        "      *//** Printing only those characters \n" +
                        "          whose count is at least 1 *//*\n" +
                        " \n" +
                        "      if (count[c] != 0)\n" +
                        "         printf(\"%c occurs %d times in the entered string.\\n\",c+'a',count[c]);\n" +
                        "   }\n" +
                        " \n" +
                        "   return 0;\n" +
                        "}\n" +
                        "\n" );
        programWiki.setOutput("" +
                "Output\n" +
                "\n" +
                "Enter a string\na quick brown fox jump over the lazy dogs\n" +
                "a occurs 2 times in the entered string.\n" +
                "b occurs 1 times in the entered string.\n" +
                "c occurs 1 times in the entered string.\n" +
                "d occurs 1 times in the entered string.\n" +
                "e occurs 2 times in the entered string.\n" +
                "f occurs 1 times in the entered string.\n" +
                "g occurs 1 times in the entered string.\n" +
                "h occurs 1 times in the entered string.\n" +
                "i occurs 1 times in the entered string.\n" +
                "j occurs 1 times in the entered string.\n" +
                "k occurs 1 times in the entered string.\n" +
                "l occurs 1 times in the entered string.\n" +
                "m occurs 1 times in the entered string.\n" +
                "n occurs 1 times in the entered string.\n" +
                "o occurs 1 times in the entered string.\n" +
                "p occurs 1 times in the entered string.\n" +
                "q occurs 1 times in the entered string.\n" +
                "r occurs 1 times in the entered string.\n" +
                "s occurs 1 times in the entered string.\n" +
                "t occurs 1 times in the entered string.\n" +
                "u occurs 1 times in the entered string.\n" +
                "v occurs 1 times in the entered string.\n" +
                "w occurs 1 times in the entered string.\n" +
                "x occurs 1 times in the entered string.\n" +
                "y occurs 1 times in the entered string.\n" +
                "z occurs 1 times in the entered string.\n");
        programWikis.add(programWiki);

        wikiModel = new WikiModel("c21", "Character's frequency ", programWikis, "c" );
        firebaseDatabaseHandler.writeProgramWiki(wikiModel);*/
    }

    public void insertProgramIndex() {

        int index = 1;
        String programLanguage = new CreekPreferences(context).getProgramLanguage();
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ POSIX compliant program to check the following limits:\n" +
                "(i) No. of clock ticks (ii) Max. no. of child processes (iii) Max. path length (iv) Max. no. of characters in a file name (v) Max. no. of open files/ process",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ POSIX compliant program that prints the POSIX defined configuration options supported on any given system using feature test macros",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Consider the last 100 bytes as a region. Write a C/C++ program to check whether the region is locked or not. If the region is locked, print pid of the process which has locked. If the region is not locked, lock the region with an exclusive lock, read the last 50 bytes and unlock the region.",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program which demonstrates interprocess communication between a reader process and a writer process. Use mkfifo, open, read, write and close APIs in your program.",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program which demonstrates interprocess communication between a reader process and a writer process. Use mkfifo, open, read, write and close APIs in your program.",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program that outputs the contents of its Environment list",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C / C++ program to emulate the unix \"ln\" command ",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program to illustrate the race condition.",
                programLanguage, ""));

        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program that creates a zombie and then calls system to execute the ps command to verify that the process is zombie",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program to avoid zombie process by forking twice",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program to implement the system function.",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "C/C++ program to set up a real-time clock interval timer using the alarm API. ",
                programLanguage, ""));


    }

    public void insertUSPProgramTables() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        //http://man7.org/linux/man-pages/man3/mkfifo.3.html
        String programCode =
                "#include <stdio.h>\n" +
                        "#include <sys/wait.h>\n" +
                        "#include <errno.h>\n" +
                        "#include <stdlib.h>\n" +
                        "#include <unistd.h>\n" +
                        "int main(){\n" +
                        "pid_t pid;\n" +
                        "if ((pid = fork()) < 0){\n" +
                        "printf(\"fork error\");\n" +
                        "}\n" +
                        "else if (pid == 0){\n" +
                        "if ((pid = fork()) < 0)\n" +
                        "printf(\"fork error\");\n" +
                        "else if (pid > 0)\n" +
                        "exit(0);\n" +
                        "sleep(2);\n" +
                        "printf(\"second child, parent pid = %d\\n\", getppid());\n" +
                        "exit(0);\n" +
                        "}\n" +
                        "if (waitpid(pid, NULL, 0) != pid)\n" +
                        "printf(\"waitpid error\");\n" +
                        "exit(0);\n" +
                        "}";
        //https://gcc.gnu.org/onlinedocs/cpp/Ifdef.html
        String programExplanation =
                "include header <stdio.h>\n" +
                        "include header <sys/wait.h>\n" +
                        "include header <errno.h>\n" +
                        "include header <stdlib.h>\n" +
                        "include header <unistd.h>\n" +
                        "Main declaration\n" +
                        "variable declaration pid_t pid;\n" +
                        "check if ((pid = fork()) < 0)\n" +
                        "print \"fork error\"\n" +
                        "end if\n" +
                        "else check if (pid == 0)\n" +
                        "check if ((pid = fork()) < 0)\n" +
                        "print \"fork error\";\n" +
                        "else check if (pid > 0)\n" +
                        "normal exit with 0;\n" +
                        "sleep for 2 seconds;\n" +
                        "print \"second child, parent pid = %d\\n\", getppid()\n" +
                        "normal exit 0;\n" +
                        "end\n" +
                        "check if (waitpid(pid, NULL, 0) != pid)\n" +
                        "print \"waitpid error\"\n" +
                        "normal exit with status 0\n" +
                        "end main";
        ArrayList<String> programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        ArrayList<String> programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        int programIndex = 10;
        for( int i = 0; i < programLines.size(); i++ ) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i+1,
                            "usp",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "#include<errno.h>\n" +
                        "#include<unistd.h>\n" +
                        "#include<stdio.h>\n" +
                        "#include<stdlib.h>\n" +
                        "int mySystem(const char *cmdstring){\n" +
                        "pid_t pid; int status;\n" +
                        "if (cmdstring == NULL)\n" +
                        "return(1);\n" +
                        "if ((pid = fork()) < 0) {\n" +
                        "status = -1;\n" +
                        "}\n" +
                        "else if (pid == 0){\n" +
                        "execl(\"/bin/sh\", \"sh\", \"-c\", cmdstring, NULL);\n" +
                        "_exit(127);\n" +
                        "}\n" +
                        "else\n" +
                        "while (waitpid(pid, &status, 0) < 0) {\n" +
                        "if (errno != EINTR)\n" +
                        "status = -1; /* error other than EINTR from waitpid()*/\n" +
                        "break;\n" +
                        "}\n" +
                        "return(status);\n" +
                        "}\n" +
                        "int main() {\n" +
                        "int status;\n" +
                        "if ((status = mySystem(\"what\")) < 0)\n" +
                        "printf(\"system() error\");\n" +
                        "if ((status = mySystem(\"who\")) < 0)\n" +
                        "printf(\"system() error\");\n" +
                        "exit(0);\n" +
                        "}";
        programExplanation =
                "include header<errno.h>\n" +
                        "include header<unistd.h>\n" +
                        "include header<stdio.h>\n" +
                        "include header<stdlib.h>\n" +
                        "define function mySystem\n" +
                        "declare variable pid_t pid; int status;\n" +
                        "check if (cmdstring == NULL)\n" +
                        "return 1;\n" +
                        "check if ((pid = fork()) < 0)\n" +
                        "Assign status = -1;\n" +
                        "end if\n" +
                        "check else if (pid == 0)\n" +
                        "call execl(\"/bin/sh\", \"sh\", \"-c\", cmdstring, NULL)\n" +
                        "exit with error code 127\n" +
                        "end else if\n" +
                        "else\n" +
                        "loop while (waitpid(pid, &status, 0) < 0) {\n" +
                        "check if (errno != EINTR)\n" +
                        "assign status = -1; /* error other than EINTR from waitpid()*/\n" +
                        "break loop\n" +
                        "end while\n" +
                        "return(status);\n" +
                        "end function\n" +
                        "Main declaration\n" +
                        "variable declaration status;\n" +
                        "check if ((status = mySystem(\"what\")) < 0)\n" +
                        "print \"system() error\"\n" +
                        "check if status = mySystem(\"who\")) < 0\n" +
                        "print \"system() error\"\n" +
                        "normal exit with status 0;\n" +
                        "end main";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for( int i = 0; i < programLines.size(); i++ ) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i+1,
                            "usp",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "#include<signal.h>\n" +
                        "#include<stdio.h>\n" +
                        "#include<unistd.h>\n" +
                        "#include<errno.h>\n" +
                        "void wakeup(){\n" +
                        "printf(\"Hello\\n\");\n" +
                        "}\n" +
                        "int main(){\n" +
                        "signal(SIGALRM,&wakeup);\n" +
                        "while(1){\n" +
                        "alarm(5);\n" +
                        "pause();\n" +
                        "printf(\"Waiting For Alarm\\n\");\n" +
                        "}\n" +
                        "return 0;\n" +
                        "}";
        programExplanation =
                "include header<signal.h>\n" +
                        "include header<stdio.h>\n" +
                        "include header<unistd.h>\n" +
                        "include header<errno.h>\n" +
                        "define function wakeup\n" +
                        "print \"Hello\\n\"\n" +
                        "end function\n" +
                        "define main\n" +
                        "call signal(SIGALRM, &wakeup);\n" +
                        "loop till while(1)\n" +
                        "call alarm(5);\n" +
                        "call pause();\n" +
                        "print \"Waiting For Alarm\\n\"\n" +
                        "end while\n" +
                        "return 0;\n" +
                        "end main";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for( int i = 0; i < programLines.size(); i++ ) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i+1,
                            "usp",
                            programLines.get(i),
                            programExplanations.get(i)));
        }
    }
}
