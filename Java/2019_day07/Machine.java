import java.io.*; 
import java.util.*;

class Machine
{
    String[] instructions;
    ArrayList<Integer> phaseSettings = new ArrayList<Integer>();

    Machine(String filename)
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            
            String fileInput = "";

            if (scanner.hasNext())
            {
                fileInput = scanner.nextLine();
            }
            instructions = fileInput.split(",");
            
            for (int i = 1234; i < 43210; i++)
            {
                i = GetPhase(i);

                Intcode7 amplifierA = new Intcode7(instructions);
                Intcode7 amplifierB = new Intcode7(instructions);
                Intcode7 amplifierC = new Intcode7(instructions);
                Intcode7 amplifierD = new Intcode7(instructions);
                Intcode7 amplifierE = new Intcode7(instructions);
                
                ArrayList<Integer> AinEout = new ArrayList<Integer>();
                ArrayList<Integer> BinAout = new ArrayList<Integer>();
                ArrayList<Integer> CinBout = new ArrayList<Integer>();
                ArrayList<Integer> DinCout = new ArrayList<Integer>();
                ArrayList<Integer> EinDout = new ArrayList<Integer>();

                AinEout.add(phaseSettings.get(0));
                AinEout.add(0);
                BinAout.add(phaseSettings.get(1));
                CinBout.add(phaseSettings.get(2));
                DinCout.add(phaseSettings.get(3));
                EinDout.add(phaseSettings.get(4));

                while (true)
                {
                    Intcode7.status var = Intcode7.status.done;
                    if (amplifierA.RunProgram(AinEout, BinAout) == var)
                    {
                        System.out.println(BinAout.get(BinAout.size() - 1));
                        break;
                    }
                    if (amplifierB.RunProgram(BinAout, CinBout) == var)
                    {
                        System.out.println(CinBout.get(CinBout.size() - 1));
                        break;
                    }
                    if (amplifierC.RunProgram(CinBout, DinCout) == var)
                    {
                        System.out.println(DinCout.get(DinCout.size() - 1));
                        break;
                    }
                    if (amplifierD.RunProgram(DinCout, EinDout) == var)
                    {
                        System.out.println(EinDout.get(EinDout.size() - 1));
                        break;
                    }
                    if (amplifierE.RunProgram(EinDout, AinEout) == var)
                    {
                        System.out.println(AinEout);
                        break;
                    }
                }
            }
            scanner.close();
        } 
        catch(FileNotFoundException ex)
        {
            System.out.println("file not found");
        }
    }

    private int GetPhase(int phase)
    {
        phaseSettings.clear();
        String phaseString = Integer.toString(phase); 
        ArrayList<String> phaseArray = new ArrayList<String>();
        for (int i = 0; i < phaseString.length(); i++)
        {
            phaseArray.add(phaseString.charAt(i) + "");
        }
        // add in zeros to fill the phase values
        while (phaseArray.size() < 5)
        {
            phaseArray.add(0, "0");
        }
        // make sure none of the values are above 4
        for (int i = phaseArray.size() - 1; i >= 0; i--)
        {
            if (Integer.parseInt(phaseArray.get(i)) > 4)
            {
                phaseArray.set(i, "0");
                phaseArray.set(i - 1, Integer.toString(Integer.parseInt(phaseArray.get(i - 1)) + 1));
            }
        }
       
        // fill the phase settings
        for (String values : phaseArray)
        {
            phaseSettings.add(Integer.parseInt(values));
        }

        // find the index value that is the array
        String filler = "";
        for (String test : phaseArray)
        {
            filler += test;
        }
        return Integer.parseInt(filler);
    }
}