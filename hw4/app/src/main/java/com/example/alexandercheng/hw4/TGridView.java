package com.example.alexandercheng.hw4;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Canvas;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by alexandercheng on 9/28/15.
 */
public class TGridView extends View {

    TGrid tgrid = new TGrid(4, 6);
    private Paint lines = new Paint();
    private Paint paint = new Paint();
    private int cellWidth;
    private int cellHeight;
    Paint squares = new Paint();

    public TGridView(Context context){
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public TGridView(Context context, TGrid tg){
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tgrid = tg;
    }

    public TGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void addTGrid(TGrid tg) {
        tgrid = tg;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        cellWidth = getWidth()/tgrid.getWidth();
        cellHeight = getHeight()/(tgrid.getHeight()-2);

        canvas.drawColor(Color.WHITE);
        lines.setColor(Color.BLACK);

//        for (int i = 0; i < (tgrid.getHeight()-2); i++)
//        {
//            canvas.drawLine(0, i * getHeight()/(tgrid.getHeight()-2), getWidth(), i * getHeight()/(tgrid.getHeight()-2),
//                   lines);
//
//
//        }
//
//        for (int i = 0; i < tgrid.getWidth(); i++)
//        {
//            canvas.drawLine(i * getWidth()/tgrid.getWidth(), 0, i * getWidth()/tgrid.getWidth(),  getHeight(),
//                    lines);
//        }


        for (int x = 0; x < tgrid.getWidth(); x++) {
            for (int y = 2; y < (tgrid.getHeight()); y++) {
                TCell currCell = tgrid.getCellAt(x, y);
                if(currCell != null) {
                    squares.setColor(currCell.getColor());
                    squares.setStyle(Paint.Style.FILL);
                    canvas.drawRect(x * cellWidth, (y-2) * cellHeight, (x + 1) * cellWidth, (y - 1) * cellHeight, squares);

                    lines.setStyle(Paint.Style.STROKE);
                    lines.setStrokeWidth(2);
                    lines.setColor(Color.BLACK);
                    canvas.drawRect(x * cellWidth, (y-2) * cellHeight, (x + 1) * cellWidth, (y - 1) * cellHeight, lines);

                    //canvas.drawLine(x * cellWidth, y * cellHeight, (x + 1) * cellWidth, y * cellHeight, lines);
                    //canvas.drawLine(x * cellWidth, y * cellHeight, x * cellWidth, (y + 1) * cellHeight, lines);
                    //canvas.drawLine(x * cellWidth, (y + 1) * cellHeight, (x + 1) * cellWidth, (y + 1) * cellHeight, lines);
                    //canvas.drawLine((x + 1) * cellWidth, y * cellHeight, (x + 1) * cellWidth, (y + 1) * cellHeight, lines);

                }
            }
        }

    }
}
