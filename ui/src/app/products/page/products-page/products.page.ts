import {Component, OnInit} from "@angular/core";
import {ProductPreviewModel} from "../../model/product-preview.model";
import {NgbPaginationConfig} from "@ng-bootstrap/ng-bootstrap";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductsService} from "../../service/products.service";
import {ComparisonType, ProductFilerModel} from "../../model/product-filer.model";

@Component({
  selector: 'app-products.page',
  templateUrl: 'products.page.html',
  styleUrls: ['products.page.less']
})
export class ProductsPage implements OnInit{
  currentPage = 1;
  pageSize = 24;
  category: string
  subcategory: string
  products: ProductPreviewModel[] = []
  numberOfProducts
  priceRanges: { label: string, min: number, max: number }[] = [
    { label: 'All', min: 0, max: Infinity},
    { label: 'Below $50', min: 0, max: 50 },
    { label: '$50 - $100', min: 50, max: 100 },
    { label: '$100 - $200', min: 100, max: 200 },
    { label: '$200 - $500', min: 200, max: 500 },
    { label: '$500 and above', min: 500, max: Infinity }
  ];

  availableOnly: boolean = false
  selectedPriceRange: any;
  priceFilters: ProductFilerModel[] = []
  selectedOnlyFilter: ProductFilerModel

  constructor(config: NgbPaginationConfig, private route: ActivatedRoute, private router: Router, private productsService: ProductsService) {
    // Customize pagination appearance
    config.boundaryLinks = true;
    config.maxSize = 5;
    this.route.params.subscribe(params => {
      this.category = params['category']
      this.subcategory = params['subcategory']
      this.applyFilters()
    })
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
  onPriceChange(range: any) {
    this.priceFilters = []
    if(range.min == 0 && range.max == Infinity) {
      this.priceFilters.push({fieldName:'price', comparisonType: ComparisonType.GREATER_THAN, value: range.min.toString()})
    } else if (range.min == 500 && range.max == Infinity) {
      this.priceFilters.push({fieldName:'price', comparisonType: ComparisonType.GREATER_THAN, value: range.min.toString()})
    } else if(range.min == 0 && range.max == 50) {
      this.priceFilters.push({fieldName:'price', comparisonType: ComparisonType.LESS_THAN, value: range.max.toString()})
    } else {
      this.priceFilters.push({fieldName:'price', comparisonType: ComparisonType.BETWEEN, value: range.min.toString()+'|'+range.max.toString()})
    }
    this.applyFilters()
  }

  onAvailableOnly(val) {
    if(val.checked) {
      this.selectedOnlyFilter = {fieldName: 'outOfStock', comparisonType: ComparisonType.EQUALS, value: 'false'}
    } else {
      this.selectedOnlyFilter = null
    }
    this.applyFilters()
  }

  applyFilters(arr: ProductFilerModel[] = []) {
    const filters: ProductFilerModel[] = []
    filters.push({fieldName: 'category', comparisonType: ComparisonType.EQUALS, value: this.category})
    if (this.subcategory != '') {
      filters.push({fieldName: 'subcategory', comparisonType: ComparisonType.EQUALS, value: this.subcategory})
    }
    if(this.priceFilters != []) {
      this.priceFilters.forEach(filter => {
        filters.push(filter)
      })
    }
    if(this.selectedOnlyFilter != null) {
      filters.push(this.selectedOnlyFilter)
    }
    this.productsService.getProductsOverview(filters, this.currentPage.toString()).subscribe((res: any) => {
      this.products = res.products
      this.numberOfProducts = res.numberOfProducts
    })

  }

  ngOnInit() {
    this.fun()
  }


  dummyProduct: ProductPreviewModel = {
    id: 'dasdas',
    title: 'Telefon mobil Samsung Galaxy A04s, 32GB, 3GB RAM, 4G, Green',
    imagePath: 'https://s13emagst.akamaized.net/products/47987/47986357/images/res_319c737b16eaca27c8979cbb7636f38a.jpg?width=450&height=450&hash=536627584EA622C57F741280E3C23898',
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
    title: 'Laptop Gaming ASUS TUF F15 FX506HE cu procesor Intel® Core™ i5-11400H pana la 4.50 GHz, 15.6", Full HD, IPS, 144 Hz, 16GB, 512GB SSD, NVIDIA® GeForce RTX™ 3050 Ti 4GB GDDR6, No OS, Graphite Black',
    imagePath: 'https://s13emagst.akamaized.net/products/38293/38292813/images/res_1b08d70a1eee1f6f25e22914f1f7d3bd.jpg?width=450&height=450&hash=E41B5328278D336F1AA65182667D06D4',
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
