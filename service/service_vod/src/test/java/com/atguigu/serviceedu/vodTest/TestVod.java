package com.atguigu.serviceedu.vodTest;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    private static initObject InitObject;

    public static void main(String[] args) throws Exception {
        getPlayAuth();
//        getVideoPlayURL();

//         //测试本地文件上传
//        String accessKeyId = "LTAI4G3JqujYo4jFAaEqmDRJ";
//        String accessKeySecret = "ueWlv3zQDVYxBiqkPQUxgJ7XCo0Tai";
//
//        String title = "6 - What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
//        String fileName = "F:/6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
//        //上传视频的方法
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        }
    }

    //1 根据视频iD获取视频播放凭证
    public static void getPlayAuth() throws Exception{
        //第一步  先获取 初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G3JqujYo4jFAaEqmDRJ", "ueWlv3zQDVYxBiqkPQUxgJ7XCo0Tai");
        //第二部  创建获取视频凭证的request 和 response 对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //第三步 向request对象设置视频id
        request.setVideoId("0e6b718305e046588d0697e8e276e05a");
        //第四步 调用初始化对象的方法获取内容 通过response对象接受
        response = client.getAcsResponse(request);

        System.out.println("playAuth:"+response.getPlayAuth());
    }

    //2 获取视频播放地址
    public static void getVideoPlayURL() throws com.aliyuncs.exceptions.ClientException {
        //第一步  先获取 初始化对象

        DefaultAcsClient client = InitObject.initVodClient("LTAI4G3JqujYo4jFAaEqmDRJ","ueWlv3zQDVYxBiqkPQUxgJ7XCo0Tai");
        //第二部 获取视屏
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //第三步 想request设置 视频id
        request.setVideoId("0e6b718305e046588d0697e8e276e05a");

        //第四步 调用方法得到视频地址 用response对象接受
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL = "+ playInfo.getPlayURL() +"\n");
        }
    }


}
