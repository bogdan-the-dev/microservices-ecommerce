import {Component, OnInit} from "@angular/core";
import {ProductPreviewModel} from "../../model/product-preview.model";
import {NgbPaginationConfig} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute, Router} from "@angular/router";
import {take} from "rxjs";

@Component({
  selector: 'app-products.page',
  templateUrl: 'products.page.html',
  styleUrls: ['products.page.less']
})
export class ProductsPage implements OnInit{
  currentPage = 1;
  pageSize = 24;


  constructor(config: NgbPaginationConfig, private route: ActivatedRoute, private router: Router) {
    // Customize pagination appearance
    config.boundaryLinks = true;
    config.maxSize = 5;
    this.route.queryParams.subscribe(params => {
      let pageNumber = params['page']
      if(pageNumber === undefined) {
        this.currentPage = 1
      }
      else {
        this.currentPage = Number(pageNumber)
      }
    })
  }


  ngOnInit() {
    this.fun()
  }


  dummyProduct: ProductPreviewModel = {
    id: 'dasdas',
    title: 'Tigaie inalta cu capac 2in1 reversibil, Tortillada, tip cuptor olandez Combo Cooker, fonta pre-asezonata, 26cm, 3.5L, manere cu husa de protectie termorezistenta, e-book 50 retete inclus',
    imagePath: 'https://s13emagst.akamaized.net/products/21544/21543666/images/res_46e05db67be480de4eac89e8bc0260b7.jpg?width=720&height=720&hash=70807539365BA3F2F40A8560D979C5F5',
    promActive: false,
    promo: {
      name: 'Summer Sale',
      percentage: 20,
    },
    price: 99.99,
    rating: 2.56,
    numberOfReviews: 10,
    isAvailable: false
  };

  dummyProduct2: ProductPreviewModel = {
    id: 'sadas',
    title: 'Tigaie inalta cu 4 capace denumita in popot Golf 7',
    imagePath: 'https://frankfurt.apollo.olxcdn.com/v1/files/tz2wrxee8hzi-RO/image;s=2048x1365',
    promActive: true,
    promo: {
      name: 'Winter Sale',
      percentage: 20,
    },
    price: 12050,
    rating: 3.56,
    numberOfReviews: 125,
    isAvailable: true
  };

  list = []
  fun() {
    for (let i = 0; i < 12; i++) {
      this.list.push(this.dummyProduct)
      this.list.push(this.dummyProduct2)
    }
  }
  onPageChange(pageNumber: number) {
    let val = pageNumber > 1 ? pageNumber : null
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: {'page': val},
        queryParamsHandling: 'merge'
      }
    )
    console.log(pageNumber)
    this.scrollTop();
  }

  private scrollTop(){
    const options: ScrollToOptions = {
      top: 0,
      behavior: 'smooth'
    }

    window.scroll(options)
  }
}
