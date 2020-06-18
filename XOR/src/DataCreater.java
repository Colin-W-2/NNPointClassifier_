import processing.core.PApplet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import library.*;
public class DataCreater extends PApplet{
    public static void main(String[] args) {PApplet.main("DataCreater");}
    public void settings() {size(800, 800);}
    // -----------------------------------------------------------------------------------------------------------------
    Scanner inputScanner = new Scanner(System.in);
    int numOfClasses;
    ArrayList<dataPoint> datas;
    PrintWriter writer;
    public void setup() {
        System.out.println("Nunmber of classes: ");
        numOfClasses = inputScanner.nextInt();
        onKey=0;
        datas = new ArrayList<>();
    }
    int onKey;
    public void draw() {
        for(int i=0;i<datas.size();i++) {
            datas.get(i).draw();
        }
    }
    public void keyPressed() {
        onKey++;
    }
    public void exit() {
        try {
            writer = new PrintWriter("outputData.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<datas.size();i++){
            writer.println();
            for(int j=0;j<datas.get(i).data.length;j++){
                writer.print(datas.get(i).data[j] + ",");
            }
            writer.print(datas.get(i).label);

        }
        writer.flush();
        writer.close();
    }
    public void mousePressed() {
        datas.add(new dataPoint(new float[]{mouseX, mouseY}, onKey));
    }
    public class dataPoint {
        float[] data;
        int label;
        float[] nnLabel;
        dataPoint(float[] inData, int labell) {
            nnLabel = new float[numOfClasses];
            data=inData;
            label=labell;
            nnLabel[labell] =1;
        }
        public void draw() {
            noFill();
            stroke(0);
            ellipse(data[0], data[1], 32, 32);
            textSize(10);
            text(label, data[0]+8,data[1]+8);
        }
    }
}
