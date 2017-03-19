package com.sortedqueue.programmercreek.asynctask;

import android.app.Activity;
import android.content.Context;

import com.sortedqueue.programmercreek.constants.AlgorithmConstants;
import com.sortedqueue.programmercreek.database.Algorithm;
import com.sortedqueue.programmercreek.database.AlgorithmContent;
import com.sortedqueue.programmercreek.database.AlgorithmsIndex;
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

    public void insertAlgorithmIndex() {
        int index = 1;
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        Algorithm algorithm;
        ArrayList<AlgorithmContent> algorithmContentArrayList;
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                "Quick Sort",
                "Sort a given set of elements using the Quicksort method and determine the time required to sort the elements.\n" +
                "Repeat the experiment for different values of n, the number of elements in the list to be sorted and plot a graph of\n" +
                "the time taken versus n. The elements can be read from a file or can be generated using the random number\n" +
                "generator.", "c" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Quick Sort",
                "Sort a given set of elements using the Quicksort method and determine the time required to sort the elements.\n" +
                        "Repeat the experiment for different values of n, the number of elements in the list to be sorted and plot a graph of\n" +
                        "the time taken versus n. The elements can be read from a file or can be generated using the random number\n" +
                        "generator.", "c" ));
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "To Sort a given set of elements using the Quicksort method and determine the time required to sort the elements.\n" +
                        "Repeat the experiment for different values of n, the number of elements in the list to be sorted and plot a graph of the\n" +
                        "time taken versus n. The elements can be read from a file or can be generated using the random number generator.",
                "The program is based on the Quicksort algorithm which is an instatiation of divide and conquer method of solving the problem.\n" +
                        "Here the given array is partitioned every time and the sub-array is sorted.Dividing is based on an element called pivot. A divide\n" +
                        "and conquer algorithm works by recursively breaking down a problem into two or more sub-problems of the same (or related)\n" +
                        "type, until these become simple enough to be solved directly. The solutions to the sub-problems are then combined to give a\n" +
                        "solution to the original problem."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "1. Pick an element, called a pivot, from the list.\n" +
                        "Reorder the list so that all elements with values less than the pivot come before the pivot, while all elements with\n" +
                        "values greater than the pivot come after it (equal values can go either way). After this partitioning, the pivot is in its\n" +
                        "final position. This is called the partition operation.\n" +
                        "2.\n" +
                        "Recursively apply the above steps to the sub-list of elements with smaller values and separately the sub-list of\n" +
                        "elements with greater values."));

        int codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <stdio.h>\n" +
                        "#include <stdlib.h>\n" +
                        "#include <sys/time.h>\n" +
                        "#include <time.h>\n" +
                        "void fnGenRandInput(int [], int);\n" +
                        "void fnDispArray( int [], int);\n" +
                        "int fnPartition(int [], int , int );\n" +
                        "void fnQuickSort(int [], int , int );\n" +
                        "inline void fnSwap(int*, int*);\n" +
                        "inline void fnSwap(int *a, int *b)\n" +
                        "{\n" +
                        "int t = *a; *a = *b; *b = t;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main( int argc, char **argv)\n" +
                        "{\n" +
                        "FILE *fp;\n" +
                        "struct timeval tv;\n"+
                "double dStart,dEnd;\n" +
                        "int iaArr[500000],iNum,iPos,iKey,i,iChoice;\n" +
                        "for(;;)\n" +
                        "{\n" +
                        "printf(\"\\n1.Plot the Graph\\n2.QuickSort\\n3.Exit\");\n" +
                        "printf(\"\\nEnter your choice\\n\");\n" +
                        "scanf(\"%d\",&iChoice);\n" +
                        "switch(iChoice)\n" +
                        "{\n" +
                        "case 1:\n" +
                        "fp = fopen(\"QuickPlot.dat\",\"w\");\n" +
                        "for(i=100;i<100000;i+=100)\n" +
                        "{\n" +
                        "fnGenRandInput(iaArr,i);\n" +
                        "gettimeofday(&tv,NULL);\n" +
                        "dStart = tv.tv_sec + (tv.tv_usec/1000000.0);\n" +
                        "fnQuickSort(iaArr,0,i-1);\n" +
                        "gettimeofday(&tv,NULL);\n" +
                        "dEnd = tv.tv_sec + (tv.tv_usec/1000000.0);\n" +
                        "fprintf(fp,\"%d\\t%lf\\n\",i,dEnd-dStart);\n" +
                        "}\n" +
                        "fclose(fp);\n" +
                        "printf(\"\\nData File generated and stored in file < QuickPlot.dat >.\\n Use a plottin\n" +
                        "g utility\\n\");\n" +
                        "break;\n" +
                        "case 2:\n" +
                        "printf(\"\\nEnter the number of elements to sort\\n\");\n" +
                        "scanf(\"%d\",&iNum);\n" +
                        "printf(\"\\nUnsorted Array\\n\");\n" +
                        "fnGenRandInput(iaArr,iNum);\n" +
                        "fnDispArray(iaArr,iNum);\n" +
                        "fnQuickSort(iaArr,0,iNum-1);\n" +
                        "printf(\"\\nSorted Array\\n\");\n" +
                        "fnDispArray(iaArr,iNum);\n" +
                        "break;\n" +
                        "case 3:\n" +
                        "exit(0);\n" +
                        "}\n" +
                        "}\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int fnPartition(int a[], int l, int r)\n" +
                        "{\n" +
                        "int i,j,temp;\n" +
                        "int p;\n" +
                        "p = a[l];\n" +
                        "i = l;\n" +
                        "j = r+1;\n" +
                        "do\n" +
                        "{\n" +
                        "do { i++; } while (a[i] < p);\n" +
                        "do { j--; } while (a[j] > p);\n" +
                        "fnSwap(&a[i], &a[j]);\n" +
                        "}\n" +
                        "while (i<j);\n" +
                        "fnSwap(&a[i], &a[j]);\n" +
                        "fnSwap(&a[l], &a[j]);\n" +
                        "return j;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnQuickSort(int a[], int l, int r)\n" +
                        "{\n" +
                        "int s;\n" +
                        "if (l < r)\n" +
                        "{\n" +
                        "s = fnPartition(a, l, r);\n" +
                        "fnQuickSort(a, l, s-1);\n" +
                        "fnQuickSort(a, s+1, r);\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnGenRandInput(int X[], int n)\n" +
                        "{\n" +
                        "int i;+\n"+
        "srand(time(NULL));\n" +
                        "for(i=0;i<n;i++)\n" +
                        "{\n" +
                        "X[i] = rand()%10000;\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnDispArray( int X[], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "for(i=0;i<n;i++)\n" +
                        "printf(\" %5d \\n\",X[i]);\n" +
                        "}"));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Merge Sort",
                        "Using OpenMP, implement a parallelized Merge Sort algorithm to sort a given set of elements and determine the\n" +
                                "time required to sort the elements. Repeat the experiment for different values of n, the number of elements in the\n" +
                                "list to be sorted and plot a graph of the time taken versus n. The elements can be read from a file or can be\n" +
                                "generated using the random number generator.", "c" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Merge Sort",
                "Using OpenMP, implement a parallelized Merge Sort algorithm to sort a given set of elements and determine the\n" +
                        "time required to sort the elements. Repeat the experiment for different values of n, the number of elements in the\n" +
                        "list to be sorted and plot a graph of the time taken versus n. The elements can be read from a file or can be\n" +
                        "generated using the random number generator.", "c"  ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Program to sort an array using Merge Sort",
                "Merge sort is an O(n log n) comparison-based sorting algorithm. Most implementations produce a stable sort, meaning that the\n" +
                        "implementation preserves the input order of equal elements in the sorted output. It is a divide and conquer algorithm."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "1. Mergesort(A[O .. n - 1])\n" +
                        "2. Sorts array A[O .. n - 1] by recursive mergesort\n" +
                        "3. Input: An array A[O .. n - 1] of orderable elements\n" +
                        "4. Output: Array A[O .. n - 1] sorted in nondecreasing order\n" +
                        "5. Merge(B[O .. p- 1], C[O .. q -1], A[O.. p + q -1])\n" +
                        "6. Merges two sorted arrays into one sorted array\n" +
                        "7. Input: Arrays B[O .. p -1] and C[O .. q -1] both sorted\n" +
                        "8. Output: Sorted array A[O .. p + q -1] of the elements of Band C"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <stdio.h>\n" +
                        "#include <stdlib.h>\n" +
                        "#include <sys/time.h>\n" +
                        "#include <omp.h>\n" +
                        "void simplemerge(int a[], int low, int mid, int high)\n" +
                        "{\n" +
                        "int i,j,k,c[20000];\n" +
                        "i=low;\n" +
                        "j=mid+1;\n" +
                        "k=low;\n" +
                        "int tid;\n" +
                        "omp_set_num_threads(10);\n" +
                        "{\n" +
                        "tid=omp_get_thread_num();\n" +
                        "while(i<=mid&&j<=high)\n" +
                        "{\n" +
                        "if(a[i] < a[j])\n" +
                        "{\n"+
                        "c[k]=a[i];\n" +
                        "//printf(\"%d%d\",tid,c[k]);\n" +
                        "i++;\n" +
                        "k++;\n" +
                        "}\n" +
                        "else\n" +
                        "{\n" +
                        "c[k]=a[j];\n" +
                        "//printf(\"%d%d\", tid, c[k]);\n" +
                        "j++;\n" +
                        "k++;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "while(i<=mid)\n" +
                        "{\n" +
                        "c[k]=a[i];\n" +
                        "i++;\n" +
                        "k++;\n" +
                        "}\n" +
                        "while(j<=high)\n" +
                        "{\n" +
                        "c[k]=a[j];\n" +
                        "j++;\n" +
                        "k++;\n" +
                        "}\n" +
                        "for(k=low;k<=high;k++)\n" +
                        "a[k]=c[k];\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void merge(int a[],int low,int high)\n" +
                        "{\n" +
                        "int mid;\n" +
                        "if(low < high)\n" +
                        "{\n" +
                        "mid=(low+high)/2;\n" +
                        "merge(a,low,mid);\n" +
                        "merge(a,mid+1,high);\n" +
                        "simplemerge(a,low,mid,high);\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void getnumber(int a[], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "for(i=0;i < n;i++)\n" +
                        "a[i]=rand()%100;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main()\n" +
                        "{\n" +
                        "FILE *fp;\n" +
                        "int a[2000],i;\n" +
                        "struct timeval tv;\n" +
                        "double start, end, elapse;\n" +
                        "fp=fopen(\"mergesort.txt\",\"w\");\n" +
                        "for(i=10;i<=1000;i+=10)\n" +
                        "{\n" +
                        "getnumber(a,i);\n" +
                        "gettimeofday(&tv,NULL);\n" +
                        "start=tv.tv_sec+(tv.tv_usec/1000000.0);\n" +
                        "merge(a,0,i-1);\n" +
                        "gettimeofday(&tv,NULL);\n" +
                        "end=tv.tv_sec+(tv.tv_usec/1000000.0);\n" +
                        "elapse=end-start;\n" +
                        "fprintf(fp,\"%d\\t%lf\\n\",i,elapse);\n" +
                        "}\n" +
                        "fclose(fp);\n" +
                        "system(\"gnuplot\");\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "GPL",
                "mergesort.gpl\n" +
                        "Gnuplot script file for plotting data in file \"mergesort.txt\" This file is called mergesort.gpl\n" +
                        "set terminal png font arial\n" +
                        "set title \"Time Complexity for Merge Sort\"\n" +
                        "set autoscale\n" +
                        "set xlabel \"Size of Input\"\n" +
                        "set ylabel \"Sorting Time (microseconds)\"\n" +
                        "set grid\n" +
                        "set output \"mergesort.png\"\n" +
                        "plot \"mergesort.txt\" t \"Merge Sort\" with lines"));



        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Topological Ordering",
                        "Obtain the Topological ordering of vertices in a given digraph.", "c" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Topological Ordering",
                "Obtain the Topological ordering of vertices in a given digraph.", "c"  ));
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "To obtain the Topological ordering of vertices in a given\n" +
                        "digraph.",
                "Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every directed edge uv, vertex u\n" +
                        "comes before v in the ordering.Topological Sorting for a graph is not possible if the graph is not a DAG. Input parameters: int\n" +
                        "a[MAX][MAX] - adjacency matrix of the input graph int n - no of vertices in the graph"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "L Empty list that will contain the sorted elements S Set of all nodes with no incoming edges while S is non-empty do\n" +
                        "remove a node n from S add n to tail of L for each node m with an edge e from n to m do remove edge e from the\n" +
                        "graph if m has no other incoming edges then insert m into S if graph has edges then return error (graph has at least\n" +
                        "one cycle) else return L (a topologically sorted order)"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <stdio.h>\n" +
                        "const int MAX = 10;\n" +
                        "void fnTopological(int a[MAX][MAX], int n);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        "int a[MAX][MAX],n;\n" +
                        "int i,j;\n" +
                        "printf(\"Topological Sorting Algorithm -\\n\");\n" +
                        "printf(\"\\nEnter the number of vertices : \");\n" +
                        "scanf(\"%d\",&n);\n" +
                        "printf(\"Enter the adjacency matrix -\\n\");\n" +
                        "for (i=0; i<n; i++)\n" +
                        "for (j=0; j<n; j++)\n" +
                        "scanf(\"%d\",&a[i][j]);\n" +
                        "fnTopological(a,n);\n" +
                        "printf(\"\\n\");\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnTopological(int a[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int in[MAX], out[MAX], stack[MAX], top=-1;\n" +
                        "int i,j,k=0;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "in[i] = 0;\n" +
                        "for (j=0; j<n; j++)\n" +
                        "if (a[j][i] == 1)\n" +
                        "in[i]++;\n" +
                        "}\n"+
                        "while(1)\n" +
                        "{\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (in[i] == 0)\n" +
                        "{\n" +
                        "stack[++top] = i;\n" +
                        "in[i] = -1;\n" +
                        "}\n" +
                        "}\n" +
                        "if (top == -1)\n" +
                        "break;\n" +
                        "out[k] = stack[top--];\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (a[out[k]][i] == 1)\n" +
                        "in[i]--;\n" +
                        "}\n" +
                        "k++;\n" +
                        "}\n" +
                        "printf(\"Topological Sorting (JOB SEQUENCE) is:- \\n\");\n" +
                        "for (i=0;i<k;i++)\n" +
                        "printf(\"%d \",out[i] + 1);\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Input Graph : 5 vertices 0 0 1 0 0 0 0 1 0 0 0 0 0 1 1 0 0 0 0 1 0 0 0 0 0\n" +
                        "Topological Sorting (JOB SEQUENCE) is:- 2 1 3 4 5"));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Warshall's algorithm",
                        "Compute the transitive closure of a given\n" +
                                "directed graph using Warshall's algorithm.", "c" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Warshall's algorithm",
                "Compute the transitive closure of a given\n" +
                        "directed graph using Warshall's algorithm.", "c"  ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Compute the transitive closure of a given directed graph\n" +
                        "using Warshall's algorithm.",
                "Warshall's algorithm determines whether there is a path between any two nodes in the graph. It does not give the number of the\n" +
                        "paths between two nodes. According to Warshall's algorith,a path exists between two vertices i, j, iff there is a path from i to j or\n" +
                        "there is a path from i to j through 1,..,k intermadiate nodes."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "n = |V|\n" +
                        "t(0) = the adjacency matrix for G\n" +
                        "for i in 1..n do\n" +
                        "t(0)[i,i] = True\n" +
                        "end for\n" +
                        "for k in 1..n do\n" +
                        "for i in 1..n do\n" +
                        "for j in 1..n do\n" +
                        "t(k)[i,j] = t(k-1)[i,j] OR\n" +
                        "(t(k-1)[i,k] AND t(k-1)[k,j])\n" +
                        "end for\n" +
                        "end for\n" +
                        "end for\n" +
                        "return t(n)"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include<stdio.h>\n" +
                        "const int MAX = 100;\n" +
                        "void WarshallTransitiveClosure(int graph[MAX][MAX], int numVert);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        "int i, j, numVert;\n" +
                        "int graph[MAX][MAX];\n" +
                        "printf(\"Warshall's Transitive Closure\\n\");\n" +
                        "printf(\"Enter the number of vertices : \");\n" +
                        "scanf(\"%d\",&numVert);\n" +
                        "printf(\"Enter the adjacency matrix :-\\n\");\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        "scanf(\"%d\",&graph[i][j]);\n" +
                        "WarshallTransitiveClosure(graph, numVert);\n" +
                        "printf(\"\\nThe transitive closure for the given graph is :-\\n\");\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "{\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        "{\n" +
                        "printf(\"%d\\t\",graph[i][j]);\n" +
                        "}\n" +
                        "printf(\"\\n\");\n" +
                        "}\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void WarshallTransitiveClosure(int graph[MAX][MAX], int numVert)\n" +
                        "{\n" +
                        "int i,j,k;\n" +
                        "for (k=0; k<numVert; k++)\n" +
                        "{\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "{\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        "{\n" +
                        "if (graph[i][j] || (graph[i][k] && graph[k][j]))\n" +
                        "graph[i][j] = 1;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of vertices : 4 Enter the adjacency matrix :- 0 0 1 0 0 0 0 1 1 0 0 0 0 1 0 0\n" +
                        "The transitive closure for the given graph is :- 1 0 1 0\n" +
                        "0 1 0 1\n" +
                        "1 0 1 0\n" +
                        "0 1 0 1\n" +
                        "Warshall's Transitive Closure Enter the number of vertices : 4 Enter the adjacency matrix :-\n" +
                        "0 1 1 0 1 0 0 1 1 0 0 1 0 1 1 0\n" +
                        "The transitive closure for the given graph is :- 1 1 1 1\n" +
                        "1 1 1 1\n" +
                        "1 1 1 1\n" +
                        "1 1 1 1\n" +
                        ""));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "0/1 Knapsack problem",
                        "Implement 0/1 Knapsack problem using Dynamic Programming.", "c" ));

        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "0/1 Knapsack problem",
                "Implement 0/1 Knapsack problem using Dynamic Programming.", "c"  ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "To solve 0/1 Knapsack problem using Dynamic Programming.",
                "The Knapsack problem is probably one of the most interesting and most popular in computer science, especially when we talk\n" +
                        "about dynamic programming.The knapsack problem is a problem in combinatorial optimization. Given a set of items, each with a\n" +
                        "weight and a value, determine the number of each item to include in a collection so that the total weight is less than or equal to a\n" +
                        "given limit and the total value is as large as possible. It derives its name from the problem faced by someone who is constrained\n" +
                        "by a fixed-size knapsack and must fill it with the most valuable items."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "Input:\n" +
                        "a set of items with weights and values\n" +
                        "output:\n" +
                        "the greatest combined value of a subset\n" +
                        "partition the set {1...n} into two sets A and B of approximately equal size\n" +
                        "compute the weights and values of all subsets of each set\n" +
                        "for each subset of A\n" +
                        "find the subset of B of greatest value such that the combined weight is less than W\n" +
                        "keep track of the greatest combined value seen so far\n" +
                        "PseudoCode Explained : \n" +
                        "Input:\n" +
                        "Values (stored in array v or profit)\n" +
                        "Weights (stored in array w or weight)\n" +
                        "Number of distinct items (n)\n" +
                        "Knapsack capacity (W)\n" +
                        "for j from 0 to W do\n" +
                        "m[0, j] = 0\n" +
                        "end for\n" +
                        "for i from 1 to n do\n" +
                        "for j from 0 to W do\n" +
                        "if w[i] <= j then\n" +
                        "m[i, j] = max(m[i-1, j], m[i-1, j-w[i]] + v[i])\n" +
                        "else\n" +
                        "m[i, j] = m[i-1, j]\n" +
                        "end if\n" +
                        "end for\n" +
                        "end for"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "#include <cstdlib>\n" +
                        "using namespace std;\n" +
                        "const int MAX = 10;\n" +
                        "inline int max(int a, int b);\n" +
                        "void fnProfitTable(int w[MAX], int p[MAX], int n, int c, int t[MAX][MAX]);\n" +
                        "void fnSelectItems(int n,int c, int t[MAX][MAX], int w[MAX], int l[MAX]);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        "int i, j, totalProfit;\n" +
                        "int weight[MAX];\n" +
                        "int profit[MAX];\n" +
                        "int capacity;\n" +
                        "int num;\n" +
                        "int loaded[MAX];\n" +
                        "int table[MAX][MAX];\n" +
                        "cout<<\"Enter the maxium number of objects : \";\n" +
                        "cin >> num;\n" +
                        "cout << \"Enter the weights : \\n\";\n" +
                        "for (i=1; i<=num; i++)\n" +
                        "{\n" +
                        "cout << \"\\nWeight \" << i << \": \";\n" +
                        "cin >> weight[i];\n" +
                        "}\n" +
                        "cout << \"\\nEnter the profits : \\n\";\n" +
                        "for (i=1; i<=num; i++)\n" +
                        "{\n" +
                        "cout << \"\\nProfit \" << i << \": \";\n" +
                        "cin >> profit[i];\n" +
                        "}\n" +
                        "cout << \"\\nEnter the maximum capacity : \";\n" +
                        "cin >> capacity;\n" +
                        "totalProfit = 0;\n" +
                        "for( i=1; i<=num; i++)\n" +
                        "loaded[i] = 0;\n" +
                        "fnProfitTable(weight,profit,num,capacity,table);\n" +
                        "fnSelectItems(num,capacity,table,weight,loaded);\n" +
                        "cout << \"Profit Matrix\\n\";\n" +
                        "for (i=0; i<=num; i++)\n" +
                        "{\n" +
                        "for(j=0; j<=capacity; j++)\n" +
                        "{\n" +
                        "cout <<\"\\t\"<<table[i][j];\n" +
                        "}\n" +
                        "cout << endl;\n" +
                        "}\n" +
                        "cout << \"\\nItem numbers which are loaded : \\n{ \";\n" +
                        "for (i=1; i<=num; i++)\n" +
                        "{\n" +
                        "if (loaded[i])\n" +
                        "{\n" +
                        "cout <<i << \" \";\n" +
                        "totalProfit += profit[i];\n" +
                        "}\n" +
                        "}\n" +
                        "cout << \"}\" << endl;\n" +
                        "cout << \"\\nTotal Profit : \" << totalProfit << endl;\n" +
                        "return 0;\n" +
                        "}\n" +
                        "inline int max(int a, int b)\n" +
                        "{\n" +
                        "return a>b ? a : b;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnProfitTable(int w[MAX], int p[MAX], int n, int c, int t[MAX][MAX])\n" +
                        "{\n" +
                        "int i,j;\n" +
                        "for (j=0; j<=c; j++)\n" +
                        "t[0][j] = 0;\n" +
                        "for (i=0; i<=n; i++)\n" +
                        "t[i][0] = 0;\n" +
                        "for (i=1; i<=n; i++)\n" +
                        "{\n" +
                        "for (j=1; j<=c; j++)\n" +
                        "{\n" +
                        "if (j-w[i] < 0)\n" +
                        "t[i][j] = t[i-1][j];\n" +
                        "else\n" +
                        "t[i][j] = max( t[i-1][j], p[i] + t[i-1][j-w[i]]);\n" +
                        "}\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnSelectItems(int n,int c, int t[MAX][MAX], int w[MAX], int l[MAX])\n" +
                        "{\n" +
                        "int i,j;\n" +
                        "i = n;\n" +
                        "j = c;\n" +
                        "while (i >= 1 && j >= 1)\n" +
                        "{\n" +
                        "if (t[i][j] != t[i-1][j])\n" +
                        "{\n" +
                        "l[i] = 1;\n" +
                        "j = j - w[i];\n" +
                        "i--;\n" +
                        "}\n" +
                        "else\n" +
                        "i--;\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the maxium number of objects : 4 Enter the weights :\n" +
                        "Weight 1: 2\n" +
                        "Weight 2: 1\n" +
                        "Weight 3: 3\n" +
                        "Weight 4: 2\n" +
                        "Enter the profits :\n" +
                        "Profit 1: 12\n" +
                        "Profit 2: 10\n" +
                        "Profit 3: 20\n" +
                        "Profit 4: 15\n" +
                        "Enter the maximum capacity : 5 Profit Matrix 0 0 0 0 0 0 0 0 12 12 12 12 0 10 12 22 22 22 0 10 12 22 30 32 0 10 15\n" +
                        "25 30 37\n" +
                        "Item numbers which are loaded : { 1 2 4 }\n" +
                        "Total Profit : 37\n" +
                        ""));


        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );

        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Dijkstra's algorithm.",
                        "From a given vertex in a weighted connected graph, find shortest paths to other vertices using Dijkstra's algorithm.", "c++" ));

        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Dijkstra's algorithm.",
                "From a given vertex in a weighted connected graph, find shortest paths to other vertices using Dijkstra's algorithm.", "c++" ));
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "From a given vertex in a weighted connected\n" +
                        "graph, find shortest paths to other vertices using Dijkstra's\n" +
                        "algorithm.",
                "To find the shortest path between points, the weight or length of a path is calculated as the sum of the weights of\n" +
                        "the edges in the path.\n" +
                        "1.\n" +
                        "A path is a shortest pa 2. th is there is no path from x to y with lower weight.\n" +
                        "Dijkstra's algorithm finds the shortest path from x to y in order of increasing distance from x. That is, it chooses the\n" +
                        "first minimum edge, stores this value and adds the next minimum value from the next edge it selects.\n" +
                        "3.\n" +
                        "4. It starts out at one vertex and branches out by selecting certain edges that lead to new vertices.\n" +
                        "5. It is similar to the minimum spanning tree algorithm, in that it is \"greedy\", always choosing the closest edge in\n" +
                        "hopes of an optimal solution."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "function Dijkstra(Graph, source):\n" +
                        "for each vertex v in Graph: // Initializations\n" +
                        "dist[v] := infinity ; // Unknown distance function from\n" +
                        "// source to v\n" +
                        "previous[v] := undefined ; // Previous node in optimal path\n" +
                        "end for // from source\n" +
                        "dist[source] := 0 ; // Distance from source to sou\n" +
                        "rce\n" +
                        "Q := the set of all nodes in Graph ; // All nodes in the graph are\n" +
                        "// unoptimized - thus are in Q\n" +
                        "while Q is not empty: // The main loop\n" +
                        "u := vertex in Q with smallest distance in dist[] ; // Source node in first case\n" +
                        "remove u from Q ;\n" +
                        "if dist[u] = infinity:\n" +
                        "break ; // all remaining vertices are\n" +
                        "end if // inaccessible from source\n" +
                        "for each neighbor v of u: // where v has not yet been\n" +
                        "// removed from Q.\n" +
                        "alt := dist[u] + dist_between(u, v) ;\n" +
                        "if alt < dist[v]: // Relax (u,v,a)\n" +
                        "dist[v] := alt ;\n" +
                        "previous[v] := u ;\n" +
                        "decrease-key v in Q; // Reorder v in the Queue\n" +
                        "end if\n" +
                        "end for\n" +
                        "end while\n" +
                        "return dist;\n" +
                        "end function"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include<iostream>\n" +
                        "#include<cstdio>\n" +
                        "using namespace std;\n" +
                        "const int MAXNODES = 10,INF = 9999;\n" +
                        "void fnDijkstra(int [][MAXNODES], int [], int [], int[], int, int, int);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        "int n,cost[MAXNODES][MAXNODES],dist[MAXNODES],visited[MAXNODES],path[MAXNODES],i,j,source,d\n" +
                        "est;\n" +
                        "cout << \"\\nEnter the number of nodes\\n\";\n" +
                        "cin >> n;\n" +
                        "cout << \"Enter the Cost Matrix\\n\" << endl;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "for (j=0;j<n;j++)\n" +
                        "cin >> cost[i][j];\n" +
                        "for (source = 0; source < n; source++)\n" +
                        "{\n" +
                        "getchar();\n" +
                        "cout << \"\\n//For Source Vertex : \" << source << \" shortest path to other vertices//\"<< end\n" +
                        "l;\n" +
                        "for (dest=0; dest < n; dest++)\n" +
                        "{\n" +
                        "fnDijkstra(cost,dist,path,visited,source,dest,n);\n" +
                        "if (dist[dest] == INF)\n" +
                        "cout << dest << \" not reachable\" << endl;\n" +
                        "else\n" +
                        "{\n" +
                        "cout << endl;\n" +
                        "i = dest;\n" +
                        "do\n" +
                        "{\n" +
                        "cout << i << \"<--\";\n" +
                        "i = path[i];\n" +
                        "}while (i!= source);\n" +
                        "cout << i << \" = \" << dist[dest] << endl;\n" +
                        "}\n" +
                        "}\n" +
                        "cout << \"Press Enter to continue...\";\n" +
                        "}\n" +

                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnDijkstra(int c[MAXNODES][MAXNODES], int d[MAXNODES], int p[MAXNODES], int s[MAXNODES], i\n" +
                        "nt so, int de, int n)\n" +
                        "{\n" +
                        "int i,j,a,b,min;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "s[i] = 0;\n" +
                        "d[i] = c[so][i];\n" +
                        "p[i] = so;\n" +
                        "}\n" +
                        "s[so] = 1;\n" +
                        "for (i=1;i<n;i++)\n" +
                        "{\n" +
                        "min = INF;\n" +
                        "a = -1;\n" +
                        "for (j=0;j<n;j++)\n" +
                        "{\n" +
                        "if (s[j] == 0)\n" +
                        "{\n" +
                        "if (d[j] < min)\n" +
                        "{\n" +
                        "min = d[j];\n" +
                        "a = j;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "if (a == -1) return;\n" +
                        "s[a] = 1;\n" +
                        "if (a == de) return;\n" +
                        "for (b=0;b<n;b++)\n" +
                        "{\n" +
                        "if (s[b] == 0)\n" +
                        "{\n" +
                        "if (d[a] + c[a][b] <d[b])\n" +
                        "{\n" +
                        "d[b] = d[a] + c[a][b];\n" +
                        "p[b] = a;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of nodes 5 Enter the Cost Matrix\n" +
                        "0 3 9999 7 9999 3 0 4 2 9999 9999 4 0 5 6 7 2 5 0 4 9999 9999 6 4 0\n" +
                        "//For Source Vertex : 0 shortest path to other vertices//\n" +
                        "0<--0 = 0\n" +
                        "1<--0 = 3\n" +
                        "2<--1<--0 = 7\n" +
                        "3<--1<--0 = 5\n" +
                        "4<--3<--1<--0 = 9 Press Enter to continue...\n" +
                        "//For Source Vertex : 1 shortest path to other vertices//\n" +
                        "0<--1 = 3\n" +
                        "1<--1 = 0\n" +
                        "2<--1 = 4\n" +
                        "3<--1 = 2\n" +
                        "4<--3<--1 = 6 Press Enter to continue...\n" +
                        "//For Source Vertex : 2 shortest path to other vertices//\n" +
                        "0<--1<--2 = 7\n" +
                        "1<--2 = 4\n" +
                        "2<--2 = 0\n" +
                        "3<--2 = 5\n" +
                        "4<--2 = 6 Press Enter to continue...\n" +
                        "//For Source Vertex : 3 shortest path to other vertices//\n" +
                        "0<--1<--3 = 5\n" +
                        "1<--3 = 2\n" +
                        "2<--3 = 5\n" +
                        "3<--3 = 0\n" +
                        "4<--3 = 4 Press Enter to continue...\n" +
                        "//For Source Vertex : 4 shortest path to other vertices//\n" +
                        "0<--1<--3<--4 = 9\n" +
                        "1<--3<--4 = 6\n" +
                        "2<--4 = 6\n" +
                        "3<--4 = 4\n" +
                        "4<--4 = 0 Press Enter to continue..."));
        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );

        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Minimum cost spanning tree using Kruskal's algorithm",
                        "Find Minimum Cost Spanning Tree of a given undirected graph using Kruskal's algorithm.", "c++" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Minimum cost spanning tree using Kruskal's algorithm",
                "Find Minimum Cost Spanning Tree of a given undirected graph using Kruskal's algorithm.", "c++" ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Find Minimum Cost Spanning Tree of a given undirected\n" +
                        "graph using Kruskal's algorithm.",
                "Kruskal's algorithm is an algorithm in graph theory that finds a minimum spanning tree for a connectedweighted graph. This\n" +
                        "means it finds a subset of the edges that forms a tree that includes every vertex, where the total weight of all the edges in the tree\n" +
                        "is minimized. If the graph is not connected, then it finds a minimum spanning forest (a minimum spanning tree for each connected\n" +
                        "component). Kruskal'salgorithm is an example of a greedy algorithm"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "Let G = (V, E) be the given graph, with | V| = n\n" +
                        "{\n" +
                        "Start with a graph T = (V,$ \\phi$) consisting of only the\n" +
                        "vertices of G and no edges; /* This can be viewed as n connected components, each vertex b\n" +
                        "eing one connected component */\n" +
                        "Arrange E in the order of increasing costs;\n" +
                        "for (i = 1, i$ \\leq$n - 1, i + +)\n" +
                        "{\n" +
                        "Select the next smallest cost edge;\n" +
                        "if (the edge connects two different connected components)\n" +
                        "add the edge to T;\n" +
                        "}\n" +
                        "}"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "using namespace std;\n" +
                        "const int MAXNODES = 10;\n" +
                        "const int INF = 9999;\n" +
                        "// Structure to represent an edge\n" +
                        "struct edge\n" +
                        "{\n" +
                        "int u, v, cost;\n" +
                        "};\n" +
                        "int fnFindParent(int v, int parent[]);\n" +
                        "void fnUnion_ij(int i, int j, int parent[]);\n" +
                        "void fnInputGraph(int m, edge e[]);\n" +
                        "int fnGetMinEdge(edge e[], int n);\n" +
                        "void kruskal(int n, edge e[], int m);"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main( int argc, char **argv)\n" +
                        "{\n" +
                        "int n = 6, m = 10;\n" +
                        "edge e[2*MAXNODES] = {{0,1,6},{1,4,3},{4,5,6},{5,3,2},{3,0,5},{0,2,1},{1,2,5},{3,2,5},{4,2,\n" +
                        "6},{5,2,4}};\n" +
                        "cout << \"Enter the number of nodes : \";\n" +
                        "cin >> n;\n" +
                        "cout << \"Enter the number of edges : \";\n" +
                        "cin >> m;\n" +
                        "fnInputGraph(m, e);\n" +
                        "kruskal(n, e, m);\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int fnFindParent(int v, int parent[])\n" +
                        "{\n" +
                        "while (parent[v] != v)\n" +
                        "v = parent[v];\n" +
                        "return v;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnUnion_ij(int i, int j, int parent[])\n" +
                        "{\n" +
                        "if(i < j)\n" +
                        "parent[j] = i;\n" +
                        "else\n" +
                        "parent[i] = j;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnInputGraph(int m, edge e[])\n" +
                        "{\n" +
                        "int i, j, k, cost;\n" +
                        "for(k=0; k<m; k++)\n" +
                        "{\n" +
                        "cout << \"Enter edge and cost in the form u, v, w : \\n\";\n" +
                        "cin >> i >> j >> cost;\n" +
                        "e[k].u = i;\n" +
                        "e[k].v = j;\n" +
                        "e[k].cost = cost;\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int fnGetMinEdge(edge e[], int n)\n" +
                        "{\n" +
                        "int i, small, pos;\n" +
                        "small = INF;\n" +
                        "pos = -1;\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "if(e[i].cost < small)\n" +
                        "{\n" +
                        "small = e[i].cost;\n" +
                        "pos = i;\n" +
                        "}\n" +
                        "}\n" +
                        "return pos;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void kruskal(int n, edge e[], int m)\n" +
                        "{\n" +
                        "int i, j, count, k, sum, u, v, t[MAXNODES][2], pos;\n" +
                        "int parent[MAXNODES];\n" +
                        "count = 0;\n" +
                        "k = 0;\n" +
                        "sum = 0;\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "parent[i] = i;\n" +
                        "}\n" +
                        "while(count != n-1)\n" +
                        "{\n" +
                        "pos = fnGetMinEdge(e,m);\n" +
                        "if(pos == -1)\n" +
                        "{\n" +
                        "break;\n" +
                        "}\n" +
                        "u = e[pos].u;\n" +
                        "v = e[pos].v;\n" +
                        "i = fnFindParent(u,parent);\n" +
                        "j = fnFindParent(v,parent);\n" +
                        "if(i != j)\n" +
                        "{\n" +
                        "t[k][0] = u;\n" +
                        "t[k][1] = v;\n" +
                        "k++;\n" +
                        "count++;\n" +
                        "sum += e[pos].cost;\n" +
                        "fnUnion_ij(i, j, parent);\n" +
                        "}\n" +
                        "e[pos].cost = INF;\n" +
                        "}\n" +
                        "if(count == n-1)\n" +
                        "{\n" +
                        "cout << \"\\nSpanning tree exists\";\n" +
                        "cout << \"\\nThe Spanning tree is shown below\\n\";\n" +
                        "for(i=0; i<n-1; i++)\n" +
                        "cout << t[i][0] << \" \" << t[i][1] << endl;\n" +
                        "cout << \"\\nCost of the spanning tree : \" << sum;\n" +
                        "}\n" +
                        "else\n" +
                        "cout << \"\\nThe spanning tree does not exist\";\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of nodes : 6\n" +
                        "Enter the number of edges : 10\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "0 1 6\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "1 4 3\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "4 5 6\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "5 3 2\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "3 0 5\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "0 2 1\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "1 2 5\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "3 2 5\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "4 2 6\n" +
                        "Enter edge and cost in the form u, v, w :\n" +
                        "5 2 4\n" +
                        "Spanning tree exists\n" +
                        "The Spanning tree is shown below\n" +
                        "0 2\n" +
                        "5 3\n" +
                        "1 4\n" +
                        "5 2\n" +
                        "1 2\n" +
                        "Cost of the spanning tree : 15"));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "BFS Method",
                        "Print all the nodes reachable from a given starting node in a digraph using BFS method.", "c++" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "BFS Method",
                "Print all the nodes reachable from a given starting node in a digraph using BFS method.", "c++" ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Program to find all nodes reachable from a given node using BFS",
                "In graph theory, breadth-first search (BFS) is a strategy for searching in a graph when search is limited to essentially\n" +
                        "two operations:\n" +
                        "** Visit and inspect a node of a graph;\n" +
                        "** Gain access to visit the nodes that neighbor the currently visited node.\n" +
                        "The BFS begins at a root node and inspects all the neighboring nodes. Then for each of those neighbor nodes in turn,\n" +
                        "it inspects their neighbor ##nodes which were unvisited, and so on."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "Input: A graph G and a root v of G 1 procedure BFS(G,v) is 2 create a queue Q 3 create a set V 4 enqueue v onto Q 5\n" +
                        "add v to V 6 while Q is not empty loop 7 t â† Q.dequeue() 8 if t is what we are looking for then 9 return t 10 end if 11\n" +
                        "for all edges e in G.adjacentEdges(t) loop 12 u â† G.adjacentVertex(t,e) 13 if u is not in V then 14 add u to V 15\n" +
                        "enqueue u onto Q 16 end if 17 end loop 18 end loop 19 return none 20 end BFS"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "#include <cstdlib>\n" +
                        "using namespace std;\n" +
                        "const int MAX = 100;\n" +
                        "void fnBreadthFirstSearchReach(int vertex, int g[MAX][MAX], int v[MAX], int n);\n" +
                        "class Queue\n" +
                        "{\n" +
                        "private:\n" +
                        "int cQueue[MAX];\n" +
                        "int front, rear;\n" +
                        "public:\n" +
                        "Queue();\n" +
                        "~Queue();\n" +
                        "int enqueue(int data);\n" +
                        "int dequeue();\n" +
                        "int empty() { return front == -1 ? 1 : 0; };\n" +
                        "};"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main(void)\n" +
                        "{\n" +
                        "int i,j;\n" +
                        "int graph[MAX][MAX];\n" +
                        "int visited[MAX];\n" +
                        "int numVert;\n" +
                        "int startVert;\n" +
                        "cout << \"Enter the number of vertices : \";\n" +
                        "cin >> numVert;\n" +
                        "cout << \"Enter the adjacency matrix :\\n\";\n" +
                        "for (i=0; i < numVert; i++)\n" +
                        "visited[i] = 0;\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        "cin >> graph[i][j];\n" +
                        "cout << \"Enter the starting vertex : \";\n" +
                        "cin >> startVert;\n" +
                        "fnBreadthFirstSearchReach(startVert-1,graph,visited,numVert);\n" +
                        "cout << \"Vertices which can be reached from vertex \" << startVert << \" are :-\" << endl;\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "if (visited[i])\n" +
                        "cout << i+1 << \", \";\n" +
                        "cout << endl;\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "/*Constructor*/\n" +
                        "Queue::Queue()\n" +
                        "{\n" +
                        "front = rear = -1;\n" +
                        "}\n" +
                        "/*Destructor*/\n" +
                        "Queue::~Queue()\n" +
                        "{\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int Queue::enqueue(int data)\n" +
                        "{\n" +
                        "if (front == (rear+1)%MAX)\n" +
                        "return 0;\n" +
                        "if (rear == -1)\n" +
                        "front = rear = 0;\n" +
                        "else\n" +
                        "rear = (rear+1)%MAX;\n" +
                        "cQueue[rear] = data;\n" +
                        "return 1;\n" +
                        "}\n\n" +
                        "int Queue::dequeue()\n" +
                        "{\n" +
                        "int data;\n" +
                        "if (front == -1)\n" +
                        "return -1;\n" +
                        "data = cQueue[front];\n" +
                        "if (front == rear)\n" +
                        "front = rear = -1;\n" +
                        "else\n" +
                        "front = (front+1)%MAX;\n" +
                        "return data;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnBreadthFirstSearchReach(int vertex, int g[MAX][MAX], int v[MAX], int n)\n" +
                        "{\n" +
                        "Queue verticesVisited;\n" +
                        "int frontVertex;\n" +
                        "int i;\n" +
                        "v[vertex] = 1;\n" +
                        "verticesVisited.enqueue(vertex);\n" +
                        "while (!verticesVisited.empty())\n" +
                        "{\n" +
                        "frontVertex = verticesVisited.dequeue();\n" +
                        "for (i=0; i<n; i++)\n" +
                        "{\n" +
                        "if (g[frontVertex][i] && !v[i])\n" +
                        "{\n" +
                        "v[i] = 1;\n" +
                        "verticesVisited.enqueue(i);\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int fnGetMinEdge(edge e[], int n)\n" +
                        "{\n" +
                        "int i, small, pos;\n" +
                        "small = INF;\n" +
                        "pos = -1;\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "if(e[i].cost < small)\n" +
                        "{\n" +
                        "small = e[i].cost;\n" +
                        "pos = i;\n" +
                        "}\n" +
                        "}\n" +
                        "return pos;\n" +
                        "}"));


        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of vertices : 4\n" +
                        "Enter the adjacency matrix :\n" +
                        "0 1 1 0\n" +
                        "1 0 0 1\n" +
                        "1 0 0 1\n" +
                        "0 1 1 0\n" +
                        "Enter the starting vertex : 1\n" +
                        "Vertices which can be reached from vertex 1 are :-\n" +
                        "1, 2, 3, 4,\n" +
                        "SAMPLE 2\n" +
                        "Enter the number of vertices : 4\n" +
                        "Enter the adjacency matrix :\n" +
                        "0 1 0 0\n" +
                        "1 0 0 0\n" +
                        "0 0 0 1\n" +
                        "0 0 1 0\n" +
                        "Enter the starting vertex : 1\n" +
                        "Vertices which can be reached from vertex 1 are :-\n" +
                        "1, 2,\n" +
                        "SAMPLE 3\n" +
                        "Enter the number of vertices : 4\n" +
                        "Enter the adjacency matrix :\n" +
                        "0 1 0 0\n" +
                        "0 0 1 0\n" +
                        "0 0 0 1\n" +
                        "0 0 0 0\n" +
                        "Enter the starting vertex : 2\n" +
                        "Vertices which can be reached from vertex 2 are :-\n" +
                        "2, 3, 4,\n" +
                        "SAMPLE 4\n" +
                        "Enter the number of vertices : 4\n" +
                        "Enter the adjacency matrix :\n" +
                        "0 1 0 0\n" +
                        "0 0 1 0\n" +
                        "0 0 0 1\n" +
                        "1 0 0 0\n" +
                        "Enter the starting vertex : 2\n" +
                        "Vertices which can be reached from vertex 2 are :-\n" +
                        "1, 2, 3, 4"));



        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "DFS Method",
                        "Check whether a\n" +
                                "given graph is connected or not using DFS method.", "c++" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "DFS Method",
                "Check whether a\n" +
                        "given graph is connected or not using DFS method.", "c++" ));


        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Check whether a given graph is connected or\n" +
                        "not using DFS method.",
                "Depth-first search is a graph traversal algorithm, which has a very wide application area. This algorithm may be used for finding\n" +
                        "out number of components of a graph, topological order of its nodes or detection of cycles.Itis an algorithm for traversing or\n" +
                        "searching a tree, tree structure, or graph. One starts at the root (selecting some node as the root in the graph case) and explores\n" +
                        "as far as possible along each branch before backtracking."));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "DFS(G,v) ( v is the vertex where the search starts )\n" +
                        "Stack S := {}; ( start with an empty stack )\n" +
                        "for each vertex u, set visited[u] := false;\n" +
                        "push S, v;\n" +
                        "while (S is not empty) do\n" +
                        "u := pop S;\n" +
                        "if (not visited[u]) then\n" +
                        "visited[u] := true;\n" +
                        "for each unvisited neighbour w of u\n" +
                        "push S, w;\n" +
                        "end if\n" +
                        "end while\n" +
                        "END DFS()"));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "#include <cstdlib>\n" +
                        "using namespace std;\n" +
                        "const int MAX = 100;\n" +
                        "void DepthFirstSearch(int currentVertex, int v[MAX], int g[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "v[currentVertex] = 1;\n" +
                        "for (i=0; i<n; i++)\n" +
                        "if (g[currentVertex][i] && !v[i])\n" +
                        "DepthFirstSearch(i,v,g,n);\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main()\n" +
                        "{\n" +
                        "int i,j,k;\n" +
                        "int visited[MAX];\n" +
                        "int graph[MAX][MAX];\n" +
                        "int numVert;\n" +
                        "cout << \"Enter the number of vertices : \";\n" +
                        "cin >> numVert;\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "visited[i] = 0;\n" +
                        "cout << \"Enter the adjacency matrix :\\n\";\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        "cin >> graph[i][j];\n" +
                        "for (i=0; i<numVert; i++)\n" +
                        "{\n" +
                        "for (k=0; k<numVert; k++)\n" +
                        "visited[k] = 0;\n" +
                        "DepthFirstSearch(i,visited,graph,numVert);\n" +
                        "for (k=0; k<numVert; k++)\n" +
                        "{\n" +
                        "if (!visited[k])\n" +
                        "{\n" +
                        "cout << \"\\nGraph is not connected since there is no path between \" << i << \" and \"\n" +
                        "<< k << endl;\n" +
                        "exit(0);\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "cout << \"\\nGraph is connected.\"<< endl;\n" +
                        "return 0;\n" +
                        "}"));




        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of vertices : 4\n" +
                        "Enter the adjacency matrix :\n" +
                        "0 1 0 0\n" +
                        "0 0 1 0\n" +
                        "0 0 0 1\n" +
                        "1 0 0 0\n" +
                        "Graph is connected."));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Subset sum problem",
                        "Find a subset of a given set S = {sl, s2,.....,sn} of n positive integers whose sum is equal to a given positive integer\n" +
                                "d. For example, if S= {1, 2, 5, 6, 8} and d = 9 there are two solutions{1,2,6}and{1,8}. A suitable message is to be\n" +
                                "displayed if the given problem instance doesn't have a solution.", "c++" ));

        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Subset sum problem",
                "Find a subset of a given set S = {sl, s2,.....,sn} of n positive integers whose sum is equal to a given positive integer\n" +
                        "d. For example, if S= {1, 2, 5, 6, 8} and d = 9 there are two solutions{1,2,6}and{1,8}. A suitable message is to be\n" +
                        "displayed if the given problem instance doesn't have a solution.", "c++" ));


        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Program to solve Subset sum problem.",
                "In computer science, the subset sum problem is an important problem in complexity theory and cryptography. The problem is this:\n" +
                        "Given a set of integers, is there a non-empty subset whose sum is zero? For example, given the set {-7, -3, -2, 5, 8}, the answer is\n" +
                        "yes because the subset {-3, -2, 5} sums to zero. The problem is NP-complete. Input: The number of elements. The Weights of\n" +
                        "each element. Total Required Weight. Output:Subsets in which the sum of elements is equal to the given required weight(input)."));



        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "using namespace std;\n" +
                        "// Constant definitions\n" +
                        "const int MAX = 100;\n" +
                        "// class definitions\n" +
                        "class SubSet\n" +
                        "{\n" +
                        "int stk[MAX], set[MAX];\n" +
                        "int size, top, count;\n" +
                        "public:\n" +
                        "SubSet()\n" +
                        "{\n" +
                        "top = -1;\n" +
                        "count = 0;\n" +
                        "}\n" +
                        "void getInfo(void);\n" +
                        "void push(int data);\n" +
                        "void pop(void);\n" +
                        "void display(void);\n" +
                        "int fnFindSubset(int pos, int sum);\n" +
                        "};"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void SubSet :: getInfo(void)\n" +
                        "{\n" +
                        "int i;\n" +
                        "cout << \"Enter the maximum number of elements : \";\n" +
                        "cin >> size;\n" +
                        "cout << \"Enter the weights of the elements : \\n\";\n" +
                        "for (i=1; i<=size; i++)\n" +
                        "cin >> set[i];\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void SubSet :: push(int data)\n" +
                        "{\n" +
                        "stk[++top] = data;\n" +
                        "}\n" +
                        "void SubSet :: pop(void)\n" +
                        "{\n" +
                        "top--;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void SubSet :: display()\n" +
                        "{\n" +
                        "int i;\n" +
                        "cout << \"\\nSOLUTION #\"<< ++count <<\" IS\\n{ \";\n" +
                        "for (i=0; i<=top; i++)\n" +
                        "cout << stk[i] << \" \";\n" +
                        "cout << \"}\" << endl;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int SubSet :: fnFindSubset(int pos, int sum)\n" +
                        "{\n" +
                        "int i;\n" +
                        "static int foundSoln = 0;\n" +
                        "if (sum>0)\n" +
                        "{\n" +
                        "for (i=pos; i<=size; i++)\n" +
                        "{\n" +
                        "push(set[i]);\n" +
                        "fnFindSubset(i+1, sum - set[i]);\n" +
                        "pop();\n" +
                        "}\n" +
                        "}\n" +
                        "if (sum == 0)\n" +
                        "{\n" +
                        "display();\n" +
                        "foundSoln = 1;\n" +
                        "}\n" +
                        "return foundSoln;\n" +
                        "}"));


        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main(void)\n" +
                        "{\n" +
                        "int i,sum;\n" +
                        "SubSet set1;\n" +
                        "set1.getInfo();\n" +
                        "cout << \"Enter the total required weight : \";\n" +
                        "cin >> sum;\n" +
                        "cout << endl;\n" +
                        "if (!set1.fnFindSubset(1, sum))\n" +
                        "cout << \"\\n\\nThe given problem instance doesnt have any solution.\" << endl;\n" +
                        "else\n" +
                        "cout << \"\\n\\nThe above-mentioned sets are the required solution to the given instance.\"\n" +
                        "<< endl;\n" +
                        "return 0;\n" +
                        "}"));



        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "SAMPLE 1\n" +
                        "Enter the maximum number of elements : 5\n" +
                        "Enter the weights of the elements :\n" +
                        "1 2 3 4 5\n" +
                        "Enter the total required weight : 5\n" +
                        "SOLUTION #1 IS\n" +
                        "{ 1 4 }\n" +
                        "SOLUTION #2 IS\n" +
                        "{ 2 3 }\n" +
                        "SOLUTION #3 IS\n" +
                        "{ 5 }\n" +
                        "The above-mentioned sets are the required solution to the given instance.\n" +
                        "SAMPLE 2\n" +
                        "Enter the maximum number of elements : 4\n" +
                        "Enter the weights of the elements :\n" +
                        "1 2 3 4\n" +
                        "Enter the total required weight : 11\n" +
                        "The given problem instance doesnt have any solution."));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Traveling sales person problem",
                        "Implement any scheme to find the optimal solution for the Traveling Salesperson problem and then solve the same\n" +
                                "problem instance using any approximation algorithm and determine the error in the approximation.", "c++" ));

        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Traveling sales person problem",
                "Implement any scheme to find the optimal solution for the Traveling Salesperson problem and then solve the same\n" +
                        "problem instance using any approximation algorithm and determine the error in the approximation.", "c++" ));


        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Implement any scheme to find the optimal solution for the TRAVELLING SALESMAN PROBLEM and then solve the\n" +
                        "same problem instance using any approximation algorithm and determine the error in the approximation.",
                "The travelling salesman problem (TSP) asks the following question: Given a list of cities and the distances between each pair of\n" +
                        "cities, what is the shortest possible route that visits each city exactly once and returns to the origin city? It is an NP-hard problem\n" +
                        "in combinatorial optimization, important in operations research and theoretical computer science.\n" +
                        "The TSP has several applications even in its purest formulation, such as planning, logistics, and the manufacture of microchips.\n" +
                        "Slightly modified, it appears as a sub-problem in many areas, such as DNA sequencing. In these applications, the concept city\n" +
                        "represents, for example, customers, soldering points, or DNA fragments, and the concept distance represents travelling times or\n" +
                        "cost, or a similarity measure between DNA fragments. In many applications, additional constraints such as limited resources or\n" +
                        "time windows make the problem considerably harder. TSP is a special case of the travelling purchaser problem.\n" +
                        "In the theory of computational complexity, the decision version of the TSP (where, given a length L, the task is to decide whether\n" +
                        "the graph has any tour shorter than L) belongs to the class of NP-complete problems. Thus, it is possible that the worst-case\n" +
                        "running time for any algorithm for the TSP increases superpolynomially (or perhaps exponentially) with the number of cities."));

        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "#include <iomanip>\n" +
                        "using namespace std;\n" +
                        "const int MAX = 10;\n" +
                        "int path[MAX];\n" +
                        "static int k=0;\n" +
                        "int count = 0;\n" +
                        "int perm[120][7];\n" +
                        "int tourcost[120];\n" +
                        "void swap (int *x, int *y)\n" +
                        "{\n" +
                        "int temp;\n" +
                        "temp = *x;\n" +
                        "*x = *y;\n" +
                        "*y = temp;\n" +
                        "}\n" +
                        "void DepthFirstSearch(int currentVertex, int v[MAX], int g[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "v[currentVertex]=1;\n" +
                        "path[k++]=currentVertex;\n" +
                        "for (i=0; i<n; i++)\n" +
                        "if (g[currentVertex][i] && !v[i])\n" +
                        "DepthFirstSearch(i,v,g,n);\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void permute(int *a, int i, int n)\n" +
                        "{\n" +
                        "int j,k;\n" +
                        "if (i == n)\n" +
                        "{\n" +
                        "for(k=0;k<=n;k++)\n" +
                        "{\n" +
                        "//start from 2nd column\n" +
                        "perm[count][k+1] = a[k];\n" +
                        "}\n" +
                        "count++;\n" +
                        "}\n" +
                        "else\n" +
                        "{\n" +
                        "for (j = i; j <= n; j++)\n" +
                        "{\n" +
                        "swap((a+i), (a+j));\n" +
                        "permute(a, i+1, n);\n" +
                        "swap((a+i), (a+j)); //backtrack\n" +
                        "}\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int AppTSP(int n, int cost[MAX][MAX])\n" +
                        "{\n" +
                        "int i, j, u, v, min,Excost=0;\n" +
                        "int sum, k, t[MAX][2], p[MAX], d[MAX], s[MAX],tree[MAX][MAX];\n" +
                        "int source, count;\n" +
                        "int visited[MAX];\n" +
                        "for (i=0; i<n; i++)\n" +
                        "visited[i] = 0;\n" +
                        "min = 9999;\n" +
                        "source = 0;\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "for(j=0; j<n; j++)\n" +
                        "{\n" +
                        "if(cost[i][j] != 0 && cost[i][j] <= min)\n" +
                        "{\n" +
                        "min = cost[i][j];\n" +
                        "source = i;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "d[i] = cost[source][i];\n" +
                        "s[i] = 0;\n" +
                        "p[i] = source;\n" +
                        "}\n" +
                        "s[source] = 1;\n" +
                        "sum = 0;\n" +
                        "k = 0;\n" +
                        "count = 0;\n" +
                        "while (count != n-1)\n" +
                        "{\n" +
                        "min = 9999;\n" +
                        "u = -1;\n" +
                        "for(j=0; j<n; j++)\n" +
                        "{\n" +
                        "if(s[j] == 0)\n" +
                        "{\n" +
                        "if(d[j] <= min)\n" +
                        "{\n" +
                        "min = d[j];\n" +
                        "u = j;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "t[k][0] = u;\n" +
                        "t[k][1] = p[u];\n" +
                        "k++;\n" +
                        "count++;\n" +
                        "sum += cost[u][p[u]];\n" +
                        "s[u] = 1;\n" +
                        "for(v=0; v<n; v++)\n" +
                        "{\n" +
                        "if(s[v]==0 && cost[u][v]<d[v])\n" +
                        "{\n" +
                        "d[v] = cost[u][v];\n" +
                        "p[v] = u;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "for(j=0; j<n; j++)\n" +
                        "{\n" +
                        "tree[i][j]=0;\n" +
                        "}\n" +
                        "}\n" +
                        "if(sum >= 9999)\n" +
                        "cout << \"\\nSpanning tree does not exist\";\n" +
                        "else\n" +
                        "{\n" +
                        "for(i=0; i<k; i++)\n" +
                        "{\n" +
                        "tree[t[i][0]][t[i][1]] = tree[t[i][1]][t[i][0]] =1;\n" +
                        "}\n" +
                        "}\n" +
                        "DepthFirstSearch(0,visited,tree,n);\n" +
                        "cout << \"\\n The Approximate Minimum Cost tour is\" << endl;\n" +
                        "for(i=0;i<=k;i++)\n" +
                        "{\n" +
                        "cout << path[i] << \"->\";\n" +
                        "Excost += cost[path[i]][path[i+1]];\n" +
                        "}\n" +
                        "cout << path[0];\n" +
                        "Excost += cost[path[i]][path[0]];\n" +
                        "cout << \"\\n The Approximate Minimum Cost of the tour is\" << Excost << endl;\n" +
                        "return Excost;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int main(void)\n" +
                        "{\n" +
                        "int a[MAX][MAX] = { { 0, 4, 8, 9, 12},\n" +
                        "{ 4, 0, 6, 8, 9},\n" +
                        "{ 8, 6, 0, 10, 11},\n" +
                        "{ 9, 8, 10, 0, 7},\n" +
                        "{12, 9, 11, 7, 0}};\n" +
                        "int NumOfCity = 5;\n" +
                        "int interCities = 4,i,j;\n" +
                        "int mct=999,mctIndex,Appmct;\n" +
                        "//Source and destination is 0 remaining are intermediary cities\n" +
                        "int city[4] = {1,2,3,4};\n" +
                        "permute(city, 0, interCities-1);\n" +
                        "for(i=0;i<24;i++)\n" +
                        "{\n" +
                        "for(j=0;j<5;j++)\n" +
                        "{\n" +
                        "tourcost[i]+= a[perm[i][j]][perm[i][j+1]];\n" +
                        "}\n" +
                        "if( mct > tourcost[i])\n" +
                        "{\n" +
                        "mct = tourcost[i];\n" +
                        "mctIndex = i;\n" +
                        "}\n" +
                        "}\n" +
                        "cout << \"\\n The Exact Minimum Cost tour is\" << endl;\n" +
                        "for(i=0;i<NumOfCity;i++)\n" +
                        "cout << perm[mctIndex][i] << \"->\";\n" +
                        "cout << perm[mctIndex][i];\n" +
                        "cout << \"\\n The Exact Minimum Cost of the tour is\" << mct << endl;\n" +
                        "Appmct = AppTSP(NumOfCity,a);\n" +
                        "cout << \"\\n The error in Approximation is \" << Appmct - mct << \" units\" << endl;\n" +
                        "cout << \"\\n The Accuracy ratio is \" << (float)Appmct / mct << endl;\n" +
                        "cout << \"\\n The Approximate tour is \"<<(((float)Appmct / mct) - 1)*100<< \" percent longer t\n" +
                        "han the optimal tour\" << endl;\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "The Exact Minimum Cost tour is\n" +
                        "0->1->2->4->3->0\n" +
                        "The Exact Minimum Cost of the tour is37\n" +
                        "The Approximate Minimum Cost tour is\n" +
                        "0->1->2->3->4->0\n" +
                        "The Approximate Minimum Cost of the tour is39\n" +
                        "The error in Approximation is 2 units\n" +
                        "The Accuracy ratio is 1.05405\n" +
                        "The Approximate tour is 5.40541 percent longer than the optimal tour"));

        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Prim's algorithm.",
                        "Find Minimum Cost Spanning Tree of a given undirected graph using Prim's algorithm.", "c++" ));

        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Prim's algorithm.",
                "Find Minimum Cost Spanning Tree of a given undirected graph using Prim's algorithm.", "c++" ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Find Minimum Cost Spanning Tree of a given undirected\n" +
                        "graph using Prim's algorithm.",
                "The program uses prim's algorithm which is based on minimum spanning tree for a connected undirected graph.A predefinined\n" +
                        "cost adjecency matrix is the input.To find the minimum spanning tree, we choose the source node at random and in every step we\n" +
                        "find the node which is closest as well as having the least cost from the previously selected node.And also the cost of selected\n" +
                        "edge is being added to variable sum.Based on the value of sum, the presence of the minimum spanning tree is found."));

        algorithmContentArrayList.add( new AlgorithmContent(AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "Input: A non-empty connected weighted graph with vertices V and edges E (the weights can be\n" +
                        "negative).\n" +
                        "Initialize: Vnew = {x}, where x is an arbitrary node (starting point) from V, Enew = {}\n" +
                        "Repeat until Vnew = V:\n" +
                        "Choose an edge {u, v} with minimal weight such that u is in Vnew and v is not (if there\n" +
                        "are multiple edges with the same weight, any of them may be picked)\n" +
                        "Add v to Vnew, and {u, v} to Enew\n" +
                        "Output: Vnew and Enew describe a minimal spanning tree"));
        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include<iostream>\n" +
                        "using namespace std;\n" +
                        "const int MAXNODES = 10;\n" +
                        "void fnPrims(int n, int cost[MAXNODES][MAXNODES]);\n" +
                        "void fnGetMatrix(int n,int a[MAXNODES][MAXNODES]);\n" +
                        "int main( int argc, char **argv)\n" +
                        "{\n" +
                        "int a[MAXNODES][MAXNODES] = {{0, 3, 9999, 7, 9999},\n" +
                        "{3, 0, 4, 2, 9999},\n" +
                        "{9999, 4, 0, 5, 6},\n" +
                        "{7, 2, 5, 0, 4},\n" +
                        "{9999, 9999, 6, 4, 0}};\n" +
                        "int n = 5;\n" +
                        "cout << \"Enter the number of vertices : \";\n" +
                        "cin >> n;\n" +
                        "fnGetMatrix(n,a);\n" +
                        "fnPrims(n,a);\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnPrims(int n, int cost[MAXNODES][MAXNODES])\n" +
                        "{\n" +
                        "int i, j, u, v, min;\n" +
                        "int sum, k, t[MAXNODES][2], p[MAXNODES], d[MAXNODES], s[MAXNODES];\n" +
                        "int source, count;\n" +
                        "min = 9999;\n" +
                        "source = 0;\n" +
                        "for(i=0; i<n; i++) //finding the node with minimum cost\n" +
                        "{\n" +
                        "for(j=0; j<n; j++)\n" +
                        "{\n" +
                        "if(cost[i][j] != 0 && cost[i][j] <= min)\n" +
                        "{\n" +
                        "min = cost[i][j];\n" +
                        "source = i;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "for(i=0; i<n; i++)\n" +
                        "{\n" +
                        "d[i] = cost[source][i]; //initializing the array with th cost of all the nodes from sou\n" +
                        "rce.\n" +
                        "s[i] = 0;\n" +
                        "p[i] = source;\n" +
                        "}\n" +
                        "s[source] = 1;\n" +
                        "sum = 0;\n" +
                        "k = 0;\n" +
                        "count = 0;\n" +
                        "while (count != n-1)\n" +
                        "{\n" +
                        "min = 9999;\n" +
                        "u = -1;\n" +
                        "for(j=0; j<n; j++)\n" +
                        "{\n" +
                        "if(s[j] == 0)\n" +
                        "{\n" +
                        "if(d[j] <= min)\n" +
                        "{\n" +
                        "min = d[j];\n" +
                        "u = j;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "t[k][0] = u;\n" +
                        "t[k][1] = p[u];\n" +
                        "k++;\n" +
                        "count++;\n" +
                        "sum += cost[u][p[u]];\n" +
                        "s[u] = 1;\n" +
                        "for(v=0; v<n; v++)\n" +
                        "{\n" +
                        "if(s[v]==0 && cost[u][v]<d[v])\n" +
                        "{\n" +
                        "d[v] = cost[u][v];\n" +
                        "p[v] = u;\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "if(sum >= 9999)\n" +
                        "cout << \"\\nSpanning tree does not exist\\n\";\n" +
                        "else\n" +
                        "{\n" +
                        "cout << \"\\nThe spanning tree exists and minimum cost spanning tree is \\n\";\n" +
                        "for(i=0; i<k; i++)\n" +
                        "cout << t[i][1] << \" \" << t[i][0] << endl;\n" +
                        "cout << \"\\nThe cost of the minimum cost spanning tree is \" << sum << endl;\n" +
                        "}\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnGetMatrix(int n,int a[MAXNODES][MAXNODES])\n" +
                        "{\n" +
                        "int i, j;\n" +
                        "cout << \"Enter the Cost Adjacency Matrix\" << endl;\n" +
                        "for(i=0; i<n; i++)\n" +
                        "for(j=0; j<n; j++)\n" +
                        "cin >> a[i][j];\n" +
                        "}"));



        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of vertices : 5 Enter the Cost Adjacency Matrix\n" +
                        "0 3 9999 7 9999\n" +
                        "3 0 4 2 9999\n" +
                        "9999 4 0 5 6\n" +
                        "7 2 5 0 4\n" +
                        "9999 9999 6 4 0\n" +
                        "The spanning tree exists and minimum cost spanning tree is\n" +
                        "3 1\n" +
                        "1 0\n" +
                        "3 4\n" +
                        "1 2\n" +
                        "The cost of the minimum cost spanning tree is 13\n" +
                        "Output Sample 2:\n" +
                        "Enter the number of vertices : 5\n" +
                        "Enter the Cost Adjacency Matrix\n" +
                        "0 3 9999 7 9999\n" +
                        "3 0 9999 2 9999\n" +
                        "9999 9999 0 9999 9999\n" +
                        "7 2 9999 0 4\n" +
                        "9999 9999 9999 4 0\n" +
                        "Spanning tree does not exist"));


        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "Floyd's algorithm",
                        "Implement All-Pairs Shortest Paths Problem using Floyd's algorithm. Parallelize this algorithm, implement it using\n" +
                                "OpenMP and determine the speed-up achieved.", "c" ));

        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "Floyd's algorithm",
                "Implement All-Pairs Shortest Paths Problem using Floyd's algorithm. Parallelize this algorithm, implement it using\n" +
                        "OpenMP and determine the speed-up achieved.", "c" ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Implement All-Pairs Shortest Paths Problem using Floyd's algorithm. Parallelize this algorithm, implement it using\n" +
                        "OpenMP and determine the speed-up achieved.",
                "The Floyd's algorithm is a graph analysis algorithm for finding shortest paths in a weighted graph with positive or negative edge\n" +
                        "weights (but with no negative cycles, see below) and also for finding transitive closure of a relation R. A single execution of the\n" +
                        "algorithm will find the lengths (summed weights) of the shortest paths between all pairs of vertices, though it does not return\n" +
                        "details of the paths themselves."));

        algorithmContentArrayList.add( new AlgorithmContent(AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "let dist be a |V| x |V| array of minimum distances initialized to infinity\n" +
                        "for each vertex v\n" +
                        "dist[v][v] <- 0\n" +
                        "for each edge (u,v)\n" +
                        "dist[u][v] <- w(u,v) // the weight of the edge (u,v)\n" +
                        "for k from 1 to |V|\n" +
                        "for i from 1 to |V|\n" +
                        "for j from 1 to |V|\n" +
                        "if dist[i][j] > dist[i][k] + dist[k][j]\n" +
                        "dist[i][j] <- dist[i][k] + dist[k][j]\n" +
                        "end if"));
        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include<stdio.h>\n" +
                        "#include<stdlib.h>\n" +
                        "#include<sys/time.h>\n" +
                        "#include<omp.h>\n" +
                        "int min(int,int);\n" +
                        "int main()\n" +
                        "{\n" +
                        "int n,k,i,j,c[10][10];\n" +
                        "int tid;\n" +
                        "omp_set_num_threads(0);\n" +
                        "{\n" +
                        "tid=omp_get_thread_num();\n" +
                        "printf(\"Enter the number of nodes:\");\n" +
                        "scanf(\"%d\",&n);\n" +
                        "printf(\"Enter the cost matrix:\\n\");\n" +
                        "for(i=0;i<n;i++)\n" +
                        "for(j=0;j<n;j++)\n" +
                        "scanf(\"%d\",&c[i][j]);\n" +
                        "for(k=0;k<n;k++)\n" +
                        "{\n" +
                        "for(i=0;i<n;i++)\n" +
                        "for(j=0;j<n;j++)\n" +
                        "c[i][j]=min(c[i][j],c[i][k]+c[k][j]);\n" +
                        "}\n" +
                        "printf(\"\\n All pairs shortest path\\n\");\n" +
                        "for(i=0;i<n;i++)\n" +
                        "{\n" +
                        "for(j=0;j<n;j++)\n" +
                        "printf(\"%d\\t\",c[i][j]);\n" +
                        "printf(\"\\n\");\n" +
                        "}\n" +
                        "}\n" +
                        "return 0;\n" +
                        "}\n" +
                        "int min(int a,int b)\n" +
                        "{\n" +
                        "return(a<b?a:b);\n" +
                        "}"));


        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "Enter the number of nodes:3\n" +
                        "Enter the cost matrix:\n" +
                        "5 6 7\n" +
                        "8 9 1\n" +
                        "2 3 4\n" +
                        "All pairs shortest path\n" +
                        "5 6 7\n" +
                        "3 4 1\n" +
                        "2 3 4"));
        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
        firebaseDatabaseHandler.writeAlgorithmIndex(
                new AlgorithmsIndex(index++,
                        "N Queen's problem",
                        "Implement N Queen's problem using Back Tracking.", "c++" ));
        algorithm = new Algorithm(); algorithmContentArrayList = new ArrayList<>();
        algorithm.setAlgorithmsIndex(new AlgorithmsIndex(index - 1,
                "N Queen's problem",
                "Implement N Queen's problem using Back Tracking.", "c++" ));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_AIM_DESCRIPTION,
                "Aim and Description",
                "Program to solve N Queens problem using\n" +
                        "backtracking.",
                "Given a CHESS BOARD of size N*N,we are supposed to place N QUEEN's such that no QUEEN is in an attacking position.\n" +
                        "BACKTRACKING:Backtracking is a general algorithm for finding all (or some) solutions to some computational problem, that\n" +
                        "incrementally builds candidates to the solutions, and abandons each partial candidate c (\"backtracks\") as soon as it determines\n" +
                        "that c cannot possibly be completed to a valid solution."));

        algorithmContentArrayList.add( new AlgorithmContent(AlgorithmConstants.CONTENT_ALGORITHM,
                "Algorithm",
                "1) Start in the leftmost column\n" +
                        "2) If all queens are placed\n" +
                        "return true\n" +
                        "3) Try all rows in the current column. Do following for every tried row.\n" +
                        "a) If the queen can be placed safely in this row then mark this [row,\n" +
                        "column] as part of the solution and recursively check if placing\n" +
                        "queen here leads to a solution.\n" +
                        "b) If placing queen in [row, column] leads to a solution then return\n" +
                        "true.\n" +
                        "c) If placing queen doesn't lead to a solution then umark this [row,\n" +
                        "column] (Backtrack) and go to step (a) to try other rows.\n" +
                        "3) If all rows have been tried and nothing worked, return false to trigger\n" +
                        "backtracking."));
        codeIndex = 1;
        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - " + codeIndex++,
                "#include <iostream>\n" +
                        "#include <cstdlib>\n" +
                        "using namespace std;\n" +
                        "const int MAX = 10;\n" +
                        "int SolnCount =0;\n" +
                        "void fnChessBoardShow(int n, int row[MAX]);\n" +
                        "bool fnCheckPlace(int KthQueen, int ColNum, int row[MAX]);\n" +
                        "int NQueen(int k,int n, int row[MAX]);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        "int n;\n" +
                        "int row[MAX];\n" +
                        "cout << \"Enter the number of queens : \";\n" +
                        "cin >> n;\n" +
                        "if (!NQueen(0,n,row))\n" +
                        "cout << \"No solution exists for the given problem instance.\" << endl;\n" +
                        "else\n" +
                        "cout << \"Number of solution for the given problem instance is : \" << SolnCount << endl;\n" +
                        "return 0;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "int NQueen(int k,int n, int row[MAX])\n" +
                        "{\n" +
                        "static int flag;\n" +
                        "for(int i=0;i<n;i++)\n" +
                        "{\n" +
                        "if(fnCheckPlace(k,i,row) == true)\n" +
                        "{\n" +
                        "row[k] = i;\n" +
                        "if(k == n-1)\n" +
                        "{\n" +
                        "fnChessBoardShow(n,row);\n" +
                        "SolnCount++;\n" +
                        "flag = 1;\n" +
                        "return flag;\n" +
                        "}\n" +
                        "NQueen(k+1, n, row);\n" +
                        "}\n" +
                        "}\n" +
                        "return flag;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "bool fnCheckPlace(int KthQueen, int ColNum, int row[MAX])\n" +
                        "{\n" +
                        "for(int i=0; i<KthQueen; i++)\n" +
                        "{\n" +
                        "if(row[i] == ColNum || abs(row[i]-ColNum) == abs(i-KthQueen))\n" +
                        "return false;\n" +
                        "}\n" +
                        "return true;\n" +
                        "}"));

        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_CODE,
                "Code Part - "+ codeIndex++,
                "void fnChessBoardShow(int n, int row[MAX])\n" +
                        "{\n" +
                        "cout << \"\\nSolution #\" << SolnCount+1 << endl << endl;\n" +
                        "for (int i=0; i<n; i++)\n" +
                        "{\n" +
                        "for (int j=0; j<n; j++)\n" +
                        "{\n" +
                        "if (j == row[i])\n" +
                        "cout << \"Q \";\n" +
                        "else\n" +
                        "cout << \"# \";\n" +
                        "}\n" +
                        "cout << endl;\n" +
                        "}\n" +
                        "cout << endl;\n" +
                        "}"));



        algorithmContentArrayList.add(new AlgorithmContent(
                AlgorithmConstants.CONTENT_OUTPUT,
                "Output",
                "SAMPLE 1\n" +
                        "Enter the number of queens : 4\n" +
                        "Solution #1\n" +
                        "# Q # #\n" +
                        "# # # Q\n" +
                        "Q # # #\n" +
                        "# # Q #\n" +
                        "Solution #2\n" +
                        "# # Q #\n" +
                        "Q # # #\n" +
                        "# # # Q\n" +
                        "# Q # #\n" +
                        "Number of solution for the given problem instance is : 2\n" +
                        "SAMPLE 2\n" +
                        "Enter the number of queens : 3 No solution exists for the given problem instance."));
        algorithm.setAlgorithmContentArrayList(algorithmContentArrayList);
        firebaseDatabaseHandler.writeAlgorithm( algorithm );
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
        firebaseDatabaseHandler.writeProgramTable( new ProgramTable(programIndex, lineNo++,"cout<<I<<endl;",  "Print l",  "c++"));
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
        moduleId = 7;
        String generatedId = programLanguage + "_" + moduleId++;
        int syntaxIndex = 1;

        SyntaxModule syntaxModule = new SyntaxModule();

        syntaxModule.setSyntaxModuleId("s_" + syntaxIndex++);
        syntaxModule.setModuleId(generatedId);
        syntaxModule.setSyntaxName("SQL - LIKE Clause");
        syntaxModule.setSyntaxDescription(
                "The SQL LIKE clause is used to compare a value to similar values using wildcard operators. There are two wildcards used in conjunction with the LIKE operator:\n" +
                        "\n" +
                        "The percent sign (%)\n" +
                        "\n" +
                        "The underscore (_)\n" +
                        "\n" +
                        "The percent sign represents zero, one, or multiple characters. The underscore represents a single number or character. The symbols can be used in combinations.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of % and _ is as follows:\n" +
                        "\n" +
                        "SELECT FROM table_name\n" +
                        "WHERE column LIKE 'XXXX%'\n" +
                        "\n" +
                        "or \n" +
                        "\n" +
                        "SELECT FROM table_name\n" +
                        "WHERE column LIKE '%XXXX%'\n" +
                        "\n" +
                        "or\n" +
                        "\n" +
                        "SELECT FROM table_name\n" +
                        "WHERE column LIKE 'XXXX_'\n" +
                        "\n" +
                        "or\n" +
                        "\n" +
                        "SELECT FROM table_name\n" +
                        "WHERE column LIKE '_XXXX'\n" +
                        "\n" +
                        "or\n" +
                        "\n" +
                        "SELECT FROM table_name\n" +
                        "WHERE column LIKE '_XXXX_'\n" +
                        "You can combine N number of conditions using AND or OR operators. Here, XXXX could be any numeric or string value.\n" +
                        "\n" +
                        "Example:\n" +
                        "Here are number of examples showing WHERE part having different LIKE clause with '%' and '_' operators:\n\n" +
                        "WHERE SALARY LIKE '200%'\tFinds any values that start with 200\n" +
                        "WHERE SALARY LIKE '%200%'\tFinds any values that have 200 in any position\n" +
                        "WHERE SALARY LIKE '_00%'\tFinds any values that have 00 in the second and third positions\n" +
                        "WHERE SALARY LIKE '2_%_%'\tFinds any values that start with 2 and are at least 3 characters in length\n" +
                        "WHERE SALARY LIKE '%2'\tFinds any values that end with 2\n" +
                        "WHERE SALARY LIKE '_2%3'\tFinds any values that have a 2 in the second position and end with a 3\n" +
                        "WHERE SALARY LIKE '2___3'\tFinds any values in a five-digit number that start with 2 and end with 3\n\n" +
                        "Following is an example, which would display all the records from CUSTOMERS table where SALARY starts with 200:\n" +
                        "\n" +
                        "SQL> SELECT * FROM CUSTOMERS\n" +
                        "WHERE SALARY LIKE '200%';\n" +
                        "This would produce the following result:\n" +
                        "\n"
                        );

        syntaxModule.setSyntaxCommandOutput(
                "+----+----------+-----+-----------+----------+\n" +
                "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                "+----+----------+-----+-----------+----------+\n" +
                "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                "+----+----------+-----+-----------+----------+");
        syntaxModule.setSyntaxQuestion("Select a row in table : codeTable[id, programLine] containing entry programLine starting with Infinite");
        syntaxModule.setSyntaxQuestionOutput("| 1 | Infinite_Programmer |");
        syntaxModule.setSyntaxSolution("SELECT * FROM CODETABLE WHERE PROGRAMLINE LIKE 'Infinite%'");
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
        syntaxModule.setSyntaxName("SQL - ORDER BY Clause");
        syntaxModule.setSyntaxDescription(
                "The SQL ORDER BY clause is used to sort the data in ascending or descending order, based on one or more columns. Some database sorts query results in ascending order by default.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of ORDER BY clause is as follows:\n" +
                        "\n" +
                        "SELECT column-list \n" +
                        "FROM table_name \n" +
                        "[WHERE condition] \n" +
                        "[ORDER BY column1, column2, .. columnN] [ASC | DESC];\n" +
                        "You can use more than one column in the ORDER BY clause. Make sure whatever column you are using to sort, that column should be in column-list.\n" +
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
                        "Following is an example, which would sort the result in ascending order by NAME and SALARY:\n" +
                        "\n" +
                        "SQL> SELECT * FROM CUSTOMERS\n" +
                        "  ORDER BY NAME, SALARY;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Following is an example, which would sort the result in descending order by NAME:\n" +
                        "\n" +
                        "SQL> SELECT * FROM CUSTOMERS\n" +
                        "  ORDER BY NAME DESC;\n" +
                        "This would produce the following result:");


        syntaxModule.setSyntaxCommandOutput(
                "+----+----------+-----+-----------+----------+\n" +
                "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                "+----+----------+-----+-----------+----------+\n" +
                "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                "|  2 | Khilan   |  25 | Delhi     |  1500.00 |\n" +
                "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                "|  4 | Chaitali |  25 | Mumbai    |  6500.00 |\n" +
                "+----+----------+-----+-----------+----------+");
        syntaxModule.setSyntaxQuestion("Select all fields from table codeTable [id, programLine] order by id - descending order[DESC]\n\nSIDE NOTE: For Ascending order its ASC");
        syntaxModule.setSyntaxSolution("SELECT * FROM CODETABLE ORDER BY ID DESC;");
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
        syntaxModule.setSyntaxName("SQL - Group By");
        syntaxModule.setSyntaxDescription(
                "The SQL GROUP BY clause is used in collaboration with the SELECT statement to arrange identical data into groups.\n" +
                        "\n" +
                        "The GROUP BY clause follows the WHERE clause in a SELECT statement and precedes the ORDER BY clause.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of GROUP BY clause is given below. The GROUP BY clause must follow the conditions in the WHERE clause and must precede the ORDER BY clause if one is used.\n" +
                        "\n" +
                        "SELECT column1, column2\n" +
                        "FROM table_name\n" +
                        "WHERE [ conditions ]\n" +
                        "GROUP BY column1, column2\n" +
                        "ORDER BY column1, column2\n" +
                        "Example:\n" +
                        "Consider the CUSTOMERS table is having the following records:\n" +
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
                        "If you want to know the total amount of salary on each customer, then GROUP BY query would be as follows:\n" +
                        "\n" +
                        "SQL> SELECT NAME, SUM(SALARY) FROM CUSTOMERS\n" +
                        "  GROUP BY NAME;\n" +
                        "This would produce the following result:\n" +
                        "\n" +
                        "+----------+-------------+\n" +
                        "| NAME     | SUM(SALARY) |\n" +
                        "+----------+-------------+\n" +
                        "| Chaitali |     6500.00 |\n" +
                        "| Hardik   |     8500.00 |\n" +
                        "| kaushik  |     2000.00 |\n" +
                        "| Khilan   |     1500.00 |\n" +
                        "| Komal    |     4500.00 |\n" +
                        "| Muffy    |    10000.00 |\n" +
                        "| Ramesh   |     2000.00 |\n" +
                        "+----------+-------------+\n" +
                        "Now, let us have following table where CUSTOMERS table has the following records with duplicate names:\n" +
                        "\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "| ID | NAME     | AGE | ADDRESS   | SALARY   |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "|  1 | Ramesh   |  32 | Ahmedabad |  2000.00 |\n" +
                        "|  2 | Ramesh   |  25 | Delhi     |  1500.00 |\n" +
                        "|  3 | kaushik  |  23 | Kota      |  2000.00 |\n" +
                        "|  4 | kaushik  |  25 | Mumbai    |  6500.00 |\n" +
                        "|  5 | Hardik   |  27 | Bhopal    |  8500.00 |\n" +
                        "|  6 | Komal    |  22 | MP        |  4500.00 |\n" +
                        "|  7 | Muffy    |  24 | Indore    | 10000.00 |\n" +
                        "+----+----------+-----+-----------+----------+\n" +
                        "Now again, if you want to know the total amount of salary on each customer, then GROUP BY query would be as follows:\n" +
                        "\n" +
                        "SQL> SELECT NAME, SUM(SALARY) FROM CUSTOMERS\n" +
                        "  GROUP BY NAME;\n" +
                        "This would produce the following result:");


        syntaxModule.setSyntaxCommandOutput(
                "+---------+-------------+\n" +
                "| NAME    | SUM(SALARY) |\n" +
                "+---------+-------------+\n" +
                "| Hardik  |     8500.00 |\n" +
                "| kaushik |     8500.00 |\n" +
                "| Komal   |     4500.00 |\n" +
                "| Muffy   |    10000.00 |\n" +
                "| Ramesh  |     3500.00 |\n" +
                "+---------+-------------+");
        syntaxModule.setSyntaxQuestion("Select all row values from codeTable[id, programLine] GROUP BY programLine");
        syntaxModule.setSyntaxSolution("SELECT ID, PROGRAMLINE FROM CODETABLE GROUP BY PROGRAMLINE;");
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
        syntaxModule.setSyntaxName("SQL - Distinct Keyword");
        syntaxModule.setSyntaxDescription(
                "The SQL DISTINCT keyword is used in conjunction with SELECT statement to eliminate all the duplicate records and fetching only unique records.\n" +
                        "\n" +
                        "There may be a situation when you have multiple duplicate records in a table. While fetching such records, it makes more sense to fetch only unique records instead of fetching duplicate records.");
        syntaxModule.setSyntaxCommand(
                "Syntax:\n" +
                        "The basic syntax of DISTINCT keyword to eliminate duplicate records is as follows:\n" +
                        "\n" +
                        "SELECT DISTINCT column1, column2,.....columnN \n" +
                        "FROM table_name\n" +
                        "WHERE [condition]\n" +
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
                        "First, let us see how the following SELECT query returns duplicate salary records:\n" +
                        "\n" +
                        "SQL> SELECT SALARY FROM CUSTOMERS\n" +
                        "  ORDER BY SALARY;\n" +
                        "This would produce the following result where salary 2000 is coming twice which is a duplicate record from the original table.\n" +
                        "\n" +
                        "+----------+\n" +
                        "| SALARY   |\n" +
                        "+----------+\n" +
                        "|  1500.00 |\n" +
                        "|  2000.00 |\n" +
                        "|  2000.00 |\n" +
                        "|  4500.00 |\n" +
                        "|  6500.00 |\n" +
                        "|  8500.00 |\n" +
                        "| 10000.00 |\n" +
                        "+----------+\n" +
                        "Now, let us use DISTINCT keyword with the above SELECT query and see the result:\n" +
                        "\n" +
                        "SQL> SELECT DISTINCT SALARY FROM CUSTOMERS\n" +
                        "  ORDER BY SALARY;\n" +
                        "This would produce the following result where we do not have any duplicate entry:");


        syntaxModule.setSyntaxCommandOutput(
                "+----------+\n" +
                "| SALARY   |\n" +
                "+----------+\n" +
                "|  1500.00 |\n" +
                "|  2000.00 |\n" +
                "|  4500.00 |\n" +
                "|  6500.00 |\n" +
                "|  8500.00 |\n" +
                "| 10000.00 |\n" +
                "+----------+");
        syntaxModule.setSyntaxQuestion("Select all columns from codeTable[id, programLine] with distict programLine");
        syntaxModule.setSyntaxQuestionOutput("| 1 |");
        syntaxModule.setSyntaxSolution("SELECT DISTINCT ID FROM CODETABLE ORDER BY ID;\n");
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
        /*firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);*/

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
        /*firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);*/

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
        /*firebaseDatabaseHandler.writeSyntaxModule(
                syntaxModule);*/



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
                                "char ch, file_name[25];\n" +
                                "FILE *fp;\n" +
                                " \n" +
                                "printf(\"Enter the name of file you wish to see\\n\");\n" +
                                "gets(file_name);\n" +
                                " \n" +
                                "fp = fopen(file_name,\"r\"); // read mode\n" +
                                " \n" +
                                "if( fp == NULL )\n" +
                                "{\n" +
                                "perror(\"Error while opening the file.\\n\");\n" +
                                "exit(EXIT_FAILURE);\n" +
                                "}\n" +
                                " \n" +
                                "printf(\"The contents of %s file are :\\n\", file_name);\n" +
                                " \n" +
                                "while( ( ch = fgetc(fp) ) != EOF )\n" +
                                "printf(\"%c\",ch);\n" +
                                " \n" +
                                "fclose(fp);\n" +
                                "return 0;\n" +
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
                                "char a[100], b[100];\n" +
                                "int position;\n" +
                                "  printf(“Enter some text\\n”);\n" +
                                "  gets(a);\n" +
                                "  printf(“Enter a string to find\\n”);\n" +
                                "  gets(b);\n" +
                                "  position = match(a, b);\n" +
                                "  if(position!=-1)\n" +
                                " printf(“Found at location %d\\n”, position+1);\n" +
                                "  else\n" +
                                " printf(“Not found.\\n”);\n" +
                                "  getch();\n" +
                                "}\n" +
                                "\n" +
                                "int match(char *a, char *b)\n" +
                                "{\n" +
                                "int c;\n" +
                                "int position = 0;\n" +
                                "char *x, *y;\n" +
                                "x = a;\n" +
                                "y = b;\n" +
                                "while(*a)\n" +
                                "{\n" +
                                "  while(*x==*y)\n" +
                                " {\n" +
                                "x++;\n" +
                                "y++;\n" +
                                "  if(*x==”||*y==”)\n" +
                                "break;\n" +
                                " }\n" +
                                " if(*y==”)\n" +
                                "  break;\n" +
                                " a++;\n" +
                                " position++;\n" +
                                "x = a;\n" +
                                "y = b;\n" +
                                "  }\n" +
                                "if(*a)\n" +
                                "  return position;\n" +
                                "else\n" +
                                "  return -1;\n" +
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
                        "char a[1000], b[1000];\n" +
                        " \n" +
                        "printf(\"Enter the first string\\n\");\n" +
                        "gets(a);\n" +
                        " \n" +
                        "printf(\"Enter the second string\\n\");\n" +
                        "gets(b);\n" +
                        " \n" +
                        "strcat(a,b);\n" +
                        " \n" +
                        "printf(\"String obtained on concatenation is %s\\n\",a);\n" +
                        " \n" +
                        "return 0;\n" +
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
                        "char p[100], q[100];\n" +
                        " \n" +
                        "printf(\"Input a string\\n\");\n" +
                        "gets(p);\n" +
                        " \n" +
                        "printf(\"Input a string to concatenate\\n\");\n" +
                        "gets(q);\n" +
                        " \n" +
                        "concatenate(p, q); \n" +
                        " \n" +
                        "printf(\"String obtained on concatenation is \\\"%s\\\"\\n\", p);\n" +
                        " \n" +
                        "return 0;\n" +
                        "}\n" +
                        " \n" +
                        "void concatenate(char p[], char q[]) {\n" +
                        "int c, d;\n" +
                        " \n" +
                        "c = 0;\n" +
                        " \n" +
                        "while (p[c] != '\\0') {\n" +
                        "c++;   \n" +
                        "}\n" +
                        " \n" +
                        "d = 0;\n" +
                        " \n" +
                        "while (q[d] != '\\0') {\n" +
                        "p[c] = q[d];\n" +
                        "d++;\n" +
                        "c++; \n" +
                        "}\n" +
                        " \n" +
                        "p[c] = '\\0';\n" +
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
                        " scanf(\"%d\", &array[c]);\n" +
                        "  }\n" +
                        " \n" +
                        "  for (c = 1 ; c <= n - 1; c++) {\n" +
                        " d = c;\n" +
                        " \n" +
                        " while ( d > 0 && array[d] < array[d-1]) {\n" +
                        "t          = array[d];\n" +
                        "array[d]   = array[d-1];\n" +
                        "array[d-1] = t;\n" +
                        " \n" +
                        "d--;\n" +
                        " }\n" +
                        "  }\n" +
                        " \n" +
                        "  printf(\"Sorted list in ascending order:\\n\");\n" +
                        " \n" +
                        "  for (c = 0; c <= n - 1; c++) {\n" +
                        " printf(\"%d\\n\", array[c]);\n" +
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
                        "char string[100];\n" +
                        "int c = 0, count[26] = {0};\n" +
                        " \n" +
                        "printf(\"Enter a string\\n\");\n" +
                        "gets(string);\n" +
                        " \n" +
                        "while (string[c] != '\\0')\n" +
                        "{\n" +
                        "*//** Considering characters from 'a' to 'z' only\n" +
                        "    and ignoring others *//*\n" +
                        " \n" +
                        "if (string[c] >= 'a' && string[c] <= 'z') \n" +
                        "   count[string[c]-'a']++;\n" +
                        " \n" +
                        "c++;\n" +
                        "}\n" +
                        " \n" +
                        "for (c = 0; c < 26; c++)\n" +
                        "{\n" +
                        "*//** Printing only those characters \n" +
                        "    whose count is at least 1 *//*\n" +
                        " \n" +
                        "if (count[c] != 0)\n" +
                        "   printf(\"%c occurs %d times in the entered string.\\n\",c+'a',count[c]);\n" +
                        "}\n" +
                        " \n" +
                        "return 0;\n" +
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
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Swap by reference - inline",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Display Array",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Generate Random input",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Function to partition an int Array using First element as Pivot",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Quicksort method",
                programLanguage, ""));

        /************************************************************************************************/

        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Topological ordering of vertices in a given digraph",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Read adjacency matrix and topologically sort them",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Transitive closure of a given\n" +
                "directed graph using Warshall's algorithm",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Read adjacency matrix and compute transitive closure using Warshall's algorithm",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Function to construct profit table for 0/1 Knapsack problem",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Function to determine optimal subset that fits into the knapsack",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "0/1 Knapsack problem using Dynamic Programming",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Find shortest paths to other vertices using Dijkstra's algorithm",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Minimum Cost Spanning Tree of a given undirected graph using Kruskal's algorithm",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Print all the nodes reachable from a given starting node in a digraph using BFS method",
                programLanguage, ""));

        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Check whether a\n" +
                "given graph is connected or not using DFS method",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Find a subset of a given set S = {sl, s2,.....,sn} of n positive integers whose sum is equal to a given positive integer\n" +
                "d.",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Traveling Salesperson problem",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "Minimum Cost Spanning Tree of a given undirected graph using Prim's algorithm",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "All-Pairs Shortest Paths Problem using Floyd's algorithm",
                programLanguage, ""));
        firebaseDatabaseHandler.writeProgramIndex( new ProgramIndex(index++, "N Queen's problem using Back Tracking",
                programLanguage, ""));


    }

    public void insertADAProgramTables() {
        String programLanguage = "ada";
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        //http://man7.org/linux/man-pages/man3/mkfifo.3.html
        //Getting database connection : JDBC MySQL
        String programCode =
                "inline void swapByReference(int *a, int *b){\n" +
                        "int temp = *a;\n" +
                        "*a = *b;\n" +
                        "*b = temp;\n" +
                        "}";
        //https://gcc.gnu.org/onlinedocs/cpp/Ifdef.html
        String programExplanation =
                "inline void swapByReference(int *a, int *b){\n" +
                        "int temp = *a;\n" +
                        "*a = *b;\n" +
                        "*b = temp;\n" +
                        "}";
        ArrayList<String> programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        ArrayList<String> programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        int programIndex = 1;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        //Create db
        programCode =
                "void displayArray( int X[], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "for(i=0;i<n;i++)\n" +
                        "printf(\" %5d \\n\",X[i]);\n" +
                        "}";
        programExplanation =
                "void displayArray( int X[], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "for(i=0;i<n;i++)\n" +
                        "printf(\" %5d \\n\",X[i]);\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void generateRandomInput(int X[], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "srand(time(NULL));\n" +
                        "for(i=0;i<n;i++)\n" +
                        "{\n" +
                        "X[i] = rand()%10000;\n" +
                        "}\n" +
                        "}";
        programExplanation =
                "void generateRandomInput(int X[], int n)\n" +
                        "{\n" +
                        "int i;\n" +
                        "srand(time(NULL));\n" +
                        "for(i=0;i<n;i++)\n" +
                        "{\n" +
                        "X[i] = rand()%10000;\n" +
                        "}\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "int partitionByPivot(int a[], int startIndex, int endIndex)\n" +
                        "{\n" +
                        "int i,j,temp;\n" +
                        "int p;\n" +
                        "p = a[startIndex];\n" +
                        "i = startIndex;\n" +
                        "j = endIndex+1;\n" +
                        "do\n" +
                        "{\n" +
                        "do { i++; } while (a[i] < p);\n" +
                        "do { j--; } while (a[j] > p);\n" +
                        "swapByReference(&a[i], &a[j]);\n" +
                        "}\n" +
                        "while (i<j);\n" +
                        "swapByReference(&a[i], &a[j]);\n" +
                        "swapByReference(&a[startIndex], &a[j]);\n" +
                        "return j;\n" +
                        "}";
        programExplanation =
                "int partitionByPivot(int a[], int startIndex, int endIndex)\n" +
                        "{\n" +
                        "int i,j,temp;\n" +
                        "int p;\n" +
                        "p = a[startIndex];\n" +
                        "i = startIndex;\n" +
                        "j = endIndex+1;\n" +
                        "do\n" +
                        "{\n" +
                        "do { i++; } while (a[i] < p);\n" +
                        "do { j--; } while (a[j] > p);\n" +
                        "swapByReference(&a[i], &a[j]);\n" +
                        "}\n" +
                        "while (i<j);\n" +
                        "swapByReference(&a[i], &a[j]);\n" +
                        "swapByReference(&a[startIndex], &a[j]);\n" +
                        "return j;\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void quickSort(int a[], int startIndex, int endIndex)\n" +
                        "{\n" +
                        "int s;\n" +
                        "if (startIndex < endIndex)\n" +
                        "{\n" +
                        "s = partitionByPivot(a, startIndex, endIndex);\n" +
                        "quickSort(a, startIndex, s-1);\n" +
                        "quickSort(a, s+1, endIndex);\n" +
                        "}\n" +
                        "}";
        programExplanation =
                "void quickSort(int a[], int startIndex, int endIndex)\n" +
                        "{\n" +
                        "int s;\n" +
                        "if (startIndex < endIndex)\n" +
                        "{\n" +
                        "s = partitionByPivot(a, startIndex, endIndex);\n" +
                        "quickSort(a, startIndex, s-1);\n" +
                        "quickSort(a, s+1, endIndex);\n" +
                        "}\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void printTopologicalOrder(int a[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int in[MAX], out[MAX], stack[MAX], top=-1;\n" +
                        "int i,j,k=0;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "in[i] = 0;\n" +
                        "for (j=0; j<n; j++)\n" +
                        "if (a[j][i] == 1)\n" +
                        "in[i]++;\n" +
                        "}\n" +
                        "while(1)\n" +
                        "{\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (in[i] == 0)\n" +
                        "{\n" +
                        "stack[++top] = i;\n" +
                        "in[i] = -1;\n" +
                        "}\n" +
                        "}\n" +
                        "if (top == -1)\n" +
                        "break;\n" +
                        "out[k] = stack[top--];\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (a[out[k]][i] == 1)\n" +
                        "in[i]--;\n" +
                        "}\n" +
                        "k++;\n" +
                        "}\n" +
                        "printf(\"Topological Sorting (JOB SEQUENCE) is:- \\n\");\n" +
                        "for (i=0;i<k;i++)\n" +
                        "printf(\"%d \",out[i] + 1);\n" +
                        "}";
        programExplanation =
                "void printTopologicalOrder(int a[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int in[MAX], out[MAX], stack[MAX], top=-1;\n" +
                        "int i,j,k=0;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "in[i] = 0;\n" +
                        "for (j=0; j<n; j++)\n" +
                        "if (a[j][i] == 1)\n" +
                        "in[i]++;\n" +
                        "}\n" +
                        "while(1)\n" +
                        "{\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (in[i] == 0)\n" +
                        "{\n" +
                        "stack[++top] = i;\n" +
                        "in[i] = -1;\n" +
                        "}\n" +
                        "}\n" +
                        "if (top == -1)\n" +
                        "break;\n" +
                        "out[k] = stack[top--];\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (a[out[k]][i] == 1)\n" +
                        "in[i]--;\n" +
                        "}\n" +
                        "k++;\n" +
                        "}\n" +
                        "printf(\"Topological Sorting (JOB SEQUENCE) is:- \\n\");\n" +
                        "for (i=0;i<k;i++)\n" +
                        "printf(\"%d \",out[i] + 1);\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void printTopologicalOrder(int a[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int in[MAX], out[MAX], stack[MAX], top=-1;\n" +
                        "int i,j,k=0;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "in[i] = 0;\n" +
                        "for (j=0; j<n; j++)\n" +
                        "if (a[j][i] == 1)\n" +
                        "in[i]++;\n" +
                        "}\n" +
                        "while(1)\n" +
                        "{\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (in[i] == 0)\n" +
                        "{\n" +
                        "stack[++top] = i;\n" +
                        "in[i] = -1;\n" +
                        "}\n" +
                        "}\n" +
                        "if (top == -1)\n" +
                        "break;\n" +
                        "out[k] = stack[top--];\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (a[out[k]][i] == 1)\n" +
                        "in[i]--;\n" +
                        "}\n" +
                        "k++;\n" +
                        "}\n" +
                        "printf(\"Topological Sorting (JOB SEQUENCE) is:- \\n\");\n" +
                        "for (i=0;i<k;i++)\n" +
                        "printf(\"%d \",out[i] + 1);\n" +
                        "}";
        programExplanation =
                "void printTopologicalOrder(int a[MAX][MAX], int n)\n" +
                        "{\n" +
                        "int in[MAX], out[MAX], stack[MAX], top=-1;\n" +
                        "int i,j,k=0;\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "in[i] = 0;\n" +
                        "for (j=0; j<n; j++)\n" +
                        "if (a[j][i] == 1)\n" +
                        "in[i]++;\n" +
                        "}\n" +
                        "while(1)\n" +
                        "{\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (in[i] == 0)\n" +
                        "{\n" +
                        "stack[++top] = i;\n" +
                        "in[i] = -1;\n" +
                        "}\n" +
                        "}\n" +
                        "if (top == -1)\n" +
                        "break;\n" +
                        "out[k] = stack[top--];\n" +
                        "for (i=0;i<n;i++)\n" +
                        "{\n" +
                        "if (a[out[k]][i] == 1)\n" +
                        "in[i]--;\n" +
                        "}\n" +
                        "k++;\n" +
                        "}\n" +
                        "printf(\"Topological Sorting (JOB SEQUENCE) is:- \\n\");\n" +
                        "for (i=0;i<k;i++)\n" +
                        "printf(\"%d \",out[i] + 1);\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "#include <stdio.h>\n" +
                        "const int MAX = 10;\n" +
                        "void printTopologicalOrder(int a[MAX][MAX], int n);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        " int a[MAX][MAX],n;\n" +
                        " int i,j;\n" +
                        " printf(\"Topological Sorting Algorithm -\\n\");\n" +
                        " printf(\"\\nEnter the number of vertices : \");\n" +
                        " scanf(\"%d\",&n);\n" +
                        " printf(\"Enter the adjacency matrix -\\n\");\n" +
                        " for (i=0; i<n; i++)\n" +
                        " for (j=0; j<n; j++)\n" +
                        " scanf(\"%d\",&a[i][j]);\n" +
                        " printTopologicalOrder(a,n);\n" +
                        " printf(\"\\n\");\n" +
                        " return 0;\n" +
                        "}";
        programExplanation =
                "#include <stdio.h>\n" +
                        "const int MAX = 10;\n" +
                        "void printTopologicalOrder(int a[MAX][MAX], int n);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        " int a[MAX][MAX],n;\n" +
                        " int i,j;\n" +
                        " printf(\"Topological Sorting Algorithm -\\n\");\n" +
                        " printf(\"\\nEnter the number of vertices : \");\n" +
                        " scanf(\"%d\",&n);\n" +
                        " printf(\"Enter the adjacency matrix -\\n\");\n" +
                        " for (i=0; i<n; i++)\n" +
                        " for (j=0; j<n; j++)\n" +
                        " scanf(\"%d\",&a[i][j]);\n" +
                        " printTopologicalOrder(a,n);\n" +
                        " printf(\"\\n\");\n" +
                        " return 0;\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void computeWarshallClosure(int graph[MAX][MAX], int numVert)\n" +
                        "{\n" +
                        " int i,j,k;\n" +
                        " for (k=0; k<numVert; k++)\n" +
                        " {\n" +
                        " for (i=0; i<numVert; i++)\n" +
                        " {\n" +
                        " for (j=0; j<numVert; j++)\n" +
                        " {\n" +
                        " if (graph[i][j] || (graph[i][k] && graph[k][j]))\n" +
                        " graph[i][j] = 1;\n" +
                        " }\n" +
                        " }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "void computeWarshallClosure(int graph[MAX][MAX], int numVert)\n" +
                        "{\n" +
                        " int i,j,k;\n" +
                        " for (k=0; k<numVert; k++)\n" +
                        " {\n" +
                        " for (i=0; i<numVert; i++)\n" +
                        " {\n" +
                        " for (j=0; j<numVert; j++)\n" +
                        " {\n" +
                        " if (graph[i][j] || (graph[i][k] && graph[k][j]))\n" +
                        " graph[i][j] = 1;\n" +
                        " }\n" +
                        " }\n" +
                        " }\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "#include<stdio.h>\n" +
                        "const int MAX = 100;\n" +
                        "void computeWarshallClosure(int graph[MAX][MAX], int numVert);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        " int i, j, numVert;\n" +
                        " int graph[MAX][MAX];\n" +
                        " printf(\"Warshall's Transitive Closure\\n\");\n" +
                        " printf(\"Enter the number of vertices : \");\n" +
                        " scanf(\"%d\",&numVert);\n" +
                        " printf(\"Enter the adjacency matrix :-\\n\");\n" +
                        " for (i=0; i<numVert; i++)\n" +
                        " for (j=0; j<numVert; j++)\n" +
                        " scanf(\"%d\",&graph[i][j]);\n" +
                        " computeWarshallClosure(graph, numVert);\n" +
                        " printf(\"\\nThe transitive closure for the given graph is :-\\n\");\n" +
                        " for (i=0; i<numVert; i++)\n" +
                        " {\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        " {\n" +
                        " printf(\"%d\\t\",graph[i][j]);\n" +
                        " }\n" +
                        " printf(\"\\n\");\n" +
                        " }\n" +
                        " return 0;\n" +
                        "}";
        programExplanation =
                "#include<stdio.h>\n" +
                        "const int MAX = 100;\n" +
                        "void computeWarshallClosure(int graph[MAX][MAX], int numVert);\n" +
                        "int main(void)\n" +
                        "{\n" +
                        " int i, j, numVert;\n" +
                        " int graph[MAX][MAX];\n" +
                        " printf(\"Warshall's Transitive Closure\\n\");\n" +
                        " printf(\"Enter the number of vertices : \");\n" +
                        " scanf(\"%d\",&numVert);\n" +
                        " printf(\"Enter the adjacency matrix :-\\n\");\n" +
                        " for (i=0; i<numVert; i++)\n" +
                        " for (j=0; j<numVert; j++)\n" +
                        " scanf(\"%d\",&graph[i][j]);\n" +
                        " computeWarshallClosure(graph, numVert);\n" +
                        " printf(\"\\nThe transitive closure for the given graph is :-\\n\");\n" +
                        " for (i=0; i<numVert; i++)\n" +
                        " {\n" +
                        "for (j=0; j<numVert; j++)\n" +
                        " {\n" +
                        " printf(\"%d\\t\",graph[i][j]);\n" +
                        " }\n" +
                        " printf(\"\\n\");\n" +
                        " }\n" +
                        " return 0;\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void calculateProfitTable(int weight[MAX], int profit[MAX], int n, int c, int profitTable[MAX][MAX])\n" +
                        "{\n" +
                        " int i,j;\n" +
                        " for (j=0; j<=c; j++)\n" +
                        " profitTable[0][j] = 0;\n" +
                        " for (i=0; i<=n; i++)\n" +
                        " profitTable[i][0] = 0;\n" +
                        " for (i=1; i<=n; i++)\n" +
                        " {\n" +
                        " for (j=1; j<=c; j++)\n" +
                        " {\n" +
                        " if (j-weight[i] < 0)\n" +
                        " profitTable[i][j] = profitTable[i-1][j];\n" +
                        " else\n" +
                        " profitTable[i][j] = max( t[i-1][j], profit[i] + profitTable[i-1][j-weight[i]]);\n" +
                        " }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "void calculateProfitTable(int weight[MAX], int profit[MAX], int n, int c, int profitTable[MAX][MAX])\n" +
                        "{\n" +
                        " int i,j;\n" +
                        " for (j=0; j<=c; j++)\n" +
                        " profitTable[0][j] = 0;\n" +
                        " for (i=0; i<=n; i++)\n" +
                        " profitTable[i][0] = 0;\n" +
                        " for (i=1; i<=n; i++)\n" +
                        " {\n" +
                        " for (j=1; j<=c; j++)\n" +
                        " {\n" +
                        " if (j-weight[i] < 0)\n" +
                        " profitTable[i][j] = profitTable[i-1][j];\n" +
                        " else\n" +
                        " profitTable[i][j] = max( t[i-1][j], profit[i] + profitTable[i-1][j-weight[i]]);\n" +
                        " }\n" +
                        " }\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void calculateProfitTable(int weight[MAX], int profit[MAX], int n, int c, int profitTable[MAX][MAX])\n" +
                        "{\n" +
                        " int i,j;\n" +
                        " for (j=0; j<=c; j++)\n" +
                        " profitTable[0][j] = 0;\n" +
                        " for (i=0; i<=n; i++)\n" +
                        " profitTable[i][0] = 0;\n" +
                        " for (i=1; i<=n; i++)\n" +
                        " {\n" +
                        " for (j=1; j<=c; j++)\n" +
                        " {\n" +
                        " if (j-weight[i] < 0)\n" +
                        " profitTable[i][j] = profitTable[i-1][j];\n" +
                        " else\n" +
                        " profitTable[i][j] = max( t[i-1][j], profit[i] + profitTable[i-1][j-weight[i]]);\n" +
                        " }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "void calculateProfitTable(int weight[MAX], int profit[MAX], int n, int c, int profitTable[MAX][MAX])\n" +
                        "{\n" +
                        " int i,j;\n" +
                        " for (j=0; j<=c; j++)\n" +
                        " profitTable[0][j] = 0;\n" +
                        " for (i=0; i<=n; i++)\n" +
                        " profitTable[i][0] = 0;\n" +
                        " for (i=1; i<=n; i++)\n" +
                        " {\n" +
                        " for (j=1; j<=c; j++)\n" +
                        " {\n" +
                        " if (j-weight[i] < 0)\n" +
                        " profitTable[i][j] = profitTable[i-1][j];\n" +
                        " else\n" +
                        " profitTable[i][j] = max( t[i-1][j], profit[i] + profitTable[i-1][j-weight[i]]);\n" +
                        " }\n" +
                        " }\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "void calculateProfitTable(int weight[MAX], int profit[MAX], int n, int c, int profitTable[MAX][MAX])\n" +
                        "{\n" +
                        " int i,j;\n" +
                        " for (j=0; j<=c; j++)\n" +
                        " profitTable[0][j] = 0;\n" +
                        " for (i=0; i<=n; i++)\n" +
                        " profitTable[i][0] = 0;\n" +
                        " for (i=1; i<=n; i++)\n" +
                        " {\n" +
                        " for (j=1; j<=c; j++)\n" +
                        " {\n" +
                        " if (j-weight[i] < 0)\n" +
                        " profitTable[i][j] = profitTable[i-1][j];\n" +
                        " else\n" +
                        " profitTable[i][j] = max( t[i-1][j], profit[i] + profitTable[i-1][j-weight[i]]);\n" +
                        " }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "void calculateProfitTable(int weight[MAX], int profit[MAX], int n, int c, int profitTable[MAX][MAX])\n" +
                        "{\n" +
                        " int i,j;\n" +
                        " for (j=0; j<=c; j++)\n" +
                        " profitTable[0][j] = 0;\n" +
                        " for (i=0; i<=n; i++)\n" +
                        " profitTable[i][0] = 0;\n" +
                        " for (i=1; i<=n; i++)\n" +
                        " {\n" +
                        " for (j=1; j<=c; j++)\n" +
                        " {\n" +
                        " if (j-weight[i] < 0)\n" +
                        " profitTable[i][j] = profitTable[i-1][j];\n" +
                        " else\n" +
                        " profitTable[i][j] = max( t[i-1][j], profit[i] + profitTable[i-1][j-weight[i]]);\n" +
                        " }\n" +
                        " }\n" +
                        "}";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            programLanguage,
                            programLines.get(i),
                            programExplanations.get(i)));
        }
    }

    public void insertSQLProgramTables() {
        FirebaseDatabaseHandler firebaseDatabaseHandler = new FirebaseDatabaseHandler(context);
        //http://man7.org/linux/man-pages/man3/mkfifo.3.html
        //Getting database connection : JDBC MySQL
        String programCode =
                "inline void swapByReference(int *a, int *b){\n" +
                        "int temp = *a;\n" +
                        "*a = *b;\n" +
                        "*b = temp;\n" +
                        "}";
        //https://gcc.gnu.org/onlinedocs/cpp/Ifdef.html
        String programExplanation =
                "inline void swapByReference(int *a, int *b){\n" +
                        "int temp = *a;\n" +
                        "*a = *b;\n" +
                        "*b = temp;\n" +
                        "}";
        ArrayList<String> programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        ArrayList<String> programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        int programIndex = 1;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }


        //Create db
        programCode =
                "import java.sql.*;\n" +
                        "public class JDBCExample {\n" +
                        "public static void main(String[] args) {\n" +
                        "Connection conn = null;\n" +
                        "Statement stmt = null;\n" +
                        "try{\n" +
                        "conn = DBConnector.getConnection();\n" +
                        "stmt = conn.createStatement();\n" +
                        "String sql = \"CREATE DATABASE STUDENTSDB\";\n" +
                        "stmt.executeUpdate(sql);\n" +
                        "System.out.println(\"Database created successfully...\");\n" +
                        "}catch(SQLException se){\n" +
                        "se.printStackTrace();\n" +
                        "}catch(Exception e){\n" +
                        "e.printStackTrace();\n" +
                        "}finally{\n" +
                        "try{\n" +
                        "   if(stmt!=null)\n" +
                        "      stmt.close();\n" +
                        "   if(conn!=null)\n" +
                        "      conn.close();\n" +
                        "}catch(SQLException se2){\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "} ";
        programExplanation =
                "import java.sql.*\n" +
                        "class definition JDBCExample \n" +
                        "Main declaration\n" +
                        "Variable declaration Connection conn;\n" +
                        "Variable declaration Statement stmt;\n" +
                        "try block start\n" +
                        "initialize conn = call DBConnector class getConnection();\n" +
                        "initialize stmt = conn.createStatement()\n" +
                        "initialize sql = \"CREATE DATABASE STUDENTSDB\"\n" +
                        "call stmt.executeUpdate(sql)\n" +
                        "Print (\"Database created successfully...\")\n" +
                        "catch SQLException se \n" +
                        "Print stacktrace se\n" +
                        "catch Exception e \n" +
                        "Print stacktrace e\n" +
                        "finally block start\n" +
                        "try block start\n" +
                        "check if(stmt!=null)\n" +
                        "      close stmt\n" +
                        "check if(conn!=null)\n" +
                        "      close connection\n" +
                        " catch SQLException se2 \n" +
                        "End catch block\n" +
                        "End finally block\n" +
                        "End of main\n" +
                        "End of class ";


        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "import java.sql.Connection;  \n" +
                        "import java.sql.DriverManager;  \n" +
                        "import java.sql.SQLException;  \n" +
                        "public class DBConnector {  \n" +
                        "  private static Connection conn;  \n" +
                        "  private static String url = \"jdbc:mysql://localhost:3306/STUDENTDB\", user = \"root\", pass = \"password\";\n" +
                        "  public static Connection connect() throws SQLException{  \n" +
                        " try{  \n" +
                        "Class.forName(\"com.mysql.jdbc.Driver\").newInstance();  \n" +
                        " }catch( IllegalAccessException | InstantiationException | ClassNotFoundException e ){  \n" +
                        "System.err.println(\"Error: \"+cnfe.getMessage());  \n" +
                        " } \n" +
                        " conn = DriverManager.getConnection(url,user,pass);  \n" +
                        " return conn;  \n" +
                        "  }  \n" +
                        "  public static void main(String[] args) throws SQLException, ClassNotFoundException{  \n" +
                        " if(conn !=null && !conn.isClosed())  \n" +
                        "return conn;  \n" +
                        " connect();  \n" +
                        " return conn;  \n" +
                        "  }  \n" +
                        "}  ";
        //Select db
        programExplanation =
                "import class java.sql.Connection;  \n" +
                        "import class java.sql.DriverManager;  \n" +
                        "import class java.sql.SQLException;  \n" +
                        "public class DBConnector definition  \n" +
                        "Variable declaration : Connection conn;  \n" +
                        "Variable declaration : url = \"jdbc:mysql://localhost:3306/STUDENTDB\", user = \"root\", pass = \"password\";\n" +
                        "Connection connect() method definition throws SQLException{  \n" +
                        "try block start\n" +
                        "create new instance : Class.forName(\"com.mysql.jdbc.Driver\").newInstance();  \n" +
                        "catch 3 types of exception\n" +
                        "Print error with exception message (\"Error: \"+cnfe.getMessage())  \n" +
                        "end of try catch block\n" +
                        "initialize con with DriverManager.getConnection function\n" +
                        "return conn  \n" +
                        "End of connect method\n" +
                        "Function definition main which throws SQLException, ClassNotFoundException \n" +
                        "check if(conn !=null && !conn.isClosed())  \n" +
                        "return conn \n" +
                        "call connect() to select and connect to db \n" +
                        "return conn  \n" +
                        "End of getConnection  \n" +
                        "End of class  ";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex++;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        //drop db
        programCode =
                "import java.sql.*;\n" +
                        "public class JDBCExample {\n" +
                        "public static void main(String[] args) {\n" +
                        "Connection conn = null;\n" +
                        "Statement stmt = null;\n" +
                        "try{\n" +
                        "conn = DBConnector.getConnection();\n" +
                        "stmt = conn.createStatement();\n" +
                        "String sql = \"DROP DATABASE STUDENTSDB\";\n" +
                        "stmt.executeUpdate(sql);\n" +
                        "System.out.println(\"Database created successfully...\");\n" +
                        "}catch(SQLException se){\n" +
                        "se.printStackTrace();\n" +
                        "}catch(Exception e){\n" +
                        "e.printStackTrace();\n" +
                        "}finally{\n" +
                        "try{\n" +
                        "   if(stmt!=null)\n" +
                        "      stmt.close();\n" +
                        "   if(conn!=null)\n" +
                        "      conn.close();\n" +
                        "}catch(SQLException se2){\n" +
                        "}\n" +
                        "}\n" +
                        "}\n" +
                        "} ";
        programExplanation =
                "import java.sql.*\n" +
                        "class definition JDBCExample \n" +
                        "Main declaration\n" +
                        "Variable declaration Connection conn;\n" +
                        "Variable declaration Statement stmt;\n" +
                        "try block start\n" +
                        "initialize conn = call DBConnector class getConnection();\n" +
                        "initialize stmt = conn.createStatement()\n" +
                        "initialize sql = \"CREATE DATABASE STUDENTSDB\"\n" +
                        "call stmt.executeUpdate(sql)\n" +
                        "Print (\"Database created successfully...\")\n" +
                        "catch SQLException se \n" +
                        "Print stacktrace se\n" +
                        "catch Exception e \n" +
                        "Print stacktrace e\n" +
                        "finally block start\n" +
                        "try block start\n" +
                        "check if(stmt!=null)\n" +
                        "      close stmt\n" +
                        "check if(conn!=null)\n" +
                        "      close connection\n" +
                        " catch SQLException se2 \n" +
                        "End catch block\n" +
                        "End finally block\n" +
                        "End of main\n" +
                        "End of class ";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        //Create table
        programCode =
                "public void createTable() throws SQLException {\n" +
                        " String createString =\n" +
                        "  \"Create table StudentTable (\\n\" +\n" +
                        "\t\t\"Usn varchar(10) NOT NULL PRIMARY KEY, \\n\" + \n" +
                        "\t\t\"FirstName varchar(100) NOT NULL, \\n\" +\n" +
                        "\t\t\"LastName varchar(100) NOT NULL );\";\n" +
                        " Statement stmt = null;\n" +
                        " try {\n" +
                        "\t    Connection con = DBConnection.connect();\n" +
                        "  stmt = con.createStatement();\n" +
                        "  stmt.executeUpdate(createString);\n" +
                        " } catch (SQLException e) {\n" +
                        "  e.printStackTrace();\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function defnition createTable() throws SQLException {\n" +
                        "Variable declaration String createString =\n" +
                        "Create table StudentTable (\\n\" +\n" +
                        "Usn varchar(10) NOT NULL PRIMARY KEY, \\n\" + \n" +
                        "FirstName varchar(100) NOT NULL, \\n\" +\n" +
                        "LastName varchar(100) NOT NULL );\";\n" +
                        "Variable declare : Statement stmt = null;\n" +
                        " try block\n" +
                        "Initialize : Connection con = DBConnection.connect();\n" +
                        "Initialize : stmt = con.createStatement();\n" +
                        "Execute update : stmt.executeUpdate(createString);\n" +
                        "catch SQLException e\n" +
                        "Print exception stack trace\n" +
                        "} finally block\n" +
                        "check if (stmt != null), stmt.close(); \n" +
                        "end of if block\n" +
                        "End of function ";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        //drop table
        programCode =
                "public void dropTable() throws SQLException {\n" +
                        " String createString =\n" +
                        "  \"Drop table StudentTable\";\n" +
                        " Statement stmt = null;\n" +
                        " try {\n" +
                        "\t    Connection con = DBConnection.connect();\n" +
                        "  stmt = con.createStatement();\n" +
                        "  stmt.executeUpdate(createString);\n" +
                        " } catch (SQLException e) {\n" +
                        "  e.printStackTrace();\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function defnition dropTable() throws SQLException {\n" +
                        "Variable declaration String createString =\n" +
                        "Drop table StudentTable\n" +
                        "Variable declare : Statement stmt = null;\n" +
                        " try block\n" +
                        "Initialize : Connection con = DBConnection.connect();\n" +
                        "Initialize : stmt = con.createStatement();\n" +
                        "Execute update : stmt.executeUpdate(createString);\n" +
                        "catch SQLException e\n" +
                        "Print exception stack trace\n" +
                        "} finally block\n" +
                        "check if (stmt != null), stmt.close(); \n" +
                        "end of if block\n" +
                        "End of function ";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        //TODO Insert table
        programCode =
                "public void insertTable(String studentId, String firstName, String lastName) throws SQLException {\n" +
                        " String insertString =\n" +
                        " \"Insert into StudentTabe values (\"+studentId+\",\"+firstName+\",\"+lastName+\")\";\n" +
                        " Statement stmt = null;\n" +
                        " try {\n" +
                        "\t    Connection con = DBConnection.connect();\n" +
                        "  stmt = con.createStatement();\n" +
                        "  stmt.executeUpdate(createString);\n" +
                        " } catch (SQLException e) {\n" +
                        "  e.printStackTrace();\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function defnition insertTable(String studentId, String firstName, String lastName) throws SQLException {\n" +
                        "Variable declaration String insertString =\n" +
                        " \"Insert into StudentTabe values (\"+studentId+\",\"+firstName+\",\"+lastName+\")\"\n" +
                        "Variable declare : Statement stmt = null;\n" +
                        " try block\n" +
                        "Initialize : Connection con = DBConnection.connect();\n" +
                        "Initialize : stmt = con.createStatement();\n" +
                        "Execute update : stmt.executeUpdate(createString);\n" +
                        "catch SQLException e\n" +
                        "Print exception stack trace\n" +
                        "} finally block\n" +
                        "check if (stmt != null), stmt.close(); \n" +
                        "end of if block\n" +
                        "End of function ";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }


        programCode =
                "public static void viewTable(Connection con) throws SQLException {\n" +
                        " Statement stmt = null;\n" +
                        " String query =\n" +
                        "  \"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "  \"from STUDENTTABLE\";\n" +
                        " try {\n" +
                        "  stmt = con.createStatement();\n" +
                        "  ResultSet rs = stmt.executeQuery(query);\n" +
                        "  while (rs.next()) {\n" +
                        "      String studentId = rs.getString(\"STUDENTID\");\n" +
                        "      String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        "      String lastName = rs.getString(\"LASTNAME\");\n" +
                        "      System.out.println(studentId + \"\\t\" + firstName + \"\\t\" + lastName);\n" +
                        "  }\n" +
                        " } catch (SQLException e ) {\n" +
                        "  e.print;\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function definition viewTable(Connection con) throws SQLException {\n" +
                        "Variable declaration : Statement stmt = null;\n" +
                        "Variable initialization : String query =\n" +
                        "\"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "\"from STUDENTTABLE\";\n" +
                        "try block\n" +
                        "initialize stmt = con.createStatement();\n" +
                        "initialize ResultSet rs = stmt.executeQuery(query);\n" +
                        "while (rs.next()) start\n" +
                        " initialize : String studentId = rs.getString(\"STUDENTID\");\n" +
                        " initialize : String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        " initialize : String lastName = rs.getString(\"LASTNAME\");\n" +
                        " print (studentId + \"\\t\" + firstName + \"\\t\" + lastName)\n" +
                        "end while\n" +
                        "catch SQLException e \n" +
                        "Print stack trace for exception;\n" +
                        "} finally block\n" +
                        "check if (stmt != null) { stmt.close(); }\n" +
                        "end finally block\n" +
                        "End function";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "public static void updateTable(Connection con, String userName) throws SQLException {\n" +
                        " Statement stmt = null;\n" +
                        " String query =\n" +
                        "  \"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "  \"from STUDENTTABLE\";\n" +
                        " try {\n" +
                        "  stmt = con.createStatement();\n" +
                        "  ResultSet rs = stmt.executeQuery(query);\n" +
                        "  while (rs.next()) {\n" +
                        "      String studentId = rs.getString(\"STUDENTID\");\n" +
                        "      String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        "      String lastName = rs.getString(\"LASTNAME\");\n" +
                        "      System.out.println(studentId + \"\\t\" + firstName + \"\\t\" + lastName);\n" +
                        "      rs.updateString(\"FIRSTNAME\", userName);\n" +
                        "      rs.updateRow();\n" +
                        "  }\n" +
                        " } catch (SQLException e ) {\n" +
                        "  e.print;\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function definition updateTable(Connection con, String userName) throws SQLException {\n" +
                        "Variable declaration : Statement stmt = null;\n" +
                        "Variable initialization : String query =\n" +
                        "\"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "\"from STUDENTTABLE\";\n" +
                        "try block\n" +
                        "initialize stmt = con.createStatement();\n" +
                        "initialize ResultSet rs = stmt.executeQuery(query);\n" +
                        "while (rs.next()) start\n" +
                        " initialize : String studentId = rs.getString(\"STUDENTID\");\n" +
                        " initialize : String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        " initialize : String lastName = rs.getString(\"LASTNAME\");\n" +
                        " print (studentId + \"\\t\" + firstName + \"\\t\" + lastName)\n" +
                        " Update field in row to : rs.updateString(\"FIRSTNAME\", userName)\n" +
                        " Update row rs.updateRow()\n" +
                        "end while\n" +
                        "catch SQLException e \n" +
                        "Print stack trace for exception;\n" +
                        "} finally block\n" +
                        "check if (stmt != null) { stmt.close(); }\n" +
                        "end finally block\n" +
                        "End function";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "public static void deleteRow(Connection con, String studentID) throws SQLException {\n" +
                        " Statement stmt = null;\n" +
                        " String query =\n" +
                        "  \"Delete from StudentTable where STUDENTID = \" + studentId\" + \";\"\n" +
                        " try {\n" +
                        "  stmt = con.createStatement();\n" +
                        "  ResultSet rs = stmt.executeUpdate(query);\n" +
                        "  while (rs.next()) {\n" +
                        "      String studentId = rs.getString(\"STUDENTID\");\n" +
                        "      String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        "      String lastName = rs.getString(\"LASTNAME\");\n" +
                        "      System.out.println(studentId + \"\\t\" + firstName + \"\\t\" + lastName);\n" +
                        "  }\n" +
                        " } catch (SQLException e ) {\n" +
                        "  e.print;\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function definition deleteRow(Connection con, String studentID) throws SQLException {\n" +
                        "Variable declaration : Statement stmt = null;\n" +
                        "Variable initialization : String query =\n" +
                        "Delete from StudentTable where STUDENTID = studentId\"\n" +
                        "try block\n" +
                        "initialize stmt = con.createStatement();\n" +
                        "initialize ResultSet rs = stmt.executeUpdate(query);\n" +
                        "while (rs.next()) start\n" +
                        " initialize : String studentId = rs.getString(\"STUDENTID\");\n" +
                        " initialize : String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        " initialize : String lastName = rs.getString(\"LASTNAME\");\n" +
                        " print (studentId + \"\\t\" + firstName + \"\\t\" + lastName)\n" +
                        "end while\n" +
                        "catch SQLException e \n" +
                        "Print stack trace for exception;\n" +
                        "} finally block\n" +
                        "check if (stmt != null) { stmt.close(); }\n" +
                        "end finally block\n" +
                        "End function";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "public static void viewRowForId(Connection con, String studentId) throws SQLException {\n" +
                        " Statement stmt = null;\n" +
                        " String query =\n" +
                        "  \"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "  \"from STUDENTTABLE where STUDENTID = \"+studentId+\"\";\n" +
                        " try {\n" +
                        "  stmt = con.createStatement();\n" +
                        "  ResultSet rs = stmt.executeQuery(query);\n" +
                        "  while (rs.next()) {\n" +
                        "      String studentId = rs.getString(\"STUDENTID\");\n" +
                        "      String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        "      String lastName = rs.getString(\"LASTNAME\");\n" +
                        "      System.out.println(studentId + \"\\t\" + firstName + \"\\t\" + lastName);\n" +
                        "  }\n" +
                        " } catch (SQLException e ) {\n" +
                        "  e.print;\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function definition viewRowForId(Connection con, String studentId) throws SQLException {\n" +
                        "Variable declaration : Statement stmt = null;\n" +
                        "Variable initialization : String query =\n" +
                        "\"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "\"from STUDENTTABLE where STUDENTID = \"+studentId+\"\"\n" +
                        "try block\n" +
                        "initialize stmt = con.createStatement();\n" +
                        "initialize ResultSet rs = stmt.executeQuery(query);\n" +
                        "while (rs.next()) start\n" +
                        " initialize : String studentId = rs.getString(\"STUDENTID\");\n" +
                        " initialize : String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        " initialize : String lastName = rs.getString(\"LASTNAME\");\n" +
                        " print (studentId + \"\\t\" + firstName + \"\\t\" + lastName)\n" +
                        "end while\n" +
                        "catch SQLException e \n" +
                        "Print stack trace for exception;\n" +
                        "} finally block\n" +
                        "check if (stmt != null) { stmt.close(); }\n" +
                        "end finally block\n" +
                        "End function";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "public static void viewSimilarRowForId(Connection con, String studentId) throws SQLException {\n" +
                        " Statement stmt = null;\n" +
                        " String query =\n" +
                        "  \"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "  \"from STUDENTTABLE where STUDENTID LIKE '% \"+studentId+\" %' \";\n" +
                        " try {\n" +
                        "  stmt = con.createStatement();\n" +
                        "  ResultSet rs = stmt.executeQuery(query);\n" +
                        "  while (rs.next()) {\n" +
                        "      String studentId = rs.getString(\"STUDENTID\");\n" +
                        "      String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        "      String lastName = rs.getString(\"LASTNAME\");\n" +
                        "      System.out.println(studentId + \"\\t\" + firstName + \"\\t\" + lastName);\n" +
                        "  }\n" +
                        " } catch (SQLException e ) {\n" +
                        "  e.print;\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";
        programExplanation =
                "Function definition viewSortedRows(Connection con, String studentId) throws SQLException {\n" +
                        "Variable declaration : Statement stmt = null;\n" +
                        "Variable initialization : String query =\n" +
                        "\"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "from STUDENTTABLE where STUDENTID LIKE '%studentId%'\n" +
                        "try block\n" +
                        "initialize stmt = con.createStatement();\n" +
                        "initialize ResultSet rs = stmt.executeQuery(query);\n" +
                        "while (rs.next()) start\n" +
                        " initialize : String studentId = rs.getString(\"STUDENTID\");\n" +
                        " initialize : String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        " initialize : String lastName = rs.getString(\"LASTNAME\");\n" +
                        " print (studentId + \"\\t\" + firstName + \"\\t\" + lastName)\n" +
                        "end while\n" +
                        "catch SQLException e \n" +
                        "Print stack trace for exception;\n" +
                        "} finally block\n" +
                        "check if (stmt != null) { stmt.close(); }\n" +
                        "end finally block\n" +
                        "End function";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }

        programCode =
                "public static void viewSortedRows(Connection con, String sortOrder) throws SQLException {\n" +
                        " Statement stmt = null;\n" +
                        " String query =\n" +
                        "  \"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "  \"from STUDENTTABLE order by STUDENTID \"+sortOrder+\";//ASC :Ascending / DESC : descending\n" +
                        " try {\n" +
                        "  stmt = con.createStatement();\n" +
                        "  ResultSet rs = stmt.executeQuery(query);\n" +
                        "  while (rs.next()) {\n" +
                        "      String studentId = rs.getString(\"STUDENTID\");\n" +
                        "      String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        "      String lastName = rs.getString(\"LASTNAME\");\n" +
                        "      System.out.println(studentId + \"\\t\" + firstName + \"\\t\" + lastName);\n" +
                        "  }\n" +
                        " } catch (SQLException e ) {\n" +
                        "  e.print;\n" +
                        " } finally {\n" +
                        "  if (stmt != null) { stmt.close(); }\n" +
                        " }\n" +
                        "}";

        programExplanation =
                "Function definition viewSortedRows(Connection con, String sortOrder) throws SQLException {\n" +
                        "Variable declaration : Statement stmt = null;\n" +
                        "Variable initialization : String query =\n" +
                        "\"Select STUDENTID, FIRSTNAME, LASTNAME \" +\n" +
                        "from STUDENTTABLE order by STUDENTID sortOrder\n" +
                        "try block\n" +
                        "initialize stmt = con.createStatement();\n" +
                        "initialize ResultSet rs = stmt.executeQuery(query);\n" +
                        "while (rs.next()) start\n" +
                        " initialize : String studentId = rs.getString(\"STUDENTID\");\n" +
                        " initialize : String firstName = rs.getString(\"FIRSTNAME\");\n" +
                        " initialize : String lastName = rs.getString(\"LASTNAME\");\n" +
                        " print (studentId + \"\\t\" + firstName + \"\\t\" + lastName)\n" +
                        "end while\n" +
                        "catch SQLException e \n" +
                        "Print stack trace for exception;\n" +
                        "} finally block\n" +
                        "check if (stmt != null) { stmt.close(); }\n" +
                        "end finally block\n" +
                        "End function";
        programLines = AuxilaryUtils.splitProgramIntolines(programCode);
        programExplanations = AuxilaryUtils.splitProgramIntolines(programExplanation);
        programIndex = ++programIndex;
        for (int i = 0; i < programLines.size(); i++) {
            firebaseDatabaseHandler.writeProgramTable(
                    new ProgramTable(
                            programIndex,
                            i + 1,
                            "sql",
                            programLines.get(i),
                            programExplanations.get(i)));
        }
    }
}
