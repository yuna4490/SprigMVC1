package hello.itemservice.web.basic;

import hello.itemservice.domain.Item.Item;
import hello.itemservice.domain.Item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final 붙은 애한테 자동으로 생성자 주입해줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        // 클래스 이름이 Item -> item 으로 model에 들어감
        itemRepository.save(item);
        return "/basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item){
        // string, int 같은 단순 타입이 오면 @RequestParam이 자동 적용하므로 생략 가능
        // 객체의 경우 @ModelAttribute가 자동으로 적용하므로 생략 가능
        itemRepository.save(item);
        return "/basic/item";
    }

   //@PostMapping("/add")
    public String addItemV5(Item item){
        // redirect를 통해서 상품을 save한 후 상세화면으로 돌려줌
        // 이렇게 하면 새로고침을 할 때 상세화면이 불러와지므로 상품을 중복 등록하는 문제가 발생하지 않음음
       itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    //redirectattribute 사용해서 "저장했습니다" 문구 뜨도록 하기
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
       Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
        //{itemId}에 savedItem.getId()가 치환되고, status는 쿼리 파라미터 형태로 뒤에 붙음 ?status=true
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }



    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 1));
        itemRepository.save(new Item("itemB", 20000, 2));
    }
}
