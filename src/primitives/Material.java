package primitives;

public class Material {
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public double getkD() {
        return kD;
    }

    public double getkS() {
        return kS;
    }

    public int getnShininess() {
        return nShininess;
    }

    double kD = 0, kS = 0;
    int nShininess = 0;


}
