package com.sortedqueue.programmercreek.v2.ui.module

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sortedqueue.programmercreek.R

import com.sortedqueue.programmercreek.v2.data.helper.SimpleContent
import com.sortedqueue.programmercreek.v2.data.model.Chapter
import com.sortedqueue.programmercreek.v2.ui.chapters.SubModulesAdapter
import kotlinx.android.synthetic.main.fragment_new_module.*

import com.sortedqueue.programmercreek.util.AnimationUtils
import com.sortedqueue.programmercreek.v2.base.*


class ModuleFragment : BaseFragment(), BaseAdapterClickListener<SimpleContent> {

    override fun onItemClick(position: Int, item: SimpleContent) {

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View?
            = inflater.inflate(R.layout.fragment_new_module, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chapter = arguments!!.getParcelable<Chapter>(Chapter::class.java.simpleName)
        tvHeader.text = chapter.moduleTitle

        rvModuleContent.layoutManager = LinearLayoutManager(context)
        rvTracker.layoutManager = LinearLayoutManager(context)

        navigateToContent( arguments!!.getInt(ModuleActivity.modulePosition) )

        ivNavigation.setOnClickListener { rvTracker.show() }

        val chaptersList = arguments!!.getParcelableArrayList<Chapter>(ModuleActivity.chaptersListExtra)
        rvTracker.adapter = SubModulesAdapter( chaptersList, -1, object : BaseAdapterClickListener<Chapter> {
            override fun onItemClick(position: Int, item: Chapter) {
                tvHeader.text = item.moduleTitle
                navigateToContent( position )
                AnimationUtils.slideOutToLeft(rvTracker)
            }
        })

        moduleProgressBar.max = chaptersList.size

    }

    private fun navigateToContent(moduleId: Int) {
        questionContainer.hide()
        when(  moduleId ) {
            0 -> getFirstContent()
            1 -> getSecondContent()
            2 -> getThirdContent()
            3 -> getFourthContent()
            4 -> getFifthContent()
            5 -> getSixthContent()
        }
        moduleProgressBar.progress = moduleId + 1
    }

    private fun getSixthContent() {
        val simpleContentList = ArrayList<SimpleContent>()
        simpleContentList.add(SimpleContent("",
                "Qn. Choose all applicable : Java is ?" +
                        "Object oriented|||" +
                        "Distributed|||" +
                        "Multithreaded|||" +
                        "Architecture neutral|||" +
                        "Not dynamic",
                SimpleContent.mcq,
                "Object oriented|||" +
                        "Distributed|||" +
                        "Multithreaded|||" +
                        "Architecture neutral"))
        simpleContentList.add(SimpleContent("",
                "Qn. What's this comment type?/**\n" +
                        " * The HelloWorldApp class implements an application that\n" +
                        " * simply displays \"Hello World!\" to the standard output.\n" +
                        " */\n??" +
                        "Single line comment|||" +
                        "Multi line comment|||" +
                        "Not a comment",
                SimpleContent.codeMcq,
                "Multi line comment"))


        simpleContentList.add(SimpleContent("",
                "Qn. Rearrange in the right order?" +
                        "class HelloWorldApp {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello World!\"); //Display the string.\n" +
                        "    }\n" +
                        "}",
                SimpleContent.rearrange))

        simpleContentList.add(SimpleContent("",
                "The <API> is a large collection of ready-made software " +
                        "components that provide many useful capabilities. " +
                        "It is grouped into libraries of related <classes and interfaces>; these libraries are known as <packages>.",
                SimpleContent.fillBlanks))

        rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )

        questionContainer.show()
        val fragmentTransaction = childFragmentManager.beginTransaction()
        var pagerFragment = childFragmentManager.findFragmentByTag(PagerFragment::class.java.simpleName) as PagerFragment?
        if (pagerFragment == null) {
            pagerFragment = PagerFragment()
        }
        val bundle = Bundle()
        bundle.putParcelableArrayList(SimpleContent::class.java.simpleName, simpleContentList)
        pagerFragment.arguments = bundle
        //AnimationUtils.enterReveal(checkFAB);
        fragmentTransaction!!.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        fragmentTransaction.replace(R.id.questionContainer, pagerFragment, PagerFragment::class.java.simpleName)
        fragmentTransaction.commit()
    }

    private fun getFifthContent() {
        val simpleContentList = ArrayList<SimpleContent>()
        simpleContentList.add(SimpleContent("",
                "A Closer Look at the \"Hello World!\" Application",
                SimpleContent.header))
        simpleContentList.add(SimpleContent("",
                "/**\n" +
                        " * The HelloWorldApp class implements an application that\n" +
                        " * simply displays \"Hello World!\" to the standard output.\n" +
                        " */\n" +
                        "class HelloWorldApp {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello World!\"); //Display the string.\n" +
                        "    }\n" +
                        "}",
                SimpleContent.code))
        simpleContentList.add(SimpleContent("",
                "The \"Hello World!\" application consists of three primary components: " +
                        "\n\nSource code comments " +
                        "\n\nThe HelloWorldApp class definition " +
                        "\n\nThe main method",
                SimpleContent.content))

        simpleContentList.add(SimpleContent("", "Comments are ignored by the compiler but are useful to other programmers. The Java programming language supports three kinds of comments:", SimpleContent.header))

        simpleContentList.add(SimpleContent("", "/* text */", SimpleContent.code))
        simpleContentList.add(SimpleContent("", "The compiler ignores everything from /* to */.", SimpleContent.bullets))
        simpleContentList.add(SimpleContent("", "/** documentation */", SimpleContent.code))
        simpleContentList.add(SimpleContent("", "This indicates a documentation comment (doc comment, for short). The compiler ignores this kind of comment, just like it ignores comments that use /* and */. The javadoc tool uses doc comments when preparing automatically generated documentation.", SimpleContent.bullets))
        simpleContentList.add(SimpleContent("", "// text", SimpleContent.code))
        simpleContentList.add(SimpleContent("", "The compiler ignores everything from // to the end of the line.", SimpleContent.bullets))


        rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )
    }

    private fun getFourthContent() {
        val simpleContentList = ArrayList<SimpleContent>()
        simpleContentList.add(SimpleContent("", "We can't promise you fame, fortune, or even a job if you learn the Java programming language. Still, it is likely to make your programs better and requires less effort than other languages. We believe that Java technology will help you do the following:", SimpleContent.header ))
        simpleContentList.add(SimpleContent("",
                "Get started quickly: Although the Java programming language is a powerful object-oriented language, it's easy to learn, especially for programmers already familiar with C or C++.\n" +
                        "Write less code: Comparisons of program metrics (class counts, method counts, and so on) suggest that a program written in the Java programming language can be four times smaller than the same program written in C++.\n" +
                        "Write better code: The Java programming language encourages good coding practices, and automatic garbage collection helps you avoid memory leaks. Its object orientation, its JavaBeans™ component architecture, and its wide-ranging, easily extendible API let you reuse existing, tested code and introduce fewer bugs.\n" +
                        "Develop programs more quickly: The Java programming language is simpler than C++, and as such, your development time could be up to twice as fast when writing in it. Your programs will also require fewer lines of code.\n" +
                        "Avoid platform dependencies: You can keep your program portable by avoiding the use of libraries written in other languages.\n" +
                        "Write once, run anywhere: Because applications written in the Java programming language are compiled into machine-independent bytecodes, they run consistently on any Java platform.\n" +
                        "Distribute software more easily: With Java Web Start software, users will be able to launch your applications with a single click of the mouse. An automatic version check at startup ensures that users are always up to date with the latest version of your software. If an update is available, the Java Web Start software will automatically update their installation.", SimpleContent.content ))

        rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )
    }

    private fun getThirdContent() {
        val simpleContentList = ArrayList<SimpleContent>()
        simpleContentList.add(SimpleContent("", "The general-purpose, high-level Java programming language is a powerful software platform. Every full implementation of the Java platform gives you the following features:", SimpleContent.header ))
        simpleContentList.add(SimpleContent("", "The Java platform has two components:\n" +
                "\n" +
                "The Java Virtual Machine\n" +
                "The Java Application Programming Interface (API)", SimpleContent.bullets ))
        simpleContentList.add(SimpleContent("",
                "Development Tools: The development tools provide everything you'll need for compiling, running, monitoring, debugging, and documenting your applications. As a new developer, the main tools you'll be using are the javac compiler, the java launcher, and the javadoc documentation tool.\n" +
                        "\n" +
                        "Application Programming Interface (API): The API provides the core functionality of the Java programming language. It offers a wide array of useful classes ready for use in your own applications. It spans everything from basic objects, to networking and security, to XML generation and database access, and more.\n" +
                        "Deployment Technologies: The JDK software provides standard mechanisms such as the Java Web Start software and Java Plug-In software for deploying your applications to end users.\n" +
                        "\n" +
                        "User Interface Toolkits: The JavaFX, Swing, and Java 2D toolkits make it possible to create sophisticated Graphical User Interfaces (GUIs).\n" +
                        "\n" +
                        "Integration Libraries: Integration libraries such as the Java IDL API, JDBC API, Java Naming and Directory Interface (JNDI) API, Java RMI, and Java Remote Method Invocation over Internet Inter-ORB Protocol Technology (Java RMI-IIOP Technology) enable database access and manipulation of remote objects."
                , SimpleContent.content))


        rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )
    }

    private fun getSecondContent() {

        val simpleContentList = ArrayList<SimpleContent>()
        simpleContentList.add(SimpleContent("", "A platform is the hardware or software environment in which a program runs. We've already mentioned some of the most popular platforms like Microsoft Windows, Linux, Solaris OS, and Mac OS. Most platforms can be described as a combination of the operating system and underlying hardware. The Java platform differs from most other platforms in that it's a software-only platform that runs on top of other hardware-based platforms.", SimpleContent.content ))
        simpleContentList.add(SimpleContent("", "The Java platform has two components:\n" +
                "\n" +
                "The Java Virtual Machine\n" +
                "The Java Application Programming Interface (API)", SimpleContent.bullets ))
        simpleContentList.add(SimpleContent("",
                "The API is a large collection of ready-made software components that provide many useful capabilities. It is grouped into libraries of related classes and interfaces; these libraries are known as packages."
                , SimpleContent.content))

        simpleContentList.add(SimpleContent("",
                "https://docs.oracle.com/javase/tutorial/figures/getStarted/getStarted-jvm.gif",
                SimpleContent.image))

        simpleContentList.add(SimpleContent("",
                "As a platform-independent environment, the Java platform can be a bit slower than native code. However, advances in compiler and virtual machine technologies are bringing performance close to that of native code without threatening portability.\n" +
                        "\n" +
                        "The terms\"Java Virtual Machine\" and \"JVM\" mean a Virtual Machine for the Java platform.",
                SimpleContent.bullets))


        rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )
    }

    private fun getFirstContent() {
        val simpleContentList = ArrayList<SimpleContent>()
        simpleContentList.add(SimpleContent("", "The Java programming language is a high-level language that can be characterized by all of the following buzzwords:", SimpleContent.header ))
        simpleContentList.add(SimpleContent("",
                "Simple\n" +
                "Object oriented\n" +
                "Distributed\n" +
                "Multithreaded\n" +
                "Dynamic\n" +
                "Architecture neutral\n" +
                "Portable\n" +
                "High performance\n" +
                "Robust\n" +
                "Secure", SimpleContent.bullets ))
        simpleContentList.add(SimpleContent("",
                "In the Java programming language, all source code is first written in plain text files ending with the .java extension. Those source files are then compiled into .class files by the javac compiler. A .class file does not contain code that is native to your processor; it instead contains bytecodes — the machine language of the Java Virtual Machine1 (Java VM). The java launcher tool then runs your application with an instance of the Java Virtual Machine."
                , SimpleContent.content))

        simpleContentList.add(SimpleContent("",
                "https://docs.oracle.com/javase/tutorial/figures/getStarted/getStarted-compiler.gif",
                SimpleContent.image))

        simpleContentList.add(SimpleContent("",
                "Because the Java VM is available on many different operating systems, the same .class files are capable of running on Microsoft Windows, the Solaris™ Operating System (Solaris OS), Linux, or Mac OS. Some virtual machines, such as the Java SE HotSpot at a Glance, perform additional steps at runtime to give your application a performance boost. This includes various tasks such as finding performance bottlenecks and recompiling (to native code) frequently used sections of code.",
                SimpleContent.content))

        simpleContentList.add(SimpleContent("",
                "https://docs.oracle.com/javase/tutorial/figures/getStarted/helloWorld.gif",
                SimpleContent.image))


        rvModuleContent.adapter = SimpleContentAdapter( simpleContentList, this )
    }

}