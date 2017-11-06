package webservice.client;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/9/25.
 */
public class TestService {

    /**
     * jdk方式调用（需生成客户端）
     */
    public static void main(String args[]){
        CityServcieService service = new CityServcieService();
        CityServcie cityServcie = service.getCityServciePort();
        System.out.println(cityServcie.getCity("贵州"));
    }

    /**
     * soap方式调用（无需构建客户端）
     */
    @Test
    public void test() {
        // xml解析
        Document doc;
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            DocumentBuilder db=dbf.newDocumentBuilder();
            // WebService服务的地址
            URL url = new URL("http://localhost:8989/city");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //是否具有输入参数
            conn.setDoInput(true);
            //是否输出输入参数
            conn.setDoOutput(true);
            //发POST请求
            conn.setRequestMethod("POST");
            //设置请求头（注意一定是xml格式）
            conn.setRequestProperty("content-type", "text/xml;charset=utf-8");

            // 构造请求体，符合SOAP规范[可使用soapui工具得到请求体]
            String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.webservice/\">" +
                    "<soapenv:Header/>" +
                    "<soapenv:Body>" +
                    "<ser:getCity>" +
                    "<arg0>北京</arg0>" +
                    "</ser:getCity>" +
                    "</soapenv:Body>" +
                    "</soapenv:Envelope>";

            //获得一个输出流
            OutputStream out = conn.getOutputStream();
            out.write(requestBody.getBytes());

            //获得服务端响应状态码
            int code = conn.getResponseCode();
            StringBuffer sb = new StringBuffer();
            if (code == 200) {
                //获得一个输入流，读取服务端响应的数据
                InputStream is = conn.getInputStream();
               // 由输入流构建文档对象
                doc=db.parse(is);
                NodeList nl=doc.getElementsByTagName("return");
                Node n=nl.item(0);
                // 获得节点的值
                sb.append(n.getFirstChild().getNodeValue());
               /* byte[] b = new byte[1024];
                int len = 0;

                while ((len = is.read(b)) != -1) {
                    String s = new String(b, 0, len, "utf-8");
                    sb.append(s);
                }*/
                is.close();
            }

            out.close();
            System.out.println("服务端响应数据为(" + code + ")：" + sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
