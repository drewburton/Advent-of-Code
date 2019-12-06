#include "pch.h"
#include "Reader.h"

Reader::Reader(string fileName)
{
    data = new list<Claim>;

    reader.open(fileName);
    if (!reader.is_open())
    {
        cout << "Could not open the file " << fileName << endl;
        cout << "Program terminating.\n";
        exit(EXIT_FAILURE);
    }

    ScanFile();
}

Reader::~Reader()
{
    delete data;
}

void Reader::ScanFile()
{
    string information = string();
    while (reader >> information) // gets #number
    {
        if (information[0] == '#')
        {            
            int id = readNumber(information, 1);

            reader >> information; // gets @

            reader >> information; // gets #,#:
            int distFromLeft = readNumber(information, 0);
            int distFromTop = readNumber(information, 0);

            reader >> information; // gets #x#
            int inchesWide = readNumber(information, 0);
            int inchesTall = readNumber(information, 0);
            data->push_back(Claim(id, distFromLeft, distFromTop, inchesWide, inchesTall));
        }
    }
    //CountContested();
    FindUncontested();
}

void Reader::CountContested()
{
    for (int yCoordinate = 1; yCoordinate <= kGridHeight; yCoordinate++)
    {
        for (int xCoordinate = 1; xCoordinate <= kGridWidth; xCoordinate++)
        {
            int claimsInCoordinate = 0;
            list<Claim>::iterator dataIterator = data->begin();
            while (dataIterator != data->end())
            {
                if (isClaimInCoordinate(*dataIterator, xCoordinate, yCoordinate))
                {
                    claimsInCoordinate += 1;
                }
                advance(dataIterator, 1);
            }
            if (claimsInCoordinate > 1)
                _squaresContested += 1;
        }
    }
}

void Reader::FindUncontested()
{
    list<Claim>::iterator dataIterator = data->begin();
    bool isContested = true;

    while (dataIterator != data->end())
    {
        // for each claim
        isContested = false;

        for (int yRange = dataIterator->yRange[0]; yRange <= dataIterator->yRange[1]; yRange++)
        {
            for (int xRange = dataIterator->xRange[0]; xRange <= dataIterator->xRange[1]; xRange++)
            {
                // for each coordinate
                list<Claim>::iterator checkingIterator = data->begin();

                while (checkingIterator != data->end())
                {
                        bool isOtherClaimInCoordinate = false;
                        if ((checkingIterator != dataIterator) && (isClaimInCoordinate(*checkingIterator, xRange, yRange)))
                            isOtherClaimInCoordinate = true;
                        if (isOtherClaimInCoordinate)
                        {
                            yRange = dataIterator->yRange[1] + 1;
                            xRange = dataIterator->xRange[1] + 1;
                            isContested = true;
                            break;
                        }
                        //check each coordinate in claim to see if it has multiple claims
                        //have a variable isContested that is set to true when there are multiple claims
                        //if the claim is not contested after checking the last coordinate
                        //then save the ID of the claim that is uncontested
                        advance(checkingIterator, 1);
                }
            }
        }
        if (!isContested)
        {
            _uncontestedClaimId = dataIterator->_id;
            break;
        }
        advance(dataIterator, 1);
    }
}

bool Reader::isClaimInCoordinate(Claim claim, int x, int y)
{
    bool betweenX = (x >= claim.xRange[0]) && (x <= claim.xRange[1]);
    bool betweenY = (y >= claim.yRange[0]) && (y <= claim.yRange[1]);
    return betweenX && betweenY;
}

int Reader::readNumber(string &text, int startingIndex)
{
    string numberString = "";
    int stringLength = text.size();
    for (int index = startingIndex; index < stringLength; index++)
    {
        if (isdigit(text[index]))
        {
            numberString += text[index];
            text[index] = '*';
        }
        else if (text[index] == '*')
            continue;
        else
        {
            text[index] = '*';
            break;
        }
    }
    return stoi(numberString);
}