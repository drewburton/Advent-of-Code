import java.io.*;
import java.util.*;

class COM
{
    ArrayList<String> orbits = new ArrayList<String>();

    COM(String filename)
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext())
            {
                orbits.add(scanner.nextLine());
            }
            FindCom();

            scanner.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("file not found");
        }

    }

    private void FindCom()
    {
        for (String currentOrbit : orbits)
        {
            String[] objects = currentOrbit.split("\\)");
            //System.out.println(objects[0]);
            if (objects[0].equals("COM"))
            {
                Object first = new Object(1, objects[1], orbits);
                System.out.println(first.GetHowManyOrbits());
                return;
            }
        }
    }
}