package com.dre.sjty.watermark;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.*;
import static org.opencv.core.Mat.zeros;

public class OpenCVDemo {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCVDemo demo = new OpenCVDemo();
//        demo.test();
        demo.fliy();
    }

    private void fliy() {
        Mat input = Imgcodecs.imread("/Users/yuqiuyuan/Downloads/WechatIMG1.jpeg");
        int w = getOptimalDFTSize(input.cols());
        int h = getOptimalDFTSize(input.rows());
        Mat padded = new Mat();
        copyMakeBorder(input, padded, 0, h - input.rows(), 0, w - input.cols(), BORDER_CONSTANT, new Scalar(0, 0, 0));//填充图像保存到padded中

        List<Mat> plane = new ArrayList<Mat>();//创建通道
        plane.add(padded);
        plane.add(zeros(padded.size(), CvType.CV_32F));
        ImgViewer imgViewer = new ImgViewer(padded);
        imgViewer.imshow();
        Mat complexIm = new Mat();
        merge(plane, complexIm);//合并通道
//        split(complexIm, plane);//分离通道
//        magnitude(plane.get(0), plane.get(1), plane.get(0));//获取幅度图像，0通道为实数通道，1为虚数，因为二维傅立叶变换结果是复数
//
//        int cx = padded.cols() / 2;
//        int cy = padded.rows() / 2;//一下的操作是移动图像，左上与右下交换位置，右上与左下交换位置
//        Mat temp = new Mat();
//        Mat part1 = new Mat(plane.get(0), new Rect(0, 0, cx, cy));
//        Mat part2 = new Mat(plane.get(0), new Rect(cx, 0, cx, cy));
//        Mat part3 = new Mat(plane.get(0), new Rect(0, cy, cx, cy));
//        Mat part4 = new Mat(plane.get(0), new Rect(cx, cy, cx, cy));
//
//        part1.copyTo(temp);
//        part4.copyTo(part1);
//        temp.copyTo(part4);
//
//        part2.copyTo(temp);
//        part3.copyTo(part2);
//        temp.copyTo(part3);
//
//
//        Mat _complexim = new Mat();
//        complexIm.copyTo(_complexim);//把变换结果复制一份，进行逆变换，也就是恢复原图
//        List<Mat> iDft = new ArrayList<Mat>();
//        iDft.add(zeros(plane.get(0).size(), CvType.CV_32F));
//        iDft.add(zeros(plane.get(0).size(), CvType.CV_32F));
//        idft(_complexim, _complexim);//傅立叶逆变换
//        split(_complexim, iDft);//结果貌似也是复数
//        magnitude(iDft.get(0), iDft.get(0), iDft.get(0));//分离通道，主要获取0通道
//        normalize(iDft.get(0), iDft.get(0), 1, 0, /*CV_MINMAX*/1);//归一化处理，float类型的显示范围为0-1,大于1为白色，小于0为黑色

//        Imgcodecs.imshow("idft", iDft.get(0));//显示逆变换

    }

    private void test() {

        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("Mat " + mat.toString());
    }
}
