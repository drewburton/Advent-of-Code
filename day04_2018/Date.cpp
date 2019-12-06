#include "pch.h"
#include "Date.h"


Date::Date()
{
    _time.month = 0;
    _time.day = 0;
    _time.hour = 0;
    _time.minute = 0;
}

Date::~Date()
{

}

void Date::setTime(int month, int day, int hour, int minute)
{
    _time.month = month;
    _time.day = day;
    _time.hour = hour;
    _time.minute = minute;
}

bool Date::operator<(const Date &date)
{
    if ((_time.month < date._time.month) || (_time.month > date._time.month))
        return _time.month < date._time.month ? true : false;
    if ((_time.day < date._time.day) || (_time.day > date._time.day))
        return _time.day < date._time.day ? true : false;
    if ((_time.hour < date._time.hour) || (_time.hour > date._time.hour))
        return _time.hour < date._time.hour ? false : true; // backwards because 23:00 is before 00:00
    if ((_time.minute < date._time.minute) || (_time.minute > date._time.minute))
        return _time.minute < date._time.minute ? true : false;
    return false;
}