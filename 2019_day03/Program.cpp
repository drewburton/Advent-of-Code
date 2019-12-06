#include "Program.h"

Program::Program(string filename)
{
    wire1 = new list<string>;
    wire2 = new list<string>;

    wire1coor = new list<Point>;
    wire2coor = new list<Point>;

    intersections = new list<Point>;

    reader.open(filename);
    if (!reader.is_open())
    {
        exit(EXIT_FAILURE);
    }

    ReadFile();
}

Program::~Program()
{
    delete wire1;
    delete wire2;
    delete wire1coor;
    delete wire2coor;
    delete intersections;
}

void Program::ReadFile()
{
    ReadWire(wire1);
    ReadWire(wire2);

    CreateLine1();
}

void Program::ReadWire(list<string>*& list)
{
    string line;
    string individualPath;

    reader >> line;
    for (int lineI = 0; lineI < line.size(); lineI++)
    {
        if (line[lineI] != ',')
        {
            individualPath += line[lineI];
        }
        else
        {
            list->push_back(individualPath);
            individualPath = "";
        }
    }
    list->push_back(individualPath);
}

void Program::CreateLine1()
{
    cout << "Creating line 1\n";

    wire1coor->push_back(Point(0, 0));
    list<string>::iterator wireIt = wire1->begin();
    for (int i = 0; i < wire1->size(); i++)
    {
        string direction = *wireIt;

        string numberString;
        for (int directionI = 1; directionI < direction.size(); directionI++)
        {
            numberString += direction[directionI];
        }
        int number = stoi(numberString);

        int startX = 0;
        int startY = 0;
        GetLastCoor(*wire1coor, startX, startY);
        for (int distance = 1; distance <= number; distance++)
        {
            int x = startX;
            int y = startY;
            if (direction[0] == 'R')
            {
                x += distance;
            }
            else if (direction[0] == 'L')
            {
                x -= distance;
            }
            else if (direction[0] == 'U')
            {
                y += distance;
            }
            else
            {
                y -= distance;
            }

            wire1coor->push_back(Point(x, y));
        }
        advance(wireIt, 1);
    }
    CreateLine2();
}

void Program::CreateLine2()
{
    cout << "Creating line 2\n";

    wire2coor->push_back(Point(0, 0));
    list<string>::iterator wireIt = wire2->begin();
    for (int i = 0; i < wire2->size(); i++)
    {
        string direction = *wireIt;

        string numberString;
        for (int directionI = 1; directionI < direction.size(); directionI++)
        {
            numberString += direction[directionI];
        }
        int number = stoi(numberString);

        int startX = 0;
        int startY = 0;
        GetLastCoor(*wire2coor, startX, startY);
        for (int distance = 1; distance <= number; distance++)
        {
            int x = startX;
            int y = startY;
            if (direction[0] == 'R')
            {
                x += distance;
            }
            else if (direction[0] == 'L')
            {
                x -= distance;
            }
            else if (direction[0] == 'U')
            {
                y += distance;
            }
            else
            {
                y -= distance;
            }

            if (!(wire2coor->size() % 10))
                cout << wire2coor->size() << endl;

            if (ContainsPoint(*wire1coor, x, y))
            {
                intersections->push_back(Point(x, y));
            }
            
            wire2coor->push_back(Point(x, y));
        }
        advance(wireIt, 1);
    }
    ClosestIntersection();
}

void Program::ClosestIntersection()
{
    cout << "Finding closeset intersection\n";

    int distance = INT_MAX;
    list<Point>::iterator it = intersections->begin();
    for (int i = 0; i < intersections->size(); i++)
    {
        Point numbers = *it;
       
        if (abs(numbers.x) + abs(numbers.y) < distance)
            distance = abs(numbers.x) + abs(numbers.y);

        advance(it, 1);
    }
    cout << distance << endl;
}








void Program::GetLastCoor(list<Point>& coordinates, int& x, int& y)
{
    list<Point>::iterator it = coordinates.end();
    advance(it, -1);

    Point numbers = *it;

    x = numbers.x;
    y = numbers.y;
}

bool Program::ContainsPoint(list<Point> points, int& x, int& y)
{
    list<Point>::iterator it = points.begin();
    for (int i = 0; i < points.size(); i++)
    {
        Point numbers = *it;
        int numbersX = numbers.x;
        int numbersY = numbers.y;

        if (numbersX == x && numbersY == y)
            return true;

        advance(it, 1);
    }
    return false;
}