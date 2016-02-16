import uwcse.graphics.*;

import java.awt.Point;
import java.awt.Color;
public class Monster implements CaterpillarGameConstants {
	private Point core;
	public GWindow window;
	private Oval body,eye1,eye2;
	private int hp;
	private TextShape textHp;
	/**
	 * 		creates a monster which can chase caterpillar
	 * @param window
	 * the GWindow this Monster belongs to
	 */
	public Monster(GWindow window){
		hp=10;
		this.window = window;
		core = new Point();
		core.x=WINDOW_HEIGHT-50;
		core.y=WINDOW_WIDTH-50;
		display();
	}
	/**
	 * display the monster on the window
	 */
	public void display(){
		body = new Oval (core.x-10,core.y-10,20,20,Color.black,true);
		eye1 = new Oval (core.x-5,core.y-5,10,10,Color.red,true);
		eye2 = new Oval (core.x-3	,core.y-5,5,10,Color.yellow,true);
		textHp = new TextShape ("hp:"+hp+"/10",0,0); 
		window.add(body);
		window.add(eye1);
		window.add(eye2);
		window.add(textHp);
	}
	/**
	 * Move the monster base on caterpillar's position
	 * @param cp
	 * 			The caterpillar the monster is chasing 
	 */
	public void move(Caterpillar cp){
		Point aveCenter=cp.getCenter();
		if(Math.abs(core.x-aveCenter.x)>Math.abs(core.y-aveCenter.y)){
			if(core.x-aveCenter.x>=0) core.x-=MONSTER_STEP;
			else core.x+=MONSTER_STEP;
		}
		else if(Math.abs(core.x-aveCenter.x)==Math.abs(core.y-aveCenter.y)){
			if(core.x-aveCenter.x>=0&&core.y-aveCenter.y>=0) {
				core.x-=MONSTER_STEP;
				core.y-=MONSTER_STEP;
			}
			else if (core.x-aveCenter.x<0&&core.y-aveCenter.y>=0) {
				core.x+=MONSTER_STEP;
				core.y-=MONSTER_STEP;
			}
			else if (core.x-aveCenter.x>=0&&core.y-aveCenter.y<0) {
				core.x-=MONSTER_STEP;
				core.y+=MONSTER_STEP;
			}
			else {
				core.x+=MONSTER_STEP;
				core.y+=MONSTER_STEP;
			}
		}
		else{
			if(core.y-aveCenter.y>=0) core.y-=MONSTER_STEP;
			else core.y+=MONSTER_STEP;
		}
		
		updateDisplay();
	}
	/**
	 * update the display
	 */
	public void updateDisplay(){
		window.remove(body);
		window.remove(eye1);
		window.remove(eye2);
		window.remove(textHp);
		display();
		this.window.doRepaint();
	}
	/**
	 * have the monster eaten the caterpillar
	 * @param cp  the caterpillar
	 * @return  true if monster have eaten cp and false otherwise.
	 */
	public boolean haveEated(Caterpillar cp){
		for (Point p :cp.body){
			if(core.distance(p)<=10) return true;
		}
		return false;
		}
	/**
	 * 
	 * @return
	 * 			the core of the monster
	 */
	public Point getCore(){
		return core;
	}
	/**
	 * get shot by the caterpillar
	 * @param cp the caterpillar
	 */
	public void getShot(Caterpillar cp){
		for(int i=0;i<cp.bullet.size();i++){
			if(core.distance(cp.bullet.get(i))<=10) {
				hp-=1;
				cp.bulletDir.remove(i);
				cp.bullet.remove(i);
				cp.removeBullet(i);
			}	
		}
	}
	public boolean isDied(){
		if(hp<=0) return true;
		else return false;
	}
}
