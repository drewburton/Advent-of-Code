#include "Program.h"

Program::Program(string filename)
{
    reader.open(filename);
    if (!reader.is_open())
    {
        exit(EXIT_FAILURE);
    }

    ReadFile();
}

void Program::ReadFile()
{
    string number1;
    reader >> number1;

    string number2;
    reader >> number2;
    
    lowRange = stoi(number1);
    highRange = stoi(number2);
    
    FindPasswords();
}

void Program::FindPasswords()
{
    for (currentPassword = lowRange; currentPassword <= highRange; currentPassword++)
    {
        if (!(currentPassword % 1000))
            cout << currentPassword << endl;

        if (IsPassword(currentPassword))
        {
            passwords++;
        }
    }
    cout << passwords << endl;
}

bool Program::IsPassword(int password)
{
    string number = to_string(password);

    // adjacent are same
    // never decrease
    int adjacentSame = 0;
    bool decreases = false;
    for (int i = 0; i < number.size() - 1; i++)
    {
        //adj
        if (number[i] == number[i + 1])
        {
            adjacentSame++;

            if (number[i + 2] == number[i])
            {
                adjacentSame--;
                continue;
            }
            if (i != 0)
            {
                if (number[i - 1] == number[i])
                    adjacentSame--;
            }
        }

        //decreases
        if (number[i + 1] < number[i])
            decreases = true;
    }

    if (adjacentSame > 0 && !decreases)
    {
        return true;
    }
    return false;
}