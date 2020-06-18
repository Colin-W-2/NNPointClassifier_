package UselessTestFiles;

import processing.core.PApplet;
import library.NN;

import java.util.ArrayList;

public class main extends PApplet{
    public static void main(String[] args) {PApplet.main("Otus.main");}
    public void settings(){size(800, 800);}
    // -----------------------------------------------------------------------------------------------------------------
    NN.NeuralNetwork net;
    float[][][] trainingData;
    ArrayList<dataPoint> datas;
    int trainingSteps=1000;
    int grid=10;
    public float[] normalizeData(float[] l, float max) {
        float[] result = new float[l.length];
        for(int i=0;i<l.length;i++) {
            result[i]=l[i]/max;
        }
        return result;
    }
    public void setup() {
        datas = new ArrayList<dataPoint>();
        net = new NN().new NeuralNetwork(new int[]{2, 2, 1}, "sigmoid");
        datas.add(new dataPoint(new float[]{0, 0}, 0));
        datas.add(new dataPoint(new float[]{0, 1}, 1));
        datas.add(new dataPoint(new float[]{1, 0}, 1));
        datas.add(new dataPoint(new float[]{1, 1}, 0));
        trainingData = new float[4][2][1];
        // -----------------------------------------------------------------------------------------------------------
        trainingData[0] = new float[][]{{0, 0}, {0}};
        trainingData[1] = new float[][]{{0, 1}, {1}};
        trainingData[2] = new float[][]{{1, 0}, {1}};
        trainingData[3] = new float[][]{{1, 1}, {0}};

    }
    public class dataPoint {
        float[] data;
        int label;
        float[] nnLabel;
        dataPoint(float[] pos, int n) {
            data =pos;
            label=n;
            nnLabel = new float[]{n};
        }

    }
    public void trainNN() {
        int index = (int) random(datas.size()-1);
        for(int i=0;i<datas.size();i++) {
            net.feedForward(datas.get(index).data);
//            System.out.println("Coord 1: " +datas.get(i).data[0]);
//            System.out.println("Cord 2: " + datas.get(i).data[1]);
//            System.out.println("Label: " + datas.get(i).label);
            net.backPropogation(datas.get(index).nnLabel, -0.05f);
        }
    }
    public void draw() {
        background(0);
        trainNN();
        for(int i=0;i*grid<width;i++) {
            for(int j=0;j*grid<height;j++) {
                net.feedForward(normalizeData(new float[]{i*grid,j*grid}, 800));
                noStroke();
                if(i==0) {
                    fill(255, 0, 0);
                } else {
                    fill(net.shape.data[net.shape.data.length-1][0]*255);
                }
                rect(i*grid, j*grid, grid, grid);
            }
        }
    }
}
