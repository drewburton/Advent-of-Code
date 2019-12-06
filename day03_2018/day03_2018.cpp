#include "pch.h"
#include "Reader.h"

int main()
{
    Reader reader("data.txt");
    cout << reader.UncontestedId() << endl;
}