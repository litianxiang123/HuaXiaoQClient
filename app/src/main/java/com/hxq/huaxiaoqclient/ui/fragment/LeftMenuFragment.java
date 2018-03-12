package com.hxq.huaxiaoqclient.ui.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午4:16:54
 * @描述 左侧菜单的fragment
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-04 16:27:53 +0800 (Sat, 04 Jul 2015) $
 * @ 当前版本: $Rev: 14 $
 */
public class LeftMenuFragment extends BaseFragment
{

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		TextView tv = new TextView(mainActivity);
		tv.setText("左侧菜单");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
