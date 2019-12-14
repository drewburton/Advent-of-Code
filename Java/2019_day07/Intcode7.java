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
    private int instructionsIndex = 0;

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
                int currentOutput = RunProgram(0, phaseSettings.get(0));

                // Amplifier B
                currentOutput = RunProgram(currentOutput, phaseSettings.get(1));

                // Amplifier C
                currentOutput = RunProgram(currentOutput, phaseSettings.get(2));

                // Amplifier D
                currentOutput = RunProgram(currentOutput, phaseSettings.get(3));

                // Amplifier E
                currentOutput = RunProgram(currentOutput, phaseSettings.get(4));

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


    private int RunProgram(int input, int phase)
    {
        currentInput = 0;
        copyInstructions = instructions.clone();
        for (instructionsIndex = 0; instructionsIndex < instructions.length; instructionsIndex++)
        {
            String instruction = copyInstructions[instructionsIndex];
            FindInstructions(instruction);

            // follow instructions and move on to the next instruction pointer
            instructionsIndex++;
            if (opcode == 1)
                Add();
            else if (opcode == 2)
                Multiply();
            else if (opcode == 3)
                Input(input, phase);
            else if (opcode == 4)
                return Output();
            else if (opcode == 5)
                JumpTrue();
            else if (opcode == 6)
                JumpFalse();
            else if (opcode == 7)
                LessThan();
            else if (opcode == 8)
                Equals();
            else if (opcode == 99)
            {
                System.out.println("done!");
                break;
            }
            else
            {
                break;
            }
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

    private void Add()
    {
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        copyInstructions[GetParam(1)] = Integer.toString(first + second);
    }

    private void Multiply()
    {
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        copyInstructions[GetParam(1)] = Integer.toString(first * second);
    }

    private void Input(int input, int phase)
    {
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

    }

    private int Output()
    {
        if (mode.get(0) == 0)
        {
            return Integer.parseInt(copyInstructions[GetParam(1)]);
        }
        else
        {
            return Integer.parseInt(copyInstructions[instructionsIndex]);
        }
    }

    private void JumpTrue()
    {
        if (mode.get(0) == 0)
        {
            if (Position() == 0)
            {
                instructionsIndex++;
                return;
            }
        }
        else
        {
            if (Immediate() == 0)
            {
                instructionsIndex++;
                return;
            }
        }

        instructionsIndex++;
        if (mode.get(1) == 0)
            instructionsIndex = Position() - 1;
        else
            instructionsIndex = Immediate() - 1;
    }

    private void JumpFalse()
    {
        if (mode.get(0) == 0)
        {
            if (Position() != 0)
            {
                instructionsIndex++;
                return;
            }
        }
        else
        {
            if (Immediate() != 0)
            {
                instructionsIndex++;
                return;
            }
        }
        
        instructionsIndex++;
        if (mode.get(1) == 0)
            instructionsIndex = Position() - 1;
        else
        {
            instructionsIndex = Immediate() - 1;
        }
    }

    private void LessThan()
    {
        int first = GetParam(mode.get(0));
        
        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        if (first < second)
            copyInstructions[GetParam(1)] = "1";
        else
            copyInstructions[GetParam(1)] = "0";
    }

    private void Equals()
    {
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        if (first == second)
            copyInstructions[GetParam(1)] = "1";
        else
            copyInstructions[GetParam(1)] = "0";
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