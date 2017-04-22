//
// VolumeMeterView.java - How to use AudioRecord class...
//
// GitHub:
//     https://github.com/yoggy/VolumeMeter
//
// license:
//     Copyright (c) 2017 yoggy <yoggy0@gmail.com>
//     Released under the MIT license
//     http://opensource.org/licenses/mit-license.php;
//
package net.sabamiso.android.volumemeter;

import android.content.Context;

import net.sabamiso.android.p5.PseudoP5View;

public class VolumeMeterView extends PseudoP5View {
    float volume_level = 0.0f;

    public VolumeMeterView(Context context) {
        super(context);
    }

    @Override
    protected void setup() {
        size(360, 640);
        frameRate(30);
    }

    @Override
    protected void draw() {
        background(0, 0, 0);

        noStroke();
        fill(0, 255, 0);

        rect(100, (height * (1.0f - volume_level)), width-200, height * volume_level);

        fill(255, 0, 0);
        ellipse(30, 30, 20, 20);
    }

    public void setVolumeLevel(float val) {
        this.volume_level = val;

        // clipping
        if (volume_level < 0.0f) volume_level = 0.0f;
        if (volume_level > 1.0f) volume_level = 1.0f;
    }
}

