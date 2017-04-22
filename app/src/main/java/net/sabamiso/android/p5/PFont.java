//
//	PFont.java - pseudo PFont implementation.
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

public class PFont {

	String fontname;
	int size;

	public PFont(String fontname, int size) {
		this.fontname = fontname;
		this.size = size;
	}

	String getFontName() {
		return fontname;
	}

	int getSize() {
		return size;
	}

}
