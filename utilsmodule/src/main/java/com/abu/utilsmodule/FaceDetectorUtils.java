package com.abu.utilsmodule;


import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;

/**
 * 人脸检测工具类
 */
public class FaceDetectorUtils {

    private static FaceDetector faceDetector;

    private FaceDetectorUtils() {
    }

    public interface Callback {
        void onFaceDetected(Face[] faces, Bitmap bitmap);

        void onFaceNotDetected(Bitmap bitmap);
    }

    /**
     * 检测bitmap中的人脸，在callback中返回人脸数据
     *
     * @param bitmap
     * @param callback
     */
    public static void detectFace(Bitmap bitmap, Callback callback) {
        try {
            faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 1);
            Face[] faces = new Face[1];
            int faceNum = faceDetector.findFaces(bitmap, faces);
            if (faceNum > 0) {
                if (callback != null) {
                    callback.onFaceDetected(faces, bitmap);
                }
            } else {
                if (callback != null) {
                    callback.onFaceNotDetected(bitmap);
                    bitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
