import {Component, OnInit} from "@angular/core";
import {MyOrderModel} from "../../../order/model/my-order.model";
import {SelectionModel} from "@angular/cdk/collections";
import {MatDialog} from "@angular/material/dialog";
import {OrderService} from "../../../order/service/order.service";
import {Sort} from "@angular/material/sort";
import {OrderStatus} from "../../../order/model/order-status";

@Component({
  selector: 'app-order-management.page',
  templateUrl: 'order-management.page.html',
  styleUrls: ['order-management.page.less']
})
export class OrderManagementPage implements OnInit{

  orders: MyOrderModel[]
  displayedColumns: string[] = ['select', 'id', 'orderStatus', 'date', 'total', 'trackingNumber']
  selection = new SelectionModel<MyOrderModel>(true, [])
  searchKeyword: string = ''
  filteredOrders: MyOrderModel[] = []

  constructor(private dialog: MatDialog, private service: OrderService) {
  }

  ngOnInit() {
    this.onRefresh()
  }

  isAllSelected() {
    const numSelected = this.selection.selected?.length;
    const numRows = this.filteredOrders?.length;
    return numSelected === numRows && numRows > 0;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.filteredOrders.forEach(row => this.selection.select(row));
  }

  onRefresh() {
    this.service.getAll().subscribe(res => {
      this.orders = res
      this.applyFilter()
    })
    this.selection.clear()
  }

  onEdit() {

  }

  onCancel() {

  }

  sortData(sort: Sort) {
    const data = this.filteredOrders.slice()
    if (!sort.active || sort.direction === '') {
      this.filteredOrders   = data
      return;
    }
    this.filteredOrders = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc'
      switch (sort.active) {
        case 'id': {
          return this.compare(a.id, b.id, isAsc)
        }
        case 'date': {
          return this.compare(a.date, b.date, isAsc)
        }
        case 'total': {
          return  this.compare(a.total, b.total, isAsc)
        }
        default: {
          return 0
        }
      }
    })
  }

  applyFilter() {
    this.filteredOrders = []
    this.filteredOrders = this.orders.filter(elem => {
      if(this.searchKeyword == '') {
        return true
      }
      return elem.id.toString().trim().toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    })
  }
  compare(a: string | number | Date , b: string | number | Date, isAsc: boolean) {
    if (a instanceof Date && b instanceof Date) {
      return (a.getTime() - b.getTime()) * (isAsc ? 1 : -1);
    }
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  private getSelectedIds() {
    return this.selection.selected.map(elem => {return elem.id})
  }
}
