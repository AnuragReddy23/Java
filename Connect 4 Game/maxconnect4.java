/*
Name: Anurag Reddy Pingili
UTA ID: 1001863623
Class: 2222-CSE-5360-001
*/


import java.io.*;

public class maxconnect4
{
    long start = System.currentTimeMillis();
    public static void main(String[] args)
    {
        /* 
		Check whether the correct number of argumets are entered
		*/

        if(args.length != 4){
            //System.out.Println("If wrong arguments are entered");
			System.out.println("Required number of arguments are 4\n" + "<Mode> <Input File> <Next Turn> <Depth> for Interactive mode\n" + "<Mode> <Input_file File> <Output File> <Depth> for One-Move mode\n");
            exit_function(0);
        }

        String mode = args[0].toString();				
        String input_file = args[1].toString();					
        int depth = Integer.parseInt(args[3]);  		

        /*
		Create the game board layout
		*/

        board current = new board( input_file );

        /*
		Calling method to make game AI
		*/ 
        ai calculate = new ai(depth);

        /*
		Variable declaration and initialisation
		*/

        int clmn = 999;				

        if( mode.equalsIgnoreCase( "Interactive" ) )
        {
            Boolean computer_turn = false;
            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) 
			{
                computer_turn = true;
            } 
			else
			 if(args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")) 
			 {
                computer_turn = false;
            }
			 else 
			 {
                System.out.println("Incorrect argumnets entered.\n" + "Enter arguments as: java maxconnet4.java interactive <Computer-Next/Human-Next> <depth> for interactive mode.\n" + " and  java maxconnect4.java one-move <input-file> <output-file> <depth> for one-move mode\n");
                exit_function( 0 );
            }
            try {
                while (current.count_of_pieces() < 42) 
				{
                    current.gameboard_print();
                    current.Score_counter();
                    if (computer_turn){
                        current.put_computer_first(true);
                        current.make_move(calculate.best_move(current));
                        current.gameboard_printToFile("computer.txt");
                        current.gameboard_print();
                        current.Score_counter();
                        computer_turn = false;
						//System.out.println("Computer's turn is done here");
                    } 
					else 
					{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("Make your move:");
                        int col = Integer.parseInt(reader.readLine()) - 1;
                        while (!current.move_valid(col)) 
						{
                            System.out.println("Invalid move.");
                            col = Integer.parseInt(reader.readLine()) - 1;
                        }
                        //System.out.println("while loop for invalid move ends");
                        current.put_computer_first(false);
                        current.make_move(col);
                        current.gameboard_printToFile("human.txt");
                        current.gameboard_print();
                        current.Score_counter();
                        computer_turn = true;
                    }
                }
            }
			 catch (IOException e) 
			{
                e.printStackTrace();
            }
            current.gameboard_print();
            System.out.println("Game Over. Final Score will now be displayed.");
            current.Score_counter();
            return;
            //System.out.println("Control is here");
        }

        else if( !mode.equalsIgnoreCase( "one-move" ) )
        {
            System.out.println( "\n" + mode + " is an invalid mode.\n" );
            return;
        }

        String output = args[2].toString();			
        System.out.print("\n MAXCONNECT-4!\n");
        System.out.print("Current game board:\n");
        current.gameboard_print();
        System.out.println( "Score- Player 1: " + current.Score_Counter(1) + " , Player2 : " + current.Score_Counter(2) + "\n ");

        if(current.count_of_pieces() < 42) {
            int current_player = current.turn_keeper_method();
            clmn = calculate.best_move(current);
            current.make_move(clmn);
            System.out.println("move " + current.count_of_pieces()  + ": Player " + current_player  + ", clmn " + clmn);
            System.out.print("Board after play:\n");
            current.gameboard_print();
            System.out.println( "Score- Player 1: " + current.Score_Counter( 1 ) + ", Player2: " + current.Score_Counter( 2 ) + "\n " );
            current.gameboard_printToFile(output);
        } else {
            System.out.println("\nGameBoard is Full\n");
        }
		//System.out.println("Main function returns after this");
        return;
    } /*	Main() function ends here   */

    private static void exit_function(int value) {
        System.out.println("maxconnect4.java exit\n\n");
        System.exit(value);
    }
}