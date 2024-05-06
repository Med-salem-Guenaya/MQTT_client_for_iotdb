package org.example;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {

        MQTT mqtt = new MQTT();
        mqtt.setHost("192.168.56.101", 1883);
        mqtt.setUserName("root");
        mqtt.setPassword("root");

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String payload = String.format("{\n" +
                    "\"device\":\"root.sg.d1\",\n" +
                    "\"timestamp\":%d,\n" +
                    "\"measurements\":[\"s1\"],\n" +
                    "\"values\":[%f]\n" +
                    "}", System.currentTimeMillis(), random.nextDouble());

            connection.publish("root.sg.d1.s1", payload.getBytes(), QoS.AT_LEAST_ONCE, false);

            // used a timer to make data transmission/collection spread out
            TimeUnit.SECONDS.sleep(1);
            // should be replaced with a better solution, like "ScheduledExecutorService"
        }

        connection.disconnect();



    }
}