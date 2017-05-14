package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.UploadImageResult;
import com.michen.olaxueyuan.protocol.result.UploadMediaResult;
import com.michen.olaxueyuan.protocol.result.VideoUploadResult;
import com.snail.photo.upload.UploadResult;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by mingge on 16/4/28.
 */
public interface UploadService {
    /**
     * @param imgData
     * @param imgAngle
     * @param width
     * @param height
     * @param picType
     * @param cb
     * @see {{@link #uploadImage(TypedFile, String, Callback)}}
     * @deprecated
     */
    @Multipart
    @POST("/SDpic/common/picUpload")
    void uploadImage(@Part("imgData") TypedFile imgData,
                     @Part("imgAngle") int imgAngle,
                     @Part("width") int width,
                     @Part("height") int height,
                     @Part("picType") String picType,
                     Callback<UploadResult> cb);

    /**
     * 上传音频和视频
     *
     * @param movData
     * @param movType mp3、mp4
     * @param cb
     * @see {{@link #updaloadMedia(TypedFile, String, Callback)}}
     * @deprecated
     */
    @Multipart
    @POST("/SDpic/common/movieUpload")
    void movieUpload(@Part("movData") TypedFile movData,
                     @Part("movType") String movType,
                     Callback<VideoUploadResult> cb);

    /**
     * 新的上传图片
     *
     * @param imgData
     * @param picType
     * @param cb
     */
    @Multipart
    @POST("/ola/upload/uploadImage")
    void uploadImage(@Part("file") TypedFile imgData,
                     @Part("fileType") String picType,
                     Callback<UploadImageResult> cb);

    /**
     * 上传音频和视频
     *
     * @param imgData
     * @param picType mp3、mp4
     * @param cb
     */
    @Multipart
    @POST("/ola/upload/uploadMedia")
    void updaloadMedia(@Part("file") TypedFile imgData,
                       @Part("fileType") String picType,
                       Callback<UploadMediaResult> cb);

}
