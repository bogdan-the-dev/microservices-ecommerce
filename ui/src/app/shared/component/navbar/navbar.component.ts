import {Component, OnInit} from "@angular/core";
import {CategoryService} from "../../../admin/service/category.service";
import {Router} from "@angular/router";
import {select, Store} from "@ngrx/store";
import {filter} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.less']
})
export class NavbarComponent implements OnInit {
  showCategoriesDropdown = false;
  showSub: boolean = false

  showSubcategoriesDropdown = false;

  userRole

  categories

  activeCategory: any = null;
  activeCategoryIndex: number = -1;

  constructor(private categoryService: CategoryService, private router: Router, private store: Store<any>) {
  }

  ngOnInit() {
    this.onLoad()

    this.store.pipe(
      select(s => s.loginModuleFeature.loginState.role),
      filter(s => s !== undefined)
    ).subscribe(role => {
      this.userRole = role
    })
  }

  isAdmin() : boolean {
    return this.userRole == "ADMIN"
  }

  showCategories(): void {
    this.showCategoriesDropdown = true;
  }

  hideCategories(): void {
    this.showCategoriesDropdown = false;
  }

  showSubcategories(category: any): void {
    this.activeCategory = category;
    this.showSub = ! this.showSub
    this.activeCategoryIndex = this.categories.findIndex((c) => c.id === category.id);
  }

  hideSubcategories(): void {
    this.activeCategory = null;
    this.activeCategoryIndex = -1;
  }

  isActiveCategory(category: any): boolean {
    return this.activeCategoryIndex !== -1 && category.id === this.categories[this.activeCategoryIndex].id;
  }

  onLoad() {
    this.categoryService.getCategories().subscribe(res => {
      this.categories = res
    })
  }

  onNavigate(val: [category: string, subcategory: string]) {
    this.router.navigate(['products', val[0], val[1]])
  }
}
