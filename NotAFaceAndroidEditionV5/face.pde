class face {
  boolean isNotFace = false;
  int nX;
  int nY;
  color c = color(random(255), random(255), random(255));
  color c1 = color(random(255), random(255), random(255));
  color c2 = color(random(255), random(255), random(255));
  int r = round(random(-200, 200));
  int speed = round(random(-5, 5));
  int nFaceSize;
  int n10thFaceSize;
  int n5thFaceSize;
  int n5thFaceSizeX2;
  int nTimeSinceLastJitter = 0;
  int nPos = 0; //0 is middle, 1 is up, 2 is down
  boolean bUp = true;
  boolean bRight;
  face(int nTempX, int nTempY, int nTempFaceSize, boolean bTempRight) {
    nY = nTempY;
    nX = nTempX;
    nFaceSize = nTempFaceSize;
    n10thFaceSize = round(nFaceSize / 10);
    n5thFaceSize = round(nFaceSize / 5);
    n5thFaceSizeX2 = n5thFaceSize * 2;
    bRight = bTempRight;
    if (random(4) > 2) {
      bUp = true;
    } else {
      bUp = false;
    }
  }
  void update() {
    display();
    colorChange();
    scroll();
    jitter();
  }
  void display() {
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
  void colorChange() {
    if (level > 1 || nScreen == 7) {
      c = color(random(255), random(255), random(255));
      c1 = color(random(255), random(255), random(255));
      c2 = color(random(255), random(255), random(255));
    }
  }

  void scroll() {
    if (nScreen == 7 && level >=2) {
      if (bRight == true) {
        nX += 3;
        if (nX > width + nFaceSize /2) {
          nX = -(nFaceSize / 2);
        }
      } else {
        nX -= 3;
        if (nX < -(nFaceSize /2)) {
          nX = width + (nFaceSize / 2);
        }
      }
    }
  }
  void jitter() {
    if (nScreen == 7) {
      if (millis() - nTimeSinceLastJitter > 100) {
        if (nPos == 0) {
          if (bUp == true) {
            nY += 10;
            nTimeSinceLastJitter = millis();
            bUp = false;
            nPos = 1;
          } else {
            nY -= 10;
            bUp = true;
            nTimeSinceLastJitter = millis();
            nPos = -1;
          }
        } else if (nPos == 1) {
          nY -= 10;
          nTimeSinceLastJitter = millis();
          nPos = 0;
        } else if (nPos == -1) {
          nY += 10;
          nTimeSinceLastJitter = millis();
          nPos = 0;
        }
      }
    }
  }

  boolean clickityClack(int nX, int nY, int nFaceSize) {
    if ((mouseX >= nX -(nFaceSize/2) && mouseX <= nX+(nFaceSize/2) && 
      mouseY >= nY-(nFaceSize/2) && mouseY <= nY + (nFaceSize/2))) {
      return true;
    }
    return false;
  }
}