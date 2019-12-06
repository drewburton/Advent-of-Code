#pragma once
class Claim
{
public:
    Claim(int id, int distFromLeft, int distFromTop, int inchesWide, int inchesTall);
    ~Claim();

    int xRange[2];
    int yRange[2];
    int _id = 0;

protected:
    void findXRange();
    void findYRange();

    int _distFromLeft = 0;
    int _distFromTop = 0;
    int _inchesWide = 0;
    int _inchesTall = 0;

};

