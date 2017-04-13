package plugins.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

public class AliyunSMS implements SMSSender {
	
	private AliyunSMS(){}
	
	private static final AliyunSMS instance = new AliyunSMS();
	
	public static AliyunSMS getInstance(){
		return instance;
	}
	
	public static void main(String[] args) {
		AliyunSMS.getInstance().send("18801011130", "234567");
	}
	
	

	@Override
	public int send(String mobile, String sms) {
		try {
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAInFtypTgbhIGm", "hHZjsw49yvqQLtgy3IA2bTi2hGtaNT");
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
			IAcsClient client = new DefaultAcsClient(profile);
			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName("和美科技");//控制台创建的签名名称
			request.setTemplateCode("SMS_58130002");//控制台创建的模板CODE
			request.setParamString("{\"vcode\":\""+sms+"\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
			//request.setParamString("{}");
			request.setRecNum(mobile);//接收号码
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
			String model = httpResponse.getModel();
			System.out.println(model);
		} catch (ServerException e) {
			e.printStackTrace();
		}
		catch (ClientException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
