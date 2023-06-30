import {Component, EventEmitter, Output} from "@angular/core";

@Component({
  selector: 'app-product-filter',
  templateUrl: 'product-filter.component.html',
  styleUrls: ['product-filter.component.less']
})
export class ProductFilterComponent {
  @Output() filterApplied: EventEmitter<any> = new EventEmitter<any>();

  brands: string[] = ['Brand 1', 'Brand 2', 'Brand 3']; // Replace with your brand options
  priceRanges: { label: string, min: number, max: number }[] = [
    { label: 'Below $50', min: 0, max: 50 },
    { label: '$50 - $100', min: 50, max: 100 },
    { label: '$100 - $200', min: 100, max: 200 },
    { label: '$200 - $500', min: 200, max: 500 },
    { label: '$500 and above', min: 500, max: Infinity }
  ];

  selectedBrands: string[] = []

  ratings: number[] = [1, 2, 3, 4, 5]; // Replace with your rating options
  selectedRatings: number[] = [];
  selectedAvailability: string = '';
  minPrice: number = 0;
  maxPrice: number = 10000;

  selectedPrice: string = 'all';

  availability: boolean = false;
  selectedPriceRange: any;

  applyFilter() {
    const filterOptions = {
      brands: this.selectedBrands,
      ratings: this.selectedRatings,
      price: this.selectedPrice,
      availability: this.availability
    };

    this.filterApplied.emit(filterOptions);
  }

  onCategoryFilterChange(brand: string) {
    if(this.selectedBrands.includes(brand)) {
      this.selectedBrands = this.selectedBrands.filter(brandList => brandList !== brand)
    } else {
      this.selectedBrands.push(brand)
    }
  }

  onRatingFilterChange(rating: number) {
    if(this.selectedRatings.includes(rating)) {
      this.selectedRatings = this.selectedRatings.filter(ratingList =>  ratingList !== rating)
    } else {
      this.selectedRatings.push(rating)
    }
  }


  protected readonly Array = Array;
}
