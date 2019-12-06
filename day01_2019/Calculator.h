#pragma once
#ifndef CALCULATOR_H_
#define CALCULATOR_H_

#include <string>
#include <list>
#include <fstream>

using namespace std;

class Calculator
{
private:
    ifstream reader;
    string _filename;

    list<int> *masses;
    list<int> *fuels;
    int totalFuel;

    void GetMasses();
    void CalculateFuel();
    void CalculateFuelPt2();

public:
    Calculator(string filename);
    ~Calculator();

    int GetTotalFuel() { return totalFuel; };
};

#endif