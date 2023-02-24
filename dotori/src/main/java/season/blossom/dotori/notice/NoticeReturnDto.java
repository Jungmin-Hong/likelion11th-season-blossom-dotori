package season.blossom.dotori.notice;

import lombok.Data;

@Data
public class NoticeReturnDto {
    private Long id;
    private Long postId;
    private String postTitle;
    private String content;
    private String postType;
    private String noticeType;

    public NoticeReturnDto(Notice notice) {
        this.id = notice.getId();
        this.content = notice.getContent();
        this.noticeType = notice.getNoticeType().toString();
        this.postType = notice.getPostType().toString();
        if(postType.equals("DELIVERY")){
            this.postId = notice.getDeliveryPost().getId();
            this.postTitle = notice.getDeliveryPost().getTitle();
        }
        else{
            this.postId = notice.getRoommatePost().getId();
            this.postTitle = notice.getRoommatePost().getTitle();
        }
    }
}
