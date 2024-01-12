package foo.bar.baz;

public class LibCpp{
    static {
        System.loadLibrary("strcpp");
    }

    public native String readStr();
    public native void writeStr(String str);
    public native boolean cmpInternalStr(String str1, String str2);
}
