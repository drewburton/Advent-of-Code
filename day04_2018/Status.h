#pragma once
class Status
{
public:
    Status(int month, int day, int hour, int minute, bool isAwake);
    ~Status();

protected:
    int _month = 0;
    int _day = 0;
    int _hour = 0;
    int _minute = 0;
    
    enum status { asleep, awake };
};

