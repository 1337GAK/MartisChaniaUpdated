int UVOUT = 6;
int REF_3V3 = 7;



float uvIndex() {
  int uvLevel = averageAnalogRead(UVOUT);//sensor output
  int refLevel = averageAnalogRead(REF_3V3);//3.3Volt pin 

  //Use the 3.3V power pin as a reference to get a very accurate output value from sensor
  float outputVoltage = 3.3 / refLevel * uvLevel;

  //Convert the voltage to a UV intensity level(mW/cm^2)
  //returning value to the app
  float uvIntensity = 151.93*outputVoltage-150.41;

  float uvindex;
 
  uvindex =uvIntensity/25;

  return uvindex;
}



//Obtain the output voltage based on the average of 8 measurments to be more accurate
int averageAnalogRead(int pinToRead){
  byte numberOfReadings = 64;
  unsigned int runningValue = 0; 
  for(int x = 0 ; x < numberOfReadings ; x++)
    runningValue = runningValue + analogRead(pinToRead);//sum of the 8 measurments
  runningValue = runningValue/ numberOfReadings;//average 
  return(runningValue);    
}
