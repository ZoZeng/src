import java.awt.Color;
import java.awt.Point;

import uwcse.graphics.*;;


public class CrazyCabbage extends Cabbage implements CaterpillarGameConstants{
	public CrazyCabbage(GWindow window, Point center) {
		super(window, center);
		o=new Oval(center.x-CABBAGE_RADIUS,center.y-CABBAGE_RADIUS, 2*CABBAGE_RADIUS, 2*CABBAGE_RADIUS, Color.blue, true);
		// TODO Auto-generated constructor stub
	}
	public void draw(){
		window.add(o);
	}
	/**
	 *  it is eaten by caterpillar
	 *  that remove it from window and let the speed up the animation
	 */
	public void isEatenBy(Caterpillar cp){
		window.remove(o);
		cp.speed-=30;
		window.stopTimerEvents();
		window.startTimerEvents(cp.speed);
	}
}