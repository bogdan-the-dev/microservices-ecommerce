import {ApiUrls} from "../../shared/service/api-urls";

export class AdminApiUrls {
  static readonly promotionBase = ApiUrls.serverUrl + '/api/v1/promotion'
  static readonly getAllPromotions = AdminApiUrls.promotionBase + '/get-all'
  static readonly getPromotion = AdminApiUrls.promotionBase + '/get-promotion'
  static readonly getPromotionActive = AdminApiUrls.promotionBase + '/get-active'
  static readonly createPromotion = AdminApiUrls.promotionBase + '/create'
  static readonly editPromotion = AdminApiUrls.promotionBase + '/edit'
  static readonly enablePromotions = AdminApiUrls.promotionBase + '/enable'
  static readonly disablePromotions = AdminApiUrls.promotionBase + '/disable'
  static readonly deletePromotion = AdminApiUrls.promotionBase + '/delete'

  static readonly categoryBase = ApiUrls.serverUrl + '/api/v1/category'
  static readonly getALlCategories = AdminApiUrls.categoryBase + '/get-all-categories'
  static readonly getCategory = AdminApiUrls.categoryBase + '/get-category'
  static readonly addCategory = AdminApiUrls.categoryBase + '/add-category'
  static readonly editCategory = AdminApiUrls.categoryBase + '/edit-category'
  static readonly deleteCategory = AdminApiUrls.categoryBase + '/delete-category'

}
