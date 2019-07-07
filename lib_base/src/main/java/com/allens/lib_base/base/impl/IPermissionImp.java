package com.allens.lib_base.base.impl;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

public interface IPermissionImp {


    /***
     * 申请权限
     * @param permissions 权限
     * @return Observable
     */
    Observable<Boolean> permissionRequest(String... permissions);

    /***
     *
     * 同时请求多个权限（分别获取结果）
     * accept()方法会被调用多次，即申请了几个权限accept()方法就被调用了几次，
     * 每一次的调用都是对每一个权限的是否被授予权限的单独结果，因此该方法是对每个权限的单独处理。
     * @param permissions 权限
     * @return Observable
     */
    Observable<Permission> permissionRequestEach(String... permissions);

    /***
     *
     * @return 获取RxPermissions
     */
    RxPermissions getRxPermissions();
}
