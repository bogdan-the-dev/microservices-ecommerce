export interface CategoryModel {
  id: string,
  name: string
  subcategories: SubcategoryModel[]
}

export interface SubcategoryModel {
  id: string
  name: string


}
