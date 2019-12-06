#include "pch.h"
#include "Guard.h"

Guard::Guard()
{
    statuses = new list<Status>;
}

Guard::~Guard()
{
    delete statuses;
}

int Guard::TimeAsleep()
{
    int timeAsleep = 0;
    list<Status>::iterator statusIterator = statuses->begin();
    while (statusIterator != statuses->end())
    {
        timeAsleep += statusIterator->minuteAwake - statusIterator->minuteAsleep;
        statusIterator++;
    }
    return timeAsleep;
}

int Guard::MinuteAsleepMost()
{
    list<int> *everyMinuteAsleep = new list<int>;
    list<Status>::iterator statusIterator = statuses->begin();
    while (statusIterator != statuses->end())
    {
        for (int index = statusIterator->minuteAsleep; index < statusIterator->minuteAwake; index++)
            everyMinuteAsleep->push_back(index);
        statusIterator++;
    }

    map<int, int> timesAsleepAtMinute;
    list<int>::iterator minutesIterator = everyMinuteAsleep->begin();
    while (minutesIterator != everyMinuteAsleep->end())
    {
        timesAsleepAtMinute[*minutesIterator] += 1;
        minutesIterator++;
    }

    int minuteAsleepMost = 0;
    int mostTimesSleptAtMinute = 0;
    map<int, int>::iterator mapIterator = timesAsleepAtMinute.begin();
    while (mapIterator != timesAsleepAtMinute.end())
    {
        if (mapIterator->second > mostTimesSleptAtMinute)
            minuteAsleepMost = mapIterator->first;
        mapIterator++;
    }
    delete everyMinuteAsleep;
    return minuteAsleepMost;
}

Guard Guard::operator=(const Guard &guard)
{
    Guard newGuard;

    list<Status>::iterator statusIterator = statuses->begin();
    while (statusIterator != statuses->end())
    {
        newGuard.statuses->push_back(*statusIterator);
        statusIterator++;
    }

    list<Status>::iterator iterator = guard.statuses->begin();
    while (iterator != guard.statuses->end())
    {
        newGuard.statuses->push_back(*iterator);
        iterator++;
    }

    return newGuard;
}