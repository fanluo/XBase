package com.allens.lib_base.bean;


import com.allens.lib_base.app.BaseApplication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInfo {

    /***
     * 单个文件最大多少M
     */
    @Builder.Default
    public int maxM = 3;

    /**
     * 最大文件数
     */
    @Builder.Default
    public int maxFileSize = 5;

    /***
     * 文件名称
     */
    @Builder.Default
    public String fileName = "logs";

    //tag
    @Builder.Default
    public String tag = "log";

    /**
     * isOpen
     */
    @Builder.Default
    public boolean isOpen = true;

    /**
     * 保存文件地址
     */
    @Builder.Default
    public String path = "";


}
