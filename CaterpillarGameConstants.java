/**
 * Interface that lists useful constants for the caterpillar game
 */

public interface CaterpillarGameConstants {
	// Possible directions for the motion of the caterpillar
	public static final int NORTH = 1;

	public static final int EAST = 2;

	public static final int WEST = 3;

	public static final int SOUTH = 4;

	// Distance covered by the caterpillar in one move
	public static final int STEP = 10;
	// Distance covered by the bullet in one move
	public static final int BULLET_STEP = 15;
	// Number of body elements added to the caterpillar when it grows
	// (after eating a good cabbage)
	public static final int GROWTH_SPURT = 5;

	// Thickness of the caterpillar
	public static final int CATERPILLAR_WIDTH = 6;

	// Number of good cabbages
	public static final int N_GOOD_CABBAGES = 10;

	// Number of bad cabbages
	public static final int N_BAD_CABBAGES = 1;
	
	public static final int N_CRAZY_CABBAGES =10;
	// Radius of a cabbage head
	public static final int CABBAGE_RADIUS = 5;

	// Size of the graphics window
	public static final int WINDOW_HEIGHT = 500;

	public static final int WINDOW_WIDTH = 500;

	// Period of the animation (in ms)
	public static final int ANIMATION_PERIOD = 150;
	public static final int MONSTER_STEP =4;
}