import {Component, OnInit} from "@angular/core";
import {ProductTableModel} from "../../model/product-table.model";
import {SelectionModel} from "@angular/cdk/collections";
import {ProductsService} from "../../../products/service/products.service";
import {MatDialog} from "@angular/material/dialog";
import {Sort} from "@angular/material/sort";
import {ProductModalComponent} from "../../component/product-modal/product-modal.component";

@Component({
  selector: 'app-product-management.page',
  templateUrl: 'product-management.page.html',
  styleUrls: ['product-management.page.less']
})
export class ProductManagementPage implements OnInit{
  products: ProductTableModel[]
  displayedColumns: string[] = ['select', 'imagePath', 'title', 'price', 'promoActive','category','subcategory','creationTime','isEnabled', 'isAvailable','id']
  selection = new SelectionModel<ProductTableModel>(true, [])
  searchKeyword: string = ''
  filteredProducts: ProductTableModel[] = []

  constructor(private productService: ProductsService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.onRefresh()
  }

  isAllSelected() {
    const numSelected = this.selection.selected?.length;
    const numRows = this.filteredProducts?.length;
    return numSelected === numRows;
  }


  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.filteredProducts.forEach(row => this.selection.select(row));
  }



  onCreate() {
    const dialogRef = this.dialog.open(ProductModalComponent, {
      width: '600px',
      data: {
        id: '',
        editMode: false
      }
    })
  }
  onEdit() {
    const dialogRef = this.dialog.open(ProductModalComponent, {
      width: '600px',
      data: {
        id: this.selection.selected[0].id,
        editMode: true
      }
    })
  }

  onCreateVariation() {

  }

  onDelete() {

  }

  onRefresh() {
    this.productService.getProductsForTable().subscribe(res => {
      this.products = res
      this.applyFilter()
    })
  }

  sortData(sort: Sort) {
    const data = this.filteredProducts.slice()
    if (!sort.active || sort.direction === '') {
      this.filteredProducts = data
      return;
    }
    this.filteredProducts = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc'
      switch (sort.active) {
        case 'id' : {
          return this.compare(a.id, b.id, isAsc)
        }
        case 'title': {
          return this.compare(a.title, b.title, isAsc)
        }
        case 'price': {
          return this.compare(a.price, b.price, isAsc)
        }
        case 'isAvailable': {
          return  this.compare(a.isAvailable, b.isAvailable, isAsc)
        }
        case 'category': {
          return  this.compare(a.category, b.category, isAsc)
        }
        case 'subcategory': {
          return this.compare(a.subcategory, b.subcategory, isAsc)
        }
        case 'creationDate': {
          return this.compare(a.creationTime, b.creationTime, isAsc)
        }
        case 'isEnabled': {
          return  this.compare(a.isEnabled, b.isEnabled, isAsc)
        }
        default: {
          return 0
        }
      }
    })
  }

  compare(a: number | string | boolean | Date, b: number | string | boolean | Date, isAsc: boolean) {
    if (typeof a === 'boolean' && typeof b === 'boolean') {
      return (a === b ? 0 : (a ? 1 : -1)) * (isAsc ? 1 : -1);
    }

    if (a instanceof Date && b instanceof Date) {
      return (a.getTime() - b.getTime()) * (isAsc ? 1 : -1);
    }
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }
  applyFilter() {
    this.filteredProducts = []
    this.filteredProducts = this.products.filter(elem => {
      if(this.searchKeyword == '') {
        return true
      }
      return elem.title.trim().toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    })
  }

}
