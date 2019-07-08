package com.allens.lib_base.retrofit.download.bean;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class DownLoadBean {

    private Throwable throwable;

    private Boolean isSuccess;
}
