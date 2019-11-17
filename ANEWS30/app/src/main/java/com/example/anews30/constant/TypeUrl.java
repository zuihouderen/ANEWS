package com.example.anews30.constant;

public class TypeUrl {
    public static String getTypeUrl(int type){
        switch (type){
            case 0:
                return "auto";
            case 1:
                return "house";
            case 2:
                return "ai";
            case 3:
                return "military";
            case 4:
                return "game";
            case 5:
                return "social";
            case 6:
                return "world";
            default:
                return "tiyu";
        }
    }
}
