package com.xiaosi.wx.service;

import com.xiaosi.wx.annotation.Policy;
import com.xiaosi.wx.enums.PolicyEnum;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    @Policy(PolicyEnum.OPEN)
    public String openPolicy() {
        return "Open Policy Service";
    }

    @Policy(PolicyEnum.RESTRICTED)
    public String restrictedPolicy() {
        return "Restricted Policy Service";
    }
}
