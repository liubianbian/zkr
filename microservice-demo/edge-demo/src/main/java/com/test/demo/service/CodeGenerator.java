package com.test.demo.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @description: 二维码测试
 * @author: luofang
 * @create: 2019-11-07 17:37
 **/
public class CodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "C:/Users/lufang/Desktop/二维码/qqq.png";


    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("https://member-uat.test-cignacmb.com/?test=864654", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (Exception e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        }

    }

}
