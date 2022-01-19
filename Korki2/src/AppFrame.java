package kolos;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public JButton up = new JButton("góra");
	public JButton down = new JButton("dó³");
	public JButton right = new JButton("prawo");
	public JButton left = new JButton("lewo");;
    private JPanel panel2 = new JPanel();
	public AnimationPanel animArea;
    
public AppFrame() {

	setTitle("Poruszaj¹cy siê kwadrat");	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	setSize(700, 700);
	
	 //layout
    this.setLayout(new BorderLayout());
  
    //dodajemy rzeczy
    animArea = new AnimationPanel();
    this.add(animArea);

    this.add(this.panel2, BorderLayout.SOUTH);

	
    //panel 2
    this.panel2.setLayout(new GridLayout(1,3));
    this.panel2.add(up);
    this.panel2.add(down);
    this.panel2.add(right);
    this.panel2.add(left);
    
    up.addActionListener(gora);
    down.addActionListener(dol);
    right.addActionListener(prawo);
    left.addActionListener(lewo);

    this.setVisible(true); //To musi byæ po dodaniu wszystkiego
    animArea.initGUI(); //To musi byæ po setVisible
}

private ActionListener gora = (e) -> {
	animArea.switchAnimationState1();
};

private ActionListener dol = (e) -> {
	 animArea.switchAnimationState2();
};
private ActionListener prawo = (e) -> {
	 animArea.switchAnimationState3();
};
private ActionListener lewo = (e) -> {
	 animArea.switchAnimationState4();
};
}