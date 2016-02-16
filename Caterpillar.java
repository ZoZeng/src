import uwcse.graphics.*;

import java.awt.Point;
import java.util.*;
import java.awt.Color;

/**
 * A Caterpillar is the representation and the display of a caterpillar
 */

public class Caterpillar implements CaterpillarGameConstants{
	// The body of a caterpillar is made of Points stored
	// in an ArrayList
	public ArrayList<Point> body = new ArrayList<Point>();

	// Store the graphical elements of the caterpillar body
	// Useful to erase the body of the caterpillar on the screen
	private ArrayList<Rectangle> bodyUnits = new ArrayList<Rectangle>();
	//Store the elements of the bullets' informations
	public ArrayList<Point> bullet=new ArrayList<Point>();
	public ArrayList<Rectangle> bulletRectangle= new ArrayList<Rectangle>();
	public ArrayList<Integer> bulletDir=new ArrayList<Integer>();
	// The window the caterpillar belongs to
	private GWindow window;
	// Direction of motion of the caterpillar (NORTH initially)
	private int dir = NORTH;
	public int speed;
	/**
	 * Constructs a caterpillar
	 * 
	 * @param window
	 *            the graphics where to draw the caterpillar.
	 */
	public Caterpillar(GWindow window) {
		// Initialize the graphics window for this Caterpillar
		this.window = window;
		speed=150;
		// Create the caterpillar (10 points initially)
		// First point
		Point p = new Point();
		p.x = 5;
		p.y = WINDOW_HEIGHT / 2;
		body.add(p);

		// Other points
		for (int i = 0; i < 9; i++) {
			Point q = new Point(p);
			q.translate(STEP, 0);
			body.add(q);
			p = q;
		}

		// Other initializations (if you have more instance fields)

		// Display the caterpillar (call a private method)
		display();
	}
	/**
	 * the caterpillar shoot the bullet out
	 * and lessen it's body
	 */
	public void shoot(){
		bullet.add(body.get(0));
		Rectangle  r = new Rectangle (body.get(0).x,body.get(0).y,5,5,Color.black,true);
		bulletRectangle.add(r);
		window.remove(bodyUnits.get(0));
		body.remove(0);
		bodyUnits.remove(0);
		bulletDir.add(dir);
	}

	/**
	 * Moves the caterpillar in the current direction (complete)
	 */
	public void move() {
		move(dir);
	}
	/**
	 * display the caterpillar on the window
	 */
	private void display(){
		for(int i=0;i<body.size();i++){
			Rectangle r=new Rectangle(body.get(i).x,body.get(i).y,5,5
					,Color.black,true);
			if(i==0){
				bodyUnits.clear();
				bodyUnits.add(r);
			}
			else{
				bodyUnits.add(r);
			}
			window.add(bodyUnits.get(i));
		}
		
	}
	/**
	 * update the graphic of caterpillar on the window
	 */
	private void updateDisplay(){
		for(Rectangle r:bodyUnits){
			window.remove(r);
		}
		for(int i=0;i<body.size();i++){
			Rectangle r=new Rectangle(body.get(i).x,body.get(i).y,5,5
					,Color.black,true);
			bodyUnits.set(i, r);
			window.add(bodyUnits.get(i));
		}
	}
	/**
	 * remove the bullet from window
	 * @param i
	 *        index of the bullet list
	 */
	public void removeBullet(int i){
		window.remove(bulletRectangle.get(i));
	}
	/**
	 * let the bullet move after shot
	 */
	public void bulletMove(){
		for(int i=0; i<bulletDir.size();i++){
			if (bulletDir.get(i)==NORTH){
				bullet.get(i).y-=BULLET_STEP;
				window.remove(bulletRectangle.get(i));
				bulletRectangle.set(i, new Rectangle(bullet.get(i).x,bullet.get(i).y,5,5
						,Color.black,true));
				window.add(bulletRectangle.get(i));
			}
			else if (bulletDir.get(i)==SOUTH){
				bullet.get(i).y+=BULLET_STEP;
				window.remove(bulletRectangle.get(i));
				bulletRectangle.set(i, new Rectangle(bullet.get(i).x,bullet.get(i).y,5,5
						,Color.black,true));
				window.add(bulletRectangle.get(i));
			}
			else if (bulletDir.get(i)==EAST){
				bullet.get(i).x+=BULLET_STEP;
				window.remove(bulletRectangle.get(i));
				bulletRectangle.set(i, new Rectangle(bullet.get(i).x,bullet.get(i).y,5,5
						,Color.black,true));
				window.add(bulletRectangle.get(i));
			}
			else {
				bullet.get(i).x-=BULLET_STEP;
				window.remove(bulletRectangle.get(i));
				bulletRectangle.set(i, new Rectangle(bullet.get(i).x,bullet.get(i).y,5,5
						,Color.black,true));
				window.add(bulletRectangle.get(i));
			}
		}
	}

	/**
	 * Move the caterpillar in the direction newDir. <br>
	 * If the new direction is illegal, select randomly a legal direction of
	 * motion and move in that direction.<br>
	 * 
	 * @param newDir
	 *            the new direction.
	 */
	public void move(int newDir) {
		if (newDir==NORTH){
			Point unchangePoint= new Point(body.get(0).x,body.get(0).y);
			body.get(0).y-=STEP;
			for(int i =body.size()-1; i>0;i--){
				if(i==1){
					body.set(i,unchangePoint);
				}
				else body.set(i,body.get(i-1));
			}
			updateDisplay();
			dir=NORTH;
		}
		else if(newDir==SOUTH){
			Point unchangePoint= new Point(body.get(0).x,body.get(0).y);
			body.get(0).y+=STEP;
			for(int i =body.size()-1; i>0;i--){
				if(i==1){
					body.set(i,unchangePoint);
				}
				else body.set(i,body.get(i-1));
			}
			updateDisplay();
			dir=SOUTH;
		}
		else if(newDir==EAST){
			Point unchangePoint= new Point(body.get(0).x,body.get(0).y);
			body.get(0).x+=STEP;
			for(int i =body.size()-1; i>0;i--){
				if(i==1){
					body.set(i,unchangePoint);
				}
				else body.set(i,body.get(i-1));
			}
			updateDisplay();
			dir=EAST;
		}
		else{
			Point unchangePoint= new Point(body.get(0).x,body.get(0).y);
			body.get(0).x-=STEP;
			for(int i =body.size()-1; i>0;i--){
				if(i==1){
					body.set(i,unchangePoint);
				}
				else body.set(i,body.get(i-1));
			}
			updateDisplay();
			dir=WEST;
		}
	}

	/**
	 * Is the caterpillar crawling over itself?
	 * 
	 * @return true if the caterpillar is crawling over itself and false
	 *         otherwise.
	 */
	public boolean isCrawlingOverItself() {
		// Is the head point equal to any other point of the caterpillar?
		int repeat =0;
		for(Point p: body ){
			if (p.x==body.get(0).x&&p.y==body.get(0).y){
				repeat+=1;
			}
		}
		if(repeat >=2)return true;
		else return false; // CHANGE THIS!
	}

	/**
	 * Are all of the points of the caterpillar outside the garden
	 * 
	 * @return true if the caterpillar is outside the garden and false
	 *         otherwise.
	 */
	public boolean isOutsideGarden() {
		if(body.get(0).x<0||body.get(0).x>WINDOW_WIDTH||
				body.get(0).y<0||body.get(0).y>=WINDOW_HEIGHT) {
			return true;
		}
		else return false; // CHANGE THIS!
	}

	/**
	 * Return the location of the head of the caterpillar (complete)
	 * 
	 * @return the location of the head of the caterpillar.
	 */
	public Point getHead() {
		return new Point((Point) body.get(0));
	}	
	/**
	 * Are the head of the caterpillar in the fences
	 * 
	 * @return true if the caterpillar is in the fences and false
	 *         otherwise.
	 */
	public boolean isTouchFence(Point p){
		if(p.x>=100&&p.x<=110
				&&p.y<200) return true;
		else if (p.x>=100&&p.x<=110
				&&p.y<=500&&p.y>295) return true;
		else return false;
	}
	/**
	 * 
	 * @return the point of the average point of the caterpillar 
	 */
	public Point getCenter(){
		Point aveCenter = new Point();
		int sumX=0;
		int sumY=0;
		for (Point i: body){
			sumX+=i.x;
			sumY+=i.y;
		}
		aveCenter.x=Math.round(sumX/body.size());
		aveCenter.y=Math.round(sumY/body.size());
		return aveCenter;
	}

	/**
	 * Increase the length of the caterpillar (by GROWTH_SPURT elements) Add the
	 * elements at the tail of the caterpillar.
	 */
	public void grow() {
		for(int i= 0;i<GROWTH_SPURT;i++){
			Point p = new Point ();
			if(body.get(body.size()-1).x==body.get(body.size()-2).x){
				if(body.get(body.size()-1).y>body.get(body.size()-2).y){
					p.setLocation(body.get(body.size()-1).x, body.get(body.size()-1).y+10);
					body.add(p);
				}
				else{
					p.setLocation(body.get(body.size()-1).x, body.get(body.size()-1).y-10);
					body.add(p);
				}
			}
			else if (body.get(body.size()-1).y==body.get(body.size()-2).y){
				if(body.get(body.size()-1).x<body.get(body.size()-2).x){
					p.setLocation(body.get(body.size()-1).x-10, body.get(body.size()-1).y);
					body.add(p);
				}
				else{
					p.setLocation(body.get(body.size()-1).x+10, body.get(body.size()-1).y);
					body.add(p);
				}
			}
			if(isTouchFence(body.get(body.size()-1))){
				
			}
			Rectangle r=new Rectangle(body.get(body.size()-1).x,body.get(body.size()-1).y,5,5
					,Color.black,true);
			bodyUnits.add(r);
			window.add(bodyUnits.get(body.size()-1));
		}
	}
}