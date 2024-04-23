package gameResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu {
    public JFrame manageExercises;
    JPanel panel;
    final int sw, sh;
    int currentLine = 0;
    int rowDistacne = 30;
    int lineTickness =4;
    private Timer timer;
    private int elapsedTimeInSeconds;
    Color checkColor;

    //kaj
    private boolean term;
    int boxDistance, currentSelect=0;
    int flag;

    CircleInsideSquare[][] buttons;
    CircleInsideSquare[] selectButton;
    Integer[][] iconSelect;
    boolean[][] termFlag;
    Integer[][] remainingPown;
    Font smallLabelFont,mediumLabelFont,largeLabelFont;


    public MainMenu(int screenWidth, int screenHeight) {
        this.sh = screenHeight;
        this.sw = screenWidth;
        init();
    }

    private void init() {
        manageExercises = new JFrame("Main Menu");
        panel = new JPanel(null); // Null layout

        stage();




        manageExercises.add(this.panel);
        manageExercises.setSize(sw, sh); // Set frame size
        manageExercises.setLocationRelativeTo(null); // Center the frame on the screen
        manageExercises.setVisible(true);

        manageExercises.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Dispose of the frame
                manageExercises.dispose();
            }
        });
    }

    void stage(){
        currentSelect=0;
        checkColor = null;
        flag=0;
        buttons = new CircleInsideSquare[3][3];
        iconSelect = new Integer[3][3]; ///1,2,3
        termFlag = new boolean[3][3];

        remainingPown = new Integer[2][3];
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                remainingPown[i][j]=3;
            }
        }

        selectButton = new CircleInsideSquare[3];




        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 14);
        smallLabelFont = labelFont.deriveFont(labelFont.getSize() + 5.0f);
        mediumLabelFont = labelFont.deriveFont(labelFont.getSize() + 10.0f);
        largeLabelFont = labelFont.deriveFont(labelFont.getSize() + 20.0f);

        int newWidth = (5 * sw) / 8;
        int requirementDistance = sh-100;
        int d = (newWidth*3)/4;
        if(d>requirementDistance)d=requirementDistance;

        System.out.println("d=>"+d);

        int gridEndx = newWidth-10;
        int gridStartx =(gridEndx-d);
        int gridStarty =50;
        int gridEndy = 50+d;


        //Select Button
        int startSelectx = 5, endSelectx=gridStartx-20;
        int selectDistance = endSelectx-startSelectx;
        int startSelecty =gridStarty+ ((gridEndy-gridStarty)-(selectDistance*3))/2;
        for(int i = 0;i<3;i++){
            int y = startSelecty + i * selectDistance;
            selectButton[i] = new CircleInsideSquare(Color.GRAY, 50+i*25,Color.LIGHT_GRAY,selectDistance);
            selectButton[i].setBounds(startSelectx, y, selectDistance, selectDistance);

            int ttt = 0;

            if(term){
                ttt=1;
                selectButton[currentSelect].setCircleColor(Color.GREEN);
            }
            else{
                selectButton[currentSelect].setCircleColor(Color.RED);
            }
            selectButton[i].setText(String.valueOf(remainingPown[ttt][i]));
            selectButton[i].setFont(mediumLabelFont);

            final int ii = i;
            final int tt=ttt;
//            final int buttonSize = selectButton[i].getWidth();
            selectButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(remainingPown[tt][ii]>0)currentSelect = ii;
                    else{
                        JOptionPane.showMessageDialog(null,  "All are used!");
                        for(int id = 0;id<3;id++) {
                            if (remainingPown[tt][id] > 0) {
                                currentSelect = id;
                                break;
                            }
                        }
                    }


                    for(int kk=0;kk<3;kk++){
                        if(kk==currentSelect){
                            if(term){
                                selectButton[kk].setCircleColor(Color.GREEN);
                            }
                            else{
                                selectButton[kk].setCircleColor(Color.RED);
                            }
                        }
                        else{
                            selectButton[kk].setCircleColor(Color.GRAY);
                        }
                        selectButton[kk].setText(String.valueOf(remainingPown[tt][kk]));
                        selectButton[kk].setFont(mediumLabelFont);
                    }
                }
            });
            panel.add(selectButton[i]);
            panel.repaint();
        }


//





        boxDistance = d/3;
        // Load the image

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int x = gridStartx + i * boxDistance;
                int y = gridStarty + j * boxDistance;

                buttons[i][j] = new CircleInsideSquare(Color.gray, 50+currentSelect*25, Color.LIGHT_GRAY,boxDistance);
                buttons[i][j].setBounds(x, y, boxDistance, boxDistance);
                final int ii = i;
                final int jj = j;
                final int buttonSize = boxDistance;

                final String currentTermDisplay;
                if(term){
                    currentTermDisplay = "First Person's Term";
                }
                else{
                    currentTermDisplay = "Second Person's Term";
                }
                buttons[i][j].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Clicked " + ii + "," + jj + ". Term: " + term + "  current:" + currentSelect);


                        if((buttons[ii][jj].getCircleColor()!=Color.green && buttons[ii][jj].getCircleColor()!=Color.RED)||(termFlag[ii][jj]!=term && iconSelect[ii][jj]<currentSelect)){
                            if(buttons[ii][jj].getCircleColor()==Color.GRAY) flag++;
                            System.out.println("Flag: "+flag);
                            //buttons[ii][jj].setText(Integer.toString(currentSelect));
                            iconSelect[ii][jj]=currentSelect;
                            termFlag[ii][jj]=term;

                            if (term) {
                                //buttons[ii][jj].setIcon(scaledIcon);
                                checkColor = Color.green;
                                buttons[ii][jj].setCircleColor(Color.green);
                                buttons[ii][jj].setRedius(50+currentSelect*25);
                            } else {
                                checkColor = Color.RED;
                                buttons[ii][jj].setCircleColor(Color.RED);
                                buttons[ii][jj].setRedius(50+currentSelect*25);
                            }
                            int indx=0;
                            if(!term)indx=1;
                            remainingPown[indx][currentSelect]--;

                            System.out.println("remaining Pown:"+remainingPown[indx][currentSelect]);

                            term = !term;      // Toggle the value of term
                            indx=0;
                            if(!term)indx=1;

                            if(remainingPown[indx][currentSelect]<=0){
                                for(int id = 0;id<3;id++) {
                                    if (remainingPown[indx][id] > 0) {
                                        currentSelect = id;
                                        break;
                                    }
                                }
                            }




                            for(int kk=0;kk<3;kk++){
                                if(kk==currentSelect){
                                    if(term)selectButton[kk].setCircleColor(Color.GREEN);
                                    else selectButton[kk].setCircleColor(Color.RED);
                                }
                                else selectButton[kk].setCircleColor(Color.GRAY);

                                selectButton[kk].setText(String.valueOf(remainingPown[indx][kk]));
                                selectButton[kk].setFont(mediumLabelFont);
                            }


                            Component[] components = panel.getComponents();
                            for (Component component : components) {
                                if (component instanceof JLabel) {
                                    String text = ((JLabel) component).getText();
                                    if (text != null && text.contains("Term")) {
                                        panel.remove(component);
                                    }
                                }
                            }
                            JLabel termDisplay2;
                            if (term) {
                                termDisplay2 = new JLabel("First Person's Term");
                            } else {
                                termDisplay2 = new JLabel("Second Person's Term");
                            }

                            termDisplay2.setFont(mediumLabelFont);
                            termDisplay2.setHorizontalAlignment(SwingConstants.CENTER);
                            termDisplay2.setBounds(0, 10, newWidth, 30);
                            panel.add(termDisplay2);
                            panel.repaint();

                        }

                        if(flag==9){
                            System.out.println("khela shes!");
                        }

                        if(flag>2 && buttons[ii][jj]!=null && check(ii,jj)){
                            System.out.println(checkColor+ " Winner!!!");
                            if (checkColor.getRed() == 255 && checkColor.getGreen() == 0 && checkColor.getBlue() == 0) {
                                JOptionPane.showMessageDialog(null,   "Red Winner!!!");
                            }
                            else if (checkColor.getRed() == 0 && checkColor.getGreen() == 255 && checkColor.getBlue() == 0) {
                                JOptionPane.showMessageDialog(null,   "Green Winner!!!");
                            }
                            panel.removeAll();
                            stage();
                        }
                        else{
                            if(flag==9){
                                System.out.println("Draw!!!");
                                JOptionPane.showMessageDialog(null,  "Draw!!!");
                                panel.removeAll();
                                stage();
                            }

                        }

                    }
                });
                panel.add(buttons[i][j]);


            }
        }
        // Create and add the vertical line
        VerticalLine verticalLine = new VerticalLine(newWidth, sh, lineTickness); // 20 pixels thickness
        verticalLine.setBounds(newWidth, 0, lineTickness, sh); // Width set to the desired thickness
        panel.add(verticalLine);



        //================== Create a panel for the right side with background color ====================
        int rightSideWidth = sw - newWidth - lineTickness;
        int middlePointY = sh / 2;
        // Create and add Button 1
        JButton button1 = new JButton("Restart");
        button1.setBounds(newWidth + lineTickness + 20, middlePointY - 60, 200, 50); // Adjust position as needed
        panel.add(button1);
        // Create and add Button 2 (visible)
        JButton button2 = new JButton("Exit");
        button2.setBounds(newWidth + lineTickness + 20, middlePointY + 10, 200, 50); // Adjust position as needed
        panel.add(button2);
        System.out.println(sw + "=>" + newWidth);



        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iterate over the buttons array and reset their background colors
                panel.removeAll();
                stage();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iterate over the buttons array and reset their background colors
                manageExercises.dispose();
            }
        });


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.gray); // Change color as needed
        rightPanel.setBounds(newWidth + lineTickness, 0, sw, sh);
        panel.add(rightPanel);
        //===============================================================================================



        //=========================Nodes create====================================
        int positionX1 = (newWidth)/4+30;
        int positionX2 = (newWidth-30) - positionX1;


        JLabel termDisplay;
        if(term){
            termDisplay = new JLabel("First Person's Term");
        }
        else{
            termDisplay = new JLabel("Second Person's Term");
        }






        termDisplay.setFont(mediumLabelFont);
        termDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        termDisplay.setBounds(0, 10, newWidth, 30);
        panel.add(termDisplay);
        panel.repaint();
    }

    boolean check(int r, int c){
        System.out.println("=========================>"+r+ ","+c+"  =>checking! =>"+checkColor);
        int points=0;
        for(int i=0;i<3;i++){
            if(buttons[r][i]!=null && buttons[r][i].getCircleColor()==checkColor)points++;
        }
        System.out.println("Columnchecked!=>"+points);


        if(points==3)return true;
        else points=0;

        for(int i=0;i<3;i++){
            if(buttons[i][c]!=null && buttons[i][c].getCircleColor()==checkColor)points++;
        }

        System.out.println("Rowchecked!=>"+points);
        if(points==3)return true;
        else points=0;

        if((r+c)%2 ==0){
            for (int i = 0; i < 3; i++) {
                if (buttons[i][i]!=null && buttons[i][i].getCircleColor() == checkColor) points++;
                else break;
            }
            if (points == 3) return true;
            else points = 0;

            if (buttons[0][2]!=null && buttons[0][2].getCircleColor() == checkColor) points++;
            if (buttons[1][1]!=null && buttons[1][1].getCircleColor() == checkColor) points++;
            if (buttons[2][0]!=null && buttons[2][0].getCircleColor() == checkColor) points++;

            if (points == 3) return true;
        }
        return false;
    }


























    static class HorizontalLine extends JComponent {
        private final int y;
        private final int panelWidth;
        private final int thickness;

        public HorizontalLine(int y, int panelWidth, int thickness) {
            this.y = y;
            this.panelWidth = panelWidth;
            this.thickness = thickness;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Set stroke to create a thicker line
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

            // Draw a single line
            g2d.drawLine(0, thickness / 2, panelWidth, thickness / 2);

            // Reset the stroke to its original value
            g2d.setStroke(oldStroke);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(panelWidth, thickness); // Width of panel, height of the desired thickness
        }
    }

    static class VerticalLine extends JComponent {
        private final int x;
        private final int panelHeight;
        private final int thickness;

        public VerticalLine(int x, int panelHeight, int thickness) {
            this.x = x;
            this.panelHeight = panelHeight;
            this.thickness = thickness;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Set stroke to create a thicker line
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

            // Draw a single line
            g2d.drawLine(thickness / 2, 0, thickness / 2, panelHeight);

            // Reset the stroke to its original value
            g2d.setStroke(oldStroke);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(thickness, panelHeight); // Width of the desired thickness, height of panel
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu(1000, 600); // Example width and height
        });
    }
}
