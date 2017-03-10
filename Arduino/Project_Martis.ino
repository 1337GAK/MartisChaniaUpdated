#include <OneWire.h>
#include <SPI.h>


unsigned long bpmTimer;

void setup() {
  Serial.begin(115200);
  pinMode(4,OUTPUT);
  resetLED();
  
  //Attach interrupt to hearbeat pin2
  attachInterrupt(digitalPinToInterrupt(2), pulseInterrupt, RISING);
}


void loop() {

  if(Serial.available()) {
    byte a = Serial.read();
    //Serial.write(a);
    if(a == (byte)'m') {
          
          int uvi = (int)uvIndex();
          String uvindex = String(uvi);
          if(uvindex.length() == 1) {
            uvindex = "0" + uvindex;
          }

           //Ambient
          float temp1 = getTemp();
          String ambientTemp = String(temp1).substring(0,4);
          
          //Skin
          float temp2 = getTemp();
          String bodyTemp = String(temp2).substring(0,4);
      
          float gsr = getGSR();
          String galvanic = String((int)gsr);
          if (galvanic.length() == 3) {
            galvanic = "0" + galvanic;
          } else if (galvanic.length() == 2) {
            galvanic  = "00" + galvanic;
          } else if (galvanic.length() == 1) {
            galvanic = "000" + galvanic;
          }
  
          int bpm = getBPM();
          String BPM = String(bpm);
          if (BPM.length() == 2) {
            BPM  = "0" + BPM;
          } else if (BPM.length() == 1) {
            BPM = "00" + BPM;
          }


          //String data = String(uvi) +String(temp1) +String(temp2)+ String(gsr)+ String(bpm);
          
          String data = uvindex +ambientTemp+bodyTemp+galvanic+BPM;

          Serial.print(data);
          

    } else if ( a == (byte)'c') {
          int uvi = (int)uvIndex();
          String uvindex = String(uvi);
          if(uvindex.length() == 1) {
            uvindex = "0" + uvindex;
          }

           //Ambient
          float temp1 = getTemp();
          String ambientTemp = String(temp1).substring(0,4);
          
          //Skin
          float temp2 = getTemp();
          String bodyTemp = String(temp2).substring(0,4);
      
          float gsr = getGSR();
          String galvanic = String((int)gsr);
          if (galvanic.length() == 3) {
            galvanic = "0" + galvanic;
          } else if (galvanic.length() == 2) {
            galvanic  = "00" + galvanic;
          } else if (galvanic.length() == 1) {
            galvanic = "000" + galvanic;
          }
  

          String data = uvindex +ambientTemp+bodyTemp+galvanic+"000";

          Serial.print(data);
    }
    
  }

 

}
