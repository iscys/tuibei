package com.tuibei.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.SmsContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.security.jca.GetInstance;

import java.util.Properties;

@Component
public class AliSmsUtils {

    private   Properties properties;

    private AliSmsUtils(){}

    public static AliSmsUtils  getInstance(){
        return Instance.getInsatnce();
    }

    /**
     * 加载外部classpath:sms.properties 装配到properties
     */
    static  class  Instance {
        private static  AliSmsUtils utils =new AliSmsUtils();

        public static  AliSmsUtils getInsatnce(){
            utils.properties =ResourceUtils.loadSingleProperties("sms.properties");
            return utils;
        }
    }

    /**
     * 获取properties
     */
    public Properties getProperties(){
        return properties;
    }


    /**
     * 获取properties value
     */

    public String getPropertiesValue(String key){
        Properties properties = getProperties();
        String property = properties.getProperty(key);
        return property;
    }


    /**
     * 发送短信验证码
     * 具体查看阿里云验证码demo以及sdk；
     */
    public  String sendSms(SmsContent content)throws Exception {
        String accessKeyId=getPropertiesValue("alibaba.sms.accessKeyId");
        String accessKeySecret=getPropertiesValue("alibaba.sms.accessKeySecret");
        String phone = content.getPhone();
        String product = Constant.SMS.PRODUCT;
        String domain = Constant.SMS.DOMAIN;
        String templateCode = content.getTemplateCode();
        String json = content.getTemplateParam();
        String signName = content.getSignName();

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到//"SMS_151625215"
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(json);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse.getCode();
    }


    public static void main(String[] args) throws Exception {


        String accessKeyId="LTAIgfIifp2T0OKy";
                String accessKeySecret="U5F0ZjSakWMNmbVqq6sxqsYZtBJ3Ob";
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", Constant.SMS.PRODUCT, Constant.SMS.DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers("15034083689");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("退呗验证码");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_170346592");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"2345\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println(sendSmsResponse.getCode());
    }


}
