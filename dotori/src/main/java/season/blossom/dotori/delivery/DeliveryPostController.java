package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class DeliveryPostController {
    private DeliveryPostService deliveryPostService;

    // 목록
    @GetMapping("/")
    public String list(Model model) {
        List<DeliveryPostDto> deliveryPostList = deliveryPostService.getDeliveryPostList();
        model.addAttribute("deliveryPostList", deliveryPostList);
        return "deliveryPost/list";
    }

    // 글 작성
    @GetMapping("/post")
    public String write() {
        return "deliveryPost/write";
    }

    // 글 작성 완료 후
    @PostMapping("/post")
    public String write(DeliveryPostDto deliveryPostDto) {
        deliveryPostService.savePost(deliveryPostDto);
        return "redirect:/";
    }

    //
}
