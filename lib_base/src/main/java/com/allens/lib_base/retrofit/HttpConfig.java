package com.allens.lib_base.retrofit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpConfig {

    public Boolean connectTime;
    public Boolean readTime;
    public Boolean writeTime;
    public Boolean retryOnConnectionFailure;
    public Boolean isLog;
    public Boolean logToFile;
}
