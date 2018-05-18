package com.yinglan.scc.homepage.goodslist.shop;

import com.common.cklibrary.common.BasePresenter;
import com.common.cklibrary.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface AllGoodsContract {
    interface Presenter extends BasePresenter {

        /**
         * 获取店铺的全部商品列表
         */
        void getStoreGoodsList(int storeid, int cat_id, int page, String sort);
    }

    interface View extends BaseView<Presenter, String> {
    }

}


