import {Component} from "@angular/core";
import {BaseComponent} from "../../../shared/component/base-component/base-component";
import {ItemCartModel} from "../../../shopping-cart/model/item-cart.model";
import {select, Store} from "@ngrx/store";
import {filter} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BillingInfoModel} from "../../model/billing-info.model";
import {DeliveryInfoModel} from "../../model/delivery-info.model";
import {getPaymentTypeByValue, PaymentType} from "../../model/payment-type";
import {loadStripe} from "@stripe/stripe-js";
import {HttpClient} from "@angular/common/http";
import {OrderApiUrls} from "../../service/order.api-urls";
import {OrderModel} from "../../model/order.model";
import {CreateOrderModel} from "../../model/create-order.model";
import {OrderItemDtoModel} from "../../model/order-item-dto.model";
import {OrderService} from "../../service/order.service";
import {Router} from "@angular/router";
import {CartAction} from "../../../shopping-cart/state-management/cart.action";

@Component({
  selector: 'app-checkout.page',
  templateUrl: 'checkout.page.html',
  styleUrls: ['checkout.page.less']
})
export class CheckoutPage extends BaseComponent{
  cartItems: ItemCartModel[] = []
  total: number

  transport = 24;

  billingInfo: BillingInfoModel
  deliveryInfo: DeliveryInfoModel
  paymentType: PaymentType

  billingForm: FormGroup
  addressForm: FormGroup
  paymentForm: FormGroup

  sameData: boolean

  disableGoBack: boolean = false

  private username

  constructor(private store: Store<any>, private http: HttpClient, private orderService: OrderService, private router: Router) {
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
    this.subscriptions.push(
      this.store.pipe(
        select(s => s.loginModuleFeature.loginState),
        filter(s => s !== undefined)
      ).subscribe(loginData => {
        if(loginData.success) {
          this.username = loginData.username
        }
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

  onBillingFormFinished() {
    this.billingInfo = {firstName: this.billingForm.get('firstName').value,
                        lastName: this.billingForm.get('lastName').value,
                        address: this.billingForm.get('address').value,
                        city: this.billingForm.get('city').value,
                        county: this.billingForm.get('county').value
    }
  }
  onDeliveryFormFinished() {
    this.deliveryInfo = {
      firstName: this.addressForm.get('firstName').value,
      lastName: this.addressForm.get('lastName').value,
      address: this.addressForm.get('address').value,
      city: this.addressForm.get('city').value,
      county: this.addressForm.get('county').value,
      phoneNumber: this.addressForm.get('phone').value
    }
  }

  onPaymentSelected() {
    this.paymentType = this.paymentForm.get('method').value
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

  capitalize (input: string) {
    if(input != null)
      return input
        .split(' ') // Split the input into words
        .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()) // Capitalize each word
        .join(' '); // Join the words back together with spaces
    else return null
  }

  async attemptPayment(orderId: string) {

    let stripePromise = loadStripe('pk_test_51NJdFwKsuRPrWUFjHwpU36CUFilxkyom9loy6HC7YmA5NU7XUoWxMt1pb3zVoM082wbwvZ6s7SREzrGWz7SKCAiE00DQ1L5OoN')
    {
      const payment = {
        name: 'Order ' + orderId,
        currency: 'usd',
        amount: this.total * 100,
        quantity: 1
      };

      const stripe = await stripePromise

      this.http.post(OrderApiUrls.pay, payment).subscribe((res: any) => {
        stripe.redirectToCheckout({sessionId: res.id})
      })
    }
  }

  placeOrder() {
    let order: OrderModel = {
      id: null,
      username: this.username,
      orderDate: null,
      orderTotal: this.total,
      trackingNumber: null,
      phoneNumber: this.deliveryInfo.phoneNumber,
      deliveryAddress: this.deliveryInfo.county+', '+this.deliveryInfo.city+', '+this.deliveryInfo.address,
      deliveryName: this.deliveryInfo.firstName + ' ' +this.deliveryInfo.lastName,
      billingName: this.billingInfo.firstName + ' ' +this.billingInfo.lastName,
      billingAddress: this.billingInfo.county+', '+this.billingInfo.city+', '+this.billingInfo.address,
      stripeId: null,
      status: null,
      paymentType: this.paymentType,
      transportCost: 24.00,
      notifySMS: this.addressForm.get('notifyViaSMS').value
    }
    let arr = []

    this.cartItems.forEach(elem => {
      let orderItem: OrderItemDtoModel = {
        itemId: elem.id,
        quantity: elem.quantity
      }
      arr.push(orderItem)
    })

    let orderDTO: CreateOrderModel = {
      order: order,
      orderItemDTOList: arr
    }
    this.orderService.place(orderDTO).subscribe((res: any) => {
      if(!res.valid) {
        window.alert("Not enough products in inventory")
      }
      if(orderDTO.order.paymentType == PaymentType.CARD && res.valid == true) {
        this.attemptPayment(res.orderId)
      } else if(res.valid) {
        this.store.dispatch({type: CartAction.EMPTY_CART, payload: {}})
      }
    })
    this.disableGoBack = true;

  }

  redirectToOrders() {
    this.router.navigate(['/','order','my-orders'])
  }

  protected readonly Object = Object;
  protected readonly PaymentType = PaymentType;
  protected readonly getPaymentTypeByValue = getPaymentTypeByValue;
}
