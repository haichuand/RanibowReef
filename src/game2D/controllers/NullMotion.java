package game2D.controllers;

import game2D.objects.MoveableObject;

/**
 * Motion controller that does nothing
 * */
public class NullMotion extends Motion {

	public NullMotion(MoveableObject obj) {
		super(obj);
	}

	@Override
	public void move(int steps) {}

}
