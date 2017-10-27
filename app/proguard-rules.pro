# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Users\Alok Omkar\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
# Add this to the 'proguard-rules.pro' file
-keep public class * implements co.uk.rushorm.core.Rush { *; }
# Add this global rule
-keepattributes Signature
-keepattributes *Annotation*
# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.sortedqueue.programmercreek.database.** {
  *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}

-keep public class com.google.ads.** {
   public *;
}

#Calligraphy
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Glide progaurd
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#codeview - progaurd
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

#Syntax highlighter java - Not working
#-keep class javax.** {
#   *;
#}

#-keep class java.** {
#   *;
#}

#-dontwarn javax.**
#-dontwarn java.**
-libraryjars /libs/JavaPrettify-1.2.1.jar

# For communication with AdColony's WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#Tappx Proguard
-keepattributes *Annotation*
-keepclassmembers class com.google.**.R$* {
    public static <fields>;
}
-keep public class com.google.ads.** {*;}
-keep public class com.google.android.gms.** {*;}
-keep public class com.tappx.** { *; }
-dontobfuscate

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
#-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

#OKio
-dontwarn okio.**

-keep class com.startapp.** {
      *;
}

#StartApp - Ad service
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**
-keep class com.android.vending.billing.**

-keep class com.appnext.** { *; }
-dontwarn com.appnext.**