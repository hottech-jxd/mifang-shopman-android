package com.github.glomadrian.loadingballs.factory.path;

import android.graphics.Path;
import android.graphics.Point;

public class Line extends BallPath {

    public Line(Point center, int pathWidth, int pathHeight, int maxBallSize) {
        super(center, pathWidth, pathHeight, maxBallSize);
    }

    @Override
    public Path draw() {
        Path path=new Path();
        path.moveTo( maxBallSize, center.y );
        path.lineTo(center.x , center.y );
        path.lineTo(pathWidth-maxBallSize , center.y );
        path.close();

        return path;
    }
}
