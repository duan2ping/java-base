import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/8.
 */
public class MY {

    public static void main(String args[]) throws IllegalAccessException, InstantiationException {
       test();
        //System.out.println((new utils.Student("23",123)).hashCode());
        //System.out.println((new utils.Student("23",123)).hashCode());
    }

    public static void test(){
        try {
            throw new RuntimeException();
        }catch (Exception e){
            e.printStackTrace();
        }
        List result = new ArrayList();
        result.add("{\"name\":\"张三\",\"age\":13}");
        result.add("{\"name\":\"李四\",\"age\":18}");
        List<Map> list = (List)result;
        System.out.println(list.get(1).size());
    }
}
