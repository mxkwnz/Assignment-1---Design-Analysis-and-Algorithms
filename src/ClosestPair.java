import java.util.*;

class ClosestPair {

    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
                (p1.y - p2.y) * (p1.y - p2.y));
    }

    static double bruteForce(Point[] pts, int l, int r) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = l; i < r; i++) {
            for (int j = i + 1; j < r; j++) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    static double stripClosest(List<Point> strip, double d) {
        double min = d;
        strip.sort(Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; j++) {
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }

    static double closestUtil(Point[] pts, int l, int r) {
        if (r - l <= 3) return bruteForce(pts, l, r);

        int mid = (l + r) / 2;
        double dl = closestUtil(pts, l, mid);
        double dr = closestUtil(pts, mid, r);
        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        double midX = pts[mid].x;
        for (int i = l; i < r; i++) {
            if (Math.abs(pts[i].x - midX) < d) {
                strip.add(pts[i]);
            }
        }

        return Math.min(d, stripClosest(strip, d));
    }

    public static double closest(Point[] pts) {
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        return closestUtil(pts, 0, pts.length);
    }

    public static void main(String[] args) {
        Point[] pts = {
                new Point(2, 3), new Point(12, 30),
                new Point(40, 50), new Point(5, 1),
                new Point(12, 10), new Point(3, 4)
        };

        System.out.println("Минимальное расстояние = " + closest(pts));
    }
}
