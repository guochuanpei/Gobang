package com.bwie.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：郭传沛 on 2017/4/5 18:30
 * 邮箱：bestyourselfgcp@163.com
 * 类用途:
 */

public class CustomView extends View implements MainActivity.Repaint {
    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;
    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;
    private Bitmap mBlockPiece;
    private float ratioPieceofLineHeight = 3 * 1.0f / 4;//设置BitMap的比例
    //判断白棋先手还是当前轮到白棋
    private boolean mIsWhite = true;
    private List<Point> mWhiteArray = new ArrayList<>();
    private List<Point> mBlockArray = new ArrayList<>();
    private boolean mIsGameOver;//游戏结束了
    private boolean mIsWhiteWinner;//为true就是白棋赢
    private Utils mUtils = new Utils();

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setDither(true);//设置防抖动
        mPaint.setStyle(Paint.Style.STROKE);
        //初始化棋子图片
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);//白
        mBlockPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);//黑

        //再来一局操作
        MainActivity.setRepaint(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;//宽
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;//行高
        int pieceWidth = (int) (mLineHeight * ratioPieceofLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlockPiece = Bitmap.createScaledBitmap(mBlockPiece, pieceWidth, pieceWidth, false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);//画棋盘
        Log.i("TAG", "集合长度" + mWhiteArray.size() + mBlockArray.size());
        drawPiece(canvas);//画棋子
        checkGameOver();
    }

    private void checkGameOver() {
        Boolean whiteWin = checkFiveInLine(mWhiteArray);
        Boolean blackWin = checkFiveInLine(mBlockArray);
        if (whiteWin || blackWin) {
            //不论whiteWin和blackWin那个为true都结束游戏
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;
            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
        if (mWhiteArray.size() + mBlockArray.size() == MAX_LINE * MAX_LINE) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;
            Toast.makeText(getContext(), "平局", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean checkFiveInLine(List<Point> whiteArray) {
        for (Point point : whiteArray) {
            int x = point.x;
            int y = point.y;
            //横向连珠
            Boolean horizontalWin = mUtils.checkHorizontal(x, y, whiteArray);
            Boolean verictalWin = mUtils.checkVerictal(x, y, whiteArray);
            Boolean leftDiagonalWin = mUtils.checkleftDiagonal(x, y, whiteArray);
            Boolean rightDiagonalWin = mUtils.checkrightDiagonal(x, y, whiteArray);
            if (horizontalWin || verictalWin || leftDiagonalWin || rightDiagonalWin) {
                return true;
            }
        }
        return false;
    }


    private void drawPiece(Canvas canvas) {
        //for循环这样写是为了性能考虑
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - ratioPieceofLineHeight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - ratioPieceofLineHeight) / 2) * mLineHeight, null);
        }
        for (int i = 0, n = mBlockArray.size(); i < n; i++) {
            Point blackPoint = mBlockArray.get(i);
            canvas.drawBitmap(mBlockPiece, (blackPoint.x + (1 - ratioPieceofLineHeight) / 2) * mLineHeight,
                    (blackPoint.y + (1 - ratioPieceofLineHeight) / 2) * mLineHeight, null);
        }
    }

    //绘制棋盘
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            //如果游戏结束那么不允许在落子
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point point = getValidPoint(x, y);
            if (mWhiteArray.contains(point) || mBlockArray.contains(point)) {
                return false;
            }
            if (mIsWhite) {
                mWhiteArray.add(point);
            } else {
                mBlockArray.add(point);
            }
            invalidate();
            mIsWhite = !mIsWhite;
        }
        return true;
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    public void RepaintListener(boolean b) {
        Log.i("TAG", "接口回调成功");
        if (b) {
            mWhiteArray.removeAll(mWhiteArray);
            mBlockArray.removeAll(mBlockArray);
            mIsGameOver = false;
            invalidate();
        }
    }
}
