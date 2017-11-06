package io;

import org.junit.Test;

import java.io.*;

/**
 * Created by duan2ping on 2017/10/11.
 */
public class Reader {

    // 测试【InputStream.read()】
    @Test
    public void testaInputRead() {
        InputStream is = null;
        try {
            // 指定源
            is = new FileInputStream(new File("d:/aa.txt"));
            // 读取源文件[返回的是字符的ASCII码值，如果没有字符则返回-1]
            int i = is.read();
            while (i != -1) {
                // 因为是ASCII码值所以需要强转成char
                System.out.println((char) i);
                i = is.read();
            }
        }catch (Exception e){
            System.err.println("testRead ERROR");
        }finally {
            // 释放资源
            try {
                if(is != null) is.close();
            } catch (IOException e) {
               System.err.println("CLOSE ERROR");
            }
        }
    }

    // 测试【InputStream.read(byte[])】
    @Test
    public void testInputRead2() {
        InputStream is = null;
        try {
            // 指定源
            is = new FileInputStream(new File("d:/aa.txt"));
            // 创建缓冲字节数组（根据文件大小适当设置缓冲大小）
            byte[] data = new byte[1024];
            // 记录读取数据的字节数
            int i = -1;
            // 每次循环读取1024字节数据
            while ((i = is.read(data)) != -1) {
                // 只打印读取到的数据（固定读取1024，但可能只读到10，所以有用的只有10字节其他的不需要）
                // 并且指定编码
                System.out.println(new String(data,0,i,"gbk"));
            }
        }catch (Exception e){
            System.err.println("testRead2 ERROR");
        }finally {
            // 释放资源
            try {
                if(is != null ) is.close();
            } catch (IOException e) {
                System.err.println("CLOSE ERROR");
            }
        }
    }

    // 测试【BufferedInputStream.read】
    @Test
    public void testBufferedInputRead(){
        BufferedInputStream buf = null;
        try {
            buf = new BufferedInputStream(new FileInputStream(new File("d:/aa.txt")));
        } catch (FileNotFoundException e) {
            System.out.println("testBufferedInputRead ERROR");
        }finally {
            // 释放资源
            try {
                if (buf != null) buf.close();
            } catch (IOException e) {
                System.out.println("CLOSE ERROR");
            }
        }
    }
}
