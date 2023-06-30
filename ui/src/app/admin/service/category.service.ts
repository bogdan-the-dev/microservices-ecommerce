import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CategoryModel, SubcategoryModel} from "../model/category.model";
import {AdminApiUrls} from "./admin.api-urls";

@Injectable({
  providedIn: "root"
})
export class CategoryService {

  constructor(private http: HttpClient) {
  }

  getCategories() {
    return this.http.get<CategoryModel[]>(AdminApiUrls.getALlCategories)
  }
  getCategory() {
    return this.http.get<CategoryModel>(AdminApiUrls.getCategory)
  }
  addCategory(category: CategoryModel) {
    return this.http.post(AdminApiUrls.addCategory, {name: category.name, subcategories: category.subcategories.map(elem => {return elem.name})})
  }

  deleteCategory(categoryId: string) {
    return this.http.delete(AdminApiUrls.deleteCategory + '?id=' + categoryId)
  }
  editCategory(category: CategoryModel) {
    return this.http.put(AdminApiUrls.editCategory, {id: category.id, name: category.name, subcategories: category.subcategories})
  }

}
