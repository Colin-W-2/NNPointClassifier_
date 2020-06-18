package UselessTestFiles;

import processing.core.PApplet;
import library.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FancyPointTest1 extends PApplet{
    public static void main(String[] args) {PApplet.main("Otus.FancyPointTest1");}
    public void settings() {size(800, 800); }
    // -----------------------------------------------------------------------------------------------------------------
    ArrayList<dataPoint> datas = new ArrayList<>();
    FileReader reader;
    BufferedReader bufferedreader;
    Scanner inputStream;
    String fileName;
    File file;
    List<List<String>> lines = new ArrayList<>();
    NN.NeuralNetwork net;
    int res = 10;
    public void trainNN() {
        for(int i=0;i<datas.size();i++){

            net.feedForward(normalizeData(new float[] {datas.get(i).x, datas.get(i).y}, 800));

            net.backPropogation(new float[]{datas.get(i).label}, (float) -0.003);
        }
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
        net = new NN().new NeuralNetwork(new int[]{2,3,4,3,1}, "sigmoid");

    }
    public float[] normalizeData(float[] l, float max) {
        float[] result = new float[l.length];
        for(int i=0;i<l.length;i++) {
            result[i]=l[i]/max;
        }
        return  result;
    }
    int index;

    public void draw() {
        background(100);
        for(int i=0;i<300;i++) {
            trainNN();
        }



        for(int i=0;i*res<width;i++) {
            for(int j=0;j*res<height;j++) {
                net.feedForward(normalizeData(new float[]{i*res, j*res}, 800));
                noStroke();
                fill(net.shape.data[net.shape.data.length-1][0]*255);
                rect(i*res, j*res, res, res);
            }
        }
        for(int i=0;i<datas.size();i++) {
            stroke(0);
            noFill();
            ellipse(datas.get(i).x, datas.get(i).y, 12, 12);
            textSize(16);
            text((int)datas.get(i).label, datas.get(i).x, datas.get(i).y);
        }
    }
    public void mousePressed() {
        net.feedForward(normalizeData(new float[]{mouseX,mouseY}, 800));

        System.out.println(net.shape.data[net.shape.data.length-1][0]);
        System.out.println("Error: " + net.errors.data[net.errors.data.length-1][0]);
    }
    public class dataPoint {
        float x;
        float y;
        float label;
        float[] nnLabel;
        dataPoint(float x_, float y_, float l) {

            x=x_;
            y=y_;
            label=l;

        }
    }
}
