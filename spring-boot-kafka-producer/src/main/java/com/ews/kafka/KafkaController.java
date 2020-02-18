package com.ews.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Autowired
    private PaymentProducer producer;

    @GetMapping(value = "/messages/{count}")
    public void send(@PathVariable("count") int count) {
        this.producer.send(count);
    }

}
