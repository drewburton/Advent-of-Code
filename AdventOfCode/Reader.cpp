#include "stdafx.h"
#include "Reader.h"

Reader::Reader(string fileName, bool isPart1)
{
    data = new list<string>;
    sortedData = new list<int>;
    frequencies = new list<int>;
    _fileName = fileName;
    _isPart1 = isPart1;

    reader.open(fileName);
    if (!reader.is_open())
    {
        cout << "Could not open the file " << fileName << endl;
        cout << "Program terminating.\n";
        exit(EXIT_FAILURE);
    }

    ReadFile();
}

Reader::~Reader()
{
    delete data;
    delete sortedData;
    delete frequencies;
}

void Reader::ReadFile()
{
    string number;
    while (reader >> number)
    {
        data->push_back(number);
    }
    SortFile();
}

void Reader::SortFile()
{
    list<string>::iterator dataIterator = data->begin();
    int number;
    string unprocessed;

    while (dataIterator != data->end())
    {
        if ((*dataIterator)[0] == '+')
        {
            (*dataIterator)[0] = '0';
        }

        number = stoi(*dataIterator);
        sortedData->push_back(number);
        advance(dataIterator, 1);
    }
    if (_isPart1)
        AddData();
    else
        CheckDuplicates();
}

void Reader::AddData(bool shouldRunPart2)
{
    list<int>::iterator sortedIterator = sortedData->begin();
    while (sortedIterator != sortedData->end())
    {
        _frequency += *sortedIterator;
        if (!shouldRunPart2)
        {
            frequencies->push_back(_frequency);
        }
        else
        {
            list<int>::iterator frequencyIterator = frequencies->begin();
            while (frequencyIterator != frequencies->end())
            {
                if ((_frequency == *frequencyIterator) && !_isDuplicate)
                {
                    _isDuplicate = true;
                    _duplicate = _frequency;
                }
                advance(frequencyIterator, 1);
            }
        }
        advance(sortedIterator, 1);
    }
}

void Reader::CheckDuplicates()
{
    AddData();
    while (!_isDuplicate)
    {
        AddData(true);
    }
}