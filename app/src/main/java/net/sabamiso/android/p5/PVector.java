//
//	PVector.java - pseudo PVector implementation.
//
//	github:
//      yoggy / PseudoP5View
//      https://github.com/yoggy/PseudoP5View
//
//  license:
//      Copyright (c) 2015 yoggy <yoggy0@gmail.com>
//      Released under the MIT license
//      http://opensource.org/licenses/mit-license.php
//
package net.sabamiso.android.p5;

import java.util.Random;

public class PVector {
	public float x;
	public float y;
	
	public PVector() {
		set(0.0f, 0.0f);
	}

	public PVector(int x, int y) {
		set(x, y);
	}

	public PVector(float x, float y) {
		set(x, y);
	}

	public PVector(double x, double y) {
		set(x, y);
	}

	public void set(int x, int y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}

	public static PVector random2D() {
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		
		float x = (float)(rnd.nextDouble() * 2 - 1.0);
		float y = (float)(rnd.nextDouble() * 2 - 1.0);		
		return new PVector(x, y);
	}
	
	public PVector get() {
		PVector rv = new PVector();
		rv.x = this.x;
		rv.y = this.y;
		return rv;
	}
	
	public void add(PVector p) {
		add(p.x, p.y);
	}

	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}

	public void sub(PVector p) {
		sub(p.x, p.y);
	}

	public void sub(float x, float y) {
		this.x -= x;
		this.y -= y;
	}

	public void mult(PVector p) {
		mult(p.x, p.y);
	}

	public void mult(float x, float y) {
		this.x *= x;
		this.y *= y;
	}

	public void div(PVector p) {
		div(p.x, p.y);
	}

	public void div(float x, float y) {
		this.x /= x;
		this.y /= y;
	}
	
	public float dist(PVector p) {
		float dx = p.x - this.x;
		float dy = p.y - this.y;
		
		float dd = dx * dx + dy * dy;
		float d = (float)Math.sqrt(dd);
		
		return d;
	}
}
