import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameOverPanel extends JPanel implements ActionListener{
	
	private JButton endGame = new JButton();
	private JButton restart = new JButton();
	
	private static JLabel gameOver = new JLabel();
	private static JLabel congrats = new JLabel();
	
	public GameOverPanel(){
		panelSetUp();
	}

	private void panelSetUp() {
		
		endGame.setBounds(100,200,50,50);
		endGame.addActionListener(this);
		endGame.setVisible(true);
		this.add(endGame);
		
		restart.setBounds(50,200,50,50);
		restart.addActionListener(this);
		restart.setVisible(true);
		this.add(restart);
		
		gameOver.setBounds(50, 50, 200, 100);
		gameOver.setText("Game Over");
		this.add(gameOver);
		
		congrats.setBounds(50, 50, 200, 100);
		congrats.setText("Congratuations");
		this.add(congrats);
		
		setBounds(0,0,800,800);
		setBackground(Color.BLUE);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == endGame)
			System.exit(0);
		else if(e.getSource() == restart)
			new GameFrame();
		
	}

	public JLabel getGameOver() {
		return gameOver;
	}

	public void setGameOver(JLabel gameOver) {
		this.gameOver = gameOver;
	}

	public static JLabel getCongrats() {
		return congrats;
	}

	public void setCongrats(JLabel congrats) {
		this.congrats = congrats;
	}

}
