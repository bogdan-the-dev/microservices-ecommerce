import {Component, Input, OnInit} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {ProductFullModel} from "../../model/product-full.model";
import {ProductsService} from "../../service/products.service";
import {Store} from "@ngrx/store";
import {ItemCartModel} from "../../../shopping-cart/model/item-cart.model";
import {CartAction} from "../../../shopping-cart/state-management/cart.action";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product-component',
  templateUrl: 'product.component.html',
  styleUrls: ['product.component.less']
})
export class ProductComponent extends BaseComponent implements OnInit{
  @Input() productId: string

  currentPictureIndex = 0;

  product: ProductFullModel /*={
    id: "dummy-id-123",
    photos: [
      "https://upload.wikimedia.org/wikipedia/commons/a/ab/Gallet_clamshell_600x600_movement.jpg",
      "https://storage.googleapis.com/photo-storage-bucket/dsadasda/MicrosoftTeams-image.png",
      "https://storage.googleapis.com/photo-storage-bucket/dsadasda/Screenshot_1675866058.png"
    ],
    title: "Laptop Gaming ASUS TUF F15 FX506HE cu procesor Intel® Core™ i5-11400H pana la 4.50 GHz, 15.6, Full HD, IPS, 144 Hz, 16GB, 512GB SSD, NVIDIA® GeForce RTX™ 3050 Ti 4GB GDDR6, No OS, Graphite Black",
    price: 49.99,
    category: "Electronics",
    subcategory: "Smartphones",
    description: "This is a dummy product description.",
    specifications: new Map<string, Map<string, string>>([
      [
        "Color",
        new Map<string, string>([
          ["label", "Color"],
          ["value", "Black"]
        ])
      ],
      [
        "Memory",
        new Map<string, string>([
          ["label", "Memory"],
          ["value", "64GB"]
        ])
      ]
    ]),
    promotion: {name: 'dasdasd', percentage: 20},
    outOfStock: false,
    variation: new Map<string, Map<string, string>>([
      [
        "Size",
        new Map<string, string>([
          ["link1", "Small"],
          ["link2", "Medium"],
          ["link3","Large"]
        ])
      ],
      [
        "Style",
        new Map<string, string>([
          ["link3", "Vintage"],
          ["link4", "Modern"]
        ])
      ]
    ])
  };*/

  variations: [string,[string, string][]][] = []

  constructor(private productService: ProductsService, private store: Store<any>, private route: ActivatedRoute) {
    super();
  }

  override ngOnInit() {
    this.subscriptions.push(this.route.params.subscribe(params => {
      this.productId = params['id']
      this.getProduct()
    }))
  }

  private getProduct() {
    this.productService.getProduct(this.productId)
      .subscribe(res => {
        this.product = res
      })
    this.parseVariations()
  }
  private parseVariations() {
    this.product.variation.forEach((variationOption, variationName) => {
      let arr: [string, string][] = []
      variationOption.forEach((option, link) => {
        arr.push([link, option])
      })
      this.variations.push([variationName, arr])
    })
  }

  onAddToCart() {
    const realPrice = this.product.promotion !== undefined ? Number((this.product.price - (this.product.price * (this.product.promotion.percentage / 100))).toFixed(2)) : this.product.price
    const item: ItemCartModel = {id: this.product.id, price: realPrice, title: this.product.title, quantity: 1, image: this.product.photos[0]}
    this.store.dispatch({type: CartAction.ADD_TO_CART, payload: item})
  }

  nextPicture() {
    if (this.currentPictureIndex < this.product.photos.length - 1) {
      this.currentPictureIndex++;
    } else {
      this.currentPictureIndex = 0;
    }
  }

  prevPicture() {
    if (this.currentPictureIndex > 0) {
      this.currentPictureIndex--;
    } else {
      this.currentPictureIndex = this.product.photos.length - 1;
    }
  }

}
