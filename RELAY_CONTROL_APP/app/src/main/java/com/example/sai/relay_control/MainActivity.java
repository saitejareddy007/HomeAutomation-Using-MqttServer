package com.example.sai.relay_control;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.relay1);
        final Button button2 = (Button) findViewById(R.id.relay2);
        final Button button3 = (Button) findViewById(R.id.relay3);
        final Button button4 = (Button) findViewById(R.id.relay4);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://test.mosquitto.org:1883", clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        //options.setUserName("dkkbstvv");options.setPassword("guJK8JJA68cL".toCharArray());

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    try{
                        client.publish(topic,"*".getBytes(),0,false);

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                    //Toast.makeText(MainActivity.this,"Connected!",Toast.LENGTH_LONG).show();
                    try {
                        client.subscribe("homeAutomation/saiteja/057/userConsol/relay1Status", 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                //Toast.makeText(MainActivity.this, "1",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                //Toast.makeText(MainActivity.this,"Your request is failed to initiate",Toast.LENGTH_LONG).show();
                            }
                        });
                        client.subscribe("homeAutomation/saiteja/057/userConsol/relay2Status", 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                //Toast.makeText(MainActivity.this, "1",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                //Toast.makeText(MainActivity.this,"Your request is failed to initiate",Toast.LENGTH_LONG).show();
                            }
                        });
                        client.subscribe("homeAutomation/saiteja/057/userConsol/relay3Status", 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                //Toast.makeText(MainActivity.this, "1",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                //Toast.makeText(MainActivity.this,"Your request is failed to initiate",Toast.LENGTH_LONG).show();
                            }
                        });
                        client.subscribe("homeAutomation/saiteja/057/userConsol/relay4Status", 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                //Toast.makeText(MainActivity.this, "1",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                //Toast.makeText(MainActivity.this,"Your request is failed to initiate",Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    client.setCallback(new MqttCallback() {
                        public void connectionLost(Throwable cause) {}

                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            //System.out.println("Message: " + message.toString().equals("Relay1 is on"));
                            if(message.toString().equals("Relay1 is on")){
                                button1.setBackgroundColor(Color.GREEN);
                            }
                            if(message.toString().equals("Relay1 is off")){
                                button1.setBackgroundColor(Color.RED);
                            }
                            if(message.toString().equals("Relay2 is on")){
                                button2.setBackgroundColor(Color.GREEN);
                            }
                            if(message.toString().equals("Relay2 is off")){
                                button2.setBackgroundColor(Color.RED);
                            }
                            if(message.toString().equals("Relay3 is on")){
                                button3.setBackgroundColor(Color.GREEN);
                            }
                            if(message.toString().equals("Relay3 is off")){
                                button3.setBackgroundColor(Color.RED);
                            }
                            if(message.toString().equals("Relay4 is on")){
                                button4.setBackgroundColor(Color.GREEN);
                            }
                            if(message.toString().equals("Relay4 is off")){
                                button4.setBackgroundColor(Color.RED);
                            }

                            //Toast.makeText(MainActivity.this, message.toString(),Toast.LENGTH_LONG).show();
                        }

                        public void deliveryComplete(IMqttDeliveryToken token) {}
                    });
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this,"Connection failed!",Toast.LENGTH_LONG).show();
                }


            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

        button1.setOnClickListener(onclick);
        button2.setOnClickListener(onclick);
        button3.setOnClickListener(onclick);
        button4.setOnClickListener(onclick);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_LONG).show();
            switch(view.getId())
            {
                case R.id.relay1:
                    //Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                    try{
                        client.publish(topic,"1".getBytes(),0,false);

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.relay2:
                    try{
                        client.publish(topic,"2".getBytes(),0,false);

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.relay3:
                    try{
                        client.publish(topic,"3".getBytes(),0,false);

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.relay4:
                    try{
                        client.publish(topic,"4".getBytes(),0,false);

                    }catch (MqttException e){
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };

    public static String topic="homeAutomation/saiteja/057/deviceConsol";

    @Override
    public void onClick(View view) {

    }

}
