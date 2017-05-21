import java.util.Scanner;




class TestClass {
    
static int size = 4;    
static int queens_added = 0;
static boolean[][] chess;
static boolean[][] attack_chess;
static int[] queens_x;
static int[] queens_y;
static int removed_queen_x; 
static int removed_queen_y;
static int col = 0, row = 0;
private static boolean success = false;
static int starting_x = 0;
static int starting_y = 0;
    
    
    
    public static void main(String args[] ) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
		
		
		size = scanner.nextInt();
	    do
        {
            //initialize chess and attacking chess
            init();
            //start a new position by shifting the queen to a new column
            if(starting_x < size && starting_y < size)
            {
                if(verify_empty(starting_x,starting_y))
                {
                    if(verify_not_attacked(starting_x,starting_y))
                    {
                        addQueen_recursive(starting_x,starting_y, row, col, queens_added, size);
//                          
                    }
                }
            }
            //increment the starting position for the first queen
            //by shifting it to the right
            starting_y++;
            if(starting_y >= size)
            {
                starting_y = 0;
                if(row < size)
                {
                    starting_x = row;
                }
                else
                {
                    starting_x = 0;
                }
                
            }
            
            
         } while(!success);
        
    }

    //verify that the chess position is empty
    private static boolean verify_empty(int i, int j) {
        if(chess[i][j] == false)
        {
            return true;
        }
        return false;
    }
    //print informations (the number of queens added)
    private static void print_info()
    {
        System.out.println("numbers of queens added: " + queens_added);
    }
    //verify that the chess position is not attacked by a queen
    private static boolean verify_not_attacked(int i, int j) {
//        reinit_attack_chess();
        if(attack_chess[i][j] == false)
        {
            return true;
        }
        return false;
    }
    //verify that there is a safe position in the chess borad;
    //a position is considered safe if it is not attacked by another
    //queen
    private static boolean verify_fully_attacked()
    {
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(attack_chess[i][j] == true)
                {
                    continue;
                }
                else
                {
                    //System.out.println("verifying fully attacked is " + false);
                    return false;
                }
            }
        }
        //System.out.println("verifying fully attacked is " + true);
        return true;
    }
    

    //add a queen to the chess board
    private static void addQueen(int i, int j) {
        queens_x[queens_added] = i;
        queens_y[queens_added] = j;
        chess[i][j] = true;
        
//        attack_chess_backup[queens_added] = attack_chess;
//        chess_backup[queens_added] = chess;
        queens_added++;
        reinit_attack_chess();
        //print_info();
    }

    
    private static boolean addQueen_recursive(int startx, int starty, int i1, int j1, int queens_added1, int size1)
    {
        addQueen(startx,starty);
        //iterate through the chess and try to add a queen in each free position
            for( row=0; row < size; row++)
            {
                for(col=0; col < size; col++)
                {
                    //test if the mission of placing the N queens is completed 
                    //when there is no free position for a new queen
                    //ootherwise backtrack to last queen and shift a new queen to the right
                    if(queens_added != size && verify_fully_attacked())
                    {
                        if(queens_added >= 1)
                        {
                            remove_last_queen_from_chess();
                            backtrack();
                            reinit_attack_chess();
                        }          
                    }
                    //if there is an empty and safe position for the queen,
                    //add the queen to the chess
                    if(row < size && col < size && queens_added != size)
                    {
                        if(verify_empty(row,col))
                        {
                            if(verify_not_attacked(row,col))
                            {
                                addQueen(row,col);
                            }
                        }
                    }
                }
            }
            //if all the queens are added,
            //quit
            if(queens_added == size || startx >= size)
            {
                if(queens_added == size)
                {
                    success=true;    
                }
                
                if(success == true)
                {
                    System.out.println("YES");    
                }
                else
                {
                    System.out.println("NO");    
                }
                
                print_queens_on_chess();
                //print_attacking_queens_on_chess();
                return true;
            }
            return false;
            
            
    }
    //print the positions being attacked by all queens on the chess board
    private static void print_attacking_queens_on_chess()
    {
        //System.out.println("chess with attacking queens:");
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(attack_chess[i][j])
                {
                    System.out.print("1 ");
                }
                else
                {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
    //print the queens on the chess board
    private static void print_queens_on_chess() {
        //System.out.println("chess with queens:");
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(chess[i][j])
                {
                    System.out.print("1 ");
                }
                else
                {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    //initialize the chess and the other attacked positions chess
    private static void init() 
    {
        queens_y = new int[size];
        queens_x = new int[size];
        for(int i = 0; i < size; i++)
        {
            queens_x[i] = 0;
            queens_y[i] = 0;
            
        }
        queens_added = 0;
        
        chess = new boolean[size][size];
        attack_chess = new boolean[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                chess[i][j] = attack_chess[i][j] = false;
            }
        }

    }
    
    //remove the last queen added from the chess board
    //and reinitialize the attacking chess board
    static boolean remove_last_queen_from_chess()
    {
        if(queens_added-1 >= 0)
        {
            int position = queens_added-1;
            chess[queens_x[position]][queens_y[position]] = false;
            removed_queen_x = queens_x[position];
            removed_queen_y = queens_y[position];

            queens_added--;
            reinit_attack_chess();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //if we foind that a path does not lead to success,
    //chose another path by building on the last good path
    //ex: if by adding 2nd queen, we are blocked, we remove the 2nd queen
    //and get its position,
    //then we try to put another 2nd queen in another position
    //
    static boolean[][] backtrack()
    {
        int x,y;
        
        if(queens_added-1 >= 0)
        {
            remove_last_queen_from_chess();
            
            row = removed_queen_x;
            col = removed_queen_y+1;
        }
        else
        {
            starting_x = removed_queen_x;
            starting_y = removed_queen_y+1;
        }
        
    
        return chess;
    }

    private static void reinit_attack_chess() {
        attack_chess = new boolean[size][size];
        
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                attack_chess[i][j] = false;
            }
        }
        
        
        
        for(int i = 0; i < queens_added; i++)
        {
            int x,y;
            x = queens_x[i];
            y = queens_y[i];
            
            attack_chess[x][y] = true;
            //fill rows and columns that match the queen position
            fill_attack_chess_diagonals(x, y);
            for(int a = 0; a < size; a++)
            {
                for(int b = 0; b < size; b++)
                {
                    if(a == x)
                    {
                        attack_chess[a][b] = true;
                    }
                    if(b == y)
                    {
                        attack_chess[a][b] = true;
                    }
                }
            }
        }
        
//        attack_chess_backup =  new boolean[size][size][size];
//        for(int i =0; i < size;i++)
//        {
//            attack_chess_backup[i] = attack_chess;
//        }
//        
//        chess_backup =  new boolean[size][size][size];
//        for(int i =0; i < size;i++)
//        {
//            chess_backup[i] = chess;
//        }
    }

    private static void fill_attack_chess_diagonals(int x, int y) 
    {
        int i,j;
        //south - east
        i = x;
        j = y;
        while(i < size && j < size)
        {
            attack_chess[i][j] = true;
            i++; j++;
        }
        
        //south - west
        i = x;
        j = y;
        while(i < size && j >= 0)
        {
            attack_chess[i][j] = true;
            i++; j--;
        }
        
        //north - east
        i = x;
        j = y;
        while(i >= 0 && j < size)
        {
            attack_chess[i][j] = true;
            i--; j++;
        }
        
        //south - west
        i = x;
        j = y;
        while(i >= 0 && j >= 0)
        {
            attack_chess[i][j] = true;
            i--; j--;
        }
    }
    
}
