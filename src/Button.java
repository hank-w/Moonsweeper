import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author hank.wu
 */

public class Button extends JButton {
    int value;
    public int[] location;
    boolean exposed;
    boolean flagged;
    boolean white;
    
    public Button(int value){
        this.location = location;
        this.value = value;
        this.exposed = false;
        this.setPreferredSize(new Dimension(50,50));
        
    }
    public int getvalue(){ 
        return value;
    }
    
    public void setNumber(int number) {
        value = number;
    }
   
    public int[] getPosition(){
        return location;
    }
    
    public void setPosition(int row, int col){
        this.location = new int[2];
        this.location[0] = row;
        this.location[1] = col;
        
    }
    
    public boolean isExposed(){
        return exposed;
        
    }
    
    public void expose(){
        setText("" + value);
        if(value == 0){
            setText("");
        }
        this.exposed = true;
       
    }
    
    
    /*private void endGame(int i, int e) {
    for (int j=i-1;j<i+2;j++){              
       for (int h=e-1;h<e+2;h++){
           if ( (j>-1) && (j<Minesweeper.sweep.grid.length) && (h>-1) && (h<Minesweeper.sweep.grid[0].length) && (Minesweeper.sweep.grid[j][h] != -1)){
              if (Minesweeper.sweep.grid[j][h] == -1){ 
                Minesweeper.sweep.grid[j][h] = -1;
                endGame(j, h);
              } 
              else if (Minesweeper.sweep.grid[j][h] != -1)
                 Minesweeper.sweep.grid[j][h] = Integer.toString(Minesweeper.sweep.grid[j][h]).charAt(0);     
           }
    }
    }
}
*/
}
