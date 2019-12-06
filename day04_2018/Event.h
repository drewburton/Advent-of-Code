#pragma once
#include "Date.h"

class Event
{
public:
    Event();
    ~Event();

    int month;
    int day;
    int hour;
    int minute;
    int id;
    bool isAwake;

    Date date;
    void SetDate();
};

