package com.daop.sso.controller;

import com.daop.sso.common.utils.ResultVoUtil;
import com.daop.sso.kafka.KafkaProducer;
import com.daop.sso.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: spring-sso
 * @BelongsPackage: com.daop.sso.controller
 * @Description: Kafka 控制器
 * @DATE: 2021-03-04
 * @AUTHOR: Administrator
 **/
@RestController
public class KafkaController {
//    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/kafka")
    public ResultVO send() {
        kafkaProducer.send("This is kafka message!");
        return ResultVoUtil.success();
    }
}
