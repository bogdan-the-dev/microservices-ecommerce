import {Component, Inject, OnInit} from "@angular/core";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ProductFullModel} from "../../../products/model/product-full.model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProductsService} from "../../../products/service/products.service";
import {PromotionModel} from "../../model/promotion.model";
import {CategoryModel, SubcategoryModel} from "../../model/category.model";
import {CategoryService} from "../../service/category.service";
import { moveItemInArray, CdkDragDrop } from '@angular/cdk/drag-drop';
import {PromotionService} from "../../service/promotion.service";
import {CreateProductModel, ProductPromotion} from "../../model/create-product.model";

@Component({
  selector: 'app-product-modal',
  templateUrl: 'product-modal.component.html',
  styleUrls: ['product-modal.component.less']
})
export class ProductModalComponent implements OnInit{
  form: FormGroup
  id: string
  product: ProductFullModel
  editMode: boolean
  buttonAction: string
  promotions: PromotionModel[]
  category: CategoryModel[]
  subcategoryOptions: SubcategoryModel[] = []
  selectedPhotos = []
  disableSubcategory
  disableInitialQuantity: boolean = true

  constructor(public dialogRef: MatDialogRef<ProductModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data,
              private productService: ProductsService,
              private formBuilder: FormBuilder,
              private categoryService: CategoryService,
              private promotionService: PromotionService) {
    this.id = data.id
    this.editMode = data.editMode

  }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(res => {
      this.category = res
    })
    this.promotionService.getAllPromotions().subscribe(res => {

      this.promotions = res.filter(elem => {return elem.active})
    })

    if(this.id !== '') {
      this.productService.getProduct(this.id).subscribe(res => {
        this.product = res
        this.selectedPhotos = this.product.photos
        this.subcategoryOptions = this.category.find(cat => cat.name === this.product.category).subcategories
        this.patchInitialData()
      })
    }

    this.form = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      category: ['', Validators.required],
      subcategory: [''],
      photos: [[]],
      specifications: this.formBuilder.array([]),
      promotion: ['', ],
      outOfStock: [true],
      initialQuantity: [''],
      isEnabled: []
    });

    if(this.editMode) {
        this.buttonAction = 'Edit'


    } else {
      this.buttonAction = 'Create'
      this.form.get('initialQuantity').disable()
    }
  }

  get specificationsFormArray() {
    return this.form.get('specifications') as FormArray
  }

  getSpecsArray(subcategoryGroup) {
    return subcategoryGroup.get('fields') as FormArray
  }

  addSpecificationSubcategory() {
    const subcategoryGroup = this.formBuilder.group({
      subcategory: ['', Validators.required],
      fields: this.formBuilder.array([])
    })
    this.specificationsFormArray.push(subcategoryGroup)
  }

  removeSpecificationSubcategory(index: number) {
    this.specificationsFormArray.removeAt(index)
  }

  addSpecificationField(subcategoryIndex: number) {
    const subcategoryFormGroup = this.specificationsFormArray.at(subcategoryIndex) as FormGroup
    const fieldsArray  = subcategoryFormGroup.get('fields') as FormArray

    const fieldGroup = this.formBuilder.group({
      name: ['', Validators.required],
      value: ['', Validators.required]
    })
    fieldsArray.push(fieldGroup)
  }

  removeSpecificationField(subcategoryIndex: number, fieldIndex: number) {
    const subcategoryFormGroup = this.specificationsFormArray.at(subcategoryIndex) as FormGroup
    const specificationsFormArray = subcategoryFormGroup.get('fields') as FormArray

    specificationsFormArray.removeAt(fieldIndex)
  }

  onCategorySelectionChange(cat) {
    const selectedCategory = this.category.find(category => category.id === cat.id)

      if (selectedCategory && selectedCategory.subcategories.length > 0) {
      this.subcategoryOptions = selectedCategory.subcategories
      this.disableSubcategory = false
      this.form.get('subcategory').setValidators(Validators.required)
        this.form.get('subcategory').updateValueAndValidity({ emitEvent: false })
      } else {
      this.form.get('subcategory').clearValidators()
      this.disableSubcategory = true
        this.subcategoryOptions = []
      this.form.get('subcategory').setErrors(null)
      }
    this.form.updateValueAndValidity()
  }

  onPhotosSelected(event) {
    const files = event.target.files
    if (files && files.length > 0) {

      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader()
        reader.onload = () => {
          this.selectedPhotos.push(reader.result as string)
        };
        reader.readAsDataURL(files[i])
      }
    } else {
      this.selectedPhotos = []
    }
  }

  onOutOfStockChange(event: any) {
    const outOfStockChecked = event.checked;
    const initialQuantityControl = this.form.get('initialQuantity');

    if (outOfStockChecked) {
      initialQuantityControl.disable();
      initialQuantityControl.clearValidators()
      initialQuantityControl.setErrors(null)
      this.disableInitialQuantity = true
    } else {
      initialQuantityControl.enable();
      initialQuantityControl.setValidators([Validators.required, Validators.min(1)])
      initialQuantityControl.updateValueAndValidity({ emitEvent: false })
      this.disableInitialQuantity = false
    }
  }

  removeSelectedPhoto(index: number) {
    this.selectedPhotos.splice(index, 1);
  }

  reorderSelectedPhotos(event: CdkDragDrop<string[]>) {
    this.selectedPhotos = this.moveItemInArray(this.selectedPhotos, event.previousIndex, event.currentIndex);
  }

  private moveItemInArray(array: any[], previousIndex: number, currentIndex: number): any[] {
    const newArray = [...array]
    const movedItem = newArray.splice(previousIndex, 1)[0]
    newArray.splice(currentIndex, 0, movedItem)
    return newArray;
  }
  closeDialog(): void {
    this.dialogRef.close()
  }

  onSubmit() {
    const initialQuantity = this.disableInitialQuantity? 0 : this.form.get('initialQuantity').value
    const product: CreateProductModel = {
      id: this.id,
      title: this.form.get('title').value,
      description: this.form.get('description').value,
      price: this.form.get('price').value,
      category: this.form.get('category').value.name,
      subcategory: this.form.get('subcategory')?.value?.name,
      photos: this.selectedPhotos,
      specifications: this.parseSpecifications(),
      promotion: this.getPromotion(this.form.get('promotion').value),
      outOfStock: this.form.get('outOfStock').value,
      initialQuantity: initialQuantity,
      isEnabled: this.form.get('isEnabled').value
    }

    if (!this.editMode) {
      this.productService.addProduct(product).subscribe(_ => {
        this.closeDialog()
      })
    } else {
      this.productService.editProduct(product).subscribe(_ => {
        this.closeDialog()
      })
    }
  }

  private getPromotion(promotion: PromotionModel) {
    if (promotion !== null) {
      const promo: ProductPromotion = {
        id: promotion.id,
        name: promotion.name,
        percentage: promotion.percentage
      }
      return promo
    }
    return null
  }


  private parseSpecifications() {
    const specifications = new Map<string, Map<string, string>>()
    this.form.get('specifications').value.forEach(specCategory => {
      let specs = new Map<string, string>()
      specCategory.fields.forEach(pair => {
        specs.set(pair.name, pair.value)
      })
      specifications.set(specCategory.subcategory, specs)
    })
    return specifications
  }

  private dataUrlToFile(dataUrl: string): File {
    const arr = dataUrl.split(',')
    const mime = arr[0].match(/:(.*?);/)[1]
    const name = arr[0].match(/\/(.*?);/)[1]
    const bstr = atob(arr[1])
    let n = bstr.length
    const u8arr = new Uint8Array(n)

    while (n--) {
      u8arr[n] = bstr.charCodeAt(n)
    }

    return new File([u8arr], name, { type: mime })
  }



  private patchInitialData() {
    this.form.patchValue({
      title: this.product.title,
      description: this.product.description,
      price: this.product.price,
      category: this.category.find(cat => cat.name === this.product.category),
      subcategory: this.subcategoryOptions.find(sub => sub.name === this.product.subcategory),
      isEnabled: this.product.isEnabled,
      promotion: this.product.promotion,
  })

  }
  comparePromotions(promotion1: any, promotion2: any) {
    return promotion1 && promotion2 && promotion1.id === promotion2.id;
  }
}
