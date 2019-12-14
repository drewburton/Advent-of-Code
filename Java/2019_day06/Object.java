import java.io.*;
import java.util.*;

class Object
{
    ArrayList<String> orbits = new ArrayList<String>();
    ArrayList<Object> objectsInOrbit = new ArrayList<Object>();
    private int distanceFromCOM = 0;
    private int numberOrbiting = 0;
    private String identification = "";

    Object(int distance, String id, ArrayList<String> o)
    {
        distanceFromCOM = distance;
        identification = id;
        orbits = o;
        FindOrbits();
    }

    public void FindOrbits()
    {
        for (String current : orbits)
        {
            String[] objects = current.split("\\)");
            if (objects[0].equals(identification))
            {
                objectsInOrbit.add(new Object(distanceFromCOM + 1, objects[1], orbits));
                numberOrbiting++;
            }
        }
    }

    public int GetHowManyOrbits()
    {
        if (numberOrbiting == 0)
        {
            return distanceFromCOM;
        }
        else
        {
            for (Object current : objectsInOrbit)
            {
                distanceFromCOM += current.GetHowManyIndirectOrbits();
            }
            return distanceFromCOM;
        }
    }

    public ArrayList<String> Find(ArrayList<String> path, String comp)
    {
        for (Object current : objectsInOrbit)
        {
            ArrayList<String> newPath = Copy(path);
            newPath.add(current.identification);
            if (current.identification.equals(comp))
            {
                return newPath;
            }
            else
            {
                ArrayList<String> possPath = current.Find(newPath, comp);
                if (possPath.size() > 0)
                {
                    if (possPath.get(possPath.size() - 1).equals(comp))
                        return possPath;
                }
            }
        }
        return path;
    }

    private ArrayList<String> Copy(ArrayList<String> copy)
    {
        ArrayList<String> temp = new ArrayList<String>();
        for (String loop : copy)
        {
            temp.add(loop);
        }
        return temp;
    }
}