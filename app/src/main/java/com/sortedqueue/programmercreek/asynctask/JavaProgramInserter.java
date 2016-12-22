package com.sortedqueue.programmercreek.asynctask;

import android.content.Context;

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
        Program_Index program_index1 = new Program_Index(index++, "Prime numbers", "http://www.instanceofjava.com/2014/12/program-to-print-prime-numbers-in-java.html");
        Program_Index program_index2 = new Program_Index(index++, "Command line arguments", "http://introcs.cs.princeton.edu/java/11hello/UseArgument.java.html");
        Program_Index program_index3 = new Program_Index(index++, "String concatenation example", "http://introcs.cs.princeton.edu/java/12types/Ruler.java.html");
        Program_Index program_index4 = new Program_Index(index++, "Quadratic formula", "http://introcs.cs.princeton.edu/java/12types/Quadratic.java.html");
        Program_Index program_index5 = new Program_Index(index++, "Your first while loop", "http://introcs.cs.princeton.edu/java/13flow/TenHellos.java.html");
        Program_Index program_index6 = new Program_Index(index++, "Your first nested loops", "http://introcs.cs.princeton.edu/java/13flow/DivisorPattern.java.html");
        Program_Index program_index7 = new Program_Index(index++, "Interactive user input", "http://introcs.cs.princeton.edu/java/15inout/TwentyQuestions.java.html");
        Program_Index program_index8 = new Program_Index(index++, "Factorial Recursion", "http://introcs.cs.princeton.edu/java/23recursion/Factorial.java.html");
        Program_Index program_index9 = new Program_Index(index++, "Binary search", "http://introcs.cs.princeton.edu/java/42sort/Questions.java.html");

        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramIndex(program_index1);
        firebaseDatabaseHandler.writeProgramIndex(program_index2);
        firebaseDatabaseHandler.writeProgramIndex(program_index3);
        firebaseDatabaseHandler.writeProgramIndex(program_index4);
        firebaseDatabaseHandler.writeProgramIndex(program_index5);
        firebaseDatabaseHandler.writeProgramIndex(program_index6);
        firebaseDatabaseHandler.writeProgramIndex(program_index7);
        firebaseDatabaseHandler.writeProgramIndex(program_index8);
        firebaseDatabaseHandler.writeProgramIndex(program_index9);
        CommonUtils.dismissProgressDialog();
    }*/

    /*public void insertProgramTables( ) {
        CommonUtils.displayProgressDialog(context, "Inserting program tables");
        int programIndex = 9;
        int lineNo = 1;
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "public class RecursiveFactorial {", "Class definition"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "  public static void main(String[] args) {", "Main declaration"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "   int k = Integer.parseInt(args[0]);", "initialize k"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "   int n = (int) Math.pow(2, k);", "Initialize n"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "   StdOut.printf(\"Think of an integer between %d and %d\\n\", 0, n-1);", "Print to standard output : Think of an integer..."));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "   int secret = search(0, n);", "Call search function"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "   StdOut.printf(\"Your number is %d\\n\", secret);", "Print to standard output : the secret number"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "  }", "End of main"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "  public static int search(int lo, int hi) {", "search function declaration"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "    if ((hi - lo) == 1) return lo;", "Check if hi - lo = 1 ? return lo"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "    int mid = lo + (hi - lo) / 2;", "Assign mid = lo + (hi-lo)/2"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "    StdOut.printf(\"Is it less than %d?  \", mid);", "Print Is it less than mid"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "    if (StdIn.readBoolean()) return search(lo, mid);", "if true, search from lo to mid"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "    else return search(mid, hi);", "else search from mid to hi"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "   }", "End of search"));
        firebaseDatabaseHandler.writeProgramTable( new Program_Table(programIndex, lineNo++, "}", "End of class"));
        CommonUtils.dismissProgressDialog();
    }*/
}
