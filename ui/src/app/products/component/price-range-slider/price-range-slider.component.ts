import {Component, Input, OnInit, Output} from "@angular/core";
import {LabelType, Options} from "@angular-slider/ngx-slider";

@Component({
  selector: 'app-price-range-selector',
  templateUrl: 'price-range-slider.component.html',
  styleUrls: ['price-range-slider.component.less']
})
export class PriceRangeSliderComponent{
  minPrice = 0;
  maxPrice: number = 100000;
  @Output() lowerBound: number
  @Output() upperBound: number
  @Output() sliderEnabled: boolean = false;

  options: Options = {
    floor: this.minPrice,
    ceil: this.maxPrice,
    disabled: !this.sliderEnabled,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:
          this.lowerBound  = value
          return value.toString();
        case LabelType.High:
          this.upperBound = value
          return "$" + value;
        default:
          return "$" + value;
      }
    }
  };

  constructor() {
    this.sliderEnabled = false;
  }

  toggleDisabled() {
    this.sliderEnabled = !this.sliderEnabled
    this.options = Object.assign({}, this.options, {disabled: !this.sliderEnabled});

  }
}
