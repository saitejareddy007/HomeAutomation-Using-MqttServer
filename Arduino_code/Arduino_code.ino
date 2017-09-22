/*
 * 
 Basic ESP8266 MQTT example

 This sketch demonstrates the capabilities of the pubsub library in combination
 with the ESP8266 board/library.

 It connects to an MQTT server then:
  - publishes "hello world" to the topic "outTopic" every two seconds
  - subscribes to the topic "inTopic", printing out any messages
    it receives. NB - it assumes the received payloads are strings not binary
  - If the first character of the topic "inTopic" is an 1, switch ON the ESP Led,
    else switch it offm

 It will reconnect to the server if the connection is lost using a blocking
 reconnect function. See the 'mqtt_reconnect_nonblocking' example for how to
 achieve the same result without blocking the main loop.

 To install the ESP8266 board, (using Arduino 1.6.4+):
  - Add the following 3rd party board manager under "File -> Preferences -> Additional Boards Manager URLs":
       http://arduino.esp8266.com/stable/package_esp8266com_index.json
  - Open the "Tools -> Board -> Board Manager" and click install for the ESP8266"
  - Select your ESP8266 in "Tools -> Board"

*/
#define relay1 D4
#define relay2 D5
#define relay3 D6
#define relay4 D7

#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// Update these with values suitable for your network.

const char* ssid        = "sai";
const char* password    = "saiteja123#";
const char* mqtt_server = "test.mosquitto.org";


WiFiClient espClient;
PubSubClient client(espClient);

/*
long lastMsg = 0;
char msg[50];
int value = 0;
*/

void setup_wifi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  //randomSeed(micros());
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();
  if((char)payload[0] == '5'){
    delay(500);
    if(digitalRead(relay1)==HIGH) {
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay1 is off"); 
    } 
    else{
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay1 is on");
    }
    if(digitalRead(relay2)==HIGH) {
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay2 is off"); 
    } 
    else{
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay2 is on");
    }
    if(digitalRead(relay3)==HIGH) {
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay3 is off"); 
    } 
    else{
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay3 is on");
    }
    if(digitalRead(relay4)==HIGH) {
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay4 is off"); 
    } 
    else{
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay4 is on");
    }
  }
  if ((char)payload[0] == '1') {
    if(digitalRead(relay1)==HIGH) {
      digitalWrite(relay1, LOW); 
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay1 is on"); 
    } 
    else{
      digitalWrite(relay1, HIGH);
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay1 is off");
    }
  }
  
  else if ((char)payload[0] == '2') {
    if(digitalRead(relay2)==HIGH){
      digitalWrite(relay2, LOW);
      client.publish("homeAutomation/saiteja/057/userConsol/relay2Status", "Relay2 is on");
    } 
    else{
      digitalWrite(relay2, HIGH);
      client.publish("homeAutomation/saiteja/057/userConsol/relay2Status", "Relay2 is off");
    }
  }
  
  else if ((char)payload[0] == '3') {
    if(digitalRead(relay3)==HIGH){
      digitalWrite(relay3, LOW);
      client.publish("homeAutomation/saiteja/057/userConsol/relay3Status", "Relay3 is on");
    } 
    else{
      digitalWrite(relay3, HIGH);
      client.publish("homeAutomation/saiteja/057/userConsol/relay3Status", "Relay3 is off");
    }
  }
  
  else if ((char)payload[0] == '4') {
    if(digitalRead(relay4)==HIGH){
      digitalWrite(relay4, LOW);
      client.publish("homeAutomation/saiteja/057/userConsol/relay4Status", "Relay4 is on"); 
    } 
    else{
      digitalWrite(relay4, HIGH);
      client.publish("homeAutomation/saiteja/057/userConsol/relay4Status", "Relay4 is off");
    }
  }
  
  else {
    digitalWrite(BUILTIN_LED, HIGH);  
  }

}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "ESP8266Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("homeAutomation/saiteja/057/userConsol", "hello boss, Your are ready to control your home.");
      client.publish("homeAutomation/saiteja/057/userConsol/relay1Status", "Relay1 is on");
      client.publish("homeAutomation/saiteja/057/userConsol/relay2Status", "Relay2 is on");
      client.publish("homeAutomation/saiteja/057/userConsol/relay3Status", "Relay3 is on");
      client.publish("homeAutomation/saiteja/057/userConsol/relay4Status", "Relay4 is on");
      // ... and resubscribe
      client.subscribe("homeAutomation/saiteja/057/deviceConsol");
      
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {
  pinMode(relay1, OUTPUT);
  pinMode(relay2, OUTPUT);
  pinMode(relay3, OUTPUT);
  pinMode(relay4, OUTPUT);
  Serial.begin(115200);
  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {

  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  /*long now = millis();
  if (now - lastMsg > 2000) {
    lastMsg = now;
    ++value;
    snprintf (msg, 75, "hello world #%ld", value);
    Serial.print("Publish message: ");
    Serial.println(msg);
    client.publish("outTopic", msg);
  }*/
}
