package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.init.InitFunc;

public class DataSourceInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {
        System.out.println("DataSourceInitFunc");
    }
}
