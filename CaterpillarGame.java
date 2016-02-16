import uwcse.graphics.*;

import java.util.*;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JOptionPane;
/**
 * A CaterpillarGame displays a garden that contains good and bad cabbages and a
 * constantly moving caterpillar. The player directs the moves of the
 * caterpillar. Every time the caterpillar eats a cabbage, the caterpillar
 * grows. The player wins when all of the good cabbages are eaten and the
 * caterpillar has left the garden. The player loses if the caterpillar eats a
 * bad cabbage or crawls over itself.
 */

public class CaterpillarGame extends GWindowEventAdapter implements
		CaterpillarGameConstants
// The class inherits from GWindowEventAdapter so that it can handle key events
// (in the method keyPressed), and timer events.
// All of the code to make this class able to handle key events and perform
// some animation is already written.
{
	// Game window
	private GWindow window;

	// The caterpillar
	private Monster m;
	private Caterpillar cp;
	private Rectangle fence1,fence2;
	// Direction of motion given by the player
	private int dirFromKeyboard;

	// Do we have a keyboard event
	private boolean isKeyboardEventNew = false;
    private boolean isBulletEventNew =false;
	// The list of all the cabbages
	private ArrayList<Cabbage> cabbages;

	// is the current game over?
	private boolean gameOver;
	private String messageGameOver;

	/**
	 * Constructs a CaterpillarGame
	 */
	public CaterpillarGame() {
		// Create the graphics window
		window = new GWindow("Caterpillar game", WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setExitOnClose();
		// Any key or timer event while the window is active is sent to this
		// CaterpillarGame
		window.addEventHandler(this);
		JOptionPane.showMessageDialog(null,"Let's play\n Game rule\n kill the monster"
				+ " with 10 hp to win the game\n the yellow cabbage can grow up the caterpillar,\n the blue one can "
				+ "speed up the catepillar\n the white can killyour caterpillar\n the length of caterpillar "
				+ "represent number or  \ncatepillar's bullets(but the length can less than 2)");
		// Set up the game (fence, cabbages, caterpillar)
		JOptionPane.showMessageDialog(null,"to move left, press 'a'\n to move right, press 'd'\n "
				+ "to move up, press 'w'\n to move down, press 's'\n to shoot bullet, press 'y'\n ");
		initializeGame();
		// Display the game rules
		
		// ...
	}

	/**
	 * Initializes the game (draw the garden, garden fence, cabbages,
	 * caterpillar)
	 */
	private void initializeGame() {
		// Clear the window
		window.erase();
		// New game
		gameOver = false;
		// No keyboard event yet
		isKeyboardEventNew = false;
		isBulletEventNew = false;
		// Background (the garden)
		window.add(new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT,
				Color.green, true));

		// Create the fence around the garden
		fence1 = new Rectangle (100,0,10,200,Color.BLUE,true);
		fence2 = new Rectangle (100,300,10,200,Color.BLUE,true);
		window.add(fence1);
		window.add(fence2);
		
		// Cabbages
		
		cabbages = new ArrayList<Cabbage>(N_GOOD_CABBAGES + N_BAD_CABBAGES+N_CRAZY_CABBAGES);
		for(int i=0;i<N_GOOD_CABBAGES;i++){
			Point radomPoint=new Point((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)),(int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
			while(radomPoint.x<150||radomPoint.y<CABBAGE_RADIUS){
				radomPoint.setLocation((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)), (int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
			}
			GoodCabbage c= new GoodCabbage(window,radomPoint);
			c.draw();
			cabbages.add(c);
		}
		for(int i=0;i<N_BAD_CABBAGES;i++){
			Point radomPoint=new Point((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)),(int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
			while(radomPoint.x<150||radomPoint.y<CABBAGE_RADIUS){
				radomPoint.setLocation((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)), (int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
			}
			BadCabbage c= new BadCabbage(window,radomPoint);
			c.draw();
			cabbages.add(c);
		}
		for(int i=0;i<N_CRAZY_CABBAGES;i++){
			Point radomPoint=new Point((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)),(int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
			while(radomPoint.x<150||radomPoint.y<CABBAGE_RADIUS){
				radomPoint.setLocation((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)), (int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
			}
			CrazyCabbage c= new CrazyCabbage(window,radomPoint);
			c.draw();
			cabbages.add(c);
		}
		// compare all the points in the cabbages list and make them not overlap each other
		for(int i=0;i<cabbages.size();i++){
			for(int j=0;j<cabbages.size();j++){
				if(i!=j){
					while(cabbages.get(i).center.distance(cabbages.get(j).center)<2*CABBAGE_RADIUS){
						cabbages.get(j).removeFromGWindow();
						Point radomPoint=new Point((int)(Math.random()*WINDOW_WIDTH),(int)(Math.random()*WINDOW_HEIGHT));
						while(radomPoint.x<150||radomPoint.y<CABBAGE_RADIUS){
							radomPoint.setLocation((int)(Math.random()*(WINDOW_WIDTH-CABBAGE_RADIUS)), (int)(Math.random()*(WINDOW_HEIGHT-CABBAGE_RADIUS)));
						}
					cabbages.get(j).center.setLocation(radomPoint);
					cabbages.get(j).draw();
					}
				}
			}
		}
		
		// Create the caterpillar and the monster
		cp = new Caterpillar(window);
		m = new Monster (window);

		// start timer events (to do the animation)
		this.window.startTimerEvents(ANIMATION_PERIOD);
	}

	/**
	 * Moves the caterpillar within the graphics window every ANIMATION_PERIOD
	 * milliseconds.
	 * 
	 * @param e
	 *            the timer event
	 */
	public void timerExpired(GWindowEvent e) {
		// Did we get a new direction from the user?
		// Use isKeyboardEventNew to take the event into account
		// only once
		window.suspendRepaints();
		if (isBulletEventNew)  {//if it shoots
			isBulletEventNew =false;
			cp.bulletMove();
			if(cp.body.size()==1) {//if it has no tail
				messageGameOver="your caterpillar has no tail, you lose";
				gameOver=true;
			}
		}
		else if (isKeyboardEventNew) {
			if (cp.bullet.size()>0) cp.bulletMove();
			isKeyboardEventNew = false;
			cp.move(dirFromKeyboard);
			if(cp.isCrawlingOverItself()||cp.isOutsideGarden()
					||m.haveEated(cp)||cp.isTouchFence(cp.getHead())){
				messageGameOver="you lose";
				gameOver=true;
			}
		} else{
			if (cp.bullet.size()>0) cp.bulletMove();
			cp.move();
			m.move(cp);
			if(cp.isCrawlingOverItself()||cp.isOutsideGarden()||m.haveEated(cp)
					||cp.isTouchFence(cp.getHead())){
				messageGameOver="you lose";
				gameOver=true;
			}
		}
		//the monster get shot by the cabbages
		m.getShot(cp);
		for (int i =0;i<cabbages.size();i++){
			Cabbage c = cabbages.get(i);
			Point head1=new Point();
			Point head2=new Point();
			Point head3=new Point();
			head1.setLocation(cp.getHead().x, cp.getHead().y+5);
			head2.setLocation(cp.getHead().x+5, cp.getHead().y);
			head3.setLocation(cp.getHead().x+5, cp.getHead().y+5);
			// Is the caterpillar eating a cabbage?
			if(c.isPointInCabbage(cp.getHead())||c.isPointInCabbage(head1)
					||c.isPointInCabbage(head2)||c.isPointInCabbage(head3)){
				c.isEatenBy(cp);
				gameOver=c.cpGameOver;
				cabbages.remove(c);
				if(gameOver)  messageGameOver=" do not eat the white cabbage";
			}
		}
		window.resumeRepaints();
		if(m.isDied()){
			messageGameOver="you win";
			gameOver=true;
		}
		// Is the game over?
		 if (gameOver) {
		 endTheGame();
		 }
	}

	/**
	 * Moves the caterpillar according to the selection of the user i: NORTH, j:
	 * WEST, k: EAST, m: SOUTH
	 * 
	 * @param e
	 *            the keyboard event
	 */
	public void keyPressed(GWindowEvent e) {
		switch (Character.toLowerCase(e.getKey())) {
		case 'w':
			dirFromKeyboard = NORTH;
			break;
		case 'a':
			dirFromKeyboard = WEST;
			break;
		case 'd':
			dirFromKeyboard = EAST;
			break;
		case 's':
			dirFromKeyboard = SOUTH;
			break;
		case 'y':
			cp.shoot();
			isBulletEventNew= true;
		default:
			return;
		}

		// new keyboard event
		isKeyboardEventNew = true;
	}

	/**
	 * The game is over. Starts a new game or ends the application
	 */
	private void endTheGame() {
		window.stopTimerEvents();
		// messageGameOver is an instance String that
		// describes the outcome of the game that just ended
		// (e.g. congratulations! you win)
		boolean again = anotherGame(messageGameOver);
		if (again) {
			initializeGame();
		} else {
			System.exit(0);
		}
	}

	/**
	 * Does the player want to play again?
	 */
	private boolean anotherGame(String s) {
		int choice = JOptionPane.showConfirmDialog(null, s
				+ "\nDo you want to play again?", "Game over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.YES_OPTION)
			return true;
		else
			return false;
	}

	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		new CaterpillarGame();
	}
}