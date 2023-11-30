package homework;

import java.io.*;
import java.util.ArrayList;

public class Cipher {
    //创建三个变量
    String plaintext, key, ciphertext;

    //构造方法给变量赋值
    public Cipher() {
        plaintext = "202210139075"; //初始明文
        ciphertext = "32566625<457";//初始密文
        key = "123456";             //初始密匙
    }

    //设置密匙
    public void setKey(String key) {
        this.key = key;
    }

    //获得密匙方法
    public String getKey() {
        return key;
    }

    //输入明文方法
    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    //获得明文方法
    public String getPlaintext() {
        return plaintext;
    }

    //输入密文方法
    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    //获得密文方法
    public String getCiphertext() {
        return ciphertext;
    }

    //创建动态数组储存数据
    ArrayList<Character> c1 = new ArrayList<Character>();

    //加密算法
    public String encryption(String temp) {
        String tp = "";                     //创建临时变量tp接受加密后的密文
        for (int i = 0; i < temp.length(); i++) {
            int p = temp.charAt(i) - 0;       //获取明文中每个字符的Ascll码
            if (p >= 32 && p <= 126) {             //若果明文为可见字符
                for (int j = 0; j < key.length(); j++) {
                    p += key.charAt(j) - '0';      //明文Ascll码循环加上密匙加密
                    if (p > 126) {                 //若字符Ascll码大于126(不可见字符)
                        p = p % 126 + 31;
                    }
                }
            }
            c1.add((char) (p));                    //将加密后的Ascll码转成字符加入动态数组
        }
        for (int j = 0; j < c1.size(); j++) {
            tp += String.valueOf(c1.get(j));   //循环动态数组将字符串拼接并传输给临时变量tp
        }
        c1.clear();                                //清空动态数组（因为动态数组多次使用，需要清除遗留的数据，避免影响下一次使用）
        return tp;                                 //返回密文
    }

    //解密算法
    public String decryption(String temp) {
        String tp = "";                          //创建临时变量tp接受解密后的明文
        for (int i = 0; i < temp.length(); i++) {
            int p = temp.charAt(i) - 0;          //获取密文中每个字符的Ascll码
            if (p >= 32 && p <= 126) {                 //若果密文为可见字符
                for (int j = 0; j < key.length(); j++) {//循环密匙进行解密
                    p -= (key.charAt(j) - '0');         //密文字符的Ascll码减去密匙的每个字符的字面量值
                    if (p < 32) {                       //若字符Ascll码小于36(不可见字符)
                        p = p - 31 + 126;               //还原Ascll码值
                    }
                }
            }
            c1.add((char) (p));                      //解密后的Ascll码转成字符加入动态数组
        }
        for (int j = 0; j < c1.size(); j++) {
            tp += String.valueOf(c1.get(j)); //循环动态数组将字符串拼接并传输给临时变量tp
        }
        c1.clear();                                //清空动态数组（因为动态数组多次使用，需要清除遗留的数据，避免影响下一次使用）
        return tp;                                 //返回明文
    }

    //对文件进行加密
    public String encryption1() throws IOException {
        Reader reader = new FileReader("plain.txt");//创建字节输入流，指向plain.txt文件
        StringBuilder temp = new StringBuilder();   //用于储存明文
        int data;
        while ((data=reader.read()) != -1) {
            temp.append((char) data);  //获取文件内容追加到字符串temp
        }
        String str = temp.toString();           //StringBuilder转成String
        reader.close();                         //关闭字节输入流
        String en_str = encryption(str);        //将加密后的内容重新赋值给en_str变量

        Writer writer = new FileWriter("cipher.txt");//加密的内容写到cipher.txt文件
        writer.write(en_str);
        writer.close();
        return en_str;
    }

    //对文件进行解密
    public String decryption1() throws IOException {
        Reader reader = new FileReader("cipher.txt");//创建字节输入流，指向cipher.txt文件
        StringBuilder temp = new StringBuilder();   //用于储存密文
        int data;
        while ((data=reader.read()) != -1) {
            temp.append((char) data);  //获取文件内容追加到字符串temp
        }
        String str = temp.toString();           //StringBuilder转成String
        reader.close();
        String en_str = decryption(str);        //将解密后的内容重新赋值给en_str变量

        Writer writer = new FileWriter("plain.txt");//加密的内容写到plain.txt文件
        writer.write(en_str);
        writer.close();
        return en_str;
    }
}
