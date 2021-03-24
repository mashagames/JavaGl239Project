package problem.Figures;

/**
 * Класс угол
 */

public class Angle {
    public double centerx, centery;
    public double alpha1, alpha2;
    public Angle() {
        centerx = Math.random()*2-1;
        centery = Math.random()*2-1;
        alpha1 = Math.random()*Math.PI*2;
        alpha2 = Math.random()*Math.PI/2+alpha1;
        if(alpha2>2*Math.PI) {
            alpha2-=2*Math.PI;
        }
    }
    public Angle(double centerx, double centery, double alpha1, double alpha2) {
        this.centerx = centerx;
        this.centery = centery;
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
    }
}

