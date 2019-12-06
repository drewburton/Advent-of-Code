#include "Reader.h"

Reader::Reader(std::string filename)
{  
    _filename = filename;
    reader = new std::ifstream();
    reader->open(_filename);
    if (!reader->is_open())
    {
        exit(EXIT_FAILURE);
    }
}

Reader::Reader(const Reader& r)
{
    _filename = r._filename;
    reader = new std::ifstream();
    reader->open(_filename);
    if (!reader->is_open())
    {
        exit(EXIT_FAILURE);
    }
}

Reader::~Reader()
{
    delete reader;
}

void Reader::operator>>(int &number)
{
    (*reader) >> number;
}

void Reader::operator>>(std::string& line)
{
    (*reader) >> line;
}