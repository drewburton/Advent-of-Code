import java.lang.Math;

class Program
{
    private ifstream reader;

    private list<string> wire1;
    private list<string> wire2;

    private list<Point> wire1coor;
    private list<Point> wire2coor;

    private list<Point>* intersections;
    private void GetLastCoor(list<Point>& coordinates, int& x, int& y);

    private void ReadFile();
    private void ReadWire(list<string>*& list);
    private void CreateLine1();
    private void CreateLine2();
    private void ClosestIntersection();

    private boolean ContainsPoint(list<Point> points, int& x, int& y);

    Program(String filename)
    {
        
    }
};

