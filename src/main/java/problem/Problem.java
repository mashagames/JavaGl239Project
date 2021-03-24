package problem;

import problem.Figures.Figures;
import problem.Figures.Angle;

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
            "Заданы два множества точек в пространстве.\n" +
            "Требуется построить пересечения и разность этих множеств";

    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученицы 10-7 Кучиной Марии";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "points.txt";

    /**
     * список точек
     */
    private ArrayList<Point> points;
    private ArrayList<Angle> angles;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
        angles = new ArrayList<>();
        Angle angle = new Angle();
        angles.add(angle);
        angle = new Angle();
        angles.add(angle);

    }

    /**
     * Добавить точку
     *
     * @param x      координата X точки
     * @param y      координата Y точки
     * @param setVal номер множества
     */

    public void addAngle(double centerx, double centery, double alpha1, double alpha2) {
        Angle angle = new Angle(centerx, centery, alpha1, alpha2);
        angles.add(angle);
    }

    /**
     * Решить задачу
     */
    public void solve() {
        // перебираем пары точек
        for (Point p : points) {
            for (Point p2 : points) {
                // если точки являются разными
                if (p != p2) {
                    // если координаты у них совпадают
                    if (Math.abs(p.x - p2.x) < 0.0001 && Math.abs(p.y - p2.y) < 0.0001) {
                        p.isSolution = true;
                        p2.isSolution = true;
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
                sc.nextLine();
                Angle angle = new Angle(centerx, centery, alpha1, alpha2);
                angles.add(angle);
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
                out.printf("%.2f %.2f %.2f %.2f\n", angle.centerx, angle.centery, angle.alpha1, angle.alpha2);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    /**
     * Добавить заданное число случайных точек
     *
     * @param n кол-во точек
     */
    public void addRandomPoints(int n) {
        for (int i = 0; i < n; i++) {
            Angle a = new Angle();
            angles.add(a);
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        angles.clear();
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {
//        for (Point point : points) {
//            point.render(gl);
//        }
//        Figures.renderQuads(gl,-1,1,1,-1,-1, -1,1,1,false);
        Vector2 posA = new Vector2(0.1, 0.2), posB = new Vector2(-0.1, 0.6);
        Figures.renderLine(gl, posA, posB,5);
        //Figures.renderPoint(gl,0.3,0.2,4);
        //Figures.renderPoint(gl,-0.1,0.2,3);
        //Figures.renderTriangle(gl, 0.1, 0.2, 0.3, 0.2, -0.1, 0.2, true);

        for (int i=0;i<angles.size();i++) {
            Figures.renderAngle(gl, angles.get(i));
        }
//        Figures.renderCircle(gl, 0.2, 0, 0.2, false);
    }
}
