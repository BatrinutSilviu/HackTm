package com.example.hacktmfrontend;

enum Macros{Protein, Carb, Fat};

public class MacroToGramsTransformer {

    public static int transformToGrams(Macros macroType, int percentage, int calories)
    {
        switch(macroType){
            case Carb:
            case Protein:{
                return (percentage * calories)/400;
            }
            case Fat:{
                return (percentage * calories)/900;
            }
            default:{
                return 0;
            }
        }
    }
}
