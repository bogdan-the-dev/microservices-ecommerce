import {Component, Inject, OnInit} from "@angular/core";
import {CategoryModel, SubcategoryModel} from "../../model/category.model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CategoryService} from "../../service/category.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-category-modal',
  templateUrl: 'category-modal.component.html',
  styleUrls: ['category-modal.component.less']
})
export class CategoryModalComponent implements OnInit {
  form: FormGroup;
  category: CategoryModel;
  editMode: boolean;
  buttonAction: string;

  constructor(
    public dialogRef: MatDialogRef<CategoryModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private categoryService: CategoryService,
    private formBuilder: FormBuilder
  ) {
    this.category = data.model;
    this.editMode = data.editMode;
  }

  ngOnInit() {
    if (this.editMode) {
      this.buttonAction = 'Edit';
    } else {
      this.buttonAction = 'Create';
      this.category = {
        id: '',
        name: '',
        subcategories: []
      };
    }

    this.form = this.formBuilder.group({
      name: [this.category.name, Validators.required],
      subcategories: this.formBuilder.array([])
    });

    if (this.editMode) {
      this.category.subcategories.forEach((subcategory: SubcategoryModel) => {
        const subcategoryGroup = this.formBuilder.group({
          id: [subcategory.id],
          name: [subcategory.name, Validators.required]
        });
        this.subcategories.push(subcategoryGroup);
      });
    }
  }

  get subcategories(): FormArray {
    return this.form.get('subcategories') as FormArray;
  }

  addSubcategory(): void {
    const subcategoryGroup = this.formBuilder.group({
      id: [null],
      name: [null, Validators.required]
    });
    this.subcategories.push(subcategoryGroup);
  }

  removeSubcategory(index: number): void {
    this.subcategories.removeAt(index);
  }

  onSubmit(): void {
    if (this.form.valid) {
      const category: CategoryModel = {
        id: this.category.id,
        name: this.form.get('name').value,
        subcategories: this.form.get('subcategories').value
      };

      if (this.editMode) {
        this.categoryService.editCategory(category).subscribe(res => {
          this.closeDialog();
        });
      } else {
        this.categoryService.addCategory(category).subscribe(res => {
          this.closeDialog();
        });
      }
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
