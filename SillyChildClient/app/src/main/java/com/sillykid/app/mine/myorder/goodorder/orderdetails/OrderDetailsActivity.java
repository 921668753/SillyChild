package com.sillykid.app.mine.myorder.goodorder.orderdetails;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.StringConstants;
import com.common.cklibrary.common.ViewInject;
import com.common.cklibrary.utils.ActivityTitleUtils;
import com.common.cklibrary.utils.JsonUtil;
import com.common.cklibrary.utils.MathUtil;
import com.common.cklibrary.utils.TimeCount;
import com.common.cklibrary.utils.myview.ChildListView;
import com.kymjs.common.PreferenceHelper;
import com.kymjs.common.StringUtils;
import com.sillykid.app.R;
import com.sillykid.app.adapter.mine.myorder.orderdetails.OrderDetailGoodViewAdapter;
import com.sillykid.app.entity.mine.myorder.OrderDetailBean;
import com.sillykid.app.entity.mine.myorder.OrderDetailBean.DataBean.ItemListBean;
import com.sillykid.app.loginregister.LoginActivity;
import com.sillykid.app.mine.myorder.goodorder.ordertracking.OrderTrackingActivity;
import com.sillykid.app.mine.myorder.goodorder.dialog.OrderBouncedDialog;
import com.sillykid.app.mine.myorder.goodorder.orderevaluation.PublishedeEvaluationActivity;
import com.sillykid.app.mine.myshoppingcart.makesureorder.PaymentOrderActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 我的订单---订单详情
 * Created by Administrator on 2017/9/2.
 */

public class OrderDetailsActivity extends BaseActivity implements OrderDetailsContract.View, TimeCount.TimeCountCallBack {

    /**
     * 等待买家付款
     */
    @BindView(id = R.id.ll_waitingPayment)
    private LinearLayout ll_waitingPayment;

    @BindView(id = R.id.tv_waitingPayment)
    private TextView tv_waitingPayment;

    /**
     * 请在50分钟内付款，逾期订单将被取消
     */
    @BindView(id = R.id.tv_lateCancelled)
    private TextView tv_lateCancelled;

    /**
     * 等待发货
     */
    @BindView(id = R.id.ll_waitSending)
    private LinearLayout ll_waitSending;

    /**
     * 等待发货图片
     */
    @BindView(id = R.id.img_waitSending)
    private ImageView img_waitSending;

    /**
     * 等待发货
     */
    @BindView(id = R.id.tv_waitSending)
    private TextView tv_waitSending;

    /**
     * 顺丰快递
     */
    @BindView(id = R.id.tv_courierName)
    private TextView tv_courierName;

    /**
     * 您的订单已进入库房，准备出库
     */
    @BindView(id = R.id.tv_orderCourierInformation)
    private TextView tv_orderCourierInformation;

    /**
     * 2018-04-30  13:32
     */
    @BindView(id = R.id.tv_orderCourierTime)
    private TextView tv_orderCourierTime;

    /**
     * 收货人
     */

    @BindView(id = R.id.ll_name)
    private LinearLayout ll_name;

    @BindView(id = R.id.tv_name)
    private TextView tv_name;

    /**
     * 收货人手机号
     */
    @BindView(id = R.id.tv_phone)
    private TextView tv_phone;

    /**
     * 收货人地址
     */
    @BindView(id = R.id.ll_address)
    private LinearLayout ll_address;

    @BindView(id = R.id.tv_address)
    private TextView tv_address;

    /**
     * 收货人详细地址
     */
    @BindView(id = R.id.tv_detailedAddress)
    private TextView tv_detailedAddress;

    /**
     * 商品信息列表
     */
    @BindView(id = R.id.lv_shopGoods)
    private ChildListView lv_shopGoods;

    /**
     * 商品合计
     */
    @BindView(id = R.id.tv_goodsMoney)
    private TextView tv_goodsMoney;

    /**
     * 运费
     */
    @BindView(id = R.id.tv_freightMoney)
    private TextView tv_freightMoney;

    /**
     * 优惠券
     */
    @BindView(id = R.id.tv_couponsMoney)
    private TextView tv_couponsMoney;

    /**
     * 优惠活动
     */
    @BindView(id = R.id.tv_preferentialActivities)
    private TextView tv_preferentialActivities;

    /**
     * 订单号
     */
    @BindView(id = R.id.tv_orderCode)
    private TextView tv_orderCode;

    /**
     * 提交时间
     */
    @BindView(id = R.id.tv_submitTime)
    private TextView tv_submitTime;

    /**
     * 支付方式
     */
    @BindView(id = R.id.ll_modePayment)
    private LinearLayout ll_modePayment;

    @BindView(id = R.id.tv_modePayment)
    private TextView tv_modePayment;

    /**
     * 支付金额
     */
    @BindView(id = R.id.ll_amountRealPay)
    private LinearLayout ll_amountRealPay;

    @BindView(id = R.id.tv_amountRealPay)
    private TextView tv_amountRealPay;

    /**
     * 付款时间
     */
    @BindView(id = R.id.ll_paymentTime)
    private LinearLayout ll_paymentTime;

    @BindView(id = R.id.tv_paymentTime)
    private TextView tv_paymentTime;

    /**
     * 发货时间
     */
    @BindView(id = R.id.ll_deliveryTime)
    private LinearLayout ll_deliveryTime;

    @BindView(id = R.id.tv_deliveryTime)
    private TextView tv_deliveryTime;


    /**
     * 底部按钮
     */
    @BindView(id = R.id.ll_bottom)
    private LinearLayout ll_bottom;

    /**
     * 取消订单
     */
    @BindView(id = R.id.tv_cancelOrder, click = true)
    private TextView tv_cancelOrder;

    /**
     * 确认付款
     */
    @BindView(id = R.id.tv_payment, click = true)
    private TextView tv_payment;

    /**
     * 提醒发货
     */
    @BindView(id = R.id.tv_remindDelivery, click = true)
    private TextView tv_remindDelivery;

    /**
     * 查看物流
     */
    @BindView(id = R.id.tv_checkLogistics, click = true)
    private TextView tv_checkLogistics;

    /**
     * 确认收货
     */
    @BindView(id = R.id.tv_confirmReceipt, click = true)
    private TextView tv_confirmReceipt;

    /**
     * 评价订单
     */
    @BindView(id = R.id.tv_appraiseOrder, click = true)
    private TextView tv_appraiseOrder;

    private int orderId = 0;

    private int status = 0;

    /**
     * 倒计时内部类
     */
    private TimeCount time;

    private long lastTime = 0;

//    private OptionsPickerView<LogisBean.DataBean> pvOptions = null;
//
//
//    private List<LogisBean.DataBean> logisList;


    private OrderDetailGoodViewAdapter mAdapter;

    private OrderBouncedDialog orderBouncedDialog = null;

    private String payMoney = "";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_orderdetails);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new OrderDetailsPresenter(this);
        mAdapter = new OrderDetailGoodViewAdapter(this);
        orderId = getIntent().getIntExtra("order_id", 0);
        showLoadingDialog(getString(R.string.dataLoad));
        ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
        initDialog();
    }

    private void initDialog() {
        orderBouncedDialog = new OrderBouncedDialog(aty, "") {
            @Override
            public void toDialogDo(int id, int flag) {
                if (flag == 0) {
                    showLoadingDialog(getString(R.string.cancelLoad));
                    ((OrderDetailsContract.Presenter) mPresenter).postOrderCancel(aty, id);
                } else if (flag == 1) {
                    showLoadingDialog(getString(R.string.submissionLoad));
                    ((OrderDetailsContract.Presenter) mPresenter).postOrderRemind(aty, id);
                } else if (flag == 2) {
                    showLoadingDialog(getString(R.string.submissionLoad));
                    ((OrderDetailsContract.Presenter) mPresenter).postOrderConfirm(aty, id);
                }
            }
        };
    }


    @Override
    public void initWidget() {
        super.initWidget();
        initTitle();
        lv_shopGoods.setAdapter(mAdapter);
    }


    /**
     * 设置标题
     */
    public void initTitle() {
        ActivityTitleUtils.initToolbar(aty, getString(R.string.orderDetails), true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_cancelOrder:
                if (orderBouncedDialog == null) {
                    initDialog();
                }
                if (orderBouncedDialog != null && !orderBouncedDialog.isShowing()) {
                    orderBouncedDialog.show();
                    orderBouncedDialog.setIdContentFlag(orderId, getString(R.string.confirmCancellationOrder), 0);
                }
                break;
            case R.id.tv_payment:
                ((OrderDetailsContract.Presenter) mPresenter).getMyWallet(aty);
                break;
            case R.id.tv_remindDelivery:
                if (orderBouncedDialog == null) {
                    initDialog();
                }
                if (orderBouncedDialog != null && !orderBouncedDialog.isShowing()) {
                    orderBouncedDialog.show();
                    orderBouncedDialog.setIdContentFlag(orderId, getString(R.string.confirmReminderDelivery), 1);
                }
                break;
            case R.id.tv_checkLogistics:
                Intent checkLogisticsIntent = new Intent(aty, OrderTrackingActivity.class);
                checkLogisticsIntent.putExtra("order_id", String.valueOf(orderId));
                showActivity(aty, checkLogisticsIntent);
                break;
            case R.id.tv_confirmReceipt:
                if (orderBouncedDialog == null) {
                    initDialog();
                }
                if (orderBouncedDialog != null && !orderBouncedDialog.isShowing()) {
                    orderBouncedDialog.show();
                    orderBouncedDialog.setIdContentFlag(orderId, getString(R.string.confirmReceiptGoods), 2);
                }
                break;
            case R.id.tv_appraiseOrder:
                Intent publishedeEvaluationIntent = new Intent(aty, PublishedeEvaluationActivity.class);
                publishedeEvaluationIntent.putExtra("order_id", String.valueOf(orderId));
                showActivity(aty, publishedeEvaluationIntent);
                break;
        }
    }

    @Override
    public void setPresenter(OrderDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        if (flag == 0) {
            OrderDetailBean orderDetailBean = (OrderDetailBean) JsonUtil.getInstance().json2Obj(success, OrderDetailBean.class);
            if (orderDetailBean != null && orderDetailBean.getData() != null && orderDetailBean.getData().getOrder_id() > 0 && orderDetailBean.getData().getStatus() == 1) {
                obligationGood(orderDetailBean);
            } else if (orderDetailBean != null && orderDetailBean.getData() != null && orderDetailBean.getData().getOrder_id() > 0 && orderDetailBean.getData().getStatus() == 2) {
                sendGoodsGood();
            } else if (orderDetailBean != null && orderDetailBean.getData() != null && orderDetailBean.getData().getOrder_id() > 0 && orderDetailBean.getData().getStatus() == 3) {
                waitGoodsGood();
            } else if (orderDetailBean != null && orderDetailBean.getData() != null && orderDetailBean.getData().getOrder_id() > 0 && orderDetailBean.getData().getStatus() == 4) {
                completedGood();
            } else if (orderDetailBean != null && orderDetailBean.getData() != null && orderDetailBean.getData().getOrder_id() > 0 && orderDetailBean.getData().getStatus() == 5) {
                completedGood();
            } else if (orderDetailBean != null && orderDetailBean.getData() != null && orderDetailBean.getData().getOrder_id() > 0 && orderDetailBean.getData().getStatus() == 7) {
                afterSaleGood();
            } else {
                tradingClosedGood();
            }
            status = orderDetailBean.getData().getStatus();
            mAdapter.setStatus(status, orderDetailBean.getData().getPaymoney());
            tv_name.setText(orderDetailBean.getData().getShip_name());
            tv_phone.setText(orderDetailBean.getData().getShip_mobile());
            tv_address.setText(orderDetailBean.getData().getShipping_area());
            tv_detailedAddress.setText(orderDetailBean.getData().getShip_addr());
            if (orderDetailBean.getData().getItemList() != null && orderDetailBean.getData().getItemList().size() > 0) {
                mAdapter.clear();
                mAdapter.addMoreData(orderDetailBean.getData().getItemList());
                tv_goodsMoney.setText(getString(R.string.renminbi) + calculatePrice(orderDetailBean.getData().getItemList()));
            }
            payMoney = MathUtil.keepTwo(StringUtils.toDouble(orderDetailBean.getData().getOrder_amount()));
            tv_freightMoney.setText(getString(R.string.renminbi) + MathUtil.keepTwo(StringUtils.toDouble(orderDetailBean.getData().getShip_money())));
            if (StringUtils.toDouble(orderDetailBean.getData().getBouns_money()) <= 0) {
                tv_couponsMoney.setText(getString(R.string.renminbi) + MathUtil.keepTwo(StringUtils.toDouble(orderDetailBean.getData().getBouns_money())));
            } else {
                tv_couponsMoney.setText(getString(R.string.renminbi) + "-" + MathUtil.keepTwo(StringUtils.toDouble(orderDetailBean.getData().getBouns_money())));
            }
            if (StringUtils.toDouble(orderDetailBean.getData().getActivity()) <= 0) {
                tv_preferentialActivities.setText(getString(R.string.renminbi) + MathUtil.keepTwo(StringUtils.toDouble(orderDetailBean.getData().getActivity())));
            } else {
                tv_preferentialActivities.setText(getString(R.string.renminbi) + "-" + MathUtil.keepTwo(StringUtils.toDouble(orderDetailBean.getData().getActivity())));
            }
            tv_orderCode.setText(orderDetailBean.getData().getSn());
            tv_submitTime.setText(orderDetailBean.getData().getCreate_time());
            if (orderDetailBean.getData().getPayment_type().contains("qianbao")) {
                tv_modePayment.setText(getString(R.string.balancePay));
            } else if (orderDetailBean.getData().getPayment_type().contains("weixin")) {
                tv_modePayment.setText(getString(R.string.weChatPay));
            } else if (orderDetailBean.getData().getPayment_type().contains("zhifubao")) {
                tv_modePayment.setText(getString(R.string.alipayToPay));
            } else if (orderDetailBean.getData().getPayment_type().contains("yinlian")) {
                tv_modePayment.setText(getString(R.string.unionpayPay));
            }
            tv_paymentTime.setText(orderDetailBean.getData().getPay_time());
            tv_deliveryTime.setText(orderDetailBean.getData().getAllocation_time());
            dismissLoadingDialog();
        } else if (flag == 1 || flag == 4) {
            showLoadingDialog(getString(R.string.dataLoad));
            ((OrderDetailsContract.Presenter) mPresenter).getOrderDetails(orderId);
        } else if (flag == 2) {
            ViewInject.toast(getString(R.string.remindSuccessfulDelivery));
            dismissLoadingDialog();
        } else if (flag == 3) {
            dismissLoadingDialog();
            String balance = PreferenceHelper.readString(aty, StringConstants.FILENAME, "balance");
            Intent intent = new Intent(aty, PaymentOrderActivity.class);
            intent.putExtra("order_id", String.valueOf(orderId));
            intent.putExtra("last_time", lastTime / 1000);
            intent.putExtra("money", payMoney);
            intent.putExtra("balance", MathUtil.keepTwo(StringUtils.toDouble(balance)));
            showActivity(aty, intent);
        }
    }

    /**
     * 计算价格
     */
    private String calculatePrice(List<ItemListBean> list) {
        double totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            int num = StringUtils.toInt(list.get(i).getNum(), 0);
            double price = StringUtils.toDouble(list.get(i).getPrice());
            totalPrice = totalPrice + price * num;
        }
        return MathUtil.keepTwo(totalPrice);
    }

    /**
     * 待付款
     */
    private void obligationGood(OrderDetailBean orderDetailBean) {
        ll_waitingPayment.setVisibility(View.VISIBLE);
        tv_waitingPayment.setText(getString(R.string.waitingPayment));
        ll_waitSending.setVisibility(View.GONE);
        tv_courierName.setVisibility(View.GONE);
        tv_orderCourierInformation.setVisibility(View.GONE);
        tv_orderCourierTime.setVisibility(View.GONE);
        ll_name.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        ll_modePayment.setVisibility(View.GONE);
        ll_amountRealPay.setVisibility(View.GONE);
        ll_paymentTime.setVisibility(View.GONE);
        ll_deliveryTime.setVisibility(View.GONE);
        if (StringUtils.toLong(orderDetailBean.getData().getLastTime()) > 0) {
            time = new TimeCount(StringUtils.toLong(orderDetailBean.getData().getLastTime()) * 1000 - System.currentTimeMillis(), 1000);
            time.setTimeCountCallBack(this);
            time.start();
        } else {
            tv_waitingPayment.setText(getString(R.string.tradingClosed));
            tv_lateCancelled.setVisibility(View.GONE);
        }
        ll_bottom.setVisibility(View.VISIBLE);
        tv_cancelOrder.setVisibility(View.VISIBLE);
        tv_payment.setVisibility(View.VISIBLE);
        tv_remindDelivery.setVisibility(View.GONE);
        tv_checkLogistics.setVisibility(View.GONE);
        tv_confirmReceipt.setVisibility(View.GONE);
        tv_appraiseOrder.setVisibility(View.GONE);
    }

    /**
     * 待发货
     */
    private void sendGoodsGood() {
        ll_waitingPayment.setVisibility(View.GONE);
        ll_waitSending.setVisibility(View.VISIBLE);
        tv_courierName.setVisibility(View.GONE);
        img_waitSending.setImageResource(R.mipmap.order_to_be_shipped_icon);
        tv_waitSending.setText(getString(R.string.waitSending));
        tv_orderCourierInformation.setVisibility(View.GONE);
        tv_orderCourierTime.setVisibility(View.GONE);
        ll_name.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        ll_modePayment.setVisibility(View.VISIBLE);
        ll_amountRealPay.setVisibility(View.VISIBLE);
        ll_paymentTime.setVisibility(View.VISIBLE);
        ll_deliveryTime.setVisibility(View.GONE);

        ll_bottom.setVisibility(View.VISIBLE);
        tv_cancelOrder.setVisibility(View.GONE);
        tv_payment.setVisibility(View.GONE);
        tv_remindDelivery.setVisibility(View.VISIBLE);
        tv_checkLogistics.setVisibility(View.GONE);
        tv_confirmReceipt.setVisibility(View.GONE);
        tv_appraiseOrder.setVisibility(View.GONE);
    }

    /**
     * 待收货
     */
    private void waitGoodsGood() {
        tv_courierName.setVisibility(View.VISIBLE);
        ll_waitingPayment.setVisibility(View.GONE);
        ll_waitSending.setVisibility(View.VISIBLE);
        img_waitSending.setImageResource(R.mipmap.order_shipped_icon);
        tv_waitSending.setText(getString(R.string.sellerShippedGoods));
        tv_orderCourierInformation.setVisibility(View.VISIBLE);
        tv_orderCourierTime.setVisibility(View.VISIBLE);
        ll_name.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        ll_modePayment.setVisibility(View.VISIBLE);
        ll_amountRealPay.setVisibility(View.VISIBLE);
        ll_paymentTime.setVisibility(View.VISIBLE);
        ll_deliveryTime.setVisibility(View.VISIBLE);

        ll_bottom.setVisibility(View.VISIBLE);
        tv_cancelOrder.setVisibility(View.GONE);
        tv_payment.setVisibility(View.GONE);
        tv_remindDelivery.setVisibility(View.GONE);
        tv_checkLogistics.setVisibility(View.VISIBLE);
        tv_confirmReceipt.setVisibility(View.VISIBLE);
        tv_appraiseOrder.setVisibility(View.GONE);
    }

    /**
     * 已完成---0 未平价 1 已评价
     */
    private void completedGood() {
        ll_waitingPayment.setVisibility(View.GONE);
        ll_waitSending.setVisibility(View.VISIBLE);
        tv_courierName.setVisibility(View.VISIBLE);
        img_waitSending.setImageResource(R.mipmap.order_complete_icon);
        tv_waitSending.setText(getString(R.string.transactionCompleted));
        tv_orderCourierInformation.setVisibility(View.VISIBLE);
        tv_orderCourierTime.setVisibility(View.VISIBLE);
        ll_name.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        ll_modePayment.setVisibility(View.VISIBLE);
        ll_amountRealPay.setVisibility(View.VISIBLE);
        ll_paymentTime.setVisibility(View.VISIBLE);
        ll_deliveryTime.setVisibility(View.VISIBLE);

        ll_bottom.setVisibility(View.VISIBLE);
        tv_cancelOrder.setVisibility(View.GONE);
        tv_payment.setVisibility(View.GONE);
        tv_remindDelivery.setVisibility(View.GONE);
        tv_checkLogistics.setVisibility(View.GONE);
        tv_confirmReceipt.setVisibility(View.GONE);
        tv_appraiseOrder.setVisibility(View.VISIBLE);
    }

    /**
     * 售后
     */
    private void afterSaleGood() {
        ll_waitingPayment.setVisibility(View.GONE);
        ll_waitSending.setVisibility(View.VISIBLE);
        tv_courierName.setVisibility(View.VISIBLE);
        img_waitSending.setImageResource(R.mipmap.order_after_sale_icon);
        tv_waitSending.setText(getString(R.string.applyAfterSales));
        tv_orderCourierInformation.setVisibility(View.VISIBLE);
        tv_orderCourierTime.setVisibility(View.VISIBLE);
        ll_name.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        ll_modePayment.setVisibility(View.VISIBLE);
        ll_amountRealPay.setVisibility(View.VISIBLE);
        ll_paymentTime.setVisibility(View.VISIBLE);
        ll_deliveryTime.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.GONE);
    }


    /**
     * 交易关闭
     */
    private void tradingClosedGood() {
        ll_waitingPayment.setVisibility(View.VISIBLE);
        tv_courierName.setVisibility(View.GONE);
        tv_waitingPayment.setText(getString(R.string.tradingClosed));
        tv_lateCancelled.setVisibility(View.GONE);
        ll_waitSending.setVisibility(View.GONE);
        tv_orderCourierInformation.setVisibility(View.GONE);
        tv_orderCourierTime.setVisibility(View.GONE);
        ll_name.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        ll_modePayment.setVisibility(View.GONE);
        ll_amountRealPay.setVisibility(View.GONE);
        ll_paymentTime.setVisibility(View.GONE);
        ll_deliveryTime.setVisibility(View.GONE);
        ll_bottom.setVisibility(View.GONE);
    }


    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if (isLogin(msg)) {
            //  ViewInject.toast(getString(R.string.reloginPrompting));
            showActivity(this, LoginActivity.class);
            if (flag == 0) {
                finish();
            }
            return;
        }
        ViewInject.toast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  pvOptions = null;
        if (orderBouncedDialog != null) {
            orderBouncedDialog.cancel();
        }
        if (time != null) {
            time.cancel();
        }
        time = null;
    }

    @Override
    public void onFinishTime() {
        lastTime = 0;
        tv_waitingPayment.setText(getString(R.string.tradingClosed));
        tv_lateCancelled.setVisibility(View.GONE);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        lastTime = millisUntilFinished;
        String millisUntilFinish = "";
        long minute = millisUntilFinished / 60000;
        long seconds = millisUntilFinished % 60000;
        if (minute > 0) {
            millisUntilFinish = minute + getString(R.string.minute) + seconds + getString(R.string.seconds);
        } else {
            millisUntilFinish = seconds + getString(R.string.seconds);
        }
        tv_lateCancelled.setText(getString(R.string.lateCancelled) + millisUntilFinish + getString(R.string.lateCancelled1));
    }
}
