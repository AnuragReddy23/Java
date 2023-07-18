/*
Name: Anurag Reddy Pingili
UTA ID: 1001863623
Class: 2222-CSE-5360-001
*/


public class ai
{
    private int depth_level;
    public ai(){
        this.depth_level = 5;
    }
    public ai(int depth_level) 
    {
        this.depth_level = depth_level;
    }

    public int best_move(board current) {
        int current_choice = 0;
        if (current.turn_keeper_method() == 1) 
        {
            int v = 1000000000;
            int i=0;
            while(i<6) 
            {
                if (current.move_valid(i)) 
                {
                    board turn_next = new board(current.getboard());
                    turn_next.make_move(i);
                    int value = Maximum(turn_next, depth_level, -1000000000, 1000000000);
                    if (v > value) {
                        current_choice = i;
                        v = value;
                    }
                }
                i++;
            }
            //System.out.println("best_method for computer move method ends here");
        } 
        else 
        {
            int v = -1000000000;
            int i=0;
            while(i<6) 
            {
                if (current.move_valid(i)) 
                {
                    board turn_next = new board(current.getboard());
                    turn_next.make_move(i);
                    int value = Minimum(turn_next, depth_level, -1000000000, 1000000000);
                    if (v < value) {
                        current_choice = i;
                        v = value;
                    }
                }
                i++;
            }
            //System.out.println("best_method for computer move method ends here");
        }
        return current_choice;
    }

    public int Minimum(board current, int depth_level, int a, int b) 
    {

        if (current.count_of_pieces() < 42 && depth_level > 0) 
        {
            int v = 1000000000;
            int i=0;
            while(i<6) 
            {
                if (current.move_valid(i)) 
                {
                    board turn_next = new board(current.getboard());
                    turn_next.make_move(i);
                    int value = Maximum(turn_next, depth_level - 1, a, b);
                    if (v > value) 
                    {
                        v = value;
                    }
                    if (v <= a) 
                    {
                        return v;
                    }
                    if (b > v) 
                    {
                        b = v;
                    }
                }
                i++;
            }
            //System.out.println("Minimum method ends here");
            return v;
        } else 
        {
            return evaluation_function(current);
        }
    }


    public int Maximum(board current, int depth_level, int a, int b) 
    {
        if (current.count_of_pieces() < 42 && depth_level > 0) 
        {
            int v = -1000000000;
            int i=0;
            while(i<6) 
            {
                if (current.move_valid(i)) 
                {
                    board turn_next = new board(current.getboard());
                    turn_next.make_move(i);
                    int value = Minimum(turn_next, depth_level - 1, a, b);
                    if (v < value) 
                    {
                        v = value;
                    }
                    if (v >= b) 
                    {
                        return v;
                    }
                    if (a < v) 
                    {
                        a = v;
                    }
                }
                i++;
            }
            //System.out.println("Maximum method ends here");
            return v;
        } 
        else 
        {
           return evaluation_function(current);
        }
    }


    private int evaluation_function(board current)
    {
        int result = 0;
        if (current.isFull()) 
        {
            if((current.Score_Counter(2) - current.Score_Counter(1)) > 0)
            {
                return 1000000000;
            }
             else 
             if((current.Score_Counter(2) - current.Score_Counter(1)) == 0)
             {
                return 0;
            }
             else
              {
                return -1000000000;
            }
        } 
        else 
        {
            result = (current.Score_Counter(1) * 1000) + (current.counting_threes(1) * 50) + (current.counting_twos(1) * 10)  - (current.Score_Counter(2) * 1000) - (current.counting_threes(2) * 50) - (current.counting_twos(2) * 10);

        }
        return (-result);
    }
}