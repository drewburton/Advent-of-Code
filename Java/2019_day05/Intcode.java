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
            for (int i = 0; i < instructions.length; i++)
            {
                String instruction = instructions[i];
                
                FindInstructions(instruction);

                // follow instructions and move on to the next instruction pointer
                i++;
                if (opcode == 1)
                    i = Add(instructions, i);
                else if (opcode == 2)
                    i = Multiply(instructions, i);
                else if (opcode == 3)
                    i = Input(instructions, i);
                else if (opcode == 4)
                    i = Output(instructions, i);
                else if (opcode == 5)
                    i = JumpTrue(instructions, i);
                else if (opcode == 6)
                    i = JumpFalse(instructions, i);
                else if (opcode == 7)
                    i = LessThan(instructions, i);
                else if (opcode == 8)
                    i = Equals(instructions, i);
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

    private int Add(String[] instructions, int instructionsIndex)
    {
        int first = FirstParam(instructionsIndex);


        instructionsIndex++;
        int second = 0;
        if (mode.get(1) == 0)
        {
            second = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(1) == 1)
        {
            String temp = instructions[instructionsIndex];
            second = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding second failed");
        }

        instructionsIndex++;
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(first + second);
        return instructionsIndex;
    }

    private int Multiply(String[] instructions, int instructionsIndex)
    {
        int first = 0;
        if (mode.get(0) == 0)
        {
            first = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(0) == 1)
        {
            String temp = instructions[instructionsIndex];
            first = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding first failed");
        }

        instructionsIndex++;
        int second = 0;
        if (mode.get(1) == 0)
        {
            second = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(1) == 1)
        {
            second = Integer.parseInt(instructions[instructionsIndex]);
        }
        else
        {
            System.out.println("finding second failed");
        }

        instructionsIndex++;
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(first * second);
        return instructionsIndex;
    }

    private int Input(String[] instructions, int instructionsIndex)
    {
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(input);
        return instructionsIndex;
    }

    private int Output(String[] instructions, int instructionsIndex)
    {
        if (mode.get(0) == 0)
        {
            System.out.println(instructions[Integer.parseInt(instructions[instructionsIndex])]);
            return instructionsIndex;
        }
        else
        {
            System.out.println(instructions[instructionsIndex]);
            return instructionsIndex;
        }
    }

    private int JumpTrue(String[] instructions, int instructionsIndex)
    {
        // something is wrong 
        if (mode.get(0) == 0)
        {
            if (Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]) != 0)
            {
                instructionsIndex++;
                if (mode.get(1) == 0)
                    instructionsIndex = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]) - 1;
                else
                {
                    String temp = instructions[instructionsIndex];
                    instructionsIndex = Integer.parseInt(temp) - 1;
                }
            }
            else
                instructionsIndex++;
        }
        else
        {
            if (Integer.parseInt(instructions[instructionsIndex]) != 0)
            {
                instructionsIndex++;
                if (mode.get(1) == 0)
                    instructionsIndex = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]) - 1;
                else
                {
                    String temp = instructions[instructionsIndex];
                    instructionsIndex = Integer.parseInt(temp) - 1;
                }
            }
            else
                instructionsIndex++;
        }
        return instructionsIndex;
    }

    private int JumpFalse(String[] instructions, int instructionsIndex)
    {
        if (mode.get(0) == 0)
        {
            if (Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]) == 0)
            {
                instructionsIndex++;
                if (mode.get(1) == 0)
                    instructionsIndex = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]) - 1;
                else
                {
                    String temp = instructions[instructionsIndex];
                    instructionsIndex = Integer.parseInt(temp) - 1;
                }
            }
            else
                instructionsIndex++;
        }
        else
        {
            if (Integer.parseInt(instructions[instructionsIndex]) == 0)
            {
                instructionsIndex++;
                if (mode.get(1) == 0)
                    instructionsIndex = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]) - 1;
                else
                {
                    String temp = instructions[instructionsIndex];
                    instructionsIndex = Integer.parseInt(temp) - 1;
                }
            }
            else
                instructionsIndex++;
        }
        return instructionsIndex;
    }

    private int LessThan(String[] instructions, int instructionsIndex)
    {
        int first = 0;
        if (mode.get(0) == 0)
        {
            first = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(0) == 1)
        {
            String temp = instructions[instructionsIndex];
            first = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding first failed");
        }

        instructionsIndex++;
        int second = 0;
        if (mode.get(1) == 0)
        {
            second = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(1) == 1)
        {
            String temp = instructions[instructionsIndex];
            second = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding second failed");
        }

        instructionsIndex++;
        if (first < second)
            instructions[Integer.parseInt(instructions[instructionsIndex])] = "1";
        else
            instructions[Integer.parseInt(instructions[instructionsIndex])] = "0";

        return instructionsIndex;
    }

    private int Equals(String[] instructions, int instructionsIndex)
    {
        int first = 0;
        if (mode.get(0) == 0)
        {
            first = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(0) == 1)
        {
            String temp = instructions[instructionsIndex];
            first = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding first failed");
        }

        instructionsIndex++;
        int second = 0;
        if (mode.get(1) == 0)
        {
            second = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(1) == 1)
        {
            String temp = instructions[instructionsIndex];
            second = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding second failed");
        }

        instructionsIndex++;
        if (first == second)
            instructions[Integer.parseInt(instructions[instructionsIndex])] = "1";
        else
            instructions[Integer.parseInt(instructions[instructionsIndex])] = "0";

        return instructionsIndex;
    }



    // Utility methods
    private int FirstParam(int instructionsIndex)
    {
        int first = 0;
        if (mode.get(0) == 0)
        {
            first = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(0) == 1)
        {
            String temp = instructions[instructionsIndex];
            first = Integer.parseInt(temp);
        }
        else
        {
            System.out.println("finding first failed");
        }
        return first;
    }
}