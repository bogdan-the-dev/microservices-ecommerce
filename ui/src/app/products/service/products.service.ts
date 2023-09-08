import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ProductApiUrls} from "./product.api-urls";
import {ProductFullModel} from "../model/product-full.model";
import {ProductTableModel} from "../../admin/model/product-table.model";
import {CreateProductModel} from "../../admin/model/create-product.model";
import {ProductFilerModel} from "../model/product-filer.model";
import {Deserializer} from "../../shared/utils/deserializer";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  constructor(private http: HttpClient) {
  }

  getProduct(id: string) {
    return this.http.get<ProductFullModel>(ProductApiUrls.getProduct + '?id=' + id)
  }

  getProductsOverview(filters: ProductFilerModel[], pageNumber: string) {
    return this.http.put(ProductApiUrls.getProductsPreview + '?page=' + pageNumber, filters)
  }

  getProductForCart(ids: string[]) {
    return this.http.put(ProductApiUrls.getProductForCart, ids)
  }

  getProductsForTable() {
    return this.http.get<ProductTableModel[]>(ProductApiUrls.getProductsForTable)
  }

  addProduct(product: CreateProductModel) {
    return this.http.post(ProductApiUrls.addProduct, product)
  }

  editProduct(product: CreateProductModel) {
    return this.http.put(ProductApiUrls.editProduct, product)
  }

  deleteProduct(productIds: string[]) {
    return this.http.put(ProductApiUrls.deleteProduct, productIds)
  }

  enableProducts(productsId: string[]) {
    return this.http.put(ProductApiUrls.enableProduct, productsId)
  }

  disableProducts(productsId: string[]) {
    return this.http.put(ProductApiUrls.disableProduct, productsId)
  }

  changeCategory(productIds: string[], category: string, subcategory: string) {
    return this.http.put(ProductApiUrls.changeCategory, {ids: productIds, category: category, subcategory: subcategory})
  }

  private convertToFormData(product: CreateProductModel) {
    const formData = new FormData()
    formData.append('id', product.id)
    formData.append('title', product.title)
    formData.append('price', product.price.toString())
    formData.append('description', product.description)
    formData.append('category', product.category)
    formData.append('subcategory', product.subcategory)
    formData.append('outOfStock', product.outOfStock.toString())

    for(let i = 0; i < product.photos.length; i++) {
      formData.append('photos[]', product.photos[i])
    }
    const specificationsObj: { [key: string]: { [key: string]: string } } = {};
    Deserializer.deserialize(product.specifications).forEach((value, key) => {
      specificationsObj[key] = Object.fromEntries(value);
    });

    formData.append('specifications', JSON.stringify(specificationsObj));    if(product.promotion) {
      formData.append('promotion', JSON.stringify(product.promotion))
    }
    return formData


  }

}
