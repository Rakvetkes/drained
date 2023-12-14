package org.aki.melted.refactoredfluid;

import org.aki.melted.common.reactor.Reactor;
import org.aki.melted.common.reactor.ReactorManager;

import java.lang.reflect.InvocationTargetException;

public class FluidPackage<T extends RefactoredFluid> {

    public T refStill, refFlowing;

    public FluidPackage(Class<T> fluidClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        refStill = fluidClass.getDeclaredConstructor(Boolean.TYPE).newInstance(false);
        refFlowing = fluidClass.getDeclaredConstructor(Boolean.TYPE).newInstance(true);
        refStill.copyReferences(this);
        refFlowing.copyReferences(this);
        ReactorManager.INSTANCE.registerExchangeable(refStill, refFlowing, refStill::exchange);
    }

}
