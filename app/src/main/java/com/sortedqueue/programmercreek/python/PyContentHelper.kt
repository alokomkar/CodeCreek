package com.sortedqueue.programmercreek.python

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Alok Omkar on 2017-11-16.
 */
class PyContentHelper {

    init {
        //https://docs.python.org/3/tutorial/interpreter.html
        writeToFirebase()
    }

    private fun writeToFirebase() {

        var pythonDBReference : DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://creek-55ef6.firebaseio.com/python/pythonIntro" )
        var pushId = pythonDBReference.push().key
        var pyIntro = PyIntroduction(
                pushId!!,
                "Starting with Python",
                "Python is an easy to learn, powerful programming language. It has efficient high-level data structures and a simple but effective approach to object-oriented programming. Python’s elegant syntax and dynamic typing, together with its interpreted nature, make it an ideal language for scripting and rapid application development in many areas on most platforms.\n" +
                        "\n" +
                        "The Python interpreter and the extensive standard library are freely available in source or binary form for all major platforms from the Python Web site, https://www.python.org/, and may be freely distributed. The same site also contains distributions of and pointers to many free third party Python modules, programs and tools, and additional documentation.\n" +
                        "\n" +
                        "The Python interpreter is easily extended with new functions and data types implemented in C or C++ (or other languages callable from C). Python is also suitable as an extension language for customizable applications.",
                "",
                "",
                "",
                "",
                "",
                ""
                )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "Why Python?",
                "If you do much work on computers, eventually you find that there’s some task you’d like to automate. For example, you may wish to perform a search-and-replace over a large number of text files, or rename and rearrange a bunch of photo files in a complicated way. Perhaps you’d like to write a small custom database, or a specialized GUI application, or a simple game.\n" +
                        "\n" +
                        "If you’re a professional software developer, you may have to work with several C/C++/Java libraries but find the usual write/compile/test/re-compile cycle is too slow. Perhaps you’re writing a test suite for such a library and find writing the testing code a tedious task. Or maybe you’ve written a program that could use an extension language, and you don’t want to design and implement a whole new language for your application.\n" +
                        "\n" +
                        "Python is just the language for you.",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "Simple to use",
                "Python is simple to use, but it is a real programming language, offering much more structure and support for large programs than shell scripts or batch files can offer. On the other hand, Python also offers much more error checking than C, and, being a very-high-level language, it has high-level data types built in, such as flexible arrays and dictionaries. Because of its more general data types Python is applicable to a much larger problem domain than Awk or even Perl, yet many things are at least as easy in Python as in those languages.",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "Modular in nature and interpreted",
                "Python allows you to split your program into modules that can be reused in other Python programs. It comes with a large collection of standard modules that you can use as the basis of your programs — or as examples to start learning to program in Python. Some of these modules provide things like file I/O, system calls, sockets, and even interfaces to graphical user interface toolkits like Tk.\n" +
                        "\n" +
                        "Python is an interpreted language, which can save you considerable time during program development because no compilation and linking is necessary. The interpreter can be used interactively, which makes it easy to experiment with features of the language, to write throw-away programs, or to test functions during bottom-up program development. It is also a handy desk calculator.",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "Compact Language",
                "Python enables programs to be written compactly and readably. Programs written in Python are typically much shorter than equivalent C, C++, or Java programs, for several reasons:\n" +
                        "\n" +
                        "a. the high-level data types allow you to express complex operations in a single statement;\n" +
                        "b. statement grouping is done by indentation instead of beginning and ending brackets;\n" +
                        "c. no variable or argument declarations are necessary.\n" +
                        "Python is extensible: if you know how to program in C it is easy to add a new built-in function or module to the interpreter, either to perform critical operations at maximum speed, or to link Python programs to libraries that may only be available in binary form (such as a vendor-specific graphics library). Once you are really hooked, you can link the Python interpreter into an application written in C and use it as an extension or command language for that application.",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "Interpreter configuration on Unix",
                "The Python interpreter is usually installed as /usr/local/bin/python3.6 on those machines where it is available; putting /usr/local/bin in your Unix shell’s search path makes it possible to start it by typing the command:\n" +
                        "\n" +
                        "<b>python3.6</b>\n" +
                        "to the shell. Since the choice of the directory where the interpreter lives is an installation option, other places are possible; check with your local Python guru or system administrator. (E.g., <b>/usr/local/python</b> is a popular alternative location.)",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "Interpreter on Windows",
                "On Windows machines, the Python installation is usually placed in <b>C:\\Python36</b>, though you can change this when you’re running the installer. To add this directory to your path, you can type the following command into the command prompt in a DOS box:\n" +
                        "\n" +
                        "<b>set path=%path%;C:\\python36\n</b>" +
                        "Typing an end-of-file character <b>(Control-D on Unix, Control-Z on Windows)</b> at the primary prompt causes the interpreter to exit with a zero exit status. If that doesn’t work, you can exit the interpreter by typing the following command: <b>quit()</b>.",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pythonDBReference  = FirebaseDatabase.getInstance().getReferenceFromUrl("https://creek-55ef6.firebaseio.com/python/pythonContent" )
        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

        pushId = pythonDBReference.push().key
        pyIntro = PyIntroduction(
                pushId!!,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        )
        pythonDBReference.child(pushId).setValue(pyIntro)

    }
}