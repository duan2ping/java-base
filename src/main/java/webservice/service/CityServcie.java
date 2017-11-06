package webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by duan2ping on 2017/9/25.
 */
@WebService
public class CityServcie {
    Map<String,Integer> citys = new HashMap<String,Integer>();

    @WebMethod
    public String getCity(String name){
        citys.put("北京",1);
        citys.put("贵州",2);
        citys.put("湖南",3);
        String city = "请输入正确的地区！";
        switch (citys.get(name)){
            case 1:
                city = "这是中国的首都！";
                break;
            case 2:
                city = "省会是贵阳！";
                break;
            case 3:
                city = "省会是长沙！";
                break;
            default:
                city = "没有该城市的信息！";
                break;
        }
        return city;
    }

}
