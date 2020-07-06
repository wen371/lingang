package com.jw.common.support.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class QRCode2 {

    private static final String CHARSET     = "utf-8";
    private static final String FORMAT_NAME = "JPG";

    // 二维码尺寸
    private static final int    QRCODE_SIZE = 175;


    public  BufferedImage createImage(String content,int qSize) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qSize, qSize,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        /*if (imgPath == null || "".equals(imgPath)) {
            return image;
        }*/
        // 插入图片
        //QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }



//
//     public static void main(String[] args) {
//         try {
////             BufferedImage b =  createImage("http://kargilik.xbdcar.com/yc/warrior/warriorCard/o1YpKsybMJPGvJaoI_PQWqWXVWQg.jpg");
////            // OutputStream os;
////             //  FileWriter fileWriter = new FileWriter(fileName, true);
////
////             ImageIO.write(b, FORMAT_NAME, new File("E:/poster/zz/test.jpg"));
////
////             //os = new FileOutputStream(b);
//
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }



}
