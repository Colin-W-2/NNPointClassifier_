package library;

public class NN {
    public float[] normalizeData(float[] l, float max) {
        float[] result = new float[l.length];
        for(int i=0;i<l.length;i++) {
            result[i]=l[i]/max;
        }
        return  result;
    }
    public float random(float min, float max) {
        return (float) ((Math.random()*((max-min)+1))+min);
    }
    public class NeuralNetwork {
        public list shape;

        public list weights;
        list biases;
        public list errors;
        list deltas;
        list valueChange;
        list derivatives;
        String activaitionFunction;
        public NeuralNetwork(int[] shap, String act) {
            shape = new list(shap);
            biases = new list(shap);
            derivatives = new list(shap);
            errors = new list(shap);
            weights = new list(shap.length, 0);
            for(int i=0;i<weights.data.length-1;i++) {
                weights.addCol(i, shape.data[i].length*shape.data[i+1].length);
            }
            weights.initializeValues(-1, 1);
            biases.initializeValues(-1, 1);
            activaitionFunction=act;
        }
        public void feedForward(float[] in) {
            shape.data[0]=in;
            for(int i=1;i<shape.data.length;i++) {
                for(int j=0;j<shape.data[i].length;j++) {
                    for(int n=0;n<shape.data[i-1].length;n++) {
                        shape.data[i][j]+=shape.data[i-1][n]*weights.data[i-1][(j*shape.data[i-1].length)+n];
                    }
                    if(activaitionFunction=="sigmoid") {
                        shape.data[i][j]=sigmoid(shape.data[i][j]);
                        derivatives.data[i][j] = shape.data[i][j] *(1-shape.data[i][j]);
                    }
                    //if(activaitionFunction=="tanh") {derivatives.data[i][j] = (float) (1-(Math.tanh(shape.data[i][j])*Math.tanh(shape.data[i][j])));shape.data[i][j]= (float) Math.tanh(shape.data[i][j]); }
                    if(activaitionFunction=="tanh") {
                        derivatives.data[i][j]= (float) (1-Math.pow(Math.tanh(shape.data[i][j]), 2));
                        shape.data[i][j]= (float) Math.tanh(shape.data[i][j]);

                    }

                }
            }
        }
        public float returnWeight(int l, int n, int c) {
            return weights.data[l][(n*shape.data[l].length)+c];
        }
        public void backPropogation(float[] answer, float lr) {
            // Calculate the Errors
            for(int i=0;i<shape.data[shape.data.length-1].length;i++) {
                errors.data[shape.data.length-1][i]=(answer[i]-shape.data[shape.data.length-1][i])*derivatives.data[errors.data.length-1][i];
            }
            for(int i=shape.data.length-2;i>0;i--){
                for(int j=0;j<shape.data[i].length;j++) {
                    float sum=0;
                    for(int n=0;n<shape.data[i+1].length;n++) {
                        // Possible point of bug: the thing with finding the correct weight. If bug, test it, or create method
                        //sum+=weights.data[i+1][(j*shape.data[i+1].length)+n]*errors.data[i+1][n];
                        // returnWeight(i+1, j, n)
                        sum+=returnWeight(i, n, j)*errors.data[i+1][n];
                    }
                    errors.data[i][j] = sum*derivatives.data[i][j];
                }
            }
            // --------------------------------------
            for(int i=1;i<shape.data.length;i++) {
                for(int j=0;j<shape.data[i].length;j++) {
                    for(int n=0;n<shape.data[i-1].length;n++) {
                        float delta=0;
                        delta = -lr * shape.data[i-1][n]*errors.data[i][j];
                        // ^^^See above ^^^ , possible weight recall error
                        weights.data[i-1][(j*shape.data[i-1].length)+n]+=delta;
                    }
                    float deltaB = -lr *errors.data[i][j];
                    biases.data[i][j]+=deltaB;
                }
            }
        }
    }
    public float sigmoid(float x) {
        return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
    }

    public float dsigmoid(float n) {return n/1-n;}
    public class list {
        public int rows;
        public int cols;
        public float[][] data;
        public list(int r, int c) {
            rows=r;
            cols=c;
            data = new float[rows][cols];
        }
        public list(int[] shape) {
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
}
