package hello.itemservice.domain.Item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@Data는 위험해서 메인 도메인?에는 원래 안 쓰는 게 좋긴 함
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // null 값이 들어갈 수도 있으므로 int 안 쓰고 integer
    private Integer quantity;

    public Item(){
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
