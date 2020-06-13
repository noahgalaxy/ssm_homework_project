package com.fisheep.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FontImage {
    public static void main(String[] args) throws Exception {
        FontImage.toImage();
//        createImage("请A1003到3号窗口", new Font("宋体", Font.BOLD, 30), new File(
//                "C:\\Users\\Fisheep\\Desktop\\new\\a.png"), 500, 500);
//        createImage("请A1002到2号窗口", new Font("黑体", Font.BOLD, 35), new File(
//                "e:/a1.png"), 4096, 64);
//        createImage("请A1001到1号窗口", new Font("黑体", Font.PLAIN, 40), new File(
//                "e:/a2.png"), 4096, 64);

    }

    /**
     * 根据str,font的样式以及输出文件目录
     * @param str	字符串
     * @param font	字体
     * @param outFile	输出文件位置
     * @param width	宽度
     * @param height	高度
     * @throws Exception
     */
    public static void createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.black);
        // 先用黑色填充整张图片,也就是背景
        g.fillRect(0, 0, width, height);
        // 在换成红色
        g.setColor(Color.red);
        // 设置画笔字体
        g.setFont(font);
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        // 256 340 0 680
        for (int i = 0; i < 6; i++) {
            // 画出字符串
            g.drawString(str, i * 680, y);
        }
        g.dispose();
        // 输出png图片
        ImageIO.write(image, "png", outFile);
    }

    public static void toImage() throws IOException {
        int width = 100;
        int height = 100;
        String s = "你好";

        File file = new File("C:\\Users\\Fisheep\\Desktop\\new\\a.png");

        Font font = new Font("Serif", Font.BOLD, 30);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.RED);

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(s, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;

        g2.drawString(s, (int)x, (int)baseY);

        ImageIO.write(bi, "jpg", file);
    }
}

