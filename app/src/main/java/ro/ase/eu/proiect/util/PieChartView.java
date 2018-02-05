package ro.ase.eu.proiect.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcelable;
import android.view.View;

import java.util.List;

import ro.ase.eu.proiect.R;

public class PieChartView extends View{

    private List<Line> lines = null;
    int total=0;
    float lines1stop=0;
    float lines2stops=0;
    float lines3stopsOrMore=0;
    RectF rectf = new RectF (10, 10, 400, 400);
    int temp=0;
    private Paint paint = null;
    public PieChartView(Context context, List<Line> lines) {
        super(context);

        this.lines = lines;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculate();

        paint.setColor(Color.BLUE);
        canvas.drawArc(rectf, 0, lines1stop, true, paint);
        canvas.drawLine(425,10,430,10,paint);
        canvas.drawLine(430,10,430,5,paint);
        canvas.drawLine(430,5,425,5,paint);
        canvas.drawLine(425,5,425,10,paint);

        paint.setColor(getResources().getColor(R.color.chart_pink));
        canvas.drawArc(rectf, lines1stop, lines2stops, true, paint);
        canvas.drawRect(425,30,430,30,paint);
        canvas.drawLine(430,30,430,25,paint);
        canvas.drawLine(430,25,425,25,paint);
        canvas.drawLine(425,25,425,30,paint);

        paint.setColor(getResources().getColor(R.color.chart_blue));
        canvas.drawArc(rectf,lines1stop+ lines2stops, lines3stopsOrMore, true, paint);
        canvas.drawLine(425,50,430,50,paint);
        canvas.drawLine(430,50,430,45,paint);
        canvas.drawLine(430,45,425,45,paint);
        canvas.drawLine(425,45,425,50,paint);

        paint.setColor(Color.BLACK);
        canvas.save();
        canvas.drawText("Linii cu 1 statie",435,10,paint);
        canvas.drawText("Linii cu 2 statii",435,30,paint);
        canvas.drawText("Linii cu 3 statii sau mai multe",435,50,paint);

    }

    public void calculate(){

        if(lines != null){
            total = lines.size();
            for(int i = 0; i< lines.size();i++) {
                if(lines.get(i).getStops().size() == 1){
                    lines1stop++;
                }else
                if(lines.get(i).getStops().size() == 2){
                    lines2stops++;
                }
                else{
                    lines3stopsOrMore++;
                }
            }
            lines1stop =360*(lines1stop/total);
            lines2stops =360*(lines2stops/total);
            lines3stopsOrMore =360*(lines3stopsOrMore/total);
        }
    }
}
