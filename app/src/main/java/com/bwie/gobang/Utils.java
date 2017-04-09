package com.bwie.gobang;

import android.graphics.Point;
import android.util.Log;

import java.util.List;

/**
 * 作者：郭传沛 on 2017/4/8 15:30
 * 邮箱：bestyourselfgcp@163.com
 * 类用途:五子棋逻辑工具类
 */

public class Utils {
    public Utils() {
    }

    public int MAX_COUNT_LINE = 5;//连珠最大值为五就赢

    //判断x,y位置的棋子是否横向相邻的一致
    public Boolean checkHorizontal(int x, int y, List<Point> whiteArray) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            Log.i("TAG", "x-i=" + (x - i) + "----" + y);
            if (whiteArray.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        //右
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            Log.i("TAG", "x+i=" + (x + i) + "----" + y);
            if (whiteArray.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        return false;
    }

    //判断x,y位置的棋子是否纵向相邻的一致
    public Boolean checkVerictal(int x, int y, List<Point> whiteArray) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            Log.i("TAG", "y-i=" + (y - i) + "----" + y);
            if (whiteArray.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        //右
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            Log.i("TAG", "y+i=" + (y - i) + "----" + y);
            if (whiteArray.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        return false;
    }

    //左斜
    public Boolean checkleftDiagonal(int x, int y, List<Point> whiteArray) {
        int count = 1;
        //左下
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            if (whiteArray.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        //右上
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            if (whiteArray.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        return false;
    }

    //右斜
    public Boolean checkrightDiagonal(int x, int y, List<Point> whiteArray) {
        int count = 1;
        //左下
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            if (whiteArray.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        //右上
        for (int i = 1; i < MAX_COUNT_LINE; i++) {
            if (whiteArray.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_LINE) return true;
        return false;
    }
}
