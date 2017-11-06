package webservice.service;

import javax.xml.ws.Endpoint;

/**
 * 发布服务
 * Created by duan2ping on 2017/9/25.
 */
public class PublishService {

    public static void main(String args[]){
        // 服务类
        CityServcie cityServcie = new CityServcie();
        // 发布的地址
        String address = "http://localhost:8989/city";
        // 发布服务
        Endpoint.publish(address,cityServcie);
        System.out.println("---------Success----------");
    }

}
