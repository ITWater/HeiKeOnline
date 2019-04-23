package top.aiteyou.textscan;


/**
 * Created by liuhai.lh on 17/01/12.
 */
public class BaseSample {

    protected static String accessKeyId;
    protected static String accessKeySecret;

    protected static String regionId;

    static {
            accessKeyId ="LTAIT4YbLlA9DMtQ";
            accessKeySecret ="ISxUR9oDh9IT3ZCZuTG3C5Wn248Rnq";
            regionId = "cn-shanghai";
    }


    protected static String getDomain(){
         if("cn-shanghai".equals(regionId)){
             return "green.cn-shanghai.aliyuncs.com";
         }

         if("cn-hangzhou".equals(regionId)){
             return "green.cn-hangzhou.aliyuncs.com";
         }
        
         if("local".equals(regionId)){
             return "api.green.alibaba.com";
         }

        return "green.cn-shanghai.aliyuncs.com";
    }

    protected static String getEndPointName(){
        return regionId;
    }

}
