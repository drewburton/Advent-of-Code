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

            int highestOutput = 0;
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
                    amplifierA.RunProgram(AinEout, BinAout);
                    amplifierB.RunProgram(BinAout, CinBout);
                    amplifierC.RunProgram(CinBout, DinCout);
                    amplifierD.RunProgram(DinCout, EinDout);
                    if (amplifierE.RunProgram(EinDout, AinEout) == var)
                    {
                        if (AinEout.get(AinEout.size() - 1) > highestOutput)
                            highestOutput = AinEout.get(AinEout.size() - 1);
                        System.out.println(highestOutput);
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
        boolean duplicatePhase = true;
        ArrayList<String> phaseArray = new ArrayList<String>();
        while (duplicatePhase)
        {
            phaseSettings.clear();
            phaseArray.clear();
            String phaseString = Integer.toString(phase); 
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

            duplicatePhase = false;
            for (int a = 0; a < phaseSettings.size(); a++)
            {
                for (int b = 0; b < phaseSettings.size(); b++)
                {
                    if (a == b)
                        continue;
                    if (phaseSettings.get(a).equals(phaseSettings.get(b)))
                        duplicatePhase = true;
                }
            }
            phase++;
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