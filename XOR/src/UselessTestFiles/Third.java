package UselessTestFiles;

import processing.core.PApplet;
import java.util.ArrayList;
import library.NN;
public class Third extends  PApplet{
    public static void main(String[] args) {PApplet.main("Otus.Third");}
    public void settings() {size(800, 800);}
    // ---------------------------------------------------
    ArrayList<point> points = new ArrayList<point>();
    point p;
    NN.NeuralNetwork nn;
    int time=millis();
    float[] resultt;
    public float[] normalizeData(float[] l, float max) {
        float[] result = new float[l.length];
        for(int i=0;i<l.length;i++) {
            result[i]=l[i]/max;
        }
        return  result;
    }
    public void setup() {
        nn = new NN().new NeuralNetwork(new int[]{2, 4, 5, 4}, "sigmoid");
        nn.shape.print();
        for(int i=0; i<600;i++) {
            p = new point();
            points.add(p);
        }
        for(int j=0;j<800;j++) {
            //trainNN();
        }
    }
    public void trainNN() {
        for(int i=0;i<points.size();i++) {
            nn.feedForward(normalizeData(new float[] {points.get(i).x, points.get(i).y}, 800));
            nn.backPropogation(points.get(i).nnLabel, -0.05f);
        }
    }
    public void draw() {
        background(125);



        trainNN();
        float res=8;
        float colss = width/res;
        float rowss = height/res;
        for(int v=0;v<rowss;v++) {
            for(int j=0;j<colss;j++) {
                nn.feedForward(normalizeData(new float[] {v*res, j*res}, 800));
                resultt = nn.shape.data[nn.shape.data.length-1];
                noStroke();
                fill(resultt[0]*255, resultt[1]*255, resultt[2]*255);
                rect(v*res, j*res, res, res);
            }
        }
        stroke(0);
        line(0, 0, width, height);
        line(0, width,height, 0);
        for(int i=0;i<points.size();i++) {
            points.get(i).draw();
        }
    }

    public void mousePressed() {
        nn.feedForward(normalizeData(new float[] {mouseX, mouseY}, 800));
        for(int i=0;i<nn.shape.data[nn.shape.data.length-1].length;i++) {
            System.out.println("Output at output index  " + i + " with value: " + nn.shape.data[nn.shape.data.length-1][i]);
        }
    }

    public float sigmoid(float x) {
        return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
    }
    public float dsigmoid(float n) {return n/1-n;}
    public class list {
        int rows;
        int cols;
        float[][] data;
        list(int r, int c) {
            rows=r;
            cols=c;
            data = new float[rows][cols];
        }
        list(int[] shape) {
            data = new float[shape.length][];
            for(int i=0;i<shape.length;i++) {
                data[i]=new float[shape[i]];
            }
        }
        public void initializeValues(int min, int max) {
            for(int i=0;i<data.length;i++) {
                for(int j=0;j<data[i].length;j++) {
                    data[i][j]=random(min, max);
                }
            }
        }
        public void addCol(int i, int n) {
            // Clears entire row!
            data[i] = new float[n];
        }
        public void multiplyScalar(float n) {
            for(int i=0;i<data.length;i++) {
                for(int j=0;j<data[i].length;j++) {
                    data[i][j]*=n;
                }
            }
        }
        public void multiply(list l) {
            try{
                for(int i=0;i<data.length;i++) {
                    for(int j=0;j<data[i].length;j++) {
                        data[i][j]*=l.data[i][j];
                    }
                }

            }catch (IndexOutOfBoundsException e) {
                System.out.println("Not the same shape.");
            }

        }

        public void print() {
            for(int i=0;i<data.length;i++) {
                System.out.println("Row: " + i);
                for(int j=0;j<data[i].length;j++) {
                    System.out.println("Col: " + j + " with value " + data[i][j]);
                }
            }
        }
    }
    public float mapp(float n) {
        return 800-n;
    }
    public  class point {
        float x = random(width);
        float y = random(height);
        int label;
        float[] nnLabel = new float[4];
        public point() {
            if(x>mapp(y)) {
                if(x>y) {
                    label=1;
                    nnLabel[0]=1;
                } else {label=4; nnLabel[1]=1;}

            } else {
                if(y>x) {
                    label = 8;
                    nnLabel[2]=1;
                } else {label = 6; nnLabel[3]=1;}

            }
        }
        public void draw() {
            fill(0, 0, label*25);
            ellipse(this.x, this.y, 12, 12);
        }
    }
}
