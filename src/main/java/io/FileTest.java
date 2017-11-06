package io;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;

/**
 * File类，既可以是文件也可以是目录
 * Created by duan2ping on 2017/10/14.
 */
public class FileTest{
    // File的构建方式
    @Test
    public void testCreate(){
        //==分隔符
        // 磁盘分隔符
        System.out.println("pathSeparator:"+File.pathSeparator);
        // 路径分隔符
        System.out.println("separator:"+File.separator);
        // 绝对路径方式
        File file1 = new File("d:/home");
        // 相对路径方式①
        File parent = new File("d:/home");
        File file2 = new File(parent,"ubuntu");
        // 相对路径②
        File file3 = new File("d:/home","ubuntu");
    }

    // File的基本操作
    @Test
    public void testExcute(){
        // 如果不指明根目录，则默认当前项目根目录
        File file = new File("d:\\home\\a.txt");
        // 验证文件是否存在
        System.out.println("是否存在:"+file.exists());
        // 判断是文件还是目录
        System.out.println("是文件:"+file.isFile()+"||是目录:"+file.isDirectory());
        // 获得文件或目录名称
        System.out.println("文件名:"+file.getName());
        // 获得文件或目录绝对路径
        System.out.println("文件绝对路径:"+file.getAbsolutePath());
        // 获得文件或目录相对路径(File(path),获得的是path)
        System.out.println("相对路径："+file.getPath());
        // 获得父目录名称
        System.out.println("获得父目录名称："+file.getParent());
        // 获得文件或目录大小（byte,只计算文件大小，目录则返回0）
        System.out.println("文件大小"+file.length());
        // 获得文件的权限
        System.out.println("可读："+file.canRead()+"||可写："+file.canWrite());
    }

    // File的增删改查
    @Test
    public void testCRUD() throws IOException {
        File file = new File("d:/home/a.txt");
        // 【创建目录】：会创建a.txt目录（不是文件）
        // 创建目录（前提是home必须存在，否则创建失败）
        file.mkdir();// 返回boolean：true成功 false失败
        // 创建目录（如果父目录存在则创建a.txt否则一起创建）
        file.mkdirs();

        // 创建文件，返回boolean true：成功  false：失败
        file.createNewFile();

        // 【删除文件】或目录，返回boolean true：成功 false：失败
        file.delete();
    }

    // 测试获得子文件或子目录
    @Test
    public void testGetChildsFileName(){
        File file = new File("d:/home");
        getChildsFileName(file);
    }

    /**
     * 获得指定目录下的所有子目录或子文件
     * @param parent 父文件
     */
    public void getChildsFileName(final File parent){
        if(parent == null || parent.isFile()) {
            System.out.println("没有子文件！");
            return;
        }
        for(File file : parent.listFiles()){
            // 获得所有文件（添加&&file.list().length > 0则是获得所有文件和空目录）
            if(file.isDirectory() && file.list().length > 0){
                getChildsFileName(file);
            }else{
                System.out.println(file.getAbsolutePath());
            }
        }
    }


    // 测试过滤子文件
    @Test
    public void testFileFilter(){
        File file = new File("d:/home");
        // 过滤home下的文件，只查找.txt结尾的文件
        File[] files = file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                //pathname：当前文件下的所有文件或目录对象
                // true：添加 false：过滤
                return pathname.isFile() && pathname.getName().endsWith(".txt");
            }
        });
        System.out.println(".txt结尾的文件："+ Arrays.toString(files));
    }
}
