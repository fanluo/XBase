package com.allens.allenstools.test_act;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;

import io.reactivex.functions.Consumer;

@Route(path = "/act/permission")
public class PermissionAct extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.activity_permission;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(Bundle savedInstanceState) {
        $(R.id.button).setOnClickListener(v -> {
            permissionRequest(Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) {
                            LogHelper.i("给了权限");
                            toast("给了权限");
                        } else {
                            LogHelper.i("拒绝权限");
                            toast("拒绝权限");
                        }
                    });
        });

        $(R.id.button2).setOnClickListener(v -> {
            permissionRequestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            // `permission.name` is granted !
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                        }
                    });
        });
    }

    @Override
    protected void initListener() {

    }
}
