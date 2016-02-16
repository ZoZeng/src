import java.awt.Color;
import java.awt.Point;

import uwcse.graphics.*;;


public class BadCabbage extends Cabbage implements CaterpillarGameConstants{
	public BadCabbage(GWindow window, Point center) {
		super(window, center);
		o=new Oval(center.x-CABBAGE_RADIUS,center.y-CABBAGE_RADIUS, 2*CABBAGE_RADIUS, 2*CABBAGE_RADIUS, Color.white, true);
		
		// TODO Auto-generated constructor stub
	}
	public void draw(){
		window.add(o);
	}
	/**
	 *  it is eaten by caterpillar
	 *  that remove it from window and let the game over
	 */
	public void isEatenBy(Caterpillar cp){
		window.remove(o);
		cpGameOver=true;
	}
}