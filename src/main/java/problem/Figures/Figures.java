package problem.Figures;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import problem.Figures.Angle;
import problem.Vector2;


public class Figures {

    public static void renderPoint(GL2 gl, Vector2 pos, float size) {
        gl.glPointSize(size);
        gl.glBegin(GL.GL_POINTS);

        gl.glVertex2d(pos.x, pos.y);
        gl.glEnd();
    }

    public static void renderLine(GL2 gl, Vector2 posA, Vector2 posB, float width) {
        gl.glLineWidth(width);
        gl.glBegin(GL.GL_LINES);

        gl.glVertex2d(posA.x, posA.y);
        gl.glVertex2d(posB.x, posB.y);
        gl.glEnd();
    }

    public static void renderTriangle(GL2 gl, Vector2 posA, Vector2 posB, Vector2 posC, boolean filled) {
        if (filled) {
            gl.glBegin(GL.GL_TRIANGLES);

            gl.glVertex2d(posA.x, posA.y);
            gl.glVertex2d(posB.x, posB.y);
            gl.glVertex2d(posC.x, posC.y);
            gl.glEnd();
        } else {

            gl.glBegin(GL.GL_LINE_STRIP);

            gl.glVertex2d(posA.x, posA.y);
            gl.glVertex2d(posB.x, posB.y);
            gl.glVertex2d(posC.x, posC.y);
            gl.glVertex2d(posA.x, posA.y);
            gl.glEnd();
        }
    }

    public static void renderQuads(GL2 gl, Vector2 posA, Vector2 posB, Vector2 posC, Vector2 posD, boolean filled) {
        if (filled) {
            gl.glBegin(GL.GL_TRIANGLES);

            gl.glVertex2d(posA.x, posA.y);
            gl.glVertex2d(posC.x, posC.y);
            gl.glVertex2d(posD.x, posD.y);
            gl.glVertex2d(posB.x, posB.y);
            gl.glVertex2d(posC.x, posC.y);
            gl.glVertex2d(posD.x, posD.y);
            gl.glEnd();
        } else {

            gl.glBegin(GL.GL_LINE_STRIP);

            gl.glVertex2d(posA.x, posA.y);
            gl.glVertex2d(posD.x, posD.y);
            gl.glVertex2d(posB.x, posB.y);
            gl.glVertex2d(posC.x, posC.y);
            gl.glVertex2d(posA.x, posA.y);
            gl.glEnd();
        }
    }

    public static void renderCircle(GL2 gl, Vector2 center, double rad, boolean filled) {
        if (filled) {
            gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex2d(center.x, center.y);
        } else {
            gl.glBegin(GL.GL_LINE_STRIP);
        }
        for (int i = 0; i <= 40; i++) {
            double alpha = 2 * i * Math.PI / 40;
            double x = rad * Math.cos(alpha);
            double y = rad * Math.sin(alpha);
            gl.glVertex2d(x + center.x, y + center.y);
        }
        gl.glEnd();
    }
}