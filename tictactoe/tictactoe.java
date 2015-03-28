package tictactoe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class tictactoe implements ActionListener	{
	final String VERSION = "4*5 Wen Fan";
//Setting up ALL the variables
	JFrame window = new JFrame("Tic-Tac-Toe " + VERSION);
	
	JMenuBar mnuMain = new JMenuBar();
	JMenuItem 	mnuNewGame = new JMenuItem("New Game"),	
					mnuIntroduction = new JMenuItem("Introduction"),
					mnuExit = new JMenuItem("Exit"), 
					mnuAbout = new JMenuItem("About");
	
	JButton     btn1vCPU = new JButton("Play First"),
	            btnCPUv1 = new JButton("Play Second"),
				btnQuit = new JButton("Quit"),
				btnEasy=new JButton("Easy"),
				btnNormal=new JButton("Noraml"),
				btnHard=new JButton("Hard"),
	
				btnContinue = new JButton("Continue..."),
				btnTryAgain = new JButton("Try Again?");
	JButton btnEmpty[] = new JButton[21];
	
	JPanel 	pnlNewGame = new JPanel(),
			pnlHard=new JPanel(),
				pnlMenu = new JPanel(),
				pnlMain = new JPanel(),
				pnlTop = new JPanel(),
				pnlBottom = new JPanel(),
				pnlQuitNTryAgain = new JPanel(),
				pnlPlayingField = new JPanel();
				
	JLabel 	lblTitle = new JLabel("Tic-Tac-Toe"),
				lblTurn = new JLabel(),
				lblStatus = new JLabel("", JLabel.CENTER),
				lblMode = new JLabel("", JLabel.LEFT);
	JTextArea txtMessage = new JTextArea();
	
	final int winCombo[][] = new int[][]	{
		{1, 2, 3 ,4}, 			{2, 3, 4, 5}, 		{6, 7, 8, 9},    {7, 8, 9, 10},
		{11, 12, 13, 14},       {12, 13, 14, 15},   {16, 17, 18, 19},   {17, 18, 19, 20}, 
		{1, 6, 11, 16},   {2, 7, 12, 17},  {3, 8, 13, 18},  {4, 9, 14, 19},   {5, 10, 15, 20},
		{1, 7, 13, 19},   {2, 8, 14, 20},   {5, 9, 13, 17},    {4, 8, 12, 16}
/*Horizontal Wins*/	/*Vertical Wins*/ /*Diagonal Wins*/
	};
	final int X = 500, Y = 400,
			mainColorR = 200, mainColorG = 90, mainColorB = 120,
			btnColorR = 10, btnColorG = 5, btnColorB = 25;
		Color clrBtnWonColor = new Color(170, 170, 170);
	int 	turn = 1,
			//player1Won = 0, player2Won = 0,
			wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1, wonNumber4 = 1,
			option;
	boolean 	inGame = false,
				CPUGame = false,
				win = false,
	            count = false;
	String 	message,
				Player1 = "Player 1", Player2 = "Player 2",
				tempPlayer2 = "Player 2";
	
	public tictactoe()	{	//Setting game properties and layout and sytle...
	//Setting window properties:
		window.setSize(X, Y);
		window.setLocation(350, 260);
		//window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	//Setting Menu, Main, Top, Bottom Panel Layout/Backgrounds
		pnlMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		pnlNewGame.setBackground(new Color(mainColorR - 40, mainColorG - 40, mainColorB- 40));
		pnlHard.setBackground(new Color(mainColorR - 40, mainColorG - 40, mainColorB- 40));
		pnlMenu.setBackground(new Color((mainColorR - 40), (mainColorG - 40), (mainColorB- 40)));
		pnlMain.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		pnlTop.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		pnlBottom.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		
	//Setting up Panel QuitNTryAgain
		pnlQuitNTryAgain.setLayout(new GridLayout(1, 2, 2, 2));
		pnlQuitNTryAgain.add(btnTryAgain);
		pnlQuitNTryAgain.add(btnQuit);
		
	//Adding menu items to menu bar
		mnuMain.add(mnuNewGame);
		mnuMain.add(mnuIntroduction);
		mnuMain.add(mnuAbout);
		mnuMain.add(mnuExit);//	Menu Bar is Complete
		
	//Adding buttons to NewGame panel
		pnlNewGame.setLayout(new GridLayout(4, 1, 2, 21));
		pnlNewGame.add(btnContinue);
		pnlNewGame.add(btn1vCPU);
		pnlNewGame.add(btnCPUv1);
		pnlHard.add(btnEasy);
		pnlHard.add(btnNormal);
		pnlHard.add(btnHard);
	//Setting Button propertied
		btnTryAgain.setEnabled(false);
		btnContinue.setEnabled(false);
	
	//Setting txtMessage Properties
		txtMessage.setBackground(new Color(mainColorR-20, mainColorG-20, mainColorB-20));
		txtMessage.setForeground(Color.white);
		txtMessage.setEditable(false);
		
	//Adding Action Listener to all the Buttons and Menu Items
		mnuNewGame.addActionListener(this);
		mnuExit.addActionListener(this);
		mnuIntroduction.addActionListener(this);
		mnuAbout.addActionListener(this);
		btn1vCPU.addActionListener(this);
		btnCPUv1.addActionListener(this);
		btnQuit.addActionListener(this);
		btnContinue.addActionListener(this);
		btnTryAgain.addActionListener(this);
	
	//Setting up the playing field
		pnlPlayingField.setLayout(new GridLayout(4, 5, 2, 2));
		pnlPlayingField.setBackground(Color.blue);
		for(int i=1; i<=20; i++)	{
			btnEmpty[i] = new JButton();
			btnEmpty[i].setBackground(new Color(btnColorR, btnColorG, btnColorB));
			btnEmpty[i].addActionListener(this);
			pnlPlayingField.add(btnEmpty[i]);//	Playing Field is Compelte
		}
	//Adding everything needed to pnlMenu and pnlMain
		lblMode.setForeground(Color.white);
		pnlMenu.add(lblMode);
		pnlMenu.add(mnuMain);
		pnlMain.add(lblTitle);
		
	//Adding to window and Showing window
		window.add(pnlMenu, BorderLayout.NORTH);
		window.add(pnlMain, BorderLayout.CENTER);
		window.setVisible(true);
	}
	 
	public static void main(String[] args)	{
		new tictactoe();//	Calling the class construtor.
							//		PROGRAM STARTS HERE!
	}
/*
		-------------------------
		Start of all METHODS.	|
		-------------------------
*/
	public void showGame()	{	//	Shows the Playing Field
										//	*IMPORTANT*- Does not start out brand new (meaning just shows what it had before)
		clearPanelSouth();
		pnlMain.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());
		pnlBottom.setLayout(new BorderLayout());
		pnlTop.add(pnlPlayingField);
		pnlBottom.add(lblTurn, BorderLayout.WEST);
		pnlBottom.add(lblStatus, BorderLayout.CENTER);
		pnlBottom.add(pnlQuitNTryAgain, BorderLayout.EAST);
		pnlMain.add(pnlTop, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlPlayingField.requestFocus();
		inGame = true;
		checkTurn();
		//checkWinStatus();
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void newGame()	{	//	Sets all the game required variables to default
											//	and then shows the playing field.
											//	(Basically: Starts a new 1v1 Game)
		btnEmpty[wonNumber1].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		btnEmpty[wonNumber2].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		btnEmpty[wonNumber3].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		btnEmpty[wonNumber4].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		for(int i=1; i<21; i++)	{
			btnEmpty[i].setText("");
			btnEmpty[i].setEnabled(true);
		}
		turn = 1;
		win = false;
		showGame();
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void quit()	{
		inGame = false;
		lblMode.setText("");
		btnContinue.setEnabled(false);
		clearPanelSouth();
		setDefaultLayout();
		pnlTop.add(pnlNewGame);
		pnlMain.add(pnlTop);
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void checkWin()	{	//	checks if there are 4 symbols in a row vertically, diagonally, or horizontally.
										//	then shows a message and disables buttons. If the game is over then it asks
										//	if you want to play again.
		for(int i=0; i<17; i++)	{
			if(
				!btnEmpty[winCombo[i][0]].getText().equals("") &&
				btnEmpty[winCombo[i][0]].getText().equals(btnEmpty[winCombo[i][1]].getText()) &&
				btnEmpty[winCombo[i][1]].getText().equals(btnEmpty[winCombo[i][2]].getText()) &&
				btnEmpty[winCombo[i][2]].getText().equals(btnEmpty[winCombo[i][3]].getText())) {
				win = true;
				wonNumber1 = winCombo[i][0];
				wonNumber2 = winCombo[i][1];
				wonNumber3 = winCombo[i][2];
				wonNumber4 = winCombo[i][3];
				btnEmpty[wonNumber1].setBackground(clrBtnWonColor);
				btnEmpty[wonNumber2].setBackground(clrBtnWonColor);
				btnEmpty[wonNumber3].setBackground(clrBtnWonColor);
				btnEmpty[wonNumber4].setBackground(clrBtnWonColor);
				break;
			}
		}
		
		if(win || (!win && turn>20))	{
			if(win)	{
				if(btnEmpty[wonNumber1].getText().equals("X"))	{
					message = "Computer" + " has won";
					//player1Won++;
				}
				else	{
					message = "Player" + " has won";
					//player2Won++;
				}
		}	else if(!win && turn>20)
				message = "Both players have tied!\nBetter luck next time.";
			showMessage(message);
			for(int i=1; i<=20; i++)	{
				btnEmpty[i].setEnabled(false);
			}
			btnTryAgain.setEnabled(true);
			//checkWinStatus();
		} else
			checkTurn();
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void AI()	{
		int computerButton;
		int a;
	    char [] board = new char [20]; 
		if(turn <= 20)	{
			turn++;
			a=6;
			for(int k=1; k<=20; k++)	{
				if(btnEmpty[k].getText().equals("O"))   {
					board [k-1] = 'O' ;
					}
				else if(btnEmpty[k].getText().equals("X")){
					 board [k-1] = 'X';}
				else if(!btnEmpty[k].getText().equals("X")&&!btnEmpty[k].getText().equals("O"))
					board [k-1] = 'E';
				}
			computerButton = CPU.minmax(board,a);
			if(computerButton == 0)
				Random();
			else {
				btnEmpty[computerButton+1].setText("X");
				btnEmpty[computerButton+1].setEnabled(false);
			}
			checkWin();
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void Random()	{
		int random;
		if(turn <= 20)	{
			random = 0;
			while(random == 0)	{
				random = (int)(Math.random() * 20);
			}
			if(CPU.doRandomMove(btnEmpty[random]))	{
				btnEmpty[random].setText("X");
				btnEmpty[random].setEnabled(false);
			} else {
				Random();
			}
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void checkTurn()	{
		String whoTurn;
		if(!(turn % 2 == 0))	{
			whoTurn = Player1 + " [X]";
		}	else	{
			whoTurn = Player2 + " [O]";
		}
		lblTurn.setText("Turn: " + whoTurn);
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void setDefaultLayout()	{
		pnlMain.setLayout(new GridLayout(2, 1, 2, 5));
		pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	/*public void checkWinStatus()	{
		lblStatus.setText(Player1 + ": " + player1Won + " | " + Player2 + ": " + player2Won);	
	}*/
//-----------------------------------------------------------------------------------------------------------------------------------	
	public int askMessage(String msg, String tle, int op)	{
		return JOptionPane.showConfirmDialog(null, msg, tle, op);
	}
//-----------------------------------------------------------------------------------------------------------------------------------
	public String getInput(String msg, String setText)	{
		return JOptionPane.showInputDialog(null, msg, setText);
	}
//-----------------------------------------------------------------------------------------------------------------------------------
	public void showMessage(String msg)	{
		JOptionPane.showMessageDialog(null, msg);
	}
//-----------------------------------------------------------------------------------------------------------------------------------	
	public void clearPanelSouth()	{	//Removes all the possible panels 
												//that pnlMain, pnlTop, pnlBottom
												//could have.
		pnlMain.remove(lblTitle);
		pnlMain.remove(pnlTop);
		pnlMain.remove(pnlBottom);
		pnlTop.remove(pnlNewGame);
		pnlTop.remove(txtMessage);
		pnlTop.remove(pnlPlayingField);
		pnlBottom.remove(lblTurn);
		pnlBottom.remove(pnlQuitNTryAgain);
	}
/*
		-------------------------------------
		End of all non-Abstract METHODS.		|
		-------------------------------------
*/
//-------------------ACTION PERFORMED METHOD (Button Click --> Action?)-------------------------//	
	public void actionPerformed(ActionEvent click)	{
		Object source = click.getSource();
		for(int i=1; i<=20; i++)	{
			if(source == btnEmpty[i] && turn <	21)	{
				if(!(turn % 2 == 0))
					btnEmpty[i].setText("O");
				else
					btnEmpty[i].setText("X");
				btnEmpty[i].setEnabled(false);
				pnlPlayingField.requestFocus();
				turn++;
				checkWin();
				if(CPUGame && win == false)
					AI();
			}
		}
		
		if(source == mnuNewGame || source == mnuIntroduction || source == mnuAbout)	{
			clearPanelSouth();
			setDefaultLayout();
			
			if(source == mnuNewGame)	{//NewGame
				pnlTop.add(pnlNewGame);
			}	
			else if(source == mnuIntroduction|| source == mnuAbout)	{
				if(source == mnuIntroduction)	{// Instructions
					message = 	"Instructions:\n\n" +
									"Your goal is to be the first player to get 4 O's in a\n" +
									"row. (horizontally, diagonally, or vertically)\n" ;
				} else	{//About
					message = 	"About:\n\n" +
									"Title: Tic-Tac-Toe\n" +
									"Creator: Wen Fan\n" +
									"Version: " + VERSION + "\n";
				}
				txtMessage.setText(message);
				pnlTop.add(txtMessage);
			}	
			pnlMain.add(pnlTop);
		}

		else if(source == btn1vCPU || source == btnCPUv1)	{
			if(inGame)	{
				option = askMessage("If you start a new game," +
					"your current game will be lost..." + "\n" +
					"Are you sure you want to continue?", 
					"Quit Game?" ,JOptionPane.YES_NO_OPTION
				);
				if(option == JOptionPane.YES_OPTION)
					inGame = false;
			}
			
			if(!inGame)	{
				btnContinue.setEnabled(true);
				if(source == btn1vCPU)	{// player v  Game
					Player2 = "Computer";
					//player1Won = 0;
					//player2Won = 0;
					lblMode.setText("1 v CPU");
					CPUGame = true;	
					newGame();
					count = true;
					turn = 1;
				} else	{// CPU v player Game
					Player1 = "Computer";
					//player1Won = 0;
					//player2Won = 0;
					lblMode.setText("CPU v 1");
					CPUGame = true;
					newGame();
					count = false;
					turn = 0;
					AI();
				}
			}
		}
		else if(source == btnContinue)	{
			checkTurn();
			showGame();
		}
		else if(source == mnuExit)	{
			option = askMessage("Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION)
				System.exit(0);
		}
		else if(source == btnTryAgain)	{
			newGame();
			btnTryAgain.setEnabled(false);
			if (count == false)
			{
				turn = 0;
				AI();
			}
			else {
				turn = 1;
			}
			
		}
		else if(source == btnQuit)	{
			quit();
		}
		pnlMain.setVisible(false);
		pnlMain.setVisible(true);
	}
}