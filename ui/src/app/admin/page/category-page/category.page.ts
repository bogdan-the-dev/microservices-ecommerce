import {Component, OnInit} from "@angular/core";
import {SelectionModel} from "@angular/cdk/collections";
import {CategoryModel} from "../../model/category.model";
import {Sort} from "@angular/material/sort";
import {CategoryService} from "../../service/category.service";
import {MatDialog} from "@angular/material/dialog";
import {CategoryModalComponent} from "../../component/category-modal/category-modal.component";

@Component({
  selector: 'app-category.page',
  templateUrl: 'category.page.html',
  styleUrls: ['category.page.less']
})
export class CategoryPage implements OnInit {
  categories: CategoryModel[]
  displayedColumns: string[] = ['select', 'id', 'name', 'subcategories'];
  selection = new SelectionModel<CategoryModel>(true, []);
  searchKeyword: string = ''

  filteredCategories: CategoryModel[] = []

  constructor(private categoryService: CategoryService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.onRefresh()
  }

  isAllSelected() {
    const numSelected = this.selection.selected?.length;
    const numRows = this.categories?.length;
    return numSelected === numRows;
  }


  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.categories.forEach(row => this.selection.select(row));
  }


  openPopupCreate() {
    const elementRef = this.dialog.open(CategoryModalComponent, {
      width: '400px',
      data: {
        model: null,
        editMode: false
      }
    })
  }

  openPopupEdit() {
    const elementRef = this.dialog.open(CategoryModalComponent, {
      width: '400px',
      data: {
        model: this.selection.selected[0],
        editMode: true
      }
    })
  }

  onDelete() {
    this.categoryService.deleteCategory(this.selection.selected[0].id).subscribe(_ => {
      this.onRefresh()
    })
  }

  onRefresh(){
    this.categoryService.getCategories().subscribe(res => {
      this.categories = res
      this.applyFilter()
    })
    this.selection.clear()

  }

  sortData(sort: Sort) {
    const data = this.categories.slice()
    if (!sort.active || sort.direction === '') {
      this.categories = data
      return;
    }
    this.categories = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc'
      switch (sort.active) {
        case 'id' : {
          return this.compare(a.id, b.id, isAsc)
        }
        case 'name': {
          return this.compare(a.name, b.name, isAsc)
        }
        default: {
          return 0
        }
      }
    })
  }

  compare(a: number | string , b: number | string , isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  applyFilter() {
    this.filteredCategories = []
    this.filteredCategories = this.categories.filter(elem => {
      if(this.searchKeyword == '') {
        return true
      }
      return elem.name.trim().toLowerCase().includes(this.searchKeyword.trim().toLowerCase())
    })
  }



}
