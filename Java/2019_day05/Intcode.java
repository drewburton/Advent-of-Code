import java.io.*; 
import java.util.*;

import javax.lang.model.util.ElementScanner6;

// 1 = add the next two pointers and place it in thrid pointer
// 2 = multiply the next two pointers and place it in third pointer
// step forward 4
// 3 = integer as input and saves to position given
// 4 = outputs value at position given
// 99 = done

class Intcode
{
    private static final int input = 5;

    private int opcode = 0;
    private ArrayList<Integer> mode = new ArrayList<Integer>();

    private String[] instructions;
    private int instructionsIndex = 0;

    Intcode(String filename)
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            
            String input = "";

            if (scanner.hasNext())
            {
                input = scanner.nextLine();
            }
            instructions = input.split(",");

            // go through each instructions
            for (instructionsIndex = 0; instructionsIndex < instructions.length; instructionsIndex++)
            {
                String instruction = instructions[instructionsIndex];
                
                FindInstructions(instruction);

                // follow instructions and move on to the next instruction pointer
                instructionsIndex++;
                if (opcode == 1)
                    Add();
                else if (opcode == 2)
                    Multiply();
                else if (opcode == 3)
                    Input();
                else if (opcode == 4)
                    Output();
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
                    System.out.println("Somthing has gone horribly wrong.");
                    System.out.println(instruction);
                    break;
                }
            }
            scanner.close();
        } 
        catch(FileNotFoundException ex)
        {
            System.out.println("file not found");
        }
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
        instructions[GetParam(1)] = Integer.toString(first + second);
    }

    private void Multiply()
    {
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        instructions[GetParam(1)] = Integer.toString(first * second);
    }

    private void Input()
    {
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(input);
    }

    private void Output()
    {
        if (mode.get(0) == 0)
            System.out.println(instructions[GetParam(1)]);
        else
            System.out.println(instructions[instructionsIndex]);
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
            instructions[GetParam(1)] = "1";
        else
            instructions[GetParam(1)] = "0";
    }

    private void Equals()
    {
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        if (first == second)
            instructions[GetParam(1)] = "1";
        else
            instructions[GetParam(1)] = "0";
    }



    // Utility methods
    private int GetParam(int mode)
    {
        int param = 0;
        if (mode == 0)
        {
            param = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode == 1)
        {
            String temp = instructions[instructionsIndex];
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
        String temp = instructions[instructionsIndex];
        return Integer.parseInt(temp);
    }

    private int Position()
    {
        return Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
    }
}