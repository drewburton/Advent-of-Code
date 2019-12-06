#include "stdafx.h"
#include "Reader.h"

Reader::Reader(string fileName)
{
    data = new list<string>;

    _fileName = fileName;
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
}


void Reader::ReadFile()
{
    string id;
    while (reader >> id)
    {
        data->push_back(id);
    }

    CountSameLetters();
    FindSimilarID();
}

void Reader::CountSameLetters()
{
    list<string>::iterator dataIterator = data->begin();

    while (dataIterator != data->end())
    {
        map<char, int> letters;
        int stringLength = size(*dataIterator);

        // store characters in map and count how many of each there are
        for (int lettersIndex = 0; lettersIndex < stringLength; lettersIndex++)
        {
            letters[(*dataIterator)[lettersIndex]] += 1;
        }

        bool isTwoSame = false;
        bool isThreeSame = false;
        map<char, int>::iterator lettersIterator = letters.begin();
        while (lettersIterator != letters.end())
        {
            if (lettersIterator->second == 2)
                isTwoSame = true;
            if (lettersIterator->second == 3)
                isThreeSame = true;
            advance(lettersIterator, 1);
        }

        if (isTwoSame)
            _twoSame += 1;
        if (isThreeSame)
            _threeSame += 1;
        advance(dataIterator, 1);
    }
}

void Reader::FindSimilarID()
{
    list<string>::iterator dataIterator = data->begin();
    string closestString1;
    string closestString2;
    int leastDifferentCharacters = 0;

    //find closest strings
    while (dataIterator != data->end())
    {
        list<string>::iterator secondIterator = dataIterator;
        advance(secondIterator, 1);

        while (secondIterator != data->end())
        {
            int differentCharacters = 0;
            int stringLength = size(*dataIterator);

            for (int stringIndex = 0; stringIndex < stringLength; stringIndex++)
            {
                if ((*dataIterator)[stringIndex] != (*secondIterator)[stringIndex])
                    differentCharacters += 1;
            }
            if ((differentCharacters < leastDifferentCharacters) || (dataIterator == data->begin()))
            {
                leastDifferentCharacters = differentCharacters;
                closestString1 = *dataIterator;
                closestString2 = *secondIterator;
            }
            advance(secondIterator, 1);
        }
        advance(dataIterator, 1);
    }

    //take out different characters
    for (int stringIndex = 0; stringIndex < int(closestString1.size()); stringIndex++)
    {
        if (closestString1[stringIndex] == closestString2[stringIndex])
            _similarCharacters += closestString1[stringIndex];
    }
}