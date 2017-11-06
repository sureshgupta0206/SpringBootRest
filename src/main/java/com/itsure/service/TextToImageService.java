package com.itsure.service;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TextToImageService {
	
	@Autowired
    private Environment env;
		
	public String saveImage( String text) throws IOException {
		String str = text;
	    String [] lines = str.split("\n");
		BufferedImage img = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();       
        FontRenderContext fontRenderContext = g2d.getFontRenderContext();
        Font plainFont = new Font("Times New Roman", Font.PLAIN, 24);       
        g2d.setColor(java.awt.Color.blue);
        g2d.setFont(plainFont);        
        int ascent=g2d.getFontMetrics().getAscent();
        float y=0;
        
        for(int count=0; count <lines.length;count++) {
        	if(lines[count].length()==0) {
        		 y+=ascent;	
        	} else {
        	AttributedString attributedString = new AttributedString(lines[count]);
            attributedString.addAttribute(TextAttribute.FONT, plainFont);
            LineBreakMeasurer measurer = new LineBreakMeasurer(attributedString.getIterator(), fontRenderContext);
            while(measurer.getPosition() < attributedString.getIterator().getEndIndex()) {
                TextLayout layout = measurer.nextLayout(1000);
                layout.draw(g2d, 0, y+ascent);
                y+=ascent;
            }
          }
        }     
        String serverUrl = env.getProperty("imageurl");
        File file = new File(env.getProperty("filePath"));
        new File(file.getParent()).mkdirs();
        ImageIO.write(img, "png", file);
        return serverUrl;
        
	}

}
