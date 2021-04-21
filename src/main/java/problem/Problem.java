package problem;

import problem.Figures;
import problem.Angle;


import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Класс задачи
 */

public class Problem {
    /**
     * текст задачи
     */
    public static final String PROBLEM_TEXT = "ПОСТАНОВКА ЗАДАЧИ:\n" +
            "На плоскости задано множество \"острых углов\". Найти такие два \"острых угла\", что фигура,\n" +
            " находящаяся \"внутри\" обоих \"острых углов\" замкнута и имеет максимальную площадь.\n";

    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученицы 10-7 Кучиной Марии";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "angles.txt";

    /**
     * список углов
     */
    private ArrayList<Angle> angles;

    Vector2 maxA, maxB, maxC, maxD;
    int a1sol = -1, a2sol = -1;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        angles = new ArrayList<>();
    }

    /**
     * Добавить угол
     *
     * @param a      координата центра угла
     * @param alpha1, alpha2      начальный и конечный угол
     */

    public void addAngle(Vector2 a, double alpha1, double alpha2) {
        Angle angle = new Angle(a, alpha1, alpha2);
        angles.add(angle);
    }

    /**
     * Решить задачу
     */
    public Vector2 cross(Vector2 a, Vector2 b, double alpha1, double alpha2) {
        Vector2 v = new Vector2(-100, -100);
        if (Math.sin(alpha2)-Math.cos(alpha2)*Math.tan(alpha1) !=0) {
            double l2 = (a.y + Math.tan(alpha1) * (b.x - a.x) - b.y) / (Math.sin(alpha2) - Math.cos(alpha2) * Math.tan(alpha1));
            double l1 = (b.x - a.x + l2 * Math.cos(alpha2)) / Math.cos(alpha1);
            if ((l1 > 0) && (l2 > 0)) {
                v = new Vector2(l2 * Math.cos(alpha2) + b.x, l2 * Math.sin(alpha2) + b.y);
                if ((v.x > -1) && (v.x < 1) && (v.y>-1) && (v.y<1)) {
                    return v;
                } else v.x = -100;
            }
        }
        return v;
    }

    public double getlength(Vector2 a, Vector2 b) {
        return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }

    public double getSqare(Vector2 a, Vector2 b, Vector2 c) {
        double p = (getlength(a, b) + getlength(b, c) + getlength(a,c))/2;
        double s = Math.sqrt(Math.abs(p*(p-getlength(a,b))*(p-getlength(b,c))*(p-getlength(a,c))));
        return s;
    }

    public void solve() {
        double sMax = 0;
        // перебираем пары углов
        for (int i=0;i<angles.size();i++) {
            for (int j=i+1;j<angles.size();j++){
                Angle a = angles.get(i);
                Angle b = angles.get(j);
                // если углы являются разными
                if (a != b) {
                    // поиск точек пересечения
                    Vector2 c1, c2, c3, c4, cMax, cMin;
                    double l1=0, l2=0, l3=0, l4=0, lMin=100, lMax=0;
                    int beam11 = 0; int beam12 = 0; int beam21 = 0; int beam22 = 0;

                    c1 = cross (a.c, b.c, a.alpha1, b.alpha1);
                    cMin = c1;
                    cMax = c1;
                    if (c1.x!=-100) {
                        l1 = getlength(c1, a.c)+getlength(c1,b.c);
                        lMin = l1;
                        lMax = l1;
                        beam11 ++;
                        beam21 ++;
                    }
                    c2 = cross(a.c, b.c, a.alpha1, b.alpha2);
                    if (c2.x!=-100) {
                        l2 = getlength(c2, a.c)+getlength(c2,b.c);
                        if(l2>lMax) {lMax=l2; cMax = c2; }
                        if(l2<lMin) {lMin=l2; cMin = c2; }
                        beam11 ++;
                        beam22 ++;
                    }
                    c3 = cross(a.c, b.c, a.alpha2, b.alpha1);
                    if (c3.x!=-100) {
                        l3 = getlength(c3, a.c)+getlength(c3,b.c);
                        if(l3>lMax) {lMax=l3; cMax = c3; }
                        if(l3<lMin) {lMin=l3; cMin = c3; }
                        beam12++;
                        beam21++;
                    }
                    c4 = cross(a.c, b.c, a.alpha2, b.alpha2);
                    if (c4.x!=-100) {
                        l4 = getlength(c4, a.c)+getlength(c4,b.c);
                        if(l4>lMax) { cMax = c4; }
                        if(l4<lMin) { cMin = c4; }
                        beam12++;
                        beam22++;
                    }
                    // если пересекаются - вычисление площади, если она больше предыдущего максимума - запоминаем точки пересечения и площадь
                        if ((beam11 > 0) && (beam12 > 0) && (beam21 > 0) && (beam22 > 0) && (beam11+beam12+beam21+beam22)%4==0) {
                            double s = getSqare(a.c, b.c, cMax) + getSqare(a.c, b.c, cMin);
                            if (s > sMax) {
                                sMax = s;
                                a1sol = i;
                                a2sol = j;
                                maxA = a.c;
                                maxB = b.c;
                                maxC = cMax;
                                maxD = cMin;
                            }
                    }
                }
            }
        }
    }


    /**
     * Загрузить задачу из файла
     */
    public void loadFromFile() {
        angles.clear();
        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double centerx = sc.nextDouble();
                double centery = sc.nextDouble();
                double alpha1 = sc.nextDouble();
                double alpha2 = sc.nextDouble();
                Vector2 a = new Vector2(centerx, centery);
                Angle angle = new Angle(a, alpha1, alpha2);
                angles.add(angle);
                sc.nextLine();
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    /**
     * Сохранить задачу в файл
     */
    public void saveToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME));
            for (Angle angle : angles) {
                out.printf("%.2f %.2f %.2f %.2f\n", angle.c.x, angle.c.y, angle.alpha1, angle.alpha2);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    /**
     * Добавить заданное число случайных углов
     *
     * @param n кол-во углов
     */
    public void addRandomAngles(int n) {
        for (int i = 0; i < n; i++) {
            Angle angle = Angle.getRandomAngle();
            angles.add(angle);
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        angles.clear();
        maxA = null;
        maxB = null;
        maxC = null;
        maxD = null;
        a1sol = -1;
        a2sol = -1;
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {

/*        gl.glColor3d(1.0, 1.0, 1.0);

        Vector2 posA = new Vector2(0.1, 0.2);
        Figures.renderPoint(gl, posA, 5);
 */


/*        gl.glColor3d(1.0, 1.0, 1.0);

        Vector2 posA = new Vector2(0.1, 0.2);
        Vector2 posB = new Vector2(0.5, 0.7);
        Figures.renderLine(gl, posA, posB,5);
 */


/*        gl.glColor3d(1.0, 1.0, 1.0);

        Vector2 posA = new Vector2(0.1, 0.2);
        Vector2 posB = new Vector2(0.5, 0.7);
        Vector2 posC = new Vector2(0.4, 0.3);
        Figures.renderTriangle(gl, posA, posB, posC, true);
 */


/*        gl.glColor3d(1.0, 1.0, 1.0);

        Vector2 posA = new Vector2(0.1, 0.1);
        Vector2 posB = new Vector2(0.6, 0.1);
        Vector2 posC = new Vector2(0.6, 0.6);
        Vector2 posD = new Vector2(0.1, 0.6);
        Figures.renderQuads(gl, posA, posB, posC, posD,false);
 */


/*        gl.glColor3d(1.0, 1.0, 1.0);
        Vector2 posA = new Vector2(0.1, 0.2);
        Figures.renderCircle(gl, posA,0.2, false);
 */

// Выделение области цветом
        gl.glLineWidth(1);
        gl.glColor3d(1.0, 0.0, 0.0);
        if (maxA != null) {
            Figures.renderQuads(gl, maxA, maxB, maxC, maxD, true);
        }
// Выделение контура цветом
        gl.glColor3d(0, 1, 0);
        gl.glLineWidth(3);
        if (maxA != null) {
            Figures.renderQuads(gl, maxA, maxB, maxC, maxD, false);
        }
// Отрисовка всех углов
        gl.glLineWidth(1);
        gl.glColor3d(1.0, 1.0, 1.0);
        for (int i=0;i<angles.size();i++) {
            angles.get(i).render(gl);
        }
// Выделение найденых углов
        gl.glLineWidth(3);
        gl.glColor3d(0.0, 0.0, 1.0);
        if (a1sol!=-1) {
            angles.get(a1sol).render(gl);
        }
        if (a2sol!=-1) {
            angles.get(a2sol).render(gl);
        }
    }
}
