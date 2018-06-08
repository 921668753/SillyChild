package com.sillykid.app.mine.myorder.goodorder.aftersalesdetails;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.httputil.HttpUtilParams;
import com.common.cklibrary.utils.httputil.ResponseListener;
import com.kymjs.common.StringUtils;
import com.kymjs.rxvolley.client.HttpParams;
import com.sillykid.app.R;
import com.sillykid.app.retrofit.RequestClient;

/**
 * Created by ruitu on 2018/9/24.
 */

public class ApplyAfterSalesPresenter implements ApplyAfterSalesContract.Presenter {
    private ApplyAfterSalesContract.View mView;

    public ApplyAfterSalesPresenter(ApplyAfterSalesContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getOrderRefundList() {
        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        RequestClient.getOrderRefundList(KJActivityStack.create().topActivity(), httpParams, new ResponseListener<String>() {

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

    @Override
    public void postOrderRefund(String orderid, String typeCode, String reasonCode, String reasonDetail, String apply_alltotal) {

        if (StringUtils.isEmpty(typeCode)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseSelect) + KJActivityStack.create().topActivity().getString(R.string.afterType), 1);
            return;
        }

        if (StringUtils.isEmpty(reasonCode)) {
            mView.errorMsg(KJActivityStack.create().topActivity().getString(R.string.pleaseSelect) + KJActivityStack.create().topActivity().getString(R.string.afterWhy), 1);
            return;
        }

        HttpParams httpParams = HttpUtilParams.getInstance().getHttpParams();
        httpParams.put("order_id", orderid);
        httpParams.put("typeCode", typeCode);
        httpParams.put("reason", reasonCode);
        httpParams.put("reasonDetail", reasonDetail);
        httpParams.put("apply_alltotal", apply_alltotal);
        RequestClient.postOrderRefund(KJActivityStack.create().topActivity(), httpParams, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(response, 1);
            }

            @Override
            public void onFailure(String msg) {
                mView.errorMsg(msg, 1);
            }
        });
    }
}
