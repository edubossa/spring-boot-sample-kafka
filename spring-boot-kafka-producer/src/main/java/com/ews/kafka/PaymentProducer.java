/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ews.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PaymentProducer {

    @Value("${cloudkarafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Payment> kafkaTemplate;

    public void send(int count) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                sendKafka(count);
            });
            thread.setName("PRODUCER [" + i + "]");
            thread.start();
        }
    }

    private void sendKafka(int numberMsgs) {
        for(int i = 1; i < numberMsgs; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Payment payment = Payment.of(UUID.randomUUID().toString(), new BigDecimal(i + ".0"));
            this.kafkaTemplate.send(topic, payment);
            log.info("Producer send payment [" + payment + "] to " + topic);
        }
    }


}
