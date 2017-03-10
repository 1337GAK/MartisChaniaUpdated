

int tempSensor = 3;


OneWire sensor(tempSensor);

float getTemp() {
  byte data[12];
  byte addr[8];

  static int sensorIndex = 1;

  if (sensorIndex == 3) {
    sensorIndex = 1;
    sensor.reset_search();
    return getTemp();
  }
  
  if(!sensor.search(addr)) {
    sensor.reset_search();
    Serial.println("No device found");
    return -1000;
  }

  if(OneWire::crc8(addr, 7) != addr[7]) {
    Serial.println("CRC not valid");
    return -1000;
  }

  if(addr[0] != 0x10 && addr[0] != 0x28) {
    Serial.println("Device not recognized");
    return -1000;
  }

  sensor.reset();
  sensor.select(addr);
  sensor.write(0x44,1);

  byte present = sensor.reset();
  sensor.select(addr);
  sensor.write(0xBE);

  for(int i=0;i<9;i++) {
    data[i] = sensor.read();
  }

  //sensor.reset_search();
  sensorIndex++;

  byte MSB = data[1];
  byte LSB = data[0];

  float tempRead = ((MSB << 8 | LSB));
  float tempSum = tempRead / 16;
  return tempSum;
  
}


