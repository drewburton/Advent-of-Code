#include "pch.h"
#include "Event.h"

Event::Event()
{
    month = 0;
    day = 0;
    hour = 0;
    minute = 0;
    id = 0;
    isAwake = true;
}

Event::~Event()
{

}

void Event::SetDate()
{
    if (hour > 0)
    {
        hour = 0;
        minute = 0;
        day++;
    }
    date.setTime(month, day, hour, minute);
}
