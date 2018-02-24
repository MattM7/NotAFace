class button {
  int nX;
  int nY;
  int nW;
  int nH;
  String sText;
  String nTempText;
  color c;
  button(int nTempX, int nTempY, int nTempW, int nTempH, String sTempText, color tempC) {
    nX = nTempX;
    nY = nTempY;
    nW = nTempW;
    nH = nTempH;
    sText = sTempText;
    c = tempC;
  }
  
  void display() {
    fill(c);
    rect(nX, nY, nW, nH);
    fill(255);
    text(sText, nX, nY + nY / 14);
  }
  
  boolean isHitButton(int nW, int nH, int nX, int nY) {
  if (mouseX >= nX - (nW / 2) && mouseX <= nX+(nW /2)
    && 
    mouseY >= nY - (nH / 2) && mouseY <= nY + (nH / 2)) {
    return true;
  } else {
    return false;
  }
}
}