int gsrPin = 3;

float getGSR() {
  int a = averageAnalogRead(gsrPin);
  return a * 3.3 / 1.024;
}

