#pragma once
#ifndef INTCODE_H_
#define INTCODE_H_

#include <list>
#include <string>
#include <fstream>
#include <iostream>

using namespace std;

class Intcode
{
private:
    ifstream reader;

    enum opcode { add = 1, multiply = 2, done = 99 };
    opcode currentOpcode;

    list<int>* original;
    list<int> *program;

    void ReadFile();
    void Replace(int position, int value);
    void ProcessProgram();
    void ProcessProgram2();
    void Reset();
    void Position0() { cout << *program->begin() << endl; };

    void Operation(list<int>::iterator& position, opcode operation);
    void printArr();
public:
    Intcode(string filename);
    ~Intcode();
};

#endif