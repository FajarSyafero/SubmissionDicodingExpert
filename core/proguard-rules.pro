# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

-keep class com.coco.core.** {*;}
-dontwarn com.coco.core.**
-keepattributes Exceptions, Signature, InnerClasses


-keep class com.coco.core.data.source.remote.network.ApiResponse.** {*;}

-dontobfuscate
-keepattributes *Annotation*
-keepclassmembers class **.R$* {
   public static <fields>;
}


-keepclassmembernames class com.coco.core.data.source.remote.response.GetMovieResponse{
  <fields>;
  <methods>;
}

-keepclassmembernames class com.coco.core.data.source.remote.response.Dates{
  <fields>;
  <methods>;
}
-keepclassmembernames class com.coco.core.data.source.remote.response.ResultsItem{
  <fields>;
  <methods>;
}

-keepclassmembernames class com.coco.core.data.source.remote.network.ApiResponse{
  <fields>;
  <methods>;
}
# Keep sealed classes and their subtypes
-keep class com.coco.core.** { *; }
-keep class kotlin.Metadata { *; }
-keepattributes *Annotation*

# Keep specific methods and fields that are necessary
-keepclassmembers class com.coco.core.** {
    *;
}