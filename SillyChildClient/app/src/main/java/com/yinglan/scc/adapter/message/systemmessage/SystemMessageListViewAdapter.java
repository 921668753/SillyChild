package com.yinglan.scc.adapter.message.systemmessage;

import android.content.Context;
import android.view.View;

import com.yinglan.scc.R;
import com.yinglan.scc.entity.message.systemmessage.SystemMessageListBean.DataBean;
import com.yinglan.scc.utils.GlideImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 系统消息 适配器
 * Created by Admin on 2017/8/15.
 */

public class SystemMessageListViewAdapter extends BGAAdapterViewAdapter<DataBean> {

    public SystemMessageListViewAdapter(Context context) {
        super(context, R.layout.item_systemmessage);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, DataBean listBean) {

        /**
         * 头像
         */
        GlideImageLoader.glideLoader(mContext, listBean.getNews_text(), viewHolderHelper.getImageView(R.id.img_head), 0, R.mipmap.avatar);

        /**
         * 未读消息数
         */
        viewHolderHelper.setText(R.id.tv_messageTag, "");
        if (listBean.getIs_read() == 1) {
            viewHolderHelper.setVisibility(R.id.tv_messageTag, View.GONE);
        } else {
            viewHolderHelper.setVisibility(R.id.tv_messageTag, View.VISIBLE);
        }
        /**
         * 标题
         */
        viewHolderHelper.setText(R.id.tv_title, listBean.getNews_subject());

        /**
         * 内容
         */
        viewHolderHelper.setText(R.id.tv_content, listBean.getNews_text());

        /**
         * 时间
         */
        viewHolderHelper.setText(R.id.tv_time, listBean.getPush_time());

    }

}