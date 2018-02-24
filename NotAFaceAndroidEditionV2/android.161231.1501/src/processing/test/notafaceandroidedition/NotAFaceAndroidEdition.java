package processing.test.notafaceandroidedition;

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

public class NotAFaceAndroidEdition extends PApplet {

//SS1 e1a
ArrayList <face> alFace = new ArrayList<face>();
int nNotaFace;
float nNumOfFacesAcross = 0;
float nFaceSize;// = displayWidth / nNumOfFacesAcross;
float nNumOfFacesDown;// = displayHeight / nFaceSize;
boolean isBlack = true;
int level = 3; //should be 1
int nScreen = 0; // 0 is start screen, 1 is menu, 2 is standard game;
PFont font1;
PFont font;
int nTime;
int nTimeAtStart;
int nTimePlayed;
face face;
button play;
button credits;
button menu;
button exit;
String sHour; 
String sMin; 
String sSec;
String sMillisec;
String sTime;
int nMillisec;
int nSec; 
int nMin; 
int nHour;
public void setup() {
  font1 = createFont("Consolas", 120);
  font = createFont("Consolas", 200);
  orientation(PORTRAIT);
  ellipseMode(CORNER);
  rectMode(CENTER);
  
  frameRate(9001);
  setFaces();
  //textFont(font1);
  textAlign(CENTER);
  face = new face(width / 2, height / 2, width / 3 * 2);
  play = new button(width / 2, height / 4, 500, 300, "PLAY", color(255, 0, 0)); // to be replaced with createMenu()
  menu = new button(width / 2, height / 4 * 3, 500, 300, "MENU", color(255, 0, 0));
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
    play.display();
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
  }
}

public void setFaces() {
  if (nNumOfFacesAcross == 10) { //should be 20
    if (level == 3) {
      nTimePlayed = millis() -nTimeAtStart;
      nScreen = 3;
      level = 1;
      alFace.clear();
    }
    nNumOfFacesAcross = 0;
    level ++;
  }
  alFace.clear();
  nNumOfFacesAcross+=5;
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
  play = new button(width / 2, height / 3, 500, 500, "PLAY", color(255, 0, 0));
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
    if (play.isHitButton(play.nW, play.nH, play.nX, play.nY)) {
      nScreen = 2;
      nTimeAtStart = millis();
    }
  } else if (nScreen == 3) {
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
    text(sText, nX, nY + nY / 14);
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
  text(sTime, width /2, height / 2);
}
class face {
  boolean isNotFace = false;
  float nX;
  float nY;
  int c = color(random(255), random(255), random(255));
  int c1 = color(random(255), random(255), random(255));
  int c2 = color(random(255), random(255), random(255));
  float r = round(random(-200, 200));
  float speed = random(-10, 10);
  float nFaceSize;
  face(float nTempX, float nTempY, float nTempFaceSize) {
    nY = nTempY;
    nX = nTempX;
    nFaceSize = nTempFaceSize;
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
    ellipse(0 - (nFaceSize / 5), 0 - nFaceSize / 10, nFaceSize / 5, nFaceSize / 5);
    popMatrix();
    pushMatrix();
    translate(nX, nY);
    rotate(radians(r));
    r += speed;
    ellipse(0 + (nFaceSize / 5), 0 - nFaceSize / 10, nFaceSize / 5, nFaceSize / 5);
    popMatrix();
    fill(c2);
    if (!isNotFace) {
      pushMatrix();
      translate(nX, nY);
      rotate(radians(r));
      r += speed;
            ellipse(0, 0 + nFaceSize / 5, nFaceSize / 5, nFaceSize / 5 * 2);

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

  public boolean clickityClack(float fX, float fY, float nFaceSize) {
    if ((mouseX >= fX -(nFaceSize/2) && mouseX <= fX+(nFaceSize/2) && 
      mouseY >= fY-(nFaceSize/2) && mouseY <= fY + (nFaceSize/2))) {
      return true;
    }
    return false;
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NotAFaceAndroidEdition" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
