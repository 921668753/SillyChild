package com.yinglan.scc.mine.personaldata.setsex;

import com.common.cklibrary.common.BasePresenter;
import com.common.cklibrary.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface SetSexContract {
    interface Presenter extends BasePresenter {
        /**
         * 更改用户信息
         */
        void setupInfo(String newnickname);

    }

    interface View extends BaseView<Presenter, String> {
    }

}


