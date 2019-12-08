import java.lang.Math;
import java.io.*; 
import java.util.*;

import javax.lang.model.util.ElementScanner6;

import javafx.geometry.Point2D;

class Program
{
    private ArrayList<Point2D> wire1 = new ArrayList<Point2D>();
    private ArrayList<Point2D> wire2 = new ArrayList<Point2D>();
    //private ArrayList<Point2D> intersections = new ArrayList<Point2D>();
    private HashSet<Point2D> intersections = new HashSet();

    Program(String filename)
    {
        ReadFile(filename);
        System.out.println(wire1.size());
        System.out.println(wire2.size());
        FindIntersection();
    }

    private void ReadFile(String filename)
    {
        ArrayList<ArrayList<Point2D>> wires = new ArrayList<ArrayList<Point2D>>();
        try
        {
            Scanner datafile = new Scanner(new File(filename));
            while (datafile.hasNext())
            {
                String currentLine = datafile.nextLine();
                ArrayList<Point2D> currentWire = new ArrayList<Point2D>();
                int currentx = 0;
                int currenty = 0;

                String[] paths = currentLine.split(",");
                for (String currentPath : paths)
                {
                    char direction = currentPath.charAt(0);
                    int magnitude = Integer.parseInt(currentPath.substring(1));

                    for (int i = 0; i < magnitude; i++)
                    {
                        if (direction == 'R')
                            currentx += 1;
                        else if (direction == 'L')
                            currentx -= 1;
                        else if (direction == 'U')
                            currenty += 1;
                        else
                            currenty -= 1;

                        currentWire.add(new Point2D(currentx, currenty));
                    }
                }
                wires.add(currentWire);
            }
            wire1 = wires.get(0);
            wire2 = wires.get(1);
        } 
        catch(FileNotFoundException ex)
        {
            System.out.println("could not find file");
        };
    }

    private void FindIntersection()
    {
        HashSet<Point2D> wire1Hash = new HashSet(wire1);
        HashSet<Point2D> wire2Hash = new HashSet(wire2);

        wire1Hash.retainAll(wire2Hash);
        intersections = wire1Hash;

        double distance = Double.MAX_VALUE;
        for (Point2D intersection : intersections)
        {
            int length1 = wire1.indexOf(intersection) + 1;
            int length2 = wire2.indexOf(intersection) + 1;

            if (length1 + length2 < distance)
                distance = length1 + length2;
            
            //pt1
            // if (Math.abs(intersection.getX()) + Math.abs(intersection.getY()) < distance)
            //     distance = Math.abs(intersection.getX()) + Math.abs(intersection.getY());
        }

        // for (int i2 = 0; i2 < wire2.size(); i2++)
        // {
        //     for (int i1 = 0; i1 < wire1.size(); i1++)
        //     {
        //         if (wire2.get(i2).equals(wire1.get(i1)))
        //             intersections.add(wire2.get(i2));
        //     }
        // }

        // double distance = Double.MAX_VALUE;
        // for (int i = 0; i < intersections.size(); i++)
        // {
        //     Point2D point = intersections.get(i);
        //     int length1 = wire1.indexOf(point) + 1;
        //     int length2 = wire2.indexOf(point) + 1;

        //     if (length1 + length2 < distance)
        //         distance = length1 + length2;
            
            //pt1
            // if (Math.abs(point.getX()) + Math.abs(point.getY()) < distance)
            //     distance = Math.abs(point.getX()) + Math.abs(point.getY());
        // }
        System.out.println("distance: " + distance);
    }
};

