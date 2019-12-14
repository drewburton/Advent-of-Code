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

                ArrayList<String> you = new ArrayList<String>();
                you.add(objects[1]);
                you = first.Find(you, "YOU");
        
                ArrayList<String> san = new ArrayList<String>();
                san.add(objects[1]);
                san = first.Find(san, "SAN");

                ConnectSanta(you, san);
                //System.out.println(first.GetHowManyOrbits());
                return;
            }
        }
    }

    private void ConnectSanta(ArrayList<String> you, ArrayList<String> san)
    {
        int smaller = you.size() < san.size() ? you.size() : san.size();
        for (int i = 0; i < smaller; i++)
        {
            if (you.get(i).equals(san.get(i)))
                continue;
            else
            {
                i--;
                int lengthToYou = you.size() - 2 - i;
                int lengthToSan = san.size() - 2 - i;
                System.out.println(lengthToYou + lengthToSan);
                return;
            }
        }
    }
}