int bluePin = 4;



void blue() {
  digitalWrite(bluePin,LOW);
}


void resetLED() {
  digitalWrite(bluePin,HIGH);
}

void blinkBlue(int blinks, int tBlink, int tOff) {
  for(int i =0;i<blinks;i++) {
      blue();
      delay(tBlink);
      resetLED();
      delay(tOff);
  }
}


