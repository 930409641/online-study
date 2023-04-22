package com.yxq.msmservice.service;

import java.util.HashMap;

public interface MsmService {
    boolean send(HashMap<String, Object> param, String phone);
}
