package com.daop.sso.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.server.quota.ClientQuotaCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.kafka
 * @Description: 提供者
 * @DATE: 2021-03-04
 * @AUTHOR: Administrator
 **/
//@Component
@Slf4j
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String TOPIC_TEST = "topic.test";

    public static final String GROUP_MESSAGE = "topic.message";
    public static final String GROUP_MAIL = "topic.mail";


    public void send(Object obj) {
        String obj2String = JSONObject.toJSONString(obj);
        log.info("准备发送的消息为：{}", obj2String);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_TEST, obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("{} - 生产者，发送消息成功：{}", TOPIC_TEST, result.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("{} - 生产者，发送消息失败：{}", TOPIC_TEST, ex.getMessage());
            }
        });
    }

}
