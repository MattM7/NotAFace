//SS1 e1a
ArrayList <face> alFace = new ArrayList<face>();
int nNotaFace;
float nNumOfFacesAcross = 0;
float nFaceSize;// = displayWidth / nNumOfFacesAcross;
float nNumOfFacesDown;// = displayHeight / nFaceSize;
boolean isBlack = true;
int level = 1;
void setup() {
  ellipseMode(CORNER);
  createCanvas(500,500);
  frameRate(9001);
  setFaces();
}
void draw() {
  background(0);
  if (level > 2) {
    if (isBlack == true) {
      background(0);
      isBlack = false;
    } else {
      background(255);
      isBlack = true;
    }
  }
  for (int j = 0; j < alFace.size(); j ++) {
    alFace.get(j).update();
  }
}

void setFaces() {
  if (nNumOfFacesAcross == 40) {
    nNumOfFacesAcross = 0;
    level ++;
  }
  alFace.clear();
  nNumOfFacesAcross+=10;
  nFaceSize = displayWidth / nNumOfFacesAcross;
  nNumOfFacesDown = displayHeight / nFaceSize;
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

void mousePressed() {
  for (int v = 0; v < alFace.size(); v++) {
    if (alFace.get(v).clickityClack(alFace.get(v).nX, alFace.get(v).nY, nFaceSize)) {
      println("hit");
      setFaces();
      break;
    }
  }
}