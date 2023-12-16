package catchcompany.web.module.stock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private Long id;
	private String itemName;
	private Integer price;
	private Integer quantity;

	private Boolean open; //판매 여부
	private List<String> regions; //등록 지역
	private ItemType itemType; //상품 종류
	private String deliveryCode; //배송 방식

	public Item(Long id, String itemName, Integer price, Integer quantity) {
		this.id = id;
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
