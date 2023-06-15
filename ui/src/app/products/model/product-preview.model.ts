import {PromoModel} from "./promo.model";

export interface ProductPreviewModel {
  id: string
  title: string
  imagePath: string
  promActive: boolean
  promo?: PromoModel
  price: number
  rating: number
  numberOfReviews: number
  isAvailable: boolean
  tags?: string[]
}
