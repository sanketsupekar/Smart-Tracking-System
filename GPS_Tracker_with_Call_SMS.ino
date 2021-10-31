
// TTGO T-Call pin definitions
#define MODEM_RST            5
#define MODEM_PWKEY          4
#define MODEM_POWER_ON       23
#define MODEM_TX             27
#define MODEM_RX             26
#define I2C_SDA              21
#define I2C_SCL              22
#define TINY_GSM_MODEM_SIM800
#include <TinyGPS++.h> //https://github.com/mikalhart/TinyGPSPlus
#include <AceButton.h> // https://github.com/bxparks/AceButton
#include <TinyGsmClient.h> // https://github.com/vshymanskyy/TinyGSM
#include <ArduinoHttpClient.h>
#include <Wire.h>
#include "utilities.h"

#define TINY_GSM_MODEM_SIM800
#define SMS_Button 34
#define Call_Button 35
using namespace ace_button;

//Firebase setting 
const char server[] = "pioneering-flag-294609.firebaseio.com";
const int  port = 443;
const String UPDATE_PATH = "gpsTracker/locationInfo"; // firebase root table
// Emergency Number and Message
String message = "It's an Emergency. I'm at this location ";
String mobile_number = "9130420859";
String message_with_data;
const char apn[]  = "airtelgprs.com";
const char user[] = "";
const char pass[] = "";
// Variables for storing GPS Data
int count=0;
float latitude;
float longitude;
float speed;
float satellites;
String direction;
String fireData="";
int battery,signalQuality;
int connectionCheak;
String gpsdate;

// Switch
ButtonConfig config1;
AceButton call_button(&config1);
ButtonConfig config2;
AceButton sms_button(&config2);

void handleEvent_call(AceButton*, uint8_t, uint8_t);
void handleEvent_sms(AceButton*, uint8_t, uint8_t);

// Set serial for GPS Module
#define SerialMon Serial
// Hardware Serial for builtin GSM Module
#define SerialAT Serial1

#ifdef DUMP_AT_COMMANDS
#include <StreamDebugger.h>
  StreamDebugger debugger(SerialAT, SerialMon);
  TinyGsm modem(debugger);
#else
  TinyGsm modem(SerialAT);
#endif

TinyGsmClientSecure client(modem);
HttpClient https(client, server, port);


//static const int RXPin = 4, TXPin = 5;
static const uint32_t GPSBaud = 9600;

TinyGPSPlus gps;
unsigned int move_index = 1;

          void setup()
          {
                // Set console baud rate
                Serial.begin(9600);
                delay(10);
              
                // Keep power when running from battery
                Wire.begin(I2C_SDA, I2C_SCL);
                bool   isOk = setPowerBoostKeepOn(1);
                SerialMon.println(String("IP5306 KeepOn ") + (isOk ? "OK" : "FAIL"));
              
                // Set-up modem reset, enable, power pins
                pinMode(MODEM_PWKEY, OUTPUT);
                pinMode(MODEM_RST, OUTPUT);
                pinMode(MODEM_POWER_ON, OUTPUT);
              
              
                pinMode(SMS_Button, INPUT);
                pinMode(Call_Button, INPUT);
              
              
                digitalWrite(MODEM_PWKEY, LOW);
                digitalWrite(MODEM_RST, HIGH);
                digitalWrite(MODEM_POWER_ON, HIGH);
              
                // Set GSM module baud rate and UART pins
                SerialAT.begin(115200, SERIAL_8N1, MODEM_RX, MODEM_TX);
                delay(3000);
              
                // Restart takes quite some time
                // To skip it, call init() instead of restart()
                SerialMon.println("Initializing modem...");
                modem.restart();
              
                String modemInfo = modem.getModemInfo();
                SerialMon.print("Modem: ");
                SerialMon.println(modemInfo);
              
                // Unlock your SIM card with a PIN
                //modem.simUnlock("1234");
              
                SerialMon.print("Waiting for network...");
                if (!modem.waitForNetwork(240000L)) {
                  SerialMon.println(" fail");
                  delay(10000);
                  return;
                }
                SerialMon.println(" OK");
              
                if (modem.isNetworkConnected()) {
                  SerialMon.println("Network connected");
                }
              
                SerialMon.print(F("Connecting to APN: "));
                SerialMon.print(apn);
                if (!modem.gprsConnect(apn, user, pass)) {
                  SerialMon.println(" fail");
                  delay(10000);
                  return;
                }
                SerialMon.println(" OK");
                //  ss.begin(GPSBaud);
                
              
                config1.setEventHandler(handleEvent_call);
                config2.setEventHandler(handleEvent_sms);
              
                call_button.init(Call_Button);
                sms_button.init(SMS_Button);
          }

        void loop()
            {
           
              while (Serial.available() > 0)
              {
                     if (gps.encode(Serial.read()))
                        {
                          gpsDataCollect();
                          
                        }
                        delay(250);
                        sms_button.check();
                        call_button.check();
              }
           
            sms_button.check();
            call_button.check();
            }

//Collect GPS Data
void gpsDataCollect()
    {
          if (gps.location.isValid() )
              {
                Serial.println("Working On GPS Location DataCollection....");
                latitude = (gps.location.lat());     //Storing the Lat. and Lon.
                longitude = (gps.location.lng());
                speed = gps.speed.kmph();               //get speed
                direction = TinyGPSPlus::cardinal(gps.course.value()); // get the direction
                satellites = gps.satellites.value();    //get number of satellites
                battery = modem.getBattPercent();
                signalQuality = modem.getSignalQuality();
                connectionCheak=random(300);
                
                gpsdate=(String)gps.date.day();
                gpsdate+=":"+(String)gps.date.month();
                gpsdate+=":"+(String)gps.date.year();
                fireData="";
                fireData += "{";
                fireData += " \"Latitude\"  : \"" + String(latitude,12)+"\",";
                fireData += " \"Longitude\"  : \"" + String(longitude,12)+"\",";
                fireData += " \"Speed\"  : \"" + String(speed)+"\",";
                fireData += " \"Direction\"  : \"" + String(direction)+"\",";
                fireData += " \"Satellites\"  : \"" + String(satellites)+"\",";
                fireData += " \"Battery\"  : \"" + String(battery)+"\",";
                fireData += " \"SignalQuality\"  : \"" + String(signalQuality)+"\",";
                fireData += " \"Date\"  : \"" + String(gpsdate)+"\",";
                fireData += " \"connectionCheak\"  : \"" + String(connectionCheak)+"\"";
                fireData += "}";
                
                gpsDataDisplay();
                sendData("PUT", UPDATE_PATH, fireData, &https);

              }
              else
              {
                Serial.println("GPS Location Not Valid");
              }
      
    }

 // Display GPS Data
void gpsDataDisplay()
  {
          Serial.println("Date: "+ String(gpsdate));
      Serial.print("LAT:  ");
      Serial.println(latitude, 12);  // float to x decimal places
      Serial.print("LONG: ");
      Serial.println(longitude, 12);
      Serial.println("speed: "+ String(speed));
      Serial.println("direction: "+ String(direction));
      Serial.println("satellites: "+ String( satellites));
      Serial.println("battery: "+ String(battery));
      Serial.println("signalQuality: "+ String( signalQuality));
      Serial.println("connectionCheak: "+ String(connectionCheak));

  }
       

//send data to firebase
void sendData(const char* method, const String & path , const String & data, HttpClient* http) 
{
                http->connectionKeepAlive(); //Currently, this is needed for HTTPS
                String url;
                if (path[0] != '/') 
                  {
                    url = "/";
                  }
                    url += path + ".json";
                    url += "?print=silent";
                    url += "&x-http-method-override=";
                    url += String(method);
                    
                    String contentType = "application/json";
                    http->post(url, contentType, data);
                    
                    int statusCode = http->responseStatusCode();
                    http->responseBody();
                    Serial.print("Status code: ");
                    Serial.println(url);
                    Serial.println(contentType);
                    Serial.println(data);
                    Serial.println(statusCode); 
                if (!http->connected()) {
                  Serial.println();
                  http->stop(); // Shutdown
                  Serial.println("HTTP POST disconnected");
                  
                }
}

void handleEvent_sms(AceButton* /* button */, uint8_t eventType,uint8_t /* buttonState */) 
                     {
                        switch (eventType) 
                        {
                            case AceButton::kEventPressed:
                                Serial.println("kEventPressed");
                                message_with_data = (message+"http://www.google.com/maps/place/" + String(latitude, 12) + "," + String(longitude, 12));
                                modem.sendSMS(mobile_number, message_with_data);
                                message_with_data = "";
                                break;
                            case AceButton::kEventReleased:
                                Serial.println("kEventReleased");
                                break;
                          }
                      }
void handleEvent_call(AceButton* /* button */, uint8_t eventType,uint8_t /* buttonState */) 
                      {
                        switch (eventType) 
                        {
                          case AceButton::kEventPressed:
                              Serial.println("kEventPressed");
                              modem.callNumber(mobile_number);
                              break;
                          case AceButton::kEventReleased:
                              Serial.println("kEventReleased");
                              break;
                        }
                      }
