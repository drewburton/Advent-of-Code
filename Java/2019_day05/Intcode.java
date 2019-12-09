import java.io.*; 
import java.util.*;

// 1 = add the next two pointers and place it in thrid pointer
// 2 = multiply the next two pointers and place it in third pointer
// step forward 4
// 3 = integer as input and saves to position given
// 4 = outputs value at position given
// 99 = done

class Intcode
{
    private static final int input = 1;

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
        } catch(FileNotFoundException ex)
        {

        }
    }

    private void FindInstructions(String instruction)
    {
        opcode = 0;
        mode.clear();
        for (int i = instruction.length() - 1; i >= 0; i--)
        {
            if (i == 0 || i == 1)
            {
                opcode += Integer.parseInt(Character.toString(instruction.charAt(i)));
                if (i == 1)
                    opcode *= 10;
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
        int first = 0;
        if (mode.get(0) == 0)
        {
            first = Integer.parseInt(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        }
        else if (mode.get(0) == 1)
        {
            first = Integer.parseInt(instuctions[instructionsIndex]);
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
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(first + second);
        return instructionsIndex + 1;
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
            first = Integer.parseInt(instuctions[instructionsIndex]);
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
        return instructionsIndex + 1;
    }

    private int Input(String[] instructions, int instructionsIndex)
    {
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(input);
        return instructionsIndex + 1;
    }

    private int Output(String[] instructions, int instructionsIndex)
    {
        System.out.println(instructions[Integer.parseInt(instructions[instructionsIndex])]);
        return instructionsIndex + 1;
    }
}