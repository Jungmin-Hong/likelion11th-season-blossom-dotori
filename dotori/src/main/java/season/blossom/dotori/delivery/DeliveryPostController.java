package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class DeliveryPostController {
    private DeliveryPostService deliveryPostService;

    // 목록
    @GetMapping("/board/delivery")
    public String list(Model model) {
        List<DeliveryPostDto> deliveryPostList = deliveryPostService.getDeliveryPostList();
        model.addAttribute("deliveryPostList", deliveryPostList);
        return "deliveryPost/list";
    }

    // 글 작성
    @GetMapping("/board/delivery/write")
    public String write() {
        return "deliveryPost/write";
    }

    // 글 작성 완료 후
    @PostMapping("/board/delivery/write")
    public String write(DeliveryPostDto deliveryPostDto) {
        deliveryPostService.savePost(deliveryPostDto);
        return "redirect:/";
    }

    // 상세 조회
    @GetMapping("/board/delivery/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        DeliveryPostDto deliveryPostDto = deliveryPostService.getPost(no);

        model.addAttribute("boardDto", deliveryPostDto);
        return "deliveryPost/detail";
    }

    // 수정
    @GetMapping("/board/delivery/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        DeliveryPostDto deliveryPostDto = deliveryPostService.getPost(no);

        model.addAttribute("deliveryPostDto", deliveryPostDto);
        return "deliveryPost/update";
    }

    @PutMapping("/board/delivery/edit/{no}")
    public String update(DeliveryPostDto deliveryPostDto) {
        deliveryPostService.savePost(deliveryPostDto);

        return "redirect:/";
    }

    @DeleteMapping("/board/delivery/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        deliveryPostService.deletePost(no);

        return "redirect:/";
    }
}
