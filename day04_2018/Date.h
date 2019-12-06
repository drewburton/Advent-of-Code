#pragma once

class Date
{
public:
    Date();
    ~Date();

    void setTime(int month, int day, int hour, int minute);
    bool operator<(const Date &date);

    struct time
    {
        int month;
        int day;
        int hour;
        int minute;
    };

    time _time;
protected:


};

