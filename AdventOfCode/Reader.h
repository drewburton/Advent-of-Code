#ifndef READER_H
#define READER_H

#include <iostream>
#include <fstream>
#include <cstdlib>
#include <string>
#include <list>
#include <sstream>

using namespace std;

class Reader
{
public:
    Reader(string fileName, bool isPart1 = true);
    ~Reader();

    int Frequency() { return _frequency; };
    int Duplicate() { return _duplicate; };

protected:
    void ReadFile();
    void SortFile();
    void AddData(bool shouldRunPart2 = false);
    void CheckDuplicates();

    // run info
    string _fileName;
    bool _isPart1;

    // part 1
    ifstream reader;
    list<string> *data;
    list<int> *sortedData;
    int _frequency = 0;
    
    // part 2
    list<int> *frequencies;
    bool _isDuplicate = false;
    int _duplicate = 0;
};

#endif