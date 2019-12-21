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
    public enum status { paused, done, running };

    private int opcode = 0;
    private ArrayList<Integer> mode = new ArrayList<Integer>();

    private String[] instructions;
    private int instructionsIndex = 0;

    private ArrayList<Integer> input;
    public ArrayList<Integer> output;

    Intcode7(String[] originalInstructions)
    {
        instructions = originalInstructions.clone();
    }

    public status RunProgram(ArrayList<Integer> i, ArrayList<Integer> o)
    {
        input = i;
        output = o;

        opcode = 0;
        while (opcode != 99)
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
                if (Input() == status.paused)
                    return status.paused;
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
        }
        status var = status.done;
        return var;
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
        instructionsIndex++;
    }

    private void Multiply()
    {
        int first = GetParam(mode.get(0));

        instructionsIndex++;
        int second = GetParam(mode.get(1));

        instructionsIndex++;
        instructions[GetParam(1)] = Integer.toString(first * second);
        instructionsIndex++;
    }

    private status Input()
    {
        if (input.size() == 0)
            return status.paused;

        int currentInput = input.get(0);
        input.remove(0);
        
        instructions[Integer.parseInt(instructions[instructionsIndex])] = Integer.toString(currentInput);
        instructionsIndex++;
        status var = status.running;
        return var;
    }

    private void Output()
    {
        int currentOutput = 0;
        if (mode.get(0) == 0)
        {
            currentOutput = Integer.parseInt(instructions[GetParam(1)]);
        }
        else
        {
            currentOutput = Integer.parseInt(instructions[instructionsIndex]);
        }
        instructionsIndex++;
        output.add(currentOutput);
    }

    private void JumpTrue()
    {
        if (mode.get(0) == 0)
        {
            if (Position() == 0)
            {
                instructionsIndex += 2;
                return;
            }
        }
        else
        {
            if (Immediate() == 0)
            {
                instructionsIndex += 2;
                return;
            }
        }

        instructionsIndex++;
        if (mode.get(1) == 0)
            instructionsIndex = Position();
        else
            instructionsIndex = Immediate();
    }

    private void JumpFalse()
    {
        if (mode.get(0) == 0)
        {
            if (Position() != 0)
            {
                instructionsIndex += 2;
                return;
            }
        }
        else
        {
            if (Immediate() != 0)
            {
                instructionsIndex += 2;
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

        instructionsIndex++;
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

        instructionsIndex++;
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