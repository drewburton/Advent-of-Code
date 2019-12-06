#pragma once
#ifndef READER_H_
#define READER_H_

#include <string>
#include <fstream>

class Reader
{
private:
    std::string _filename;
    std::ifstream* reader;

public:
    Reader(std::string filename = "none");
    Reader(const Reader& r);
    ~Reader();

    void operator>>(int &number);
    void operator>>(std::string &line);
};

#endif