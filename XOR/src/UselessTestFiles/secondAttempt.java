package UselessTestFiles;

import library.NN;
import processing.core.PApplet;

import java.util.ArrayList;

public class secondAttempt extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Otus.secondAttempt");
    }
    public void settings() {size(800, 800);}
    // -----------------------------------------------------------------------------------------------------------------
    NN.NeuralNetwork nn;
    ArrayList<dataPoint> datas;
    int trainingSteps=600;
    int res=10;
    float[] resultt;
    public void setup() {
        nn = new NN().new NeuralNetwork(new int[]{2,3,4,3,1}, "sigmoid");
        datas = new ArrayList<>();
        datas.add(new dataPoint(0, 0, 0));
        datas.add(new dataPoint(0, 1, 1));
        datas.add(new dataPoint(1, 0, 1));
        datas.add(new dataPoint(1, 1, 0));
        for(int i=0;i<trainingSteps;i++) {
            train();
        }

    }

    public void train() {
        for(int i=0;i<datas.size();i++) {
            nn.feedForward(new float[]{datas.get(i).x, datas.get(i).y});
            nn.backPropogation(datas.get(i).label, -0.1f);
        }
    }
    public void mousePressed() {
        nn.feedForward(normalizeData(new float[] {mouseX, mouseY}, 800));
        for(int i=0;i<nn.shape.data[nn.shape.data.length-1].length;i++) {
            System.out.println("Output at output index  " + i + " with value: " + nn.shape.data[nn.shape.data.length-1][i]);

        }
        nn.feedForward(new float[] {0, 1});
        System.out.println("Top right Corner: " + nn.shape.data[nn.shape.data.length-1][0]);
    }
    public float[] normalizeData(float[] l, float max) {
        float[] result = new float[l.length];
        for(int i=0;i<l.length;i++) {
            result[i]=l[i]/max;
        }
        return  result;
    }
    public void draw() {
        for(int i=0;i<trainingSteps;i++) {train();}
        float res=10;
        float colss = width/res;
        float rowss = height/res;
        for(int v = 0; v<rowss; v++) {
            for(int j=0;j<colss;j++) {
                nn.feedForward(normalizeData(new float[] {v*res, j*res}, 800));
                resultt = nn.shape.data[nn.shape.data.length-1];
                noStroke();
                if(false) {
                    fill(255, 0, 0);
                } else {
                    fill(resultt[0]*255);
                }
                rect(v*res, j*res, res, res);
            }
        }
    }
    public class dataPoint {
        float x;
        float y;
        float[] label;
        dataPoint(float xPos, float yPos, float answer) {
            label= new float[1];
            x=xPos;
            y=yPos;
            label[0]=answer;
        }
    }
}
