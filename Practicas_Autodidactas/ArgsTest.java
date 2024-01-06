import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class ArgsTest {
	private static int a = 0;
	private static int ms = 60;
 
	private static void arraylist() {
		int row = 1;
		double data = 1.23;
		ArrayList<ArrayList<Object>> mainList = new ArrayList<>();
 
		for (int i = 0; i < 4; i++) {
			mainList.add(new ArrayList<>());
		}
 
		mainList.get(0).add((byte) data);
		mainList.get(0).add((short) data);
		mainList.get(1).add('c');
		mainList.get(1).add((int) data);
		mainList.get(2).add((long) data);
		mainList.get(2).add((float) data);
		mainList.get(3).add(data);
		mainList.get(3).add("abc");
 
		for (ArrayList<Object> subList : mainList) {
			System.out.println("Row number " + (row++) + ":");
			for (Object o : subList) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) {
 
				}
				if (o instanceof Byte) {
					System.out.println(o + " is a byte");
				} else if (o instanceof Short) {
					System.out.println(o + " is a 8-bit integer");
				} else if (o instanceof Character) {
					System.out.println(o + " is a character");
				} else if (o instanceof Integer) {
					System.out.println(o + " is a 32-bit integer");
				} else if (o instanceof Long) {
					System.out.println(o + " is a 64-bit integer");
				} else if (o instanceof Float) {
					System.out.println(o + " is a floating point value");
				} else if (o instanceof Double) {
					System.out.println(o + " is a double floating point value");
				} else if (o instanceof String) {
					System.out.println(o + " is a string");
				}
				System.out.println();
			}
		}
 
	}
	private static void cli() {
		a = 0;
		while (true) {
			try {
				System.out.print(a++ + " ");
				Thread.sleep(ms);
			} catch (InterruptedException ie) {
			}
 
			if(a == 101) {
				System.out.println();
				break;
			}
		}
	}
 
	private static void gui() {
		a = 0;
 
		JFrame frame = new JFrame("GUI progress");
 
		JProgressBar label = new JProgressBar();
		label.setValue(a);
		label.setStringPainted(true);
 
 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(label);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
 
		Timer timer = new Timer(ms, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (a == 101) {
					((Timer) e.getSource()).stop();
				} else {
					label.setValue(a++);
				}
			}
		});
		timer.start();
	}
	public static void main(String[] args) {
		if(args.length > 0) {
			for(String arg : args) {
				if(arg.equals("-c")) {
					System.out.println("-c argument received");
					cli();
				} else if (arg.equals("-g")) {
					System.out.println("-g argument received");
					gui();
				} else if (arg.equals("--arraylist")) {
					System.out.println("--arraylist argument received");
					arraylist();
				}
			}
		} else {
			System.out.println("No arguments, doing in CLI and GUI");
			cli();
			gui();
		}
	}
}