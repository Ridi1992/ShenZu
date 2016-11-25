package com.sanmi.http;

public class HttpURL {
	public static final String URL ="http://zuwo.seotech.com.cn/";
	
	/**
	 * 服务器相册图片路径
	 */
	public static final String IMGPATH = "http://zuwo.seotech.com.cn/app_api/picture/";
	/**
	 * 获取验证码
	 */
	public static final String GETCODE ="app_api/register.php?act=send";
	/**
	 * 注册
	 */
	public static final String REGISTER ="app_api/register.php?act=check";
	/**
	 * 登录
	 */
	public static final String LOGIN ="ecmobile/?url=user/signin";
	public static final String CITY ="ecmobile/?url=region";
	public static final String CITY2 ="ecmobile/?url=region";
	public static final String CITY3 ="ecmobile/?url=region";
	/**
	 * 忘记/修改密码
	 */
	public static final String RESETPASSWORD_SEND ="app_api/reset_password.php?act=send";
	public static final String RESETPASSWORD_CHECK ="app_api/reset_password.php?act=check";
	public static final String RESETPASSWORD_SET ="app_api/reset_password.php?act=set";
	/**
	 * 寻求发布
	 */
	
	/**
	 * 轮播图
	 */
	public static final String BANNER ="app_api/banner.php";
	/**
	 * 各分类类型信息列表
	 */
	public static final String INFOLIST="app_api/publish.php?act=getrentdata";
	/**
	 * 我的发布
	 */
	public static final String MeFaBu="app_api/publish.php?act=mypublish";
	
	/**
	 * 发布信息个人详情
	 */
	public static final String INFODETAIL="app_api/publish.php?act=getrentdetail";
	/**
	 * 约此服务
	 */
	public static final String RESERVATION = "app_api/publish.php?act=reservation";
	/**
	 * 预约列表
	 */
	public static final String YUYUELIST = "app_api/publish.php?act=appointment_Check";
	/**
	 * 接受预约
	 */
	public static final String ACCEPT = "app_api/publish.php?act=appointment_Accept";
	/**
	 * 我的预约
	 */
	public static final String MECALL = "app_api/publish.php?act=myappointment";
	/**
	 * 我的预约接受
	 */
	public static final String MECALLACCEPT = "app_api/publish.php?act=AcceptFromMyAppointList";
	/**
	 * 个人详细信息
	 */
	public static final String MYSELF = "app_api/userInfo.php?act=check";
	/**
	 * 支付页面数据
	 */
	public static final String CHECKORDER = "app_api/order.php?act=checkorder";
	/**
	 * 余额支付
	 */
	public static final String PAY = "app_api/order.php?act=pay";
	/**
	 * 充值
	 */
	public static final String CHONGZHILIST = "app_api/order.php?act=get_amount_record";
	/**
	 * 提现
	 */
	public static final String TIXIANLIST = "app_api/order.php?act=get_amount_record";
	/**
	 * 提现2
	 */
	public static final String TIXIANLISTT = "app_api/order.php?act=get_poundage";
	/**
	 * 交易
	 */
	public static final String JIAOYILIST = "app_api/order.php?act=get_amount_record";
	/**
	 * 获取成为会员金额
	 */
	public static final String RANKAMOUNT = "app_api/order.php?act=get_user_amount";
	/**
	 * 成为会员
	 */
	public static final String RECHARGE_RANK = "app_api/order.php?act=recharge";
	/**
	 * 充值余额
	 */
	public static final String RECHARGE_AMOUNT = "app_api/order.php?act=recharge";
	/**
	 * 余额提现
	 */
	public static final String TIXIAN_AMOUNT = "app_api/order.php?act=get_cash";
	/**
	 * 查看余额
	 */
	public static final String USER_AMOUNT = "app_api/order.php?act=getUserAmount";
	/**
	 * 确认支付
	 */
	public static final String CONFIRMPAY = "app_api/order.php?act=confirm_pay"; 
	/**
	 * 意见反馈
	 */
	public static final String FEEDBACK = "app_api/setting.php?act=feedback";
	/**
	 * 关于我们
	 */
	public static final String ABOUT = "app_api/setting.php?act=about_us";
	/**
	 * 评价
	 */
	public static final String SENDCOMMENT = "app_api/comment.php?act=save";
	/**
	 * 完善资料
	 */
	public static final String WANSHAN = "app_api/userInfo.php?act=updata"; 
	/**
	 * 版本更新
	 */
	public static final String UPDATE = "app_api/setting.php?act=check"; 
	/**
	 * 设置我的经纬度
	 */
	public static final String SETMYLOCATION = "app_api/userInfo.php?act=updata_location"; 
	/**
	 * 设置性别
	 */
	public static final String SETSEX = "app_api/userInfo.php?act=updata_sex"; 
	/**
	 * 附近的人
	 */
	public static final String NEARDATA = "app_api/publish.php?act=neardata";
	/**
	 * 查看评论
	 */
	public static final String COMMENT = "app_api/userInfo.php?act=comment";
	/**
	 * 客户电话
	 */
	public static final String SHEZHIPHONE = "app_api/setting.php?act=contact";
	

}