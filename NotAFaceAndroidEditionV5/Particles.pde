class particle {
  boolean isNotFace = false;
  int nX;
  int nY;
  int r = round(random(-200, 200));
  int speed = round(random(-5, 5));
  int nPos = 0; //0 is middle, 1 is up, 2 is down
  boolean bUp = true;
  boolean bRight;
  color c;
  int nDir;
  int nSpeed;
  particle(int nTempX, int nTempY, int nTempDir, int nTempSpeed) {
    nY = nTempY;
    nX = nTempX;
    c = color(random(255), random(255), random(255), 100);
    nDir = nTempDir;
    nSpeed = nTempSpeed + 5;
  }
  void update() {
    display();
    move();
  }
  void display() {
    ellipseMode(CENTER);
    fill(c);
    noStroke();
    ellipse(nX, nY, nFaceSize, nFaceSize); //100
    stroke(5);
  }
  void move() {
    if (nDir == 1) {
      nX += nSpeed;
    } else if (nDir == 2) {
      nY += nSpeed;
    } else if (nDir == 3) {
      nX -= nSpeed;
    } else if (nDir == 4) {
      nY -= nSpeed;
    }
  }

}