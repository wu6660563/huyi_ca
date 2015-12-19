package com.biostime.app.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.common.ErrorCodeEnum;
import cn.jpush.api.push.MessageResult;
/**
 * 
 * android消息推送接口
 *	
 *调用规则：
 *先将要推送的信息封装成json形式，再转成String 类型  调用该类的send方法 把转换之后的String类型的数据传进来就行了
 *
 *这个只是最初测试版本  后面一些数据细节可以沟通之后再做修改
 *
 * @author zjl 新增日期：2014-6-6下午01:54:54
 * @version 1.0
 */
public class AndroidPush {

  private static final String appKey ="d539142e7d55371f5a151ad0";	//应用的唯一标识，调用者不能去修改
  private static final String masterSecret = "4d9a7a259f250dcd7ed47d52";//应用的唯一标识，调用者不能去修改
  private static JPushClient jpush = null;

  /**
   * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
   * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
   * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。
   */
  private static long timeToLive =  60 * 60 * 24;  
  
  public  boolean AndroidPushSend(String message,String mobile_no) {
	  
	// 配置推送参数，设置只推送给android手机用户
	jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android, false);
    // 推送id 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
    int sendNo = getRandomSendNo();
    //推送消息的标题
    String msgTitle = "你有新消息推送过来";
    //消息内容
    String msgContent = null;
   
    	msgContent = message;
   
    //对消息进行推送，这里默认推送给所有用户
    MessageResult msgResult = jpush.sendNotificationAll(msgContent);
    //获取推送结果
    if (null != msgResult) {
          System.out.println("服务器返回数据: " + msgResult.toString());
      if (msgResult.getErrorCode() == ErrorCodeEnum.NOERROR.value()) {
        System.out.println(String.format("发送成功， sendNo= %s,messageId= %s",msgResult.getSendNo(),msgResult.getMessageId()));
        return true;
      } else {
        System.out.println("发送失败， 错误代码=" + msgResult.getErrorCode() + ", 错误消息=" + msgResult.getErrorCode());
        return false;
      }
    } else {
    	System.out.println("无法获取数据");
    }		
      return false;
    }
	public static final int MAX = Integer.MAX_VALUE;
	public static final int MIN = (int) MAX/2;
	
	/**
	 * 保持 sendNo 的唯一性是有必要的
	 * It is very important to keep sendNo unique.
	 * @return sendNo
	 */
	public static int getRandomSendNo() {
	  return (int) (MIN + Math.random() * (MAX - MIN));
	}
  }


