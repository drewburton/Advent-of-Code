import java.io.*; 
import java.util.*;

import javax.lang.model.util.ElementScanner6;

// 1 = add the next two pointers and place it in thrid pointer
// 2 = multiply the next two pointers and place it in third pointer
// step forward 4
// 3 = integer as input and saves to position given
// 4 = outputs value at position given
// 99 = done

class Intcode7
{
    ArrayList<Integer> phaseSettings = new ArrayList<Integer>();
    private int currentInput = 0;

    private int opcode = 0;
    private ArrayList<Integer> mode = new ArrayList<Integer>();

    private String[] instructions;
    private String[] copyInstructions;

    private int[] indexes = new int[4];

    Intcode7(String filename)
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

            int maxOutput = 0;
            for (int i = 1234; i < 43210; i++)
            {
                i = GetPhase(i);

                // Amplifier A
                indexes[0] = 0;
                int currentOutput = RunProgram(0, phaseSettings.get(0), 0);

                // Amplifier B
                indexes[1] = 0;
                currentOutput = RunProgram(currentOutput, phaseSettings.get(1), 1);

                // Amplifier C
                indexes[2] = 0;
                currentOutput = RunProgram(currentOutput, phaseSettings.get(2), 2);

                // Amplifier D
                indexes[3] = 0;
                currentOutput = RunProgram(currentOutput, phaseSettings.get(3), 3);

                // Amplifier E
                indexes[4] = 0;
                currentOutput = RunProgram(currentOutput, phaseSettings.get(4), 4);

                boolean duplicatePhase = false;
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
                if (currentOutput > maxOutput && !duplicatePhase)
                {
                    maxOutput = currentOutput;
                    System.out.println(maxOutput + " " + i);
                }
            }
            scanner.close();
        } 
        catch(FileNotFoundException ex)
        {
            System.out.println("file not found");
        }
    }


    private int RunProgram(int input, int phase, int index)
    {
        int instructionsIndex = indexes[index];
        currentInput = 0;
        copyInstructions = instructions.clone();
        opcode = 0;
        while (opcode != 99)
        {
            String instruction = copyInstructions[instructionsIndex];
            FindInstructions(instruction);

            // follow instructions and move on to the next instruction pointer
            instructionsIndex++;
            if (opcode == 1)
                Add(index);
            else if (opcode == 2)
                Multiply(index);
            else if (opcode == 3)
                Input(input, phase, index);
            else if (opcode == 4)
                return Output(index);
            else if (opcode == 5)
                JumpTrue(index);
            else if (opcode == 6)
                JumpFalse(index);
            else if (opcode == 7)
                LessThan(index);
            else if (opcode == 8)
                Equals(index);
        }
        return 0;
    }

    private void FindInstructions(String instruction)
    {
        opcode = 0;
        mode.clear();
        for (int i = instruction.length() - 1; i >= 0; i--)
        {
            if (i == instruction.length() - 1 || i == instruction.length() - 2)
            {
                if (i == instruction.length() - 2)
                    opcode += 10 * Integer.parseInt(Character.toString(instruction.charAt(i)));
                else
                    opcode += Integer.parseInt(Character.toString(instruction.charAt(i)));
            }
            else
            {
                mode.add(Integer.parseInt(Character.toString(instruction.charAt(i))));
            }
        }
        while (mode.size() < 3)
        {
            mode.add(0);
        }
    }

    private void Add(int index)
    {
        int instructionsIndex = indexes[index];
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        copyInstructions[GetParam(1)] = Integer.toString(first + second);
        indexes[index] = instructionsIndex + 1;
    }

    private void Multiply(int index)
    {
        int instructionsIndex = indexes[index];
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        copyInstructions[GetParam(1)] = Integer.toString(first * second);
        indexes[index] = instructionsIndex + 1;
    }

    private void Input(int input, int phase, int index)
    {
        int instructionsIndex = indexes[index];
        String finalInput = "";
        if (currentInput == 0)
        {
            finalInput = Integer.toString(phase);
            currentInput++;
        }
        else
        {
            finalInput = Integer.toString(input);
        }
        copyInstructions[Integer.parseInt(copyInstructions[instructionsIndex])] = finalInput;
        indexes[index] = instructionsIndex + 1;
    }

    private int Output(int index)
    {
        int instructionsIndex = indexes[index];
        if (mode.get(0) == 0)
        {
            int output = Integer.parseInt(copyInstructions[GetParam(1)]);
            indexes[index] = instructionsIndex + 1;
            return output;
        }
        else
        {
            int output = Integer.parseInt(copyInstructions[instructionsIndex]);
            indexes[index] = instructionsIndex + 1;
            return output;
        }
    }

    private void JumpTrue(int index)
    {
        int instructionsIndex = indexes[index];
        if (mode.get(0) == 0)
        {
            if (Position() == 0)
            {
                indexes[index] = instructionsIndex + 2;
                return;
            }
        }
        else
        {
            if (Immediate() == 0)
            {
                indexes[index] = instructionsIndex + 2;
                return;
            }
        }

        instructionsIndex++;
        if (mode.get(1) == 0)
            instructionsIndex = Position();
        else
            instructionsIndex = Immediate();

        indexes[index] = instructionsIndex;
    }

    private void JumpFalse(int index)
    {
        int instructionsIndex = indexes[index];
        if (mode.get(0) == 0)
        {
            if (Position() != 0)
            {
                indexes[index] = instructionsIndex + 2;
                return;
            }
        }
        else
        {
            if (Immediate() != 0)
            {
                indexes[index] = instructionsIndex + 2;
                return;
            }
        }
        
        instructionsIndex++;
        if (mode.get(1) == 0)
            instructionsIndex = Position();
        else
        {
            instructionsIndex = Immediate();
        }

        indexes[index] = instructionsIndex;
    }

    private void LessThan(int index)
    {
        int instructionsIndex = indexes[index];
        int first = GetParam(mode.get(0));
        
        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        if (first < second)
            copyInstructions[GetParam(1)] = "1";
        else
            copyInstructions[GetParam(1)] = "0";

        indexes[index] = instructionsIndex + 1;
    }

    private void Equals(int index)
    {
        int instructionsIndex = indexes[index];
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        if (first == second)
            copyInstructions[GetParam(1)] = "1";
        else
            copyInstructions[GetParam(1)] = "0";

        indexes[index] = instructionsIndex + 1;
    }



    // Utility methods
    private int GetParam(int mode)
    {
        int param = 0;
        if (mode == 0)
        {
            param = Integer.parseInt(copyInstructions[Integer.parseInt(copyInstructions[instructionsIndex])]);
        }
        else if (mode == 1)
        {
            String temp = copyInstructions[instructionsIndex];
            param = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding first failed");
        }
        return param;
    }

    private int Immediate()
    {
        String temp = copyInstructions[instructionsIndex];
        return Integer.parseInt(temp);
    }

    private int Position()
    {
        return Integer.parseInt(copyInstructions[Integer.parseInt(copyInstructions[instructionsIndex])]);
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