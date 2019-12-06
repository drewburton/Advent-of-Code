#pragma once
#include <list>
#include <map>

using namespace std;

class Guard
{
public:
    Guard();
    ~Guard();

    int TimeAsleep();
    int MinuteAsleepMost();
    void InputStatus(int minuteAsleep, int minuteAwake) { statuses->push_back(Status{ minuteAsleep, minuteAwake }); }
    Guard operator=(const Guard &guard);
protected:    
    struct Status
    {
        int minuteAsleep;
        int minuteAwake;
    };

    list<Status> *statuses;
};

