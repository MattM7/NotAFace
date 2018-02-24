/*============================================ //<>//
 Not a Face
 ==============================================
 Not a Face is a simple game about tapping the
 face that does not have a mouth. Currently
 includes classic mode where you see how fast
 you can finish all the levels and time rush
 where you see how many levels you can finish
 in 60 seconds.
 ==============================================
 Features to add:
 -Credits
 -High Score
 -Settings
 -Other game modes/levels such as scrolling
 rows of faces or faces floating around
 -Soundtrack -unsure of what it should sound like
 -Add a penalty for random tapping
 -Ads? https://forum.processing.org/two/discussion/7435/how-to-make-mobile-ads-work-in-your-processing-android-sketch
 ==============================================
 Bugs:
 -Button text is not centred
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
float nNumOfFacesAcross = 0;
float nFaceSize;// = displayWidth / nNumOfFacesAcross;
float nNumOfFacesDown;// = displayHeight / nFaceSize;
boolean isBlack = true;
int level = 1; //should be 1
int nScreen = 0; // 0 is start screen, 1 is menu, 2 is standard game, 3 is post standard game menu, 4 is time rush, 5 is time rush post screen, 6 is tutorial;
PFont font1;
PFont font;
int nTime;
int nTimeAtStart;
int nTimePlayed;
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
void setup() {
  font1 = createFont("Consolas", 120);
  font = createFont("Consolas", 200);
  orientation(PORTRAIT);
  ellipseMode(CORNER);
  rectMode(CENTER);
  fullScreen();
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
void draw() {
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
  } else if (nScreen == 4) {
    background(20);
    for (int j = 0; j < alFace.size(); j ++) {
      alFace.get(j).update();
    }
    if (hasTimePassed()) {
      nScreen = 5;
    }
  } else if (nScreen == 5) {
    background(20);
    fill(255);
    text(nLevelsCompleted, width / 2, height / 2);
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

void setFaces() {
  if (nNumOfFacesAcross == 20) { //should be 20
    if (level == 3) {
      if (nScreen == 2) {
        nTimePlayed = millis() -nTimeAtStart;
        nScreen = 3;
        level = 1;
        alFace.clear();
      }
    }
    nNumOfFacesAcross = 0;
    level ++;
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

void createMenu() {
  classic = new button(width / 2, height / 3, 500, 500, "PLAY", color(255, 0, 0));
  //credits = new button (width / 2, height / 3 * 2, 500, 500, "CREDITS");
}

void mousePressed() {
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
      nTimeAtStart = millis();
    }
    if (timed.isHitButton(timed.nW, timed.nH, timed.nX, timed.nY)) {
      nScreen = 4;
      nTimeAtStart = millis();
      nNumOfFacesAcross = 10;
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
      if (alFace.get(v).clickityClack(alFace.get(v).nX, alFace.get(v).nY, nFaceSize) && alFace.get(v).isNotFace == true) {
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