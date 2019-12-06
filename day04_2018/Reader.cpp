#include "pch.h"
#include "Reader.h"

Reader::Reader(string fileName)
{
    fileLines = new list<string>;
    guards = new map<int, Guard>;

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
    delete fileLines;
    delete guards;
}

void Reader::ReadFile()
{
    string line;
    while (getline(reader, line))
    {
        fileLines->push_back(line);
    }
    ParseFile();
}

void Reader::ParseFile()
{
    list<Event> events;
    list<string>::iterator linesIterator = fileLines->begin();
    while (linesIterator != fileLines->end())
    {
        list<int> numbers = ReadNumbers(*linesIterator);
        // -1 is asleep, 0 is awake, int 1+ is id
        int status = ReadStatus(*linesIterator);

        list<int>::iterator numbersIterator = numbers.begin();
        Event event;
        numbersIterator++;
        event.month = *(numbersIterator++);
        event.day = *(numbersIterator++);
        event.hour = *(numbersIterator++);
        event.minute = *(numbersIterator);
        if (status == -1)
            event.isAwake = false;
        else if (status == 0)
            event.isAwake = true;
        else
            event.id = status;
        event.SetDate();
        events.push_back(event);
        linesIterator++;
    }
    SortFile(events);
}

void Reader::SortFile(list<Event> events)
{
    list<Event>::iterator eventsIterator = events.begin();
    list<Event> sortedEvents;
    sortedEvents.push_back(*eventsIterator);
    eventsIterator++;

    while (eventsIterator != events.end())
    {
        list<Event>::iterator sortedIterator = sortedEvents.begin();

        while (sortedIterator != sortedEvents.end())
        {
            if (eventsIterator->date < sortedIterator->date)
            {
                sortedEvents.insert(sortedIterator, *eventsIterator);
                break;
            }
            else
            {
                sortedIterator++;
                if (sortedIterator == sortedEvents.end())
                    sortedEvents.insert(sortedIterator, *eventsIterator);
            }
        }
        eventsIterator++;
    }
    AssignGuardIdToEvent(sortedEvents);
}

void Reader::AssignGuardIdToEvent(list<Event> events)
{
    int currentGuard = 0;
    bool isAsleep = false;
    int minuteAsleep = 0;
    Guard *guard = new Guard();
    list<Event>::iterator eventIterator = events.begin();
    while (eventIterator != events.end())
    {
        if (eventIterator->id > 0)
        {
            currentGuard = eventIterator->id;
            delete guard;
            guard = new Guard();
        }
        if (!eventIterator->isAwake)
        {
            minuteAsleep = eventIterator->minute;
            isAsleep = true;
        }

        eventIterator++;

        if (isAsleep)
        {
            guard->InputStatus(minuteAsleep, eventIterator->minute);
            (*guards)[currentGuard] = *guard;
            isAsleep = false;
        }
    }
}

list<int> Reader::ReadNumbers(string &text)
{
    list<int> numbers;
    string numberString = "";
    int stringLength = text.size();
    for (int index = 0; index < stringLength; index++)
    {
        if (isdigit(text[index]))
            numberString += text[index];
        else
        {
            if (numberString != "")
                numbers.push_back(stoi(numberString));
            numberString = "";
        }
        if (text[index] == ']')
        {
            text[index] = '*';
            break;
        }
        text[index] = '*';
    }
    return numbers;
}

int Reader::ReadStatus(string &text) // -1 asleep, 0 awake, 1+ id
{
    list<int> id;
    string statusString = "";
    int stringLength = text.size();
    for (int index = 0; index < stringLength; index++)
    {
        if (text[index] == 'G')
        {
            id = ReadNumbers(text);
            return *id.begin();
        }
        else if (text[index] == 'f')
            return -1;
        else if (text[index] == 'w')
            return 0;
        text[index] = '*';
    }
    return 0;
}