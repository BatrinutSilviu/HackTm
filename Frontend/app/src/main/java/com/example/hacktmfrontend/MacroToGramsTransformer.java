package com.example.hacktmfrontend;

public class MacroToGramsTransformer {
    public enum Macros{Protein, Carb, Fat};

    public int transformToGrams(Macros macroType, int percentage, int calories)
    {
        switch(macroType){
            case Carb:
            case Protein:{
//                return (float) (percentage/100) * 4 * calories;
            }
            case Fat:{
                break;
            }
        }
        return 1;
    }
}
