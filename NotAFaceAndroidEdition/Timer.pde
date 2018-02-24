void displayTime() {
  int nMillisec = (nTimePlayed/10)%100;
  int nSec = (nTimePlayed/1000)% 60;
  int nMin = (nTimePlayed/(1000*60)) % 60;
  int nHour = (nTimePlayed/ (1000*60*60)) % 24;
  /*https://www.processing.org/discourse/alpha/board_Syntax_action_display_num_1087808386.html
   https://processing.org/reference/conditional.html
   below is a shorter way to "if/else" that works w/ strings
   basically works like this - (expression) ? statement1 : statement2;
   what the 4 lines of code does below is that if the value of whatever
   place (ex. seconds) is less than 10, then it adds a "0" in front 
   so that it displays "09:54" instead of "9:54"*/
  String sHour = (nHour<10)? "0"+str(nHour):str(nHour); 
  String sMin = (nMin<10)? "0"+str(nMin):str(nMin);
  String sSec = (nSec<10)? "0"+str(nSec):str(nSec);
  String sMillisec= (nMillisec<10)? "0"+str(nMillisec):str(nMillisec);
  String sTime = sHour +":"+sMin+":"+sSec+":"+sMillisec;
  text(sTime, width /2, height / 2);
}

boolean checkTimePassed() {
  if (millis() - nTimeAtStart == 6000) {
    return true;
  } else {
    return false;
}