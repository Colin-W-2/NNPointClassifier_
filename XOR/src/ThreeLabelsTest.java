import processing.core.PApplet;
import library.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class ThreeLabelsTest extends PApplet{
    public static void main(String[] args) {PApplet.main("ThreeLabelsTest");}
    public void settings(){size(800, 800);}
    // -----------------------------------------------------------------------------------------------------------------
    ArrayList<dataPoint> datas = new ArrayList<>();
    FileReader reader;
    BufferedReader bufferedreader;
    Scanner inputStream;
    String fileName;
    File file;
    List<List<String>> lines = new ArrayList<>();
    ArrayList<Float> globalErrors = new ArrayList<>();
    NN.NeuralNetwork net;
    int res = 10;
    PrintWriter writer;
    int time = millis();
    public float[] normalizeData(float[] l, float max) {
        float[] result = new float[l.length];
        for(int i=0;i<l.length;i++) {
            result[i]=l[i]/max;
        }
        return  result;
    }
    public void addErrorValue() {
        float Err=0;
        for(int i=0;i<3;i++) {
            Err+=Math.abs(net.errors.data[net.shape.data.length-1][i]);
        }
        globalErrors.add(Err/3);
    }
    public void trainNN() {

        for(int i=0;i<datas.size();i++) {
            net.feedForward(normalizeData(new float[]{datas.get(i).x, datas.get(i).y}, 800));
            net.backPropogation(datas.get(i).nnLabel, (float) -0.001);
        }
    }
    public void exit() {
        try {
            writer = new PrintWriter("errors.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       for(int i=0;i<globalErrors.size();i++) {
           writer.println();
           writer.print(i);
           writer.print(",");
           writer.print(globalErrors.get(i));
       }
        writer.flush();
        writer.close();
    }
    public void setup() {
        fileName="C://Users//colin//IdeaProjects//XOR//outputData.txt";
        file = new File(fileName);
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }
            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<lines.size();i++) {
            datas.add(new dataPoint(Float.valueOf(lines.get(i).get(0)), Float.valueOf(lines.get(i).get(1)), Float.valueOf(lines.get(i).get(2))));
        }
        net = new NN().new NeuralNetwork(new int[]{2,3,3,3,3}, "sigmoid");
    }
    public void mousePressed() {
        start=true;
    }
    boolean start=false;
    public void draw() {
        if(start) {
            for(int i=0;i<600;i++) {
                trainNN();
            }
            if(time+50<=millis()) {
                addErrorValue();
                time=millis();
            }
            for(int i=0;i*res<width;i++) {
                for(int j=0;j*res<height;j++) {
                    net.feedForward(normalizeData(new float[]{i*res, j*res}, 800));
                    noStroke();
                    fill(net.shape.data[net.shape.data.length-1][0]*255, net.shape.data[net.shape.data.length-1][1]*255,net.shape.data[net.shape.data.length-1][2]*255);
                    rect(i*res, j*res, res, res);
                }
            }
            for(int i=0;i<datas.size();i++) {
                stroke(0);
                noFill();
                ellipse(datas.get(i).x, datas.get(i).y, 12, 12);
                textSize(16);
                fill(255, 0 ,0);
                text((int)datas.get(i).label, datas.get(i).x, datas.get(i).y);
            }
        }

    }

    public class dataPoint {
        float x;
        float y;
        float label;
        float[] nnLabel;
        dataPoint(float x_, float y_, float l) {
            nnLabel = new float[3];
            x=x_;
            y=y_;
            label=l;
            nnLabel[(int) (l)]=1;
        }
    }
}
