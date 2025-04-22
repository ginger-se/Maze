package droidPD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import MazeUI.MazeFrame;
import mazePD.Coordinates;
import mazePD.DroidInterface;
import mazePD.Maze;
import mazePD.Maze.Content;
import mazePD.Maze.Direction;


public class Droid implements DroidInterface{

  private String name;
  private LinkedStack<P> path;
  public DroidContent[][][] knownMaze;
  private Coordinates currCords;
  private ArrayList<ActionListener> actionListeners;
  private Content[] adjContent;
 
  private class P{
    private Direction d;
    private Content[] c;
    public P(Direction dir, Content[] co) {
      d = dir;
      c = co;
    }
    
    public String toString() {
      String s = "";
      s += d.toString();
      s += "\n";
      for(Content x : c) {
        s += x.toString() + ", ";
      }
      return s;
      
    }
  }
      
  public class DroidContent{
    private Content mazeContent;
    private boolean visited;
    private boolean inPath;
    
    public DroidContent() {
      mazeContent = Content.NA;
      visited = false;
    }
    public void setInPath(boolean v) {
      inPath = v;
    }
    public boolean isInPath() {
      return inPath;
    }

    public Content getMazeContent() {
      return mazeContent;
    }

    public void setMazeContent(Content mazeContent) {
      this.mazeContent = mazeContent;
    }

    public boolean isVisited() {
      return visited;
    }
    

    public void setVisited(boolean visited) {
      this.visited = visited;
    }
    public String toString() {
      String s = "[";
      if(isInPath()) {
        if(getMazeContent() == Content.PORTAL_DN ) {
          s += "PD";
        }
        else if(getMazeContent() == Content.PORTAL_UP) {
          s += "PU";
        }
        else if(getMazeContent() == Content.END) {
          s += "E ";
          
        }
        else {
          s += "* ";
        }
      }
      else {
        s += "  ";
      }
      s += "]";
      return s;
    }
    
    
  }
  
  
  public Droid(String name){
    this.name = name;
    path = new LinkedStack<P>();
    actionListeners = new ArrayList<ActionListener>();
  }
  
  public void UiTraverseMaze(Maze maze){
    currCords = maze.getCurrentCoordinates(this);
    adjContent = maze.scanAdjLoc(this);
    knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setInPath(true);
    knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setVisited(true);
    knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setMazeContent(maze.scanCurLoc(this));
    if(maze.scanCurLoc(this) == Content.PORTAL_DN) {
      maze.move(this, Direction.DN);
      path.push(new P(Direction.DN, adjContent));
    }
    else if((adjContent[0] == Content.EMPTY || adjContent[0] == Content.END || adjContent[0] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D00, maze)) {
      maze.move(this, Direction.D00);
      path.push(new P(Direction.D00, adjContent));
    }
    else if((adjContent[1] == Content.EMPTY || adjContent[1] == Content.END || adjContent[1] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D90, maze)) {
      maze.move(this, Direction.D90);
      path.push(new P(Direction.D90, adjContent));
    }
    else if((adjContent[2] == Content.EMPTY || adjContent[2] == Content.END || adjContent[2] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D180, maze)) {
      maze.move(this, Direction.D180);
      path.push(new P(Direction.D180, adjContent));
    }
    else if((adjContent[3] == Content.EMPTY || adjContent[3] == Content.END || adjContent[3] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D270, maze)) {
      maze.move(this, Direction.D270);
      path.push(new P(Direction.D270, adjContent));
    }
    else {
      if(path.isEmpty()) {
        System.out.println("Stack Empty");
      }
      else
        
        knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setInPath(false);
        maze.move(this,  reverse(path.pop().d));
    }

    
  }
  
  
  public DroidContent[][][] traverseMaze(Maze maze) {
    maze.enterMaze(this);
    initializeMaze(maze);

    while(maze.scanCurLoc(this) != Content.END) {
      currCords = maze.getCurrentCoordinates(this);
      adjContent = maze.scanAdjLoc(this);
      knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setInPath(true);
      knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setVisited(true);
      knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setMazeContent(maze.scanCurLoc(this));
      if(maze.scanCurLoc(this) == Content.PORTAL_DN) {
        maze.move(this, Direction.DN);
        path.push(new P(Direction.DN, adjContent));
      }
      else if((adjContent[0] == Content.EMPTY || adjContent[0] == Content.END || adjContent[0] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D00, maze)) {
        maze.move(this, Direction.D00);
        path.push(new P(Direction.D00, adjContent));
      }
      else if((adjContent[1] == Content.EMPTY || adjContent[1] == Content.END || adjContent[1] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D90, maze)) {
        maze.move(this, Direction.D90);
        path.push(new P(Direction.D90, adjContent));
      }
      else if((adjContent[2] == Content.EMPTY || adjContent[2] == Content.END || adjContent[2] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D180, maze)) {
        maze.move(this, Direction.D180);
        path.push(new P(Direction.D180, adjContent));
      }
      else if((adjContent[3] == Content.EMPTY || adjContent[3] == Content.END || adjContent[3] == Content.PORTAL_DN) && !adjCoordVisited(currCords, Direction.D270, maze)) {
        maze.move(this, Direction.D270);
        path.push(new P(Direction.D270, adjContent));
      }
      else {
        if(path.isEmpty()) {
          System.out.println("Stack Empty");
        }
        else
          
          knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setInPath(false);
          maze.move(this,  reverse(path.pop().d));
      }

      notifyListeners();
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setVisited(true);
    knownMaze[currCords.getZ()][currCords.getY()][currCords.getX()].setMazeContent(maze.scanCurLoc(this));
    printMaze(maze);
    System.out.println("Start Coords: " + maze.getMazeStartCoord() + " Maze Depth: " + maze.getMazeDepth() + " Maze Dim: " + maze.getMazeDim());
    notifyListeners();
    printPath();
    
    return knownMaze;
    
  }
  private Direction reverse(Direction d) {
    if(d == Direction.D00)
      return Direction.D180;
    else if(d == Direction.D90)
      return Direction.D270;
    else if(d == Direction.D180)
      return Direction.D00;
    else if(d == Direction.D270)
      return Direction.D90;
    else if(d == Direction.UP)
      return Direction.DN;
    else if(d == Direction.DN)
      return Direction.UP;
    return d;
  }
  
  public void initializeMaze(Maze maze) {
    knownMaze = new DroidContent[maze.getMazeDepth()][maze.getMazeDim()][maze.getMazeDim()];
    for(int i = 0; i < maze.getMazeDepth(); i++) {
      for (int j = 0; j < maze.getMazeDim(); j++) {
        for (int k = 0; k < maze.getMazeDim(); k++) {
          knownMaze[i][j][k] = new DroidContent();
        }
      }
    }
  }
  
  private void printMaze(Maze maze) {
    for(int i = 0; i < maze.getMazeDepth(); i++) {
      System.out.println("New Level " + i);
      for (int j = 0; j < maze.getMazeDim(); j++) {
        for (int k = 0; k < maze.getMazeDim(); k++) {
          
          System.out.print(knownMaze[i][j][k].toString() + "   ");
        }
        System.out.println();
      }
    }
    /*
    for(DroidContent[][] i : knownMaze) {
      System.out.println("New Level" += i);
      for(DroidContent[] j : i) {
        for(DroidContent k : j) {
          System.out.print(k.toString());
        }
        System.out.println();
      }
      
    }
    */
  }
  private void printPath() {
    LinkedStack<P> truePath = new LinkedStack<P>();
    while(!path.isEmpty()) {
      truePath.push(path.pop());
    }
    while(!truePath.isEmpty()) {
      System.out.println(truePath.pop());
    }
  }
  
  private boolean adjCoordVisited(Coordinates curr, Direction dir, Maze maze) {
    int depth = curr.getZ();
    int x = curr.getX();
    int y = curr.getY();
    if(dir == Direction.D00 && (y - 1 > -1) && knownMaze[depth][y - 1][x].isVisited()) {
      return true;
    }
    else if(dir == Direction.D90 &&  (x + 1 < maze.getMazeDim())  && knownMaze[depth][y][x + 1].isVisited()) {
      return true;
    }
    else if(dir == Direction.D180 &&  (y + 1 < maze.getMazeDim())  && knownMaze[depth][y + 1][x].isVisited()) {
      return true;
    }
    else if(dir == Direction.D270 &&  (x - 1 > -1) && knownMaze[depth][y][x - 1].isVisited()) {
      return true;
    }
    return false;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LinkedStack<Direction> getPath() {
    return path;
  }

  public void setPath(LinkedStack<Direction> path) {
    this.path = path;
  }
  
  public void addActionListener(ActionListener a) {
    actionListeners.add(a);
  }
  
  public void notifyListeners() {
    for(ActionListener listener : actionListeners) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          listener.actionPerformed(new ActionEvent(this,0,""));
        }
      });
    }
  }
  
}
