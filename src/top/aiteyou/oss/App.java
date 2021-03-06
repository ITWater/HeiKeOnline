package top.aiteyou.oss;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;

/**
 * Hello world!
 *
 */
public class App {
private static String accessKeyId = "LTAIT4YbLlA9DMtQ";
private static String accessKeySecret = "ISxUR9oDh9IT3ZCZuTG3C5Wn248Rnq";
static DefaultAcsClient aliyunClient= aliyunClient = new DefaultAcsClient(
DefaultProfile.getProfile("cn-beijing",accessKeyId,accessKeySecret));
    public static void main( String[] args )
    {
        String videoId = createUploadVideo(aliyunClient);
        System.out.println("VideoId:" + videoId);
        //当网络异常导致文件上传失败时,可刷新上传凭证后再次执行上传操作
//        refreshUploadVideo(aliyunClient, videoId);
    }
    private static String createUploadVideo(DefaultAcsClient client) {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        CreateUploadVideoResponse response = null;
        try {
              /*必选，视频源文件名称（必须带后缀, 支持 ".3gp", ".asf", ".avi", ".dat", ".dv", ".flv", ".f4v", ".gif", ".m2t", ".m3u8", ".m4v", ".mj2", ".mjpeg", ".mkv", ".mov", ".mp4", ".mpe", ".mpg", ".mpeg", ".mts", ".ogg", ".qt", ".rm", ".rmvb", ".swf", ".ts", ".vob", ".wmv", ".webm"".aac", ".ac3", ".acm", ".amr", ".ape", ".caf", ".flac", ".m4a", ".mp3", ".ra", ".wav", ".wma"）*/
              request.setFileName("3.mp4");
              //必选，视频标题
              request.setTitle("视频标题");
              //可选，分类ID
              request.setCateId(0);
              //可选，视频标签，多个用逗号分隔
              request.setTags("标签1,标签2");
              //可选，视频描述
              request.setDescription("视频描述");
              response = client.getAcsResponse(request);
        } catch (ServerException e) {
              System.out.println("CreateUploadVideoRequest Server Exception:");
              e.printStackTrace();
              return null;
        } catch (ClientException e) {
              System.out.println("CreateUploadVideoRequest Client Exception:");
              e.printStackTrace();
              return null;
        }
        System.out.println("RequestId:"+response.getRequestId());
        System.out.println("UploadAuth:"+response.getUploadAuth());
        System.out.println("UploadAddress:"+response.getUploadAddress());
        return response.getVideoId();
  }
  private static void refreshUploadVideo(DefaultAcsClient client, String videoId) {
      RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
      RefreshUploadVideoResponse response = null;
      try {
            request.setVideoId(videoId);
            response = client.getAcsResponse(request);
      } catch (ServerException e) {
            System.out.println("RefreshUploadVideoRequest Server Exception:");
            e.printStackTrace();
            return;
      } catch (ClientException e) {
            System.out.println("RefreshUploadVideoRequest Client Exception:");
            e.printStackTrace();
            return;
      }
      System.out.println("RequestId:" + response.getRequestId());
      System.out.println("UploadAuth:" + response.getUploadAuth());
}
}
