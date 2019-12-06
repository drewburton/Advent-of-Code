#pragma once
#ifndef PROGRAM_H_
#define PROGRAM_H_

#include <string>
#include <fstream>
#include <vector>
#include <iostream>

using namespace std;

class Program
{
private:
    int passwords = 0;

    int lowRange = 0;
    int highRange = 0;
    int currentPassword = 0;

    ifstream reader;

    void ReadFile();
    void FindPasswords();
    bool IsPassword(int password);

public:
    Program(string filename);
    ~Program() {};
};

#endif