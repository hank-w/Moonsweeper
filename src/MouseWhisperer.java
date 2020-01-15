import java.awt.event.*;
import java.lang.*;
import javax.swing.SwingUtilities;
/**
 * @author x7
 */
public class MouseWhisperer implements MouseListener{
   // MouseListener mouselistener = new MouseListener();
    Minesweeper game;
    
    public MouseWhisperer(Minesweeper game){
        this.game = game;
    }
    public MouseWhisperer(){
        
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
       if(game.tilesExposed == 0){
        game.timer.start();
       }
       
       
       if(game.gameOver == false){
       int value;
       Button click; 
       click = (Button)me.getSource();
       value = ((Button)click).getvalue();
       
       if(SwingUtilities.isMiddleMouseButton(me) && click.flagged == false || SwingUtilities.isLeftMouseButton(me) && SwingUtilities.isRightMouseButton(me) && !click.flagged){
           
           int row, col;
           int count = 0;
           row = click.location[0];
           col = click.location[1];
           for(int j = col - 1; j < col + 2; j++){
                for(int k = row - 1; k < row + 2; k++){                            
                    if (!(j == -1 || k == -1 || j == game.cols  || k == game.rows)){  
                        if(game.buttons[k][j].flagged){
                            count++;
                        }
                        
                    }
                      }     
                
            }
                        if(count == value){
                            for(int i = col - 1; i < col + 2; i++){
                                for(int h = row - 1; h < row + 2; h++){                            
                                    if (!(i == -1 || h == -1 || i == game.cols  || h == game.rows)){
                                        if(game.buttons[h][i].value != -1 && !game.buttons[h][i].flagged){
                                        game.buttons[h][i].setIcon(null);
                                        game.buttons[h][i].expose();
                                        game.tilesExposed++;
                                        }
                                        
                                        if(game.buttons[h][i].flagged){
                                            //game.buttons[h][i].setIcon(game.FLAG);
                                        }
                        
                                        else if(game.buttons[h][i].value == -1 && !game.buttons[h][i].flagged){
                                        game.exposeAll(game.buttons[h][i]);
                                        }
                                        /*
                                        click.setIcon(null);
           click.setIcon(game.MINE);
           System.out.println("GG EZ");
           for(int i = 0; i < game.grid.length; i++){
            for(int j = 0; j < game.grid[0].length; j++){
                if(game.buttons[i][j].flagged){
                    game.buttons[i][j].setIcon(game.FLAG2);
                }
                if(game.buttons[i][j].getvalue() == -1 && !game.buttons[i][j].flagged){
                    game.buttons[i][j].setIcon(game.MINE);
                }                
                else if(!game.buttons[i][j].flagged){
                game.buttons[i][j].expose();
                game.buttons[i][j].setIcon(null);
                }
            }
           }
           game.gameOver = true;
                                        */
                                    
                                    else if(game.buttons[h][i].value == 0){
                                        game.ReCurse(h, i);
                                }
                            }
                        }   
                    }
                }           
            }
       
 
       if(me.isMetaDown() && !click.flagged && !click.exposed){
           click.setIcon(game.FLAG);
           click.flagged = true;
           game.minesLeft--;
           game.unflagged.setText("Unflagged Mines: " + game.minesLeft);
       }
       
       else if(me.isMetaDown() && click.flagged && !click.exposed){
            game.minesLeft++;
            game.unflagged.setText("Unflagged Mines: " + game.minesLeft);
            if(!click.white){
                click.setIcon(game.TILE1);
            }
            else{
                click.setIcon(game.TILE);
            }
           click.flagged = false;    
       }
       if(!me.isMetaDown() && click.flagged == false){
       click.setIcon(null);
       if(value != -1 && click.exposed == false){
            click.expose();
            game.tilesExposed++;
       }
       
       if (value == -1 && game.tilesExposed <= 6){
           game.SpawnProtection(click);
           
       }
       else if (value == -1){
           game.exposeAll(click);
       }
       
       
       
       if(value == 0){
           game.ReCurse(click.location[0], click.location[1]);
           
       }    
       
       
        }
    }
       if(game.tilesExposed == ((game.cols * game.rows) - game.mineNum)){
           game.timer.stop();
           game.gameOver = true;
           System.out.println("YOU WIN :D");
           for(int i = 0; i < game.grid.length; i++){
            for(int j = 0; j < game.grid[0].length; j++){
                if(game.buttons[i][j].getvalue() == -1){
                    game.buttons[i][j].setIcon(game.FLAG);
                        }
                else if(!game.buttons[i][j].exposed){
                    //game.buttons[i][j].expose();
                }
                    }
                } 
            }
       }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
    
    }
    }
    
