//SS1 e1a //<>//
ArrayList <face> alFace = new ArrayList<face>();
int nNotaFace;
float nNumOfFacesAcross = 0;
float nFaceSize;// = displayWidth / nNumOfFacesAcross;
float nNumOfFacesDown;// = displayHeight / nFaceSize;
boolean isBlack = true;
int level = 1; //should be 1
int nScreen = 0; // 0 is start screen, 1 is menu, 2 is standard game, 3 is post standard game menu, 4 is time rush;
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
String sHour; 
String sMin; 
String sSec;
String sMillisec;
String sTime;
int nMillisec;
int nSec; 
int nMin; 
int nHour;
void setup() {
  font1 = createFont("Consolas", 120);
  font = createFont("Consolas", 200);
  orientation(PORTRAIT);
  ellipseMode(CORNER);
  rectMode(CENTER);
  fullScreen();
  frameRate(9001);
  setFaces();
  //textFont(font1);
  textAlign(CENTER);
  face = new face(width / 2, height / 2, width / 3 * 2);
  classic = new button(width / 2, height / 4, 500, 300, "CLASSIC", color(255, 0, 0)); // to be replaced with createMenu()
  timed = new button(width / 2, height / 4 * 2, 500, 300, "TIME RUSH", color(255, 0, 0)); // to be replaced with createMenu()
  menu = new button(width / 2, height / 4 * 3, 500, 300, "MENU", color(255, 0, 0));
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
  }
}

void setFaces() {
  if (nScreen == 2) {
    if (nNumOfFacesAcross == 20) { //should be 20
      if (level == 3) {
        nTimePlayed = millis() -nTimeAtStart;
        nScreen = 3;
        level = 1;
        alFace.clear();
      }
      nNumOfFacesAcross = 0;
      level ++;
    }
    nNumOfFacesAcross+=5;
  }
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
  } else if (nScreen == 3) {
    if (menu.isHitButton(menu.nW, menu.nH, menu.nX, menu.nY)) {
      nScreen = 1;
    }
  }
}