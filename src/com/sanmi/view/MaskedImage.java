package com.sanmi.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
/**
 * ԶImageView
 * @author Administrator
 *
 */
public abstract class MaskedImage extends ImageView {
	private static final Xfermode MASK_XFERMODE;
	private Bitmap mask;
	private Paint paint;
	//private Paint circlePaint;
	Paint p;
	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	@SuppressLint("NewApi")
	private void init() {
		 if (Build.VERSION.SDK_INT >= 11) {
		        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		    }
		
	}
	public MaskedImage(Context paramContext) {
		super(paramContext);
		init();
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init();
	}

	
	public abstract Bitmap createMask();

	protected void onDraw(Canvas canvas) {
	//	super.onDraw(canvas);
		
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
	
		if(p==null){
			p=new Paint();
		}
		int width = this.getWidth();
		int height = this.getHeight();
		int layerCount=canvas.saveLayer(0.0F, 0.0F, width, height, p, 31);
		Drawable d = this.getDrawable();
		if (d == null) {
			return;
		}
		d.draw(canvas);
		
		
		d.setBounds(0, 0, width, height);
		d.draw(canvas);
		if(paint==null){
			 paint=new Paint();
			 paint.setXfermode(MASK_XFERMODE);
			 paint.setFilterBitmap(true);
		}
		
		if ((this.mask == null) || (this.mask.isRecycled())) {
				Bitmap localBitmap1 = createMask();
				this.mask = localBitmap1;
				
		}
		
		if(mask!=null){
			canvas.drawBitmap(mask, 0, 0, paint);
			mask.recycle();
			mask=null;
		}
		//canvas.drawBitmap(mask, 0, 0, paint);
		canvas.restoreToCount(layerCount);
		
	}
}
