package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;

import com.sortedqueue.programmercreek.database.ModuleOption;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.SyntaxModule;
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

        moduleOptions.add(new ModuleOption(index++, ""));
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
                        "Primitive data types",
                        " The eight primitive data types in Java are:\n" +
                                "\n" +
                                "    boolean, the type whose values are either true or false\n" +
                                "    char, the character type whose values are 16-bit Unicode characters\n" +
                                "    the arithmetic types:\n" +
                                "        the integral types:\n" +
                                "            byte\n" +
                                "            short\n" +
                                "            int\n" +
                                "            long \n" +
                                "        the floating-point types:\n" +
                                "            float\n" +
                                "            double \n\n",
                        "Example : \n\n" +
                                "Type : Description : Default : Size : Example Literals" +
                                "boolean : true or false : false : 1 bit : true, false\n" +
                                "byte : twos complement integer : 0 : 8 bits : (none)\n" +
                                "char : Unicode character : \\u0000 : 16 bits  : 'a', '\\u0041', '\\101', '\\\\', '\\'', '\\n', 'ß'\n" +
                                "short : twos complement integer : 0 : 16 bits : (none)\n" +
                                "int : twos complement integer : 0 : 32 bits : -2, -1, 0, 1, 2\n" +
                                "long : twos complement integer : 0 : 64 bits : -2L, -1L, 0L, 1L, 2L\n" +
                                "float : IEEE 754 floating point : 0.0 : 32 bits  : 1.23e100f, -1.23e-100f, .3f, 3.14F\n" +
                                "double : IEEE 754 floating point : 0.0 : 64 bits  : 1.23456e300d, -1.23456e-300d, 1e1d",
                        "Description : \n" +
                                "Above class contains all the types of variables in java",
                        "",

                        "",
                        "",
                        moduleOptions
                        ));

        moduleOptions = new ArrayList<>();
        index = 0;

        moduleOptions.add(new ModuleOption(index++, "class Code "));
        moduleOptions.add(new ModuleOption(index++, "String program_Language;\n"));
        moduleOptions.add(new ModuleOption(index++, "{\n"));
        moduleOptions.add(new ModuleOption(index++, "}"));


        firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_2",
                        generatedId,
                        programLanguage,
                        "Non-primitive, or reference data types ",
                        "Non-primitive, or reference data types are the more sophisticated members of the data type family. They don't store the value, but store a reference to that value. Instead of partNumber 4030023, Java keeps the reference (also called address) to that value, not the value itself.\n" +
                                "\n" +
                                "Reference types can be a class, interface, or array variable. Remember that a class is a set of plans for a given object. There are thousands of Tree objects, but the parent set of plans would belong in the Tree class. Variables can exist inside the tree class, such as height or tree type. These are reference variables.\n" +
                                "\n" +
                                "An array is a single object that contains multiple values of the same type. We could have declared our integer for partNumbers as an array to hold a given number of partNumbers in a single object. \n\n" +
                                "Class Data Types\n" +
                                "\n" +
                                "Let's say we declare a new class called Product: " +
                                "\nclass Product {\n" +
                                "//Class specific code" +
                                "}\n\n" +
                                "In order to create a new non-primitive or reference variable for this class, we have to create a new instance of the Product class. The new keyword is used to create an object. Look at the following example where we'll be creating a new Product called car wax.\n" +
                                "\n" +
                                "The Java code is as follows: ",
                        "Example : \n" +
                                "Product carwax = new Product();",
                        "Output : \n " +
                                "So now we have a variable of carWax: But it's really an instance of the Product class, and not a set value like the primitive variables. ",
                        "Declare a new class Code : with variable : String program_Language :",

                        "A new class called Code has been made",
                        "class Code {\n" +
                                "String program_Language;\n" +
                                "}",
                        moduleOptions
                ));

        moduleOptions = new ArrayList<>();
        index = 0;

        moduleOptions.add(new ModuleOption(index++, "Code readCode();\n"));
        moduleOptions.add(new ModuleOption(index++, "String program_Language;\n"));
        moduleOptions.add(new ModuleOption(index++, "public interface readCode\n"));
        moduleOptions.add(new ModuleOption(index++, " {\n"));
        moduleOptions.add(new ModuleOption(index++, "}"));


        firebaseDatabaseHandler.writeSyntaxModule(
                new SyntaxModule(
                        "s_3",
                        generatedId,
                        programLanguage,
                        "Interface Data Types",
                        "An interface is like a dashboard or control panel for a class. It has the buttons, but the function is elsewhere. " +
                                "We won't go into detail on implementing interfaces since the focus is on the interface as a " +
                                "non-primitive, or reference, data type. ",
                        "Example : \n" +
                                "public interface writeCode {\n" +
                                "\nint onCodeInput(Code code,\n" +
                                "            String programLanguage,\n" +
                                "            String programLineCode,\n" +
                                "            String comments);\n" +
                                "//Code here is a custom class defined" +
                                "}\n\n" +
                                "public class JavaProgram implements writeCode {\n" +
                                " int onCodeInput( Code code, String programLanguage, String programLineCode, String comments ) {\n" +
                                " //Your implementation to do something with the parameters.\n}" +
                                "}",
                        "Description : \n " +
                                "To use an interface, you write a class that implements the interface. When an instantiable class implements an interface, it provides a method body for each of the methods declared in the interface. ",
                        "Declare a new interface readCode : with method : Code readCode() :",

                        "A new class called Code has been made",
                        "public interface readCode {\n" +
                                "Code readCode();\n" +
                                "}",
                        moduleOptions
                ));
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
}
