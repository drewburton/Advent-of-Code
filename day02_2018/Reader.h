#ifndef READER_H
#define READER_H
#include <string>
#include <iostream>
#include <fstream>
#include <list>
#include <map>

using namespace std;
class Reader
{
public:
    Reader(string fileName);
    ~Reader();

    int Checksum() { return _twoSame * _threeSame; };
    string SimilarCharacters() { return _similarCharacters; };

protected:
    void ReadFile();
    void CountSameLetters();
    void FindSimilarID();

    string _fileName;
    ifstream reader;

    list<string> *data;
    int _twoSame = 0;
    int _threeSame = 0;
    string _similarCharacters;
    enum parts { part1, part2 };
};

#endif