package processing.test.notafaceandroideditionv3;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NotAFaceAndroidEditionV3 extends PApplet {

/*============================================
 Not a Face
 ==============================================
 Not a Face is a simple game about tapping the
 face that does not have a mouth.
 
 Currently includes classic mode where you see
 how fast you can finish all the levels and 
 time rush where you see how many levels you 
 can finish in 60 seconds.
 ==============================================
 WILL REQUIRE USE OF ANDROID STUDIO FOR LATER
 RELEASES
 ==============================================
 Features to add:
 -High Score
 -Replay button
 -Centre the faces in the screen
 -Make a ///////////////////
 -Other game modes/levels such as scrolling
 rows of faces or faces floating around
 -Soundtrack -unsure of what it should sound like
 -Mute/Unmute button
 -Add a penalty for random tapping
 -Ads? https://forum.processing.org/two/discussion/7435/how-to-make-mobile-ads-work-in-your-processing-android-sketch
 ==============================================
 Bugs:
 -Button text is not centred (good enough)
 -Game does not fully reset inbetween rounds
  of time rush
 -faces lag (meh)
 -highscore isnt saved when app is closed - add file.io
 -classic high score is not displeyed properly
 ==============================================
 Things to do:
 -Test on different sized displays
 -Create art to be put on Google Play
 -Have people play test it
 -Crush Bugs
 -Add features
 ============================================*/
ArrayList <face> alFace = new ArrayList<face>();
int nNotaFace;
int nNumOfFacesAcross = 0;
int nFaceSize;// = displayWidth / nNumOfFacesAcross;
int nNumOfFacesDown;// = displayHeight / nFaceSize;
boolean isBlack = true;
int level = 1; //should be 1
int nScreen = 0; // 0 is start screen, 1 is menu, 2 is standard game, 3 is post standard game menu, 4 is time rush, 5 is time rush post screen, 6 is tutorial;
PFont font1;
PFont font;
int nTime;
int nTimeAtStart;
int nTimePlayed;
int nClassicHighScore = 0;
int nTimeRushHighScore = 0;
face face;
button classic;
button credits;
button menu;
button exit;
button timed;
button tutorial;
String sHour; 
String sMin; 
String sSec;
String sMillisec;
String sTime;
int nMillisec;
int nSec; 
int nMin; 
int nHour;
int nLevelsCompleted;
String sFastestTime;
public void setup() {
  font1 = createFont("Consolas", 120);
  font = createFont("Consolas", 200);
  orientation(PORTRAIT);
  ellipseMode(CORNER);
  rectMode(CENTER);
  
  frameRate(60);
  setFaces();
  //textFont(font1);
  textAlign(CENTER);
  face = new face(width / 2, height / 2, width / 3 * 2);
  classic = new button(width / 2, height / 8, width / 2, height / 8, "CLASSIC", color(255, 0, 0)); // to be replaced with createMenu()
  timed = new button(width / 2, height / 8 * 2, width / 2, height / 8, "TIME RUSH", color(255, 0, 0)); // to be replaced with createMenu()
  tutorial = new button(width / 2, height / 8 * 3, width / 2, height / 8, "TUTORIAL", color(255, 0, 0)); // to be replaced with createMenu()
  menu = new button(width / 2, height / 8 * 6, width / 2, height / 8, "MENU", color(255, 0, 0));
}
public void draw() {
  if (nScreen == 0) {
    background(20);
    fill(255);
    textFont(font);
    text("NOT A FACE", width /2, height / 5);
    textFont(font1);
    text("PRESS THE FACE \n TO START", width / 2, height / 5 * 4);
    face.update();
  } else if (nScreen == 1) {
    background(20);
    fill(255);
    classic.display();
    timed.display();
    tutorial.display();
  } else if (nScreen == 2) {
    background(20);
    if (level > 2) {
      if (isBlack == true) {
        background(20);
        isBlack = false;
      } else {
        background(255);
        isBlack = true;
      }
    }
    for (int j = 0; j < alFace.size(); j ++) {
      alFace.get(j).update();
    }
  } else if (nScreen == 3) {
    background(20);
    menu.display();
    displayTime();
    stringOfFastestTime();
    text("High Score: " + sFastestTime, width / 2, height / 4);
  } else if (nScreen == 4) {
    background(20);
    for (int j = 0; j < alFace.size(); j ++) {
      alFace.get(j).update();
    }
    if (hasTimePassed()) {
      if (nLevelsCompleted > nTimeRushHighScore) {
        nTimeRushHighScore = nLevelsCompleted;
      }
      nScreen = 5;
    }
  } else if (nScreen == 5) {
    background(20);
    fill(255);
    text("Your Score: " + nLevelsCompleted, width / 2, height / 2);
    text("Fastest Time: " + nTimeRushHighScore, width / 2, height / 4);
    menu.display();
  } else if (nScreen == 6) {
    background(20);
    fill(255);
    textFont(createFont("Consolas", 72));
    text("Basics: On every level there will be \n one face missing it's mouth \n and you beat the level by tapping it \n \n Classic: See how fast you can complete \n the original game \n \n Time Rush: See how many levels you can \n beat in one minute ", width / 2, height / 4);
    textFont(font1);
    menu.display();
  }
}

public void setFaces() {
  if (nNumOfFacesAcross == 20) { //should be 20
    if (level == 3) { // should be 3
      if (nScreen == 2) {
        nTimePlayed = millis() -nTimeAtStart;
        if (nClassicHighScore == 0 || nTimePlayed < nClassicHighScore) {
          nClassicHighScore = nTimePlayed;
        }
        nScreen = 3;
        level = 1;
        alFace.clear();
      }
    }
    nNumOfFacesAcross = 0;
    level ++;
  }
  if (nScreen == 4) {
    nNumOfFacesAcross = 0;
    level = 1;
  }
  nNumOfFacesAcross += 5;
  alFace.clear();
  nFaceSize = displayWidth / nNumOfFacesAcross;
  nNumOfFacesDown = displayHeight / nFaceSize - 1;
  for (int h = 0; h < nNumOfFacesDown*2; h ++) {
    for (int w = 0; w < nNumOfFacesAcross*2; w ++) {
      alFace.add(new face((nFaceSize/2 * w) + nFaceSize / 2, (nFaceSize/2 * h) + nFaceSize /2, nFaceSize));
      w ++;
    }
    h ++;
  }
  nNotaFace = (int)random(0, alFace.size());
  alFace.get(nNotaFace).isNotFace = true;
}

public void createMenu() {
  classic = new button(width / 2, height / 3, 500, 500, "PLAY", color(255, 0, 0));
  //credits = new button (width / 2, height / 3 * 2, 500, 500, "CREDITS");
}

public void mousePressed() {
  if (nScreen == 2) {
    for (int v = 0; v < alFace.size(); v++) {
      if (alFace.get(v).clickityClack(alFace.get(v).nX, alFace.get(v).nY, nFaceSize) && alFace.get(v).isNotFace == true) {
        println("hit");
        setFaces();
        break;
      }
    }
  } else if (nScreen == 0) {
    if (face.clickityClack(face.nX, face.nY, face.nFaceSize)) {
      nScreen = 1;
    }
  } else if (nScreen == 1) {
    if (classic.isHitButton(classic.nW, classic.nH, classic.nX, classic.nY)) {
      nScreen = 2;
      level = 1;
      nTimeAtStart = millis();
    }
    if (timed.isHitButton(timed.nW, timed.nH, timed.nX, timed.nY)) {
      nScreen = 4;
      nTimeAtStart = millis();
      nNumOfFacesAcross = 10;
      level = 1;
      nLevelsCompleted = 0;
    }
    if (tutorial.isHitButton(tutorial.nW, tutorial.nH, tutorial.nX, tutorial.nY)) {
      nScreen = 6;
    }
  } else if (nScreen == 3) {
    if (menu.isHitButton(menu.nW, menu.nH, menu.nX, menu.nY)) {
      nScreen = 1;
    }
  } else if (nScreen == 4) {
    for (int v = 0; v < alFace.size(); v++) {
      if (alFace.get(nNotaFace).clickityClack(alFace.get(nNotaFace).nX, alFace.get(nNotaFace).nY, nFaceSize) && alFace.get(nNotaFace).isNotFace == true) {
        println("hit");
        setFaces();
        nLevelsCompleted ++;
        break;
      }
    }
  } else if (nScreen == 5) {
    if (menu.isHitButton(menu.nW, menu.nH, menu.nX, menu.nY)) {
      nScreen = 1;
    }
  } else if (nScreen == 6) {
    if (menu.isHitButton(menu.nW, menu.nH, menu.nX, menu.nY)) {
      nScreen = 1;
    }
  }
}
class button {
  int nX;
  int nY;
  int nW;
  int nH;
  String sText;
  String nTempText;
  int c;
  button(int nTempX, int nTempY, int nTempW, int nTempH, String sTempText, int tempC) {
    nX = nTempX;
    nY = nTempY;
    nW = nTempW;
    nH = nTempH;
    sText = sTempText;
    c = tempC;
  }

  public void display() {
    fill(c);
    rect(nX, nY, nW, nH);
    fill(255);
    text(sText, nX, nY + nH / 3);
  }

  public boolean isHitButton(int nW, int nH, int nX, int nY) {
    if (mouseX >= nX - (nW / 2) && mouseX <= nX+(nW /2)
      && 
      mouseY >= nY - (nH / 2) && mouseY <= nY + (nH / 2)) {
      return true;
    } else {
      return false;
    }
  }
}
public void displayTime() {
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
  text("Your Time: " + sTime, width /2, height / 2);
}

public void stringOfFastestTime() {
  int nMillisec = (nClassicHighScore/10)%100;
  int nSec = (nClassicHighScore/1000)% 60;
  int nMin = (nClassicHighScore/(1000*60)) % 60;
  int nHour = (nClassicHighScore/ (1000*60*60)) % 24;
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
  sFastestTime = sHour +":"+sMin+":"+sSec+":"+sMillisec;
}

public boolean hasTimePassed() {
  if (millis() - nTimeAtStart >= 60000) { // should be 60000
    return true;
  } else {
    return false;
  }
}
class face {
  boolean isNotFace = false;
  int nX;
  int nY;
  int c = color(random(255), random(255), random(255));
  int c1 = color(random(255), random(255), random(255));
  int c2 = color(random(255), random(255), random(255));
  int r = round(random(-200, 200));
  int speed = round(random(-5, 5));
  int nFaceSize;
  int n10thFaceSize;
  int n5thFaceSize;
  int n5thFaceSizeX2;
  face(int nTempX, int nTempY, int nTempFaceSize) {
    nY = nTempY;
    nX = nTempX;
    nFaceSize = nTempFaceSize;
    n10thFaceSize = round(nFaceSize / 10);
    n5thFaceSize = round(nFaceSize / 5);
    n5thFaceSizeX2 = n5thFaceSize * 2;
  }
  public void update() {
    display();
    colorChange();
  }
  public void display() {
    ellipseMode(CENTER);
    fill(c);
    ellipse(nX, nY, nFaceSize, nFaceSize); //100
    fill(c1);
    pushMatrix();
    translate(nX, nY);
    rotate(radians(r));
    r += speed;
    ellipse(0 - n5thFaceSize, 0 - n10thFaceSize, n5thFaceSize, n5thFaceSize);
    popMatrix();
    pushMatrix();
    translate(nX, nY);
    rotate(radians(r));
    r += speed;
    ellipse(0 + n5thFaceSize, 0 - n10thFaceSize, n5thFaceSize, n5thFaceSize);
    popMatrix();
    fill(c2);
    if (!isNotFace) {
      pushMatrix();
      translate(nX, nY);
      rotate(radians(r));
      r += speed;
      ellipse(0, 0 + n5thFaceSize, n5thFaceSize, n5thFaceSizeX2);

      popMatrix();
    }
  }
  public void colorChange() {
    if (level > 1) {
      c = color(random(255), random(255), random(255));
      c1 = color(random(255), random(255), random(255));
      c2 = color(random(255), random(255), random(255));
    }
  }

  public boolean clickityClack(int nX, int nY, int nFaceSize) {
    if ((mouseX >= nX -(nFaceSize/2) && mouseX <= nX+(nFaceSize/2) && 
      mouseY >= nY-(nFaceSize/2) && mouseY <= nY + (nFaceSize/2))) {
      return true;
    }
    return false;
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NotAFaceAndroidEditionV3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
