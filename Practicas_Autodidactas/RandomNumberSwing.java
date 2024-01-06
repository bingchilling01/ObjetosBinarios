import java.util.Random;
import java.awt.FlowLayout;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;  
import java.awt.event.*;  
  
public class RandomNumberSwing {
 
    public static void main(String args[]) {
 
        Random random = new Random();
        JFrame frame = new JFrame("Number generator");
        
        JPanel panelRange = new JPanel();
        panelRange.setLayout(new GridLayout(3, 2));
        
        JTextField fMin = new JTextField();
        JTextField fMax = new JTextField();
        JLabel numb = new JLabel();
        JButton generate = new JButton("Generate number");
        generate.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent ae){
                try {
                    float min = Float.parseFloat(fMin.getText()+"");
                    float max = Float.parseFloat(fMax.getText()+"");
                    float generatedNumber = random.nextFloat() * (max - min) + min;
                    numb.setText(String.format("%.2f", generatedNumber));
                } catch (Exception e) {
                    numb.setText("Error");
                }
                
            }  
        }); 
        
        panelRange.add(new JLabel("Range 1: "));
        panelRange.add(fMin);
        panelRange.add(new JLabel("Range 2: "));
        panelRange.add(fMax);
        panelRange.add(generate);
        panelRange.add(numb);
        
        JPanel panel = new JPanel();  
        panel.setLayout(new BorderLayout());  
        JLabel label = new JLabel("Example");  
        JButton button = new JButton();  
        button.setText("Close");  
        button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                System.exit(0);
            }  
        }); 
        panel.add(label);  
        panel.add(panelRange, BorderLayout.CENTER);
        panel.add(button, BorderLayout.NORTH);
        panel.add(numb, BorderLayout.SOUTH);
        frame.add(panel);  
        frame.pack();  
        frame.setLocationRelativeTo(null);  
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true);  
    }  
}  