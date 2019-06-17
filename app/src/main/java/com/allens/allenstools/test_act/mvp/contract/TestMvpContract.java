package com.allens.allenstools.test_act.mvp.contract;

import com.allens.lib_base.mvp.BaseModel;
import com.allens.lib_base.mvp.BaseView;

public interface TestMvpContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView {

        void showToast();
    }

    interface Presenter {

        void testToast();
    }
}
