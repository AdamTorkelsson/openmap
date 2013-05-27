package com.openmap.grupp1.mapview;

import com.openmap.grupp1.R;
import com.openmap.grupp1.R.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.renderscript.Type;
import android.util.Log;

public class MarkerFactory {

	public MarkerFactory(){
		
	}
	

public Bitmap createPic(String stringTitle, Resources res, String type){
	int color;
	if(type == "Event"){
		color = Color.BLACK;
	}
	else if(type == "Location"){
		color = Color.GREEN;}
	else if(type == "Owners"){
		color = Color.BLUE;}
	else{
		Log.d("TEST", "wrong type");
		color = Color.RED;
	}
color = Color.BLACK;
	
	Bitmap srv = BitmapFactory.decodeResource(res, R.drawable.markerpick); 
	Bitmap src = Bitmap.createScaledBitmap(srv, srv.getWidth()+ srv.getWidth(), 
								srv.getHeight() + srv.getHeight(), false);
	Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

	
	if(stringTitle.length() > 7){
		stringTitle = (String) stringTitle.subSequence(0, 6);
		stringTitle = stringTitle + "...";
	}
	
		Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(35);
        tPaint.setColor(color);
        tPaint.setStyle(Style.FILL);
        cs.drawBitmap(src, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        float width = tPaint.measureText(stringTitle);
        float x_coord = (src.getWidth() - width)/2;
        cs.drawText(stringTitle, x_coord, height-3f, tPaint); 
        return dest;}
	
}



