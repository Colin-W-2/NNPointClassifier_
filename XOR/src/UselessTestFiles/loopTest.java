package UselessTestFiles;

import processing.core.PApplet;
public class loopTest extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Otus.loopTest");
    }
    public int binarySearch(int[] arr, int b, int e, int key) throws InterruptedException {
            delay(1);
            int mid=b+(e-b)/2;
            if(arr[mid]==key) {return mid;}
            if(arr[mid]>key) {
                return binarySearch(arr,b,mid-1,key);
            }
            if(arr[mid]<key) {
                return binarySearch(arr, mid+1,e,key);
            }
            return -1;
    }
    public void settings() {size(200,200);}
    public void setup() {
        int[] list = new int[2000];
        for(int i=0;i<list.length;i++) {
            list[i]=i;
        }
        int target=1999;
        int time=millis();
        int guess = 0;
        try {
            guess = binarySearch(list, 0,list.length-1,target);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Answer: " + target);
        System.out.println("Time(Binary): " + (millis()-time));
        time = millis();
        //int target = list[(int) (Math.random()*(2000))];


        for(int i=0;i<list.length;i++) {

            delay(1);
            if(target==list[i]) {
                System.out.println("Time(Linear): " + (millis()-time));

                break;
            }
        }


    }
}
