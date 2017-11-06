package socket;

import org.junit.Test;

import java.io.*;
import java.net.*;

/**
 * Created by duan2ping on 2017/10/9.
 */
public class SocketTest {

    public static void main(String args[]) throws UnknownHostException {
        ServerSocket service = null; // 服务器程序对象
        Socket request = null;
        Integer port = 1725;         // 应用端口
        OutputStream out = null; // 用于响应数据
        InputStream in = null;   // 用于读取请求数据
        try {
            service = new ServerSocket(port); // 创建服务端
            System.out.println("服务器已成功启动......");
            request = service.accept();       // 获得请求（访问）
            in = request.getInputStream();    // 获得请求流
            byte[] data = new byte[1024];
            int len = in.read(data); // 将数据读取到data
            String requestData = new String(data,0,len);
            System.out.println("请求数据："+requestData);
            out = request.getOutputStream(); // 获得响应流
            String responseData = requestData+"123";
            out.write(responseData.getBytes());
            out.flush();
        } catch (IOException e) {
            System.out.println("创建服务端错误："+e.getMessage());
        }finally {
            try {
                // 释放资源
                if(out != null) out.close();
                if(in != null) in.close();
                if(service != null) service.close();
            }catch (Exception e){
                System.out.println("释放资源错误："+e.getMessage());
            }
        }
    }


    @Test
    public void testDowload() throws IOException {
        InputStream is = new FileInputStream(new File("d:/aa.txt"));
        int i = is.read();
        while(i != -1){
            System.out.println((char)i);
            i = is.read();
        }
    }

    public void dowload(String url){
        InputStream in = null;
        OutputStream os = null;
        try{
            String type = ".pdf";
            os = new FileOutputStream("d:/dowload/"+System.currentTimeMillis()+type);
            // 获得远程连接
            URL target = new URL(url);
            // 打开连接并获得远程连接输入流
            HttpURLConnection connection = (HttpURLConnection) target.openConnection();
            connection.getContentType();

            connection.getResponseCode();
            in = connection.getInputStream();
            // simple while reade and write
            byte data[] = new byte[1024];
            int readcount=in.read(data);
            int dowloadbyte = readcount;
            System.out.println("length:"+connection.getContentLength());
            System.out.print("dowload: [");
            while(readcount!=-1){
                System.out.print(((int)((dowloadbyte/(connection.getContentLength()+0.0))*100))+"%");
                os.write(data, 0, readcount);
                readcount=in.read(data);
                dowloadbyte += readcount;
            }

            System.out.println("----dowload success-----");
        }catch(Exception e){
            System.out.println("----dowload error----");
            // 关闭资源
        }finally{
            try {
                if(in != null) in.close();
                if(os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 客户端
    @Test
    public void simpleClient(){
        Socket client = null;     // 连接对象
        OutputStream out = null; // 用于写入请求数据
        InputStream in = null;   // 用于读取响应数据
        try {
            String request = "hello";   // 请求数据
            String host = "127.0.0.1"; // 请求主机
            Integer port = 1725;        // 请求端口
            client = new Socket(host, port); // 建立连接
            out = client.getOutputStream();  // 获得输出流
            out.write(request.getBytes());   // 写入缓存请求数据
            out.flush(); // 写入（发送请求）
            in = client.getInputStream();
            byte[] data = new byte[2024];
            in.read(data); // 读取响应数据
            System.out.println("响应数据：" + new String(data));
        }catch (Exception e){
            System.out.println("请求错误："+e.getMessage());
        }finally {
            // 释放资源
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (client != null) client.close();
            }catch (Exception e){
                System.out.println("释放资源错误："+e.getMessage());
            }
        }
    }
    @Test
    public void test() throws InterruptedException {
            System.out.print("Progress:");
            for (int i = 1; i <= 100; i++) {
                System.out.print(i + "%");
                Thread.sleep(100);

                for (int j = 0; j <= String.valueOf(i).length(); j++) {
                    System.out.print("\b");
                }
            }
            System.out.println();

}

    @Test
    public void testSendHttp(){
        for(int i = 0 ; i <1000 ; i++){
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    testHttp();
                }
            },"Thread"+i);
            t1.run();
        }
    }
    /**
     * 向指定 URL 发送POST方法的请求
     *
     *  url
     *            发送请求的 URL
     * param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public void testHttp(){
            String url = "http://localhost:12001/httphub?PRODUCER_SYSCODE=HS002&SERVICE_CODE=TH_S_A02&CONSUMER_SYSCODE=HS0001&" +
                         "MESSAGE_ID="+Math.random()+"&DTSEND=20170706181528&VERSION=1";
            String param = "";
            PrintWriter out = null;
            BufferedReader in = null;
            String result = "";
            try {
                URL realUrl = new URL(url);
                // 打开和URL之间的连接
                URLConnection conn = realUrl.openConnection();
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                System.out.println("发送 POST 请求出现异常！"+e);
                e.printStackTrace();
            }
            //使用finally块来关闭输出流、输入流
            finally{
                try{
                    if(out!=null){
                        out.close();
                    }
                    if(in!=null){
                        in.close();
                    }
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+result);
    }
}