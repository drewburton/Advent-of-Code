#include "pch.h"
#include "Claim.h"

Claim::Claim(int id, int distFromLeft, int distFromTop, int inchesWide, int inchesTall)
{
    _id = id;
    _distFromLeft = distFromLeft;
    _distFromTop = distFromTop;
    _inchesWide = inchesWide;
    _inchesTall = inchesTall;
    xRange[0] = 0;
    xRange[1] = 0;
    yRange[0] = 0;
    yRange[1] = 0;

    for (int rangeIndex = 0; rangeIndex < 2; rangeIndex++)
    {
        xRange[rangeIndex] = 0;
        yRange[rangeIndex] = 0;
    }

    findXRange();
    findYRange();
}

Claim::~Claim()
{

}

void Claim::findXRange()
{
    xRange[0] = _distFromLeft + 1;
    xRange[1] = _distFromLeft + _inchesWide;

}

void Claim::findYRange()
{
    yRange[0] = _distFromTop + 1;
    yRange[1] = _distFromTop + _inchesTall;
}