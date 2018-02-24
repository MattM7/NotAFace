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
  face(int nTempX, int nTempY, int nTempFaceSize) {
    nY = nTempY;
    nX = nTempX;
    nFaceSize = nTempFaceSize;
    n10thFaceSize = round(nFaceSize / 10);
    n5thFaceSize = round(nFaceSize / 5);
    n5thFaceSizeX2 = n5thFaceSize * 2;
  }
  void update() {
    display();
    colorChange();
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
    if (level > 1) {
      c = color(random(255), random(255), random(255));
      c1 = color(random(255), random(255), random(255));
      c2 = color(random(255), random(255), random(255));
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