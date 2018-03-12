package com.hxq.huaxiaoqclient.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.ali.auth.third.core.util.CommonUtils;

/**
 * 设置背景和字体色值
 * @author gaoyahang
 */
public class ViewBg {
	
	public void setBg(View view, String bgcolor) {
		// TODO Auto-generated method stub
		try {
			GradientDrawable d = new GradientDrawable();
			d.setColor(Color.parseColor(bgcolor));// 设置颜色
			view.setBackgroundDrawable(d);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	/**
	 * 设置TextView字体色值
	 * @author gaoyahang
	 * @param bgcolor
	 */
	public static void setTextColor(TextView tv, String bgcolor) {
		try {
			tv.setTextColor(Color.parseColor(bgcolor));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 设置背景 圆角
	 * 
	 * @param view
	 * @param bgcolor
	 * @param radios
	 */
	public void setBg(Context context , View view, String bgcolor, int radios) {
		// TODO Auto-generated method stub
		try {
			GradientDrawable d = new GradientDrawable();
			d.setColor(Color.parseColor(bgcolor));// 设置颜色
			d.setGradientType(GradientDrawable.RECTANGLE);
			d.setCornerRadius(com.hxq.huaxiaoqclient.utils.CommonUtils.dp2px(context, radios));
 			view.setBackgroundDrawable(d);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	/**
	 * 设置 圆角
	 *
	 * @param view
	 * @param bgcolor
	 * @param radios
	 */
	public void setBg(View view, String bgcolor, float[] radios) {
		// TODO Auto-generated method stub
		try {
			GradientDrawable d = new GradientDrawable();
			d.setColor(Color.parseColor(bgcolor));// 设置颜色
			d.setGradientType(GradientDrawable.RECTANGLE);
			d.setCornerRadii(radios);
  			view.setBackgroundDrawable(d);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 设置背景 圆角, 边线
	 * 
	 * @param view
	 * @param bgcolor
	 * @param radios
	 */
	public void setBg(Context context , View view, String bgcolor, int lineColor, int radios) {
		// TODO Auto-generated method stub
		setBg(context , view, bgcolor, lineColor, 1, radios);

	}

	public void setBg(Context context , View view, String bgcolor, String lineColor, int radios) {
		// TODO Auto-generated method stub
		setBg(context , view, bgcolor, Color.parseColor(lineColor), 1, radios);
	}
	public void setBg(Context context , View view, String bgcolor, String lineColor, int linewidth , int radios) {
		// TODO Auto-generated method stub
		setBg(context , view, bgcolor, Color.parseColor(lineColor), linewidth, radios);
	}
	public void setBg(Context context , View view, String bgcolor, int lineColor, int lineWidth,
					  int radios) {
		// TODO Auto-generated method stub
		try {
			GradientDrawable d = new GradientDrawable();
			d.setColor(Color.parseColor(bgcolor));// 设置颜色
			d.setStroke(lineWidth, lineColor);
			d.setGradientType(GradientDrawable.RECTANGLE);
			d.setCornerRadius(com.hxq.huaxiaoqclient.utils.CommonUtils.dp2px(context , radios));
			view.setBackgroundDrawable(d);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void setBg(Context mContext, View view, int bgcolor) {
		// TODO Auto-generated method stub
		try {
			GradientDrawable d = new GradientDrawable();
			d.setColor(mContext.getResources().getColor(bgcolor));// 设置颜色
			view.setBackgroundDrawable(d);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void setBg(View view) {
		// TODO Auto-generated method stub
		try {
			GradientDrawable d = new GradientDrawable();
			view.setBackgroundDrawable(d);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
