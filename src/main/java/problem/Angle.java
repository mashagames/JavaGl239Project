package problem;

import problem.Vector2;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Класс угол
 */

public class Angle {
    public Vector2 c = new Vector2(0, 0);

    public double alpha1, alpha2;

    public void render(GL2 gl) {
        gl.glBegin(GL.GL_LINE_STRIP);
        double x1 = c.x + 0.05 * Math.cos(alpha1);
        double y1 = c.y + 0.05 * Math.sin(alpha1);
        gl.glVertex2d(x1, y1);
        gl.glVertex2d(c.x, c.y);
        x1 = c.x + 0.05 * Math.cos(alpha2);
        y1 = c.y + 0.05 * Math.sin(alpha2);
        gl.glVertex2d(x1, y1);
        gl.glEnd();
    }

    public static Angle getRandomAngle() {
        Vector2 c = new Vector2();
        c.x = Math.random() * 2 - 1;
        c.y = Math.random() * 2 - 1;
        double alpha1 = Math.random() * Math.PI * 2;
        double alpha2 = Math.random() * Math.PI / 2 + alpha1;
        if (alpha2 > 2 * Math.PI) {
            alpha2 -= 2 * Math.PI;
        }
        return new Angle(c, alpha1, alpha2);
    }

     public Angle(Vector2 c, double alpha1, double alpha2) {
        this.c = c;
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
    }

}

