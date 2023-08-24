import {Component, Inject, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProductsService} from "../../../products/service/products.service";
import {CategoryService} from "../../service/category.service";
import {CategoryModel, SubcategoryModel} from "../../model/category.model";

@Component({
  selector: 'app-product-set-category-modal',
  templateUrl: 'product-set-category-modal.component.html',
  styleUrls: ['product-set-category-modal.component.less']
})
export class ProductSetCategoryModalComponent implements OnInit {
  form: FormGroup
  ids: string[]
  categories: CategoryModel[]
  subcategoryOptions: SubcategoryModel[] = []
  disableSubcategory

  constructor(public dialogRef: MatDialogRef<ProductSetCategoryModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              private productService: ProductsService,
              private formBuilder: FormBuilder,
              private categoryService: CategoryService) {
    this.ids = data.ids
  }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(res => {
      this.categories = res
    })
    this.form = this.formBuilder.group({
      category: ['', Validators.required],
      subcategory: ['', Validators.required]
    })
  }

  onCategoryChange(cat) {
    const selectedCategory = this.categories.find(category => category.id === cat.id)

    if(selectedCategory && selectedCategory.subcategories.length > 0) {
      this.subcategoryOptions = selectedCategory.subcategories
      this.disableSubcategory = false
      this.form.get('subcategory').setValidators(Validators.required)
      this.form.get('subcategory').updateValueAndValidity({emitEvent: false})
    } else {
      this.form.get('subcategory').clearValidators()
      this.disableSubcategory = true
      this.subcategoryOptions = []
      this.form.get('subcategory').setErrors(null)
    }
    this.form.updateValueAndValidity()
  }
  closeDialog() {
    this.dialogRef.close()
  }

  onSubmit() {
    this.productService.changeCategory(this.ids, this.form.get('category').value.name, this.form.get('subcategory').value).subscribe(_ => {
      this.closeDialog()
    })
  }

}
