#pragma once
#include <iostream>
#include <string>
#include <fstream>
#include <list>
#include "Claim.h"

using namespace std;

class Reader
{
public:
    Reader(string fileName);
    ~Reader();

    int SquaresContested() { return _squaresContested; };
    int UncontestedId() { return _uncontestedClaimId; };


protected:
    void ScanFile();
    void CountContested();
    void FindUncontested();
    bool isClaimInCoordinate(Claim claim, int x, int y);
    int readNumber(string &text, int startingIndex = 0);

    int kGridWidth = 1000; 
    int kGridHeight = 1000;

    ifstream reader;
    list<Claim> *data;
    int _squaresContested = 0;
    int _uncontestedClaimId = 0;
};

