#include "Calculator.h"

Calculator::Calculator(string filename)
{
    _filename = filename;
    masses = new list<int>;
    fuels = new list<int>;
    totalFuel = 0;

    reader.open(_filename);
    if (!reader.is_open())
    {
        exit(EXIT_FAILURE);
    }

    GetMasses();
}

Calculator::~Calculator()
{
    delete masses;
    delete fuels;
}

void Calculator::GetMasses()
{
    string number;
    while (reader >> number)
    {
        masses->push_back(stoi(number));
    }
    CalculateFuel();
}

void Calculator::CalculateFuel()
{
    // to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.
    list<int>::iterator massesIterator = masses->begin();
    for (int i = 0; i < masses->size(); i++)
    {
        int fuel = (int)((*massesIterator) / 3) - 2;
        totalFuel += fuel;
        fuels->push_back(fuel);
        advance(massesIterator, 1);
    }
    CalculateFuelPt2();
}

void Calculator::CalculateFuelPt2()
{
    list<int>::iterator fuelsIterator = fuels->begin();
    for (int i = 0; i < fuels->size(); i++)
    {
        int fuel = *fuelsIterator;
        int lastFuel = fuel;
        while (true)
        {
            fuel = (int)(lastFuel / 3) - 2;
            if (fuel < 0)
                break;
            totalFuel += fuel;
            lastFuel = fuel;
        }
       
        advance(fuelsIterator, 1);
    }
}