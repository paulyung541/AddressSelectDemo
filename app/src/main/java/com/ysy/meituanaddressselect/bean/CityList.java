package com.ysy.meituanaddressselect.bean;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by yang on 2016/7/26.
 */
public class CityList {
    List<City> list;
    private Position position;

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
    }

    /**
     * 根据list中的数据，给position赋值
     * note:一定要等list有数据了才能使用
     */
    public void computePosition() {
        position = new Position();
        String tmp = "";
        for (int i = 0; i < list.size(); i++) {
            tmp = list.get(i).getPinyin().substring(0, 1);
            switch (tmp) {
                case "B":
                    if (position.pB == 0) {
                        position.pB = i;
                    }
                    break;
                case "C":
                    if (position.pC == 0) {
                        position.pC = i;
                    }
                    break;
                case "D":
                    if (position.pD == 0) {
                        position.pD = i;
                    }
                    break;
                case "E":
                    if (position.pE == 0) {
                        position.pE = i;
                    }
                    break;
                case "F":
                    if (position.pF == 0) {
                        position.pF = i;
                    }
                    break;
                case "G":
                    if (position.pG == 0) {
                        position.pG = i;
                    }
                    break;
                case "H":
                    if (position.pH == 0) {
                        position.pH = i;
                    }
                    break;
                case "J":
                    if (position.pJ == 0) {
                        position.pJ = i;
                    }
                    break;
                case "K":
                    if (position.pK == 0) {
                        position.pK = i;
                    }
                    break;
                case "L":
                    if (position.pL == 0) {
                        position.pL = i;
                    }
                    break;
                case "M":
                    if (position.pM == 0) {
                        position.pM = i;
                    }
                    break;
                case "N":
                    if (position.pN == 0) {
                        position.pN = i;
                    }
                    break;
                case "P":
                    if (position.pP == 0) {
                        position.pP = i;
                    }
                    break;
                case "Q":
                    if (position.pQ == 0) {
                        position.pQ = i;
                    }
                    break;
                case "R":
                    if (position.pR == 0) {
                        position.pR = i;
                    }
                    break;
                case "S":
                    if (position.pS == 0) {
                        position.pS = i;
                    }
                    break;
                case "T":
                    if (position.pT == 0) {
                        position.pT = i;
                    }
                    break;
                case "W":
                    if (position.pW == 0) {
                        position.pW = i;
                    }
                    break;
                case "X":
                    if (position.pX == 0) {
                        position.pX = i;
                    }
                    break;
                case "Y":
                    if (position.pY == 0) {
                        position.pY = i;
                    }
                    break;
                case "Z":
                    if (position.pZ == 0) {
                        position.pZ = i;
                    }
                    break;
            }
        }
    }

    /**
     * 根据字母，获取位置
     * 不觉得从A写到Z很烦吗
     */
    public int getPositionByLetter(String s) {
        Class<Position> clazz = Position.class;
        Field field = null;
        int result = -1;
        try {
            field = clazz.getField("p" + s);
            result = field.getInt(position);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }


    private final class Position {
        public int pA;
        public int pB;
        public int pC;
        public int pD;
        public int pE;
        public int pF;
        public int pG;
        public int pH;
        public int pJ;
        public int pK;
        public int pL;
        public int pM;
        public int pN;
        public int pP;
        public int pQ;
        public int pR;
        public int pS;
        public int pT;
        public int pW;
        public int pX;
        public int pY;
        public int pZ;
    }
}
