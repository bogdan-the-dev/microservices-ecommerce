import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import * as http from "http";
import {PromotionModel} from "../model/promotion.model";
import {AdminApiUrls} from "./admin.api-urls";

@Injectable({
  providedIn: 'root'
})
export class PromotionService {
  constructor(private http: HttpClient) {
  }

  getPromotion(id: string) {
    return this.http.get<PromotionModel>(AdminApiUrls.getPromotion + '?id=' + id)
  }
  getAllPromotions() {
    return this.http.get<PromotionModel[]>(AdminApiUrls.getAllPromotions)
  }
  getActivePromotions() {
    return this.http.get<PromotionModel[]>(AdminApiUrls.getPromotionActive)
  }
  createPromotion(promotion: PromotionModel) {
    return this.http.post(AdminApiUrls.createPromotion, promotion)
  }
  enablePromotions(ids: string[]) {
    return this.http.put(AdminApiUrls.enablePromotions, ids)
  }
  disablePromotions(ids: string[]) {
    return this.http.put(AdminApiUrls.disablePromotions, ids)
  }
  editPromotion(promotion: PromotionModel) {
    return this.http.put(AdminApiUrls.editPromotion, promotion)
  }
  deletePromotion(ids: string[]) {
    return this.http.put(AdminApiUrls.deletePromotion, ids)
  }
}
