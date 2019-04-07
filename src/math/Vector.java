package math;

/**
 * Created by yana on 28.03.2018.
 */
public class Vector {
    private final int n;
    private double[] data;
    private double[] firstDotCoords;
    private double[] secondDotCoords;


   // public Vector(){}

    //setShape zero vector with length n

    public Vector(int n){
        this.n = n;
        this.data = new double[n];
    }

    public Vector(double[] data){
        this.n = data.length;

        this.data = new double[n];

        for (int i = 0; i< n; i++)
            this.data[i] = data[i];
    }
    //to make vector coords from two segment points
    public Vector(double[] firstDotCoords, double[] secondDotCoords){
        if(firstDotCoords.length != secondDotCoords.length)
            throw new IllegalArgumentException("dimensions disagree");
        this.n = firstDotCoords.length;
        this.data = new double[n];
        for (int i = 0; i < n; i++)
            this.data[i] = secondDotCoords[i] - firstDotCoords[i];

    }

    public int length(){
        return n;
    }

    //return the inner product (skalyar)
    public double innerProd(Vector that){
        if(that.length() != this.length())
            throw new IllegalArgumentException("dimensions disagree");
        double sum = 0;
        for(int i = 0; i < n; i++)
            sum = sum + (that.data[i] * this.data[i]);

        return sum;
    }

    //return cross product (vectornoe)
    public Vector crossProd(Vector that){
        if(that.length() != this.length())
            throw new IllegalArgumentException("dimensions disagree");
        if(that.length()> 3 )
            throw new IllegalArgumentException("only 3-dimensional cross product");
        Vector res = new Vector(3);
        res.data[0] = (this.data[1] * that.data[2]) - (this.data[2] * that.data[1]);
        res.data[1] = (this.data[2] * that.data[0]) - (this.data[0] * that.data[2]);
        res.data[2] = (this.data[0] * that.data[1]) - (this.data[1] * that.data[0]);
        return res;
    }



    //return the Euclidean norm of this Vector (modul)
    public double magnitude(){
        return Math.sqrt(this.innerProd(this));

    }

    //return this + that
    public Vector plus( Vector that){
        if(that.length() != this.length())
            throw new IllegalArgumentException("dimensions disagree");
        Vector res = new Vector(n);
        for(int i = 0; i < n; i++)
        res.data[i] = this.data[i] + that.data[i];
        return res;
    }

    //return this - that
    public Vector minus( Vector that){
        if(that.length() != this.length())
            throw new IllegalArgumentException("dimensions disagree");
        Vector res = new Vector(n);
        for(int i = 0; i < n; i++)
            res.data[i] = this.data[i] - that.data[i];
        return res;
    }

    // return the corresponding coordinate
    public double cartesian(int i) {
        return data[i];
    }




}
