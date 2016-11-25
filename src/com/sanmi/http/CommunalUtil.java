package com.sanmi.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import com.sanmi.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Environment;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.ScrollView;

public class CommunalUtil {
	private Context con;
	/*private DBUtils dbutils;//数据库操作类
*/	//设置私有构造方法
    private CommunalUtil(Context context){
    	con=context;
    	//初始化数据库操作类
    	/*dbutils=DBUtils.getInstance(context)*/;
    }
    public static volatile CommunalUtil commumalUtil = null;
    /**
	 * 获取共用方法实体类
	 * @return
	 */
    public static CommunalUtil getInstance(Context context) {
        // if already inited, no need to get lock everytime
        if (commumalUtil == null) {
            synchronized (CommunalUtil.class) {
                if (commumalUtil == null) {
                	commumalUtil = new CommunalUtil(context);
                }
            }
        }
        return commumalUtil;
    }
    /**
	 * 时间中间去除“-”
	 * @param strs
	 * @return
	 */
	public String dateChange(String date)
	{
		StringBuilder sb=new StringBuilder();
		String[]dateStr=date.split("-");
		for(int i=0;i<dateStr.length;i++)
		{
			sb.append(dateStr[i]);
		}
		return sb.toString();		
	}
	
	/**
	 * 判断是否都为数字
	 * @param inputnum 输入的文字
	 * @return
	 */
	public boolean isNumber(String inputnum) {
		boolean isNum = inputnum.matches("[0-9]+");
		if (!isNum) {
			return false;
		}
		return true;
	}
	// 判断一个字符串是否含有数字
	public boolean hasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
		flag = true;
		return flag;
	}
	/**
	 * 是否能输入
	 * @return
	 */
	public boolean canInput(String str)
	{
		String regEx = "[<>'']";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为邮政编码
	 * @param inputnum 输入的文字
	 * @return
	 */
	public boolean isPostalcode(String inputnum) {
		boolean isNum = inputnum.matches("[0-9]+");
		if (!isNum||inputnum.length()!=6) {
			return false;
		}
		return true;
	}

	/**
	 * 判断输入的邮箱是否合法
	 * @param inputEmail输入的邮箱
	 * @return true邮箱符合 false邮箱不合格
	 */
	public boolean isEmail(String inputEmail) {
		if (inputEmail
				.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
			return (true);
		else
			return (false);
	}

	/**
	 * 判断手机号码是否正确
	 * @param phonenum输入的手机号码
	 * @return ture 手机号码符合，false手机号码不合格
	 */
	public boolean isPhoneNum(String phonenum) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(phonenum);  
		return m.matches();  
	}
	/**
	 * 判断是否为电话号码
	 * @param phonenumber 输入的电话号码
	 * @return
	 */
	public boolean isTelephone(String phonenumber)
	 {
	  String phone = "^(0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{7})$";
	  Pattern p = Pattern.compile(phone);
	  Matcher m = p.matcher(phonenumber);
	  return m.matches();
	 }
	/**
	 * 转换时间格式
	 * @param dataStr 需要转换的字符串
	 * @return
	 * @throws java.text.ParseException 
	 */
	public String changeDate(String dataStr,String format) throws java.text.ParseException
	{
		String time="";
		try {
			Date date= new SimpleDateFormat("yyyyMMdd").parse(dataStr);
			time = new SimpleDateFormat(format).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;	
	}
	/**
	 * 判断字符长度
	 * @param str 输入的文字
	 * @param charNum最大长度
	 * @return
	 */
	public boolean allowMaxLenth(String str, int charNum) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			String tmp = str.substring(i, i + 1);
			if (tmp.getBytes().length == 3) {
				num += 2;
			} else if (tmp.getBytes().length == 1) {
				num += 1;
			}
		}
		if (num <= charNum) {
			return true;
		}
		return false;
	}
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public int daysBetween(String start,String emd)   
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
        Date smdate=null;
        Date bdate=null;
        try {
        	smdate= sdf.parse(start);
        	bdate= sdf.parse(emd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    /** 计算年龄 
     * @throws java.text.ParseException */ 
    public  int getAge(String birthDay,String time) throws java.text.ParseException{ 
    	 int age=0;
    	if(birthDay!=null&&birthDay.length()>0)
    	{
    		Calendar cal = Calendar.getInstance(); 
    	        
	        cal.setTime(StrToDate(time));
	        int yearNow = cal.get(Calendar.YEAR); 
	        int monthNow = cal.get(Calendar.MONTH); 
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
	        
	        cal.setTime(StrToDate(birthDay)); 
	        int yearBirth = cal.get(Calendar.YEAR); 
	        int monthBirth = cal.get(Calendar.MONTH); 
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 

	        age = yearNow - yearBirth; 

	        if (monthNow <= monthBirth) { 
	            if (monthNow == monthBirth) { 
	                //monthNow==monthBirth 
	                if (dayOfMonthNow < dayOfMonthBirth) { 
	                    age--; 
	                } 
	            } else { 
	                //monthNow>monthBirth 
	                age--; 
	            } 
	        } 
    	}
        return age; 
    }
    /**
     * 获取随机数
     * @param length 多少位
     * @return
     */
    public String randString(int length) {

		Random r = new Random();
		String ssource = "0123456789";
		char[] src = ssource.toCharArray();
		char[] buf = new char[length];
		int rnd;
		for (int i = 0; i < length; i++) {
			rnd = Math.abs(r.nextInt()) % src.length;

			buf[i] = src[rnd];
		}
		return new String(buf);
	}
    /**
     * 字符串转换成日期
     * @param str
     * @return date
     * @throws java.text.ParseException 
     */
     public static Date StrToDate(String str) throws java.text.ParseException {
       
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
         date = format.parse(str);
        } catch (ParseException e) {
         e.printStackTrace();
        }
        return date;
     }
 	/**
 	 * 按钮按下动画
 	 * @return
 	 */
 	public Animation getScaleAnimation()
 	{
 		float end = 0.94f;
 		float start = 1.0f;
 		final Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
 		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
 		scaleAnimation.setDuration(100);
 		scaleAnimation.setFillAfter(true);
 		return scaleAnimation;
 	}
 	/**
 	 * 按钮弹起动画
 	 * @return
 	 */
 	public Animation getEndAnimation()
 	{
 		float end = 0.94f;
 		float start = 1.0f;
 		final Animation endAnimation = new ScaleAnimation(end, start, end, start,
 		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
 		endAnimation.setDuration(100);
 		endAnimation.setFillAfter(true);
 		return endAnimation;	
 	}
	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}
	/**
	 * 保存图片
	 * @param savePath
	 * @param picName
	 * @param bitmap
	 */
	public void saveBitmap(String savePath, String picName, Bitmap bitmap,boolean iswatermark) {
		if (null != bitmap) {
			File f=new File(savePath);
			if(!f.exists())
			{
				f.mkdirs();
			}
			File file = new File(savePath + picName );
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(iswatermark)
			{
				//水印
		        int w = bitmap.getWidth();
		        int h = bitmap.getHeight();
		        /*String mstrTitle = "pad："+dbutils.getDate();*/
		        Bitmap bmpTemp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		        Canvas canvas = new Canvas(bmpTemp);
		        Paint p = new Paint();
		        String familyName = "宋体";
		        Typeface font = Typeface.create(familyName, Typeface.BOLD);
		        p.setColor(Color.BLUE);
		        p.setTypeface(font);
		        p.setTextSize(18);
		        canvas.drawBitmap(bitmap, 0, 0, p);
		        /*canvas.drawText(mstrTitle, 10, 10, p);*/
		        canvas.save(Canvas.ALL_SAVE_FLAG);
		        canvas.restore();
				//水印
				FileOutputStream bitmapWtriter = null;
				try {
					bitmapWtriter = new FileOutputStream(file);
					if(picName.contains(".png"))
					{
						if (bmpTemp.compress(Bitmap.CompressFormat.PNG, 80,
								bitmapWtriter)) {
							System.out.println("保存图片是否成功");
						}	
					}else
					{
						if (bmpTemp.compress(Bitmap.CompressFormat.JPEG, 80,
								bitmapWtriter)) {
							System.out.println("保存图片是否成功");
						}	
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
//					LogUtil.getInstance(con).info("签名保存图片异常：" + e.getMessage());
				}	
			}else
			{
				FileOutputStream bitmapWtriter = null;
				try {
					bitmapWtriter = new FileOutputStream(file);
					if(picName.contains(".png"))
					{
						if (bitmap.compress(Bitmap.CompressFormat.PNG, 80,
								bitmapWtriter)) {
							System.out.println("保存图片是否成功");
						}	
					}else
					{
						if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80,
								bitmapWtriter)) {
							System.out.println("保存图片是否成功");
						}	
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
//					LogUtil.getInstance(con).info("签名保存图片异常：" + e.getMessage());
				}		
			}
			encryptFile(savePath + picName);
		}

	}
	/**
	 * 改变图片的大小像素
	 * @param bit拍照bitmap
	 * @param newWidth改变的图片的长
	 * @param newHeight改变的图片高
	 */
	public void resizeBitmap(String filepath, int newWidth) {
		try {
			Bitmap bit = getBit(filepath);
			int width = bit.getWidth();
			int height = bit.getHeight();
			float temp = ((float) height) / ((float) width);
			int newHeight = (int) ((newWidth) * temp);
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap resizedBitmap = Bitmap.createBitmap(bit, 0, 0, width,
					height, matrix, true);
			if (bit != null && !bit.isRecycled()) {
				bit.recycle();
			}
			File fi = new File(filepath);
			FileOutputStream fout = new FileOutputStream(fi);
			resizedBitmap.compress(Bitmap.CompressFormat.JPEG, // 图片格式
					60, // 品质0-100
					fout // 使用的输出流
					);
			fout.flush();
			fout.close();
			if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
				resizedBitmap.recycle();
			}
			encryptFile(filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 通过路径获取本地图片
	 * @param path图片路径
	 * @return
	 */
	public Bitmap getBit(String path) {
		InputStream assetFile = null;
		Bitmap bit = null;
		try {
			Drawable drawable = Drawable.createFromPath(path);
			if (drawable == null) {
				Drawable d = con.getResources().getDrawable(
						R.drawable.ic_launcher);
				BitmapDrawable bd = (BitmapDrawable) d;
				bit = bd.getBitmap();
			} else {
				File fi = new File(path);
				assetFile = new BufferedInputStream(new FileInputStream(fi));
				bit = BitmapFactory.decodeStream(assetFile);
			}
		} catch (Exception e) {
			if (bit != null && !bit.isRecycled()) {
				bit.recycle();
			}
		}
		return bit;
	}
	/**
     *  截图ScrollView
     * **/
	public Bitmap getBitmap(ScrollView scroll) {
        int h = 0;
        // 获取ScrollView实际高度
        for (int i = 0; i < scroll.getChildCount(); i++) {
            h += scroll.getChildAt(i).getHeight();
            scroll.getChildAt(i).setBackgroundResource(R.color.baise);
        }
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(scroll.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        scroll.draw(canvas);
        return bitmap;
    }
	/**
	 * 截取webView快照(webView加载的整个内容的大小)
	 * @param webView
	 * @return
	 */
	public Bitmap getWebView(WebView webView){
		Bitmap bmp = null;
		try {
			Picture snapShot = webView.capturePicture();
			
			bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			snapShot.draw(canvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmp;
	}
	/**
	 * 合并图片
	 * @param path1 第一张图片路径
	 * @param path2第二张图片路劲
	 * @return
	 */
	public Bitmap conformBitmap(String path1, String path2) {
		Bitmap bit1=getBit(path1);
		Bitmap bit2=getBit(path2);
		if (bit2 == null) {
			return null;
		}
		int foreHeight = bit2.getHeight();
		int bgWidth = bit1.getWidth();
		int bgHeight = bit1.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight + foreHeight,
				Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(bit1, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		cv.drawBitmap(bit2, 0, bgHeight, null);// 在 0，0坐标开始画入fg ，可以从任意位置画入
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}
	/**
	 * 删除本地sdcard文件
	 * @param filepath文件路径
	 */
	public void deletefile(File fizip) {
		if (fizip.exists()) {

			fizip.delete();
		}
	}

	/**
	 * 删除本地sdcard文件夹
	 * 
	 * @param dir
	 *            文件夹
	 */
	public void deleteDirfile(File dir) {
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return; // 检查参数
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDirfile(file); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	/**
	 * 生成txt文件
	 * @param str传入要生成的 json 字符串
	 * @param path路径 CFCCC/plon
	 * @param name文件名字 abc.txt
	 */

	public void createText(String str, String txtpath) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {

				File saveFile = new File(txtpath);

				if (!saveFile.exists()) {
					saveFile.createNewFile();
				}
				FileOutputStream outStream = new FileOutputStream(saveFile);
				// byte [] utf8Header={(byte)0xef,(byte)0xbb,(byte) 0xbf};
				// outStream.write(utf8Header);
				outStream.write(str.getBytes("UTF-8"));
				outStream.close();
				encryptFile(txtpath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	/**
	 * 获取文件MD5值
	 * @param filepath       文件路径
	 * @return
	 */
	/*public String getFileMD5(String filepath) {
		File file = new File(filepath);
		if (!file.isFile()) {
			return null;
		}

		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// BigInteger bigInt = new BigInteger(1, digest.digest());
		byte[] md = digest.digest();
//		String rlt = Base64.encodeToString(md, Base64.DEFAULT);

		return rlt.trim();
	}*/
	/**
	 * 获取手机基站信息
	 * @throws JSONException 
	 */
	public String getGSMCellLocationInfo(Context context) {
		
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		String operator = manager.getNetworkOperator();
		JSONObject item = new JSONObject(); 
		String mcc ="";
		String mnc ="";
		String lac ="";
		String cellid ="";
		int strength = 0;
		/**通过operator获取 MCC 和MNC */
		if(operator!=null&&operator.length()>3)
		{
			mcc = operator.substring(0, 3);
			mnc =operator.substring(3);
			GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
			
			/**通过GsmCellLocation获取中国移动和联通 LAC 和cellID */
			lac = String.valueOf(location.getLac());
			cellid = String.valueOf(location.getCid());
			strength = 0;
			/**通过getNeighboringCellInfo获取BSSS */
			List<NeighboringCellInfo> infoLists = manager.getNeighboringCellInfo();
			for (NeighboringCellInfo info : infoLists) {
				strength+=(-133+2*info.getRssi());// 获取邻区基站信号强度  
				System.out.println("rssi:"+info.getRssi()+"   strength:"+strength);
			}
		}
		try {
			item.put("cellid", cellid);  
	        item.put("lac", lac);  
	        item.put("mnc", mnc);  
	        item.put("mcc", mcc);  
	        item.put("strength", String.valueOf(strength));  
		} catch (Exception e) {
			// TODO: handle exception
		}
		return item.toString();
	}
	/**
	 * 加密文件
	 * @param filepath 文件路径
	 */
	public void encryptFile(String filepath)  {
		File dir=new File(filepath);
		//如果传入的是文件路径则加密文件
		if (dir == null || !dir.exists() || !dir.isDirectory())
		{
			encrypt(dir.getPath());
			return;
		}
		//如果传入的是目录路径则递规的方式加密目录
		for (File file : dir.listFiles()) {
			if (file.isFile())
			{
				encrypt(file.getPath());	
			}	
			else if (file.isDirectory())
			{
				encryptFile(file.getPath()); 
			}
				
		}
	}
	/**
	 * 加密方法
	 * @param filepath 文件路径
	 */
	public void encrypt(String filepath) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(filepath, "rw");
			int value = -1;
			int i = 0;
			while ((value = raf.read()) != -1) {
				// 如果是txt文件则全部文件异或加密
				if (filepath.endsWith(".txt")) {
					long pointer = raf.getFilePointer();
					raf.seek(pointer - 1);
					raf.write(value ^ "2805143".hashCode());
				}
				// 如果是其他文件则只异或机密文件的前2个字符
				else {
					if (i <20) {
						long pointer = raf.getFilePointer();
						raf.seek(pointer - 1);
						raf.write(value ^ "2805143".hashCode());
						i++;
					} else {
						break;
					}
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 字串str是否为英文和中文
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndChinese(String str) {
		int j = 0;
		int k = 0;
		int i = str.length();
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			j++;
		}
		for (int idx = 0; idx < i; idx++) {
			char c = str.charAt(idx);
			int tmp = (int) c;
			if ((tmp >= 'a' && tmp <= 'z') || (tmp >= 'A' && tmp <= 'Z')) {
				k++;
			}
		}
		if (i == j + k) {
			return true;
		} else {
			return false;
		}

	}
		
	public boolean validateTime(String beginTime,String  endTime) {
		
		String strBegin[] = beginTime.split("-");
		String strEnd[] = endTime.split("-");

		int beginYear = Integer.parseInt(strBegin[0].trim());
		int beginMonth = Integer.parseInt(strBegin[1].trim());
		int beginDay = Integer.parseInt(strBegin[2].trim());
		
		int endYear = Integer.parseInt(strEnd[0].trim());
		int endMonth = Integer.parseInt(strEnd[1].trim());
		int endDay = Integer.parseInt(strEnd[2].trim());
		// 输入的开始日期不能晚于当前时间!
		if ((beginYear * 372 + beginMonth * 31 + beginDay) <= (endYear * 372
				+ endMonth * 31 + endDay)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 字串str是否为英文,数字和中文，不能有特殊符号
	 * 
	 * @param str
	 * @return true or false
	 */
	public boolean isEnglishAndDigitAndChinese(String str) {
		int j = 0;
		int k = 0;
		int q = 0;
		for (int idx = 0; idx < str.length(); idx++) {
			if (Character.isDigit(str.charAt(idx))) {
				j++;
			}
		}
		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);
			if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
				k++;
			}
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = pattern.matcher(str);
		while (m.find()) {
			q++;
		}
		if (k + j + q == str.length()) {
			return true;
		}

		return false;
	}
	
	public boolean isDEnglish(String str) {
		int k = 0;
		int i = str.length();
		
		for (int idx = 0; idx < i; idx++) {
			char c = str.charAt(idx);
			int tmp = (int) c;
			if ((tmp >= 'A' && tmp <= 'Z')) {
				k++;
			}
		}
		if (i ==  k) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 字串str是否含有数字
	 * 
	 * @param str
	 * @return true or false
	 */
	public  boolean isDigit(String str){
		for(int i = 0;i<str.length();i++){
			if(Character.isDigit(str.charAt(i))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 字串str是否含有字母
	 * 
	 * @param str
	 * @return true or false
	 */
	public  boolean isLetter(String str){
		for(int i = 0;i<str.length();i++){
			if(Character.isLetter(str.charAt(i))){
				return true;
			}
		}
		return false;
	}}
