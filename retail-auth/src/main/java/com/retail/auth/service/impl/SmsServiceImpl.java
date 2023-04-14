package com.retail.auth.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.retail.auth.service.SmsService;
import com.retail.auth.vo.SmsResultVo;
import com.retail.common.exception.BizException;
import com.retail.common.result.Result;
import com.retail.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: retail-cloud
 * @BelongsPackage: com.retail.auth.service.impl
 * @Classname: SmsServiceImpl
 * @CreateTime: 2023-03-24  11:27
 * @Created by: 喵喵
 * @Description:
 * @Version: 发送短信
 */
@Service
@Log4j2
public class SmsServiceImpl implements SmsService {
    @Value("${sms.appCode}")
    private String appCode;
    @Value("${sms.httpUrl}")
    private String httpUrl;
    @Override
    public void sendSms(String phone,String code){

        Map map = new HashMap<>();
        map.put("mobile",phone);
        map.put("param","**code**:"+code+",**minute**:5");
        map.put("smsSignId","2e65b1bb3d054466b82f0c9d125465e2");
        map.put("templateId","908e94ccf08b4476ba6c876d13f084ad");
        String str = HttpUtil.createPost(httpUrl).header("Authorization", "APPCODE " + appCode)
                .form(map).execute().body();
        log.info("response->"+str);
        if (StringUtils.isBlank(str)){
//            throw new BizException(501,"调用短信接口失败");
             Result.error("调用短信接口失败");
        }
        SmsResultVo smsResultVo = JSON.parseObject(str, SmsResultVo.class);
        if (smsResultVo.getCode()!=0){
//            throw new BizException(501,"调用短信接口失败,msg"+smsResultVo.getMsg());
            Result.error("调用短信接口失败,msg"+smsResultVo.getMsg());
        }

    }

}
