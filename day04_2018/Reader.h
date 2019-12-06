#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include "Guard.h"
#include "Event.h"

class Reader
{
public:
    Reader(string fileName);
    ~Reader();

protected:
    void ReadFile();
    void ParseFile();
    void SortFile(list<Event> events);
    void AssignGuardIdToEvent(list<Event> events);

    list<int> ReadNumbers(string &text);
    int ReadStatus(string &text); // -1 asleep, 0 awake, 1+ id

    ifstream reader;

    list<string> *fileLines;
    map<int, Guard> *guards;
};

