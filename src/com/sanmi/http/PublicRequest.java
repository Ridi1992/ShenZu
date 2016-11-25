package com.sanmi.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

/**
 * @author Best
 *
 */
public class PublicRequest{
	private static PublicRequest instance;
	private Handler handler;
	public PublicRequest(Handler handler2) {
		this.handler=handler2;
	}
	public static  PublicRequest getInstance(Handler handler){
		instance=new PublicRequest(handler);
		return instance;
	}
	/**
	 * 获取验证码
	 * @param context
	 * @param username
	 */
	public void getCode(Context context,String username){
		String[][] param=new String[1][];
		param[0]=new String[]{"mobile",username};
		HttpUtilPHP.invokePost(context,myHandler, Constants.GETCODE,
				HttpURL.URL+HttpURL.GETCODE, param);
	}
	
	/**
	 * 登录
	 * @param context
	 * @param username
	 * @param password
	 */
	public void Login(Context context,String username,String password){
		String[][] param=new String[2][];
		param[0]=new String[]{"name",username};
		param[1]=new String[]{"password",password};
		HttpUtilPHP.invokePost(context,myHandler, Constants.LOGIN,
				HttpURL.URL+HttpURL.LOGIN, param);
	}
	/**
	 * 注册 
	 */
	public void Register(
			Context context,
			String mobile,
			String mobile_code,
			String mobilec,
			String mobile_codec,
			String password
			
			){
		String[][] param=new String[5][];
		param[0]=new String[]{"mobile",mobile};
		param[1]=new String[]{"mobile_code",mobile_code};
		param[2]=new String[]{"mobilec",mobilec};
		param[3]=new String[]{"mobile_codec",mobile_codec};
		param[4]=new String[]{"password",password};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.REGISTER,
				HttpURL.URL+HttpURL.REGISTER, param);
	}
	
	/**
	 * 修改/忘记密码之发送
	 */
	public void ResetPassword_Send(Context context,String mobile){
		String[][] param=new String[1][];
		param[0]=new String[]{"mobile",mobile};
		HttpUtilPHP.invokePost(context,myHandler, Constants.RESETPASSWORD_SEND,
				HttpURL.URL+HttpURL.RESETPASSWORD_SEND, param);
	}
	/**
	 * 设置  客户电话
	 */
	public void shezhiphone(Context context){
		String[][] param=new String[0][];
		HttpUtilPHP.invokePost(context,myHandler, Constants.SHEZHIPHONE,
				HttpURL.URL+HttpURL.SHEZHIPHONE, param);
	}
	
	/**
	 * 修改/忘记密码之验证修改
	 */
	public void ResetPassword_Check(Context context,String mobile,String mobile_code,String mobilec,String mobile_codec,String password){
		String[][] param=new String[5][];
		param[0]=new String[]{"mobile",mobile};
		param[1]=new String[]{"mobile_code",mobile_code};
		param[2]=new String[]{"mobilec",mobilec};
		param[3]=new String[]{"mobile_codec",mobile_codec};
		param[4]=new String[]{"new_password",password};
		HttpUtilPHP.invokePost(context,myHandler, Constants.RESETPASSWORD_CHECK,
				HttpURL.URL+HttpURL.RESETPASSWORD_CHECK, param);
	}
	/**
	 *提现2
	 */
	public void TiXianAmount2(Context context,String user_id){
		String[][] param=new String[1][];
		param[0]=new String[]{"user_id",user_id};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.TIXIANT,
				HttpURL.URL+HttpURL.TIXIANLISTT, param);
	}
	
	/**
	 * 轮播图
	 * @param context
	 */
	public void Banner(Context context){
		String[][] param=new String[0][];
		HttpUtilPHP.invokePost(context,myHandler, Constants.BANNER,
				HttpURL.URL+HttpURL.BANNER, param);
	}
	
	/**
	 * 各分类类型信息列表
	 * @param context
	 * @param username
	 */
	public void InfoList(Context context,String category,String type,int page,int pagenum,String longitude,String latitude,String order){
		String[][] param=new String[7][];
		param[0]=new String[]{"category",category};
		param[1]=new String[]{"type",type};
		param[2]=new String[]{"page",page+""};
		param[3]=new String[]{"pagenum",pagenum+""};
		param[4]=new String[]{"longitude",longitude};
		param[5]=new String[]{"latitude",latitude};
		param[6]=new String[]{"order",order};
		HttpUtilPHP.invokePost(context,myHandler, Constants.INFOLIST,
				HttpURL.URL+HttpURL.INFOLIST, param);
	}
	/**
	 * 我的发布
	 */
	public void ResetMeFaBu(Context context,String user_id){
		String[][] param=new String[1][]; 
		param[0]=new String[]{"user_id",user_id};
		HttpUtilPHP.invokePost(context, myHandler, Constants.MeFaBu, HttpURL.URL+HttpURL.MeFaBu, param);
	}
	/**
	 * 预约列表
	 */
	public void ResetYuYueList(Context context,String id){
		String[][] param=new String[1][]; 
		param[0]=new String[]{"id",id};
		HttpUtilPHP.invokePost(context, myHandler, Constants.YUYUELIST, HttpURL.URL+HttpURL.YUYUELIST, param);
	
	}
	/**
	 * 发布信息个人详情
	 */
	public void InfoDetail(Context context,String id){
		String[][] param=new String[1][];
		param[0]=new String[]{"id",id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.INFODETAIL,
				HttpURL.URL+HttpURL.INFODETAIL, param);
	}
	
	/**
	 * 约此服务
	 */
	public void Reservation(Context context,String id,String user_id,String phone){
		String[][] param=new String[3][];
		param[0]=new String[]{"id",id};
		param[1]=new String[]{"user_id",user_id};
		param[2]=new String[]{"tel",phone};
		HttpUtilPHP.invokePost(context,myHandler, Constants.RESERVATION,
				HttpURL.URL+HttpURL.RESERVATION, param);
	}
	/**
	 * 接受预约
	 * 	 */
	public void accept(Context context, String user_id, String id,String msgid) {
		String[][] param=new String[3][];
		param[0]=new String[]{"appointment_id",id};
		param[1]=new String[]{"user_id",user_id};
		param[2]=new String[]{"message_id",msgid};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.ACCEPT,
				HttpURL.URL+HttpURL.ACCEPT, param);
	}
	
	/**
	 * 我的预约
	 * 	 */
	public void mecall(Context context, 
			String user_id,
			String type) {
		String[][] param=new String[2][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"type",type};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.MECALL,
				HttpURL.URL+HttpURL.MECALL, param);
	}
	/**
	 * 我的地址
	 * 	 */
	public void mecity(Context context,
			String	parent_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"parent_id",parent_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.MCITY,
				HttpURL.URL+HttpURL.CITY, param);
	}
	public void mecity2(Context context,
			String	parent_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"parent_id",parent_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.MCITY2,
				HttpURL.URL+HttpURL.CITY2, param);
	}
	public void mecity3(Context context,
			String	parent_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"parent_id",parent_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.MCITY3,
				HttpURL.URL+HttpURL.CITY3, param);
	}
	/**
	 * 我的预约 接受
	 * @param context
	 * @param user_id
	 * @param type
	 */
	public void mecallaccept(Context context, 
			String appointment_id,
			String message_id,
			String type,
			String user_id) {
		String[][] param=new String[4][];
		param[0]=new String[]{"appointment_id",appointment_id};
		param[1]=new String[]{"message_id",message_id};
		param[2]=new String[]{"type",type};
		param[3]=new String[]{"user_id",user_id};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.MECALLACCEPT,
				HttpURL.URL+HttpURL.MECALLACCEPT, param);
	}
	/**
	 * 个人详细信息
	 */
	public void myself(Context context, 
			String user_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"user_id",user_id};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.MYSELF,
				HttpURL.URL+HttpURL.MYSELF, param);
	}
	/**
	 * 支付页面数据
	 */
	public void CheckOrder(Context context, String appointment_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"appointment_id",appointment_id};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.CHECKORDER,
				HttpURL.URL+HttpURL.CHECKORDER, param);
	}
	
	/**
	 * 余额支付
	 */
	public void Pay(Context context, String appointment_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"appointment_id",appointment_id};
		
		HttpUtilPHP.invokePost(context,myHandler, Constants.PAY,
				HttpURL.URL+HttpURL.PAY, param);
	}
	
	/**
	 * 充值记录列表
	 */
	public void ChongzhiList(Context context, String user_id,String page, String pagenum) {
		String[][] param=new String[4][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"type","1"};
		param[2]=new String[]{"page",page};
		param[3]=new String[]{"pagenum",pagenum};
		HttpUtilPHP.invokePost(context,myHandler, Constants.CHONGZHILIST,
				HttpURL.URL+HttpURL.CHONGZHILIST, param);
	}
	
	/**
	 * 提现记录列表
	 */
	public void TiXianList(Context context, String user_id,String page, String pagenum) {
		String[][] param=new String[4][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"type","2"};
		param[2]=new String[]{"page",page};
		param[3]=new String[]{"pagenum",pagenum};
		HttpUtilPHP.invokePost(context,myHandler, Constants.TIXIANLIST,
				HttpURL.URL+HttpURL.TIXIANLIST, param);
	}
	
	/**
	 * 交易记录列表
	 */
	public void JiaoYiList(Context context, String user_id,String page, String pagenum) {
		String[][] param=new String[4][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"type","3"};
		param[2]=new String[]{"page",page};
		param[3]=new String[]{"pagenum",pagenum};
		HttpUtilPHP.invokePost(context,myHandler, Constants.JIAOYILIST,
				HttpURL.URL+HttpURL.JIAOYILIST, param);
	}
	
	/**
	 * 获取成为会员金额
	 */
	public void RankAmount(Context context, String rank_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"rank_id",rank_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.RANKAMOUNT,
				HttpURL.URL+HttpURL.RANKAMOUNT, param);
	}
	
	/**
	 * 成为会员
	 */
	public void RechargeRank(Context context, String rank_id,String user_id,String amount) {
		String[][] param=new String[4][];
		param[0]=new String[]{"type","user"};
		param[1]=new String[]{"user_rank",rank_id};
		param[2]=new String[]{"user_id",user_id};
		param[3]=new String[]{"amount",amount};
		HttpUtilPHP.invokePost(context,myHandler, Constants.RECHARGE_RANK,
				HttpURL.URL+HttpURL.RECHARGE_RANK, param);
	}
	
	/**
	 * 充值余额
	 */
	public void RechargeAmount(Context context, String amount,String user_id) {
		String[][] param=new String[3][];
		param[0]=new String[]{"type","normal"};
		param[1]=new String[]{"amount",amount};
		param[2]=new String[]{"user_id",user_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.RECHARGE_AMOUNT,
				HttpURL.URL+HttpURL.RECHARGE_AMOUNT, param);
	}
	/**
	 * 确认支付
	 */
	public void conform_pay(Context context,
			String appointment_id,
			String type,
			String user_id
			) {
		String[][] param=new String[3][];
		param[0]=new String[]{"appointment_id",appointment_id};
		param[1]=new String[]{"type",type};
		param[2]=new String[]{"user_id",user_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.CONFIRMPAY,
				HttpURL.URL+HttpURL.CONFIRMPAY, param);
	}
	/**
	 * 提交评价
	 */
	public void comment(Context context,
			String appointment_id,
			String user_id,
			String content,
			String comment_rank,
			String message_userid
			) {
		String[][] param=new String[5][];
		param[0]=new String[]{"appointment_id",appointment_id};
		param[1]=new String[]{"user_id",user_id};
		param[2]=new String[]{"content",content};
		param[3]=new String[]{"comment_rank",comment_rank};
		param[4]=new String[]{"message_userid",message_userid};
		HttpUtilPHP.invokePost(context,myHandler, Constants.SENDCOMMENT,
				HttpURL.URL+HttpURL.SENDCOMMENT, param);
	}
	/**
	 * 完善资料
	 */
	public void wanshan(Context context,
			String user_id,
			String age,
			String address,
			String height,
			String work,
			String signature,
			String nickname
			) {
		String[][] param=new String[7][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"age",age};
		param[2]=new String[]{"address",address};
		param[3]=new String[]{"height",height};
		param[4]=new String[]{"work",work};
		param[5]=new String[]{"signature",signature};
		param[6]=new String[]{"nickname",nickname};
		HttpUtilPHP.invokePost(context,myHandler, Constants.WANSHAN,
				HttpURL.URL+HttpURL.WANSHAN, param);
	}
	
	/**
	 * 余额提现
	 */
	public void TiXianAmount(Context context,String user_id, String amount,String bank_number,String true_name) {
		String[][] param=new String[4][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"amount",amount};
		param[2]=new String[]{"true_name",true_name};
		param[3]=new String[]{"bank_number",bank_number};
		HttpUtilPHP.invokePost(context,myHandler, Constants.TIXIAN_AMOUNT,
				HttpURL.URL+HttpURL.TIXIAN_AMOUNT, param);
	}
	
	/**
	 * 查看余额
	 */
	public void GetUserAmount(Context context,String user_id) {
		String[][] param=new String[1][];
		param[0]=new String[]{"user_id",user_id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.USER_AMOUNT,
				HttpURL.URL+HttpURL.USER_AMOUNT, param);
	}
	/**
	 * 设置我的经纬度
	 */
	public void setmylocation(Context context,
			String user_id,
			String longitude,
			String latitude) {
		String[][] param=new String[3][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"longitude",longitude};
		param[2]=new String[]{"latitude",latitude};
		HttpUtilPHP.invokePost(context,myHandler, Constants.SETMYLOCATION,
				HttpURL.URL+HttpURL.SETMYLOCATION, param);
	}
	
	/**
	 * 意见反馈
	 */
	public void FeedBack(Context context,String user_id,String content,String phone) {
		String[][] param=new String[3][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"content",content};
		param[2]=new String[]{"phone",phone};
		HttpUtilPHP.invokePost(context,myHandler, Constants.FEEDBACK,
				HttpURL.URL+HttpURL.FEEDBACK, param);
	}
	
	/**
	 * 关于我们
	 */
	public void AboutUs(Context context) {
		String[][] param=new String[0][];
		HttpUtilPHP.invokePost(context,myHandler, Constants.ABOUT,
				HttpURL.URL+HttpURL.ABOUT, param);
	}
	/**
	 *设置性别
	 */
	public void setSex(Context context,
			String user_id,int sex,String nickname) {
		String[][] param=new String[3][];
		param[0]=new String[]{"user_id",user_id};
		param[1]=new String[]{"sex",sex+""};
		param[2]=new String[]{"nickname",nickname};
		HttpUtilPHP.invokePost(context,myHandler, Constants.SETSEX,
				HttpURL.URL+HttpURL.SETSEX, param);
	}
	
	/**
	 * 检查更新
	 */
	public void CheckUpdate(Context context,String version_code) {
		String[][] param=new String[1][];
		param[0]=new String[]{"version_code",version_code};
		HttpUtilPHP.invokePost(context,myHandler, Constants.UPDATE,
				HttpURL.URL+HttpURL.UPDATE, param);
	}
	
	/**
	 * 附近的人
	 */
	public void NearData(Context context,String longitude,String latitude,String sex,String page ,String pagenum) {
		String[][] param=new String[5][];
		param[0]=new String[]{"longitude",longitude};
		param[1]=new String[]{"latitude",latitude};
		param[2]=new String[]{"sex",sex};
		param[3]=new String[]{"page",page};
		param[4]=new String[]{"pagenum",pagenum};
		HttpUtilPHP.invokePost(context,myHandler, Constants.NEARDATA,
				HttpURL.URL+HttpURL.NEARDATA, param);
	}
	
	/**
	 * 查看评论
	 */
	public void Comment(Context context,String id){
		String[][] param=new String[1][];
		param[0]=new String[]{"user_id",id};
		HttpUtilPHP.invokePost(context,myHandler, Constants.COMMENT,
				HttpURL.URL+HttpURL.COMMENT, param);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			handler.handleMessage(handler.obtainMessage(msg.what, msg.obj));
		};
	};
	
	
}
