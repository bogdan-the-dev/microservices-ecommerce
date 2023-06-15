import {Component} from "@angular/core";

@Component({
  selector: 'app-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.less']
})
export class NavbarComponent {
  showCategoriesDropdown = false;
  showSub: boolean = false
  selectedCategory /*= {
    id: 1,
    name: 'Category 1',
    subcategories: [
      { id: 1, name: 'Subcategory 1.1' },
      { id: 2, name: 'Subcategory 1.2' },
      { id: 3, name: 'Subcategory 1.3' }
    ]
  }*/
  showSubcategoriesDropdown = false;


  categories = [
    {
      id: 1,
      name: 'Category 1',
      subcategories: [
        { id: 1, name: 'Subcategory 1.1' },
        { id: 2, name: 'Subcategory 1.2' },
        { id: 3, name: 'Subcategory 1.3' }
      ]
    },
    {
      id: 2,
      name: 'Category 2',
      subcategories: [
        { id: 4, name: 'Subcategory 2.1' },
        { id: 5, name: 'Subcategory 2.2' },
        { id: 6, name: 'Subcategory 2.3' }
      ]
    }
    // Add more categories and subcategories as needed
  ];

  activeCategory: any = null;
  activeCategoryIndex: number = -1;

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
}
