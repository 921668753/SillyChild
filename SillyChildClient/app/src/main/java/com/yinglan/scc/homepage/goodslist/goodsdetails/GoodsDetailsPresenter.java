package com.yinglan.scc.homepage.goodslist.goodsdetails;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.httputil.HttpUtilParams;
import com.common.cklibrary.utils.httputil.ResponseListener;
import com.kymjs.rxvolley.client.HttpParams;
import com.yinglan.scc.retrofit.RequestClient;

/**
 * Created by ruitu on 2016/9/24.
 */

public class GoodsDetailsPresenter implements GoodsDetailsContract.Presenter {
    private GoodsDetailsContract.View mView;

    public GoodsDetailsPresenter(GoodsDetailsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getGoodDetail(int goodsid) {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("goodsid", goodsid);
        RequestClient.getGoodDetail(KJActivityStack.create().topActivity(), httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 0);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 0);
            }
        });
    }

}
