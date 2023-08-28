import {ApiUrls} from "../../shared/service/api-urls";

export class ProductApiUrls {
  static baseProductUrl = ApiUrls.serverUrl + '/api/v1/products'
  static getProductsPreview = ProductApiUrls.baseProductUrl + '/get-product-previews'
  static getProduct = ProductApiUrls.baseProductUrl + '/get-full-product'
  static getProductsForTable = ProductApiUrls.baseProductUrl + '/get-product-for-table'
  static getProductForPreview = ProductApiUrls.baseProductUrl + '/get-product-previews'
  static addProduct = ProductApiUrls.baseProductUrl + '/create'
  static editProduct = ProductApiUrls.baseProductUrl + '/edit'
  static deleteProduct = ProductApiUrls.baseProductUrl + '/delete'
  static getProductForCart = ProductApiUrls.baseProductUrl + '/get-product-cart'
  static enableProduct = ProductApiUrls.baseProductUrl + '/enable'
  static disableProduct = ProductApiUrls.baseProductUrl + '/disable'
  static changeCategory = ProductApiUrls.baseProductUrl + '/change-category'
}
