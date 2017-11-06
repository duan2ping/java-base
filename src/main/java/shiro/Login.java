package shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;


/**
 * 登录
 * Created by duan2ping on 2017/9/1.
 */
public class Login{

    // 安全管理工厂
    private static  Factory<SecurityManager> factory = null;
    // 安全管理
    private static  SecurityManager securityManager = null;

    // 初始化参数
    static{
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        factory = new IniSecurityManagerFactory("classpath:user.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    /**
     * 登录
     * @param username   用户名
     * @param password   密码
     * @return            状态
     */
    public static String login(String username,String password){
        String result = "登录成功！";
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        // 构建令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //4、登录，即身份验证
            subject.login(token);
        }catch (UnknownAccountException e){
            result = "❤、认证失败：[unknownAccount]";
        } catch (AuthenticationException e) {
            result = "❤、认证失败：[authentication]";
        }
        return  result;
    }

    /**
     * 退出
     */
    public static void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public static void main(String args[]){
       String pass = "213";
       String enPass = Base64.encodeToString(pass.getBytes());
       System.out.println("原密码："+pass+"   加密后："+enPass);
       String dePass = Base64.decodeToString(enPass);
       System.out.println("加密后："+enPass +"   解密后"+dePass);
    }

   /**
     * 密码散列算法加密
     * @param passWord             明文密码
     * @param credentialsSalt     盐值
     * @param hashAlgorithmName  算法名
     * @param hashIterations     加密次数
     * @return                    密文
     */
    public static String Encrypt(String hashAlgorithmName,String passWord,int hashIterations,Object credentialsSalt){

        return new SimpleHash(hashAlgorithmName,passWord,credentialsSalt,hashIterations).toString();
    }

    // 加密
    public static byte[] Base64Encode(String text){

        return Base64.encode(text.getBytes());
    }
    // 解密
    public static byte[] Base64Decode(String text){

        return Base64.decode(text.getBytes());
    }

}
