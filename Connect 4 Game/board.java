/*
Name: Anurag Reddy Pingili
UTA ID: 1001863623
Class: 2222-CSE-5360-001
*/


import java.io.*;

public class board {

    private int[][] game_Board;
    private int turn_keeper;
    private boolean computerfirst;
    private int counter_piece;

    public board(String inputFile) 
    {
        this.game_Board = new int[6][7];
        this.computerfirst = true;
        int count = 0;
        this.counter_piece = 0;
        String data = null;
        BufferedReader input_file = null;

        /* Reading the input file
        */
        try {
            input_file = new BufferedReader(new FileReader(inputFile));
        } 
        catch (IOException e) 
        {
            System.out.println("\nCannot open the input file.\n");
            e.printStackTrace();
        }

        int i=0;
        while(i<6)
        {
            try {
                data = input_file.readLine();
    
                int j=0;
                while(j<7) 
                {

                    this.game_Board[i][j] = data.charAt(count++) - 48;

                    if (!((this.game_Board[i][j] == 0) || (this.game_Board[i][j] == 1) || (this.game_Board[i][j] == 2))) 
                    {
                        System.out.println("\nERROR\n--The input from the input file was not a 1, 2 or 0");
                        this.exit_function(0);
                    }
                    if (this.game_Board[i][j] > 0) 
                    {
                        this.counter_piece++;
                    }
                    j++;
                }
            } catch (Exception e) 
            {
                System.out.println("\nInput file Error\n");
                e.printStackTrace();
                this.exit_function(0);
            }
              //System.out.println("Resetting the counter after this");

            count = 0;
        i++;
        } //System.out.println("While loop for game board ended");
              
        try {
            data = input_file.readLine();
        } 
        catch (Exception e) {
            System.out.println("\nError. Please try again.\n");
            e.printStackTrace();
        }

        this.turn_keeper = data.charAt(0) - 48;
        if (!((this.turn_keeper == 1) || (this.turn_keeper == 2))) 
        {
            System.out.println("Problems!\nError! The current input is not a 1 or 2");
            this.exit_function(0);
        }
         else
          if (this.turn_keeper_method() != this.turn_keeper) 
          {
            System.out.println("Error! Current turn read doesn't match the number of pieces played.");
            this.exit_function(0);
        }
    } 


    public board(int gameboard1[][])
     {
        this.game_Board = new int[6][7];
        this.counter_piece = 0;
        for (int i = 0; i < 6; i++) 
        {
            for (int j = 0; j < 7; j++) 
            {
                this.game_Board[i][j] = gameboard1[i][j];
                if (this.game_Board[i][j] > 0) 
                {
                    this.counter_piece++;
                }
            }
        }
    } 


    public int Score_Counter(int user) 
    {

        int score_current = 0;

         /**Checking forward diagonally */
         for (int i = 0; i < 3; i++) 
         {
             for (int j = 0; j < 4; j++) 
             {
                 if ((this.game_Board[i + 3][j] == user) && (this.game_Board[i + 2][j + 1] == user) &&  (this.game_Board[i + 1][j + 2] == user) && (this.game_Board[i][j + 3] == user)) 
                 {
                     score_current++;
                 }
             }
         }

        /**Checking horizontally */
        for (int i = 0; i < 6; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                if ((this.game_Board[i][j] == user) && (this.game_Board[i][j + 1] == user) && (this.game_Board[i][j + 2] == user) && (this.game_Board[i][j + 3] == user)) 
                {
                    score_current++;
                }
            }
        } 
        
        
         /**Checking backward diagonally */
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                if ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j + 1] == user) && (this.game_Board[i + 2][j + 2] == user) && (this.game_Board[i + 3][j + 3] == user)) 
                {
                    score_current++;
                }
            }
        }

         /**Checking Vertically */
         for (int i = 0; i < 3; i++) 
         {
             for (int j = 0; j < 7; j++) 
             {
                 if ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j] == user) && (this.game_Board[i + 2][j] == user) && (this.game_Board[i + 3][j] == user))
                  {
                     score_current++;
                 }
             }
         } 
          
         //System.out.println("Score Checking ends here");

        return score_current;
    } 


    public int counting_threes(int user) 
    {

        int score_current = 0;


         /**Checking Vertically */
         for (int i = 0; i < 3; i++) 
         {
            for (int j = 0; j < 7; j++) 
            {
                if ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j] == user) && (this.game_Board[i + 2][j] == user) && (this.game_Board[i + 3][j] == 0)) 
                {
                    score_current++;
                }
            }
        } 

         /**Checking forward diagonally */
         for (int i = 0; i < 3; i++) 
         {
            for (int j = 0; j < 4; j++) 
            {
                if (((this.game_Board[i + 3][j] == 0) && (this.game_Board[i + 2][j + 1] == user) && (this.game_Board[i + 1][j + 2] == user) && (this.game_Board[i][j + 3] == user)) || ((this.game_Board[i + 3][j] == user) && (this.game_Board[i + 2][j + 1] == user) && (this.game_Board[i + 1][j + 2] == user) && (this.game_Board[i][j + 3] == 0)) || ((this.game_Board[i + 3][j] == user) && (this.game_Board[i + 2][j + 1] == 0) && (this.game_Board[i + 1][j + 2] == user) && (this.game_Board[i][j + 3] == user)) || ((this.game_Board[i + 3][j] == user) && (this.game_Board[i + 2][j + 1] == user) && (this.game_Board[i + 1][j + 2] == 0) && (this.game_Board[i][j + 3] == user))) 
                {
                    score_current++;
                }
            }
        }


        /**Checking horizontally */
        for (int i = 0; i < 6; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                if (((this.game_Board[i][j] == user) && (this.game_Board[i][j + 1] == user) && (this.game_Board[i][j + 2] == user) && (this.game_Board[i][j + 3] == 0)) || ((this.game_Board[i][j] == 0) && (this.game_Board[i][j + 1] == user) && (this.game_Board[i][j + 2] == user) && (this.game_Board[i][j + 3] == user)) || ((this.game_Board[i][j] == user) && (this.game_Board[i][j + 1] == 0) &&  (this.game_Board[i][j + 2] == user) &&  (this.game_Board[i][j + 3] == user)) || ((this.game_Board[i][j] == user) && (this.game_Board[i][j + 1] == user) && (this.game_Board[i][j + 2] == 0) && (this.game_Board[i][j + 3] == user)))
                 {
                    score_current++;
                }
            }
        }


         /**Checking backward diagonally */
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                if ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j + 1] == user) && (this.game_Board[i + 2][j + 2] == user) && (this.game_Board[i + 3][j + 3] == 0) || ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j + 1] == 0) && (this.game_Board[i + 2][j + 2] == user) && (this.game_Board[i + 3][j + 3] == user)) || ((this.game_Board[i][j] == 0) && (this.game_Board[i + 1][j + 1] == user) && (this.game_Board[i + 2][j + 2] == user) && (this.game_Board[i + 3][j + 3] == user)) || ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j + 1] == user) && (this.game_Board[i + 2][j + 2] == 0) && (this.game_Board[i + 3][j + 3] == user))) 
                {
                    score_current++;
                }
            }
        }

        //System.out.println("Counting for threes ends here");
        return score_current;
    }

    public int counting_twos(int user) 
    {

        int score_current = 0;

         /**Checking Vertically */
         for (int i = 0; i < 3; i++) 
         {
             for (int j = 0; j < 7; j++) 
             {
                 if ((this.game_Board[i][j] == user) && (this.game_Board[i + 1][j] == user) && (this.game_Board[i + 2][j] == 0) && (this.game_Board[i + 3][j] == 0)) 
                 {
                     score_current++;
                 }
             }
         }


        /**Checking horizontally */
        for (int i = 0; i < 6; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                int s = 0;
                if(this.game_Board[i][j] == user) 
                {
                    s++;
                }
                 else
                 if(this.game_Board[i][j] == 0) 
                 {

                } 
                else
                {
                    s--;
                }
                if(this.game_Board[i][j+1] == user) 
                {
                    s++;
                } 
                else
                 if(this.game_Board[i][j+1] == 0) 
                 {

                }
                 else 
                {
                    s--;
                }
                if(this.game_Board[i][j+2] == user) 
                {
                    s++;
                }
                 else
                if(this.game_Board[i][j+2] == 0) 
                {

                } else {
                    s--;
                }
                if(this.game_Board[i][j+3] == user) 
                {
                    s++;
                }
                 else
                  if(this.game_Board[i][j+3] == 0) 
                  {

                } 
                else 
                {
                    s--;
                }

                if (s == 2) 
                {
                    score_current++;
                }
            }
        } 

/**Checking forward diagonally */
for (int i = 0; i < 3; i++) 
{
    for (int j = 0; j < 4; j++) 
    {
        int s = 0;
        if(this.game_Board[i+3][j] == user) 
        {
            s++;
        } 
         else 
         if(this.game_Board[i+3][j] == 0) 
         {

        }
         else 
         {
            s--;
        }
        if(this.game_Board[i+2][j+1] == user) 
        {
            s++;
        }
         else
          if(this.game_Board[i+2][j+1] == 0) 
          {

        } 
        else {
            s--;
        }
        if(this.game_Board[i+1][j+2] == user) 
        {
            s++;
        }
         else
          if(this.game_Board[i+1][j+2] == 0) 
          {

        } 
        else 
        {
            s--;
        }
        if(this.game_Board[i][j+3] == user) 
        {
            s++;
        }
         else
          if(this.game_Board[i][j+3] == 0) 
          {

        }
         else
        {
            s--;
        }
        if (s == 2*user) 
        {
            score_current++;
        }
    }
}

         /**Checking backward diagonally */
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                int s = 0;
                if(this.game_Board[i][j] == user) 
                {
                    s++;
                }
                 else
                  if(this.game_Board[i][j] == 0) 
                  {

                } 
                else
                 {
                    s--;
                }
                if(this.game_Board[i+1][j+1] == user) 
                {
                    s++;
                }
                 else
                  if(this.game_Board[i+1][j+1] == 0) 
                {

                }
                 else
                  {
                    s--;
                }
                if(this.game_Board[i+2][j+2] == user) 
                {
                    s++;
                }
                 else
                  if(this.game_Board[i+2][j+2] == 0) 
                {

                }
                 else
                  {
                    s--;
                }
                if(this.game_Board[i+3][j+3] == user) 
                {
                    s++;
                }
                 else 
                 if(this.game_Board[i+3][j+3] == 0) 
                 {

                } else 
                {
                    s--;
                }
                if (s == 2) 
                {
                    score_current++;
                }
            }
        }

         
        return score_current;
    } 

    public int counting_ones(int user) 
    {
        int score_current = 0;
        for (int i = 0; i < 6; i++) 
        {
            for (int j = 0; j < 7; j++) 
            {
                if (this.game_Board[i][j] == user) 
                {
                    score_current++;
                }
            }
        } 

        return score_current;
    }

    public int turn_keeper_method() 
    {
        return (this.counter_piece % 2) + 1;
    }

    
    public void Score_counter() 
    {
        System.out.println("Score: Player1 - " + Score_Counter(1) + " | Player2 - " + Score_Counter(2));
    }



    public boolean isFull() 
    {
        return (this.counter_piece > 41);
    }

    public int[][] getboard() 
    {
        return this.game_Board;
    }

    public int count_of_pieces() 
    {
        return this.counter_piece;
    }


    public boolean move_valid(int clmn) 
    {

        if (!(clmn >= 0 && clmn <= 7)) 
        {
            return false;
        }
        else
        if (this.game_Board[0][clmn] > 0) 
        {
            return false;
        }
         else 
         {
            return true;
        }
    }
    public boolean make_move(int clmn) 
    {
     
        if (!this.move_valid(clmn)) 
        {
            return false;
        }
         else 
         {

            for (int i = 5; i >= 0; i--) 
            {
                if (this.game_Board[i][clmn] == 0) 
                {
                    if (this.counter_piece % 2 == 0) 
                    {
                        this.game_Board[i][clmn] = 1;
                        this.counter_piece++;

                    }
                    else 
                    {
                        this.game_Board[i][clmn] = 2;
                        this.counter_piece++;
                    }

                    return true;
                }
            }

            System.out.println("Error in make_move()!!");

            return false;
        }
    } 



    public void gameboard_printToFile(String outputFile) 
    {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
            for (int i = 0; i < 6; i++) 
            {
                for (int j = 0; j < 7; j++) 
                {
                    output.write(this.game_Board[i][j] + 48);
                }
                output.write("\r\n");
            }

            output.write(this.turn_keeper_method() + "\r\n");
            output.close();

        } 
        catch (IOException e) 
        {
            System.out.println("\nError writing to the output file.");
            e.printStackTrace();
        }
    }
    
    public void piece_delete(int clmn) 
    {


        for (int i = 0; i < 6; i++) 
        {
            if (this.game_Board[i][clmn] > 0) 
            {
                this.game_Board[i][clmn] = 0;
                this.counter_piece--;
                break;
            }
        }

    }

    public void gameboard_print() 
    {
        System.out.println(" -----------------");

        for (int i = 0; i < 6; i++) 
        {
            System.out.print(" | ");
            for (int j = 0; j < 7; j++) 
            {
                System.out.print(this.game_Board[i][j] + " ");
            }
            System.out.println("| ");
        }
        System.out.println(" -----------------");
    }



    public boolean get_computer_first() {
        return computerfirst;
    }
    public void put_computer_first(boolean value) 
    {
        computerfirst = value;
    }


    private void exit_function(int value) {
        System.out.println("board.java Exit\n");
        System.exit(value);
    }

}