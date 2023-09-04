import {PromoModel} from "./promo.model";

export interface ProductFullModel{
  id: string
  photos: string[]
  title: string
  price: number
  category: string
  subcategory: string
  description: string
  specifications: string
  promotion: PromoModel
  outOfStock: boolean
  variation: string
  isEnabled?: boolean
}
