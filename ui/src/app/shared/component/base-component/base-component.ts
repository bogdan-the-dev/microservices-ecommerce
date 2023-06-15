import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-base',
  template: ''
})
export class BaseComponent implements OnInit, OnDestroy {
  subscriptions = new Array<Subscription>()

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

}
