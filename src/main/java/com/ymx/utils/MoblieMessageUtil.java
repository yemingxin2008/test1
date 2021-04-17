package com.ymx.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;

/**
 * 阿里短信接口   
 *<p>Title:MoblieMessageUtil </p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author 123
 * @date 2018-3-20
 */
public class MoblieMessageUtil {

    // 产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    // 产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    @Value("${shortMessage.accessKeyId}")
    private static String accessKeyId = "yourAccessKeyId";
    @Value("${shortMessage.accessKeySecret}")
    private static String accessKeySecret = "yourAccessKeySecret";
    @Value("${shortMessage.signName}")
    private static String signName = "yourAccessKeySecret";//必填:短信签名-可在短信控制台中找到
    @Value("${shortMessage.identifyingTempleteCode}")
    private static String identifyingTempleteCode = "yourAccessKeySecret"; //必填:短信模板-可在短信控制台中找到      验证码
    @Value("${shortMessage.registTempleteCode}")
    private static String registTempleteCode = "yourAccessKeySecret"; //带账号密码

    public static void init(String accessKeyId, String accessKeySecret, String signName, String identifyingTempleteCode,
            String registTempleteCode) {
        MoblieMessageUtil.accessKeyId = accessKeyId;
        MoblieMessageUtil.accessKeySecret = accessKeySecret;
        MoblieMessageUtil.signName = signName;
        MoblieMessageUtil.identifyingTempleteCode = identifyingTempleteCode;
        MoblieMessageUtil.registTempleteCode = registTempleteCode;
    }
   static {

    	MoblieMessageUtil.init(accessKeyId,accessKeySecret,signName,identifyingTempleteCode,registTempleteCode);
	   // MoblieMessageUtil.init("LTAIBrTEWYOvrdrh", "vpMWezG6ydm7PBNxFEm8nFpGaAPQjp", "阿拉的", "SMS_129075024","SMS_129075024");
    }
    public static void main(String[] args) {
    	//测试用
    	MoblieMessageUtil.init("LTAIBrTEWYOvrdrh", "vpMWezG6ydm7PBNxFEm8nFpGaAPQjp", "阿拉的", "SMS_129075024","SMS_129075024");
        // 发短信
        SendSmsResponse response = MoblieMessageUtil.sendIdentifyingCode("17607617897", "123456");
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
        if(response.getCode() != null && response.getCode().equals("OK")) {
        	//请求成功
        	System.out.println("发送成功");
        }
       /* response = MoblieMessageUtil.sendNewUserNotice("18637903705", "123456", "4512");
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());*/

    }

    public static SendSmsResponse sendSms(String mobile, String templateParam, String templateCode)
            throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public static SendSmsResponse sendNewUserNotice(String mobile, String username, String password) throws Exception {
        try {
            return sendSms(mobile, "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}",
                    registTempleteCode);
        } catch (ClientException e) {
            throw new  Exception(e.getMessage());
        }
    }
    /**
     * 发送短信验证码
     * @param mobile
     * @param code
     * @return
     */
    public static SendSmsResponse sendIdentifyingCode(String mobile, String code)   {
        try {
            return sendSms(mobile, "{\"code\":\"" + code + "\"}", identifyingTempleteCode);
        } catch (ClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
