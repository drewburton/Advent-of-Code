// AdventOfCode.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <fstream>
#include <stdlib.h>
#include <string>
#include "Reader.h"

int main()
{
    using namespace std;
    bool isPart1 = false;
    string fileName = "Data.txt";
    Reader reader(fileName, isPart1);
    if (isPart1)
        cout << reader.Frequency() << endl;
    else
        cout << reader.Duplicate() << endl;
    return 0;
}

