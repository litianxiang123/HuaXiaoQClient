

package com.hxq.huaxiaoqclient.ui.fragment;

import android.view.View;

import com.hxq.huaxiaoqclient.R;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午4:17:20
 * @描述 主界面的fragment
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-04 17:09:14 +0800 (Sat, 04
 *     Jul 2015) $ @ 当前版本: $Rev: 18 $
 */
public class MainContentFragment extends BaseFragment
{


	@Override
	public View initView() {
		View root = View.inflate(mainActivity, R.layout.fragment_content_view,
				null);
		return root;
	}
}
