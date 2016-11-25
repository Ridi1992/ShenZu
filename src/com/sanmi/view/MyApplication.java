package com.sanmi.view;

import java.util.Set;
import org.json.JSONException;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.best.mesage.MessageData;
import com.best.mesage.MessageTool;
import com.bset.tool.Timetool;
import com.example.jpushdemo.ExampleUtil;
import com.google.gson.reflect.TypeToken;
import com.sanmi.activity.MainActivity;
import com.sanmi.activity.MeCallActivity;
import com.sanmi.activity.R;
import com.sanmi.http.JsonUtil;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
/**
 * 软件应用?
 */
public class MyApplication extends Application {
	/************* map ***********************/
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;

	public static double nlatitude = 0;

	public static double nlontitude = 0;

	public boolean checkrep = false;// 查看医师回复页面默认为未打开,打开时要进行消息刷新

	private myLocation myLocation;

	public static Boolean havelocation = false;
	/************* map ***********************/

	protected static MyApplication application;

	/******************** 极光 **********************/
	private android.support.v4.app.NotificationCompat.Builder mBuilder;
	public NotificationManager mNotificationManager;
	private String TAG = "极光";
	public static boolean isForeground = true;
	private int notifyId = 100;
	public static SharedPreferences share;
	/******************** 极光 **********************/
	
	public void onCreate() {
		super.onCreate();
		application = this;
		/************* map ***********************/
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();
		mLocationClient.start();
		/************* map ***********************/

		/******************** 极光 **********************/
		
		  JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		  JPushInterface.init(this); // 初始化 JPush registerMessageReceiver();
		  registerMessageReceiver();
		  share=getSharedPreferences("message", 0);
		/******************** 极光 **********************/
	}

	public void getmyLocation(myLocation myLocation) {
		this.myLocation = myLocation;
		if (!havelocation) {
			// mLocationClient.start();
			myLocation.result(0, 0, false);
		} else {
			myLocation.result(nlatitude, nlontitude, true);
		}
		myLocation = null;
	}

	/**
	 * 初始化百度地图
	 */
	private void initLocation() {
		LocationMode tempMode = LocationMode.Hight_Accuracy;
		String tempcoor = "gcj02";
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType(tempcoor);// 可选，默认gcj02，设置返回的定位结果坐标系，
		int span = 10000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		mLocationClient.setLocOption(option);
	}

	/**
	 * 百度地图回调
	 * 
	 * @author Administrator
	 * 
	 */
	public interface myLocation {

		void result(double nlatitude, double nlontitude, boolean success);

	}

	/**
	 * 百度地图
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			System.out.println("定位结果" + sb.toString());
			if (location.getLocType() == BDLocation.TypeGpsLocation
					|| location.getLocType() == BDLocation.TypeNetWorkLocation
					|| location.getLocType() == BDLocation.TypeOffLineLocation
					|| location.getLocType() == BDLocation.TypeCacheLocation) {// 定位成功
				nlatitude = location.getLatitude();
				nlontitude = location.getLongitude();
				mLocationClient.stop();// 停止定位
				// if(myLocation!=null){
				// myLocation.result(nlatitude,nlontitude,true);
				havelocation = true;
				// }
			} else {
				havelocation = false;
				// if(myLocation!=null){
				// myLocation.result(nlatitude,nlontitude,false);
				// }
			}
			// myLocation = null;
		}
	}

	/**
	 * 获得应用实例
	 * 
	 * @return
	 */
	public static MyApplication getApplication() {
		return application;
	}

	/**
	 * 获得屏幕尺寸
	 * 
	 * @return
	 */
	@SuppressLint("NewApi")
	public Point getScreenSize() {
		WindowManager windowManager = (WindowManager) MyApplication
				.getApplication().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point p = new Point();
		display.getRealSize(p);
		return p;
	}
    /**
     * 得到屏幕的分辨率，密度......
     * @return
     */
	public float getDenstiy() {
		WindowManager windowManager = (WindowManager) MyApplication
				.getApplication().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metric = new DisplayMetrics();
		display.getMetrics(metric);
		return metric.density;
	}

	public int getDpi() {
		WindowManager windowManager = (WindowManager) MyApplication
				.getApplication().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metric = new DisplayMetrics();
		display.getMetrics(metric);
		return metric.densityDpi;
	}

	/**
	 * DisplayMetrics metric = new DisplayMetrics();
	 * getWindowManager().getDefaultDisplay().getMetrics(metric); int width =
	 * metric.widthPixels; // 屏幕宽度（像素） int height = metric.heightPixels; //
	 * 屏幕高度（像素） float density = metric.density; // 屏幕密度?.75 / 1.0 / 1.5?int
	 * densityDpi = metric.densityDpi; // 屏幕密度DPI?20 / 160 / 240?
	 */

	/******************** 极光 **********************/
	// 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
	public void setAlias(String alias) {

		if (TextUtils.isEmpty(alias)) {
			// Toast.makeText(MainActivity.this,R.string.error_alias_empty,
			// Toast.LENGTH_SHORT).show();
			return;
		}
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			// Toast.makeText(MainActivity.this,R.string.error_tag_gs_empty,
			// Toast.LENGTH_SHORT).show();
			return;
		}
		System.out.println("alias==========" + alias);
		// 调用 Handler 来异步设置别名
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}

	private static final int MSG_SET_ALIAS = 1001;
	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				Log.d("极光返回", "Set alias in handler.");
				// 调用 JPush 接口来设置别名。
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;
			default:
				Log.i("极光返回", "Unhandled msg - " + msg.what);
			}
		}
	};
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i("TAG", logs);
				// 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
				break;
			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i("TAG", logs);
				// 延迟 60 秒来调用 Handler 设置别名
				mHandler.sendMessageDelayed(
						mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
				break;
			default:
				logs = "Failed with errorCode = " + code;
				Log.e("TAG", logs);
			}
			// ExampleUtil.showToast(logs, getApplicationContext());
		}
	};

	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_MESSAGE = "message";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				System.out.println("收到消息" + messge);
				initNotify();
				saveData(messge);
			}
		}
	}

	private void saveData(String result) {
		if (result != null) {
			//				JSONObject json = new JSONObject(result);
			MessageData data = JsonUtil.instance().fromJson(result,
					new TypeToken<MessageData>() {
					}.getType());
			if(data!=null){
				data.setTime(Timetool.getnowtime());
				MessageTool messageTool=new MessageTool();
				messageTool.adddata(share, data);
				showIntentActivityNotify(data);
				if(MainActivity.miaActivity.mMsgFragment!=null){
					MainActivity.miaActivity.mMsgFragment.refresh();
				}
				System.out.println("消息已经保存到本地");
			}
		}
	}

	
	/** 初始化通知栏 */
	@SuppressLint("InlinedApi")
	private void initNotify() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mBuilder = new android.support.v4.app.NotificationCompat.Builder(this);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
				new Intent(), Notification.FLAG_AUTO_CANCEL);
		mBuilder.setContentTitle("").setContentText("")
				.setContentIntent(pendingIntent)
				// .setNumber(number)//显示数量
				.setTicker("")// 通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				.setDefaults(Notification.DEFAULT_SOUND)
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
	}
	/**
	 * 显示通知栏点击跳转到指定Activity
	 * 
	 * @param data
	 * @throws JSONException
	 */
	@SuppressLint("HandlerLeak")
	public void showIntentActivityNotify(MessageData data) {
		// Notification.FLAG_ONGOING_EVENT --设置常驻
		// Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
		// notification.flags = Notification.FLAG_AUTO_CANCEL;
		// //在通知栏上点击此通知后自动清除此通知
		
		mBuilder.setAutoCancel(true)//点击后让通知将消失  
		.setContentTitle("偶见")
		.setContentText(data.getMessage())
		.setTicker(data.getMessage());
		//点击的意图ACTION是跳转到Intent
		Intent resultIntent2 = new Intent(this, MeCallActivity.class);
		resultIntent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,resultIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent2);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	/******************** 极光 **********************/
}
