package io;

import org.junit.Test;

import java.io.*;

/**
 * Created by duan2ping on 2017/10/10.
 */
public class IOTest {
    public IOTest(){}
    public static void main(String args[]) throws IOException {
        File file = new File("d:/aa.txt");
        if(file.exists()){
            System.out.println("file aa.txt exists");
            if(file.delete()){
                System.out.println("file delete success");
            }else{
                System.out.println("file delete error");
            }
        }else{
            System.out.println("file is not exists");
        }


        FileWriter writer = new FileWriter(file,true);
        writer.write("sbsbsbsb");
        writer.flush();
        writer.close();
        InputStream is = new FileInputStream(file);
        System.out.println(is.read()+"----"+is.available());
    }


    /**
     * 测试断点续传
     * 原理：使用变量记录文件操作的位置（pos）
     *       利用RandomAccsessFile到指定位置进行操作
     */
    @Test
    public void testUpload() {
        File source = new File("d:/aa.txt");       // 源文件
        File target = new File("d:/aaupload.txt");// 目标文件
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(target);
            byte[] data = new byte[1]; //缓冲数组
            // 循环读写
            while (is.read(data) != -1) {
                os.write(data);
                // 当目的文件为3字节时模拟暂停|异常实现断点续传
                if (target.length() == 3) {
                    throw new RuntimeException();
                }
            }

        }catch (RuntimeException e){
            // 实现断点续传
            keepUpload(source,target,3);
        } catch (IOException e) {
            System.err.println("test ERROR");
        }finally {
            // 释放资源
            try {
                if (is != null) is.close();
                if (os != null) os.close();
            }catch (Exception e){
                System.err.println("CLOSE ERROR");
            }
        }
    }

    /**
     * 断点续传简单实现
     * @param source     源文件
     * @param target    目地文件
     * @param pos       断点位置
     */
    public void keepUpload(File source,File target,Integer pos){
        try {
            // 停止10s
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RandomAccessFile readFile = null;
        RandomAccessFile writeFile = null;
        try {
            // 随机访问文件，指定文件和操作（r只读，rw读写）
            readFile = new RandomAccessFile(source, "rw");
            writeFile = new RandomAccessFile(target, "rw");
            // 设置读的起始位置
            readFile.seek(pos);
            // 设置写的起始位置
            writeFile.seek(pos);
            // 数据缓冲区
            byte[] buf = new byte[1];
            // 数据读写
            while (readFile.read(buf) != -1) {
                writeFile.write(buf);
            }
        } catch (IOException e) {
            System.err.println("keepUpload ERROR");
        }finally {
            // 释放资源
            try {
                if(readFile != null) readFile.close();
                if(writeFile != null) writeFile.close();
            }catch (IOException e){
                System.out.println("CLOSE ERROR");
            }
        }
    }

    @Test
    public void test1() throws IOException {
        File file = new File("d:/aa.txt");
        File file2 = new File("d:/aaupload.txt");
        InputStream is = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        is.read(data);
        System.out.println("data1:"+new String(data));
        System.out.println("data2:"+new String(data));
    }

}
