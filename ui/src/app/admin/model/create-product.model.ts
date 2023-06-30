
export interface CreateProductModel {
  id: string,
  title: string
  description: string
  price: number
  category: string
  subcategory: string
  photos: string[]
  specifications: Map<string, Map<string, string>>
  promotion: ProductPromotion
  outOfStock: boolean
  initialQuantity: number
  isEnabled: boolean
}

export interface ProductPromotion {
  id: string
  name: string
  percentage: number
}
