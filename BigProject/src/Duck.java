import java.util.Random;

public class Duck implements DuckInterface{
    private double eps = 1e-8;
    private double px;
    private double py;
    private double dx;
    private double dy;
    private double size;
    private int timeNoFood = 0;
    Duck(double x, double y){
        px = x;
        py = y;
        Random r = new Random();
        dx = (r.nextInt() % 100) / 100.0;
        dy = (r.nextInt() % 100) / 100.0;
        size = 1;
    }
    public void changeDir(){
        Random r = new Random();
        dx = (r.nextInt() % 100) / 100.0;
        dy = Math.sqrt(1 - dx * dx) * Math.pow(-1, Math.abs(r.nextInt()) % 2);
    }
    public void chase(Duck d){
        double dir = Math.sqrt((getX() - d.getX()) * (getX() - d.getX())
                + (getY() - d.getY()) * (getY() - d.getY()));
        dx = ((getX() - d.getX())) / dir;
        dx = -1 * dx * dir / 150.0;
        dy = ((getY() - d.getY())) / dir;
        dy = -1 * dy * dir / 150.0;
    }
    public void eat(){
        size = size * 1.2;
        timeNoFood = 0;
    }
    public void setX(double x){
        px = x;
    }
    public double getX(){
        return px;
    }
    public void setY(double y){
        py = y;
    }
    public double getY(){
        return py;
    }
    public void move(){
        px = px + dx;
        py = py + dy;
        timeNoFood = timeNoFood + 1;
    }
    public double getSize(){
        return size;
    }
    public boolean isDead(){
        if(timeNoFood <= 800){
            return false;
        }
        timeNoFood = 0;
        if(Math.abs(size - 1.0) < eps){
            return true;
        }
        size = size / 1.2;
        return false;
    }
    public void setSize(double _size){
        size = _size;
    }
    public void changeDirection(){
        Random r = new Random();
        dx = (r.nextInt() % 100) / 100.0;
        dy = (r.nextInt() % 100) / 100.0;
    }
    public void setDx(double _dx){
        dx = _dx;
    }
    public void setDy(double _dy){
        dy = _dy;
    }
    public double getDx(){
        return dx;
    }
    public double getDy(){
        return dy;
    }
}
