// day01_2019.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "Calculator.h"

int main()
{
    Calculator *calculator = new Calculator("Data.txt");
    cout << calculator->GetTotalFuel() << endl;
}
