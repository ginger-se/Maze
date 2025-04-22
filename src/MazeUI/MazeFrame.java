package MazeUI;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import droidPD.Droid;
import droidPD.Droid.DroidContent;
import mazePD.Coordinates;
import mazePD.Maze;
import mazePD.Maze.Content;
import mazePD.Maze.MazeMode;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MazeFrame extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;

  /**
   * Launch the application.
   */
  public static void main() {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MazeFrame frame = new MazeFrame();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
        

      }
    });
  }

  /**
   * Create the frame.
   */
  JButton b = new JButton();
  private JTextField textField;
  private JTextField textField_1;
  public MazeFrame() {
   
    
    
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 996, 747);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    
   
    
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    JPanel panel = new JPanel();
    panel.setBounds(200, 30, 752, 647);
    contentPane.add(panel);
    panel.setLayout(new GridLayout(10,10));
    
    for(int i = 0; i < 10; i++) {
      for(int j = 0; j < 10; j++) {
        
        b = new JButton();
        panel.add(b);
        b.setBackground(Color.gray);
      }
    }
    
    JLabel lblNewLabel_2 = new JLabel("Current Level: 1");
    lblNewLabel_2.setBounds(518, 5, 111, 14);
    contentPane.add(lblNewLabel_2);
    
    JLabel lblNewLabel = new JLabel("Size");
    lblNewLabel.setBounds(10, 30, 49, 14);
    contentPane.add(lblNewLabel);
    
    JLabel lblNewLabel_1 = new JLabel("Levels");
    lblNewLabel_1.setBounds(10, 67, 49, 14);
    contentPane.add(lblNewLabel_1);
    
    textField = new JTextField();
    textField.setBounds(54, 30, 96, 20);
    contentPane.add(textField);
    textField.setColumns(10);
    
    textField_1 = new JTextField();
    textField_1.setBounds(54, 64, 96, 20);
    contentPane.add(textField_1);
    textField_1.setColumns(10);
    
    JButton btnNewButton = new JButton("Solve");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Maze maze = new Maze(Integer.parseInt(textField.getText()), Integer.parseInt(textField_1.getText()), MazeMode.NORMAL);
        panel.removeAll();
        panel.setLayout(new GridLayout(maze.getMazeDim(), maze.getMazeDim()));
        for(int i = 0; i < maze.getMazeDim(); i++) {
          for(int j = 0; j < maze.getMazeDim(); j++) {
            
            b = new JButton();
            panel.add(b);
            b.setBackground(Color.gray);
          }
        }
        panel.revalidate();
        panel.repaint();
        System.out.println(maze.getMazeDim());
        Droid droid = new Droid("Justin");
        droid.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
            Coordinates cords = maze.getCurrentCoordinates(droid);
            panel.removeAll();
            lblNewLabel_2.setText("Current Level: " + (cords.getZ() + 1));
            for (int j = 0; j < maze.getMazeDim(); j++) {
              for (int k = 0; k < maze.getMazeDim(); k++) {
                b = new JButton();
                panel.add(b);
                
                System.out.println(new Coordinates(j,k,cords.getZ()) + "  " +  j + "   " + k  + "    "  + cords);
                if(droid.knownMaze[cords.getZ()][j][k].getMazeContent() == Content.END) {
                  b.setBackground(Color.green);
                }
                else if(cords.equals(new Coordinates(k,j,cords.getZ()))) {
                  System.out.println("Here");
                  b.setBackground(Color.red);
                }
                else if(droid.knownMaze[cords.getZ()][j][k].getMazeContent() == Content.EMPTY || droid.knownMaze[cords.getZ()][j][k].isVisited() ) {
                  
                  b.setBackground(Color.white);
                  
                }
                else if (droid.knownMaze[cords.getZ()][j][k].getMazeContent() == Content.BLOCK) {
                  b.setBackground(Color.black);
                }
                else {
                  b.setBackground(Color.gray);
                }
              }
              
            }
            panel.revalidate();
            panel.repaint();
            
          }

          
          
        });
        new Thread(new Runnable() {
          public void run() {
              droid.traverseMaze(maze);
           }
          }).start();


      }
    });
    btnNewButton.setBounds(10, 119, 89, 23);
    contentPane.add(btnNewButton);
    
    
    
    
    
    
    
    
  }
  
}
