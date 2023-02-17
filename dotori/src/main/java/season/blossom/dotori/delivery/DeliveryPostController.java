package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class DeliveryPostController {
    private DeliveryPostService deliveryPostService;

    @GetMapping("/")
    public String list() {
        return "deliveryPost/list";
    }

    @GetMapping("/post")
    public String write() {
        return "deliveryPost/write";
    }

    @PostMapping("/post")
    public String write(DeliveryPostDto deliveryPostDto) {
        deliveryPostService.savePost(deliveryPostDto);

        return "redirect:/";
    }
}
