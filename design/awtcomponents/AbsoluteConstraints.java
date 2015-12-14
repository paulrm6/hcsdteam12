package design.awtcomponents;

/**
 * Taken from Java.net
 *
 * @author unable to identify
 * @date Aug 19, 1998
 * @version 1.01
 * Source: https://java.net/projects/oooview/sources/svn/content/trunk/src/writer/ui/layout/AbsoluteConstraints.java?rev=7
 **/

import java.awt.*;

/** An object that encapsulates position and (optionally) size for
 *  absolute positioning of objects.
 */

/** Absolute positioning of components */
public class AbsoluteConstraints implements java.io.Serializable {
    /** generated Serialized Version UID */
    static final long serialVersionUID = 5261460716622152494L;

    /** The X and Y position of the components */
    public int x;
    public int y;

    /** The width/height of the component or -1 if the component's preferred width/height should be used */
    public int width = -1;
    public int height = -1;

    /***/
    public AbsoluteConstraints(Point pos) {
        this(pos.x, pos.y);
    }

    /***/
    public AbsoluteConstraints(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /***/
    public AbsoluteConstraints(Point pos, Dimension size) {
        this.x = pos.x;
        this.y = pos.y;
        if (size != null) {
            this.width = size.width;
            this.height = size.height;
        }
    }

    /** Constructs a new AbsoluteConstraints for specified position and size **/
    public AbsoluteConstraints(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /** The X and Y position are represented by these AbsoluteConstraints */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /***/
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String toString() {
        return super.toString() + " [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }
}