package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Level
{
    private final double height;
    private final double width;
    private int live = 3;
    private int score = 0;
    private String name;

    private Objects[] obj;
    private Manic manic;
    private Mines[] mines;
    private DrawSimulate[] ds;
    private Monster[] monsters;
    private Key[] keys;
    private MinesRotate[] minesR;

    public Level(double width, double height){
        this.width=width;
        this.height=height;

        obj = new Objects[]
                {
                        new Objects(this, new Point2D(0, 0), new Point2D(800, 20)),
                        new Objects(this, new Point2D(0, 480),new Point2D(800, 20)),
                        new Objects(this, new Point2D(0, 0),new Point2D(20, 500)),
                        new Objects(this, new Point2D(780, 0),new Point2D(20, 500)),
                        //dal palky
                        new Objects(this, new Point2D(200, 400),new Point2D(600, 15)),
                        new Objects(this, new Point2D(0, 290),new Point2D(500, 15)),
                        new Objects(this, new Point2D(600, 385),new Point2D(200, 15)),
                        new Objects(this, new Point2D(700, 305),new Point2D(100, 15)),
                        new Objects(this, new Point2D(550, 270),new Point2D(100, 15)),
                        new Objects(this, new Point2D(200, 180),new Point2D(600, 15)),
                        new Objects(this, new Point2D(0, 230),new Point2D(120, 15)),
                        new Objects(this, new Point2D(0, 150),new Point2D(100, 15)),
                        new Objects(this, new Point2D(500, 90),new Point2D(300 , 15)),
                        new Objects(this, new Point2D(380, 165),new Point2D(50, 15)),
                        new Objects(this, new Point2D(330, 100),new Point2D(90, 15)),
                        new Objects(this, new Point2D(110, 30),new Point2D(70, 15)),
                };
        manic = new Manic(this,new Point2D(20,440), new Point2D(25,40));
        mines = new Mines[]
                {
                        new Mines(this, new Point2D(575, 365), new Point2D(30,40)),
                        new Mines(this, new Point2D(755, 350), new Point2D(30,40)),
                        new Mines(this, new Point2D(755, 270), new Point2D(30,40)),
                        new Mines(this, new Point2D(15, 195), new Point2D(30,40)),
                        new Mines(this, new Point2D(415, 150), new Point2D(15,20)),
                        new Mines(this, new Point2D(355, 145), new Point2D(30,40)),
                        new Mines(this, new Point2D(400, 255), new Point2D(30,40))
                };
        monsters = new Monster[]
                {
                        new Monster(this, new Point2D(250,245), new Point2D(40,40), new Point2D(50,0),20, 350),
                        new Monster(this, new Point2D(600,135), new Point2D(40,40), new Point2D(50,0),470, 700),
                };
        keys = new Key[]
                {
                        new Key(this, new Point2D(530,30), new Point2D(15,30)),
                        new Key(this, new Point2D(600,210), new Point2D(15,30)),
                        new Key(this, new Point2D(350,350), new Point2D(15,30)),
                        new Key(this, new Point2D(75,180), new Point2D(15,30)),
                        new Key(this, new Point2D(250,100), new Point2D(15,30)),
                        new Key(this, new Point2D(480,140), new Point2D(15,30)),
                        new Key(this, new Point2D(650,120), new Point2D(15,30)),
                };
        minesR = new MinesRotate[]
                {
                        new MinesRotate(this, new Point2D(300,15), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(20,15), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(700,15), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(720,20), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(740,15), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(100,300), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(450,300), new Point2D(20,27)),
                        new MinesRotate(this, new Point2D(200,185), new Point2D(20,27)),
                        new MinesRotate(this, new Point2D(130,40), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(150,35), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(110,35), new Point2D(30,40)),
                        new MinesRotate(this, new Point2D(740,190), new Point2D(30,40)),
                };

        ds = new DrawSimulate[obj.length + mines.length + monsters.length + keys.length + minesR.length + 1];
        ds[0] = manic;
        for (int i = 0; i < obj.length; i++) {
            ds[i + 1] = obj[i];
        }
        for (int i = 0; i < mines.length; i++) {
            ds[obj.length + i + 1] = mines[i];
        }
        for (int i = 0; i < monsters.length; i++){
            ds[obj.length + mines.length + i + 1] = monsters[i];
        }
        for (int i = 0; i < keys.length; i++){
            ds[obj.length + mines.length + monsters.length + i + 1] = keys[i];
        }
        for (int i = 0; i < minesR.length; i++){
            ds[obj.length + mines.length + monsters.length + keys.length + i + 1] = minesR[i];
        }
    }

    public void draw(GraphicsContext gc){
        gc.clearRect(0,0,width, height);

        gc.setFill(Color.rgb(20,20,20));
        gc.fillRect(0, 0, width, height);

        for(DrawSimulate ds: ds){
            if (ds != null) {
                ds.draw(gc);
            }
        }

        printScores(gc);
    }

    public void printScores(GraphicsContext gc){
        String liveText;
        if (live == 3) {
            liveText = "LIVE: ❤ ❤ ❤";
        }
        else if (live == 2) {
            liveText = "LIVE: ❤ ❤   ";
        }
        else if (live == 1) {
            liveText = "LIVE: ❤      ";
        }
        else {
            liveText = "LIVE:         ";
        }

        gc.setFont(new Font("Verdana", 18));
        gc.setFill(Color.WHITE);
        gc.fillText("SCORE:" + "0" + score + "00" , 600, 455);
        gc.fillText(liveText, 450, 455);
    }


    public void simulate(double deltaT){
        for (DrawSimulate ds : ds) {
            if (ds != null) {
                ds.simulate(deltaT);
            }
        }
    }

    public double getHeight(){
        return height;
    }

    public double getWidth(){
        return width;
    }

    public Manic getManic(){
        return this.manic;
    }

    public Objects[] getPlatforms() {
        return this.obj;
    }

    public void handleCollision() {
        live--;
        System.out.println("-live");
        manic.setPosition(new Point2D(20, 440));
    }

    public void countScore(){
        score++;
        System.out.println("+score");
    }

    public void removeObject(DrawSimulate object) {
        for (int i = 0; i < ds.length; i++) {
            if (ds[i] == object) {
                ds[i] = null;
                break;
            }
        }
    }

    public int getScore(){
        return this.score;
    }

    public int getLive(){
        return this.live;
    }

    public Result getResult(){
        return new Result(name, score);
    }

    public void setName(String name){
        this.name = name;
    }

}