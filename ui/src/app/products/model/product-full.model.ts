import {PromoModel} from "./promo.model";

export interface ProductFullModel{
  id: string
  photos: string[]
  title: string
  price: number
  category: string
  subcategory: string
  description: string
  specifications: Map<string, Map<string, string>>
  promotion: PromoModel
  outOfStock: boolean
  variation: Map<string, Map<string, string>>
  isEnabled?: boolean
}
