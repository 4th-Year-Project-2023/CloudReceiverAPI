package major.project.receiver.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import major.project.receiver.telemetry.Telemetry;
import major.project.receiver.telemetry.TelemetryRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Component
public class MultiThreadedReceiver {

    @Autowired
    @Setter
    @Getter
    private TelemetryRepository telemetryRepository;


    private MqttClient mqttClient1;
    private MqttClient mqttClient2;

    public void start() {
        try {
            mqttClient1 = new MqttClient("tcp://localhost:1883", MqttAsyncClient.generateClientId(), new MemoryPersistence());
            mqttClient2 = new MqttClient("tcp://localhost:1883", MqttAsyncClient.generateClientId(), new MemoryPersistence());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(new MqttSubscriber(mqttClient1, "topic1", telemetryRepository));
        executor.execute(new MqttSubscriber(mqttClient2, "topic2", telemetryRepository));
    }

    @Slf4j
    private static class MqttSubscriber implements Runnable {


        @Autowired
        private Telemetry telemetry;

        @Autowired
        @Setter
        @Getter
        private TelemetryRepository telemetryRepository;

        @Setter
        @Getter
        private final MqttClient mqttClient;
        @Setter
        @Getter
        private final String topic;

        public MqttSubscriber(MqttClient mqttClient, String topic, TelemetryRepository telemetryRepository) {
            this.mqttClient = mqttClient;
            this.topic = topic;
            this.telemetryRepository = telemetryRepository;
        }

        @Override
        public void run() {
            try {
                MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                mqttConnectOptions.setUserName("edwin");
                mqttConnectOptions.setPassword("edu".toCharArray());
                mqttConnectOptions.setCleanSession(true);
                mqttConnectOptions.setConnectionTimeout(3000);
                mqttConnectOptions.setAutomaticReconnect(true);
                mqttConnectOptions.setMaxInflight(10);
                mqttClient.connect(mqttConnectOptions);
                mqttClient.subscribe(topic);
                mqttClient.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable throwable) {
                        // Handle connection lost event
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        // Handle incoming message event
                        log.info(String.valueOf(message));
                        telemetry = new ObjectMapper().readValue(message.getPayload(), Telemetry.class);
                        telemetry = telemetryRepository.save(telemetry);
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        // Handle message delivery complete event
                    }
                });
            } catch (MqttException e) {
                // Handle MQTT exception
            }

        }
    }
}
