class face {
  boolean isNotFace = false;
  float nX;
  float nY;
  color c = color(random(255), random(255), random(255));
  color c1 = color(random(255), random(255), random(255));
  color c2 = color(random(255), random(255), random(255));
  float r = round(random(-200, 200));
  float speed = random(-5, 5);
  float nFaceSize;
  int n10thFaceSize;
  int n5thFaceSize;
  face(float nTempX, float nTempY, float nTempFaceSize) {
    nY = nTempY;
    nX = nTempX;
    nFaceSize = nTempFaceSize;
    n10thFaceSize = round(nFaceSize / 10);
    n5thFaceSize = round(nFaceSize / 5);
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
    ellipse(0 - n5thFaceSize, 0 - n10thFaceSize, n5thFaceSize, nFaceSize / 5);
    popMatrix();
    pushMatrix();
    translate(nX, nY);
    rotate(radians(r));
    r += speed;
    ellipse(0 + n5thFaceSize, 0 - n10thFaceSize, n5thFaceSize, nFaceSize / 5);
    popMatrix();
    fill(c2);
    if (!isNotFace) {
      pushMatrix();
      translate(nX, nY);
      rotate(radians(r));
      r += speed;
            ellipse(0, 0 + n5thFaceSize, n5thFaceSize, n5thFaceSize * 2);

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

  boolean clickityClack(float fX, float fY, float nFaceSize) {
    if ((mouseX >= fX -(nFaceSize/2) && mouseX <= fX+(nFaceSize/2) && 
      mouseY >= fY-(nFaceSize/2) && mouseY <= fY + (nFaceSize/2))) {
      return true;
    }
    return false;
  }
}