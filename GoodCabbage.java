import java.awt.Color;
import java.awt.Point;

import uwcse.graphics.*;;


public class GoodCabbage extends Cabbage implements CaterpillarGameConstants{
	public GoodCabbage(GWindow window, Point center) {
		super(window, center);
		o= new Oval(center.x-CABBAGE_RADIUS,center.y-CABBAGE_RADIUS, 2*CABBAGE_RADIUS, 2*CABBAGE_RADIUS, Color.yellow, true);
		// TODO Auto-generated constructor stub
	}
	public void draw(){
		window.add(o);
	}
	/**
	 *  it is eaten by caterpillar
	 *  that remove it from window and let the caterpillar grow
	 */
	public void isEatenBy(Caterpillar cp){
		window.remove(o);
		cp.grow();
	}
}
