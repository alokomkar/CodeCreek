package com.sortedqueue.programmercreek.v2.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Chapter(val chapterId: String,
                   val chapterTitle: String,
                   val moduleTitle: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(chapterId)
        writeString(chapterTitle)
        writeString(moduleTitle)
    }

    companion object {
        fun getAllChapters(): List<Chapter> {

            val titles = ArrayList<String>()
            titles.add("Getting Started — An introduction to Java technology and lessons on installing Java development software and using it to create a simple program.")
            titles.add("Learning the Java Language — Lessons describing the essential concepts and features of the Java Programming Language.")
            titles.add("Essential Java Classes — Lessons on exceptions, basic input/output, concurrency, regular expressions, and the platform environment.")
            titles.add("Collections — Lessons on using and extending the Java Collections Framework.")

            val chaptersList = ArrayList<Chapter>()

            var chapterTitle = "Introduction to Java"
            chaptersList.add(Chapter("", chapterTitle, "The Java Programming Language"))
            chaptersList.add(Chapter("", chapterTitle, "The Java Platform"))
            chaptersList.add(Chapter("", chapterTitle, "What can Java do?"))
            chaptersList.add(Chapter("", chapterTitle, "Advantages"))
            chaptersList.add(Chapter("", chapterTitle, "Hello World!!"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Getting started"))


            chapterTitle = "Object-Oriented Programming Concepts"
            chaptersList.add(Chapter("", chapterTitle, "What Is an Object?"))
            chaptersList.add(Chapter("", chapterTitle, "What Is a Class?"))
            chaptersList.add(Chapter("", chapterTitle, "What Is Inheritance?"))
            chaptersList.add(Chapter("", chapterTitle, "What Is an Interface?"))
            chaptersList.add(Chapter("", chapterTitle, "What Is a Package?"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Object-Oriented Programming Concepts"))

            chapterTitle = "Language Basics"
            chaptersList.add(Chapter("", chapterTitle, "Variables"))
            chaptersList.add(Chapter("", chapterTitle, "Primitive Data Types"))
            chaptersList.add(Chapter("", chapterTitle, "Arrays"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Variables"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Variables"))

            chapterTitle = "Operators"
            chaptersList.add(Chapter("", chapterTitle, "Assignment, Arithmetic, and Unary Operators"))
            chaptersList.add(Chapter("", chapterTitle, "Equality, Relational, and Conditional Operators"))
            chaptersList.add(Chapter("", chapterTitle, "Bitwise and Bit Shift Operators"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Operators"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Operators"))
            chaptersList.add(Chapter("", chapterTitle, "Expressions, Statements, and Blocks"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Expressions, Statements, and Blocks"))

            chapterTitle = "Control Flow Statements"
            chaptersList.add(Chapter("", chapterTitle, "The if-then and if-then-else Statements"))
            chaptersList.add(Chapter("", chapterTitle, "The switch Statement"))
            chaptersList.add(Chapter("", chapterTitle, "The while and do-while Statements"))
            chaptersList.add(Chapter("", chapterTitle, "The for Statement"))
            chaptersList.add(Chapter("", chapterTitle, "Branching Statements"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Control Flow Statements"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Control Flow Statements"))

            chapterTitle = "Classes and Objects"
            chaptersList.add(Chapter("", chapterTitle, "Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Declaring Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Declaring Member Variables"))
            chaptersList.add(Chapter("", chapterTitle, "Defining Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Providing Constructors for Your Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Passing Information to a Method or a Constructor"))
            chaptersList.add(Chapter("", chapterTitle, "Objects"))
            chaptersList.add(Chapter("", chapterTitle, "Creating Objects"))
            chaptersList.add(Chapter("", chapterTitle, "Using Objects"))
            chaptersList.add(Chapter("", chapterTitle, "More on Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Returning a Value from a Method"))
            chaptersList.add(Chapter("", chapterTitle, "Using the this Keyword"))
            chaptersList.add(Chapter("", chapterTitle, "Controlling Access to Members of a Class"))
            chaptersList.add(Chapter("", chapterTitle, "Understanding Class Members"))
            chaptersList.add(Chapter("", chapterTitle, "Initializing Fields"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Creating and Using Classes and Objects"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Objects"))

            chapterTitle = "Nested Classes"
            chaptersList.add(Chapter("", chapterTitle, "Inner Class Example"))
            chaptersList.add(Chapter("", chapterTitle, "Local Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Anonymous Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Lambda Expressions"))
            chaptersList.add(Chapter("", chapterTitle, "Method References"))
            chaptersList.add(Chapter("", chapterTitle, "When to Use Nested Classes, Local Classes, Anonymous Classes, and Lambda Expressions"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Nested Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Enum Types"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Enum Types"))


            chapterTitle = "Annotations"
            chaptersList.add(Chapter("", chapterTitle, "Annotations Basics"))
            chaptersList.add(Chapter("", chapterTitle, "Declaring an Annotation Type"))
            chaptersList.add(Chapter("", chapterTitle, "Predefined Annotation Types"))
            chaptersList.add(Chapter("", chapterTitle, "Type Annotations and Pluggable Type Systems"))
            chaptersList.add(Chapter("", chapterTitle, "Repeating Annotations"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Annotations"))
            chaptersList.add(Chapter("", chapterTitle, "Interfaces and Inheritance"))
            chaptersList.add(Chapter("", chapterTitle, "Interfaces"))
            chaptersList.add(Chapter("", chapterTitle, "Defining an Interface"))
            chaptersList.add(Chapter("", chapterTitle, "Implementing an Interface"))
            chaptersList.add(Chapter("", chapterTitle, "Using an Interface as a Type"))
            chaptersList.add(Chapter("", chapterTitle, "Evolving Interfaces"))
            chaptersList.add(Chapter("", chapterTitle, "Default Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Interfaces"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Interfaces"))


            chapterTitle = "Inheritance"
            chaptersList.add(Chapter("", chapterTitle, "Multiple Inheritance of State, Implementation, and Type"))
            chaptersList.add(Chapter("", chapterTitle, "Overriding and Hiding Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Polymorphism"))
            chaptersList.add(Chapter("", chapterTitle, "Hiding Fields"))
            chaptersList.add(Chapter("", chapterTitle, "Using the Keyword super"))
            chaptersList.add(Chapter("", chapterTitle, "Object as a Superclass"))
            chaptersList.add(Chapter("", chapterTitle, "Writing Final Classes and Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Abstract Methods and Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Inheritance"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Inheritance"))

            chapterTitle = "Numbers and Strings"
            chaptersList.add(Chapter("", chapterTitle, "Numbers"))
            chaptersList.add(Chapter("", chapterTitle, "The Numbers Classes"))
            chaptersList.add(Chapter("", chapterTitle, "Formatting Numeric Print Output"))
            chaptersList.add(Chapter("", chapterTitle, "Beyond Basic Arithmetic"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Numbers"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Numbers"))

            chapterTitle = "Characters"
            chaptersList.add(Chapter("", chapterTitle, "Strings"))
            chaptersList.add(Chapter("", chapterTitle, "Converting Between Numbers and Strings"))
            chaptersList.add(Chapter("", chapterTitle, "Manipulating Characters in a String"))
            chaptersList.add(Chapter("", chapterTitle, "Comparing Strings and Portions of Strings"))
            chaptersList.add(Chapter("", chapterTitle, "The StringBuilder Class"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Characters and Strings"))
            chaptersList.add(Chapter("", chapterTitle, "Autoboxing and Unboxing"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Characters and Strings"))

            chapterTitle = "Generics"
            chaptersList.add(Chapter("", chapterTitle, "Why Use Generics?"))
            chaptersList.add(Chapter("", chapterTitle, "Generic Types"))
            chaptersList.add(Chapter("", chapterTitle, "Raw Types"))
            chaptersList.add(Chapter("", chapterTitle, "Generic Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Bounded Type Parameters"))
            chaptersList.add(Chapter("", chapterTitle, "Generic Methods and Bounded Type Parameters"))
            chaptersList.add(Chapter("", chapterTitle, "Generics, Inheritance, and Subtypes"))
            chaptersList.add(Chapter("", chapterTitle, "Type Inference"))
            chaptersList.add(Chapter("", chapterTitle, "Wildcards"))
            chaptersList.add(Chapter("", chapterTitle, "Upper Bounded Wildcards"))
            chaptersList.add(Chapter("", chapterTitle, "Unbounded Wildcards"))
            chaptersList.add(Chapter("", chapterTitle, "Lower Bounded Wildcards"))
            chaptersList.add(Chapter("", chapterTitle, "Wildcards and Subtyping"))
            chaptersList.add(Chapter("", chapterTitle, "Wildcard Capture and Helper Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Guidelines for Wildcard Use"))
            chaptersList.add(Chapter("", chapterTitle, "Type Erasure"))
            chaptersList.add(Chapter("", chapterTitle, "Erasure of Generic Types"))
            chaptersList.add(Chapter("", chapterTitle, "Erasure of Generic Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Effects of Type Erasure and Bridge Methods"))
            chaptersList.add(Chapter("", chapterTitle, "Non-Reifiable Types"))
            chaptersList.add(Chapter("", chapterTitle, "Restrictions on Generics"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Generics"))

            chapterTitle = "Packages"
            chaptersList.add(Chapter("", chapterTitle, "Creating and Using Packages"))
            chaptersList.add(Chapter("", chapterTitle, "Creating a Package"))
            chaptersList.add(Chapter("", chapterTitle, "Naming a Package"))
            chaptersList.add(Chapter("", chapterTitle, "Using Package Members"))
            chaptersList.add(Chapter("", chapterTitle, "Managing Source and Class Files"))
            chaptersList.add(Chapter("", chapterTitle, "Summary of Creating and Using Packages"))
            chaptersList.add(Chapter("", chapterTitle, "Questions and Exercises: Creating and Using Packages"))

            return chaptersList

        }

        @JvmField
        val CREATOR: Parcelable.Creator<Chapter> = object : Parcelable.Creator<Chapter> {
            override fun createFromParcel(source: Parcel): Chapter = Chapter(source)
            override fun newArray(size: Int): Array<Chapter?> = arrayOfNulls(size)
        }
    }
}