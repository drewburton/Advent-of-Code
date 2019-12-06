#pragma once
#ifndef PROGRAM_H_
#define PROGRAM_H_

#include <fstream>
#include <string>
#include <list>
#include <iostream>
#include <map>
#include <cmath>
#include "Point.h"

using namespace std;

class Program
{
private:
    ifstream reader;

    list<string>* wire1;
    list<string>* wire2;

    list<Point>* wire1coor;
    list<Point>* wire2coor;

    list<Point>* intersections;
    void GetLastCoor(list<Point>& coordinates, int& x, int& y);

    void ReadFile();
    void ReadWire(list<string>*& list);
    void CreateLine1();
    void CreateLine2();
    void ClosestIntersection();

    bool ContainsPoint(list<Point> points, int& x, int& y);

public:
    Program(string filename);
    ~Program();
};

#endif