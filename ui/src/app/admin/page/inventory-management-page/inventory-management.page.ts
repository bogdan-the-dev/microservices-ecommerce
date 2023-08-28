import {Component, OnInit} from "@angular/core";
import {InventoryModel} from "../../model/inventory.model";
import {SelectionModel} from "@angular/cdk/collections";
import {AdminAccountGridModel} from "../../model/admin-account-grid.model";
import {MatDialog} from "@angular/material/dialog";
import {Sort} from "@angular/material/sort";
import {InventoryService} from "../../service/inventory.service";
import {CreateAdminModalComponent} from "../../component/create-admin-account-modal/create-admin-modal.component";
import {EditInventoryModalComponent} from "../../component/edit-Inventory-modal/edit-Inventory-modal.component";

@Component({
  selector: 'app-inventory-management.page',
  styleUrls: ['inventory-management.page.less'],
  templateUrl: 'inventory-management.page.html'
})
export class InventoryManagementPage implements OnInit{

  inventories: InventoryModel[]
  displayedColumns: string[] = ['select', 'productName', 'quantity', 'productId']
  selection = new SelectionModel<InventoryModel>(true, [])
  searchKeyword: string = ''
  filteredInventories: InventoryModel[] = []


  constructor(private dialog: MatDialog, private service: InventoryService) {
  }

  ngOnInit() {
    this.onRefresh()
  }

  isAllSelected() {
    const numSelected = this.selection.selected?.length;
    const numRows = this.filteredInventories?.length;
    return numSelected === numRows && numRows > 0;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.filteredInventories.forEach(row => this.selection.select(row));
  }
  onEdit() {
    const dialogRef = this.dialog.open(EditInventoryModalComponent, {
      width: '600px',
      data: {
        productName: this.selection.selected[0].productName,
        productId: this.selection.selected[0].productId,
        quantity: this.selection.selected[0].quantity
      }
    }).afterClosed().subscribe(_ => {
      this.onRefresh()
    })
  }

  onRefresh() {
    this.service.GetInventories().subscribe(res => {
      this.inventories = res
      this.applyFilter()
    })
    this.selection.clear()
  }


  sortData(sort: Sort) {
    const data = this.filteredInventories.slice()
    if (!sort.active || sort.direction === '') {
      this.filteredInventories   = data
      return;
    }
    this.filteredInventories = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc'
      switch (sort.active) {
        case 'productName': {
          return this.compare(a.productName, b.productName, isAsc)
        }
        case 'quantity': {
          return this.compare(a.quantity, b.quantity, isAsc)
        }
        case 'productId': {
          return  this.compare(a.productId, b.productId, isAsc)
        }
        default: {
          return 0
        }
      }
    })
  }

  compare(a: string | number, b: string | number, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  applyFilter() {
    this.filteredInventories = []
    this.filteredInventories = this.inventories.filter(elem => {
      if(this.searchKeyword == '') {
        return true
      }
      return elem.productName.trim().toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    })
  }


}
