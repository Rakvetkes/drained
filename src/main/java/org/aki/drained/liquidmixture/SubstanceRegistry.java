package org.aki.drained.liquidmixture;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

public class SubstanceRegistry {

    public static SubstanceRegistry INSTANCE = new SubstanceRegistry();

    private Object2ObjectLinkedOpenHashMap<SubstanceUnit, String> nameMap;
    private Object2ObjectLinkedOpenHashMap<String, SubstanceUnit> substanceMap;

    private SubstanceRegistry() {
        nameMap = new Object2ObjectLinkedOpenHashMap<>();
        substanceMap = new Object2ObjectLinkedOpenHashMap<>();
    }

    public SubstanceUnit register(SubstanceUnit substance, String name) {
        nameMap.put(substance, name);
        substanceMap.put(name, substance);
        return substance;
    }

    public SubstanceUnit get(String name) {
        return substanceMap.get(name);
    }

    public String getName(SubstanceUnit substance) {
        return nameMap.get(substance);
    }

}
