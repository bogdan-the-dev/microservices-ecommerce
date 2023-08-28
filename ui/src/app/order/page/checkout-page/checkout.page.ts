import {Component} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {ItemCartModel} from "../../../shopping-cart/model/item-cart.model";
import {select, Store} from "@ngrx/store";
import {filter} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-checkout.page',
  templateUrl: 'checkout.page.html',
  styleUrls: ['checkout.page.less']
})
export class CheckoutPage extends BaseComponent{
  cartItems: ItemCartModel[] = []
  total: number

  billingForm: FormGroup
  addressForm: FormGroup
  paymentForm: FormGroup

  sameData: boolean

  constructor(private store: Store<any>) {
    super();
  }

  override ngOnInit() {
    super.ngOnInit();
    this.sameData = false
    this.store.pipe(
      select(s => s.cartModuleFeature.cartState.items),
      filter(s => s !== null)
    ).subscribe(items => {
      this.cartItems = items
    })
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.cartModuleFeature.cartState.total),
        filter(s => s !== null)
      ).subscribe(total => {
        this.total = total
      })
    )
    this.billingForm = new FormGroup({
      'firstName': new FormControl('', Validators.required),
      'lastName': new FormControl('', Validators.required),
      'address': new FormControl('', Validators.required),
      'city': new FormControl('', Validators.required),
      'county': new FormControl('', Validators.required)
    })

    this.addressForm = new FormGroup({
      'same': new FormControl(false),
      'phone': new FormControl('', Validators.required),
      'firstName': new FormControl('', Validators.required),
      'lastName': new FormControl('', Validators.required),
      'address': new FormControl('', Validators.required),
      'city': new FormControl('', Validators.required),
      'county': new FormControl('', Validators.required),
      'notifyViaSMS': new FormControl()
    })

    this.paymentForm = new FormGroup({
      'method': new FormControl('', Validators.required)
    })

  }

  onSameAsBillingToggle(event) {
    if (event.checked) {
      this.addressForm.patchValue({
        'firstName': this.billingForm.get('firstName').value,
        'lastName': this.billingForm.get('lastName').value,
        'address': this.billingForm.get('address').value,
        'city': this.billingForm.get('city').value,
        'county': this.billingForm.get('county').value
      })

      this.addressForm.get('firstName').disable()
      this.addressForm.get('lastName').disable()
      this.addressForm.get('address').disable()
      this.addressForm.get('city').disable()
      this.addressForm.get('county').disable()

    } else {
      this.addressForm.patchValue({
        'firstName': '',
        'lastName': '',
        'address': '',
        'city': '',
        'county': ''
      })
      this.addressForm.enable()
    }
  }

}
