package com.sortedqueue.programmercreek.network;

/*import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;*/

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alok on 13/03/17.
 */

public class RetrofitCreator {

    //http://square.github.io/retrofit/

    private static final String BASE_URL = "http://fa839326.compilers.sphere-engine.com/";
    private static final String TOKEN_COMPILER_API = "faed39ebdab374918efffba2d99bfd86";
    private static final String TOKEN_PROBLEM_API = "e561399f6fc1fd3d18525d8056bc209afe5a66b1";

    @NonNull
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return logging;
    }

    public static <T> T createService(Class<T> service) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = getHttpLoggingInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        // add your other interceptors …
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(service);

    }

    public static <T> T createDownloadService(Class<T> service) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = getHttpLoggingInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        // add your other interceptors …
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(service);

    }

    public static String getTokenCompilerApi() {
        return TOKEN_COMPILER_API;
    }

    public static String getTokenProblemApi() {
        return TOKEN_PROBLEM_API;
    }

    //http://fa839326.compilers.sphere-engine.com/api/v3/languages?access_token=TOKEN_COMPILER_API
    //Get all languages

    //{"7":"Ada (gnat 5.1.1)","13":"Assembler (NASM 2.11.05)","45":"Assembler (gcc 4.9.3)","42":"Assembler 64bit
    // (nasm 2.12.01)","105":"AWK (mawk 1.3.3)","104":"AWK (gawk) (fawk 4.1.1)","28":"Bash (bash 4.3.33)","110":"bc
    // (bc 1.06.95)","12":"Brainf**k (1.0.6)","11":"C (gcc 5.1.1)","27":"C# (Mono 4.0.2)","1":"C++ (5.1.1)","41":
    // "C++ 4.3.2 (gcc-4.3.2)","44":"C++14 (gcc-5 5.1.1)","34":"C99 strict (gcc-5 5.1.1)","14":"CLIPS (clips 6.24)",
    // "111":"Clojure (clojure 1.7.0)","118":"COBOL (1.1.0)","32":"Common Lisp (clisp) (clisk 2.49)","102":"D (dmd 2.072.2)",
    // "20":"D (gdc-5 5.1.1)","36":"Erlang (erl 18)","124":"F# (1.3)","107":"Forth (gforth 0.7.2)","5":"Fortran (5.1.1)",
    // "114":"Go (1.4)","121":"Groovy (2.4)","21":"Haskell (ghc 7.8)","16":"Icon (icon 9.4.3)","9":"Intercal (c-intercal 28.0-r1)",
    // "10":"Java (jdk 8u51)","35":"JavaScript (rhino) (rhino 1.7.7)","112":"JavaScript (spidermonkey) (24.2.0)","26":"Lua (lua 7.2)","30":"Nemerle (ncc 1.2.0)","25":"Nice (0.9.13)","56":"Node.js (v0.12.7)","43":"Objective-C (gcc-5 5.1.1)","8":"Ocaml (4.01.0)","127":"Octave (4.0.0)","22":"Pascal (fpc) (fpc 2.6.4+dfsg-6)","2":"Pascal (gpc) (gpc 20070904)","3":"Perl (perl 5.20.1)","54":"Perl 6 (perl6 2014.07)","29":"PHP (PHP 5.6.11-1)","19":"Pike (pike v7.8)","108":"Prolog (gnu prolog 1.4.5)","15":"Prolog (swi) (swi 7.2)","4":"Python (2.7.10)","99":"Python (Pypy) (PyPy 2.6.0)","116":"Python 3 (Python 3.4.3+)","117":"R (3.2.2)","17":"Ruby (ruby 2.1.5)","39"
    // :"Scala (2.11.7)","18":"Scheme (stalin 0.3)","33":"Scheme (guile) (guile 2.0.11)","46":"Sed (sed 4.2.2)","23":
    // "Smalltalk (gst 3.2.4)","40":"SQL (sqlite3-3.8.7)","85":"Swift (swift 3.0.2)","38":"Tcl (tclsh 8.6)","50":
    // "VB.NET (mono 4.0.2)","6":"Whitespace (wspace 0.3)"}
}
