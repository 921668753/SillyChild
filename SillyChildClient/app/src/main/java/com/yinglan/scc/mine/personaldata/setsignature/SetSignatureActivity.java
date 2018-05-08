package com.yinglan.scc.mine.personaldata.setsignature;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.StringConstants;
import com.common.cklibrary.common.ViewInject;
import com.kymjs.common.PreferenceHelper;
import com.yinglan.scc.R;
import com.yinglan.scc.loginregister.LoginActivity;
import com.yinglan.scc.main.MainActivity;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 设置个性签名
 * Created by Administrator on 2017/9/2.
 */

public class SetSignatureActivity extends BaseActivity implements SetSignatureContract.View {
    private SetSignatureContract.Presenter mPresenter;

    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;

    @BindView(id = R.id.et_signature)
    private EditText et_signature;


    @BindView(id = R.id.tv_number)
    private TextView tv_number;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_setsignature);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new SetSignaturePresenter(this);
        String signature = getIntent().getStringExtra("signature");
        if (!TextUtils.isEmpty(signature)) {
            et_signature.setText(signature);
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initTitle();
        changeInputView(et_signature, tv_number);
    }

    /**
     * 设置标题
     */
    public void initTitle() {
        titlebar.setTitleText(R.string.personalizedSignature);
        titlebar.setRightText(R.string.complete);
        titlebar.getRightCtv().setTextColor(getResources().getColor(R.color.greenColors));
        titlebar.getRightCtv().setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        BGATitleBar.SimpleDelegate simpleDelegate = new BGATitleBar.SimpleDelegate() {
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                aty.finish();
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                showLoadingDialog(getString(R.string.saveLoad));
                mPresenter.setupInfo(et_signature.getText().toString());
            }
        };
        titlebar.setDelegate(simpleDelegate);
    }


    @Override
    public void setPresenter(SetSignatureContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(String success, int flag) {
        dismissLoadingDialog();
        setResult(0, getIntent().putExtra("signature", et_signature.getText().toString()));
        finish();
    }

    @Override
    public void errorMsg(String msg, int flag) {
        dismissLoadingDialog();
        if (isLogin(msg)) {
            ViewInject.toast(getString(R.string.reloginPrompting));
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isRefreshMineFragment", false);
            PreferenceHelper.write(aty, StringConstants.FILENAME, "isReLogin", true);
            finish();
            KJActivityStack.create().finishToThis(LoginActivity.class, MainActivity.class);
            return;
        }
        ViewInject.toast(msg);
    }


    /**
     * 监听EditText输入改变
     */
    @SuppressWarnings("deprecation")
    public void changeInputView(final EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().toString().length() > 0 && view != null) {
                    view.setVisibility(View.VISIBLE);
                    ((TextView) view).setText(String.valueOf(editText.getText().toString().length()));
                } else {
                    view.setVisibility(View.GONE);
                    ((TextView) view).setText(String.valueOf(0));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
