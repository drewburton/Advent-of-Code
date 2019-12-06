#include "Intcode.h"

Intcode::Intcode(string filename)
{
    reader.open(filename);
    if (!reader.is_open())
    {
        exit(EXIT_FAILURE);
    }

    program = new list<int>;
    original = new list<int>;

    ReadFile();
}

Intcode::~Intcode()
{
    delete original;
    delete program;
}

void Intcode::ReadFile()
{
    string input;
    reader >> input;
    string number = "";
    for (int i = 0; i < input.size(); i++)
    {
        if (input[i] != ',')
        {
            number += input[i];
        }
        else
        {
            program->push_back(stoi(number));
            original->push_back(stoi(number));
            number = "";
        }
    }
    program->push_back(stoi(number));
    original->push_back(stoi(number));

    //Replace(1, 12);
    //Replace(2, 2);
    //ProcessProgram();
    ProcessProgram2();
}

void Intcode::Replace(int position, int value)
{
    list<int>::iterator it = program->begin();
    advance(it, position);
    program->emplace(it, value);
    program->erase(it);
}

void Intcode::ProcessProgram()
{
    list<int>::iterator programIterator = program->begin();
    for (int i = 0; i < program->size(); i++)
    {
        switch (*programIterator)
        {
        case add: Operation(programIterator, add);
            break;
        case multiply: Operation(programIterator, multiply);
            break;
        case done: //Position0();
            return;
        }
    }
}

void Intcode::ProcessProgram2()
{
    for (int noun = 0; noun <= 99; noun++)
    {
        for (int verb = 0; verb <= 99; verb++)
        {
            Reset();
            Replace(1, noun);
            Replace(2, verb);
            ProcessProgram();
            if (*program->begin() == 19690720)
            {
                cout << "answer: " << 100 * noun + verb << endl;
                return;
            }
        }
    }
}

void Intcode::Reset()
{
    delete program;
    program = new list<int>;
    list<int>::iterator it = original->begin();
    for (int i = 0; i < original->size(); i++)
    {
        program->push_back(*it);
        advance(it, 1);
    }
}

void Intcode::Operation(list<int>::iterator& position, opcode operation)
{
    int positions[3];
    list<int>::iterator iterators[3];

    for (int i = 0; i < 3; i++)
    {
        advance(position, 1);
        positions[i] = *position;

        iterators[i] = program->begin();
        advance(iterators[i], positions[i]);
    }
    int replaceVal = 0;
    switch (operation)
    {
    case add: replaceVal = *iterators[0] + *iterators[1];
        break;
    case multiply: replaceVal = *iterators[0] * *iterators[1];
        break;
    case done: cout << "ALERT SOMETHING BROKE HERE!!!!!!!!" << endl;
    }
    advance(position, 1);
    Replace(positions[2], replaceVal);
}

void Intcode::printArr()
{
    cout << endl;
    list<int>::iterator it = program->begin();
    for (int i = 0; i < program->size(); i++)
    {
        cout << *it << " ";
        advance(it, 1);
    }
    cout << endl;
}