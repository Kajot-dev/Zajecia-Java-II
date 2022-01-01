import java.awt.BorderLayout;

import javax.swing.*;


public class AppFrame extends JFrame {
	public AppFrame() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Kó³ko i kwadracik");
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.initGUI();
	}
	
	public void initGUI() {
		setLayout(new BorderLayout());
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Plansza", new PlanszaGry());
		tabPane.addTab("Obrazek", new PanelZObrazkiem());
		tabPane.addTab("Wykres", new Wykres());
		tabPane.addTab("Warcaby", new Warcaby());
		tabPane.addTab("Warcaby4", new Warcaby4());
		
		add(tabPane, BorderLayout.CENTER);
	}
}
