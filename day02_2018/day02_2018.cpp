#include "stdafx.h"
#include "Reader.h"

/* test for part 1
abcdef
bababc
abbcde
abcccd
aabcdd
abcdee
ababab
*/

int main()
{
    Reader reader("Data.txt");
    //cout << reader.Checksum() << endl;
    cout << reader.SimilarCharacters() << endl;
    return 0;
}

