package org.aki.melted.liquidmixture;

public abstract class SubstanceUnit {

    public abstract Spectrum getSpectrum();

    public abstract double getDensity(Environment environment);

    public abstract Status getStatus(Environment environment);

    public enum Status {
        SOLID, LIQUID, GAS;
    }

    public static class Environment {

    }

}
