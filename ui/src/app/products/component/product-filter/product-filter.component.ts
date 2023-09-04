import {Component, EventEmitter, Output} from "@angular/core";

@Component({
  selector: 'app-product-filter',
  templateUrl: 'product-filter.component.html',
  styleUrls: ['product-filter.component.less']
})
export class ProductFilterComponent {
  @Output() filterApplied: EventEmitter<any> = new EventEmitter<any>();

  priceRanges: { label: string, min: number, max: number }[] = [
    { label: 'All', min: 0, max: Infinity},
    { label: 'Below $50', min: 0, max: 50 },
    { label: '$50 - $100', min: 50, max: 100 },
    { label: '$100 - $200', min: 100, max: 200 },
    { label: '$200 - $500', min: 200, max: 500 },
    { label: '$500 and above', min: 500, max: Infinity }
  ];

  selectedBrands: string[] = []

  selectedAvailability: string = '';
  minPrice: number = 0;
  maxPrice: number = 10000;

  selectedPrice: string = 'all';

  availability: boolean = false;
  selectedPriceRange: any;

  applyFilter() {
    const filterOptions = {
      minPrice: this.selectedPrice,
      maxPrice: this.selectedPrice,
      availability: this.availability
    };

    this.filterApplied.emit(filterOptions);
  }

  protected readonly Array = Array;
}
