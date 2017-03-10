
int pulses = 0;
int bpm = 0;
void pulseInterrupt() {
  pulses++;
  //Serial.print("Pulse ");
  //Serial.println(pulses);
}


int getBPM() {
  
  blinkBlue(16,200,200);

  
  pulses = 0;
  bpmTimer = millis();
  while(millis() - bpmTimer < 20000) {
   blue(); 
  }
  resetLED();
  noInterrupts();
  bpm = pulses * 3 ;
  //Serial.println("BPM :");
  //Serial.println(bpm);
  interrupts();
  return bpm;
}
