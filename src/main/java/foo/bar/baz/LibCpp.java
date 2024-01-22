package foo.bar.baz;

import com.sun.jna.Library;

public interface LibCpp extends Library {

    public String readStr();
    public void writeStr(String str);
    public boolean cmpInternalStr(String str1, String str2);
}
